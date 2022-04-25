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
        victima = new int[totalThreads - permits];

        for (int i = 0; i < totalThreads; i++)
            nivel[i] = -1;
    }

    @Override
    public void acquire(){
        int ID = Integer.valueOf(Thread.currentThread().getName());
        for (int l = 0; l < totalThreads - permits; l++) {
            nivel[ID] = l;
            victima[l] = ID;
            while ((victima[l] == ID) && noThreadAboveMe(ID, l));
        }
    }

    public boolean noThreadAboveMe(int id, int l) {
        for (int k = 0; k < totalThreads; k++) {
            if (k != id && nivel[k] >= l) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void release(){
        int ID = Integer.valueOf(Thread.currentThread().getName());
        nivel[ID] = -1;
    }

    @Override
    public int getPertitsOnCriticalSections() {
        return permits;
    }
}
