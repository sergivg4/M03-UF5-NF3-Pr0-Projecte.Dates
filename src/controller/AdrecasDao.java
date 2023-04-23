/**
 * 
 */
package controller;

import java.util.HashMap;

import model.Adreca;
import model.Persistable;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public class AdrecasDao implements Persistable<Adreca>{

	private HashMap<Integer, Adreca> direccions = new HashMap<>();
	
	@Override
	public void guardar(Adreca direccion) {
		direccions.put(direccion.getId(), direccion);
	}

	@Override
	public void eliminar(int id) {
		direccions.remove(id);
	}

	@Override
	public Adreca buscar(int id) {
		return direccions.get(id);
	}

	@Override
	public HashMap<Integer, Adreca> getMap() {
		return direccions;
	}

	
}
