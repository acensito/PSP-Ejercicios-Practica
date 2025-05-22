
package newsletter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Felipe R.
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
            
            //Si es una petición POST extraemos los datos recibidos
            StringBuilder cuerpo = new StringBuilder(); 
            if (metodo.equals("POST") && contentLength > 0) {
                char[] buffer = new char[contentLength];
                entrada.read(buffer, 0, contentLength);
                cuerpo.append(buffer);
            }
            
            String response; //Variable que contendrá la respuesta del servidor

            //Ahora vamos a derivar al controlador dependiendo de la ruta indicada
            if (ruta.equals("/")) {
                response = registroUsuarios(cuerpo.toString());
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
    
    private String registroUsuarios(String cuerpo) {
        
        int codigo = 200;
        String responseHTML;
        System.out.println("Cuerpo: " + cuerpo);
        
        try {
            if (!cuerpo.isEmpty()) { //POST
                //DEBUG
                System.out.println(cuerpo);

                //Separamos los datos del request en clave-valor
                String[] datos = cuerpo.split("&");
                String nombre = datos[0].split("=")[1];
                String email = datos[1].split("=")[1];
                nombre = URLDecoder.decode(nombre, StandardCharsets.UTF_8);
                email= URLDecoder.decode(email, StandardCharsets.UTF_8);
                
                String resultado = nombre+":"+email+"\n";
                
                if (!NewsHelper.correoRepetido("basedatos", email)) {
                    NewsHelper.escribirArchivo(resultado, "basedatos");
                } else {
                    System.err.println("CORREO REPETIDO");
                }

                //Ahora, comprobamos en el listado ya ya existe el usuario
                
                //Si existe, mandamos mensaje WARNING
                
                //Si no existe, mandamos mensaje SUCCESS

                responseHTML = String.format(Response.INDEX, "");

            } else { //GET
                responseHTML = String.format(Response.INDEX, "");
            }
        } catch (NumberFormatException e) { //ERROR
            responseHTML = Response.NOTFOUND;
            codigo = 400;
            System.err.println(e.getMessage()); // Muestra errores en la consola.
        }

        return construirResponse(codigo, responseHTML);
    }
}
