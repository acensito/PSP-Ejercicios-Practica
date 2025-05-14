# 📝 Enunciado del Proyecto: Registro Seguro a Videoconferencia

## 🎯 Objetivo
Desarrollar una aplicación cliente-servidor en Java que funcione como una página web accesible por HTTPS y permita registrar participantes en una videoconferencia. El registro se realizará a través de un formulario, y los datos se almacenarán en un archivo local de forma segura y concurrente.

---

## ✅ Requisitos Funcionales

1. **Interfaz web con formulario (HTTPS):**
   - La aplicación debe ofrecer una página web accesible por **HTTPS**.
   - La página contendrá un formulario donde el usuario pueda introducir:
     - Nombre del alumno
     - Correo electrónico

2. **Validación de datos:**
   - El campo de correo electrónico debe tener un formato válido.
   - No se debe permitir registrar dos veces el mismo correo electrónico.
   - Si los datos son inválidos o el correo ya existe, se debe mostrar un mensaje de error claro y ofrecer la posibilidad de reintentar mostrando el formulario nuevamente.

3. **Almacenamiento seguro de registros:**
   - Si los datos son correctos, se debe guardar la información en un archivo de texto llamado `videoconferencia.txt`.
   - Cada línea del archivo debe seguir el formato:
     ```
     nombre_alumno:correo@electronico
     ```

4. **Control de concurrencia:**
   - Se debe garantizar el acceso seguro al archivo cuando múltiples usuarios se registren simultáneamente (uso de `synchronized`).

5. **Cifrado del archivo (opcional):**
   - El archivo `videoconferencia.txt` puede almacenarse de forma cifrada usando un algoritmo simétrico como **AES**.
   - En este caso, se debe poder cifrar al guardar y descifrar al leer.

6. **Ruta adicional para consultar registros (opcional):**
   - Se puede implementar una segunda ruta accesible por navegador (ej: `/listado`) que muestre una lista con los alumnos registrados hasta el momento.

---

## 🔧 Recomendaciones Técnicas

- Utilizar `synchronized` o bloques `synchronized` sobre un objeto común para proteger el acceso al archivo.
- Para la validación del correo electrónico, se puede usar una expresión regular, aunque solamente se requiere comprobar que no existe en el archivo.
---

## 🎯 Objetivo Final

El sistema debe ser accesible mediante un navegador web, registrar usuarios de forma segura y concurrente, y reflejar adecuadamente tanto los éxitos como los errores durante el registro.
