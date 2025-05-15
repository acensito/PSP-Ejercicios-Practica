
package osogoloso;

/**
 *
 * @author Felipe R.
 */
public class Panal {
    private final int MAX_MIEL = 10;
    private final int MIN_ASEDIO = 5;
    private int miel = 0;
    private int asedios = 0;
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
                System.out.println("--- Error a la espera de poner miel");
            }
        }
        
        //simula la recoleccion de miel y lo deja en el panal
        miel++;
        System.out.println("--- "+hilo + " trajo néctar. Total miel: " + (miel+1));
        notifyAll();
    }
    
    public synchronized void saquear() {
        while (miel <= MIN_ASEDIO && abierto) {
            try {
                System.out.println("Panal vacío. Oso esperando para saquear...");
                wait();
            } catch (InterruptedException e) {
                System.out.println("Error a la espera de saquear miel");
            }
        }
        
        asedios++;
        System.out.println(">>> Comilona " + "#"+asedios+". Miel antes: "+miel);
        miel = 0;
        
        if (asedios == 3) {
            abierto = false;
            System.out.println(">>> El panal ha sido cerrado tras 3 saqueos.");
        }
        
        notifyAll();
    }
}
