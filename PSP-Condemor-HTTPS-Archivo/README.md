# 🕸️ Práctica: Servidor HTTPS con respuesta aleatorias de Chiquito de la Calzada

## 🧾 Descripción

El objetivo de esta práctica es desarrollar una aplicación **cliente-servidor HTTPS en Java** que sirva una página HTML interactiva. El servidor debe generar dinámicamente una página HTML con un botón titulado **"AL ATAQUERRR!!"**. Al pulsar dicho botón, se enviará una **petición POST** al servidor. Este devolverá la misma página HTML con una **frase aleatoria de Chiquito de la Calzada** justo debajo del botón.

## 🎯 Requisitos

- El servidor debe funcionar bajo el protocolo **HTTPS** utilizando un certificado SSL (autofirmado para pruebas locales).
- El contenido HTML debe generarse manualmente como **texto** dentro del código Java.
- El botón debe estar contenido en un formulario con método `POST`.
- Al hacer clic en el botón, se debe mostrar en la misma página una **frase aleatoria** obtenida de una colección de frases predefinidas de Chiquito de la Calzada procedente de un archivo `frases.txt`.
- El servidor debe ser capaz de manejar múltiples peticiones concurrentes utilizando **hilos** (threads).
- El servidor debe ser capaz de manejar excepciones y errores de forma adecuada, devolviendo un mensaje de error en caso de fallo.
- El servidor debe ser capaz de cerrar conexiones de forma segura y liberar recursos al finalizar.
- Debe de gestionarse la concurrencia de peticiones utilizando metodos sincronizados o bloqueos (locks) para evitar problemas de acceso concurrente a los recursos compartidos.
- Implementar un sistema de logging para registrar las peticiones y respuestas del servidor. 
Por ejemplo: 
```log
[2017-05-18 14:04:07] [INFO] peticion recibida y procesada por el servidor
[2017-05-18 14:04:07] [ERROR] error al procesar la peticion recibida
```

### 🔑 Certificado SSL
- Para generar un certificado SSL autofirmado, puedes utilizar el siguiente comando de `keytool`:

```bash
keytool -genkeypair -alias mydomain -keyalg RSA -keystore keystore.jks -storepass password -validity 365
```
Donde `mydomain` es el alias del certificado, `keystore.jks` es el nombre del archivo donde se guardará el certificado, `password` es la contraseña del keystore y `365` es el número de días de validez del certificado.
- Asegúrate de que el keystore se encuentre en la misma carpeta que tu código Java o proporciona la ruta completa al archivo.
- Asegúrate de que el puerto que elijas para el servidor HTTPS esté disponible y no esté siendo utilizado por otro servicio. Puedes elegir un puerto como `8443`.

### 🔌 Posibles mejoras/alternativas optativas a realizar
- Obtener las frases de un archivo `frases.txt` en lugar de tenerlas codificadas en el programa.
- Añadir un contador de peticiones para mostrar cuántas veces se ha pulsado el botón.

## 💡 Comportamiento esperado

1. El cliente accede a `https://localhost:puerto/`.
2. Se muestra una página HTML con un título, un botón `"AL ATAQUERRR!!"` y (en principio) ningún mensaje.
3. Al pulsar el botón, se realiza una petición `POST` al servidor.
4. El servidor responde con la misma página HTML, pero incluyendo una **frase aleatoria** bajo el botón.

<img src="https://i.ibb.co/PG1R9nNn/Screenshot-2025-05-07-at-21-53-48-Chiquito-Wiki.png" alt="Screenshot-2025-05-07-at-21-53-48-Chiquito-Wiki" border="0">
<img src="https://i.ibb.co/mrnfdG5K/Screenshot-2025-05-07-at-21-15-55-Chiquito-Wiki.png" alt="Screenshot-2025-05-07-at-21-15-55-Chiquito-Wiki" border="0">
<img src="https://i.ibb.co/RkpdxgsD/Screenshot-2025-05-07-at-21-53-59-Chiquito-Wiki.png" alt="Screenshot-2025-05-07-at-21-53-59-Chiquito-Wiki" border="0">
<img src="https://i.ibb.co/NgrSwSXT/Screenshot-2025-05-07-at-21-57-21-Chiquito-Wiki.png" alt="Screenshot-2025-05-07-at-21-57-21-Chiquito-Wiki" border="0">

## 📚 Sugerencias
- Se recomienda generar el certificado SSL con la herramienta `keytool`.
- La colección de frases puede estar implementada como una lista estática en Java.
- La lógica para elegir una frase debe asegurar aleatoriedad.


