import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Clase que implementa un candado
 * de tipo Backoff
 */
public class BackoffLock implements Lock {
    // Variable booleana atomica
    // asegura la integridad del estado
    private AtomicBoolean state = new AtomicBoolean(false);
    // constante: minimo tiempo pa mimir
    private static final int MIN_DELAY = 1;
    // constante: maximo tiempo pa mimir
    private static final int MAX_DELAY = 100;
    
    /**
     * Bloquea el candado para entrar a
     * la sección critica
     */
    public void lock() {
        // Instanciamos Backoff usando
        // el mínimo y el máximo
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
        while(true) {
            // mientras el estado es true, esperamos
            while(state.get()) {};

            // el recurso se encontraba libre,
            // asi que lo tomamos
            if(!state.getAndSet(true)){
                return;
            } else {
                // esperamos un tiempo aleatorio
                // a que se desocupe el recurso
                try {
                    backoff.backoff();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Libera el recurso
     */
    public void unlock(){
        state.set(false);
    }
}