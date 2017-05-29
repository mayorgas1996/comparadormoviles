package com.example.myproject;

public class Mobile {

	public String id;
	public String name;
	public double price;
	public String URLimage;
	public String URLproduct;
	public String pagina;
	
	//Constructor por defecto y vacio
	public Mobile(){
		
		setId(null);
		setName(null);
		setPrice(0.0);
		setURLimage(null);
		setURLproduct(null);
		setPagina(null);
	}
	
	//Constructor con argumentos
	public Mobile(String idd, String nam, double pric, String URLimag, String URLproduc, String page){
		
		setId(idd);
		setName(nam);
		setPrice(pric);
		setURLimage(URLimag);
		setURLproduct(URLproduc);
		setPagina(page);
	}

	public void set (String idd, String nam, double pric, String URLimag, String URLproduc,String page){
		
		setId(idd);
		setName(nam);
		setPrice(pric);
		setURLimage(URLimag);
		setURLproduct(URLproduc);
		setPagina(page);
	}
	
	//Getters y Setters para los atributos de la clase
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getURLimage() {
		return URLimage;
	}

	public void setURLimage(String uRLimage) {
		URLimage = uRLimage;
	}

	public String getURLproduct() {
		return URLproduct;
	}

	public void setURLproduct(String uRLproduct) {
		URLproduct = uRLproduct;
	}
	
	public void setPagina(String page){
		pagina = page;
	}
	
	public String getPagina() {
		return pagina;
	}
	
	
}
