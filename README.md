# Fabrica Piezas

Estamos en una fábrica que produce piezas mecánicas. La fábrica cuenta con tres máquinas automáticas que generan piezas y las colocan en una cinta transportadora circular para que luego una máquina empaquetadora las recoja y las embale.

La cinta tiene una capacidad máxima de 5 piezas, por lo que si está llena, las máquinas deben esperar a que se libere espacio. Por otro lado, si la máquina empaquetadora intenta recoger una pieza y no hay ninguna disponible, también deberá esperar a que las máquinas depositen una.

**Dinámica:**

0. Las máquinas generan piezas cada 1–3 s y las ponen en la cinta si hay espacio; si está llena, esperan.  
1. El empaquetador retira una pieza cada 2 s; si no hay piezas, espera.  
2. Mostrar el estado de la cinta cada vez que se ponga o retire una pieza.

**¿Cuándo termina?**  
Cuando se fabriquen 50 piezas. Pero no pueden quedarse piezas en la cinta.

Mostrar el mensaje “Fábrica cerrada” cuando todos los hilos terminen.

---

# Practica Farmacia

## Versión 1: Simulación Cliente-Servidor de Farmacia

**Contexto:**  
La farmacia *FarmaciaViva* ha decidido digitalizar la venta de sus productos. Para ello, ha desarrollado un servidor que gestiona el stock de sus productos y recibe peticiones simuladas de clientes que desean comprar.

### Requisitos del sistema:

1. **Servidor:**
   - El servidor recibe una petición de stock:
     - Si hay suficiente stock, actualiza el stock y responde con éxito.
     - Si no hay suficiente, responde con el correspondiente mensaje.

2. **Productos:**
   - Hay solo 3 productos disponibles: Paracetamol, Ibuprofeno y Vitamina C.
   - Cada uno tiene un stock inicial (por ejemplo, 10 unidades).
   - El stock se mantiene en memoria con el manejo de variables.

3. **Simulación de clientes:**
   - Debes crear varios hilos cliente que simulan peticiones de compra al servidor.
   - Cada cliente solicitará un producto aleatorio y una cantidad entre 1 y 2.
   - Las peticiones deben realizarse de forma concurrente, y el servidor debe gestionar correctamente los accesos concurrentes al stock.

---

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
