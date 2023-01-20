package ABP6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ABP6 {
	
	

	public static void main(String[] args) throws IOException {
		Scanner in=new Scanner(System.in);
		String comando="";
		while(true) {
			System.out.println("Esperando comando...");
			comando=in.nextLine();
		
			comprobarComando(comando);
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
