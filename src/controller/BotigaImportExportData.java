
package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import model.BotigaImportExportable;
import model.Pack;
import model.Persistable;
import model.Producte;
import model.ProducteAbstract;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public class BotigaImportExportData implements BotigaImportExportable<ProducteAbstract>{

	private static TreeMap<Integer, ProducteAbstract> productesMap = new TreeMap<>();
	
	@Override
	public TreeMap<Integer, ProducteAbstract> importar(String nomFitxer) throws IOException {
		//pack para poder leer datos
		try(DataInputStream dis = new DataInputStream(new FileInputStream(nomFitxer))) {
				while (dis.available() > 0) {
					int id = dis.readInt();
					String nom = dis.readUTF();
					int stock = dis.readInt();
					double precio = dis.readDouble();
					int yearInicio = dis.readInt();
					int monthInicio = dis.readInt();
					int dayInicio = dis.readInt();
					LocalDate dataInicio = LocalDate.of(dayInicio, monthInicio, yearInicio);
					int yearFinal = dis.readInt();
					int monthFinal = dis.readInt();
					int dayFinal = dis.readInt();
					LocalDate dataFinal = LocalDate.of(dayFinal, monthFinal, yearFinal);
					ProducteAbstract tempProd = new Producte( id, nom, stock, precio, dataInicio, dataFinal);
					productesMap.put(id, tempProd);
				}
		}
		return productesMap;
	}

	@Override
	public boolean exportar(String nomFitxer, Persistable<ProducteAbstract> dao) throws IOException {
		
		//creamos el pack de objetos de escritura de datos
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(nomFitxer))) {
			
			for (Map.Entry<Integer, ProducteAbstract> entry : dao.getMap().entrySet()) {
				ProducteAbstract tempProd = entry.getValue();
				if (tempProd instanceof Producte tempProd2) {
					dos.writeInt(tempProd2.getId());
					dos.writeUTF(tempProd2.getNom());
					dos.writeInt(tempProd2.getStock());
					dos.writeDouble(tempProd2.getPrecio());
					dos.writeInt(tempProd2.getDataInicio().getDayOfMonth());
					dos.writeInt(tempProd2.getDataInicio().getMonthValue());
					dos.writeInt(tempProd2.getDataInicio().getYear());
					dos.writeInt(tempProd2.getDataFinal().getDayOfMonth());
					dos.writeInt(tempProd2.getDataFinal().getMonthValue());
					dos.writeInt(tempProd2.getDataFinal().getYear());
				}else if(tempProd instanceof Pack) {
					//Todavía no se puede exportar packs
				}
			}
			System.out.println("Datos exportados en: " + nomFitxer);
		}
		return false;
	}
	
}
