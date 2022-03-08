package practica1;

import practica1.filtros.Imagen;

public class Practica1 {
    
    public static void main(String[] args) {

        System.out.println("--- Multiplicación de matrices ---");
        Matriz a = new Matriz(
            new int[][] {
                {1, 1, 0, 5, 6},
                {2, -1, 2, 7, 1},
                {0, 3, 0, 8, 9},
                {9, 4, -6, 8, 9},
                {0, 7, 0, 1, -9}
            }
        );
        System.out.println("A = \n" + a);
        Matriz b = new Matriz(
            new int[][]{
                {1, 2, 5, 4, -6},
                {3, -1, 3, 4, 1},
                {5, 2, 8, -2, 8},
                {2, 6, 3, 8, 2},
                {-5, 2, 1, -9, 1}
            }
        );
        System.out.println("B = \n" + b);

        long timestamp = System.nanoTime();
        Matriz c1 = a.multiplica(b);
        long ms = System.nanoTime() - timestamp;
        System.out.println("Resultado secuencial: \n" + c1);
        System.out.println("Tiempo transcurrido: " + ms);

        timestamp = System.nanoTime();
        Matriz c2 = a.multiplicaConcurrente(b);
        ms = System.nanoTime() - timestamp;
        System.out.println("Resultado concurrente: \n" + c2);
        System.out.println("Tiempo transcurrido: " + ms);

        args = new String[] {
            "Practica1/src/practica1/filtros/ejemplo.jpeg", "10"
        };

        try {
            String rutaImagen = args[0];
            int filtro = Integer.parseInt(args[1]);

            if (args.length != 2)
                throw new IllegalArgumentException("Tienes que meter 2 parametros");

            Imagen img = new Imagen(rutaImagen);
            System.out.println("\nAplicando filtro secuencial");
            timestamp = System.nanoTime();
            img.aplicarFiltro(filtro, true);
            ms = System.nanoTime() - timestamp;
            System.out.println("Tiempo transcurrido: " + ms);
            ms = ms / 1000000000l;
            System.out.println("Tiempo transcurrido es segundos: " + ms);

            img.reset();

            System.out.println("\nAplicando filtro concurrente");
            timestamp = System.nanoTime();
            img.aplicarFiltro(filtro, false);
            ms = System.nanoTime() - timestamp;
            System.out.println("Tiempo transcurrido: " + ms);
            ms = ms / 1000000000l;
            System.out.println("Tiempo transcurrido es segundos: " + ms);

            img.mostrarImagen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}