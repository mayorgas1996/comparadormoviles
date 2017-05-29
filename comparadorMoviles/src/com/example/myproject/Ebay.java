package com.example.myproject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Ebay {

    public final static String EBAY_APP_ID = "JavierMa-Comparad-PRD-9dbc19de5-e8219a5a";
    public final static String EBAY_FINDING_SERVICE_URI = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME="
            + "{operation}&SERVICE-VERSION={version}&SECURITY-APPNAME="
            + "{applicationId}&GLOBAL-ID={globalId}&keywords={keywords}"
            + "&paginationInput.entriesPerPage={maxresults}&LH_ItemCondition=1000";
    public static final String SERVICE_VERSION = "1.0.0";
    public static final String OPERATION_NAME = "findItemsByKeywords";
    public static final String GLOBAL_ID = "EBAY-ES";
    public final static int REQUEST_DELAY = 3000;
    public final static int MAX_RESULTS = 10;
    private int maxResults;
    private List<Mobile> res;
    
    public Ebay() {
        this.maxResults = MAX_RESULTS;
        res=new ArrayList<Mobile>();

    }

    public Ebay(int maxResults) {
        this.maxResults = maxResults;
    }

    public List<Mobile> run(String tag) throws Exception {

        String address = createAddress(tag);
        print("sending request to :: ", address);
        String response = URLReader.read(address);
        print("response :: ", response);
        //process xml dump returned from EBAY
        res = processResponse(response);
        //Honor rate limits - wait between results
        Thread.sleep(REQUEST_DELAY);

        return res;
    }

    private String createAddress(String tag) {

        //substitute token
        String address = Ebay.EBAY_FINDING_SERVICE_URI;
        address = address.replace("{version}", Ebay.SERVICE_VERSION);
        address = address.replace("{operation}", Ebay.OPERATION_NAME);
        address = address.replace("{globalId}", Ebay.GLOBAL_ID);
        address = address.replace("{applicationId}", Ebay.EBAY_APP_ID);
        address = address.replace("{keywords}", tag);
        address = address.replace("{maxresults}", "" + this.maxResults);

        return address;

    }

    public void setOkurl(String url){
    	url = url.replace("http:","");
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
    
    private  List<Mobile> processResponse(String response) throws Exception {


        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = domFactory.newDocumentBuilder();


        Document doc = builder.parse(is);
        XPathExpression ackExpression = xpath.compile("//findItemsByKeywordsResponse/ack");
        XPathExpression itemExpression = xpath.compile("//findItemsByKeywordsResponse/searchResult/item");

        String ackToken = (String) ackExpression.evaluate(doc, XPathConstants.STRING);
        print("ACK from ebay API :: ", ackToken);
        if (!ackToken.equals("Success")) {
            throw new Exception(" service returned an error");
        }

        NodeList nodes = (NodeList) itemExpression.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);

            String itemId = (String) xpath.evaluate("itemId", node, XPathConstants.STRING);
            String title = (String) xpath.evaluate("title", node, XPathConstants.STRING);
            String itemUrl = (String) xpath.evaluate("viewItemURL", node, XPathConstants.STRING);
            String galleryUrl = (String) xpath.evaluate("galleryURL", node, XPathConstants.STRING);

            String currentPrice = (String) xpath.evaluate("sellingStatus/currentPrice", node, XPathConstants.STRING);

            setOkurl(galleryUrl);
            
            Mobile movil=new Mobile();
            
            double precio = Double.parseDouble(currentPrice);
            
            movil.set(itemId,title,precio,galleryUrl,itemUrl,"Ebay");
            res.add(movil);

        }

        is.close();
        
        ordenarPorPrecio(res);
        return res;

    }

    private void print(String name, String value) {
        System.out.println(name + "::" + value);
    }

    public static void main(String[] args) throws Exception {
        Ebay driver = new Ebay();
        String tag = "Velo binding machine";
        driver.run(java.net.URLEncoder.encode(tag, "UTF-8"));

    }
}