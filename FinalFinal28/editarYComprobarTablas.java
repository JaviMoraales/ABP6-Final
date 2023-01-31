package AccesoADatos.ABP.ABP6Javi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class editarYComprobarTablas {
    //Archivo de texto
    public static void creacionTablaMetadata(String nombreTabla,ArrayList<Integer> longitudesCampo,
    ArrayList<Integer> tiposCampos,ArrayList<String> nombresCampos) throws IOException {
        // C:\\Users\\Usuario\\Desktop\\
        File ficheroTabla = new File(nombreTabla+".metadata");
        FileWriter fw = new FileWriter(ficheroTabla);        
        BufferedWriter bfw = new BufferedWriter(fw);
        boolean todoBien = true;
        for (int i = 0; i < nombresCampos.size(); i++) {
            if (!nombresCampos.get(i).equalsIgnoreCase("id")) {
                if (tiposCampos.get(i) == 3) {
                    if (longitudesCampo.get(i) <= 255 && longitudesCampo.get(i) >= 1) {

                    } else {
                        System.out.println("La longitud tiene que ser entre 1 y 255");
                        todoBien = false;
                    }
                }
            } else {
                System.out.println("Ningun campo se puede llamar id");
                todoBien = false;
            }
            if (todoBien) {
                if (tiposCampos.get(i)==3) {
                    bfw.write(nombresCampos.get(i)+","+tiposCampos.get(i)+","+longitudesCampo.get(i)+"\n");
                }
                else{
                    bfw.write(nombresCampos.get(i)+","+tiposCampos.get(i)+"\n");
                }
            }
        }
        bfw.close();
        fw.close();
    }
    public static int recogerNumeroCampos(String nombreTabla) throws IOException{
        String nombreDeLaTablaVerdadera = null;
        if(nombreTabla.contains(".data")){
            nombreDeLaTablaVerdadera = nombreTabla.substring(0,nombreTabla.indexOf('.'));
        }
        else{
            nombreDeLaTablaVerdadera = nombreTabla;
        }
        File archivoNombresTablas = new File (nombreDeLaTablaVerdadera+".metadata");
        FileReader fr = new FileReader(archivoNombresTablas);
        BufferedReader bfr = new BufferedReader(fr);
        String campo = "";
        int numeroCampos = 0;
        try{
            while((campo = bfr.readLine())!=null){
                numeroCampos++;
            }    
        }catch(Exception e){
            e.printStackTrace();
            bfr.close();
        }
        bfr.close();
        return numeroCampos;
    }
    //Archivo Binario
    public static void creacionTablaData(String nombreTabla){
        File ficheroBinarioData = new File(nombreTabla+".data");
		try {
            ficheroBinarioData.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void escribirNombreTablaEnArchivo(String nombreTabla,File fichero){
        BufferedWriter salida;
        try {
            salida = new BufferedWriter(new FileWriter(fichero,true));
            salida.write(nombreTabla+"\n");
            salida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean existeNombreTabla (File archivo,String nombreTabla) throws IOException{
        BufferedReader bfr = new BufferedReader(new FileReader(archivo));
        String linea = "";
        while((linea=bfr.readLine())!=null){
            if(linea.equals(nombreTabla)){
                bfr.close();
                return true;
            }
        }
        bfr.close();
        return false;
    }
    public static void dropTable(String nombreTabla){
        try {
            File nombreTablas = new File("nombresTablas.txt");
            if(existeNombreTabla(nombreTablas, nombreTabla)){
                File archivoParaSobreescribir = new File(nombreTablas.getAbsolutePath()+".tmp");
                PrintWriter pw = new PrintWriter(new FileWriter(archivoParaSobreescribir));
                BufferedReader bfr = new BufferedReader(new FileReader(nombreTablas));
                String linea = "";
                while((linea=bfr.readLine())!=null){
                    if(!linea.trim().equals(nombreTabla)){
                        pw.println(linea);
                    }
                }
                nombreTablas.delete();
                pw.close();
                bfr.close();
                nombreTablas.delete();
                archivoParaSobreescribir.renameTo(nombreTablas);
                File tablaMetadata = new File(nombreTabla+".metadata");
                tablaMetadata.delete();
                File tablaData = new File(nombreTabla+".data");
                tablaData.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}