
package osogoloso;

import java.util.Random;

/**
 *
 * @author Felipe R.
 */
public class Oso extends Thread {
    private final Panal panal;
    private static final Random random = new Random();

    public Oso(Panal panal) {
        this.panal = panal;
    }

    @Override
    public void run() {
        while (panal.isAbierto()) {            
            try {
                Thread.sleep(random.nextInt(15000)+2000);
                panal.saquear();
            } catch (InterruptedException ex) {
                System.out.println("ERROR: en la espera de hilo. " + ex.getMessage());
            }
        }
        System.out.println(">>> Oso lleno hasta arriba se marcha a hibernar una larga temporada.");
    }
}
