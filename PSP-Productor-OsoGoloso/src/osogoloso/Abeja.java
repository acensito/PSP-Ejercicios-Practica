
package osogoloso;

import java.util.Random;

/**
 *
 * @author Felipe R.
 */
public class Abeja extends Thread {
    private final Panal panal;
    private static final Random random = new Random();

    public Abeja(Panal panal, String name) {
        super(name);
        this.panal = panal;
    }

    @Override
    public void run() {
        while (panal.isAbierto()) {            
            try {
                Thread.sleep(random.nextInt(3000)+1000);
                panal.recolectar(getName());
            } catch (InterruptedException ex) {
                System.out.println("ERROR: en la espera de hilo. " + ex.getMessage());
            }
        }
        System.out.println(getName() + " ve que el panal esta cerrado y se marcha.");
    }
    
}
