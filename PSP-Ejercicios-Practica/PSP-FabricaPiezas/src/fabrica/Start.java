
package fabrica;

import java.util.ArrayList;

public class Start {
    private static final int NUM_MAQUINAS = 3;
    private static final ArrayList<Maquina> maquinas = new ArrayList<>();
    
    public static void main(String[] args) {
        try {
            //"abrimos" la fabrica
            Fabrica fabrica = new Fabrica();
            
            //"encendemos" las maquinas e instanciamos
            //recorremos un bucle con el numero de maquinas definidas y vamos
            //agregandolas al array e iniciandolas
            for (int i = 0; i < NUM_MAQUINAS; i++) {
                Maquina maquina = new Maquina(fabrica, "Maquina "+(i+1));
                maquinas.add(maquina);
                maquina.start();
            }
            //"encendemos" la empaquetadora instanciandola
            Empaquetadora empaquetadora = new Empaquetadora(fabrica, "Empaquetadora");
            empaquetadora.start();
            
            //"programamos" las maquinas para que esperen a terminar todos los hilos
            //recorremos nuevamente el array de maquinas para indicarlo
            for (Maquina maquina : maquinas) {
                maquina.join();
            }
            //lo indicamos en empaquetadora
            empaquetadora.join();
            //una vez finalizadas las tareas de los hilos, cerramos la fabrica
            System.out.println("Fabrica cerrada.");
        } catch (InterruptedException ex) {
            System.err.println("Ha ocurrido un error con los hilos.");
        }
    }
    
}
