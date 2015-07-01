package com.example.proyecto_final;

public class ObjetoComentario {
	
	public ObjetoComentario() {
		super();
	}
	
	public ObjetoComentario(String nombre, String texto, String fecha, int idfoto) {
		super();
		this.nombre = nombre;
		this.texto = texto;
		this.fecha = fecha;
		this.idfoto = idfoto;
	}
	
	String nombre, texto, fecha;
	int idfoto;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getIdfoto() {
		return idfoto;
	}
	public void setIdfoto(int idfoto) {
		this.idfoto = idfoto;
	}
	
}
