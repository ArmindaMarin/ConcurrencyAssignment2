public class Visitor extends Thread {
    private Club world;

    public Visitor(int number, Club world) {
        super("Visitor " + number);
        this.world = world;
    }

    public void run() {
        while (true) {
            try {
                System.out.println(getName() + " wants to enter the disco");

                world.enterClub();

                System.out.println(getName() + " is in the disco");


                sleep((int) (Math.random() * 5000 + 1000));

                world.exitClub();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
