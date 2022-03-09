package practica1;


public class Practica1 {

    public static void main(String[] args) {

        System.out.println("--- Multiplicación de matrices ---");
        Matriz a = new Matriz("10");        
        System.out.println("A = \n" + a);

        for (int i = 0; i < 20; i++) {
            long timestamp = System.nanoTime();
            // Matriz c1 = a.multiplica(a);
            long ms = System.nanoTime() - timestamp;
            // // System.out.println("Resultado secuencial: \n" + c1);
            Matriz c2 = a.multiplicaConcurrente(a);            
            System.out.println(ms); 
        }

        // timestamp = System.nanoTime();
        // Matriz c2 = a.multiplicaConcurrente(a);
        // ms = System.nanoTime() - timestamp;
        // System.out.println("Resultado concurrente: \n" + c2);
        // System.out.println("Tiempo transcurrido: " + ms);

        // args = new String[] {
        //     "Practica1/src/practica1/filtros/ejemplo.jpeg", "6", "false"
        // };

        // try {
        //     String rutaImagen = args[0];
        //     int filtro = Integer.parseInt(args[1]);
        //     boolean sec = Boolean.parseBoolean(args[2]);

        //     if (args.length != 3)
        //         throw new IllegalArgumentException("Tienes que meter 3 parametros: ruta opFiltro secuencial?");

        //     Imagen img = new Imagen(rutaImagen);
        //     String cadena = sec? "secuencial" : "concurrente";
        //     System.out.println("\nAplicando filtro "+ cadena);
        //     img.aplicarFiltro(filtro, true);
        //     img.mostrarImagen();

        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }
}