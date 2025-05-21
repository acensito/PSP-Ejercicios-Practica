
package webserver;

import java.io.IOException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Felipe R.
 */
public class LoopConexiones {
    /**
     * inicia el bucle de espera de conexiones entrantes y lanza un hilo para cada conexión.
     * @param serverSocketSSL socket SSLServerSocket configurado.
     * @throws java.io.IOException
     */
    public static void iniciarServerLoop(SSLServerSocket serverSocketSSL) throws IOException {
        //bucle para controlar las conexiones entrantes
        while (true) {
            //esperamos y aceptamos las conexiones entrantes
            SSLSocket cliente = (SSLSocket) serverSocketSSL.accept();
            //creamos un nuevo hilo para manejar la conexion entrante
            new HiloCliente(cliente).start();
        }
    } 
}
