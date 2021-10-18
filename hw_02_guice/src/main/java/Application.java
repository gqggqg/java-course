import com.google.inject.Inject;

import java.io.IOException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Application {

    enum LogMode {

        NONE, // is not explicitly used
        CONSOLE,
        FILE,
        COMBINED,
    }

    public static final String logFilePath = "log.txt";

    @Inject @ConsoleLog
    private ILogger consoleLogger;
    @Inject @FileLog
    private ILogger fileLogger;
    @Inject
    private ILogger combineLogger;
    @Inject
    private LogData logData;

    // in order not to pass it as a method argument
    private Scanner scanner;

    private LogMode mode;

    public void waitForInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            this.scanner = scanner;
            workWithUser();
        } catch (NoSuchElementException e) {
            System.out.println("Thank you for using our logger services. :)");
        } catch (IllegalStateException  e) {
            System.out.println("We apologize. The problem is on the service side. :(");
        }
    }

    private void workWithUser() throws NoSuchElementException, IllegalStateException {
        printWelcome();
        while (true) {
            var str = readLine();
            logData.setStrToLog(str);
            mode = readMode();
            if (mode != LogMode.CONSOLE) {
                logData.setTag(readTag());
            }
            log();
        }
    }

    private void log() {
        try {
            switch (mode) {
                case FILE -> fileLogger.log(logData);
                case CONSOLE -> consoleLogger.log(logData);
                case COMBINED -> combineLogger.log(logData);
            }
            logData.goodLog();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printWelcome() {
        System.out.println("""
                    Welcome to the log service.
                    We log your lines for you.
                    [Key in Ctrl+D to exit]""");
    }

    private String readLine() throws NoSuchElementException, IllegalStateException {
        System.out.print("Enter your line: ");
        return scanner.nextLine();
    }

    private LogMode readMode() throws NoSuchElementException, IllegalStateException {
        System.out.print("""
                        Select the log mode:
                        1. Console.
                        2. File.
                        3. Combined.
                        Enter your choice (number):\040""");
        var modeNumber = tryGetModeNumber();
        return LogMode.values()[modeNumber];
    }

    private int tryGetModeNumber() throws NoSuchElementException, IllegalStateException {
        while (true) {
            try {
                return tryParse();
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number from 1 to 3: ");
            }
        }
    }

    private int tryParse() throws NumberFormatException, NoSuchElementException, IllegalStateException {
        var num = Integer.parseInt(scanner.nextLine());
        if (num < 1 || num > 3) {
            throw new NumberFormatException();
        }
        return num;
    }

    private String readTag() throws NoSuchElementException, IllegalStateException {
        System.out.print("Please enter a tag name to frame the line: ");
        return scanner.nextLine();
    }
}
