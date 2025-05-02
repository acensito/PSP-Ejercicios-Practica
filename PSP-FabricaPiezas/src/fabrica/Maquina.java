
package fabrica;

import java.util.Random;


public class Maquina extends Thread {
    private final Fabrica fabrica;
    private final Random random = new Random();

    public Maquina(Fabrica fabrica, String name) {
        super(name);
        this.fabrica = fabrica;
    }
    
    @Override
    public void run() {
        //mientras que el numero de piezas fabricadas sea menor
        //las piezas almacenadas
        while(fabrica.getFabricados()< fabrica.getCAPACIDAD()){
            try {
                fabrica.colocarPieza(this.getName());
                Thread.sleep(random.nextInt(1000, 4000));
            } catch (InterruptedException ex) {
                System.err.println("Error fabricacion en " + getName());
                Thread.currentThread().interrupt();
            }
        }
    }
    
    
}
