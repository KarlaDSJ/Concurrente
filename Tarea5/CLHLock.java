import java.util.concurrent.atomic.AtomicReference;

class QNode{
    // variable que indica si el nodo
    // está siendo ocupado
    volatile boolean locked = false;
    // referencia al siguiente nodo
    QNode next = null;
}

/**
 * Simula un lock de tipo CLH
 * (los nodos se leen en FILO)
 */
public class CLHLock implements Lock {
    // cola de la lista
    AtomicReference<QNode> tail;
    // predecesor del nodo actual
    ThreadLocal<QNode> myPred;
    // Nodo actual
    ThreadLocal<QNode> myNode;
    
    /**
     * Constructor :V
     */
    public CLHLock(){
        // inicializamos la cola como vacía
        // no apunta a ningun nodo
        tail = new AtomicReference<QNode>(new QNode());
        // Guardamos el nodo actual para el hilo actual
        myNode = new ThreadLocal<QNode>(){
            protected QNode initialValue(){
                return new QNode();
            }
        };
        // inicializamos el predecesor como nulo
        myPred = new ThreadLocal<QNode>(){
            protected QNode initialValue(){
                return null;
            }
        };
    }

    /**
     * Bloquea el recurso
     */
    public void lock(){
        // obtengo la referencia al nodo actual 
        // del hilo actual
        QNode qnode = myNode.get();
        // Bloqueamos el nodo
        qnode.locked = true;
        // Obtenos la referencia a tail
        // y asignamos el nodo actual
        QNode pred = tail.getAndSet(qnode);
        // Asignamos la cola como el predecesor
        myPred.set(pred);
        // Esperamos a que se desbloquee
        // el predecesor
        while(pred.locked)Thread.yield();
    }
    
    /**
     * Desbloqueamos el nodo
     */
    public void unlock(){
        // Obtenemos el nodo actual
        QNode qnode = myNode.get();
        // Desbloqueamos el actual
        qnode.locked = false;
        // Nos vamos hacia atras en la lista
        // definiendo el nodo actual como
        // el predecesor de este
        myNode.set(myPred.get());
    }
}

