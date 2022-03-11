package practica1;

import practica1.filtros.Imagen;
import practica1.matrices.Matriz;

public class Practica1 {
 
    public static void main(String[] args) {

        try {
            if (args.length != 5)
                throw new IllegalArgumentException("Tienes que dar 5 parámetros: matriz ruta opFiltro secuencial? num_hilos");
            
            String rutaImagen = args[1];
            int filtro = Integer.parseInt(args[2]);
            boolean sec = Boolean.parseBoolean(args[3]);
            int num_hilos = Integer.parseInt(args[4]);

            System.out.println("--- Multiplicación de matrices ---");
            Matriz a = new Matriz(args[0]);        

            long timestamp = System.nanoTime();
            Matriz mul;
            mul = sec? a.multiplica(a): a.multiplicaConcurrente(a, num_hilos); 
            long ms = System.nanoTime() - timestamp; 
            System.out.println("Tiempo transcurrido: " + ms); 

            Imagen img = new Imagen(rutaImagen);
            String cadena = sec? "secuencial" : "concurrente";
            System.out.println("\nAplicando filtro "+ cadena);
            img.aplicarFiltro(filtro, sec, num_hilos);
            img.mostrarImagen();
            img.reset();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}