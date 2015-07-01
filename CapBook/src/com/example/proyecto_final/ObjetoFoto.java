package com.example.proyecto_final;

import java.io.Serializable;

public class ObjetoFoto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String nombre, titulo, url, fecha;
	int idfoto, idusuario;
	
	public ObjetoFoto(){
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getIdfoto() {
		return idfoto;
	}

	public void setIdfoto(int idfoto) {
		this.idfoto = idfoto;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
