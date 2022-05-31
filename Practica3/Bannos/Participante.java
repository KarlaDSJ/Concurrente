import java.util.Random;

public abstract class Participante implements Runnable{
    public static long MIN_SLEEP_TIME = 100;
    public static long MAX_SLEEP_TIME = 300;

    protected Banno banno;
    private Random random;
    private long timesEntered;

    public Participante(Banno banno){
        this.banno = banno;
        this.random = new Random();
        this.timesEntered = 0;
    }

    public abstract void entraBanno() throws InterruptedException;
    public abstract void salBanno() throws InterruptedException;

    public void run(){
        try {
            System.out.printf("%s.%s Iniciando Simulacion\n", Thread.currentThread().getName(), getClass().getName());
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

    private void sleepRandomTime() throws InterruptedException {
        long timeToSleep = Math.abs((MIN_SLEEP_TIME + random.nextInt()) % MAX_SLEEP_TIME);
        Thread.sleep(timeToSleep);
    }

    private void cosasXD() throws InterruptedException{
        this.timesEntered++;
        sleepRandomTime();
    }

    public long getTimesEntered(){
        return timesEntered;
    }
}
