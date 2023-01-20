package ABP6;

import java.io.File;
import java.util.ArrayList;

public class DropTable {

	public static void dropTable(String nombreTabla) {
		
		ArrayList<String> nombresTablas = new ArrayList<>();
		boolean tablaEncontrada = false;
		File archivoTabla =null,archivoTablaData;
		for(int i=0;i<nombresTablas.size();i++) {
			if(nombresTablas.contains(nombreTabla)) {
				tablaEncontrada=true;
			}
		}
		//Cambiar la direccion del archivo 
		if(tablaEncontrada) {
			 archivoTabla =  new File("D:\\Users\\dam223\\Desktop\\"+nombreTabla+".metadata");
			 if(archivoTabla.exists()) {
				 archivoTabla.delete();
			 }
			 else
				 System.out.println("El archivo "+nombreTabla+".metadata no existe");
			 archivoTablaData = new File("D:\\Users\\dam223\\Desktop\\"+nombreTabla+".data");
			 if(archivoTablaData.exists()) {
				 archivoTablaData.delete();
			 }
			 else
				 System.out.println("El archivo "+nombreTabla+".data no existe");
			 nombresTablas.remove(nombreTabla);
		}
	}
	
	public static boolean comprobarDropTable(String comando) {
		
		String dropTable1=comando.substring(0, 10);
		String dropTable2="drop table";
		int longitud=comando.length();
		
		if (!dropTable1.equalsIgnoreCase(dropTable2)) {
			return false;
		}
		
		if (comando.charAt(10)!=' ') {
			return false;
		}
		
		if (comando.indexOf(';')!=longitud-1) {
			return false;
		}

		String nombreTabla="";
		for (int i=11; i<comando.length()-1; i++) {
			nombreTabla=nombreTabla+comando.charAt(i);
		}
		
		for (int i=0; i<nombreTabla.length(); i++) {
			if (nombreTabla.charAt(i)==' ') {
				return false;
			}
		}
		
		dropTable(nombreTabla);
		return true;
	}
	
	

}
