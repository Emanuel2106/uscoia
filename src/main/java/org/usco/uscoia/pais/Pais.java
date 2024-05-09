package org.usco.uscoia.pais;

public class Pais {
	private int id;
	private String nombre;
	private String acronimo;
	
	public Pais() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pais(int id, String nombre, String acronimo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.acronimo = acronimo;
	}

	public Pais(String nombre, String acronimo) {
		super();
		this.nombre = nombre;
		this.acronimo = acronimo;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAcronimo() {
		return acronimo;
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

	
	@Override
	public String toString() {
		return "Pais [id=" + id + ", nombre=" + nombre + ", acronimo=" + acronimo + "]";
	}
	
	
	
	

}
