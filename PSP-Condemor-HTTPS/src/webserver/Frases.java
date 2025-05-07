
package webserver;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Felipe2T
 */
public class Frases {

    private static final List<String> FRASES = Arrays.asList(
        "¡Fistro pecador de la pradera!",
        "¡Cobarde! ¡Te voy a borrar el cerito sexuar!",
        "¡Hasta luego, Lucas!",
        "¡No puedor, no puedor!",
        "¿Comorl?",
        "¿Te das cuen?",
        "¡Por la gloria de mi madre!",
        "¡Eres más lento que el caballo del malo!",
        "¡Jaaaarl! ¡Eres un fistro diodenal!",
        "¡Pecador! ¡Que te meto con el mechero!",
        "¡A canneeee!",
        "¡Te voy a dar con el dedo que tengo señalando a Cuenca!",
        "¡Te voy a dar con el látigo de mi indiferencia!",
        "¡Fistro! ¡Hasta luego Maricarmen!",
        "¡Que viene el torpedo!",
        "¡Tienes menos luces que un barco pirata!",
        "¡La suegraaa... que viene la suegraaa!",
        "¡Le dije: '¿Dónde vaaaas?' y me dijo: 'a la comisaría'!",
        "¡Eres más feo que un frigorífico por detrás!",
        "¡Lo maté de un susto, pecador!",
        "¡Que te meto con el trabuco del amor!"
    );

    private static final Random RANDOM = new Random();

    public static String obtenerFrase() {
        return FRASES.get(RANDOM.nextInt(FRASES.size()));
    }
}
