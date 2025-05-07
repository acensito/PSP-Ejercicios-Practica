
package farmacia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Felipe2T
 */
public class Servidor extends Thread {
    //atributos de la clase
    private final Socket skClient;
    private static final int PORT = 2000;
    
    //constructor de la clase
    public Servidor(Socket skClient) {
        this.skClient = skClient;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //lanza el servidor
        try (ServerSocket skServer = new ServerSocket(PORT)) {
            //muestra mensaje de inicio del servidor
            System.out.println("Servidor iniciado en el puerto " + PORT);
            //bucle para recibir conexiones
            while (true) {                
                //existe una conexion entrante
                Socket skt = skServer.accept();
                //lanzamos un hilo de ejecucion
                new Servidor(skt).start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(skClient.getInputStream());
             DataOutputStream out = new DataOutputStream(skClient.getOutputStream())){
            //entra el medicamento pedido del cliente
            int medicamento = in.readInt();
            //entra la cantidad pedida por el cliente
            int cantidad = in.readInt();
            
            //se procede a intentar realizar la venta del medicamento
            boolean vendido = Farmacia.venderMedicamento(medicamento, cantidad);
            
            //se devuelve al cliente resultado de la venta (true=realizada, false=no_realizada)
            out.writeBoolean(vendido);
        } catch (Exception e) {
            System.err.println("Error");
        }
    }
    
}
