import java.util.concurrent.atomic.AtomicReference;

class QNode{
    // variable que indica si el nodo
    // está siendo ocupado
    volatile boolean locked = false;
    // referencia al siguiente nodo
    QNode next = null;
}

/**
 * Simula un lock de tipo MCS
 * (los nodos se leen en FIFO)
 */
public class MCSLock implements Lock {
    // cola de la lista
    AtomicReference<QNode> tail;
    // Nodo actual
    ThreadLocal<QNode> myNode;

    /**
     * Constructor
     */
    public MCSLock(){
        // inicializamos la cola como vacía
        // no apunta a ningun nodo
        tail = new AtomicReference<QNode>(null);
        // Guardamos el nodo actual para el hilo actual
        myNode = new ThreadLocal<QNode>(){
            protected QNode initialValue(){
                return new QNode();
            }
        };
    }

    /**
     * Implementación del candado
     */
    @Override
    public void lock() {
        // Obtenemos el nodo del hilo actual
        QNode qnode = myNode.get();
        // Obtenemos la cola anterior
        // y metemos a la cola el nodo actual
        QNode pred = tail.getAndSet(qnode);
        // Si la cola no estaba vacía
        if(pred != null){
            // bloqueamos el nodo actual
            // para indicar que vamos a usar el recurso
            qnode.locked = true;
            // Nos enlazamos al final de la lista
            pred.next = qnode;
            // Esperamos a que se desbloquee el nodo actual
            // para poder seguir avanzando
            while(qnode.locked) Thread.yield();
        }
    }

    /**
     * Desbloquea el recurso
     */
    @Override
    public void unlock() {
        // obtenemos el nodo del hilo actual
        QNode qnode = myNode.get();
        // Si no hay sucesor...
        if(qnode.next == null){
            // Si no hay hilos esperando el candado
            // asignamos la cola a null
            if(tail.compareAndSet(qnode,null)) return;
            // Esperamos a que tengamos un sucesor
            while(qnode.next == null) Thread.yield();
        }
        // Desbloqueamos al sucesor
        qnode.next.locked = false;
        // Como estoy al final de la lista, no tengo sucesor
        qnode.next = null;
    }
}