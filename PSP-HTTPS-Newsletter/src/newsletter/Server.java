
package newsletter;

import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author Felipe R.
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //configuramos el socketSSL
        try {
            SSLServerSocket serverSocketSSL = SSLHelper.configServerSSL();
            //Mostramos un mensaje de inicio por consola
            System.out.println("Servidor registro Newsletter: [ INICIADO ]");
            System.out.println("URL Acceso: https://localhost:" + SSLHelper.PORT);
            //Iniciamos el Loop de espera de llegada de hilos de clientes
            LoopConexiones.iniciarServerLoop(serverSocketSSL);  
        } catch (Exception e) {
            //aqui vienen errores que no quiero mostrar porque son por el 
            //certificado SSL. Solo activar para debug
            //System.err.println("ERROR SERVIDOR: " + e.getMessage());
        }
    }
    
}
