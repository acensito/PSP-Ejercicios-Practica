
package farmacia;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Felipe2T
 */
public class Lanzador {
    //atributos de la clase
    private static final Scanner teclado = new Scanner(System.in);
    private static boolean activo = true;
    private static final ArrayList<Cliente> clientes = new ArrayList<>();
    private static int contador = 1;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // mientras este activo el programa, muestra el menu
        while (activo) {
            //lanzamos mostrar menu
            mostrarMenu();
            //obtenemos la opcion seleccionada
            int seleccion = leerOpcion();
            //procesamos la opcion (el menu saldra cuando se termine
            //procesar al completo
            procesarOpcion(seleccion);
        }
    }

    private static void mostrarMenu() {
        System.out.printf("""
                --- FARMACIA DE GUARDIA ---
                0. Simular pedidos de clientes
                1. Ver stocks de almacén
                2. Finalizar programa
                Seleccione una opción: """);
    }

    private static int leerOpcion() {
        while (!teclado.hasNextInt()) {
            System.out.print("Entrada inválida. Introduzca un número válido: ");
            teclado.next();
        }
        return teclado.nextInt();
    }

    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 0 -> lanzarClientes();
            case 1 -> mostrarStocks();
            case 2 -> {
                activo = false;
                System.out.println("Finalizando ejecucion de clientes. Cerrando.");
            }
            default -> System.err.println("Opción incorrecta!!!");
        }
    }

    private static void lanzarClientes() {
        //pregunta cuantos clientes quiere lanzar
        System.out.print("¿Cuantos clientes desea lanzar?: ");
        int cantidad = leerOpcion();
        
        //crea objetos cliente y los añade al array
        for (int i = 0; i < cantidad; i++) {
            Cliente cliente = new Cliente("Cliente" + contador);
            clientes.add(cliente);
            //se suma el contador para que sean consecutivos los clientes 
            // en dicha ejecucion
            contador++;
        }
        
        //lanzamos los clientes
        for (Cliente cliente : clientes) {
            cliente.start();
        }
        //esperamos a que los hilos finalicen para poder continuar
        for (Cliente cliente : clientes) {
            try {
                cliente.join();
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
        //limpiamos el array para un siguiente uso
        clientes.clear();
    }
    
    /**
     * Método que muestra el stock en almacén
     */
    private static void mostrarStocks() {
        System.out.println(Farmacia.toStringAlmacen());
    }
    
}
