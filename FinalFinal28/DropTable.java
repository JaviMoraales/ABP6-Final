package AccesoADatos.ABP.ABP6Javi;


public class DropTable {
	
	public static boolean comprobarDropTable(String comando) {
		
		comando=comando.trim();
		comando=comando.replaceAll("\\s{2,}", " ");
		
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
		int cont=11;
		
		while ((comando.charAt(cont)!=' ')&&(comando.charAt(cont)!=';')) {
			nombreTabla=nombreTabla+comando.charAt(cont);
			cont++;
		}
		
		if (comando.charAt(cont)==' ') {
			if (comando.charAt(cont+1)!=';') {
				return false;
			}
		}
		
		nombreTabla=nombreTabla.toLowerCase();
		
		editarYComprobarTablas.dropTable(nombreTabla);
		return true;
	}
}
