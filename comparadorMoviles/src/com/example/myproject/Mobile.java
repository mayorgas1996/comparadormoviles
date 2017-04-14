package com.example.myproject;

public class Mobile {

	public String id;
	public String name;
	public String price;
	public String URLimage;
	public String URLproduct;
	
	//Constructor por defecto y vacio
	public Mobile(){
		
		setId(null);
		setName(null);
		setPrice(null);
		setURLimage(null);
		setURLproduct(null);
	}
	
	//Constructor con argumentos
	public Mobile(String idd, String nam, String pric, String URLimag, String URLproduc){
		
		setId(idd);
		setName(nam);
		setPrice(pric);
		setURLimage(URLimag);
		setURLproduct(URLproduc);
	}

	public void set (String idd, String nam, String pric, String URLimag, String URLproduc){
		
		setId(idd);
		setName(nam);
		setPrice(pric);
		setURLimage(URLimag);
		setURLproduct(URLproduc);
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
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
	
	
	
	
	
}
