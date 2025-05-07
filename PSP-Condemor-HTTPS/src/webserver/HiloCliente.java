
package webserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Felipe2T
 */
public class HiloCliente extends Thread {
    private final Socket skClient;

    public HiloCliente(Socket skClient) {
        this.skClient = skClient;
    }

    @Override
    public void run() {
        try (
            //Abrimos un buffer de entrada al socket y otro de salida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(skClient.getInputStream()));
            PrintWriter salida = new PrintWriter(skClient.getOutputStream(), true, StandardCharsets.UTF_8)
            ){
            
            //Ahora seguimos el ciclo de la recepción de una peticion (request)
            //y procedemos segun corresponda
            
            //Leemos la primera linea del request
            String peticion = entrada.readLine();
            
            //Vamos a ignorar peticiones que no sean GET o POST o incorrectas
            if (peticion == null || (!peticion.startsWith("GET") && !peticion.startsWith("POST"))) {
                return; // Ignora la petición si no es GET o POST.
            }
            
            //Para DEBUG y ver el recorrido de los datos que recibimos, mostramos por consola
            System.out.println("Petición: " + peticion);
            
            //Extraemos la ruta y metodo solicitado
            String[] partes = peticion.split(" ");
            String metodo = partes[0]; //Obtenemos si es GET o POST
            String ruta = partes[1];   //Obtenemos la ruta del request
            
            //Leemos los encabezados HTTP recibidos, y conformamos el contentlength
            String linea; //variable contenedor para leer linea por linea
            int contentLength = 0; //variable incializada del content-length
            while (!(linea = entrada.readLine()).isBlank()) {
                System.out.println("linea: "+ linea); //DEBUG
                if (linea.startsWith("Content-Length: ")) {
                    //obtenemos el content-length del resto del cuerpo recibido
                    //a partir del punto indicado
                    contentLength = Integer.parseInt(linea.substring(16));
                }
            }
            
            //Si es una petición POST extraemos los datos recibidos y los
            //almacenaremos como cuerpo de la solicitud
            StringBuilder cuerpo = new StringBuilder(); 
            if (metodo.equals("POST") && contentLength > 0) {
                char[] buffer = new char[contentLength];
                entrada.read(buffer, 0, contentLength);
                cuerpo.append(buffer);
            }
            
            String response; //Variable que contendrá la respuesta del servidor

            //Ahora vamos a derivar al controlador dependiendo de la ruta indicada
            if (ruta.equals("/")) {
                response = controladorFrases(cuerpo.toString());
            } else {
                response = construirResponse(404, Response.NOTFOUND);
            }
            //DEBUG
            //System.out.println("Respuesta: " + respuesta);
            salida.println(response); // Envía la respuesta al cliente.
            
        } catch (Exception e) {
            System.err.println("ERROR Hilo: " + e.getMessage());
        }
    }
    
    /**
     * Método que crea un response, recibiendo como parametros un codigo de estado
     * y el string con el contenido html que se quiere mostrar
     * @param codigo codigo de estado a enviar (200 OK, 404 NOT FOUND)
     * @param contenido String con el HTML generado para devolver
     * 
     * @return String con todo conformado como respuesta (response)
     */
    private String construirResponse(int codigo, String contenido) {
        return (codigo == 200 ? Response.CABECERA_OK : Response.CABECERA_NOTFOUND) + "\n"//Cabcera inicial
               + "Content-Type: text/html; charset=UTF-8"+ "\n"                         //Metadatos
               + "Content-Length: " + contenido.length() + "\n"                         //Content-Length
               + "\n"                                                                   //Línea vacía
               + contenido;                                                             //Cuerpo
    }
    
    /**
     * Método que recibe el cuerpo de la petición ya sea POST o GET y actua como 
     * controlador y logica de la petición
     * 
     * @param cuerpo de la peticion
     * 
     * @return String con el HTML conformado según proceda
     */
    private String controladorFrases(String cuerpo) {
        //inicializamos variables de apoyo
        int CODIGO = 200; //codigo de respuesta por defecto (que esta todo OK)
        String responseHTML; //respuesta en HTML que se va a devolver
        
        //DEBUG
        System.out.println("Cuerpo: " + cuerpo);
        
        //Ahora debemos diferenciar entre si la peticion recibida es POST o GET
        //para ello tendremos que comprobar si el cuerpo de la petición lleva 
        //contenido alguno.
        try {
            if (!cuerpo.isEmpty()) { //Petición POST (el cuerpo lleva datos)
                //obtenemos esos datos que nos han enviado por POST. Para este
                //ejercicio, lo que tenemos que recibir es el value del boton 
                //que se ha pulsado
                //los valores POST suelven venir en el siguiente formato
                //nombre_componente=valor
                //El boton tiene como nombre "frase", asi que debemos obtener el
                //valor que envia el componente frase.
                //Para ello, vamos a separar con split las partes en un array.
                //La primera posicion de datos tendra el nombre del componente
                //y la segunda posición, tendrá el valor recibido
                String[] datos = cuerpo.split("=");
                
                //Vamos a obtener el resultado
                String resultadoHTML; //variable de apoyo con el HTML del resultado
                //Ahora la condición para enviar un mensaje es recibir "true"
                if (datos[1].equals("true")) { //afirmativo
                    //Seleccionamos un mensaje aleatorio del array
                    String frase = Frases.obtenerFrase();
                    //Creamos el HTML de la respuesta. Para ello llamamos a la 
                    //variable de la clase Response para mensaje y le introducimos
                    //la frase obtenida como parametro. Para ello vamos a usar StringFormat
                    //NOTA: se recomienda observar el HTML en la clase Response
                    resultadoHTML = String.format(Response.INFO, frase);
                } else { //negativo, no se ha recibido true
                    //Vamos a devolver un resultado, pero de error
                    resultadoHTML = String.format(Response.ERROR, "Ha existido un error");
                }
                
                //Obtenido el resultado, conformamos el HTML completo que vamos 
                //a devolver, le pasamos como parametro para que lo incluya, el mensaje
                //de respuesta al mismo HTML que queremos devolver.
                responseHTML = String.format( Response.INDEX, resultadoHTML);
                
            } else { //Petición GET (el cuerpo no lleva datos)
                //devuelve el HTML del INDEX, pero con un marcador vacio, no se ve
                responseHTML = String.format(Response.INDEX, "");
            }
            
        } catch (Exception e) { //Si detecta alguna excepcion
            responseHTML = Response.NOTFOUND;
            CODIGO = 404;
            System.err.println(e.getMessage()); // Muestra errores en la consola.
        }
        
        return construirResponse(CODIGO, responseHTML);
    }
    
}
