package proyectobasehotel;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Nombre <>
 */
public class ServidorHTTP {
    
    private static final int PORT = 8063;

    public static void main(String[] args) {
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Servidor web [ iniciado ]");
            System.out.println("Visita: http://localhost:"+PORT);
            while (true) {                
                Socket cliente = serverSocket.accept();
                new HiloCliente(cliente).start();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
