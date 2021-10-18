public class ConsoleLogger implements ILogger {

    @Override
    public void log() {
        System.out.println("ConsoleLogger: Log()");
    }
}
