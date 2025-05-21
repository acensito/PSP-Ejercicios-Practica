
package webserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * Metodo que devuelve una frase aleatoria del archivo frases.txt. Este metodo
 * gestionará la concurrencia de multiples clientes al archivo aunque no exista
 * escritura en el.
 * 
 * @author Felipe R.
 */
public class FrasesHelper {
    private static final String ARCHIVO = "frases.txt";
    private static final Random random = new Random();
    private static List<String> frases = null;
    
    public static synchronized String obtenerFrase() {
        if (frases == null) {
            cargarFrases();
        }
        if (frases.isEmpty()) {
            return "No hay frases disponibles";
        }
        return frases.get(random.nextInt(frases.size()));
    }
    
    private static void cargarFrases() {
        try {
            frases = Files.readAllLines(Paths.get(ARCHIVO));
        } catch (IOException ex) {
            LogHelper.logError();
            frases = List.of("Hay un error en la carga. No hay frases disponibles");
        }
    }
}
