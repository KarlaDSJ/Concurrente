/**
 * Clase que representa un platillo de Tia Aly
 */
public class Platillo {
    // Nombre del platillo
    private String nombre;
    private int tiempoDeCoccion;

    /**
     * Constructor
     * @param nombre nombre del platillo
     * @param tiempoDeCoccion tiempo de coccion del platillo
     */
    public Platillo(String nombre, int tiempoDeCoccion) {
        this.nombre = nombre;
        this.tiempoDeCoccion = tiempoDeCoccion;
    }

    /**
     * Devuelve el nombre del platillo
     * @return nombre del platillo 
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Asigna el nombre del platillo
     * @param nombre nombre del platillo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Devuelve el tiempo de coccion del platillo
     * @return tiempo de coccion
     */
    public int getTiempoDeCoccion() {
        return tiempoDeCoccion;
    }

    /**
     * Asigna el tiempo de coccion del platillo
     * @param tiempoDeCoccion tiempo de coccion
     */
    public void setTiempoDeCoccion(int tiempoDeCoccion){
        this.tiempoDeCoccion = tiempoDeCoccion;
    }
}