package webserver;

import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author Felipe2T
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //configuramos el socketSSL
        try (SSLServerSocket serverSocketSSL = SSLHelpers.configServerSSL()){
            //Iniciamos el Loop de espera de llegada de hilos de clientes
            LoopConexiones.iniciarServerLoop(serverSocketSSL);
            //Mostramos un mensaje de inicio por consola
            System.out.println("Servidor ChiquitoWiki: [ INICIADO ]");
            System.out.println("URL Acceso: https://localhost:" + SSLHelpers.PORT);
            
        } catch (Exception e) {
        }
    }
    
}
