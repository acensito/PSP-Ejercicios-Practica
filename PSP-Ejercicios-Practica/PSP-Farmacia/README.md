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