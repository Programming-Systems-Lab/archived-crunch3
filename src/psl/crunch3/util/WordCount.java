/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 */
package psl.crunch3.util;
import com.google.soap.search.*;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author hb2143
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WordCount {

	private boolean useWordList;
	private int engineNum;
	private String STOPLIST_FILE = "frequency" + File.separator + "stoplist1.txt";;
	private final String SITES_FILE = "frequency" + File.separator + "sites.txt";
	private Vector stoplist;
	private Vector wordlist;
	
	public static void main(String[] args) {
		System.out.println(args[0] + "   " + args[1]);
		//if ((args[0] != null) && (args[1] != null))
			new WordCount(args[0], args[1]);
		//else System.out.println("please provide arguments for the number of engines (1,2,3)" 
			//	+ "and whether you want to use the words file or not (yes/no)");
	}
	
	public WordCount(String num, String words){
		if (words.equals("yes")) useWordList = true;
		else useWordList = false;
		engineNum = Integer.parseInt(num);
		storeStopList();
		try{
			BufferedReader inSites = new BufferedReader(new FileReader(new File(SITES_FILE)));
			String site = null;
			while((site = inSites.readLine()) != null){
				wordlist = new Vector();
				generateList(site);
			}
			inSites.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	/**
	 * Generates a lists of words and their corresponding frequencies and writes them to a file.
	 * @param url the address of the website
	 */
	private void generateList(String url){
		BufferedReader in = getWebsite(url);
		try{
			String line = null;
			while((line = in.readLine()) != null){
				store(removePunctuations(line));
			}
			in.close();
			
			//store words from google search.
			GoogleSearchResultElement[] re = getGoogle(parseURL(url));
			for(int k=0;k<re.length;k++){
				store(removePunctuations(re[k].getSnippet()+" "+re[k].getTitle()+" "+
						re[k].getURL()+" "+re[k].getSummary()));
			}
			
			//store words from yahoo search
			in = getYahoo(parseURL(url));
			line = null;
			while((line = in.readLine()) != null){
				store(removePunctuations(line));
			}
			in.close();
			
			//store words from dogpile search
			/*in = getDogPile(parseURL(url));
			line = null;
			while((line = in.readLine()) != null){
				store(removePunctuations(line));
			}
			in.close();*/
			
			
			
			removeStopWords();
			WordFreq temp; int i;
			
			//delete words that appear less than 5 times
			for(int j=0;j<wordlist.size();j++){
				temp = (WordFreq)(wordlist.elementAt(j));
				if ((temp.frequency)<5 ||(temp.word).length()<3||isNumber(temp.word)) {
					wordlist.removeElementAt(j);
					--j;
				}
				
				
			}
			wordlist.trimToSize();
			
			sort(wordlist,0,wordlist.size()-1);
			
			//write the data from wordlist to a file.
			DataOutputStream out = new DataOutputStream(new FileOutputStream("frequency" + File.separator+ parseURL(url)+".txt"));
			for (int j=0;j<wordlist.size();j++){
				out.writeBytes((((WordFreq)wordlist.elementAt(j)).word)+ "\t" + 
						(((WordFreq)wordlist.elementAt(j)).frequency) + "\n");
			}
			out.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * removes the punctuation marks from the line parameter and returns the clean line.
	 */
	private String removePunctuations(String line){
		char c;
		for (int i=0;i<line.length();i++){
			c = line.charAt(i);
			if((c==',')||(c=='.')||(c==';')||(c==':')||(c=='!')||(c=='?')||(c=='`')||(c=='\'')||(c=='"')||
					(c=='\\')||(c=='/')||(c=='(')||(c==')')||(c=='[')||(c==']')||(c=='<')||(c=='>')
					||(c=='}')||(c=='{')||(c=='|')||(c=='&')||(c=='+')||(c=='=')||(c=='-')||(c=='#')||(c=='%'))
				line = line.substring(0,i)+" " + line.substring(i+1);
		}
		return line.trim();
		
	}
	
	/*
	 * Stores the input line in the wordlist vector or updates the count
	 * if the word already exists in the vector.
	 */
	private void store(String cleanLine){
		StringTokenizer st  = new StringTokenizer(cleanLine);
		String word = null;
		int i;
		WordFreq temp;
		boolean found;
		while(st.hasMoreTokens()){
			word = st.nextToken().toLowerCase().trim();
			found=false;
			for(int j=0;j<wordlist.size();j++){
				temp = (WordFreq)(wordlist.elementAt(j));
				if(temp.word.equals(word)){
					i = temp.frequency;
					wordlist.removeElementAt(j);
					wordlist.addElement(new WordFreq(word,++i));
					found=true;
					j=wordlist.size(); //will terminate the loop
				}
			}
			if(!found) wordlist.addElement(new WordFreq(word,1));
		}
	}
	
	/** sorts the words vector according to frequency*/
	private void sort(Vector v, int left, int right){
		
		if(left>=right) return;
		int temp = ((WordFreq)v.elementAt(right)).frequency;
		int i = left - 1;
		int j = right;
			while(true) {
				while(((WordFreq)v.elementAt(++i)).frequency >((WordFreq)v.elementAt(right)).frequency);
				while(j > 0)
					if(((WordFreq)v.elementAt(--j)).frequency >=((WordFreq)v.elementAt(right)).frequency)
						break;
				if(i >= j) break;
				swap(v,i,j);
			}
			swap(v,i,right);
			sort(v,left, i-1);
			sort(v,i+1, right);
	}
	  
	  
	  private void swap(Vector v, int a, int b) {
	    Object tmp = v.elementAt(a);
	    v.setElementAt(v.elementAt(b),a);
	    v.setElementAt(tmp, b);
	  }

	
	
	private void removeStopWords(){
		String word;
		WordFreq temp;
		for (int i=0;i<stoplist.size();i++){
			word = (String)stoplist.elementAt(i);
			for(int j=0;j<wordlist.size();j++){
				temp = ((WordFreq)wordlist.elementAt(j));
				if ((temp.word.trim()).equals(word.trim())){
					wordlist.removeElementAt(j);
					j=wordlist.size();
				}
			}
			
		}
	}
	
	private GoogleSearchResultElement[] getGoogle(String query){
		try{
			GoogleSearch s = new GoogleSearch();
		    s.setKey("1k5kJwtQFHK8GfcJs6hI40N3M6MTxEpt");
			s.setQueryString(query);
	        GoogleSearchResult r = s.doSearch();
	        return r.getResultElements();
	        
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	private BufferedReader getYahoo(String address){
		try{
			URL url = new URL("http://search.yahoo.com/search?ei=UTF-8&fr=sfp&p=" + address.trim());
			return new BufferedReader(new InputStreamReader(url.openStream()));
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	private BufferedReader getDogPile(String address){
		try{
			URL url = new URL("http://www.dogpile.com/info.dogpl/search/web/" + address.trim());
			return new BufferedReader(new InputStreamReader(url.openStream()));
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	//returns true is the string is a number
	private boolean isNumber(String s){
		for(int i=0;i<s.length();i++){
			if(!(Character.isDigit(s.charAt(i)))){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * stores a stoplist read from a file in the stoplist vector. 
	 */
	private void storeStopList(){
		try{
			BufferedReader in = new BufferedReader(new FileReader(new File(STOPLIST_FILE)));
			stoplist = new Vector(100);
			String word = null;
			while ((word = in.readLine()) != null){
				stoplist.add(word.trim());
			}
			in.close();
			stoplist.trimToSize();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns a BufferedReader containing the html of a page with the specified address. 
	 * @param address of the website
	 * @return the buffered reader containing the source or null if connection was not made
	 */
	public BufferedReader getWebsite(String address){
		try{
			URL url = new URL("http://" + address);
			return new BufferedReader(new InputStreamReader(url.openStream()));
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * return the host name given the url string (assumes no http://...)
	 */
	private String parseURL(String url){
		int i = url.indexOf('\\');
		if (i!=-1) url = url.substring(0,i);
		i=url.indexOf('.');
		if((url.substring(0,i)).startsWith("www")){
			url = url.substring(i+1);
		}
		i=url.lastIndexOf('.');
		if(i!=-1) url=url.substring(0,i);
		//replace every '.' with " "
		i=url.indexOf('.');
		while(i!=-1){
			url=url.substring(0,i) + " " + url.substring(i+1);
			i=url.indexOf('.');
		}
		
		return url;
	}
	
	private class WordFreq{
		String word;
		int frequency;
		
		public WordFreq(String word, int freq){
			this.word = word;
			this.frequency = freq;
		}
		
	}
}
