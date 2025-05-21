# 👮‍♂️ Guardia Civil: Operación Choricillo 

En este ejercicio te pondrás en la piel de la **Benemérita** en una jornada de patrulla, donde no paran de detener maleantes con apodos pintorescos, y el juzgado hace lo que puede por impartir justicia.

---

## 🎯 Objetivo

Simular mediante sockets TCP un sistema distribuido donde:

- Varias patrullas (clientes) hacen detenidos y los entregan en los calabozos para ser juzgados (servidor).
- Cada patrulla detiene a personajes de lo más variopintos (alias los generas aleatoriamente de una lista de nombres que quieras, los motes a ser posible marroneros).
- El Juez (servidor) juzgado recibe estos nombres y los almacena temporalmente en un calabozo.
- Cada cierto intervalo aleatorio, el juzgado juzga a un detenido
  - Si es culpable: **directo al talego**. 
  - Si es inocente: **a la calle, pero fichado**.
- El servidor imprime por consola cada juicio. Y al terminar las patrullas, muestra un menú con opciones.

---

## Requisitos del Proyecto  

### Ficheros principales (como mínimo):  
📄 `Juzgado.java`  
📄 `Patrulla.java`  
📄 `Lanzador.java`  

### 1. `Juzgado.java`  
- Escucha conexiones de clientes patrulla.
- Almacena los nombres recibidos en una cola.
- Cada pocos segundos (intervalo aleatorio), extrae un detenido y decide su destino.
- Muestra por su consola algo como:
```bash
Juzgando a 'El Gasofa'... Resultado: LIBERTAD con cargos.
Juzgando a 'La Loli'... Resultado: PRISIÓN preventiva.
```

### 2. `Patrulla.java`  
- Envian un *nombre de detenido* elegido aleatoriamente de un array por ejemplo (alias de barrio como "El Chispas", "La Pili", "El Topo").
- Muestran por consola:

```bash
[PATRULLA 2] Detenido 'El Chispas' detenido y puesto a disposición judicial.
```

### 3. `Lanzador.java`  
- Proveerá un menú con opciones:  
```bash
1. Seguir patrullando
2. Cerrar juzgado
3. Ver estadísticas
```` 
- Si seleccionamos `ver estadisticas`, mostraremos el número total de detenidos puestos a disposición judicial, ingresados en prisión y puesto en libertad con cargos.

