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
        while (comido == 0) {
            filtro.acquire();
            if (tenedorL.puedoTomarlo() && tenedorD.puedoTomarlo()) {
                tomaTenedores();
                eat();
                comido = 1;
                soltarTenedores();
            }
            filtro.release();
        }
    }

    @Override
    public void tomaTenedores() {
        tenedorL.tomar();
        tenedorD.tomar();
    }

    @Override
    public void soltarTenedores() {
         //Aqui va tu codigo
        tenedorL.soltar();
        tenedorD.soltar();
    }
    
}
