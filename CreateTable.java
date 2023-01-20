package ABP6;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CreateTable {
	
	public static int contador=0;
	public static ArrayList<String> nombreTablas=new ArrayList<String>();
	public static ArrayList<String> camposTabla=new ArrayList<String>();
	public static ArrayList<Integer> tipoCampo=new ArrayList<Integer>();
	
	
	public static void createTable() throws IOException {
		
		File ficheroTabla = new File("D:\\Users\\dam223\\Desktop\\nombre_tabla.metadata");
		DataOutputStream dataOS = new  DataOutputStream(new FileOutputStream(ficheroTabla));
	
		//ArrayList<Integer> longitudesCampo = new ArrayList<>();
		
		for(int i=0;i<2;i++) {
		    dataOS.writeUTF(camposTabla.get(i)); 
		    dataOS.writeInt(tipoCampo.get(i));
			//dataOS.writeInt(longitudesCampo.get(i));
		}
		dataOS.close();
		File ficheroBinarioData = new File("D:\\Users\\dam223\\Desktop\\nombre_tabla.data");
		
	}
	
	public static boolean comprobarCreateTable(String comando) throws IOException {
		String create1="create table";
		String create2="";
		for (int i=0; i<11; i++) {
			create2=create2+comando.charAt(i);
		}
		
		if (create1.equalsIgnoreCase(create2)!=true) {
			System.out.println("Error de sintaxis");
			return false;
		}
		if (comprobarNombreNuevaTabla(comando)!=true) {
			System.out.println("El nombre escrito existe.");
			return false;
		}
		if (comprobarParametrosCreateTable(comando)!=true) {
			System.out.println("Los parámetros están mal escritos");
			return false;
		}
		return true;
	}
	
	public static boolean comprobarNombreNuevaTabla(String comando) {
		
		//comprueba que despues del create table hay un espacio
		if (comando.charAt(11)!=' ') {
			return false;
		}
		
		//recoge el nombre de la tabla
		int cont=12;
		String nombre="";
		while (comando.charAt(cont)!=' ') {
			nombre=nombre+comando.charAt(cont);
			cont++;
		}
		//comprueba que no hay nombres repetidos
		for (int i=0; i<nombreTablas.size(); i++) {
			if (nombreTablas.get(i)!=null) {
				if (nombreTablas.get(i).equals(nombre)) {
					return false;
				}
			}
		}
		nombreTablas.add(nombre);
		contador=cont++;
		return true;
	}
	
	public static boolean comprobarParametrosCreateTable(String comando) throws IOException {
		
		//Comprueba que se abre parentesis
		if (comando.charAt(contador)!='(') {
			return false;
		}
		int cont=contador+1;
		if (comando.charAt(cont)==' ') {
			return false;
		}
		
		String nombreCampo="";
		while(comando.charAt(cont)!=')') {
			while (comando.charAt(cont)!=' ') {
				nombreCampo=nombreCampo+comando.charAt(cont);
				cont++;
			}
			camposTabla.add(nombreCampo);
		
		
			cont++;
			if ((comando.charAt(cont)==1)||(comando.charAt(cont)==2)||(comando.charAt(cont)==3)) {
				tipoCampo.add(Character.getNumericValue(comando.charAt(cont)));
			}
			else {
				return false;
			}
			
			cont++;
			
			if ((comando.charAt(cont)==',')||(comando.charAt(cont)==')')) {
				if (comando.charAt(cont)==',') { 
					cont++;
					if (comando.charAt(cont)!=' ') {
						return false;
					}
					else {
						cont++;
					}
				}
			}
			else {
				return false;
			}
		}
		
		createTable();
		camposTabla.clear();
		tipoCampo.clear();
		
		return true;
	}
	
}
