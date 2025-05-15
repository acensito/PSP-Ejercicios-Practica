
package osogoloso;

/**
 *
 * @author Felipe R.
 */
public class Start {

    public static void main(String[] args) {
        try {
            Panal panal = new Panal();
            Abeja abeja1 = new Abeja(panal, "Abeja-1");
            Abeja abeja2 = new Abeja(panal, "Abeja-2");
            Oso oso = new Oso(panal);
            
            abeja1.start();
            abeja2.start();
            oso.start();
            
            abeja1.join();
            abeja2.join();
            oso.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
