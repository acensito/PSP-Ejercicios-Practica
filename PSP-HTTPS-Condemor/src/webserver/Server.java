package webserver;

import javax.net.ssl.SSLServerSocket;

/**
 * En esta versión, vamos a modificar Frases a FrasesHelper para acceder al archivo
 * de frases.txt y obtener de ahi las frases.
 * 
 * @author Felipe R.
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //configuramos el socketSSL
        try (SSLServerSocket serverSocketSSL = SSLHelpers.configServerSSL()){
            //Mostramos un mensaje de inicio por consola
            System.out.println("Servidor ChiquitoWiki: [ INICIADO ]");
            System.out.println("URL Acceso: https://localhost:" + SSLHelpers.PORT);
            //Iniciamos el Loop de espera de llegada de hilos de clientes
            LoopConexiones.iniciarServerLoop(serverSocketSSL);  
        } catch (Exception e) {
            //aqui vienen errores que no quiero mostrar porque son por el 
            //certificado SSL.
            //System.err.println("ERROR SERVIDOR: " + e.getMessage());
        }
    }
    
}
