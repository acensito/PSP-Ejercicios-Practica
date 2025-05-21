
package proyectobasehotel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Felipe R.
 */
public class ArchiveHelper {
    private static final String ENCRIPTACION = "";
    private static final String CLAVE_AES = "1234567890123456";
    private static final SecretKeySpec CLAVE = new SecretKeySpec(CLAVE_AES.getBytes(), ENCRIPTACION);
    private static final int ENCRIPTAR = Cipher.ENCRYPT_MODE;
    private static final int DESENCRIPTAR = Cipher.DECRYPT_MODE;
    
    //comprueba si el nombre de archivo pasado por parametro existe
    public static synchronized void verificarExisteArchivo(String nombreArchivo) {
        try {
            if (new File(nombreArchivo+".txt").createNewFile()) {
                System.out.println("Archivo "+nombreArchivo+".txt creado.");
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    //si se usan dentro de un metodo sincronizado no tienen por que ser sincronizados
    public static synchronized void escribirArchivo(byte[] datos, String nombreArchivo) {
        Path ruta = Paths.get(nombreArchivo + ".txt");
        try {
            //te ahorra comprobar si existe el archivo 
            //.APPEND sirve para añadir al final del archivo
            Files.write(ruta,datos, StandardOpenOption.CREATE, 
                    StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Archivo actualizado correctamente");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public static synchronized byte[] leerArchivo(String archivo) {
        try {
            //devolvemos los bytes del archivo
            return Files.readAllBytes(Paths.get((archivo+".txt")));
        } catch (IOException e) {
            System.err.println("No se pudo leer el archivo: " + e.getMessage());
            //devuelve un array vacio si no es posible leer el archivo
            return new byte[0]; 
        }
    }
    
    //cifrar o descifrar archivo
    public static byte[] procesarDatos(byte[] datos, int modo) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRIPTACION);
            cipher.init(modo, CLAVE);
            return cipher.doFinal(datos);
        } catch (GeneralSecurityException ex) {
            System.err.println("Error al procesar: " + ex.getMessage());
            return null;
        }
    }
    
    public static synchronized String descifrarDatos(String archivo) {
        byte[] archivoBruto = leerArchivo(archivo);
        if (archivoBruto.length == 0) {
            return ""; 
        }
        return new String (procesarDatos(archivoBruto, DESENCRIPTAR), StandardCharsets.UTF_8);
    }
    
    public static synchronized void guardarDatos(String archivo, String datos) {
        int datosDesEntero = Integer.parseInt(descifrarDatos(archivo));
        int datosEntero = Integer.parseInt(datos);
        String resultado = String.valueOf(datosDesEntero+datosEntero);
        
        byte[] resultadoCifrado = procesarDatos(resultado.getBytes(StandardCharsets.UTF_8), ENCRIPTAR);
        
        escribirArchivo(resultadoCifrado, archivo);
    }
}
