package com.example.myproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AmazonSearch {

	public String url = "https://www.amazon.es/s/ref=nb_sb_noss_1?__mk_es_ES=ÅMÅŽÕÑ&url=search-alias%3Dmi&field-keywords=";
	public final static int REQUEST_DELAY = 3000;
	private List<Mobile> res;
	
	//constructor
	public AmazonSearch(){
		res = new ArrayList<Mobile>();
	}

    public static String cambiarurl( String url){
        url = url.replace(' ','+');
        return url;
    }
    
    public static int getStatusConnectionCode(String url) {
		
        Response response = null;
		
        try {
            response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
        }
        return response.statusCode();
    }
	
    public static Document getHtmlDocument(String url) {

        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
        }

        return doc;
    }
   
   
    public List<Mobile> run(String tag) throws Exception {
    	//realizamos la consulta
    	url=url.concat(tag);
    	System.out.println("Url de la consulta es "+url+"\n");
    	
       // Compruebo si me da un 200 al hacer la petición
       if (getStatusConnectionCode(url) == 200) {
		
           // Obtengo el HTML de la web en un objeto Document2
           Document document = getHtmlDocument(url);
		
           // Busco todas las historias de meneame que estan dentro de: 
           Elements entradas = document.select(".s-result-item");
		
           // Paseo cada una de las entradas
           for (Element elem : entradas) {
               String url = elem.getElementsByClass("a-link-normal").attr("href");
               
               String titulo = elem.select(".a-link-normal").attr("title");
               String precio = elem.select(".a-size-base.a-color-price.s-price.a-text-bold").text();
               
               String urlImag = elem.select(".a-link-normal img").attr("src");
               
               System.out.println("el titulo es"+titulo+"\n");
               System.out.println("el precio es"+precio+"\n");
               System.out.println("la url es "+url+"\n");
               System.out.println("la urlImag es "+urlImag+"\n");
               
               Mobile book=new Mobile();
               book.set("vacio", titulo, precio, url,urlImag);
               res.add(book);
           }
           

       }else{
           System.out.println("Code error = : "+getStatusConnectionCode(url));              
       }
        //process xml dump returned from EBAY
        
        //Honor rate limits - wait between results
        Thread.sleep(REQUEST_DELAY);
        return res;
    }
}
	
