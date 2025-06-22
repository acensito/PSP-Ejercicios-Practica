
package webserver.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import webserver.response.Response;

/**
 *
 * @author Felipe2T
 */
public class HiloCliente extends Thread {
    //atributos de la clase
    private final Socket skClient; //conexion con el socket del cliente
    private static final File BD_FARMACIA = new File("farmacia.txt");

    //constructor de la clase
    public HiloCliente(Socket skClient) {
        this.skClient = skClient;
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(skClient.getInputStream()));
            PrintWriter salida = new PrintWriter(skClient.getOutputStream(), true, StandardCharsets.UTF_8)) {

            //lee la primera linea del request
            String peticion = entrada.readLine();

            //descartamos peticiones incorrectas
            if (peticion == null || (!peticion.startsWith("GET") && !peticion.startsWith("POST"))) {
                return; // Ignora la petición si no es GET o POST.
            }

            //DEBUG
            System.out.println("peticion: " + peticion);

            //extraemos la ruta y metodo solicitado
            String[] partes = peticion.split(" ");
            String metodo = partes[0];
            String ruta = partes[1];
            String linea;

            //Leer encabezados HTTP y determinar el tamaño del cuerpo.
            int contentLength = 0;
            while (!(linea = entrada.readLine()).isBlank()) {
                System.out.println("linea: "+ linea);
                if (linea.startsWith("Content-Length: ")) {
                    contentLength = Integer.parseInt(linea.substring(16));
                }
            }

            //Parseamos los datos en caso de ser un request POST y estos los
            //almacenaremos como cuerpo de la solicitud
            StringBuilder cuerpo = new StringBuilder(); 
            if ("POST".equals(metodo) && contentLength > 0) {
                char[] buffer = new char[contentLength];
                entrada.read(buffer, 0, contentLength);
                cuerpo.append(buffer);
            }

            String response; //Variable que contendrá la respuesta del servidor

            //Ahora vamos a derivar dependiendo de la ruta indicada
            if (ruta.equals("/")) {
                response = controlaFarmacia(cuerpo.toString());
            } else {
                response = construirReponse(404, Response.NOTFOUND);
            }
            //System.out.println("Respuesta: " + respuesta);
            salida.println(response); // Envía la respuesta al cliente.
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Muestra errores en la consola.
        }
    }

    private String controlaFarmacia(String cuerpo) {

        int codigo = 200;
        String responseHTML;
        System.out.println("Cuerpo: " + cuerpo);

        try {
            if (!cuerpo.isEmpty()) { //POST
                //DEBUG
                System.out.println(cuerpo);

                //Separamos los datos del request en clave-valor
                //primero separamos los tipos de datos
                //medicamento=valor  y cantidad=medicamento
                String[] datos = cuerpo.split("&");
                //ahora vamos a separar estos dos conjunto de datos para
                //obtener los valores enteros de cada uno para crear un
                //array clave-valor
                //creamos el array
                ArrayList<String> claveValor = new ArrayList<>();
                //separamos los datos por el simbolo =, nos quedamos con el
                //segundo componente, el valor, asi de esta manera, si recibimos
                //medicamento=1&cantidad=3 quedaria un array [1,3] mas manejable
                for (String dato : datos) {
                    String[] soloValores = dato.split("=");
                    claveValor.add(soloValores[1]);
                }

                //Ahora, llamamos al metodo sincronizado correspondiente
                //que accede, consulta y actualiza el archivo con los datos
                //Primero consultamos si existe el archivo en base de datos
                if (!BD_FARMACIA.exists()) {
                    //si no existe la base de datos, devolvemos mensaje de error
                    String responseERROR = String.format(Response.ERROR, "Error en el servidor");
                    responseHTML = String.format(Response.INDEX, responseERROR);
                    //salimos del metodo devolviendo el html con el error
                    return construirReponse(200, responseHTML);
                }

                //En este punto, entendemos que existe el archivo de base de
                //datos, asi que actualizamos los datos
                String reponseRESULTADO = actualizarStocks(claveValor)
                    ? String.format(Response.INFO, "Pedido suministrado correctamente")
                    : String.format(Response.ERROR, "Hay problemas de stock del medicamento solicitado");

                responseHTML = String.format(Response.INDEX, reponseRESULTADO);

            } else { //GET
                responseHTML = String.format(Response.INDEX, "");
            }
        } catch (NumberFormatException e) { //ERROR
            responseHTML = Response.NOTFOUND;
            codigo = 400;
            System.err.println(e.getMessage()); // Muestra errores en la consola.
        }

        return construirReponse(codigo, responseHTML);
    }

    private String construirReponse(int codigo, String contenido) {
        return (codigo == 200 ? "HTTP/1.1 200 OK" : "HTTP/1.1 400 Bad Request") + "\n"  //Linea inicial
               + "Content-Type: text/html; charset=UTF-8"+ "\n"                         //Metadatos
               + "Content-Length: " + contenido.length() + "\n"
               + "\n"                                                                   //Línea vacía
               + contenido;                                                             //Cuerpo
    }

    /**
     * Metodo que recibe por parametro un ArrayList el cual tiene dos valores
     * la primera posicion corresponderá con el key de un medicamento solicitado
     * la segunda posicion será la cantidad del pedido.
     * 
     * Se obtendrá de disco los stocks de medicamentos y se ajustará los stocks
     * debidos. Devolverá true si se ha podido realizar el pedido. False en caso
     * de existir algun problema.
     * 
     * @param pedido
     * @return boolean
     */
    private synchronized static boolean actualizarStocks(ArrayList<String> pedido) {
        //datos del pedido realizado
        final String keyMedicamento = pedido.get(0).trim();
        final int cantidadSolicitada;

        try {
            //se obtiene la cantidad pedida, se pasa de string a int
            cantidadSolicitada = Integer.parseInt(pedido.get(1).trim());
        } catch (NumberFormatException e) {
            System.err.println("Cantidad inválida: " + pedido.get(1));
            return false;
        }
        //datos de stocks de medicamentos
        Map<String, Integer> stocks = new HashMap<>();

        //procedemos a leer el archivo
        try (BufferedReader br = new BufferedReader(new FileReader(BD_FARMACIA))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 2) {
                    String medicamento = partes[0].trim();
                    int cantidad = Integer.parseInt(partes[1].trim());
                    stocks.put(medicamento, cantidad);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return false;
        }

        //validamos si existe el medicamento solicitado
        if (!stocks.containsKey(keyMedicamento)) {
            return false;
        }
        //validamos si hay stock suficiente del medicamento solicitado
        int stockActual = stocks.get(keyMedicamento);
        if (stockActual < cantidadSolicitada) {
            return false; //no hay suficiente
        }
        //actualizamos el stock
        stocks.put(keyMedicamento, stockActual - cantidadSolicitada);

        //reescribimos el archivo
        try (PrintWriter pr = new PrintWriter(new FileWriter(BD_FARMACIA))) {
            for (Map.Entry<String, Integer> entry : stocks.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
                pr.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
            return false;
        }

        return true;
    }
}
