package Filosofos;
import Candados.Lock;
import Candados.PetersonLock;

public class TenedorImp implements Tenedor {
    private int id;
    private volatile boolean tomado;
    private volatile int vecesTomado;
    static final Lock candado = new PetersonLock();

    public TenedorImp(int id){
        this.id = id;
        this.tomado = false;
        this.vecesTomado = 0;
    }

    public boolean puedoTomarlo() {
        return tomado;
    }

    @Override
    public void tomar() {
        this.tomado = true;
        candado.lock();
        this.vecesTomado++;
    }

    @Override
    public void soltar() {
        candado.unlock();
        this.tomado = false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getVecesTomado() {
        return vecesTomado;
    }
    
}
