package model;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;
import funciones.ProducteComparator;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public class Pack extends ProducteAbstract implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TreeSet<Producte> productes = new TreeSet<>(new ProducteComparator());
	private double porDescuento;
	
	/**
	 * 
	 * @param id
	 * @param nom
	 * @param productes
	 * @param porDescuento
	 */
	public Pack(int id, String nom, SortedSet<Producte> productes, double porDescuento) {
		super(id, nom);
		this.productes = (TreeSet<Producte>) productes;
		this.porDescuento = porDescuento;
	}

	public SortedSet<Producte> getProductes() {
		return productes;
	}

	public void setProductes(SortedSet<Producte> productes) {
		this.productes = (TreeSet<Producte>) productes;
	}

	public double getPorDescuento() {
		return porDescuento;
	}

	public void setPorDescuento(double porDescuento) {
		this.porDescuento = porDescuento;
	}
	
	@Override
	public String toString() {
		
		StringBuilder productosDelPack = new StringBuilder();
		
		for (Producte producte : productes) {
			productosDelPack.append("\n       --> " + producte);
		}
		
		return "Pack [ Id = " + getId() + ", Nom = " + getNom() + ", Descuento = " + getPorDescuento() + "% ]" + productosDelPack;
		
	}
	
	
	
	
}
