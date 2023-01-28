package AccesoADatos.ABP.ABP6Javi;
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
			//create table tabla1 (campo1 3(40));
			CreateTable.comprobarCreateTable(comando);
			existe=true;
		}
		//drop table tabla1;
		if (palabra.equals("drop")) {
			DropTable.comprobarDropTable(comando);
			existe=true;
		}
		
		if (palabra.equals("inse")) {
             //insert into tabla1 (campo1) values (alvaro);
			verificarInsert.confirmarInsert(comando);
			existe=true;
		}
		
		if (palabra.equals("sele")) {
			Select.comprobarSelect(comando);
			existe=true;
		}
		
		if (existe==false) {
			System.out.println("Error en la sintaxis.");
		}
	}
}

