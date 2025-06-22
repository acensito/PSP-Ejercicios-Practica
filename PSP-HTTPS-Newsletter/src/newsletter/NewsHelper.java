
package newsletter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class NewsHelper {
    public static synchronized void escribirArchivo(String datos, String nombreArchivo) {
        Path ruta = Paths.get(nombreArchivo + ".txt");
        try {
            //-CREATEte ahorra comprobar si existe el archivo al escribir
            //.APPEND sirve para añadir al final del archivo
            Files.writeString(ruta, datos, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Archivo actualizado correctamente");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
    public static boolean correoRepetido(String nombreArchivo, String correoBuscado) {
        Path ruta = Paths.get(nombreArchivo + ".txt");

        try {
            List<String> lineas = Files.readAllLines(ruta);
            for (String linea : lineas) {
                //buscamos el correo en cada línea
                if (linea.contains(correoBuscado)) {
                    return true;//existe
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
        }

        return false; //no se encontro
    }
    
}



