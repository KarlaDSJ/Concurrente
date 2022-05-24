package Filosofos;

/**
 * Clase que simula el problema de los 5 filosofos comiendo
 */
public abstract class Filosofos implements Runnable {
    public static int DEFAULT_MEZA_TAM = 5;
    protected int ID;
    protected int comido;
    protected Tenedor tenedorL;
    protected Tenedor tenedorD;



    @Override
    public void run() {
        try{
            for (int i = 0; i < 500; ++i){
                pensar();
                entrarALaMesa();
                comido = 0;
            }
        }catch(Exception e){}
        
    }

    /**
     * Metodo donde un filosofo entra a la mesa para comer, una vez que haya terminado de pensar
     * Debemos tomar el tenedor, comer, FINALMENTE, dejar los tenedores libres.
     * @throws InterruptedException
     */
    public void entrarALaMesa() throws InterruptedException{
        
    }

    /**
     * El filosofo se detiene a pensar unos milisegundos para decidir que tenedor tomar.
     * @throws InterruptedException
     */
    public void pensar() throws InterruptedException{
        try{
            Thread.currentThread().sleep(getRandomTime());
        }catch(InterruptedException e){
        }
    }

    /**
     * Una vez que el filosofo tenga los 2 tenedores podra comensar a comer.
     * PELIGRO: ESTA ES LA SECCION CRITICA
     * @throws InterruptedException
     */
    public void eat() throws InterruptedException{

    }

    /**
     * Metodo que nos regresa un valor entre 0 y 10
     * @return El valor pseudoaleatorio
     */
    private long getRandomTime() {
        double i=Math.random()*10.0;
        return (long)i ;
    }

    /**
     * Metodo que nos permite tomar los tenedores
     */
    public abstract void tomaTenedores();
    
    /**
     * Metodo que nos permite soltar los tenedores
     */
    public abstract void soltarTenedores();
}
