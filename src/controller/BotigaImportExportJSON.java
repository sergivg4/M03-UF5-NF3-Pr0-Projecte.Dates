
package controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import funciones.LocalDateAdapter;
import model.BotigaImportExportable;
import model.Persistable;
import model.Producte;
import model.ProducteAbstract;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.TreeMap;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public class BotigaImportExportJSON implements BotigaImportExportable<ProducteAbstract>{
	
	@Override
	public TreeMap<Integer, ProducteAbstract> importar(String nomFitxer) throws IOException {
		
		TreeMap<Integer, ProducteAbstract> productesMap = new TreeMap<>();
		
		Gson gson = new GsonBuilder()
			    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
			    .create();


		try (JsonReader getLocalJsonFile = new JsonReader(new FileReader(nomFitxer))) {
			
			Type mapTokenType = new TypeToken<TreeMap<Integer, Producte>>(){}.getType();

			productesMap = gson.fromJson(getLocalJsonFile, mapTokenType);

		}
		return productesMap;
	}

	@Override
	public boolean exportar(String nomFitxer, Persistable<ProducteAbstract> dao) throws IOException{
		
		Gson gson = new GsonBuilder()
                .setPrettyPrinting() // para dar formato legible
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateAdapter.class, new LocalDateAdapter.LocalDateAdapterInstanceCreator())
                .create();
		
		try (FileWriter fw = new FileWriter(nomFitxer)){
			
			gson.toJson(dao.getMap(), fw);
			System.out.println("Datos exportados en: " + nomFitxer);
		}
		return false;
	}
	
}
