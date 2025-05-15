
package osogoloso;

/**
 *
 * @author Felipe R.
 */
public class Oso extends Thread {
    private final Panal panal;

    public Oso(Panal panal) {
        this.panal = panal;
    }

    @Override
    public void run() {
        while (panal.isAbierto()) {            
            panal.saquear();
        }
        System.out.println(">>> Oso se marcha a hibernar una larga temporada.");
    }
}
