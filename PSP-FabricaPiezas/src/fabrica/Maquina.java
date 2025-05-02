
package fabrica;

import java.util.Random;

/**
 * Clase Maquina que hereda de Thread.
 * 
 * @author Felipe R.
 */
public class Maquina extends Thread {
    //atributos de clase
    private final Fabrica fabrica; //aqui se almacenará la fabrica común
    private final Random random = new Random(); //atributo auxiliar
    
    //constructor de la clase
    public Maquina(Fabrica fabrica, String name) {
        super(name);
        this.fabrica = fabrica;
    }
    
    /**
     * Metodo run que ejecuta el hilo
     */
    @Override
    public void run() {
        //mientras que el numero de piezas fabricadas sea menor
        //las piezas almacenadas
        while(fabrica.getFabricados()< fabrica.getCAPACIDAD()){
            try {
                //coloca una pieza en la cinta transportadora
                fabrica.colocarPieza(this.getName());
                //espera entre 1 y 3 seg (simula tiempo fabricación)
                Thread.sleep(random.nextInt(1000, 4000));
            } catch (InterruptedException ex) {
                //en caso de error, finalizamos el hilo
                System.err.println("Error fabricacion en " + getName());
                Thread.currentThread().interrupt();
            }
        }
    }
    
    
}
