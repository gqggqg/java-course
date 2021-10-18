import org.jetbrains.annotations.NotNull;
import com.google.inject.Inject;
import lombok.Getter;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Application {

    enum LogMode {

        NONE, // is not explicitly used
        CONSOLE,
        FILE,
        COMBINED,
    }

    @Inject
    ILogger consoleLogger;
    @Inject @File
    ILogger fileLogger;

    @Getter
    private String line;
    @Getter
    private LogMode mode;
    @Getter
    private String tag;

    // in order not to pass it as a method argument
    private Scanner scanner;

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
            line = readLine();
            mode = readMode();
            if (mode != LogMode.CONSOLE) {
                tag = readTag();
            }
            log();
        }
    }

    private void log() {
        fileLogger.log();
        consoleLogger.log();
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
