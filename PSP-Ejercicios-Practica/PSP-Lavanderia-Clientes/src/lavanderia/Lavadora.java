
package lavanderia;

/**
 * @author DGGC
 */
public class Lavadora {
    public final String NOMBRE;
    private boolean funcionando = false;

    public Lavadora(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public boolean isLavando() {
        return funcionando;
    }

    public void ocupar() {
        this.funcionando = true;
    }
    
    public void liberar() {
        this.funcionando = false;
    }
    
    

    
}
