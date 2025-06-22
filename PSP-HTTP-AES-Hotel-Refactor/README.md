# practica Hotel PelHilos a la Mar

Debes implementar un servidor HTTP en Java que gestione de forma concurrente las reservas de habitaciones de un hotel.  
Imagina un hotel donde, en cada recepción (tiene varias), el empleado accede en el ordenador a la página web de tu servidor. En ella:

1. Selecciona el día de la semana (lunes, martes… domingo) mediante un desplegable.  
2. Introduce el número de habitaciones que desea reservar para ese día.

En la carpeta raíz del proyecto os encontraréis siete ficheros de texto vacíos (`lunes.txt`, `martes.txt`….) y en cada uno se irá almacenando únicamente el valor total de las reservas acumuladas.  
Cada vez que se registre una reserva, el servidor deberá actualizar el fichero correspondiente.  
Por ejemplo, tras la primera reserva de 5 habitaciones, el fichero solo tendrá el número sin más: `5` y ese valor se iría actualizando con las siguientes reservas.  
Cualquier otro empleado registra otra reserva de 7 habitaciones, el fichero se actualizará y contendrá el valor `12`.  
Al final del enunciado tienes unas capturas de pantalla con este ejemplo. De esta forma, el gerente del hotel puede ver en un momento determinado las reservas de un día.  
Los ficheros siempre deben estar cifrado mediante el algoritmo AES, utilizando la clase `Cipher`.  
Por este motivo, cada vez que actualices el valor muéstralo por pantalla para verificar que lo estás actualizando correctamente, ya que si abres el fichero manualmente, no vas a poder ver el número al estar cifrado.

---

En el proyecto base ya tienes:  
- `Paginas.java` con las páginas HTML.  
- La plantilla de página principal y única, `html_reservas`.  
- La plantilla de página de error `html_noEncontrado` para rutas no contempladas.

## Tu servidor debe:

### 1. Atender rutas

- `GET /`: devolver la página de reservas.  
- `POST /`: Gestionar la reserva.  
- Cualquier otra ruta: devolver `html_noEncontrado` con estado HTTP 404.

### 2. Gestionar el fichero cifrado

- Leer los valores introducidos en el formulario que lo recibirás en el cuerpo de la petición POST como:  
  `dia=x&cantidad=y`
- Descifrar `x.txt` (siendo x el día).  
- Aumentar el número de reservas solicitado.  
- Volver a cifrar `x.txt` (siendo x el día).

### 3. Concurrencia y sincronización

- Cada petición la atiende su propio hilo.  
- La sección crítica (`descifrar → leer/actualizar → re-cifrar → escribir`) debe protegerse con sincronización (`synchronized`) para evitar condiciones de carrera sobre el fichero. Es decir, cuando un hilo esté actualizando el fichero (descifrar, actualizar, y cifrar), ningún otro hilo podrá acceder a ese fichero hasta que se complete el proceso.

### 4. Capturas de pantalla

<img src="https://i.ibb.co/jvSFgBdp/Captura.png" alt="Captura" border="0">

<img src="https://i.ibb.co/Y4qsFQ1C/Captura2.png" alt="Captura2" border="0">