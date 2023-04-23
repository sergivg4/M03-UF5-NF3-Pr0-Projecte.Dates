/**
 * 
 */
package vista;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import controller.BotigaImportExportData;
import controller.BotigaImportExportJSON;
import controller.BotigaImportExportText;
import controller.ClientsDao;
import controller.AdrecasDao;
import controller.ProductesDao;
import funciones.UtilConsole;
import model.Persistable;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 * 
 * Crear un objeto por cada dao y por cada controlador y que los controladores pidan el dao
 * hacer que los ficheros para importar sean scanner
 * 
 */
public class IniciVistaController {
	 
	public static void main(String[] args){
		
		Logger log = Logger.getLogger("logs");
		
		int opcion = 0;
		ProductesDao pd = new ProductesDao();
		ClientsDao cd = new ClientsDao();
		AdrecasDao ad = new AdrecasDao();
		ProductesVistaController pvc = new ProductesVistaController();
		ClientsVistaController cvc = new ClientsVistaController();
		BotigaImportExportText impExText = new BotigaImportExportText();
		BotigaImportExportData impExData = new BotigaImportExportData();
		BotigaImportExportJSON impExJSON = new BotigaImportExportJSON();
		
		try {
			FileHandler fh = new FileHandler("res/log.txt", true);
			fh.setFormatter(new SimpleFormatter());
			log.addHandler(fh);
			log.setLevel(Level.ALL);
			log.info("inicio");
			do {
				//menu
				System.out.println("0. Salir");
				System.out.println("1. Productes");
				System.out.println("2. Clients");
				System.out.println("3. Proveïdors");
				opcion = UtilConsole.pedirInt();
				//segun lo que se escoge, se llama al submenu del controlador que toca
				if (opcion == 1) {
					pvc.inicio(pd, impExText, impExData, impExJSON);
				}else  if (opcion == 2){
					cvc.inicio(cd, ad);
				}else  if (opcion == 3){
					System.out.println("No estan hechos los proveedores :(");
				}
				log.fine("Fue todo bien");
			} while (opcion != 0);	
		} catch (IndexOutOfBoundsException ex) {
			log.log(Level.SEVERE, "Error al pedir el valor, solo del 0 al 3!", ex);
		} catch (InputMismatchException | NumberFormatException ex) {
			log.log(Level.SEVERE, "Error al pedir el valor, tiene que ser un int!", ex);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			log.log(Level.SEVERE, "Error IOException", ex);
		}
	}
	
	/**
	 * 
	 */
	public static void imprimir(Persistable<?> p) {
		for (Map.Entry<Integer, ?> entry : p.getMap().entrySet()) {
		    System.out.println(entry.getValue());
		}
	}
	
}
