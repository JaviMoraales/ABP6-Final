package AccesoADatos.ABP.ABP6Javi;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Select {

	public static String nombreTabla = "";
	public static String nombreCampo = "";
	public static String valor = "";

	public static int valorNumerico1 = 0;
	public static int valorNumerico2 = 0;

	public static ArrayList<String> nombreCampos;

	// Sintaxis 1
	public static void selectAllFromNombreTabla(String nombreTabla) throws IOException {
		int numeroCampos = editarYComprobarTablas.recogerNumeroCampos(nombreTabla);
		File ficheroBinarioData = new File(nombreTabla + ".data");
		FileInputStream fis = null;
		DataInputStream dis = null;
		try {
			fis = new FileInputStream(ficheroBinarioData);
			dis = new DataInputStream(fis);
			while (true) {
				System.out.println(dis.readInt());
				for (int i = 0; i < numeroCampos; i++) {
					System.out.println(dis.readUTF());
					System.out.println(dis.readUTF());
				}
			}
		} catch (EOFException e) {
			System.out.println("se ha acabado el archivo");
		} finally {
			fis.close();
			dis.close();
		}
	}

	// Sintaxis 2
	public static void selectNombresCamposFrom(String nombreTabla, ArrayList<String> nombresCampos) throws IOException {
		int numeroCampos = editarYComprobarTablas.recogerNumeroCampos(nombreTabla); 
		File ficheroBinarioData = new File(nombreTabla + ".data");
		FileInputStream fis = null;
		DataInputStream dis = null;
		try {
			fis = new FileInputStream(ficheroBinarioData);
			dis = new DataInputStream(fis);
			while (dis.available()!=-1) {
				
			}
		} catch (EOFException e) {
			System.out.println("se ha acabado el archivo");
		} finally {
			fis.close();
			dis.close();
		}
	}

	// Sintaxis 3
	public static void selectAllFromTabla_Valor() {

	}

	// Sintaxis 4
	public static void selectAllFromTabla_Between() {

	}

	public static boolean comprobarSelect(String comando) throws IOException {

		nombreTabla = "";
		nombreCampo = "";
		valor = "";

		valorNumerico1 = 0;
		valorNumerico2 = 0;

		comando = comando.trim();
		comando = comando.replaceAll("\\s{2,}", " ");

		if (comando.charAt(comando.length() - 1) != ';') {
			return false;
		}

		String select1 = "select * from ";
		String select2 = comando.substring(0, 14);

		if (select1.equalsIgnoreCase(select2)) {
			if (!comprobarSelectAll(comando)) {
				return false;
			} else {
				return true;
			}
		} else {
			String select3 = "select ";
			String select4 = comando.substring(0, 7);

			if (select3.equalsIgnoreCase(select4)) {
				if (comprobarSelectNombreCamposFrom(comando) != false) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	public static boolean comprobarSelectAll(String comando) throws IOException {

		int cont = 14;
		while ((comando.charAt(cont) != ' ') && (comando.charAt(cont) != ';')) {
			nombreTabla = nombreTabla + comando.charAt(cont);
			cont++;
		}

		if ((comando.charAt(cont) == ';') || (comando.charAt(cont) + 1 == ';')) {
			selectAllFromNombreTabla(nombreTabla);
			return true;
		}

		String where1 = " where ";
		String where2 = comando.substring(cont, cont + 7);

		if (!where1.equalsIgnoreCase(where2)) {
			return false;
		}

		cont = cont + 7;

		while ((comando.charAt(cont) != '=') && (comando.charAt(cont) != ' ')) {
			nombreCampo = nombreCampo + comando.charAt(cont);
			cont++;
		}

		if ((comando.charAt(cont) == '=') || (comando.charAt(cont) + 1 == '=')) {
			cont = comando.indexOf('=') + 1;

			while ((comando.charAt(cont) != ' ') && (comando.charAt(cont) != ';')) {
				valor = valor + comando.charAt(cont);
				cont++;
			}

			if ((comando.charAt(cont) == ';') && (cont == comando.length() - 1)) {
				selectAllFromTabla_Valor();
				return true;
			}

			if ((comando.charAt(cont) + 1 == ';') && (cont + 1 == comando.length() - 1)) {
				selectAllFromTabla_Valor();
				return true;
			}
		}

		String between1 = " between ";
		String between2 = comando.substring(cont, cont + 9);

		if (!between1.equalsIgnoreCase(between2)) {
			return false;
		}

		cont = cont + 9;
		String valorNum1 = "";
		String valorNum2 = "";

		while (comando.charAt(cont) != ' ') {
			valorNum1 = valorNum1 + comando.charAt(cont);
			cont++;
		}

		String and1 = " and ";
		String and2 = comando.substring(cont, cont + 5);

		if (!and1.equalsIgnoreCase(and2)) {
			return false;
		}

		cont = cont + 5;

		while ((comando.charAt(cont) != ' ') && (comando.charAt(cont) != ';')) {
			valorNum2 = valorNum2 + comando.charAt(cont);
			cont++;
		}

		try {
			valorNumerico1 = Integer.valueOf(valorNum1);
			valorNumerico2 = Integer.valueOf(valorNum2);
		} catch (Exception e) {
			System.out.println("Error. Debes introducir valores numéricos después del 'between'.");
			return false;
		}

		if ((comando.charAt(cont) == ';') && (cont == comando.length() - 1)) {
			selectAllFromTabla_Between();
			return true;
		}

		if ((comando.charAt(cont + 1) == ';') && (cont + 1 == comando.length() - 1)) {
			selectAllFromTabla_Between();
			return true;
		}

		return false;
	}

	public static boolean comprobarSelectNombreCamposFrom(String comando) throws IOException {
		nombreTabla = "";
		nombreCampos = new ArrayList<String>();
		nombreCampos.clear();
		int cont = 8;

		try {
			if ((comando.charAt(comando.lastIndexOf("from") - 1) != ' ')
					&& (comando.charAt(comando.lastIndexOf("from") + 4) != ' ')) {
				return false;
			}
			if (comando.charAt(comando.lastIndexOf("from") - 2) == ',') {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		String subcomando = comando.substring(7, comando.length());
		String[] hastaFrom = new String[100];

		try {
			hastaFrom = subcomando.split(" from ");
		} catch (Exception e) {
			return false;
		}

		String[] nombresDeTablas = hastaFrom[0].split(",");

		for (int i = 0; i < nombresDeTablas.length; i++) {
			String nombre = nombresDeTablas[i].trim();
			for (int k = 0; k < nombre.length(); k++) {
				if (nombre.charAt(k) == ' ') {
					return false;
				}
			}

			if (nombresDeTablas != null) {
				for (int j = 0; j < nombreCampos.size(); j++) {
					if (nombresDeTablas[i].trim().equalsIgnoreCase(nombreCampos.get(j))) {
						System.out.println("Nombres de campos repetidos");
						return false;
					}
				}
			}

			nombreCampos.add(nombresDeTablas[i].trim());
		}

		cont = comando.lastIndexOf("from") + 5;

		while ((comando.charAt(cont) != ' ') && (comando.charAt(cont) != ';')) {
			nombreTabla = nombreTabla + comando.charAt(cont);
			cont++;
		}

		if ((comando.charAt(cont) == ';') && (cont == comando.length() - 1)) {
			selectNombresCamposFrom(nombreTabla, nombreCampos);
			return true;
		}

		if ((comando.charAt(cont + 1) == ';') && (cont + 1 == comando.length() - 1)) {
			selectNombresCamposFrom(nombreTabla, nombreCampos);
			return true;
		}

		return false;
	}

}