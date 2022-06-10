package Filosofos;

public class TenedorTest {
    static int ROUNDS = 300;
    TenedorImp tenedor;
    Thread[] threads;

    public void setUp(){
        tenedor = new TenedorImp(1);
        threads = new Thread[2];
        threads[0] = new Thread(this::takeAndReleaseChopStick, "0");
        threads[1] = new Thread(this::takeAndReleaseChopStick, "1");
    }

    int generateRandomOddNumber(){
        return generateRandomEvenNumber() +  1;
    }

    int generateRandomEvenNumber() {
        return 2 * new Double(Math.random() * 1000).intValue();
    }

    public void twoThreads() throws InterruptedException {
        threads[0].start();
        threads[1].start();
        threads[0].join();
        threads[1].join();
        if(2*ROUNDS == tenedor.getVecesTomado()){
            System.out.println("Prueba Superada");
        }else{
            System.out.println("Prueba no superada, esperaba: " + 2*ROUNDS);
            System.out.println("Obtuvo: " + tenedor.getVecesTomado());
        }
    }

    public void takeAndReleaseChopStick(){
        for(int i = 0; i < ROUNDS; i++) {
            tenedor.tomar();
            Thread t = Thread.currentThread();
            boolean isEven = Integer.parseInt(t.getName()) % 2 == 0;
            tenedor.soltar();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        TenedorTest test = new TenedorTest();
        test.setUp();
        test.twoThreads();
    }
}
