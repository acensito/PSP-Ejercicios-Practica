
package farmacia;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author Felipe R.
 */
public class Cliente extends Thread {
    //atributos de la clase
    private static final String HOST = "localhost";
    private static final int PORT = 2000;
    private static final Random random = new Random();
    private static final int[] medicamentos = {Farmacia.PARACETAMOL, Farmacia.IBUPROFENO, Farmacia.VITAMINA};
    
    public Cliente(String name) {
        super(name);
    }

    @Override
    public void run() {
        try (Socket skClient = new Socket(HOST, PORT);
             DataOutputStream out = new DataOutputStream(skClient.getOutputStream())){
            //mensaje informando quien entra en la farmacia
            System.out.printf("%s entra en la farmacia.\n", getName());
            //generamos un pedido aleatorio de un medicamento
            int medicamento = medicamentos[random.nextInt(medicamentos.length)];
            //generamos una pedido aleatorio de la cantidad
            int cantidad = random.nextInt(1, 3); //entre 1 y 2
            //se envia el pedido al servidor
            out.writeInt(medicamento);
            out.writeInt(cantidad);
            System.out.printf("%s sale de la farmacia.\n", getName());
            
        } catch (Exception e) {
            System.err.println("Error: se ha encontrado un error en lado cliente");
        }
    }
    
    
    
    
}
