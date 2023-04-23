/**
 * 
 */
package controller;

import java.util.HashMap;

import model.Client;
import model.Persistable;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public class ClientsDao implements Persistable<Client>{

	private HashMap<Integer, Client> clients = new HashMap<>();
	
	@Override
	public void guardar(Client cli) {
		clients.put(cli.getIdPersona(), cli);
	}

	@Override
	public void eliminar(int id) {
		clients.remove(id);
	}

	@Override
	public Client buscar(int id) {
		return clients.get(id);
	}

	@Override
	public HashMap<Integer, Client> getMap() {
		return clients;
	}

	
}
