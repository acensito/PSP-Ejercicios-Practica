
package webserver.helpers;

/**
 *
 * @author Felipe2T
 */
public class MensajeInicio {
    /**
     * muestra un mensaje de inicio del servidor.
     * @param PORT
     */
    public static void mensajeInicio(int PORT) {
        System.out.printf("""
            +-------------------------------+
            |  Servidor WEB en ejecución    |
            | Escuchando en el puerto: %d |
            +-------------------------------+
            Accede desde tu navegador a https://localhost:%d
            """, PORT, PORT);
    }
}
