import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Implementa un candado de tipo TTAS
 */
public class TTASLock implements Lock{
    // Variable booleana atomica
    // asegura la integridad del estado
    private AtomicBoolean state = new AtomicBoolean(false);

    /**
     * Bloquea el recurso
     */
    @Override
    public void lock() {
        while(true){
            // mientras el recurso esté ocupado, esperamos
            while(state.get());

            // si el recurso se encuentra libre,
            // lo tomamos
            if(!state.getAndSet(true)){
                return;
            }
        }
    }

    /**
     * Libera el recurso
     */
    @Override
    public void unlock() {
        state.set(false);
    }
}
