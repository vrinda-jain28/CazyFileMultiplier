package genBank;

import java.sql.Connection;

import javax.lang.model.util.Elements;
import javax.swing.text.Document;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;


public class MultiPage {
	public static void main(String[] args) throws Exception {

        final int OK = 200;
        String currentURL;
        int page = 1;
        int status = OK;
        Connection.Response response = null;
        Document doc = null;
        String[] keywords = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        //String keyword = "a";
        for (String keyword : keywords){
            final String url = "https://www.medindia.net/drug-price/brand-index.asp?alpha="+keyword; 
            while (status == OK) {
                currentURL = url +"&page="+ String.valueOf(page); 
                response = Jsoup.connect(currentURL)
                        .userAgent("Mozilla/5.0")
                        .execute();
                status = response.statusCode();


                if (status == OK) {
                    doc = response.parse();

                    Element table = ((Element) doc).select("table").get(1);

                    for (Element rows : table.select("tr")) {
                        for (Element tds : rows.select("td")) {
                            Elements links = (Elements) tds.select("a[href]");
                            for (Element link : links) {
                                System.out.println("link : " + link.attr("href"));
                                System.out.println("text : " + link.text());
                            }
                        }
                    }

                }
                page++;
            }

        }
    }

}
