/**
 * 
 */
package vista;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import controller.BotigaImportExportData;
import controller.BotigaImportExportJSON;
import controller.BotigaImportExportText;
import controller.ProductesDao;
import exceptions.StockInsuficientException;
import funciones.ProducteComparator;
import funciones.UtilConsole;
import model.Pack;
import model.Producte;
import model.ProducteAbstract;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
class ProductesVistaController {

	static String salir = "0. Salir";
	static Logger log = Logger.getLogger("logs");
	
	public void inicio(ProductesDao pd, BotigaImportExportText impExText, BotigaImportExportData impExData, BotigaImportExportJSON impExJSON) throws IOException {
		
		//Cargar datos
		pd.obrirFitxer();
		
		int opcion;
		do {
			// menu
			System.out.println(salir);
			System.out.println("1. Afegir");
			System.out.println("2. Buscar");
			System.out.println("3. Eliminar");
			System.out.println("4. Modificar");
			System.out.println("5. Mostrar");
			System.out.println("6. Importar");
			System.out.println("7. Exportar");
			System.out.println("8. Afegir Stock");
			System.out.println("9. Treure Stock");
			System.out.println("10. Mostrar descatalogats");
			opcion = UtilConsole.pedirInt();
			// segun lo que se escoge, se llama al submenu del controlador que toca
			if (opcion == 1) {
				afegirProducteMenu(pd, false, 0);
			} else if (opcion == 2) {
				System.out.println(comprobarId(pd));
			}else if (opcion == 3) {
				pd.eliminar(comprobarId(pd).getId());
			}else if(opcion == 4) {
				int id = comprobarId(pd).getId();
				afegirProducteMenu(pd, true, id);
			} else if (opcion == 5) {
				IniciVistaController.imprimir(pd);
			}else if(opcion == 6) {
				menuImportar(pd, impExText, impExData, impExJSON);
			} else if (opcion == 7) {
				menuExportar(pd, impExText, impExData, impExJSON);
			}else if(opcion == 8) {
				menuAfegirStock(comprobarId(pd));
			} else if (opcion == 9) {
				menuTreureStock(comprobarId(pd));
			} else if (opcion == 10) {
				pd.imprimirDescatalogats();
			}
		} while (opcion != 0);
			//Guardar datos
			pd.guardarFitxer();
	}
	
	public void menuAfegirStock(ProducteAbstract p) {
		
		int cantidad = 0;
		
		if(p instanceof Producte prod) {
			System.out.println("Cantidad de Stock que se va a añadir:");
			cantidad = UtilConsole.pedirInt();
			System.out.println("Stock de " + prod.getNom() + " antes:" + prod.getStock());
			prod.afegirStock(cantidad);
			System.out.println("Stock de " + prod.getNom() + " ahora:" + prod.getStock());
			
		} else {
			System.out.println("El Id escogido pertenece a un Pack, solo se puede añadir stock a un Producto.");
		}
	}
	
	public void menuTreureStock(ProducteAbstract p) {
		
		int cantidad = 0;
		
		if(p instanceof Producte prod) {
			
			int stockAntes = prod.getStock();
			
			System.out.println("Cantidad de Stock que se va a eliminar:");
			cantidad = UtilConsole.pedirInt();
			try {
				prod.treureStock(cantidad);
				System.out.println("Stock de " + prod.getNom() + " antes:" + stockAntes);
				System.out.println("Stock de " + prod.getNom() + " ahora:" + prod.getStock());
			} catch (StockInsuficientException e) {
				log.log(Level.SEVERE, "Error StockInsuficientException", e);
			}
		} else {
			System.out.println("El Id escogido pertenece a un Pack, solo se puede quitar Stock a un Producto.");
		}
	}

	public void menuImportar(ProductesDao pd, BotigaImportExportText impExText, BotigaImportExportData impExData, BotigaImportExportJSON impExJSON) {

		TreeMap<Integer, ProducteAbstract> productsToImport = null;
		int opcionMenu;
		int opcionConfirm = 0;
		String archivo = "datos";
		
		try {
			do {
				// menu
				System.out.println("Importar en format:");
				System.out.println("0. Salir");
				System.out.println("1. Format text");
				System.out.println("2. Format data");
				System.out.println("3. Format JSON");
				opcionMenu = UtilConsole.pedirInt();
				
				if (opcionMenu != 0) {
					System.out.println("¿Como se llama el archivo del que quieres Importar?");
					System.out.println("El archivo que ya existe se llama: datos");
					archivo = UtilConsole.pedirString();
				} else {
					opcionConfirm = 2;
				}
				
				if (opcionMenu == 1) {
					productsToImport = impExText.importar("res/" + archivo + ".txt");
					opcionMenu = 0;
				} else if (opcionMenu == 2) {
					productsToImport = impExData.importar("res/" + archivo + ".dat");
					opcionMenu = 0;
				} else if (opcionMenu == 3) {
					productsToImport = impExJSON.importar("res/" + archivo + ".json");
					opcionMenu = 0;
				}

			} while (opcionMenu != 0);

			while (opcionConfirm != 2 && (!productsToImport.isEmpty() || productsToImport == null )) {
				System.out.println("Productos que se van a importar:");
				for (Map.Entry<Integer, ProducteAbstract> entry : productsToImport.entrySet()) {
					System.out.println(entry.getValue());
				}

				System.out.println("¿Estas seguro que quieres importar estos productos?");
				System.out.println("1. Si");
				System.out.println("2. No");
				opcionConfirm = UtilConsole.pedirInt();
				if (opcionConfirm == 1) {
					System.out.println("Se han importado SOLO los productos con IDs NO existentes.");
					productsToImport.forEach((integer, producteAbstract) -> pd.productes.putIfAbsent(integer, producteAbstract));
					for (Map.Entry<Integer, ProducteAbstract> entry : pd.productes.entrySet()) {
						System.out.println(entry.getValue());
					}
					opcionConfirm = 2;
				} else if (opcionConfirm == 2) {
					System.out.println("Los productos NO han sido importados.");
				}
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, "Error IOException", e);
		}

	}
	
