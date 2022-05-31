import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

public class TiaAly {
    public static void main(String[] args) throws InterruptedException{
        List<Platillo> platillo = new ArrayList<Platillo>();
        platillo.add(new Platillo("Torta",1));
        platillo.add(new Platillo("Tacos Dorados",2));
        platillo.add(new Platillo("Enchiladas de Yakult",3));

        Queue<Cliente> fila = new LinkedList<>();
        fila.add(new Cliente("Miguel",platillo));
        fila.add(new Cliente("Juan",platillo));
        fila.add(new Cliente("Poncho",platillo));

        Cocinero[] chefs = {new Cocinero("Zeratun"),new Cocinero("Wemi")};
        Thread thread1, thread2;

        while(!fila.isEmpty()){//Mientras tengamos gente en la fila
            if(fila.size() > 1){//Si hay al menos 2 usamos 2 chefs
                chefs[0].setCliente(fila.poll());
                chefs[1].setCliente(fila.poll());
                thread1 = new Thread(chefs[0]);
                thread2 = new Thread(chefs[1]);

                thread1.start();
                thread2.start();

                thread1.join();
                thread2.join();
            }else{//Solo hay 1
                chefs[0].setCliente(fila.poll());
                thread1 = new Thread(chefs[0]);
                thread1.start();

                thread1.join();
            }
        }
    }
}