package lab;

public class Routines {

    private Routines() {}

    public static void sleep(int timeInMilliseconds) {
        try {
            Thread.sleep(timeInMilliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static boolean isEndOfThreadRequestedByJavaVM() {
        return Thread.interrupted();
    }
}
