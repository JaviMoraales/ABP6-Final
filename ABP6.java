package ABP6;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ABP6 {
	static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		ArrayList<String> nombresCampos = new ArrayList<>();
		nombresCampos.add("campo1");
		ArrayList<Integer> tiposCampos = new ArrayList<>();
		tiposCampos.add(3);
		ArrayList<Integer> longitudesCampos = new ArrayList<>();
		longitudesCampos.add(20);
		System.out.println("Introduce el nombre de la tabla");
		String nombreTabla = in.nextLine();
		editarYComprobarTablas.creacionTablaData(nombreTabla);
		File archivoNombresTablas = null;
		try {
			editarYComprobarTablas.creacionTablaMetadata(nombreTabla, longitudesCampos, 
			tiposCampos, nombresCampos);
			archivoNombresTablas = new File( "D:\\Users\\dam223\\Desktop\\nombresTablas.txt");
			editarYComprobarTablas.escribirNombreTablaEnArchivo(nombreTabla, archivoNombresTablas);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String linea = "insert into tabla1 (campo1,campo2) values (valor1, valor2);";
		try {
			if(verificarInsert.confirmarInsert(linea,archivoNombresTablas)){
				System.out.println("Datos añadidos");
			}
			else{
				System.out.println("Error. No se han podido añadir los datos");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void comprobarComando(String comando) throws IOException {
		
		String palabra=comando.substring(0, 3);
		boolean existe=false;
		
		if (palabra.equals("crea")) {
			
			CreateTable.comprobarCreateTable(comando);
			existe=true;
		}
		if (palabra.equals("drop")) {
			DropTable.comprobarDropTable(comando);
			existe=true;
		}
		
		if (palabra.equals("inse")) {
			
			existe=true;
		}
		
		if (palabra.equals("sele")) {
			
			existe=true;
		}
		
		if (existe==false) {
			System.out.println("Error en la sintaxis.");
		}
	}
	

}