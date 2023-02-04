package AccesoADatos.ABP.ABP6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class insertEnTabla {
    
    public static void insertar(HashMap<String, String> mapa, String nombreTabla) throws IOException {
        FileOutputStream fos = null;
        DataOutputStream salida = null;
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {
            fis = new FileInputStream(nombreTabla);
            dis = new DataInputStream(fis);
            fos = new FileOutputStream(nombreTabla, true);
            salida = new DataOutputStream(fos);
            if(dis.read()!=-1){
                if(dis.readInt()!=-1){
                    int nuevoNumero = recogerNumero(nombreTabla);
                    nuevoNumero++;
                    salida.writeInt(nuevoNumero);
                }
            }
            else{
                salida.writeInt(1);
            }
            for (Entry<String, String> entry : mapa.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                salida.writeUTF(key);
                salida.writeUTF(value);
            }
        }catch(IOException IOE){
            IOE.printStackTrace();
            salida.close();
            dis.close();
            fis.close();
            fos.close();
        }
        fos.close();
        salida.close();
    }
    public static int recogerNumero(String nombreTabla) throws IOException{
        int numeroCampos = editarYComprobarTablas.recogerNumeroCampos(nombreTabla);
        FileInputStream fis = null;
        DataInputStream dis = null;
        int numeroID = 0;
        int numeroAux = 0;
        try {
            fis = new FileInputStream(nombreTabla);
            dis = new DataInputStream(fis);
            while (true) {
                numeroAux = dis.readInt();
                if(numeroAux==-1){

                }
                else{
                    numeroID = numeroAux;
                }
                for(int i=0;i<numeroCampos;i++){
                    dis.readUTF();
                    dis.readUTF();
                }
                
            }
        } catch (EOFException e) {
           System.out.println("se ha acabado el archivo");
        }
        finally{
            fis.close();
            dis.close();
        }
        return numeroID;
    }
}