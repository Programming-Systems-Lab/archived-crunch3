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
	private String STOPLIST_FILE = "frequency" + File.separator + "stoplist1.txt";
	private final String SITES_FILE = "frequency" + File.separator + "sites.txt";
	private final String SITE_LINKS_FILE = "frequency" + File.separator + "links.txt";
	private Vector stoplist;
	private Vector wordlist;
	private Vector sitewords;
	private Vector keywords;
	private Vector siteNames;
	private Hashtable dictwords;
	
	public static void main(String[] args) {
		//if ((args[0] != null) && (args[1] != null))
			new WordCount(args[0], args[1]);
		//else System.out.println("please provide arguments for the number of engines (1,2,3)" 
			//	+ "and whether you want to use the words file or not (yes/no)");
	}
	
	public WordCount(String num, String words){
		if (words.equals("yes")) {
			useWordList = true;
			storeDictwords();
		}
		else useWordList = false;
		engineNum = Integer.parseInt(num);
		//generating the stoplist...
		storeStopList();
		try{
			//read site list from file
			System.out.println("reading the site list...");
			BufferedReader inSites = new BufferedReader(new FileReader(new File(SITES_FILE)));
			String site = null;
			keywords = new Vector();
			sitewords = new Vector();
			siteNames = new Vector();
			while((site = inSites.readLine()) != null){
				wordlist = new Vector();
				generateList(site);
			}
			inSites.close();
			
			
			for (int i=0;i<siteNames.size();i++){
				compare(((Vector)(sitewords.elementAt(i))), keywords, ((String)(siteNames.elementAt(i))));
			}
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
		System.out.println("generating word list for " + url);
		BufferedReader in = getWebsite(url);
		InputStreamReader read;
		try{
			storeBuffer(in);
			in.close();
			generateLinks(getWebsite(url),url);
			//store words from google search.
			System.out.println("storing google search results...");
			GoogleSearchResultElement[] re = getGoogle(parseURL(url));
			
			for(int k=0;k<re.length;k++){
				store(removePunctuations(re[k].getSnippet()+" "+re[k].getTitle()+ " " +re[k].getSummary()));
			}
			
			
			
			//store words from yahoo search
			System.out.println("storing yahoo search results");
			in = getYahoo(parseURL(url));
			storeBuffer(in);
			
			in.close();
			
			//store words from dogpile search
			System.out.println("storing dogpile search results");
			in = getDogPile(parseURL(url));
			storeBuffer(in);
			
			in.close();
			
			
			System.out.println("removing stop words...");
			removeStopWords();
			if(useWordList){
				System.out.println("removing words not in dictwords file");
				removeNotDictwords();
			}
			
			WordFreq temp; int i;
			
			//delete words with less than 3 letters, delete numbers
			for(int j=0;j<wordlist.size();j++){
				temp = (WordFreq)(wordlist.elementAt(j));
				if ((temp.word).length()<3||isNumber(temp.word)||(temp.word).equals(parseURL(url))) {
					wordlist.removeElementAt(j);
					--j;
				}	
			}
			
			
			wordlist.trimToSize();
			
			System.out.println("sorting...");
			sort(wordlist,0,wordlist.size()-1);
			
			siteNames.addElement(parseURL(url));
			sitewords.addElement(wordlist);
			
			
			//add words of frequency 5 and above to keywords vector
			for (int j=0;j<wordlist.size();j++){
				if((((WordFreq)wordlist.elementAt(j)).frequency > 4) && 
						!(inVector(keywords, ((WordFreq)wordlist.elementAt(j)).word))){
					
					keywords.add(((WordFreq)wordlist.elementAt(j)).word);	
					
				}
			}
			
			
			
			//write the data from wordlist to a file.
			/*DataOutputStream out = new DataOutputStream(new FileOutputStream("frequency" + File.separator+ parseURL(url)+".txt"));
			for (int j=0;j<wordlist.size();j++){
				out.writeBytes((((WordFreq)wordlist.elementAt(j)).word)+ "\t" + 
						(((WordFreq)wordlist.elementAt(j)).frequency) + "\n");
			}
			out.close();*/
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private String cleanLine(String s){
		if((s.indexOf('<')==(-1))&&((s.indexOf('<')==(-1)))) return s;
		else{
			int i=s.indexOf('<');
			int j=s.indexOf('>');
			
			if(j==-1)return s.substring(0,i);
			else if(i==-1) return cleanLine(s.substring(j+1));
			else if (i<j) return cleanLine(s.substring(0,i)+ " " + s.substring(j+1));
			else{
				//System.out.println("parsing error");
				return s.substring(0,j);
			}
		}
	}
	
	
	
	private void storeBuffer(BufferedReader in){
		try{
			String line = null;
			String leftover="";
			boolean bodyFound = false;
			while((line = in.readLine()) != null){
				
				if(!bodyFound){
					//try to extract information from the title
					if(line.indexOf("<title>") != -1){
						line = line.substring(line.indexOf("<title>")+7);
						if(line.indexOf("</")!= -1) {
							line = line.substring(0,line.indexOf("</"));
							store(removePunctuations(line));
						}
					}
					
					//check if line contains body tag
					if(line.toLowerCase().indexOf("<body") != -1){
						store(removePunctuations(cleanLine(line)));
						bodyFound=true;
					}
						
					//otherwise skip line...
					
				}
				else{
					
					leftover = cleanLine(line);
					store(removePunctuations(leftover));
				}
			}
			in.close();
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
					||(c=='}')||(c=='{')||(c=='|')||(c=='&')||(c=='+')||(c=='=')||(c=='-')||(c=='#')||(c=='%')
					||(c=='$')||(c=='_'))
				line = line.substring(0,i)+" " + line.substring(i+1);
		}
		return line.trim();
		
	}
	
	//returns true if the word is in the vector
	private boolean inVector(Vector v, String word){
		for(int i=0;i<v.size();i++){
			if(((String)v.elementAt(i)).equals(word))
				return true;
		}
		return false;
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
	
	private void compare(Vector a, Vector b, String site){
		try{
			DataOutputStream out = new DataOutputStream(new FileOutputStream("frequency" + File.separator+ site +".txt"));
			boolean found;
			for (int j=0;j<b.size();j++){
				found = false;
				for(int i=0;i<a.size();i++){
					if((((WordFreq)(a.elementAt(i))).word).equals((String)b.elementAt(j))){
						out.writeBytes(((String)b.elementAt(j))+ "\t" + ((WordFreq)(a.elementAt(i))).frequency + "\n");
						found = true;
					}
				}
				if(!found){
					out.writeBytes(((String)b.elementAt(j))+ "\t" + 0 + "\n");
				}
				
			}
			out.close();
		}
		catch(Exception e){
			
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
			url=url.substring(0,i) + "+" + url.substring(i+1);
			i=url.indexOf('.');
		}
		
		return url;
	}
	
	private void storeDictwords(){
		try{
			BufferedReader in = new BufferedReader(new FileReader(new File("frequency" + File.separator +"allwords.txt")));
			dictwords = new Hashtable();
			String word = null;
			while ((word = in.readLine()) != null){
				dictwords.put(word.toLowerCase(), word.toLowerCase());
			}
			in.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	private void removeNotDictwords(){
		WordFreq temp;
		String s,t;
		for(int j=0;j<wordlist.size();j++){
			temp = ((WordFreq)wordlist.elementAt(j));
			
				s = (String)dictwords.get(temp.word.trim());
				if (s==null){
					//try removing the last 's'
					if ((temp.word).endsWith("s")){
						s = (String)dictwords.get((temp.word).substring(0,(temp.word).length()-1));
					}
					if (s==null){
						//word not in file-- remove it
						wordlist.removeElementAt(j);
						//System.out.println("removed " + temp.word);
						--j;
					}
				}
	
		}
	}
	
	/**
	 * writes all links in buffer to a file.
	 */
	private void generateLinks(BufferedReader in, String url){
		
		try{
			int index;
			String line = in.readLine();
			
			while(line != null){
				line.toLowerCase();
				index = line.indexOf("<a href=\"/");
				//remove everything before the link
				if(index!=-1){
					line = line.substring(index+9);
					wordlist = new Vector();
					//generateList(url+line.substring(0,line.indexOf("\"")));
					line = line.substring(line.indexOf("\"")+1);
				}
				else{
					line = in.readLine();
				}
			}
			in.close();
			
		}
		
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
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
