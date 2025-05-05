
package webserver;

import webserver.response.Response;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Felipe2T
 */
public class Servidor {
    //atributos de la clase
    private static final int PORT = 8443;
    private static final File BD_FARMACIA = new File("farmacia.txt");
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //configuramos servidor SSL
            SSLServerSocket serverSocketSSL = configServerSSL();
            //mensaje de inicio
            mensajeInicio();
            //iniciamos el loop de hilos de cliente
            iniciarServerLoop(serverSocketSSL);
            
        } catch (IOException | KeyManagementException | KeyStoreException | 
                NoSuchAlgorithmException | UnrecoverableKeyException | 
                CertificateException e) {
            System.err.println("SSL Server ERROR: " + e.getLocalizedMessage());
        }
    }
    
    // -- METODOS AUXILIARES --
    
    /**
     * carga el keystore desde el archivo AlmacenSSL
     * @return devuelve el keystore cargado.
     * @throws FileNotFoundException si no se encuentra el archivo AlmacenSSL
     * @throws IOException si hay un error de entrada/salida
     * @throws NoSuchAlgorithmException si no se encuentra el algoritmo de cifrado
     * @throws KeyStoreException si hay un error al cargar el keystore
     * @throws CertificateException si hay un error al cargar el certificado
     */
    private static KeyStore cargaKeyStore() throws FileNotFoundException, 
            IOException, NoSuchAlgorithmException, KeyStoreException, 
            CertificateException {
        
        //se crea almacen de claves de tipo JKS
        KeyStore keyStore = KeyStore.getInstance("JKS");
        //abrimos el archivo AlmacenSSL y se carga en memoria con la key especificada
        try (FileInputStream keyFile = new FileInputStream("AlmacenSSL")){
            keyStore.load(keyFile, "123456".toCharArray());
        }
        //devolvemos la keystore
        return keyStore;
    }
    
    /**
     * configura el servidor SSL con el keystore y el algoritmo de cifrado.
     * @return devuelve un socket SSLServerSocket configurado.
     * @throws IOException si hay un error al crear el socket.
     * @throws FileNotFoundException si no se encuentra el archivo AlmacenSSL
     * @throws NoSuchAlgorithmException si no se encuentra el algoritmo de cifrado
     * @throws KeyStoreException si hay un error al cargar el keystore
     * @throws CertificateException si hay un error al cargar el certificado
     * @throws UnrecoverableKeyException si no se puede recuperar la clave
     * @throws KeyManagementException si hay un error al inicializar el contexto SSL
     */
    private static SSLServerSocket configServerSSL() throws IOException, 
            FileNotFoundException, NoSuchAlgorithmException, KeyStoreException, 
            CertificateException, UnrecoverableKeyException, KeyManagementException {
        
        //cargamos el keystore
        KeyStore ks = cargaKeyStore();
        //inicializamos el gestor de claves con algoritmo SunX509
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        //inicializamos el gestor con su keystore y contraseña
        kmf.init(ks, "123456".toCharArray());
        //obtenemos una instancia de tipo SSL/TLS
        SSLContext sslContext = SSLContext.getInstance("TLS");
        //inicializamos el contexto con los gestores de datos que se han cargado
        sslContext.init(kmf.getKeyManagers(), null, null);
        //inicializamos el factory de sockets SSL
        SSLServerSocketFactory sslFactory = sslContext.getServerSocketFactory();

        //creamos, lanzamos y devolvemos el servidor escuchando en el puerto 8443
        return (SSLServerSocket) sslFactory.createServerSocket(PORT);
    }

    /**
     * muestra un mensaje de inicio del servidor.
     */
    private static void mensajeInicio() {
        System.out.printf("""
            +-------------------------------+
            |  Servidor WEB en ejecución    |
            | Escuchando en el puerto: %d |
            +-------------------------------+
            Accede desde tu navegador a https://localhost:%d
            """, PORT, PORT);
    }
    
    /**
     * inicia el bucle de espera de conexiones entrantes y lanza un hilo para cada conexión.
     * @param serverSocketSSL socket SSLServerSocket configurado.
     */
    private static void iniciarServerLoop(SSLServerSocket serverSocketSSL) throws IOException {
        //bucle para controlar las conexiones entrantes
        while (true) {
            //esperamos y aceptamos las conexiones entrantes
            SSLSocket cliente = (SSLSocket) serverSocketSSL.accept();
            //creamos un nuevo hilo para manejar la conexion entrante
            new HiloCliente(cliente).start();
        }
    }
    
    private static class HiloCliente extends Thread {
        //atributos de la clase
        private final Socket skClient; //conexion con el socket del cliente
        
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
                //String metodo = partes[0];
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
                if (peticion.startsWith("POST") && contentLength > 0) {
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
                    pr.println(entry.getKey() + ":" + entry.getValue());
                }
            } catch (IOException e) {
                System.err.println("Error al escribir el archivo: " + e.getMessage());
                return false;
            }
            
            return true;
        }

        
    }
    
}
