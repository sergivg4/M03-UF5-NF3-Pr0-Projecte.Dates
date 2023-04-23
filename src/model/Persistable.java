/**
 * 
 */
package model;

import java.util.Map;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public interface Persistable<T> {

	//guardar
	public void guardar(T obj);
	//eliminar
	public void eliminar(int id);
	//buscar
	public T buscar(int id);
	//getMap
	public Map<Integer, T> getMap();
}
