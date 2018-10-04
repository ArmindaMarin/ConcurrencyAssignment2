import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadWorld {
    ReentrantLock lock = new ReentrantLock();
    Condition availableRoom = lock.newCondition();

    public void enterWorld() {
        Thread currentThread = Thread.currentThread();
        if (Thread.currentThread() instanceof Visitor) {
            visitorEnter((Visitor) currentThread);
        } else {
            recordLabelPersonEnter((RecordLabelPerson) currentThread);
        }
    }

    public void exitWorld() {
        Thread currentThread = Thread.currentThread();
        if (Thread.currentThread() instanceof Visitor) {
            visitorExit((Visitor) currentThread);
        } else {
            recordLabelPersonExit((RecordLabelPerson) currentThread);
        }
    }

    public void visitorEnter(Visitor visitor) {
        lock.lock();
        try {
            if (tooManyPeopleInside()) {
                availableRoom.await();
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void visitorExit(Visitor visitor) {

    }

    public void recordLabelPersonEnter(RecordLabelPerson recordLabelPerson) {
        lock.lock();
        availableRoom.signal();
        lock.unlock();
    }

    public void recordLabelPersonExit(RecordLabelPerson recordLabelPerson) {

    }

    public boolean tooManyPeopleInside() {
        return true;
    }

}
