package genBank;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser {
	public static void main(String[] args) {
		//HTMLParser ws = new HTMLParser();
		
		//try and catch is used so that we can see where the errors in our codes are
//for (int page=1; page<8; page++) {
	// ws.setUrl("http://www.cazy.org/GH29_all.html" + String.valueOf(page) + "/");
		try {
			//connects to the website but we need it to connect to all pages of the website
			String url  = "http://www.cazy.org/GH29_all.html";
			Document doc = Jsoup.connect(url).get();
			
		
			
			Element table = doc.body().getElementById("pos_onglet");
	//		System.out.println("TABLE "+table.id());
			Elements rows = table.select("tr");
	//		System.out.println("Rows"+rows.size());
	
			int cnt =0;
			for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
			    Element row = rows.get(i);
			    Elements cols = row.select("td");
				if(cols.size()  == 7 ) {
					Element genBankData = cols.get(3);
					if(!genBankData.text().equalsIgnoreCase("GenBank")) {
						System.out.println(""+genBankData.text());
						cnt++;
					}
				}
			}
			System.out.println("Total"+cnt++);

		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}
	
	
	public Element getContents(String url) throws Exception{
		Document doc = Jsoup.connect(url).get();
		Element table = doc.body().getElementById("pos_onglet");
		return table;
		
	}


}
