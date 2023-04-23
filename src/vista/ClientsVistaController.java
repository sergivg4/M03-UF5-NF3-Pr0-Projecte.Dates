/**
 * 
 */
package vista;

import java.time.LocalDate;
import java.util.TreeSet;

import controller.AdrecasDao;
import controller.ClientsDao;
import funciones.UtilConsole;
import model.Client;
import model.Adreca;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
class ClientsVistaController {

	public void inicio(ClientsDao cd, AdrecasDao ad) {
		int opcion;
		do {
			// submenu
			System.out.println("0. Salir");
			System.out.println("1. Afegir");
			System.out.println("2. Buscar");
			System.out.println("3. Eliminar");
			System.out.println("4. Modificar");
			System.out.println("5. Mostrar");
			opcion = UtilConsole.pedirInt();
			// segun lo que se escoge, se llama al submenu del controlador que toca
			if (opcion == 1) {
				afegirClientMenu(cd, ad, false, 0);
			} else if (opcion == 2) {
				System.out.println(comprobarIdClient(cd));
			}else if (opcion == 3) {
				cd.eliminar(comprobarIdClient(cd).getIdPersona());
			}else if(opcion == 4) {
				int id = comprobarIdClient(cd).getIdPersona();
				afegirClientMenu(cd, ad, true, id);
			} else if (opcion == 5) {
				IniciVistaController.imprimir(cd);
			}
		} while (opcion != 0);
	}
	
	private static Client comprobarIdClient(ClientsDao cd) {
		int id = -1;
		Client c = null;
		do {
			IniciVistaController.imprimir(cd);
			System.out.println("Introduce la id: ");
			id = UtilConsole.pedirInt();
			c = cd.buscar(id);
			if (c == null) {
				System.out.println("id erroneo");
			}
		} while (c == null);
		return c;
	}
	
	private static Adreca comprobarIdAdreca(AdrecasDao ad) {
		int id = -1;
		Adreca a = null;
		do {
			IniciVistaController.imprimir(ad);
			System.out.println("Introduce la id: ");
			id = UtilConsole.pedirInt();
			a = ad.buscar(id);
			if (a == null) {
				System.out.println("id erroneo");
			}
		} while (a == null);
		return a;
	}
	
	private static void afegirClientMenu(ClientsDao cd, AdrecasDao ad, boolean mod, int idPersona) {

		if (!mod) {
			// submenu
			System.out.println("Id de Cliente:");
			idPersona = UtilConsole.pedirInt();
		}
		System.out.println("DNI:");
		String dni = UtilConsole.pedirDNI();
		System.out.println("Nombre:");
		String nombre = UtilConsole.pedirString();
		System.out.println("Apellidos:");
		String apellido = UtilConsole.pedirString();
		System.out.println("Fecha de nacimiento:");
		LocalDate fechaDeNacimiento = UtilConsole.demanarData();
		System.out.println("Email:");
		String email = UtilConsole.pedirEmail();
		TreeSet<String> telefonos = new TreeSet<>();
		Integer addTel = -1;
		do {
			System.out.println("Escribe un numero de telefono:");
			String telefono = UtilConsole.pedirTelefono();
			//Si el telefono se repite, avisa con un mensaje y vuelve a pedirlo.
			if (telefonos.contains(telefono)) {
				System.out.println("El telefono introducido ya existe.");
			} else {
				telefonos.add(telefono);
			}
			System.out.println("Quieres añadir otro telefono?");
			System.out.println("1. Si");
			System.out.println("2. No");
			addTel = UtilConsole.pedirInt();
		} while (addTel == 1);
		System.out.println("Dirección:");
		Adreca direccion = afegirAdrecaMenu(ad);
		Boolean enviarPublicitat = false;
		Integer aceptarPubli = 0;
		do {
			System.out.println("Enviar Publicitat?");
			System.out.println("1. Si");
			System.out.println("2. No");
			aceptarPubli = UtilConsole.pedirInt();
			if(aceptarPubli == 1) {
				enviarPublicitat = true;
			}else if(aceptarPubli == 2){
				enviarPublicitat = false;
			}
		} while (aceptarPubli != 1 && aceptarPubli != 2);
		
		Client cli = new Client(idPersona, dni, nombre, apellido, fechaDeNacimiento, email, telefonos, direccion, enviarPublicitat);
		cd.guardar(cli);
	}
	
	private static Adreca afegirAdrecaMenu(AdrecasDao ad) {
		Adreca direccion = null;
		Integer newAdreca = 0;
		do {
			if (ad.getMap().isEmpty()) {
				newAdreca = 2;
			} else {
				System.out.println("¿Quieres usar una direccion creada anteriormente?");
				System.out.println("1. Si");
				System.out.println("2. No");
				newAdreca = UtilConsole.pedirInt();
			}
			if(newAdreca == 1) {
				ad.getMap();
				System.out.println("Que direccion vas a usar?");
				direccion = comprobarIdAdreca(ad);
			}else if(newAdreca == 2){
				//Crear Adreca
				System.out.println("Id:");
				Integer idAdreca = UtilConsole.pedirInt();
				System.out.println("Poblacion:");
				String poblacion = UtilConsole.pedirString();
				System.out.println("Provincia:");
				String provincia = UtilConsole.pedirString();
				System.out.println("Codigo postal:");
				String cp = UtilConsole.pedirCP();
				System.out.println("Domicilio:");
				String domicilio = UtilConsole.pedirString();
				direccion = new Adreca(idAdreca, poblacion, provincia, cp, domicilio);
				ad.guardar(direccion);
			}
		} while (newAdreca != 1 && newAdreca != 2);
	
		return direccion;
	}
	
}
