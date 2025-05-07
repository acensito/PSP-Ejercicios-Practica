
# Practica Farmacia

## Versión 2: Servidor Web Seguro con HTML y Fichero de Stock

**Contexto:**  
Ahora *FarmaciaViva* quiere que la venta de productos se haga directamente desde una página web accesible por navegador, donde los empleados de la farmacia introducen los pedidos manualmente. El servidor debe leer y actualizar un fichero de texto plano con el stock, y mantenerlo sincronizado frente a múltiples peticiones concurrentes.

### Requisitos del sistema:

1. **Servidor HTTPS con interfaz HTML:**
   - El servidor debe aceptar peticiones desde un formulario HTML que permita seleccionar un producto y una cantidad.
   - El servidor debe responder siempre con la misma página HTML, actualizada con mensajes de éxito o error tras una venta.

2. **Productos y almacenamiento:**
   - Hay tres productos: Paracetamol, Ibuprofeno y Vitamina C.
   - El stock se almacena en un fichero `stock.txt` con el siguiente formato:

     ```
     Paracetamol:10  
     Ibuprofeno:8  
     Vitamina C:12
     ```

   - Cada vez que se realiza una venta:
     1. Se debe leer el fichero.
     2. Verificar si hay stock suficiente.
     3. Actualizar el valor en el fichero.
     4. Escribir de nuevo el fichero.
   - Todo el proceso debe estar sincronizado para evitar condiciones de carrera.

3. **Petición y validación:**
   - El formulario envía una petición POST con los campos producto y cantidad.
   - El servidor debe validar:
     - Que el producto existe.
     - Que la cantidad no supere el stock.
     - Si no hay suficiente, debe mostrar un mensaje de error en la web:  
       *"Stock insuficiente para el producto seleccionado".*

4. **Seguridad:**
   - El servidor debe utilizar HTTPS y estar configurado con el *keystore* proporcionado.
   - Se debe rechazar cualquier ruta que no sea `/` con un mensaje de error o una página 404.