	private static void menuExportar(ProductesDao pd, BotigaImportExportText impExText, BotigaImportExportData impExData, BotigaImportExportJSON impExJSON)  throws IOException {
		int opcion;
		try {
			do {
				// menu
				System.out.println("Exportar en format:");
				System.out.println(salir);
				System.out.println("1. Format text");
				System.out.println("2. Format data");
				System.out.println("3. Format JSON");
				opcion = UtilConsole.pedirInt();

				System.out.println("¿Como se llama el archivo del que quieres Exportar?");
				System.out.println("El archivo que ya existe se llama: datos");
				String archivo = UtilConsole.pedirString();
				
				if (opcion == 1) {
					impExText.exportar("res/" + archivo + ".txt", pd);
					opcion = 0;
				} else if (opcion == 2) {
					impExData.exportar("res/" + archivo + ".dat", pd);
					opcion = 0;
				}else if (opcion == 3) {
					impExJSON.exportar("res/" + archivo + ".json", pd);
					opcion = 0;
				}
			} while (opcion != 0);
		} catch (IOException e) {
			log.log(Level.SEVERE, "Error IOException", e);
		}
	}
	
	private static ProducteAbstract comprobarId(ProductesDao pd) {
		int id = -1;
		ProducteAbstract p = null;
		do {
			IniciVistaController.imprimir(pd);
			System.out.println("Introduce la id: ");
			id = UtilConsole.pedirInt();
			p = pd.buscar(id);
			if (p == null) {
				System.out.println("id erroneo");
			}
		} while (p == null);
		return p;
	}

	/**
	 * 
	 */
	private static void afegirProducteMenu(ProductesDao pd, boolean mod, int id) {

		int opcion = 0;
		if (!mod) {
			do {
				// submenu
				System.out.println("Que quieres crear?");
				System.out.println(salir);
				System.out.println("1. Producte");
				System.out.println("2. Pack");
				opcion = UtilConsole.pedirInt();
				System.out.println("id");
				id = UtilConsole.pedirInt();
				System.out.println("nom");
				String nom = UtilConsole.pedirString();
				if (opcion == 1) {
					crearProducte(pd, id, nom);
					opcion = 0;
				} else if (opcion == 2) {
					crearPack(pd, id, nom);
					opcion = 0;
				}
			} while (opcion != 0);
		} else {
			System.out.println("nom");
			String nom = UtilConsole.pedirString();
			if (pd.buscar(id) instanceof Producte) {
				crearProducte(pd, id, nom);
			} else if (pd.buscar(id) instanceof Pack) {
				crearPack(pd, id, nom);
			}
		}
	}
	
	private static void crearProducte(ProductesDao pd, int id, String nom) {
		System.out.println("Stock");
		int stock = UtilConsole.pedirInt();
		System.out.println("Preu");
		double preu = UtilConsole.pedirDouble();
		System.out.println("Data inici");
		LocalDate dataInicio = UtilConsole.demanarData();
		System.out.println("Data fi");
		LocalDate dataFinal = UtilConsole.demanarData();
		Producte prod = new Producte(id, nom, stock, preu, dataInicio, dataFinal);
		guardarProducteAbstract(pd, prod);
	}
	
	private static void crearPack(ProductesDao pd, int id, String nom) {
		System.out.println("Productes");
		TreeSet<Producte> prodsTemp = new TreeSet<>(new ProducteComparator());
		Integer packEquals = 0;
		do {
			int ids = -1;
			packEquals = 0;
			do {
				System.out.println("Escribe una id o 0 para salir");
				ids = UtilConsole.pedirInt();
				ProducteAbstract p = pd.buscar(ids);
				if (p != null) {
					// Destectamos si es un producto y si ya lo tenemos en el pack
					if (p instanceof Producte p2 && !prodsTemp.contains(p2)) {
						prodsTemp.add(p2);
					} else if (p instanceof Producte p2 && prodsTemp.contains(p2)) {
						System.out.println("El producto seleccionado ya esta en el pack");
					} else {
						System.out.println("El id no es un producto");
					}
				} else if (ids != 0) {
					System.out.println("El id no existe");
				}
				System.out.println("ID: " + ids);
			} while (ids != 0);
			
			// detecta si el conjunto de productos de pack está repetido.
			for (Map.Entry<Integer, ProducteAbstract> entry : pd.getMap().entrySet()) {
				ProducteAbstract prod = entry.getValue();
				if (prod instanceof Pack pack && pack.getProductes().equals(prodsTemp)) {
					System.out.println("El Pack de Productos no puede ser igual a uno ya existente, vuelva a seleccionar los productos:");
					packEquals++;
					prodsTemp.clear();
				}
			}
		} while (packEquals > 0);

		System.out.println("Escribe el descuento del pack");
		double descompte = UtilConsole.pedirDouble();
		Pack pack = new Pack(id, nom, prodsTemp, descompte);
		guardarProducteAbstract(pd, pack);
	}
	
	private static void guardarProducteAbstract(ProductesDao pd, ProducteAbstract p) {
		if (p != null) {
			pd.guardar(p);
		} else {
			System.out.println("La creacion ha fallado!");
		}
	}
}
