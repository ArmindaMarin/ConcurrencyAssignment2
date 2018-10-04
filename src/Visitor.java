public class Visitor extends Thread {
    private ThreadWorld world;

    public Visitor(int number, ThreadWorld world) {
        super("Visitor " + number);
        this.world = world;
    }

    public void run() {
        while (true) {
            try {
                System.out.println(getName() + " wants to enter the disco");

                world.enterWorld();

                System.out.println(getName() + " is in the disco");


                sleep((int) (Math.random() * 5000 + 1000));

                world.exitWorld();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
