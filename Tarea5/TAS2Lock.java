/**
 * Clase que implementa un candado TAS
 * usando una atributo volatil
 * en lugar de una variable atomica booleana
 */
public class TAS2Lock implements Lock{
    // variable booleana volatil que funge
    // como candado
    volatile boolean state = false;

    /**
     * Asigna al estado el valor true
     * y regresa el estado anterior
     * simulando un candado para entrar a la
     * sección critica
     * NOTA: como el atributo no es atomico
     * puede que esta función lea un valor intermedio
     * incorrecto y el hilo no se desbloquee
     */
    @Override
    public void lock() {
        while(getAndSet(true));    
    }

    /**
     * Desbloquea el candado al salir
     * de la región critica
     * NOTA: como el atributo es volatil y
     * no es atómico, esto no asegura que el
     * candado se desbloquee
     */
    @Override
    public void unlock() {
        set(false); 
    }
    
    /**
     * Asigna un nuevo valor y regresa el anterior
     * @param state el nuevo valor
     * @return el viejo valor
     */
    public boolean getAndSet(Boolean state) {
        boolean result = this.state;
        this.state = state;
        return result;
    }

    /**
     * Asigna un nuevo valor
     * @param state el nuevo valor
     */
    public void set(boolean state) {
        this.state = state;
    }

}
