package ABP6;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CreateTable {
	
	public static int contador=0;
	public static ArrayList<String> camposTabla=new ArrayList<String>();
	public static ArrayList<Integer> tipoCampo=new ArrayList<Integer>();
	public static ArrayList<Integer> longitudesCampo = new ArrayList<>();
	public static String nombreTabla;


	public static boolean comprobarCreateTable(String comando) throws IOException {
		
		camposTabla.clear();
		tipoCampo.clear();
		longitudesCampo.clear();
		nombreTabla="";
		
		comando = comando.replaceAll("\\s{2,}", " ");                                      
		comando = comando.trim();
		
		String createTable1="create table";
		String createTable2=comando.substring(0, 12);
		
		if (comando.charAt(comando.length()-1)!=';') {
			return false;
		}
		
		if (!createTable1.equalsIgnoreCase(createTable2)) {
			return false;
		}
		
		if (comando.charAt(12)!=' ') {
			return false;
		}
		
		int cont=13;
		
		while ((comando.charAt(cont)!=' ')&&(comando.charAt(cont)!='(')) {
			nombreTabla=nombreTabla+comando.charAt(cont);
			cont++;
		}
		
		nombreTabla.toLowerCase();
				
		if (comando.charAt(cont)==' ') {
			cont++;
			if (comando.charAt(cont)!='(') {
				return false;
			}
		}
		
		int parentesisAbierto=comando.indexOf('(');
		int parentesisCerrado=comando.lastIndexOf(')');
		String parentesis=comando.substring(parentesisAbierto+1, parentesisCerrado);
		String[] valoresSeparados=parentesis.split(",");
		
		for (int i=0; i<valoresSeparados.length; i++) {
			valoresSeparados[i]=valoresSeparados[i].trim();
		}
		
		for (int i=0; i<valoresSeparados.length; i++) {
			cont=0;
			String nombreCampo="";
			String tiposCampo="";
			int tipoCampo2=0;
			while ((valoresSeparados[i].charAt(cont)!=' ')) {
				nombreCampo=nombreCampo+valoresSeparados[i].charAt(cont);
				
				try {
					cont++;
					valoresSeparados[i].charAt(cont);
				} catch(Exception e) {
					return false;
				}
			}
			cont++;
			
			if (valoresSeparados[i].charAt(cont)==' ') {
				return false;
			}
			
			tiposCampo=Character.toString(valoresSeparados[i].charAt(cont));
			
			try {
				 tipoCampo2=Integer.parseInt(tiposCampo); 
			} catch(Exception e) {
				return false;
			}
			
			if (tipoCampo2==3) {
				if (tipoCampo2!=3) {
					return false;
				}
				
				if (valoresSeparados[i].indexOf('(')!=valoresSeparados[i].lastIndexOf('(')) {
					return false;
				}
				
				if (valoresSeparados[i].indexOf(')')!=valoresSeparados[i].lastIndexOf(')')) {
					return false;
				}
 		
				
				int parentesisAbierto1=valoresSeparados[i].indexOf('(');
				int parentesisCerrado1=valoresSeparados[i].indexOf(')');
				
				if ((parentesisAbierto1==-1)||(parentesisCerrado1==-1)) {
					return false;
				}
				
				String longitudDelCampo=valoresSeparados[i].substring(parentesisAbierto1+1, parentesisCerrado1);
				
				int longitudDelCampo1=0;
				try {
					longitudDelCampo1=Integer.parseInt(longitudDelCampo);
				} catch(Exception e) {
					return false;
				}
				
				longitudesCampo.add(longitudDelCampo1);
			}
			if(tipoCampo2 == 1){
				longitudesCampo.add(0);
			}
			if(tipoCampo2==2){
				longitudesCampo.add(0);
			}
			
			for (int k=0; k<camposTabla.size(); k++) {
				if (camposTabla.get(k).equals(nombreCampo)) {
					return false;
				}
			}
			
 			camposTabla.add(nombreCampo);
			tipoCampo.add(tipoCampo2);
		}
		
		//C:\\Users\\Usuario\\Desktop\\
				File archivoNombresTablas = new File( "nombresTablas.txt");
				if(archivoNombresTablas.exists()) {
					if (editarYComprobarTablas.existeNombreTabla(archivoNombresTablas,nombreTabla)==true) {
						System.out.println("El nombre de la tabla ya existe");
						return false;
					}
					else {
						editarYComprobarTablas.escribirNombreTablaEnArchivo(nombreTabla, archivoNombresTablas);
					}
				}
				else {
					editarYComprobarTablas.escribirNombreTablaEnArchivo(nombreTabla, archivoNombresTablas);
				}

		
		editarYComprobarTablas.creacionTablaMetadata(nombreTabla, longitudesCampo, tipoCampo, camposTabla);
		editarYComprobarTablas.creacionTablaData(nombreTabla);
		return true;
	}
}