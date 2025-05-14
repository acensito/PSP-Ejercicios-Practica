# 🐝🍯 Ejercicio Productor-Consumidor: Las Abejas y el Oso Goloso

## 🧩 Descripción del problema

Simularemos un sistema de **productor-consumidor**  con 🐝 *abejas recolectoras de néctar* y un 🧸 *oso goloso* que se despierta para comer miel.

Las **abejas** actúan como **productores** que depositan néctar en una **colmena como recurso compartido**, mientras que el **oso** es el **consumidor** que se despierta cuando hay suficiente miel para comer.

---

## 🎯 Objetivo

Implementar una simulación concurrente en Java utilizando hilos, sincronización y control de acceso a un recurso compartido (la colmena) por hilos productores y consumidores.

---
## ⚙️ Reglas del sistema

- La **colmena** (buffer compartido) tiene una **capacidad máxima de 10 unidades de miel**.
- Hay dos **abejas** (hilos productores) que recolectan néctar y depositan 1 unidad de miel en la colmena. Tras depositar, se marchan en busca de más néctar, por lo que tardarán un tiempo aleatorio entre 0 y 2 segundos en recolectar más.
- Las abejas **no pueden depositar miel** si la colmena está llena.
- El **oso** (hilo consumidor):
  - Se despierta y solo puede consumir de la colmena **solo cuando hay al menos 5 unidades de miel**.
  - Se **come toda la miel** acumulada y luego se duerme, volviendo a despertar cuando hay al menos 5 unidades de miel.
  - Repite esto hasta que haya comido **3 veces**.
- Tras la **tercera comilona**, el oso queda satisfecho y se va a **hibernar**.
- Cuando el oso hiberna, las abejas **se retiran tras rellenar la colmena** y el programa **termina ordenadamente**.
- El acceso a la colmena debe estar correctamente **sincronizado** usando `synchronized`, `wait()` y `notifyAll()`.

---

## 🧪 Requisitos técnicos

- Utilizar clases que implementen `Thread o Runnable` para modelar a las abejas y al oso.
- Usar métodos sincronizados para controlar el acceso al recurso compartido (`Colmena`).
- Implementar correctamente las señales de espera y notificación entre hilos (`wait()`, `notifyAll()`).
- Mostrar mensajes en consola que describan la actividad, por ejemplo:
  - `🐝 Abeja-1 trajo néctar. Total miel: 7`
  - `🧸 Comilona #3. Miel antes: 8`
  - `🧸 El oso está lleno. ¡Hora de hibernar!`

---

## 💡 Posible salida por consola
```bash
🐝 Abeja-1 trajo néctar. Total miel: 1
🐝 Abeja-2 trajo néctar. Total miel: 2
...
🧸 Comilona #1. Miel antes: 6
🧸 El oso se marcha feliz...

...

🧸 Comilona #3. Miel antes: 7
🧸 El oso está lleno. ¡Hora de hibernar!
🐝 Abeja-1 se retira. El oso ya duerme.
🐝 Abeja-2 se retira. El oso ya duerme.
...
🌙 Todas las abejas se han ido a dormir. Fin del programa.
```
---

## 🧠 Objetivos de aprendizaje

- Comprender y aplicar el patrón **productor-consumidor**.
- Usar correctamente la **sincronización de hilos** en Java (`wait()` / `notifyAll()`).
- Diseñar una solución concurrente que **finalice de forma controlada**.
- Manejar múltiples hilos con condiciones de parada bien definidas.