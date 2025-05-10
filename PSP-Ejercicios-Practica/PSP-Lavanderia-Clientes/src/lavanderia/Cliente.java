
package lavanderia;

/**
 *
 * @author DGGC
 */
public class Cliente extends Thread {
    private final Lavanderia lavanderia;

    public Cliente(Lavanderia lavanderia, String name) {
        super(name);
        this.lavanderia = lavanderia;
    }

    @Override
    public void run() {
        if (lavanderia.isEstado()) {
            //si esta abierta la lavanderia, intenta usar una lavadora
            lavanderia.usarLavadora(this.getName());
        } else {
            System.out.println("Llegó y estaba cerrada la lavandería");
        }
    }
    
}
