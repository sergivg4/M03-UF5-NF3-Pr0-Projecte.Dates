
package controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import model.BotigaImportExportable;
import model.Pack;
import model.Persistable;
import model.Producte;
import model.ProducteAbstract;
import java.time.LocalDate;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public class BotigaImportExportText implements BotigaImportExportable<ProducteAbstract>{

	private static TreeMap<Integer, ProducteAbstract> productesMap = new TreeMap<>();
	
	@Override
	public TreeMap<Integer, ProducteAbstract> importar(String nomFitxer) throws IOException {
		
		//variables
		ProducteAbstract prod = null;
		String linea = "";
		String[] datos = {};
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		try (BufferedReader bfr = new BufferedReader(new FileReader(nomFitxer))){
			while ((linea = bfr.readLine()) != null) {
				datos = linea.split("\\|");
				int id = Integer.parseInt(datos[0]);
				String nom = datos[1];
				double preu = Double.parseDouble(datos[2].replace(",", "."));
				int stock = Integer.parseInt(datos[3]);
				LocalDate dataInicio = LocalDate.parse(datos[4], formatter);
				LocalDate dataFinal = LocalDate.parse(datos[5], formatter);
				prod = new Producte(id, nom, stock, preu, dataInicio, dataFinal);
				productesMap.put(id, prod);
			}
		}
		return productesMap;
	}

	@Override
	public boolean exportar(String nomFitxer, Persistable<ProducteAbstract> dao) throws IOException {
		
		//Convierte los double con . a decimal con , 
		DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.GERMANY);
		decimalFormatSymbols.setDecimalSeparator(',');
		DecimalFormat df = new DecimalFormat("#.##", decimalFormatSymbols);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(nomFitxer), StandardCharsets.UTF_8)){
			
			for (Map.Entry<Integer, ProducteAbstract> entry : dao.getMap().entrySet()) {
				ProducteAbstract tempProd = entry.getValue();
				
				if (tempProd instanceof Producte tempProd2) {
					osw.write(String.valueOf(tempProd2.getId() + "|"));
					osw.write(tempProd2.getNom() + "|");
					osw.write(df.format(tempProd2.getPrecio()) + "|");
					osw.write(tempProd2.getStock() + "|");
					osw.write(tempProd2.getDataInicio().format(formatter) + "|");
					osw.write(tempProd2.getDataFinal().format(formatter) + "\n");
				}else if(tempProd instanceof Pack) {
					//Todavía no se puede exportar packs
				}
			}
			System.out.println("Texto exportado en: " + nomFitxer);
		}
		return false;
	}
	
}
