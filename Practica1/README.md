# Práctica 1

## Descripción 
Ejercicio 1. Multiplicación de matrices 

Ejercicio 2. Aplicar varios filtros a una imagen 

Ambos ejercicios realizados de manera secuencial y concurrente


## Ejecución

Para compilar:

```
cd Practica1/

javac -d ./classes src/practica1/Practica1.java src/practica1/filtros/*.java src/practica1/matrices/*.java
```

Para correr el programa:

```
cd Practica1/ 

java -cp ./classes practica1.Practica1 10 src/practica1/filtros/ejemplos/chica.png 3 true
```

El primer parámetro indica que ejemplo usar para la multiplicación de matrices (10 indica que utilizaremos el archivo mat10 que contiene una matriz de 10x10), el segundo parámetro es la ruta de la imagen a la que le aplicaremos el filtro, el tercer parámetro indica el número de filtro que aplicaremos de la siguiente lista:


 1. Gray = (Red+Green+Blue)/3
 2. Gray = (Red*.03+Green0.59+Blue*0.11)
 3. Gray = (Red*.2126+Green0.7152+Blue*0.0722)
 4. Gray = pixel.getRed()
 5. Altro contraste
 6. Componentes RGB
 7. Blur (matriz 3x3)
 8. Blur (matriz 5x5)
 9. Blur (matriz 9x9)
 10. Sharpen 

El último parámetro indica si, tanto la multiplicación de matrices (ejercicio 1) como los filtros, se aplicarán de manera secuencial (true) o concurrente (false).

