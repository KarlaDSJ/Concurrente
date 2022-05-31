public class Cocinero implements Runnable{
    private String nombre;
    private Cliente cliente;

    public Cocinero(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public void run(){
        System.out.println("El chef " + this.nombre + " esta atendiendo al cliente " + this.cliente.getNombre() + " preparando los platillos " 
        + cliente.getPlatillos() + " lo que le llevara un tiempo de " + cliente.getTiempo());

        for(int i = 0; i < this.cliente.getListaPlatillos().size(); ++i){
            this.cocinando(cliente.getListaPlatillos().get(i).getTiempoDeCoccion());
            System.out.println("El platillo " + cliente.getListaPlatillos().get(i).getNombre() + " se ha cocinado");
        }
    }

    private void cocinando(int segundos){
        try{
            Thread.sleep(segundos*1000);
        }catch(InterruptedException e){

        }
    }
}