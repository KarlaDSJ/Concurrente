import java.util.Random;

/**
 * Clase que simula un participante
 * el que quiere entrar al baño
 */
public abstract class Participante implements Runnable{
    // Minimo tiempo de espera
    public static long MIN_SLEEP_TIME = 100;
    // Maximo tiempo de espera
    public static long MAX_SLEEP_TIME = 300;

    // Baño a usar
    protected Banno banno;
    // Generador de numeros aleatorios
    private Random random;
    // contador de veces que ha entrado al baño
    private long timesEntered;

    /**
     * Constructor
     * @param banno el banno a usar
     */
    public Participante(Banno banno){
        this.banno = banno;
        this.random = new Random();
        this.timesEntered = 0;
    }
    
    /**
     * Simula entrar al baño
     * @throws InterruptedException
     */
    public abstract void entraBanno() throws InterruptedException;
    
    /**
     * Simula salir del baño
     * @throws InterruptedException
     */
    public abstract void salBanno() throws InterruptedException;

    /**
     * Código que corre el que quiere entrar al baño
     */
    public void run(){
        try {
            // Inicia la simulación
            System.out.printf("%s.%s Iniciando Simulacion\n", Thread.currentThread().getName(), getClass().getName());
            // Entra al baño, hace sus cosas, sale del baño y luego finge irse xd
            while(true) {
                entraBanno();
                cosasXD();
                salBanno();
                sleepRandomTime();
            }
        }
        catch(InterruptedException ie) {
            System.out.printf("%s.%s finalizando simulacion, uso el banno %d\n",
                    Thread.currentThread().getName(), getClass().getName(), timesEntered);
        }
    }

    /**
    * Simula dormirse un ratito
    */
    private void sleepRandomTime() throws InterruptedException {
        long timeToSleep = Math.abs((MIN_SLEEP_TIME + random.nextInt()) % MAX_SLEEP_TIME);
        Thread.sleep(timeToSleep);
    }

    /**
    * Simula hacer cosas en el baño jaja
    * Incrementa el número de veces que se ha usado el baño
    */
    private void cosasXD() throws InterruptedException{
        this.timesEntered++;
        sleepRandomTime();
    }

    /**
     * Obtiene el número de veces que se ha usado el baño
     * @return número de veces que se ha usado el baño
     */
    public long getTimesEntered(){
        return timesEntered;
    }
}
