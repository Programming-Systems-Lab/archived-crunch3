/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 */
package psl.crunch3.util;
import com.google.soap.search.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.axis.*;

/**
 * @author hb2143
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WordCount extends JFrame{

	private boolean useWordList;
	private int engineNum;
	private String STOPLIST_FILE = "frequency" + File.separator + "stoplist1.txt";
	private final String SITES_FILE = "frequency" + File.separator + "sites.txt";
	private final String SITE_LINKS_FILE = "frequency" + File.separator + "links.txt";
	private Vector stoplist;
	private Vector wordlist;
	private Vector sitewords;
	private Vector keywords; 
	private Vector siteNames; //stores the names of the sites
	private Hashtable dictwords;
	private Container cp;
	private JPanel chartPanel;
	private int[][] frequencies;
	private Vector distances;
	private Vector clusters;
	public static void main(String[] args) {
		//if ((args[0] != null) && (args[1] != null))
			WordCount wc = new WordCount(args[0], args[1]);
			wc.setVisible(true);
		
	}
	
	public WordCount(String num, String words){
		
		cp = this.getContentPane();
		chartPanel = new JPanel();
		
		ScrollPane sp = new ScrollPane();
		
		this.setSize(800,500);
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
				generateList(site, true);
			}
			inSites.close();
			
			chartPanel.setLayout(new GridLayout(siteNames.size(),1));
			frequencies = new int[siteNames.size()][keywords.size()];
			distances = new Vector();
			for (int i=0;i<siteNames.size();i++){
				compare(i);
			}
			
			findClusters();
			
			sp.add(chartPanel);
			cp.add(sp);
		}
		catch(Exception e){
			e.printStackTrace();
		}	
	}
	/**
	 * Generates a lists of words and their corresponding frequencies and writes them to a file.
	 * @param url the address of the website
	 */
	private void generateList(String url, boolean isRoot){
		System.out.println("generating word list for " + url);
		BufferedReader in = getWebsite(url);
		InputStreamReader read;
		try{
			storeBuffer(in);
			in.close();
			//if(isRoot) generateLinks(getWebsite(url),url);
			//store words from google search.
			System.out.println("storing google search results...");
			GoogleSearchResultElement[] re = getGoogle(parseURL(url,true));
			
			for(int k=0;k<re.length;k++){
				store(removePunctuations(re[k].getSnippet()+" "+re[k].getTitle()+ " " +re[k].getSummary()));
			}
			
			
			
			//store words from yahoo search
			System.out.println("storing yahoo search results");
			in = getYahoo(parseURL(url,true));
			storeBuffer(in);
			
			in.close();
			
			//store words from dogpile search
			System.out.println("storing dogpile search results");
			in = getDogPile(parseURL(url,true));
			storeBuffer(in);
			
			in.close();
			
			//store words from msn search
			System.out.println("storing msn search results");
			in = getMSN(parseURL(url,true));
			storeBuffer(in);
			in.close();
			
			//store words from altavista search
			System.out.println("storing altavista search results");
			in = getAltaVista(parseURL(url,true));
			storeBuffer(in);
			in.close();
			
			/*System.out.println("storing  search results");
			in = getLycos(parseURL(url,true));
			storeBuffer(in);
			in.close();*/
			
			System.out.println("storing excite search results");
			in = getExcite(parseURL(url,true));
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
				if ((temp.word).length()<3||isNumber(temp.word)||(temp.word).equals(parseURL(url,true))) {
					wordlist.removeElementAt(j);
					--j;
				}	
			}
			
			
			wordlist.trimToSize();
			
			System.out.println("sorting...");
			sort(wordlist,0,wordlist.size()-1);
			
			siteNames.addElement(parseURL(url,isRoot));
			sitewords.addElement(wordlist);
			
			
			//add words of frequency 5 and above to keywords vector
			for (int j=0;j<wordlist.size();j++){
				if((((WordFreq)wordlist.elementAt(j)).frequency > 9) && 
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
		    //s.setKey("1k5kJwtQFHK8GfcJs6hI40N3M6MTxEpt");
			//s.setKey("ICXEmVhQFHLkCpwMjwWO6Ev9yYdyuMvA");
			s.setKey("xnDUeklQFHJeHhwWNLtXeDSfh0zIPfGf");
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
	
	private BufferedReader getMSN(String address){
		try{
			URL url = new URL("http://search.msn.com/results.aspx?q=" + address.trim());
			return new BufferedReader(new InputStreamReader(url.openStream()));
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}	
	private BufferedReader getAltaVista(String address){
		try{
			URL url = new URL("http://www.altavista.com/web/results?q=" + address.trim());
			return new BufferedReader(new InputStreamReader(url.openStream()));
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	private BufferedReader getLycos(String address){ 
		  
		try{ 
		 URL url = new URL("http://search.lycos.com/default.asp?query=" + address.trim()); 
		 return new BufferedReader(new InputStreamReader(url.openStream())); 
		} 
		  
		catch(Exception e){ 
		 e.printStackTrace(); 
		 return null; 
		} 
		 
	} 
		private BufferedReader getExcite(String address){ 
		  
		try{ 
		 URL url = new URL("http://msxml.excite.com/info.xcite/search/web/" + address.trim()); 
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
	
	private void compare(int index){
		try{
			Vector a = ((Vector)(sitewords.elementAt(index)));
			String site = ((String)(siteNames.elementAt(index)));
			DataOutputStream out = new DataOutputStream(new FileOutputStream("frequency" + File.separator+ site +".txt"));
			XYSeries series = new XYSeries(site,false,false);
			boolean found;
			
			for (int j=0;j<keywords.size();j++){
				found = false;
				for(int i=0;i<a.size();i++){
					if((((WordFreq)(a.elementAt(i))).word).equals((String)keywords.elementAt(j))){
						out.writeBytes(((String)keywords.elementAt(j))+ "\t" + ((WordFreq)(a.elementAt(i))).frequency + "\n");
						frequencies[index][j] = ((WordFreq)(a.elementAt(i))).frequency;
						series.add(j,((WordFreq)(a.elementAt(i))).frequency);
						found = true;
					}
				}
				if(!found){
					out.writeBytes(((String)keywords.elementAt(j))+ "\t" + 0 + "\n");
					
					series.add(j,0);
				}
				
			}
			out.close();
			
			//create the graph
			
		    
			DefaultTableXYDataset xyds = new DefaultTableXYDataset();
            xyds.addSeries(series);
			
            JFreeChart chart = ChartFactory.createXYBarChart
            (site, "x", false,"y",xyds, PlotOrientation.VERTICAL, true,true,true);
            
           
            XYPlot plot = chart.getXYPlot();
            ValueAxis yAxis = plot.getRangeAxis();
            yAxis.setRange(0,50);
            
            
            BufferedImage image = chart.createBufferedImage(500,300);
            ChartUtilities.saveChartAsJPEG(new File("frequency" + File.separator+ site +".jpg"), chart, 500, 300);
            JLabel lblChart = new JLabel();
            lblChart.setIcon(new ImageIcon(image));
            chartPanel.add(lblChart);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/*
	 * return the host name given the url string (assumes no http://...)
	 */
	private String parseURL(String url, boolean isRoot){
		int i = url.indexOf('\\');
		String first=url;
		String second = "";
		if (i!=-1) first = url.substring(0,i);
		i=first.indexOf('.');
		if((first.substring(0,i)).startsWith("www")){
			first = first.substring(i+1);
		}
		i=first.lastIndexOf('.');
		if(i!=-1) first=first.substring(0,i);
		//replace every '.' with " "
		i=first.indexOf('.');
		while(i!=-1){
			first=first.substring(0,i) + "+" + first.substring(i+1);
			i=first.indexOf('.');
		}
		if(isRoot) return first;
		else{ 
			i=url.indexOf('\\');
			if (i!=-1) second = url.substring(i+1);
			i=second.indexOf('\\');
			if(i!=-1) second = second.substring(0,i);
			return (first + "_" + second);
		}
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
					generateList(url + line.substring(0,line.indexOf("\"")),false);
					//System.out.println(url + line.substring(0,line.indexOf("\"")));
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
	
	private void findClusters(){
		System.out.println(siteNames.size());
		for(int i=0;i<siteNames.size();i++){
			for(int j=i+1;j<siteNames.size();j++){
				distances.addElement(new Distance((String)siteNames.elementAt(i), 
						(String)siteNames.elementAt(j), getDistance(frequencies[i],frequencies[j])));
				System.out.println("the distance between " + (String)siteNames.elementAt(i)+ " and " +
						(String)siteNames.elementAt(j)+ "  is  " + getDistance(frequencies[i],frequencies[j]));
			}
		}
		
		System.out.println("sorting distances... ");
		
		sortDistance(distances,0, distances.size()-1);
		
		for(int i=0;i<distances.size();i++){
			System.out.println("The distance between " + ((Distance)(distances.elementAt(i))).site1 + " and " + 
					((Distance)(distances.elementAt(i))).site2 + " is " + ((Distance)(distances.elementAt(i))).distance);
		}
		

		clusters = new Vector();
		
		//add the sites with the smallest distance to an initial cluster
		Vector cluster1 = new Vector();
		cluster1.addElement(new ClusterNode(((Distance)distances.elementAt(distances.size()-1)).site1,0, "", 0,"1"));
		cluster1.addElement(new ClusterNode(((Distance)distances.elementAt(distances.size()-1)).site2,0, "", 0,"1"));
		clusters.addElement(cluster1);
		
		int levelCounter = 0;
		Vector temp; 
		Distance dist;
		int threshold = 300;
		int currentThreshold = 300;
		boolean found1 = false;
		boolean found2 = false;
		int index2=0;
		Vector cluster2 = null;
		int l1 = -1;
		int l2 = -1;
		int clustertag = 1;
		int n1 = -1;
		int n2 =-1;
		for (int j=distances.size()-2;j>0;--j){
			found1 = false;
			found2 = false;
			cluster1 = null;
			cluster2 = null;
			l1=-1;
			l2=-1;
			if(((Distance)distances.elementAt(j)).distance <= currentThreshold){
				//get the next pair of sites with the shortest distance
				dist = (Distance)distances.elementAt(j);
				//check if either one of the sites is already in a cluster
				//for now an element can only belong to one cluster
				for(int i=0;i<clusters.size();i++){
					temp = (Vector)clusters.elementAt(i);
					for(int n=0;n<temp.size();n++){
						if ((((ClusterNode)temp.elementAt(n)).site).equals(dist.site1)){
							
								cluster1 = (Vector)clusters.elementAt(i);
								n1 = n;
								found1 = true;
								l1 = ((ClusterNode)temp.elementAt(n)).level;
							
						
						}
						if ((((ClusterNode)temp.elementAt(n)).site).equals(dist.site2)){	
							
								cluster2 = (Vector)clusters.elementAt(i);
								n2 = n;
								found2 = true;
								l2 = ((ClusterNode)temp.elementAt(n)).level;
							
						}
					}	
				}
				if(!found1 && !found2){
					//create a new cluster
					temp = new Vector();
					clustertag++;
					Integer i =  new Integer(clustertag);
					temp.addElement(new ClusterNode(dist.site1,0, "" , 0, i.toString()));
					temp.addElement(new ClusterNode(dist.site2,0, "" , 0, i.toString()));
					clusters.addElement(temp);
				}
				else if(found1 && !found2){//add to the first cluster
					if(l1 < 2){
						l1++;
						cluster1.addElement(new ClusterNode(dist.site2,l1, dist.site1,
								dist.distance, ((ClusterNode)cluster1.elementAt(n1)).getCurrentTag()));
					}
				}
				else if(found2 && !found1){ //add to the second cluster
					if(l2 < 2){
						l2++;
						cluster2.addElement(new ClusterNode(dist.site1,l2, dist.site2,
								dist.distance, ((ClusterNode)cluster2.elementAt(n2)).getCurrentTag()));
					}
				}
				else {
					//join the 2 clusters
					/**if(!(cluster1.equals(cluster2))){
						clustertag++;
						Integer i = new Integer(clustertag);
						
						for(int l=0;l<cluster1.size();l++){
							((ClusterNode)cluster1.elementAt(l)).addTag(i.toString());
						}
						for(int m=0; m < cluster2.size(); m++){
							
							cluster1.addElement(cluster2.elementAt(m));
							
							((ClusterNode)cluster2.elementAt(m)).addTag(i.toString());
						}
						
						clusters.removeElement(cluster2);
						
					}**/
				}
			}
			else{
				levelCounter++;
				currentThreshold = threshold + (50*levelCounter);
				++j;
				
				
			}
			if(currentThreshold > 1000) break;
		}
		
		System.out.println("The clusters are ...  ");
		ClusterNode cn = null;
		try{
			DataOutputStream out = new DataOutputStream(new FileOutputStream("frequency" + File.separator + "resutls.txt"));
			for(int i=0;i<clusters.size();i++){
				temp = (Vector)clusters.elementAt(i);
				System.out.print("The elements in cluster #" + (i+1) + " are: ");
				out.writeBytes("The elements in cluster #" + (i+1) + " are: \n");
				for(int j=0;j<temp.size();j++){
					cn = (ClusterNode)temp.elementAt(j);
					System.out.println(cn.site + "\t" + cn.level +
						"\t" + cn.pulled + "\t" + cn.distance);
					out.writeBytes(cn.site + "\t" + cn.level +
							"\t" + cn.pulled + "\t" + cn.distance + "\n");
				
				}
				out.writeBytes("\n");
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param site
	 */
	private void remove(String site){
		for(int i=0;i<distances.size();i++){
			if ((((Distance)distances.elementAt(1)).site1).equals(site) ||
					(((Distance)distances.elementAt(1)).site2).equals(site))
				distances.removeElementAt(i);
		}
	}
	
	private void sortDistance(Vector v, int left, int right){
		
		if(left>=right) return;
		int temp = ((Distance)v.elementAt(right)).distance;
		int i = left - 1;
		int j = right;
			while(true) {
				while(((Distance)v.elementAt(++i)).distance >((Distance)v.elementAt(right)).distance);
				while(j > 0)
					if(((Distance)v.elementAt(--j)).distance >=((Distance)v.elementAt(right)).distance)
						break;
				if(i >= j) break;
				swap(v,i,j);
			}
			swap(v,i,right);
			sortDistance(v,left, i-1);
			sortDistance(v,i+1, right);
	}
	
	/**
	 * calculates the distance between 
	 * @param a
	 */
	private int getDistance(int[] a, int[] b){
		if(a.length != b.length)
		return 0;
		int distance =0;
		for(int i=0;i<a.length;i++){
			if((a[i]-b[i])>0){
				distance += (a[i]-b[i]);
			}
			else distance += (b[i]-a[i]);
		}
		return distance;
		
	}
	
	private class WordFreq{
		String word;
		int frequency;
		
		public WordFreq(String word, int freq){
			this.word = word;
			this.frequency = freq;
		}
		
	}

	
	private class Distance{
		int distance;
		String site1;
		String site2;
		
		public Distance(String site1, String site2, int distance){
			this.site1 = site1;
			this.site2 = site2;
			this.distance = distance;
		}
		
	}
	
	private class ClusterNode{
		int level;
		String site;
		String pulled;
		int distance;
		Vector clustertags;
		
		public ClusterNode(String site, int level, String pulled, int distance, String tag){
			this.site = site;
			this.level = level;
			this.pulled = pulled;
			this.distance = distance;
			clustertags = new Vector();
			clustertags.addElement(tag);
		}
		
		
		public void addTag(String tag){
			clustertags.addElement(tag);
		}
		
		public String getCurrentTag(){
			clustertags.trimToSize();
			String s = (String)(clustertags.elementAt(clustertags.size()-1));
			return s;
		}
		
		public String printTags(){
			String s="";
			for(int i=0; i<clustertags.size();i++){
				s = s + " , " +(String)clustertags.elementAt(i);
			}
			return s;
		}
	}
	
	
}


