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
        //Aqui va tu codigo
        
    }

    @Override
    public void soltar() {
        //Aqui va tu codigo
        
    }

    @Override
    public int getId() {
        //Aqui va tu codigo
        return 0;
    }

    @Override
    public int getVecesTomado() {
        //Aqui va tu codigo
        return 0;
    }
    
}
