package funciones;

import java.io.Serializable;
import java.util.Comparator;

import model.Producte;

/**@author Álvaro Owen de la Quintana
 * ProgramacionBasicaUF5 
 * 21 mar 2023
 */
@SuppressWarnings("serial")
public class ProducteComparator implements Comparator<Producte>, Serializable {

	/**
	 * Crea un comparador que mira el id de persona a la hora de compararlo
	 * devuelve 0 si ambos valores son iguales; -1 si p1 es menor que p2 y 
	 * 1 si p1 es mayor que p2, esto determina el peso del valor
	 */
	@Override
	public int compare(Producte p1, Producte p2) {
		if (p1.getId() == p2.getId()) {
			return 0;
		}else if (p1.getId() < p2.getId()) {
			return -1;
		}else {
			return 1;
		}
	}

}
