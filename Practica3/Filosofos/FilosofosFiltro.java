package Filosofos;
import Candados.Semaforo;

/**
 * Clase que simula el problema de los filosofos resolviendolo con el Algoritmo 
 * del Filtro
 */
public class FilosofosFiltro extends Filosofos{
    private Semaforo filtro;

    /**
     * Constructor
     * @param filtro semaforo para la entrada de los filosofos
     */
    public FilosofosFiltro(Semaforo filtro){
        super(Integer.valueOf(Thread.currentThread().getName()));
        this.filtro = filtro;
    }

    /**
     * El filosofo entra a la mesa para comer
     * @throws InterruptedException
     */
    @Override
    public void entrarALaMesa() throws InterruptedException{
        // Pone el candado para poder comer
        this.filtro.acquire();
        // Tomo tenedores
        this.tomaTenedores();
        // Come
        Thread.currentThread().sleep(10);
        // Suelta tenedores
        this.soltarTenedores();          
        // Quita el candado
        filtro.release();
    }    

    /**
     * Toma ambos tenedores
     */
    @Override
    public void tomaTenedores() {
        this.tenedorL.tomar();
        this.tenedorD.tomar();
    }

    /**
     * Suelta ambod tenedores
     */
    @Override
    public void soltarTenedores() {
        this.tenedorL.soltar();
        this.tenedorD.soltar();
    }
    
}
