import java.util.Random;

public class Backoff{
    // constantes: minimo y maximo tiempo de espera
    final int minDelay, maxDelay;
    // límite de tiempo que los hilos pueden dormir
    int limit;
    // generador de numeros aleatorios
    final Random random;
    
    /**
     * Constructor
     * @param min tiempo minimo para dormir
     * @param max tiempo maximo para dormir
     */
    public Backoff(int min, int max){
        minDelay = min;
        maxDelay = max;
        limit = minDelay;
        random = new Random();
    }
    
    /**
     * Duerme al hilo en un tiempo aleatorio
     * entre el mínimo y el limite.
     * El limite se incrementa por cada ejecución
     * y tiene como tope el valor de maxDelay
     * @throws InterruptedException si ocurre un error
     * al dormir el hilo
     */
    public void backoff() throws InterruptedException {
        int delay = random.nextInt(limit);
        limit = Math.min(maxDelay, 2 * limit);
        Thread.sleep(delay);
    }
}