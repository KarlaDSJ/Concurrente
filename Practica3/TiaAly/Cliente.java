import java.util.List;

public class Cliente {
    private String nombre;
    private List<Platillo> listaPlatillos;

    public Cliente(String nombre, List<Platillo> listaPlatillos) {
        this.nombre = nombre;
        this.listaPlatillos = listaPlatillos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Platillo> getListaPlatillos() {
        return listaPlatillos;
    }

    public void setListaPlatillos(List<Platillo> listaPlatillos) {
        this.listaPlatillos = listaPlatillos;
    }

    public String getPlatillos() {
        return "";
    }

    public int getTiempo(){
        return 0;
    }
}
