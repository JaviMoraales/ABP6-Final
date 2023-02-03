package ABP6;

import java.io.IOException;
import java.util.Scanner;

public class ABP6 {
	static boolean salir = false;
	public static void main(String[] args) throws IOException {
		Scanner in=new Scanner(System.in);
		while (salir == false) {
			System.out.println("Esperando comando...");
			String comando=in.nextLine();
			comprobarComando(comando);
		}
	}
	public static void comprobarComando(String comando) throws IOException {
		boolean existe=false;
		comando=comando.trim();
		comando=comando.replaceAll("\\s{2,}", " ");
		if(comando.equalsIgnoreCase("salir")){
			salir = true;
			existe = true;
		}
		String palabra=comando.substring(0, 4);
		
		
		if (palabra.equals("crea")) {
			//create table tabla1 (nombre 3(40),apellidos 3(10),edad 1);
			//create table tabla2 (sueldo 2,nombre 3(10),edad 1);
			if (CreateTable.comprobarCreateTable(comando)==false) {
				System.out.println("Error de sintaxis");
			}
			existe=true;
		}
		//drop table tabla1;
		if (palabra.equals("drop")) {
			DropTable.comprobarDropTable(comando);
			existe=true;
		}
		
		if (palabra.equals("inse")) {
			//insert into tabla1 (nombre,apellidos,edad) values (Alvaro,Santos,20);
            //insert into tabla1 (nombre,apellidos,edad) values (Javi,Morales,19);
			//insert into tabla2 (nombre,sueldo,edad) values (alvaro,500.2,20);
			verificarInsert.confirmarInsert(comando);
			
			existe=true;
		}
		//select id,nombre from tabla1;
		//select * from tabla1;
		//select * from tabla1 where nombre=alvaro;
		//select * from tabla1 where edad between 22 and 23;
		if (palabra.equals("sele")) {
			Select.comprobarSelect(comando);
			existe=true;
		}
		
		if (existe==false) {
			System.out.println("Error en la sintaxis.");
		}
	}
}

