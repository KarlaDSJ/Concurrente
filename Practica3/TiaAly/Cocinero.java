/**
 * Clase que repreenta a un cocinero de Tia Aly
 */
public class Cocinero implements Runnable{
    // Nombre del cocinero
    private String nombre;
    // Cliente asignado en ese momento
    private Cliente cliente;

    /**
     * Constructor
     * @param nombre el nombre del cocinero
     */
    public Cocinero(String nombre){
        this.nombre = nombre;
    }

    /**
     * Devuelve el nombre del cocinero
     * @return nombre del cocinero
     */
    public String getNombre(){
        return nombre;
    }

    /**
     * Asigna el nombre del cocinero
     * @param nombre nombre del cocinero
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    /**
     * Devuelve el cliente que esta atendiendo
     * @return el cliente asignado
     */
    public Cliente getCliente(){
        return cliente;
    }

    /**
     * Asigna un cliente al chef
     * @param cliente el cliente a asignar
     */
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    /**
     * Codigo que va a ejecutar el hilo Chef
     */
    public void run(){
        // Imprime lo que hace el chef
        System.out.println("El chef " + this.nombre + " esta atendiendo al cliente " + this.cliente.getNombre() + " preparando los platillos " 
        + cliente.getPlatillos() + " lo que le llevara un tiempo de " + cliente.getTiempo());

        // prepara los platillos
        for(int i = 0; i < this.cliente.getListaPlatillos().size(); ++i){
            this.cocinando(cliente.getListaPlatillos().get(i).getTiempoDeCoccion());
            System.out.println("El platillo " + cliente.getListaPlatillos().get(i).getNombre() + " se ha cocinado");
        }
    }

    /**
     * Simula cocinar los platillos
     * @param segundos el tiempo que tarda en prepararlos
     */
    private void cocinando(int segundos){
        try{
            Thread.sleep(segundos*1000);
        }catch(InterruptedException e){

        }
    }
}