
package farmacia;

import java.util.HashMap;

/**
 * Clase Farmacia, que gestiona la farmacia
 * @author Felipe R.
 */
public class Farmacia {
    //atributos de la clase
    //definimos los medicamentos que tenemos
    public static final int PARACETAMOL = 1;
    public static final int IBUPROFENO = 2;
    public static final int VITAMINA = 3; 
    //creamos el almacen de medicamentos
    private static final HashMap<Integer, Integer> almacen = new HashMap<>();
    //iniciamos el stock
    static {
        almacen.put(PARACETAMOL, 10);
        almacen.put(IBUPROFENO, 10);
        almacen.put(VITAMINA, 10);
    }
    
    // -- METODOS AUXILIARES DE LA CLASE --
    
    /**
     * Método que vende una cantidad determinada de un medicamento del enum y 
     * resta de la cantidad disponible en almacen. En caso de no estar disponible
     * mandará como valor por defecto cero.
     * 
     * @param medicamento int del medicamento a vender
     * @param cantidad cantidad en entero a vender.
     * @return booleano que indica si ha sido posible la venta o no.
     */
    public synchronized static boolean venderMedicamento(int medicamento, int cantidad) {
        //consulta disponibilidad en almacen del medicamento, por defecto devuelve
        //cero en caso de no estar disponible
        int disponible = almacen.getOrDefault(medicamento, 0);
        //si la cantidad disponible es mayor o igual a la que se quiere comprar
        if (disponible >= cantidad) {
            //actualizamos los valores en el almacen de dicho medicamento
            almacen.put(medicamento, disponible-cantidad);
            //mostramos mensaje de venta
            System.out.printf("""
                [Venta realizada]
                - Medicamento: %s
                - Cantidad vendida: %d
                - Stock restante: %d
                """, 
                getNombreMedicamento(medicamento), 
                cantidad, 
                almacen.getOrDefault(medicamento, 0));            
            //devolvemos true como operacion satisfactoria
            return true;
        }
        //mostramos mensaje de no venta
        System.out.printf("""
            [Venta fallida]
            - Medicamento: %s
            - Cantidad solicitada: %d
            - Stock restante: %d
            """, 
            getNombreMedicamento(medicamento), 
            cantidad, 
            almacen.getOrDefault(medicamento, 0));   
        //devolvemos false como operacion fallida
        return false;
    }
    
    // -- GETTERS & SETTERS --
    
    /**
     * Método que recibe por parametro entero un medicamento y devuelve su nombre
     * en String
     * 
     * @param medicamento valor int del medicamento
     * @return String con el nombre de dicho medicamento
     */
    private static String getNombreMedicamento(int medicamento) {
        return switch (medicamento) {
            case PARACETAMOL -> "Paracetamol";
            case IBUPROFENO -> "Ibuprofeno";
            case VITAMINA -> "Vitamina";
            default -> "Desconocido";
        };
    }
    
    /**
     * Método set para obtener la cantidad en almacen de un medicamento dado
     * @param medicamento int que indica el medicamento a actualizar
     * 
     * @return valor entero con la cantidad de dicho medicamento en almacen
     */
    public synchronized static int getMedicamento(int medicamento) {
        return almacen.getOrDefault(medicamento, 0);
    }
    
    /**
     * Método que devuelve un string conformado con el stock de un medicamento
     * pasado por parametro
     * 
     * @param medicamento entero con el medicamento del que se quiere obtener el
     * stock en almacen.
     * 
     * @return String conformado con el stock
     */
    public synchronized static String toStringStockMedicamento(int medicamento) {
        return switch (medicamento) {
            case PARACETAMOL -> "Stock Paracetamol: " + getMedicamento(medicamento);
            case IBUPROFENO -> "Stock Ibuprofeno: " + getMedicamento(medicamento);
            case VITAMINA -> "Stock Vitamina: " + getMedicamento(medicamento);
            default -> "Medicamento desconocido";
        };
    }
    
    /**
     * Método que devuelve un string con el stock completo del almacén
     * 
     * @return String conformado con el stock
     */
    public synchronized static String toStringAlmacen() {
        return """
               [Stock actual]
               - Paracetamol: %d
               - Ibuprofeno: %d
               - Vitamina: %d
               """.formatted(
               getMedicamento(PARACETAMOL),
               getMedicamento(IBUPROFENO),
               getMedicamento(VITAMINA)
        );
    }
    
}
