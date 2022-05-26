package Filosofos;
import Candados.Lock;
import Candados.PetersonLock;

public class TenedorImp implements Tenedor {
    private int id;
    private volatile int tomado;
    static final Lock candado = new PetersonLock();

    public TenedorImp(int id){
        this.id = id;
        this.tomado = 0;
    }

    @Override
    public void tomar() {
        candado.lock();
        this.tomado++;
    }

    @Override
    public void soltar() {
        candado.unlock();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getVecesTomado() {
        return this.tomado;
    }
    
}
