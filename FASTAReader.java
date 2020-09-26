package genBank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FASTAReader {
	
	
	public static void main (String[] args)throws Exception{
		BufferedReader br = null;
		PrintWriter printWriter = null;
		String fileName = "/Users/vrinnie/Desktop/output/";

	try {
		String fastaFile = "/Users/vrinnie/Downloads/sequence.fasta";
		
		String line = "";
		br = new BufferedReader(new FileReader(fastaFile));
	int counter = 0;
	//System.out.println("Index: "+line.indexOf(""));
	while ((line = br.readLine()) != null) {
		if(line.indexOf(">") != -1 && line.indexOf(">") == 0 ) {
			//String name = line.valueOf(line.charAt(0));
			if(printWriter != null) {
				printWriter.close();
			}
			printWriter = new PrintWriter(new File(fileName + (counter+1)+".fasta")); 
			//printWriter.write(fastaFile.valueOf(line.startsWith("["))+"\n");
			counter++;
			
			//printWriter.write(line);
		}
		
		printWriter.write(line);
		
	}
	//printWriter.close();
	}catch(Exception ex) {
		ex.printStackTrace();
	}
	 finally {
		 if (br != null) {
		 try {
		 br.close();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 }

}
	}
}
