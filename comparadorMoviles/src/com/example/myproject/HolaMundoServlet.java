package com.example.myproject;

import java.io.IOException;
import javax.servlet.http.*;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import java.net.*;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.*;
import com.example.myproject.Mobile;
import com.example.myproject.AmazonSearch;

@SuppressWarnings("serial")
public class HolaMundoServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {/*
		resp.setContentType("text/plain");
		resp.getWriter().println("El movil a buscar es... " + req.getParameter("movil"));*/

		List<Mobile>resuAmazon;
		String searchword = req.getParameter("keywords");
		
        AmazonSearch driverAmazon=new AmazonSearch();
        String tag = searchword;
       
        try {

			resuAmazon=new ArrayList<Mobile>(driverAmazon.run(java.net.URLEncoder.encode(tag, "UTF-8")));
		
			
			req.setAttribute("articulosAmazon", resuAmazon);
			RequestDispatcher rd = req.getRequestDispatcher("/index.html");
			rd.forward(req, resp);
			
			
			//*******************************//
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
}

			