public class RecordLabelPerson extends Thread {
    private Club world;

    public RecordLabelPerson(int number, Club world) {
        super("RecordLabelPerson " + number);
        this.world = world;
    }

    public void run() {
        while (true) {
            try {
                System.out.println(getName() + " wants to enter the disco");

                world.enterClub();

                System.out.println(getName() + " is in the disco");


                sleep((int) (Math.random() * 500 + 1000));

                world.exitClub();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
