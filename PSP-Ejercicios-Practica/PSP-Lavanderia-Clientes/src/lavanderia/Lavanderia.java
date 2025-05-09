
package lavanderia;

import java.util.ArrayList;

/**
 * Clase Lavanderia
 * @author Felipe R.
 */
public class Lavanderia {
    private static final boolean estado = true; //estado apertura lavanderia
    private static int clientes_atendidos = 0;
    private static int recaudacion = 0;
    private static final ArrayList<Lavadora> lavadoras = new ArrayList<>();

    /**
     * Constructor de la clase. Recibe el numero de lavadoras que va a tener la
     * lavandería
     * 
     * @param num_lavadoras 
     */
    public Lavanderia(int num_lavadoras) {
        //instanciamos el numero de lavadoras a funcionar
        for (int i = 0; i < num_lavadoras; i++) {
            lavadoras.add(new Lavadora("Lavadora-"+i));
        }
    }
    
    /**
     * Devuelve la primera lavadora que se encuentre libre del array de lavadoras
     * @return objeto lavadora que queda libre
     */
    private Lavadora buscaLavadoraLibre() {
        //recorremos las lavadoras en busca de la primera libre en el array
        for (Lavadora lavadora : lavadoras) {
            if (!lavadora.isLavando()) {
                //se devuelve dicha lavadora
                return lavadora;
            }
        }
        return null;
    }
    
    
    
}
