
package lavanderia;

import java.util.Random;

/**
 * Clase Lavanderia
 * 
 * @author Felipe R.
 */
public class Lavanderia {
    private boolean estado = true;       //estado apertura lavanderia
    private int clientes = 0;            //num clientes atendidos
    private int funcionando = 0;         //contador lavadoras funcionando
    private final int num_lavadoras;                  //num lavadoras de la lavanderia
    private final Object lock = new Object();   //objeto lock apoyo
    private final Random random = new Random(); //objeto random

    public Lavanderia(int num_lavadoras) {
        this.num_lavadoras = num_lavadoras;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado() {
        this.estado = false;
    }

    public void usarLavadora(String cliente) {
        if (estado) { //mientras se encuentra abierto el local 
            try {
                synchronized (lock) {
                    while (funcionando >= num_lavadoras) {
                        //mientras no haya lavadores disponibles
                        System.out.println(cliente + " esperando a una lavadora disponible...");
                        lock.wait();
                    }

                    funcionando++; //contamos una lavadora funcionando
                    clientes++; //sumamos un cliente atendido
                    System.out.println(cliente + " empieza a lavar. Lavadoras funcionando: " + funcionando);
                }
                
                //simulamos un periodo de lavado entre 5-10 segundos
                Thread.sleep(random.nextInt(5000,10001));
                    
                synchronized (lock) {
                    funcionando--; //libera una lavadora
                    System.out.println(cliente + "finaliza. Lavadoras funcionando: " + funcionando);
                    lock.notifyAll(); //notificamos al resto de hilos
                }
            } catch (InterruptedException e) {
                System.err.println("ERROR");
            }
        }

    }

    public void getEstado(){
        System.out.println("Clientes atendidos: " + clientes);
        System.out.println("Ganancia total: " + clientes*3 + "€");
        System.out.println("Lavadoras disponibles: " + (num_lavadoras-funcionando));
        System.out.println("Lavadoras en uso: " + funcionando);
    } 
    
}
