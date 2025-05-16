
package osogoloso;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe R.
 */
public class Start {

    private static final int NUM_ABEJAS = 3;

    public static void main(String[] args) {
        try {
            Panal panal = new Panal();
            List<Abeja> colmena = new ArrayList<>();

            for (int i = 0; i < NUM_ABEJAS; i++) {
                Abeja abeja = new Abeja(panal, "Abeja-" + (i + 1));
                colmena.add(abeja);
                abeja.start();
            }

            Oso oso = new Oso(panal);
            oso.start();

            for (Abeja abeja : colmena) {
                abeja.join();
            }

            oso.join();
            
            System.out.println("Panal de abejas cerrado. Fin del dia.");
        } catch (InterruptedException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
}
