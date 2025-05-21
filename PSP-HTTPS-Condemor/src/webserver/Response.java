
package webserver;

/**
 *
 * @author Felipe R.
 */
public class Response {
    // -- CABECERAS --
    public static final String CABECERA_OK = "HTTP/1.1 200 OK";
    public static final String CABECERA_NOTFOUND = "HTTP/1.1 404 Not Found";
    
    // -- CONTENIDO PAGINAS --
    
    //contenido index  
    public static final String INDEX = """
        <html>
        <head>
            <title>ChiquitoWiki</title>
            <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bulma@1.0.2/css/bulma.min.css'>
        </head>
        <body class='has-text-centered is-flex is-flex-direction-column is-justify-content-center is-align-items-center' style='height: 100vh;'>
            <section class='section'>
                <div class='container'>
                    <h1 class='title is-1 has-text-success'>Bienvenido ChikitoWiki</h1>
                    <h3 class='subtitle is-3'>Pulse un botón para ser ilustrado con una frase de Chiquito de la Calzada</h3>
                    <form action="/" method="post">
                        <div class="mt-8 field is-grouped is-justify-content-center">
                            <div class="control">
                                <button type="submit" class="button is-success is-medium" name="frase" value="true">AL ATAQUEEEER!!</button>
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
            <p class="is-size-2 has-text-weight-semibold">%s</p>  
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
