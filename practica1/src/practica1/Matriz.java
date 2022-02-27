package practica1;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que guarda la lógica de operaciones
 * con matrices
 */
public class Matriz {
    // valores de la matríz
    private int[][] valores;

    /**
     * Función constructora
     * @param valores valores de la matríz
     * @throws IllegalArgumentException si se intenta crear una matríz
     *  que no sea de tamaño n x n (cuadrada)
     */
    public Matriz(int[][] valores) 
        throws IllegalArgumentException {

        if (valores.length != valores[0].length)
            throw new IllegalArgumentException(
                "Solo se permiten matrices de tamaño n x n"
            );
        
        this.valores = valores;
    }

    /**
     * Multiplica la matríz con otra dada
     * @param matriz la matriz a multiplicar
     * @return el resultado de la multiplicación
     */
    public Matriz multiplica(Matriz matriz) {
        int n = valores.length;
        int[][] resultado = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    resultado[i][j] += valores[i][k] * matriz.valores[k][j];
                }
            }
        }
        return new Matriz(resultado);
    }

    public Matriz multiplicaConcurrente(Matriz matriz) {
        MultipliacionConcurrente mc = new MultipliacionConcurrente(this, matriz);
        List<Thread> hilos = new ArrayList<>();
        int n = valores.length;

        for (int i = 0; i < n*n; i++) {
            Thread t = new Thread(mc, (i / n) + "," + (i % n));
            hilos.add(t);
            t.start();
        }

        for (Thread t: hilos) {
            try {
                t.join();
            } catch(InterruptedException e) {
                System.out.println("Error con el hilo " + t.getName() + ": " + e.getMessage());
            }
        }

        return mc.resultado();
    }

    public String toString() {
        String resultado = "";
        for (int i = 0; i < valores.length; i++) {
            resultado += "[ " + valores[i][0];
            for (int j = 1; j < valores.length; j++) {
                resultado += ", " + valores[i][j];
            }
            resultado += " ]\n";
        }
        return resultado;
    }

    /**
     * Clase auxiliar para la multiplicación concurrente
     * de dos matrices
     */
    class MultipliacionConcurrente implements Runnable{
        private Matriz a;
        private Matriz b;
        private Matriz c;

        public MultipliacionConcurrente(
            Matriz a,
            Matriz b
        ) {
            this.a = a;
            this.b = b;
            int n = a.valores.length;
            this.c = new Matriz(new int[n][n]);
        }

        @Override
        public void run() {
            String[] celda = Thread.currentThread().getName().split(",");
            calculaCelda(Integer.parseInt(celda[0]), Integer.parseInt(celda[1]));
        }

        public void calculaCelda(int fila, int columna) {
            int n = a.valores.length;
            for (int k = 0; k < n; k++) {
                c.valores[fila][columna] += a.valores[fila][k] * b.valores[k][columna];
            }
        }

        public Matriz resultado() {
            return c;
        }
    }
}
