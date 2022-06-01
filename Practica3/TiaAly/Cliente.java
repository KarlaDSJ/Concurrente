import java.util.List;

/**
 * Clase que representa un cliente que llega a tia Aly
 */
public class Cliente {
    // Nombre del cliente
    private String nombre;
    // Platillos que va a pedir
    private List<Platillo> listaPlatillos;

    /***
     * Constructor
     * @param nombre nombre del cliente
     * @param listaPlatillos lista de platillos
     */
    public Cliente(String nombre, List<Platillo> listaPlatillos) {
        this.nombre = nombre;
        this.listaPlatillos = listaPlatillos;
    }

    /**
     * Devuelve el nombre del cliente
     * @return nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna nombre al cliente
     * @param nombre el nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la lista de platillos del cliente
     * @return lista de platillos
     */
    public List<Platillo> getListaPlatillos() {
        return listaPlatillos;
    }

    /**
     * Asigna una lista de platillos al cliente
     * @param listaPlatillos lista de platillos
     */
    public void setListaPlatillos(List<Platillo> listaPlatillos) {
        this.listaPlatillos = listaPlatillos;
    }

    /***
     * Obtiene el nombre de los platillos del cliente
     * @return string con el nombre de los platillos
     */
    public String getPlatillos() {
        String platillos = "";
        for (Platillo platillo: listaPlatillos) {
            platillos += platillo.getNombre() + " ";
        }
        return platillos;
    }

    /**
     * Devuelve el tiempo de coccion de los platillos del cliente
     * @return la suma de tiempos de coccion
     */
    public int getTiempo(){
        int coccion = 0;
        for (Platillo platillo: listaPlatillos) {
            coccion += platillo.getTiempoDeCoccion();
        }
        return coccion;
    }
}
