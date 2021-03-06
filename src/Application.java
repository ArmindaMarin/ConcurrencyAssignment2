public class Application {
    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        Club world = new Club(20);
        int amountOfVisitors = 30;
        int amountOfRecordLabelPeople = 5;

        Thread[] visitors = new Thread[amountOfVisitors];
        Thread[] recordLabelPeople = new Thread[amountOfRecordLabelPeople];

        for (int i = 0; i < amountOfVisitors; i++) {
            if (i < amountOfRecordLabelPeople) {
                recordLabelPeople[i] = new RecordLabelPerson(i, world);
                recordLabelPeople[i].start();
            }

            visitors[i] = new Visitor(i, world);
            visitors[i].start();
        }
    }
}
