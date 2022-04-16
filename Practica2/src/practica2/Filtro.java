package practica2;

import practica2.interfaces.Semaforo;

public class Filtro implements Semaforo {
    private int totalThreads;
    private int permits;

    volatile int[] nivel;
    volatile int[] victima;

    public Filtro(int totalThreads, int permits){
        this.totalThreads = totalThreads;
        this.permits = permits;

        nivel = new int[totalThreads];
    }

    @Override
    public void acquire(){}

    @Override
    public void release(){}

    @Override
    public int getPertitsOnCriticalSections() {
        return 0;
    }
}
