package Filosofos;
import java.lang.Thread;

/**
 * Clase que simula el problema de los 5 filosofos comiendo
 */
public abstract class Filosofos implements Runnable {
    public static int DEFAULT_MEZA_TAM = 5;
    protected int ID;
    protected int comido;
    protected Tenedor tenedorL;
    protected Tenedor tenedorD;

    public Filosofos(int ID) {
        this.ID = ID;
        comido = 0;
    }

    @Override
    public void run() {
        try{
            for (int i = 0; i < 500; ++i){
                pensar();
                entrarALaMesa();                
            }
        }catch(Exception e){}
        
    }

    /**
     * Metodo donde un filosofo entra a la mesa para comer, una vez que haya terminado de pensar
     * Debemos tomar el tenedor, comer, FINALMENTE, dejar los tenedores libres.
     * @throws InterruptedException
     */
    public void entrarALaMesa() throws InterruptedException{
        try {
            eat();
            this.tenedorD.soltar();
            this.tenedorL.soltar();
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }

    /**
     * El filosofo se detiene a pensar unos milisegundos para decidir que tenedor tomar.
     * @throws InterruptedException
     */
    public void pensar() throws InterruptedException{
        try{
            Thread.currentThread().sleep(this.getRandomTime());
        }catch(InterruptedException e){
        }
    }

    /**
     * Una vez que el filosofo tenga los 2 tenedores podra comensar a comer.
     * PELIGRO: ESTA ES LA SECCION CRITICA
     * @throws InterruptedException
     */
    public void eat() throws InterruptedException{
        try {
            // int id = Integer.valueOf(Thread.currentThread().getName());
            if (this.ID ==0){
                this.tenedorL.tomar();
                this.tenedorD.tomar();
            } else {
                this.tenedorD.tomar();
                this.tenedorL.tomar();
            }
            Thread.currentThread().sleep(this.getRandomTime());
            comido++;
        } catch (Exception e) {
            System.out.println("Lo siento, no pude comer :(")
        }
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
