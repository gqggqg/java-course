import java.util.NoSuchElementException;
import java.util.Scanner;

public class Application {

    private Scanner scanner;

    public void waitForInput() {
        try {
            if (scanner == null) {
                throw new IllegalStateException();
            }
            printWelcome();
            while (true) {
                var line = nextLine();
                var mode = nextMode();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Thank you for using our logger services. :)");
        } catch (IllegalStateException  e) {
            System.out.println("We apologize. The problem is on the service side. :(");
        }
    }

    private void printWelcome() {
        System.out.println("""
                    Welcome to the log service.
                    We log your lines for you.
                    [Key in Ctrl+D to exit]""");
    }

    private String nextLine() throws NoSuchElementException, IllegalStateException {
        System.out.print("Enter your line: ");
        return scanner.nextLine();
    }

    private int nextMode() throws NoSuchElementException, IllegalStateException {
        System.out.print("""
                        Select the log mode:
                        1. Console log. [Why?]
                        2. File log.
                        3. Both.
                        Enter your choice (number):\040""");
        return tryGetMode();
    }

    private int tryGetMode() throws NoSuchElementException, IllegalStateException {
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
}
