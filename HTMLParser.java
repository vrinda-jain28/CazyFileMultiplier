package genBank;

import java.io.File;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.PrintWriter;



//class name
public class HTMLParser {
	public static void main(String[] args)throws Exception {
		String fileName = "/Users/vrinnie/Desktop/output/";
		PrintWriter printWriter = null; 
				//new PrinterWriter(new File("/Users/vrinnie/Desktop/output/genbank.csv")); 
		//constructor
		HTMLParser htmlParser = new HTMLParser();
		
		//try and catch is used so that we can see where the errors in our codes are

		try {
			//Insert GH number
			String GH = "28";
			
			String homeUrl  = "http://www.cazy.org/GH"+GH+"_all.html";
			//connects to the website page 1
			Document doc = Jsoup.connect(homeUrl).get();
			
			
			List<String>genBankList = new ArrayList<String>();
			
			Element table = doc.body().getElementById("pos_onglet");
	//		System.out.println("TABLE "+table.id());
			Elements rows = table.select("tr");
	//		System.out.println("Rows"+rows.size());
	//keeps track of # of rows
			int cnt =0;
			printWriter = new PrintWriter(new File(fileName + "page1.csv")); 
			for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
			    Element row = rows.get(i); //iterates all rows but there are some exceptions
			    Elements cols = row.select("td");
			    //we put this condition to make sure no others tables are involved
				if(cols.size()  == 7 ) {
					Element genBankData = cols.get(3); //only looking at 3rd col
					//ignore everytime it displays GenBank
					String[] valuesArray = genBankData.text().split(" ");
					//System.out.println("Array" +valuesArray.length);
					genBankList.add(valuesArray[0]);
					if((!genBankData.text().equalsIgnoreCase("GenBank"))&&(genBankData.text().equalsIgnoreCase(valuesArray[0]))&&(!genBankData.text().equalsIgnoreCase("\n"))) {
						//since there are multiple elements in a row, we store it into one array
						 //only want the 1st value
						printWriter.write(genBankData.text()+"\n");
						System.out.println(""+genBankData.text()); //prints all values when home Url is called
						cnt++; //to see total rows
					}
				}
			}
			printWriter.close();
			//System.out.println("Total"+cnt++);
			
			Elements pageClassElements = doc.body().getElementsByClass("lien_pagination");
			//System.out.println("pageClassElements.size(): "+pageClassElements.size());
			
			int totalPages = 0;
			//to get number of pages
			for(int i=0; i<pageClassElements.size(); i++) {
				Element sepClass = pageClassElements.get(i);
				String linkText = sepClass.text(); // "e
				if(!linkText.equalsIgnoreCase(">")) {
					totalPages = Integer.parseInt(linkText);
				}
			}

			//System.out.println("totalPages: "+totalPages);
			for(int i=0; i<totalPages; i++) {

				//to get url for other pages
				if(i!=(totalPages-1)) {
					String url = homeUrl+"?debut_PRINC="+(i+1)+"000#pagination_PRINC";
					//System.out.println("URL: "+url);
					Element dataTable = htmlParser.getContents(url);//call method to use url
					genBankList = htmlParser.addContents(fileName+"page"+(i+2),dataTable,genBankList); //adds new elements to list
//				
					}
			}
			
			
			for(int i=0; i<genBankList.size(); i++) {
				if(!(genBankList.get(i) == genBankList.get(i))) {
				System.out.println(genBankList.get(i));
				}
			}
			System.out.println("Total elements: "+genBankList.size());

		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}/*{
	try {
		//Blank workbook
	XSSFWorkbook workbook = new XSSFWorkbook(); 
	FileOutputStream out = new FileOutputStream(new File("d:/demo.xlsx"));
	workbook.write(out);
	out.close();
	
	}
	catch(Exception e){
		System.out.println(e);
		
	}
	
	System.out.println("Excel is created");
	}*/
			 /*
        //Create a blank sheet
        HTMLParser sheet = workbook.createSheet("Employee Data");
       
          
        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] {"ID", "NAME", "LASTNAME"});
        data.put("2", new Object[] {1, "Amit", "Shukla"});
        data.put("3", new Object[] {2, "Lokesh", "Gupta"});
        data.put("4", new Object[] {3, "John", "Adwards"});
        data.put("5", new Object[] {4, "Brian", "Schultz"});
          
        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("howtodoinjava_demo.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
	}
	
	*/
	public Element getContents(String url) throws Exception{
		Document doc = Jsoup.connect(url).get();
		Element table = doc.body().getElementById("pos_onglet");
		return table;
		
	}
	
	public List<String> addContents(String filename, Element dataTable,List<String> genBankList) throws Exception{
		Elements rows = dataTable.select("tr");
		//		System.out.println("Rows"+rows.size());
		PrintWriter printWriter  = new PrintWriter(new File(filename + ".csv"));
		
				int cnt =0;
				for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
				    Element row = rows.get(i);
				    Elements cols = row.select("td");
					if(cols.size()  == 7 ) {
						Element genBankData = cols.get(3);
						String[] valuesArray = genBankData.text().split(" ");
						genBankList.add(valuesArray[0]);
						//System.out.println("Array: " +valuesArray.length);
						if((!genBankData.text().equalsIgnoreCase("GenBank"))&&(genBankData.text().equalsIgnoreCase(valuesArray[0]))&&(!genBankData.text().equalsIgnoreCase("\n"))) {
							
							printWriter.write(genBankData.text()+"\n");
							System.out.println(""+genBankData.text());
							cnt++;
						}
					}
				}
				printWriter.close();
				return genBankList;
		
	}
	


}
