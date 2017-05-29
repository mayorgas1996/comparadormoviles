package com.example.myproject;

import java.io.IOException;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import com.example.myproject.Mobile;
import com.example.myproject.PhoneHouse;
import com.example.myproject.Ebay;
import com.example.myproject.Mediamarkt;


@SuppressWarnings("serial")
public class HolaMundoServlet extends HttpServlet {
	
    public String setOkurl(String url){
    	url = url.replace("http:","");
    	return url;
    }
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		out.println("<html>  <head>  <meta http-equiv=content-type content=text/html; charset=UTF-8> <link rel=stylesheet type=text/css href=index.css> ");
		out.println("<title>Comparador de móviles</title>");
		out.println("</head>");
		out.println("<body>	<header> <h1>Comparador de teléfonos móviles</h1> </header>");
		out.println(" <h2> Resultados para: " + req.getParameter("movil") + "</h2>");
		out.println("<section id=resultados_ph>");
		
		PhoneHouse ph = new PhoneHouse();
		Ebay ebay = new Ebay();
		Mediamarkt mediamarkt = new Mediamarkt();
		
		List<Mobile> moviles = new ArrayList<Mobile>();
		List<Mobile> moviles_ebay = new ArrayList<Mobile>();
		List<Mobile> moviles_mediamarkt = new ArrayList<Mobile>();
		
		try{
			moviles = ph.run(req.getParameter("movil"));
			moviles_ebay = ebay.run("moviles+"+req.getParameter("movil"));
			moviles_mediamarkt = mediamarkt.run(req.getParameter("movil"));
		}
		catch (Exception e) {
		     System.out.println("Error en la funcion para pagina PhoneHouse");
		}
		
		int tam = moviles.size();
				
		if (tam > 10){
			tam = 10;
		}
		
		out.println("<img id=logotipo src='http://arenamultiespacio.com/wp-content/uploads/2016/12/phonehouse.png'/>");
		
		for (int i = 0; i < tam; i++){

			out.println("<a href = " + moviles.get(i).getURLproduct() + "> <article> ");
			out.println("<table> <tr> <th> " + moviles.get(i).getName()+ "</th> </tr>");
			out.println("<tr> <td> <img src= http://" + moviles.get(i).getURLimage() + "></td> <td>" + moviles.get(i).getPrice() + "</td>  </tr> "
					+ " <tr> <th> "+moviles.get(i).getPagina()+ "</th> </tr> </table> </article> </a>");
			
		}
		out.println("</section>");	
		
		out.println("<section id=resultados_ph>");
		out.println("<img id=logotipo src='https://upload.wikimedia.org/wikipedia/commons/4/48/EBay_logo.png'/>");
		
		for (int i = 0; i < moviles_ebay.size(); i++){

			moviles_ebay.get(i).setURLimage(setOkurl(moviles_ebay.get(i).getURLimage()));
			out.println("<a href= " + moviles_ebay.get(i).getURLproduct() +"> <article> ");
			out.println("<table> <tr> <th> " + moviles_ebay.get(i).getName()+ "</th> </tr>");
			out.println("<tr> <td> <img src=http:" + moviles_ebay.get(i).getURLimage() +"></td> <td>" + moviles_ebay.get(i).getPrice() + "</td>  </tr> "
					+ " <tr> <th> " + moviles_ebay.get(i).getPagina()+ "</th> </tr> </table> </article> </a> ");
			
		}
		
		
		out.println("</section>");	
			
		out.println("<section id=resultados_ph>");
		
		out.println("<img id=logotipo src='http://paack.co/wp-content/uploads/2016/07/MediaMarkt.png'/>");
		
		tam = moviles_mediamarkt.size();
		if (moviles_mediamarkt.size() > 10)
		{
			tam = 10;
		}
		
		for (int i = 0; i < tam; i++){
						
			moviles_mediamarkt.get(i).setURLimage(setOkurl(moviles_mediamarkt.get(i).getURLimage()));
			out.println("<a href = " + moviles_mediamarkt.get(i).getURLproduct() +"> <article> ");
			out.println("<table> <tr> <th> " + moviles_mediamarkt.get(i).getName()+ "</th> </tr>");
			out.println("<tr> <td> <img src="+moviles_mediamarkt.get(i).getURLimage() +"></td> <td>" + moviles_mediamarkt.get(i).getPrice() + "</td>  </tr> "
					+" <tr> <th> " + moviles_mediamarkt.get(i).getPagina()+ "</th> </tr> </table> </article> </a>");
			
		}
       	
		out.println("</section>");	
		
		out.println("</body> </html>");
		
	}
}

			