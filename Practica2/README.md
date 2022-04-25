# Práctica 2

## Descripción 
Ejercicio 1. Algoritmo de Peterson

Ejercicio 2. Algoritmo de Filtro modificado (Peterson para más de 2 hilos)


## Ejecución

Para compilar:

```
cd Practica2/

javac -d ./classes src/practica2/test/*.java src/practica2/interfaces/*.java src/practica2/*.java
```

Para correr el test para el ejercicio 1:

```
java -cp ./classes practica2.test.FiltroLockTest
```

Para correr el test para el ejercicio 2:

```
java -cp ./classes practica2.test.PetersonLockTest
```