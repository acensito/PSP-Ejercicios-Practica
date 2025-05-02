
package fabrica;

public class Fabrica {
    private int cinta = 0;
    private int fabricados = 0;
    private int empaquetados = 0;
    private final int CAPACIDAD_CINTA = 5;
    private final int ALMACEN = 50;
    
    public synchronized void colocarPieza(String name) {
        while (cinta >= CAPACIDAD_CINTA) {            
            try {
                wait();
            } catch (InterruptedException ex) {
                System.out.println("Error");
            }
        }
        fabricar();
        System.out.println( name + " ha fabricado una pieza. \nPiezas en la cinta: " + cinta);
        notifyAll();
    }
    
    public synchronized void empaquetarPieza(String name) {
        while (cinta <= 0) {            
            try {
                wait();
            } catch (InterruptedException ex) {
                System.out.println("Error");
            }
        }
        empaquetar();
        System.out.println( name + " ha empaquetado una pieza. \nPiezas en la cinta: " + cinta);
        notifyAll();
    }

    public synchronized int getFabricados() {
        return fabricados;
    }

    public synchronized int getEmpaquetados() {
        return empaquetados;
    }

    public synchronized int getCAPACIDAD() {
        return ALMACEN;
    }
    
    private void fabricar(){
        cinta++;
        fabricados++;
    }
    
    private void empaquetar() {
        cinta--;
        empaquetados++;
    }
    
}
