public class Platillo {
    private String nombre;
    private int tiempoDeCoccion;

    public Platillo(String nombre, int tiempoDeCoccion) {
        this.nombre = nombre;
        this.tiempoDeCoccion = tiempoDeCoccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempoDeCoccion() {
        return tiempoDeCoccion;
    }

    public void setTiempoDeCoccion(int tiempoDeCoccion){
        this.tiempoDeCoccion = tiempoDeCoccion;
    }
}