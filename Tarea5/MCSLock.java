import java.util.concurrent.atomic.AtomicReference;

class QNode{
    volatile boolean locked = false;
    QNode next = null;
}

public class MCSLock implements Lock {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    public MCSLock(){
        tail = new AtomicReference<QNode>(null);
        myNode = new ThreadLocal<QNode>(){
            protected QNode initialValue(){
                return new QNode();
            }
        };
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        QNode pred = tail.getAndSet(qnode);
        if(pred != null){
            qnode.locked = true;
            pred.next = qnode;
            while(qnode.locked) Thread.yield();
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        if(qnode.next == null){
            if(tail.compareAndSet(qnode,null)) return;
            while(qnode.next == null) Thread.yield();
        }
        qnode.next.locked = false;
        qnode.next = null;
    }
}