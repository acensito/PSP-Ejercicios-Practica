
package fabrica;

/**
 * Clase Fabrica. Clase común a todos los hilos.
 * 
 * @author Felipe R.
 */
public class Fabrica {
    //atributos de la clase
    private int cinta = 0;
    private int fabricados = 0;
    private int empaquetados = 0;
    private final int CAPACIDAD_CINTA = 5;
    private final int ALMACEN = 50;
    
    /**
     * Método sincronizado que coloca una pieza en la cinta transportadora
     * @param name nombre de la maquina (hilo) que coloca la pieza
     */
    public synchronized void colocarPieza(String name) {
        //mientras la cinta tenga su capacidad llena
        while (cinta >= CAPACIDAD_CINTA) {            
            try {
                wait(); //los hilos esperan (las maquinas paran de producir)
            } catch (InterruptedException ex) {
                System.err.println("Error espera de fabricación");
            }
        }
        //fuera del bucle de espera, proceden a fabricar una pieza, llamando
        //al metodo auxiliar fabricar
        fabricar();
        //mostramos mensaje de feedback
        System.out.println( name + " ha fabricado una pieza. "
                + "\nPiezas en la cinta: " + cinta
                + "\nTotal almacenadas: " + empaquetados);
        //notificamos al resto de hilos
        notifyAll();
    }
    
    /**
     * Método sincronizado que empaqueta una pieza y la almacena
     * @param name nombre de la maquina (hilo) que empaqueta pieza
     */
    public synchronized void empaquetarPieza(String name) {
        //mientras la cinta este vacia
        while (cinta <= 0) {            
            try {
                wait(); //los hilos esperan (la empaquetadora para de empaquetar)
            } catch (InterruptedException ex) {
                System.err.println("Error espera empaquetado");
            }
        }
        //fuer del bucle de espera, procede a embalar una pieza, llamando al 
        //metodo auxiliar empaquetar
        empaquetar();
        //mostramos mensaje feedback
        System.out.println( name + " ha empaquetado una pieza. "
                + "\nPiezas en la cinta: " + cinta
                + "\nTotal almacenadas: " + empaquetados);
        //notificamos al resto de hilos
        notifyAll();
    }

    // -- GETTERS --
    
    /**
     * Método sincronizado que devuelve la cantidad de piezas fabricadas
     * @return entero con numero de piezas fabricadas
     */
    public synchronized int getFabricados() {
        return fabricados;
    }

    /**
     * Método sincronizado que devuelve la cantidad de piezas empaquetadas
     * @return entero con número de piezas empaquetadas
     */
    public synchronized int getEmpaquetados() {
        return empaquetados;
    }

    /**
     * Método sincronizado que devuelve la capacidad total de fabricacion y
     * almacenaje de la fábrica
     * @return entero con la capacidad total de fabricacion/almacenaje
     */
    public synchronized int getCAPACIDAD() {
        return ALMACEN;
    }
    
    /**
     * Método auxiliar que incrementa numero en la cinta transportadora e
     * incrementa numero de fabricados. No es sincronizado dado que se ejecuta
     * en bloque sincronizado.
     */
    private void fabricar(){
        cinta++;
        fabricados++;
    }
    
    /**
     * Método auxiliar que decrementa numero en la cinta transportadora e 
     * incrementa número de empaquetados. No es sincronizado dado que se ejecuta
     * en bloque sincronizado.
     */
    private void empaquetar() {
        cinta--;
        empaquetados++;
    }
    
}
