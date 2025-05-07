
package webserver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Felipe2T
 */
public class LogHelper {
    //atributos de la clase
    private static final Logger logger = Logger.getLogger(LogHelper.class.getName());
    private static final Formatter CUSTOM_FORMATTER = new CustomFormatter();
    private static final FileHandler handler = createHandler();
    
    /**
     * metodo que se encargar de crear el archivo de log
     * @return FileHandler que se encarga de crear el archivo de log
     */
    private static FileHandler createHandler() {
        try {
            //se crea un manejador del fichero, que gestiona concurrencia.
            //se indica en append: true, para que vaya añadiendo lineas al archivo
            FileHandler fh = new FileHandler("logErrores.txt", true);
            //se le indica que el formato es personalizado
            fh.setFormatter(CUSTOM_FORMATTER);
            return fh;
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    /**
     * bloque estatico que se encarga de inicializar el logger
     */
    static {
        try {
            //se añade el manejador al logger
            logger.addHandler(handler);
            //desactivamos los mensajes en la consola
            logger.setUseParentHandlers(false);
        } catch (SecurityException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Metodo que se encarga de escribir el mensaje en el log de tipo INFO
     */
    public static void logInfo() {
        logger.info("peticion recibida y procesada por el servidor");
    }
    
    /**
     * Metodo que se encarga de escribir el mensaje en el log de tipo ERROR
     */
    public static void logError() {
        logger.log(Level.SEVERE, "error al procesar la peticion recibida");
    }
    
    /**
     * metodo que se encarga de cerrar el logger
     */
//    private static void cierraLogger() {
//        handler.close();
//    }

    /**
     * Clase que se encarga de formatear el mensaje del log
     * @url https://www.logicbig.com/tutorials/core-java-tutorial/logging/customizing-default-format.html
     */
    private static class CustomFormatter extends SimpleFormatter {
        //se define el formato de la fecha
        private static final DateTimeFormatter DATE_FORMATTER 
                = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        @Override
        public String format(LogRecord record) {
            //se define la fecha
            String date = LocalDateTime.now().format(DATE_FORMATTER);
            //se define el nivel de log
            String level = record.getLevel().toString();
            //devolvemos el mensaje formateado con el nivel que queremos    
            return String.format("[%s] [ %s ] %s\n", date, level, record.getMessage());
        }
    }
}
