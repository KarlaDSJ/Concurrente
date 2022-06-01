import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase que representa un Banno
 */
public class Banno {
    // Contador de hombres que entraron
    private volatile long timesMalesEntered;
    // Contador de mujeres que entraron
    private volatile long timesFemalesEntered;

    // Numero de hombres
    private volatile long males;
    // Numero de mujeres
    private volatile long females;
    
    // True si lo usa un hombre, false si lo ocupa una mujer
    private boolean used;

    // Candado para usar
    private Lock lock = new ReentrantLock();

    // Condicion para que entren hombres
    private Condition hombres = lock.newCondition();
    // Condicion para que entren mujeres
    private Condition mujeres = lock.newCondition();

    /**
     * Constructor del baño
     */
    public Banno(){
        this.timesMalesEntered = 0;
        this.timesFemalesEntered = 0;
        males = females = 0;
        used = false;
    }

    /**
     * Obtiene las veces que se ha usado el baño
     * @return veces que se ha usado el baño
     */
    public long getTimesMalesEntered() {
        return timesMalesEntered;
    }

    /**
     * Devuelve el contador de mujeres que entraron
     * @return contador de mujeres
     */
    public long getTimesFemalesEntered() {
        return timesFemalesEntered;
    }

    /**
     * Devuelve el número de hombres que están en el baño
     * @return número de hombre 
     */
    public long getMales() {
        return males;
    }

    /**
     * Devuelve el número de mujeres en el baño
     * @return número de mujeres en el baño
     */
    public long getFemales() {
        return females;
    }

    /**
     * Entra un hombre al baño
     * @throws InterruptedException Si occure un error al
     * bloquear el candado
     */
    public void entraHombre() throws InterruptedException{
        // Tomamos el candado
        lock.lock();
        try{
            // Se pone a esperar si una mujer lo esta ocupando
            while(!used){
                hombres.await();
            }
            // marca que un hombre lo esta ocupado
            used = true;
            // incrementamos el contador de hombres que entraron
            timesMalesEntered++;
            // incrementamos el número de hombres en el baño
            males++;
        }finally{
            // soltamos el candado
            lock.unlock();
        }
    }

    /**
     * Simula la salida de un hombre
     */
    public void salHombre(){
        // Pone candado
        lock.lock();
        try{
            // Dismuye en 1 el número de hombres en el baño
            males--;
            // Si ya no hay hombres esperando a entrar
            // avisa a todas las mujeres que ya pueden pasar
            // y marca el baño como desocupado por hombres
            if(males == 0){
                mujeres.signalAll();
                used = false;
            }else{
                // Si no, solo le avisa a alguna mujer
                mujeres.signal();
            }
        }catch(Exception e){

        }finally{
            // Desbloquea el candado
            lock.unlock();
        }
    }
    
    /**
     * Entra una mujer al baño
     * @throws InterruptedException Si occure un error al
     * bloquear el candado
     */
    public void entraMujer() throws InterruptedException{
        lock.lock(); //Bloquea la entrada
        try{
            while(used){ 
                //Mientras el baño pueda no pueda ser ocupado por una mujer
                mujeres.await(); //esperamos
            }
            used = false;
            // incrementamos el contador de mujeres que han entrado
            timesFemalesEntered++;
            // incrementamos el numero de mujeres en el baño
            females++;
        }finally{
            // Liberamos el candado
            lock.unlock();
        }
    }

    /**
     * Indica que una mujer salió del baño
     */
    public void salMujer(){
        // Pone candado
        lock.lock();
        try{
            // Dismuye en 1 el número de mujeres en el baño
            females--;
            // Si ya no hay mujeres esperando a entrar
            // avisa a todos los hombres que ya pueden pasar
            // y marca el baño como desocupado por mujeres
            if(females == 0){
                hombres.signalAll();
                used = true;
            }else{
                // Si no, solo le avisa a algún hombre
                hombres.signal();
            }
        }catch(Exception e){

        }finally{
            // Desbloquea el candado
            lock.unlock();
        }
    }
}
