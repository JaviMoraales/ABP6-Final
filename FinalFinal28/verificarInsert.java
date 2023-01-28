package AccesoADatos.ABP.ABP6Javi;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class verificarInsert{
    //Insert into tabla1 (campo1,campo2) values (alvaro);
    public static boolean confirmarInsert(String frase) throws IOException{
        frase = frase.toLowerCase();
        File archivoNombresTablas = new File("C:\\Users\\Usuario\\Desktop\\nombresTablas.txt");
        frase = frase.replaceAll("\\s{2,}", " ");                                      
        frase = frase.trim();
        String comando = frase.substring(0,11);
        int primerParentesisAbierto = frase.indexOf('(');
        int primerParentesisCerrado = frase.indexOf(')');
        int segundoParentesisAbierto = frase.lastIndexOf('(');
        int segundoParentesisCerrado = frase.lastIndexOf(')');
        String stringValues = frase.substring(primerParentesisCerrado+1, segundoParentesisAbierto);
        String nombresCampos = null;
        String valoresCampos = null;
        String nombreTabla = frase.substring(12, primerParentesisAbierto-1);
        boolean estaBienEscrito = false;
        if(comprobarComando(comando)){
            if(primerParentesisAbierto!=segundoParentesisAbierto && primerParentesisCerrado!=segundoParentesisCerrado){
                if(primerParentesisAbierto!=-1 && primerParentesisCerrado!=-1 && segundoParentesisAbierto!=-1 && segundoParentesisCerrado!=-1){
                    nombresCampos = separarParentesis(frase, primerParentesisAbierto+1, primerParentesisCerrado-1);
                    valoresCampos = separarParentesis(frase, segundoParentesisAbierto+1, segundoParentesisCerrado-1);
                    if(frase.charAt(primerParentesisAbierto-1)==' ' && frase.charAt(primerParentesisCerrado+1)==' ' 
                        && frase.charAt(segundoParentesisAbierto-1)==' ' && frase.charAt(segundoParentesisCerrado+1)==';'){
                        if( comprobarQueNoHayaEspacio(nombresCampos) &&  comprobarQueNoHayaEspacio(valoresCampos)){
                            if(comprobarQueNoHayaNadaDeMas(nombreTabla,0)){
                                if(comprobarQueNoHayaNadaDeMas(stringValues, 2)){
                                    try {
                                        if(editarYComprobarTablas.existeNombreTabla(archivoNombresTablas, nombreTabla)){
                                            if(comprobarQueAcabeBien(frase)){
                                                estaBienEscrito = true;
                                            }
                                        }
                                    } catch (IOException e) {
                                        System.out.println("Error");
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            } 
        }
        if(estaBienEscrito){
            System.out.println("El comando está bien");
            //escribirCreateTable();
            if(comprobarNombreCampos(agregarCamposTabla("C:\\Users\\Usuario\\Desktop\\"+nombreTabla+".metadata"), nombresCampos)){
                insertEnTabla.insertar(guardarCampoOValor(nombresCampos,valoresCampos),"C:\\Users\\Usuario\\Desktop\\"+nombreTabla+".data");
                return true;
            }
            else{
                System.out.println("Mal");
                return false;
            }
        }
        else
            System.out.println("Mal");
            return false;
    }

    public static boolean comprobarNombreCampos(ArrayList<String> arrayList,String nombresCampos){
        String[] arrayNombres = nombresCampos.split(",");
        int camposQueCoinciden = 0 ;
        for(int i=0;i<arrayList.size();i++){
            for(int j=0;j<arrayNombres.length;j++){
                if(arrayList.get(i).equalsIgnoreCase(arrayNombres[j])){
                    camposQueCoinciden++;
                }
            }
        }
        if(camposQueCoinciden==arrayNombres.length){
            return true;
        }
        else{
            return false;
        }
    }   

    public static ArrayList<String> agregarCamposTabla(String nombreTabla) throws IOException{
        FileReader fr = new FileReader(nombreTabla);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<String> arrayList = new ArrayList<>();
        String campo = "";
        try {
            while(campo!=null){
                campo = bfr.readLine();
                if(campo!=null){
                    String nombreCampo = campo.substring(0,campo.indexOf(" "));
                    arrayList.add(nombreCampo);
                }
            }
        } catch (EOFException e) {
           System.out.println("se ha acabado el archivo");
           bfr.close();
        }
        bfr.close();
        return arrayList;
    }
    
    public static HashMap<String,String> guardarCampoOValor(String nombres,String valores){
        HashMap<String,String> nombresYValoresCampos = new HashMap<>();
        String[] arrayNombres = nombres.split(",");
        String [] arrayValores = valores.split(",");
        for(int i=0;i<arrayNombres.length;i++){
            if(!arrayValores.equals("null")){
                nombresYValoresCampos.put(arrayNombres[i],arrayValores [i]);
            }
        }
        return nombresYValoresCampos;
    }
    
    public static boolean comprobarValorOCampo(String valorOCampo){
        if(comprobarQueNoHayaEspacio(valorOCampo)){
            if(comprobarQueNoHayaNadaDeMas(valorOCampo,2)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean comprobarQueNoHayaEspacio(String string){
        if(string.contains(" ")){
            return false;
        }
        return true;
    }
    
    public static boolean comprobarComando(String comando){
        if(comando.equals("insert into")){
            return true;
        }
        return false;
    }
    
    public static String separarParentesis(String frase,int posParentesisAbierto,int posParentesisCerrado ){
        String nombreCampos = frase.substring(posParentesisAbierto, posParentesisCerrado+1);
        nombreCampos =  nombreCampos.replace(" ", "");
        return nombreCampos;
    }
    
    public static boolean comprobarQueNoHayaNadaDeMas(String frase,int numeroPermitidoDeEspacios){
        Pattern pattern = Pattern.compile("[ ]"); // 
		Matcher matcher = pattern.matcher(frase); // cadena a buscar en el patrón
		int matches = 0;
		while (matcher.find()) { // ver matcher.matches()
			matches++;
		}
		if(matches == numeroPermitidoDeEspacios){
            return true;
        }
        else
            return false;
    }

    public static boolean comprobarQueAcabeBien(String frase){
        Pattern pattern  = null;
        Matcher matcher = null;
        //termina por ); 
		// . -> es cualquier caracter excepto el salto de linea
        // * -> repetido 0 o más veces
		pattern = Pattern.compile(".*\\);"); 
		matcher = pattern.matcher(frase);
		if (matcher.matches())
			return true;
		else
			return false;
    }
}