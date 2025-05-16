
package webserver;

import webserver.helpers.MensajeInicio;
import webserver.helpers.LoopConexiones;
import webserver.helpers.SSLHelper;
import javax.net.ssl.SSLServerSocket;

/**
 * @author Felipe2T
 */
public class Servidor {    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //configuramos el SSL
            SSLServerSocket serverSocketSSL = SSLHelper.configServerSSL();
            //mosntramos mensaje de inicio del servidor
            MensajeInicio.mensajeInicio(SSLHelper.PORT);
            //iniciamos el loop de hilos de cliente
            LoopConexiones.iniciarServerLoop(serverSocketSSL);
        } catch (Exception e) {
            System.err.println("SSL Server ERROR: " + e.getLocalizedMessage());
        }
    }
    
}
