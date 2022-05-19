package Filosofos;
import Candados.Filtro;
import Candados.Semaforo;

/**
 * Clase que simula el problema de los filosofos resolviendolo con el Algoritmo 
 * del Filtro
 */
public class FilosofosFiltro extends Filosofos{
    private Semaforo filtro;

    public FilosofosFiltro(Semaforo filtro){
        super();
        this.filtro = filtro;
    }

    @Override
    public void entrarALaMesa() throws InterruptedException{
        //Aqui va tu codigo
    }

    @Override
    public void tomaTenedores() {
         //Aqui va tu codigo
        
    }

    @Override
    public void soltarTenedores() {
         //Aqui va tu codigo
        
    }
    
}
