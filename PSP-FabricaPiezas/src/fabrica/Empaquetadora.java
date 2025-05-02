
package fabrica;

import java.util.Random;

/**
 * Clase Empaquetadora que hereda de Thread
 * @author Felipe R.
 */
public class Empaquetadora extends Thread {
    //atributos de la clase
    private final Fabrica fabrica; //atributo para la instancia común a los hilos
    private final Random random = new Random(); //atributo auxiliar

    //constructor de la clase
    public Empaquetadora(Fabrica fabrica, String name) {
        super(name);
        this.fabrica = fabrica;
    }

    @Override
    public void run() {
        //mientras el numero de empaquetados sea menor que la capacidad
        while(fabrica.getEmpaquetados() < fabrica.getCAPACIDAD()){
            try {
                //empaquetamos una pieza
                fabrica.empaquetarPieza(this.getName());
                //esperamos 2 seg. (simula tiempo de embalado)
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ex) {
                //en caso de error, finalizamos el hilo
                System.err.println("Error funcionamiento " + getName());
                Thread.currentThread().interrupt();
            }
        }
    }
    
}
