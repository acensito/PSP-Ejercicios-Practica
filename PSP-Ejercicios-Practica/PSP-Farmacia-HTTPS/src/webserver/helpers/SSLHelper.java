
package webserver.helpers;

import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 *
 * @author Felipe2T
 */
public class SSLHelper {
    public static final int PORT = 8443;
    private static final String KEYSTORE_FILE = "AlmacenSSL";
    private static final String PASSWORD = "123456";
            
    public static SSLServerSocket configServerSSL() throws Exception {
        
        //cargamos el keystore
        KeyStore ks = cargaKeyStore();
        //inicializamos el gestor de claves con algoritmo SunX509
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        //inicializamos el gestor con su keystore y contraseña
        kmf.init(ks, PASSWORD.toCharArray());
        //obtenemos una instancia de tipo SSL/TLS
        SSLContext sslContext = SSLContext.getInstance("TLS");
        //inicializamos el contexto con los gestores de datos que se han cargado
        sslContext.init(kmf.getKeyManagers(), null, null);
        //inicializamos el factory de sockets SSL
        SSLServerSocketFactory sslFactory = sslContext.getServerSocketFactory();

        //creamos, lanzamos y devolvemos el servidor escuchando en el puerto 8443
        return (SSLServerSocket) sslFactory.createServerSocket(PORT);
    }
    
    private static KeyStore cargaKeyStore() throws Exception {
        //se crea almacen de claves de tipo JKS
        KeyStore keyStore = KeyStore.getInstance("JKS");
        //abrimos el archivo AlmacenSSL y se carga en memoria con la key especificada
        try (FileInputStream keyFile = new FileInputStream(KEYSTORE_FILE)){
            keyStore.load(keyFile, PASSWORD.toCharArray());
        }
        //devolvemos la keystore
        return keyStore;
    }
}
