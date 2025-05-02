
package fabrica;

import java.util.Random;

public class Empaquetadora extends Thread {
    private final Fabrica fabrica;
    private final Random random = new Random();

    public Empaquetadora(Fabrica fabrica, String name) {
        super(name);
        this.fabrica = fabrica;
    }

    @Override
    public void run() {
        while(fabrica.getEmpaquetados() < fabrica.getCAPACIDAD()){
            try {
                fabrica.empaquetarPieza(this.getName());
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ex) {
                System.err.println("Error funcionamiento " + getName());
                Thread.currentThread().interrupt();
            }
        }
    }
    
}
