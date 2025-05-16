
package webserver.response;

/**
 *
 * @author Felipe2T
 */
public class Response {
    //cabeceras
    public static final String CABECERA_OK = "HTTP/1.1 200 OK";
    public static final String CABECERA_NOTFOUND = "HTTP/1.1 404 Not Found";
    
    // -- CONTENIDO PAGINAS --
    //------------------------
    
    //contenido index  
    public static final String INDEX = """
        <html>
        <head>
            <title>Farmacia de Guardia</title>
            <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bulma@1.0.2/css/bulma.min.css'>
        </head>
        <body class='has-text-centered is-flex is-flex-direction-column is-justify-content-center is-align-items-center' style='height: 100vh;'>
            <section class='section'>
                <div class='container'>
                    <h1 class='title is-1 has-text-success'>Bienvenido a la Farmacia de Guardia</h1>
                    <h3 class='subtitle is-3'>Seleccione un medicamento e introduzca cantidad</h3>
                    <form action="/" method="post">
                        <div class="mt-6 field is-grouped is-justify-content-center">
                            <div class="control">
                                <div class="select is-medium">
                                    <select name="medicamento">
                                        <option value="paracetamol">Paracetamol</option>
                                        <option value="ibuprofeno">Ibuprofeno</option>
                                        <option value="vitamina">Vitamina C</option>
                                    </select>
                                </div>
                            </div>
                            <div class="control">
                                <input class="input is-medium" type="text" name="cantidad" placeholder="Introduzca la cantidad" required>
                            </div>
                            <div class="control">
                                <button type="submit" class="button is-success is-medium">Enviar</button>
                            </div>
                        </div>
                    </form>
                    %s
                </div>
            </section>
        </body>
        </html>
    """;
    
    //contenido resultado INFO
    public static final String INFO = """
        <div class="notification is-info mt-4">
            <p>%s</p>  
        </div>
    """;
    
    //contenido resultado
    public static final String ERROR = """
        <div class="notification is-danger mt-4">
            <p>%s</p>  
        </div>
    """;
    
    //contenido no encontrado
    public static final String NOTFOUND = """
        <html>
        <head>
            <title>404 - No encontrada</title>
            <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bulma@1.0.2/css/bulma.min.css'>
        </head>
        <body class='has-text-centered is-flex is-flex-direction-column is-justify-content-center is-align-items-center' style='height: 100vh;'>
            <section class='section'>
                <div class='container'>
                    <h1 class='title is-1 has-text-danger'>¡ERROR! Página no encontrada</h1>
                    <h3 class='subtitle is-4 my-3'>La página que solicitaste no existe en nuestro servidor</h3>
                    <a href='/' class='button is-primary mt-4'>Volver al inicio</a>
                </div>
            </section>
        </body>
        </html>
    """;
}
