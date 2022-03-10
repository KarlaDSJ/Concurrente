package practica1;

import practica1.filtros.Imagen;
import practica1.matrices.Matriz;

public class Practica1 {

    public static void main(String[] args) {

        try {
            if (args.length != 4)
                throw new IllegalArgumentException("Tienes que meter 4 parametros: matriz ruta opFiltro secuencial?");
            
                String rutaImagen = args[1];
            int filtro = Integer.parseInt(args[2]);
            boolean sec = Boolean.parseBoolean(args[3]);

            System.out.println("--- Multiplicación de matrices ---");
            Matriz a = new Matriz(args[0]);        
            System.out.println("A = \n" + a);

        //for (int i = 0; i < 20; i++) {
            long timestamp = System.nanoTime();
            Matriz mul;
            mul = sec? a.multiplica(a): a.multiplicaConcurrente(a); 
            long ms = System.nanoTime() - timestamp; 
            System.out.println("Resultado: \n" + mul);          
            System.out.println(ms); 
        //}

            Imagen img = new Imagen(rutaImagen);
            String cadena = sec? "secuencial" : "concurrente";
            System.out.println("\nAplicando filtro "+ cadena);
            img.aplicarFiltro(filtro, true);
            img.mostrarImagen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}