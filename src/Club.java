import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Club {
    private ReentrantLock lock = new ReentrantLock();
    private Condition availableRoom = lock.newCondition();
    private int availableSpace;

    public Club(int maxSpace) {
        this.availableSpace = maxSpace;
    }

    public void enterClub() {
        Thread currentThread = Thread.currentThread();
        if (Thread.currentThread() instanceof Visitor) {
            visitorEnter((Visitor) currentThread);
        } else {
            recordLabelPersonEnter((RecordLabelPerson) currentThread);
        }
    }

    public void exitClub() {
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
            availableSpace--;
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void visitorExit(Visitor visitor) {
        lock.lock();
        availableSpace++;
        System.out.println(visitor.getName() + " is leaving the disco");
        availableRoom.signal();
        lock.unlock();
    }

    public void recordLabelPersonEnter(RecordLabelPerson recordLabelPerson) {
        lock.lock();
        availableSpace--;
        availableRoom.signal();
        lock.unlock();
    }

    public void recordLabelPersonExit(RecordLabelPerson recordLabelPerson) {
        lock.lock();
        availableSpace++;
        System.out.println(recordLabelPerson.getName() + " is leaving the disco");
        lock.unlock();
    }

    public boolean tooManyPeopleInside() {
        if (availableSpace > 0) {
            return false;
        } else {
            return true;
        }
    }

}
