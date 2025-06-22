package proyectobasehotel;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HiloCliente extends Thread {

    private final Socket skClient;

    public HiloCliente(Socket skClient) {
        this.skClient = skClient;
    }

    @Override
    public void run() {
        try (
            BufferedReader entrada = new BufferedReader(new InputStreamReader(skClient.getInputStream()));
            PrintWriter salida = new PrintWriter(skClient.getOutputStream(), true, StandardCharsets.UTF_8)
        ) {
            String peticion = entrada.readLine();
            if (peticion == null || (!peticion.startsWith("GET") && !peticion.startsWith("POST"))) return;

            System.out.println(peticion);

            String[] partes = peticion.split(" ");
            String metodo = partes[0], ruta = partes[1];

            int contentLength = 0;
            String linea;
            while (!(linea = entrada.readLine()).isBlank()) {
                System.out.println("linea: " + linea);
                if (linea.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(linea.substring(16).trim());
                }
            }

            String cuerpo = "";
            if ("POST".equals(metodo) && contentLength > 0) {
                char[] buffer = new char[contentLength];
                entrada.read(buffer);
                cuerpo = new String(buffer);
                System.out.println(cuerpo);
            }

            String respuesta = switch (ruta) {
                case "/" -> procesarReserva(cuerpo);
                default -> construirResponse(404, Paginas.html_noEncontrado);
            };

            salida.println(respuesta);

        } catch (Exception e) {
            System.err.println("Error en el hilo cliente: " + e.getMessage());
        }
    }

    private String procesarReserva(String cuerpo) {
        try {
            if (!cuerpo.isEmpty()) { //POST
                String[] datos = cuerpo.split("&");
                String dia = datos[0].split("=")[1];
                String cantidad = datos[1].split("=")[1];
                ArchiveHelper.guardarDatos(dia, cantidad);
            }
            return construirResponse(200, Paginas.html_reservas);
        } catch (Exception e) {
            System.err.println("Error al procesar reserva: " + e.getMessage());
            return construirResponse(404, Paginas.html_noEncontrado);
        }
    }

    private String construirResponse(int codigo, String contenido) {
        return "HTTP/1.1 " + (codigo == 200 ? "200 OK" : "400 Bad Request") + "\n"
             + "Content-Type: text/html; charset=UTF-8\n"
             + "Content-Length: " + contenido.length() + "\n\n"
             + contenido;
    }
}
