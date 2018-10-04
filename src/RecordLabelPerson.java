public class RecordLabelPerson extends Thread {
    private ThreadWorld world;

    public RecordLabelPerson(int number, ThreadWorld world) {
        super("RecordLabelPerson " + number);
        this.world = world;
    }

    public void run() {
        while (true) {
            try {
                System.out.println(getName() + " wants to enter the disco");

                world.enterWorld();

                System.out.println(getName() + " is in the disco");


                sleep((int) (Math.random() * 500 + 1000));

                world.exitWorld();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
