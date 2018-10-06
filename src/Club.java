import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Club {
    private ReentrantLock lock = new ReentrantLock();
    private Condition availableRoom = lock.newCondition();
    private int availableSpace;
    private final int maxSpace;
    private int accessDeniedForRecordLabelPerson = 0;
    private boolean accessDeniedForVisitor = false;
    private boolean noRecordLabelPersonInside = true;

    public Club(int maxSpace) {
        this.maxSpace = maxSpace;
        this.availableSpace = this.maxSpace;
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
            if (tooManyPeopleInside() || accessDeniedForVisitor) {
                availableRoom.await();
            }
            if (accessDeniedForRecordLabelPerson >= 3) {
                accessDeniedForRecordLabelPerson = 0;
            }
            availableSpace--;
            System.out.println(visitor.getName() + " is entering the disco");
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
        try {
            if (availableSpace < (maxSpace / 2) && accessDeniedForRecordLabelPerson < 3 && noRecordLabelPersonInside) {
                availableSpace--;
                accessDeniedForVisitor = true;
                System.out.println(recordLabelPerson.getName() + " is entering the disco");
                accessDeniedForRecordLabelPerson++;
            } else {
                availableRoom.await();
            }
            noRecordLabelPersonInside = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void recordLabelPersonExit(RecordLabelPerson recordLabelPerson) {
        lock.lock();
        availableSpace++;
        System.out.println(recordLabelPerson.getName() + " is leaving the disco");
        accessDeniedForVisitor = false;
        noRecordLabelPersonInside = true;
        availableRoom.signalAll();
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
