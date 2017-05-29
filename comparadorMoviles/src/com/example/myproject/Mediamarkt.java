package com.example.myproject;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.example.myproject.Mobile;

public class Mediamarkt {
	
	//public String url_inicio = "https://tiendas.mediamarkt.es/smartphones-libres?q=";
	//public String url_final = "&filters=%7B!tag%3DrootFilter%7DrootCategories_facet:M%C3%B3viles%5C%20y%5C%20smartphones&page=1&top=0";
	public String url = "https://tiendas.mediamarkt.es/smartphones-libres/m/";
	public final static int REQUEST_DELAY = 3000;
	private List<Mobile> res;
	
	//constructor
	public Mediamarkt(){
		res=new ArrayList<Mobile>();
	}

    public static String cambiarurl( String url){
        url = url.replace(' ','+');
        return url;
    }
	
	
	/**
	 * Con esta método compruebo el Status code de la respuesta que recibo al hacer la petición
	 * EJM:
	 * 		200 OK			300 Multiple Choices
	 * 		301 Moved Permanently	305 Use Proxy
	 * 		400 Bad Request		403 Forbidden
	 * 		404 Not Found		500 Internal Server Error
	 * 		502 Bad Gateway		503 Service Unavailable
	 * @param url
	 * @return Status Code
	 */
	public static int getStatusConnectionCode(String url) {
			
	    Response response = null;
		
	    try {
		response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
	    } catch (IOException ex) {
		System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
	    }
	    return response.statusCode();
	}
	
	
	/**
	 * Con este método devuelvo un objeto de la clase Document con el contenido del
	 * HTML de la web que me permitirá parsearlo con los métodos de la librelia JSoup
	 * @param url
	 * @return Documento con el HTML
	 */
	public static Document getHtmlDocument(String url) {

	    Document doc = null;
		try {
		    doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
		    } catch (IOException ex) {
			System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
		    }
	    return doc;
	}
	
	String obtenerPrice(String precio){
		
		String[] arrayColores = precio.split(",");
		String resultado = arrayColores[1];
		// En este momento tenemos un array en el que cada elemento es un color.
		arrayColores = resultado.split(":");
		

		return arrayColores[1];
	}
	
	String obtenerId(String id){
		String[] arrayColores = id.split(",");
		String resultado = arrayColores[3];
		// En este momento tenemos un array en el que cada elemento es un color.
		arrayColores = resultado.split(":");
		

		return arrayColores[1];
	}
	
	public void ordenarPorPrecio(List<Mobile> moviles){

		int i, j;
		Mobile aux;
	         for(i=0;i<moviles.size()-1;i++)
	              for(j=0;j<moviles.size()-i-1;j++)
	                   if(moviles.get(j+1).getPrice()<moviles.get(j).getPrice()){
	                      aux=moviles.get(j+1);
	                      moviles.set(j+1, moviles.get(j));
	                      moviles.set(j, aux);
	                   }
		
	}
	
	//Como ejemplo de uso sería run(Apple) o run(sony)
    public List<Mobile> run(String tag) throws Exception {
    	//realizamos la consulta
    	//url=url_inicio.concat(tag);
    	//url=url.concat(url_final);
    	url = url.concat(tag);
    	
    	System.out.println("Url de la consulta es "+url+"\n");
    	
       // Compruebo si me da un 200 al hacer la petición
       if (getStatusConnectionCode(url) == 200) {
		
           // Obtengo el HTML de la web en un objeto Document2
           Document document = getHtmlDocument(url);
		
           // Busco todas las historias de meneame que estan dentro de: 
           Elements entradas = document.getElementsByClass("product product10");
           System.out.println("Hay "+entradas.size()+ " entradas con Mediamarkt");

           // Paseo cada una de las entradas
           for (Element elem : entradas) {
               String id = elem.attr("data-gtmProduct").toString(); 
               String name = elem.getElementsByTag("img").attr("alt").toString();
                String price = elem.attr("data-gtmProduct").toString();              
               String URLImage = elem.getElementsByTag("img").attr("src").toString();
               String URLProduct = "https://tiendas.mediamarkt.es" + elem.getElementsByTag("a").attr("href").toString();
               
               price = obtenerPrice(price);
               id = obtenerId(id);
               
               System.out.println("el Id es "+id+"\n");               
               System.out.println("el Titulo es "+name+"\n");
               System.out.println("el Precio es "+price+"\n");
               System.out.println("la URL es "+URLProduct +"\n");
               System.out.println("la URLImag es "+URLImage+"\n");
               
               Mobile movil=new Mobile();
               double precio = Double.parseDouble(price);
                              
               movil.set(id,name, precio, URLImage,URLProduct,"Mediamarkt");
               res.add(movil);
           }
           

       }else{
           System.out.println("Code error = : "+getStatusConnectionCode(url));              
       }
        //process xml dump returned from EBAY
        
        //Honor rate limits - wait between results
        Thread.sleep(REQUEST_DELAY);
        
        ordenarPorPrecio(res);
        
        return res;
    }	
	
	
}
