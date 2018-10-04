public class Application {
    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        ThreadWorld world = new ThreadWorld();
        int amountOfVisitors = 10;
        int amountOfRecordLabelPeople = 2;

        Thread[] visitors = new Thread[amountOfVisitors];
        Thread[] recordLabelPeople = new Thread[amountOfRecordLabelPeople];

        for (int i = 0; i < amountOfVisitors; i++) {
            visitors[i] = new Visitor(i, world);
            visitors[i].start();
        }
        
        for (int i = 0; i < amountOfRecordLabelPeople; i++) {
            recordLabelPeople[i] = new RecordLabelPerson(i, world);
            recordLabelPeople[i].start();
        }
    }
}
