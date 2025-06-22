
package proyectobasehotel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author Felipe2T
 */
public class HiloCliente extends Thread {
    
    private final Socket skClient;

    public HiloCliente(Socket skclient) {
        this.skClient = skclient;
    }

    @Override
    public void run() {
        
        try (
            BufferedReader entrada = new BufferedReader(new InputStreamReader(skClient.getInputStream()));
            PrintWriter salida = new PrintWriter(skClient.getOutputStream(), true, StandardCharsets.UTF_8)
        ){
            String peticion = entrada.readLine();
            
            if (peticion == null || (!peticion.startsWith("GET") && !peticion.startsWith("POST"))) {
                return;
            }
            
            System.out.println(peticion);
            
            String[] partes = peticion.split(" ");
            String metodo = partes[0];
            String ruta = partes[1];
            
            //leer encabezados & contentlength
            String linea;
            int contentLength = 0;
            //content-length
            while (!(linea = entrada.readLine()).isBlank()) {                
                System.out.println("linea: " + linea);
                if (linea.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(linea.substring(16));
                }
            }
            //cuerpo
            StringBuilder cuerpo = new StringBuilder(); 
            if (metodo.equals("POST") && contentLength > 0) {
                char[] buffer = new char[contentLength];
                entrada.read(buffer, 0, contentLength);
                cuerpo.append(buffer);
            }
            
            //reponse
            String response;
            if (ruta.equals("/")) {
                response = generarCita(cuerpo.toString());
            } else {
                response = construirResponse(404, Paginas.html_noEncontrado);
            }
            
            salida.println(response);
            
        } catch (Exception e) {
            System.err.println("Error en el hilo cliente: " + e.getMessage());
        }

    }
    
    private String construirResponse(int codigo, String contenido) {
        return (codigo == 200 ? "HTTP/1.1 200 OK" : "HTTP/1.1 400 Bad Request") 
               + "\n"                                           //Linea inicial
               + "Content-Type: text/html; charset=UTF-8"+ "\n" //Metadatos
               + "Content-Length: " + contenido.length() + "\n" //c-l
               + "\n"                                           //Línea vacía
               + contenido;                                     //Cuerpo
    }

    private String generarCita(String cuerpo) {
        
        int CODIGO = 200;
        String responseHTML;
        
        //debug
        System.out.println(cuerpo);
        try {
            if (!cuerpo.isEmpty()) { //POST
                
                String[] datos = cuerpo.split("&");
                String dia = datos[0].split("=")[1];
                String cantidad = datos[1].split("=")[1];
                
                ArchiveHelper.guardarDatos(dia, cantidad);

                responseHTML = construirResponse(CODIGO, Paginas.html_reservas);
            } else { //GET
                responseHTML = construirResponse(CODIGO, Paginas.html_reservas);
            }
        } catch (Exception e) {
            responseHTML = construirResponse(404, Paginas.html_noEncontrado);
            System.err.println(e.getMessage());
        }
        
        return responseHTML;
    }
    
}
