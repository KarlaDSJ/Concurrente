import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banno {
    private volatile long timesMalesEntered;
    private volatile long timesFemalesEntered;

    private volatile long males;
    private volatile long females;
    
    private boolean used;

    private Lock lock = new ReentrantLock();

    private Condition hombres = lock.newCondition();
    private Condition mujeres = lock.newCondition();

    public Banno(){
        this.timesMalesEntered = 0;
        this.timesFemalesEntered = 0;
        males = females = 0;
        used = false;
    }

    public long getTimesMalesEntered() {
        return timesMalesEntered;
    }

    public long getTimesFemalesEntered() {
        return timesFemalesEntered;
    }

    public long getMales() {
        return males;
    }

    public long getFemales() {
        return females;
    }

    public void entraHombre() throws InterruptedException{
        lock.lock();
        try{
            while(!used){
                hombres.await();
            }
            used = true;
            timesMalesEntered++;
            males++;
        }finally{
            lock.unlock();
        }
    }

    public void salHombre(){
        lock.lock();
        try{
            males--;
            if(males == 0){
                mujeres.signalAll();
                used = false;
            }else{
                mujeres.signal();
            }
        }catch(Exception e){

        }finally{
            lock.unlock();
        }
    }

    public void entraMujer() throws InterruptedException{
        lock.lock();
        try{
            while(used){
                mujeres.await();
            }
            used = false;
            timesFemalesEntered++;
            females++;
        }finally{
            lock.unlock();
        }
    }

    public void salMujer(){
        lock.lock();
        try{
            females--;
            if(females == 0){
                hombres.signalAll();
                used = true;
            }else{
                hombres.signal();
            }
        }catch(Exception e){

        }finally{
            lock.unlock();
        }
    }
}
