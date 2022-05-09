import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Implementa un candado de tipo TAS
 */
public class TASLock implements Lock{
    // Variable booleana atomica
    // asegura la integridad del estado
    AtomicBoolean state = new AtomicBoolean(false);

    // Asigna al estado el valor true
    // y regresa el estado anterior
    // simulando un candado para entrar a la
    // sección critica
    @Override
    public void lock() {
        while(state.getAndSet(true));
        
    }

    /**
     * Desbloquea el candado al salir
     * de la región critica
     */
    @Override
    public void unlock() {
        state.set(false);
        
    }
    
}