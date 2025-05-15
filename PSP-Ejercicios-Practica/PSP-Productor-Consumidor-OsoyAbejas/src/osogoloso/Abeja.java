
package osogoloso;

import java.util.Random;

/**
 *
 * @author Felipe R.
 */
public class Abeja extends Thread {
    private final Panal panal; // recurso compartido
    private final Random random = new Random();

    public Abeja(Panal panal, String name) {
        super(name);
        this.panal = panal;
    }

    @Override
    public void run() {
        while (panal.isAbierto()) {            
            try {
                panal.recolectar(getName());
                Thread.sleep(random.nextInt(2));
            } catch (InterruptedException ex) {
                System.out.println("ERROR: en la espera de hilo. " + ex.getMessage());
            }
        }
        System.out.println(getName() + " se va: el panal está cerrado.");
    }
    
}
