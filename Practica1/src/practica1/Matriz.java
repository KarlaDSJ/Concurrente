package practica1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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


    public Matriz(String tam) {
        int i = 0;  
        int t = Integer.parseInt(tam);
        valores = new int[t][t];
        try {
            
          File myObj = new File("Practica1/src/practica1/Matrices/mat"+tam);          
          Scanner myReader = new Scanner(myObj);
          while (myReader.hasNextLine()) {
            String data = myReader.nextLine();            
            String[] temp = data.split(" ");
            for (int j = 0; j < temp.length; j++) {
                valores[i][j] = Integer.parseInt(temp[j]);
            }  
            i++;        
          }
          myReader.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
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
        List<Thread> hilosh = new ArrayList<>();
        int n = valores.length;
        int hilos = 10;  

        try {

        for(int i = 0; i < n; i++){
            Thread t = new Thread(mc, (i / n) + "," + (i % n));
            hilosh.add(t);
            t.start();
    
            if(hilosh.size() == hilos){
                for(var threads: hilosh){
                        threads.join();
                    }
                    hilosh.clear();
            }
        }
        
        for(Thread threads: hilosh){            
                threads.join();
        }
        
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

        return mc.resultado();
    }

    @Override
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
