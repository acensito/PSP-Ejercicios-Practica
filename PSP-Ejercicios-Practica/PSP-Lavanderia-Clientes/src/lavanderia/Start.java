package lavanderia;

import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author Felipe R.
 */
public class Start {
    //atributo compartido
    private static final Lavanderia lavanderia = new Lavanderia(4);
    //atributos de la clase
    private static final Scanner teclado = new Scanner(System.in);
    private static final ArrayList<Cliente> clientes = new ArrayList<>();
    private static int contador = 1;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // mientras este activo el programa, muestra el menu
        while (lavanderia.isEstado()) {
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
                --- CLEANFAST ---
                0. Simular llegada de clientes
                1. Estado de la lavanderia
                2. Cerrar lavanderia
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
            case 1 -> { lavanderia.getEstado(); } //obtener estado lavanderia
            case 2 ->  { lavanderia.setEstado(); } //cerrar lavandería
            default -> System.err.println("Opción incorrecta!!!");
        }
    }

    private static void lanzarClientes() {
        //pregunta cuantos clientes quiere lanzar
        System.out.print("¿Cuantos clientes desea lanzar?: ");
        int cantidad = leerOpcion();
        
        //crea objetos cliente y los añade al array
        for (int i = 0; i < cantidad; i++) {
            Cliente cliente = new Cliente(lavanderia, "Cliente" + contador);
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
}

