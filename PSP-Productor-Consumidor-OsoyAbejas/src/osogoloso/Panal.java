
package osogoloso;

/**
 *
 * @author Felipe R.
 */
public class Panal {
    private final int MIN_MIEL = 3;
    private final int MAX_MIEL = 30;
    private final int MAX_ASEDIOS = 5;
    private int miel = 0;
    private int asedios = 0;
    private int panza = 0;
    private boolean abierto = true;

    public int getMiel() {
        return miel;
    }

    public int getAsedios() {
        return asedios;
    }

    public boolean isAbierto() {
        return abierto;
    }
    
    public synchronized void recolectar(String hilo) {
        while (miel >= MAX_MIEL && abierto) {
            try {
                wait();
            } catch (InterruptedException ex) {
                System.out.println("--- Error a la espera de poner nectar");
            }
        }
        
        if (abierto) {
            miel++;
            System.out.println("--- "+hilo + " trajo néctar. Miel en el panal: " + miel);
        }
        
        notifyAll();
    }
    
    public synchronized void saquear() {
        while (miel <= MIN_MIEL && abierto) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Error a la espera de saquear miel");
            }
        }
        
        asedios++;
        panza += miel;
        System.out.println(">>> Comilona del oso " + "#"+asedios+". Miel en la panza: "+panza);
        miel = 0;
        
        if (asedios == MAX_ASEDIOS) {
            abierto = false;
        }
        
        notifyAll();
    }
}
