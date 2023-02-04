package AccesoADatos.ABP.ABP6;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class verificarInsert {

    public static int numeroCaracteres = 0;

    public static boolean confirmarInsert(String frase) throws IOException {
        frase = frase.toLowerCase();
        File archivoNombresTablas = new File("nombresTablas.txt");
        frase = frase.replaceAll("\\s{2,}", " ");
        frase = frase.trim();
        String comando = frase.substring(0, 11);
        int primerParentesisAbierto = frase.indexOf('(');
        int primerParentesisCerrado = frase.indexOf(')');
        int segundoParentesisAbierto = frase.lastIndexOf('(');
        int segundoParentesisCerrado = frase.lastIndexOf(')');
        String stringValues = frase.substring(primerParentesisCerrado + 1, segundoParentesisAbierto);
        String nombresCampos = null;
        String valoresCampos = null;
        String nombreTabla = frase.substring(12, primerParentesisAbierto - 1);
        boolean estaBienEscrito = false;
        if (comprobarComando(comando)) {
            if (primerParentesisAbierto != segundoParentesisAbierto
                    && primerParentesisCerrado != segundoParentesisCerrado) {
                if (primerParentesisAbierto != -1 && primerParentesisCerrado != -1 && segundoParentesisAbierto != -1
                        && segundoParentesisCerrado != -1) {
                    nombresCampos = separarParentesis(frase, primerParentesisAbierto + 1, primerParentesisCerrado - 1);
                    valoresCampos = separarParentesis(frase, segundoParentesisAbierto + 1,
                            segundoParentesisCerrado - 1);
                    if (frase.charAt(primerParentesisAbierto - 1) == ' '
                            && frase.charAt(primerParentesisCerrado + 1) == ' '
                            && frase.charAt(segundoParentesisAbierto - 1) == ' '
                            && frase.charAt(segundoParentesisCerrado + 1) == ';') {
                        if (comprobarQueNoHayaEspacio(nombresCampos) && comprobarQueNoHayaEspacio(valoresCampos)) {
                            if (comprobarQueNoHayaNadaDeMas(nombreTabla, 0)) {
                                if (comprobarQueNoHayaNadaDeMas(stringValues, 2)) {
                                    try {
                                        if (editarYComprobarTablas.existeNombreTabla(archivoNombresTablas,
                                                nombreTabla)) {
                                            if (comprobarQueAcabeBien(frase)) {
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
        if (estaBienEscrito) {
            // escribirCreateTable();
            if (comprobarNombreCampos(agregarCamposTabla(nombreTabla + ".metadata"), nombresCampos)) {
                String[] separados = valoresCampos.split(",");
                String[] nombresCampos2 = nombresCampos.split(",");
                for (int i = 0; i < separados.length; i++) {
                    if (comprobarLongitudDeCampo(nombreTabla, nombresCampos2[i], separados[i]) == false) {
                        System.out.println("Longitud de un campo erronea");
                        return false;
                    }
                }
                if (comprobarTipoCampo(nombreTabla, valoresCampos,nombresCampos)) {
                    insertEnTabla.insertar(guardarCampoOValor(nombresCampos, valoresCampos), nombreTabla + ".data");
                    return true;
                }
                return false;

            } else {
                System.out.println("Error al introducir los datos");
                return false;
            }
        } else
            System.out.println("El comando está mal");
        return false;
    }

    public static boolean comprobarTipoCampo(String nombreTabla, String valores, String nombresCampos) {
        File ficheroTabla = new File(nombreTabla + ".metadata");
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(ficheroTabla));
            String linea = "";
            String[] valoresSeparados = valores.split(",");
            String[] nombresSeparados = nombresCampos.split(",");
            while ((linea = bfr.readLine()) != null) {
                String[] separados = linea.split(",");
                for(int i=0;i<nombresSeparados.length;i++){
                    for(int j=0;j<separados.length;j++){
                        if (separados[1].equals("1")) {
                            if (nombresSeparados[j].equals(separados[0])) {
                                int campoParseado = 0;
                                try {
                                    campoParseado = Integer.parseInt(valoresSeparados[j]);
                                } catch (Exception e) {
                                    System.out.println("No se puede parsear");
                                    return false;
                                }
                            }
                        }
                        if (separados[1].equals("2")) {
                            if (nombresSeparados[j].equals(separados[0])) {
                                double campoParseado = 0;
                                try {
                                    campoParseado = Double.parseDouble(valoresSeparados[j]);
                                } catch (Exception e) {
                                    System.out.println("No se puede parsear");
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean comprobarNombreCampos(ArrayList<String> arrayList, String nombresCampos) {
        String[] arrayNombres = nombresCampos.split(",");
        int camposQueCoinciden = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayNombres.length; j++) {
                if (arrayList.get(i).equalsIgnoreCase(arrayNombres[j])) {
                    camposQueCoinciden++;
                }
            }
        }
        if (camposQueCoinciden == arrayNombres.length) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<String> agregarCamposTabla(String nombreTabla) throws IOException {
        FileReader fr = new FileReader(nombreTabla);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<String> arrayList = new ArrayList<>();
        String campo = "";
        try {
            while (campo != null) {
                campo = bfr.readLine();
                if (campo != null) {
                    String nombreCampo = campo.substring(0, campo.indexOf(","));
                    arrayList.add(nombreCampo);
                }
            }
        } catch (EOFException e) {
            bfr.close();
        }
        bfr.close();
        return arrayList;
    }

    public static HashMap<String, String> guardarCampoOValor(String nombres, String valores) {
        HashMap<String, String> nombresYValoresCampos = new HashMap<>();
        String[] arrayNombres = nombres.split(",");
        String[] arrayValores = valores.split(",");
        for (int i = 0; i < arrayNombres.length; i++) {
            if (!arrayValores.equals("null")) {
                nombresYValoresCampos.put(arrayNombres[i], arrayValores[i]);
            }
        }
        return nombresYValoresCampos;
    }

    public static boolean comprobarValorOCampo(String valorOCampo) {
        if (comprobarQueNoHayaEspacio(valorOCampo)) {
            if (comprobarQueNoHayaNadaDeMas(valorOCampo, 2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean comprobarQueNoHayaEspacio(String string) {
        if (string.contains(" ")) {
            return false;
        }
        return true;
    }

    public static boolean comprobarComando(String comando) {
        if (comando.equals("insert into")) {
            return true;
        }
        return false;
    }

    public static String separarParentesis(String frase, int posParentesisAbierto, int posParentesisCerrado) {
        String nombreCampos = frase.substring(posParentesisAbierto, posParentesisCerrado + 1);
        nombreCampos = nombreCampos.replace(" ", "");
        return nombreCampos;
    }

    public static boolean comprobarQueNoHayaNadaDeMas(String frase, int numeroPermitidoDeEspacios) {
        Pattern pattern = Pattern.compile("[ ]"); //
        Matcher matcher = pattern.matcher(frase); // cadena a buscar en el patrón
        int matches = 0;
        while (matcher.find()) { // ver matcher.matches()
            matches++;
        }
        if (matches == numeroPermitidoDeEspacios) {
            return true;
        } else
            return false;
    }

    public static boolean comprobarQueAcabeBien(String frase) {
        Pattern pattern = null;
        Matcher matcher = null;
        // termina por );
        // . -> es cualquier caracter excepto el salto de linea
        // * -> repetido 0 o más veces
        pattern = Pattern.compile(".*\\);");
        matcher = pattern.matcher(frase);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    public static boolean comprobarLongitudDeCampo(String nombreTabla, String campo, String contenido)
            throws IOException {
        File ficheroTabla = new File(nombreTabla + ".metadata");
        BufferedReader bfr = new BufferedReader(new FileReader(ficheroTabla));
        // (DataInputStream leer=new DataInputStream(new FileInputStream(ficheroTabla));
        try {
            String linea = "";
            while ((linea = bfr.readLine()) != null) {
                String[] opciones = linea.split(",");
                if (opciones.length == 3) {
                    if (opciones[0].equals(campo)) {
                        String parsear = opciones[2];
                        int longitudCampo = Integer.parseInt(parsear);
                        int lon = contenido.length();
                        if (lon > longitudCampo) {
                            bfr.close();
                            return false;
                        } else {
                            bfr.close();
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            bfr.close();
        }
        bfr.close();
        return true;
    }
}