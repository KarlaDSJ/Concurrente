package Filosofos;
import Candados.Lock;
import Candados.PetersonLock;

public class TenedorImp implements Tenedor {
    private int id;
    private volatile int tomado;
    static final Lock candado = new PetersonLock();

    /**
     * Constructor
     * @param id identificador del hilo
     */
    public TenedorImp(int id){
        this.id = id;
        this.tomado = 0;
    }

    /**
     * Marca el tenedor como tomado
     */
    @Override
    public void tomar() {
        candado.lock();
        // Incrementamos el numero de tomadas
        this.tomado++;
    }

    /**
     * Marcamos el tenedor como soltado
     */
    @Override
    public void soltar() {
        candado.unlock();
    }

    /**
     * Devuelve el id
     * @return el identificador del hilo
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Devuelve el numero de veces que ha sido tomado el tenedor
     * @return el numero de veces que ha sido tomado el tenedor
     */
    @Override
    public int getVecesTomado() {
        return this.tomado;
    }
    
}
