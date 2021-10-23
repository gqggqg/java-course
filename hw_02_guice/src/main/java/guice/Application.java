package guice;

import guice.bind.ConsoleLog;
import guice.bind.FileLog;

import guice.logger.ILogger;

import com.google.inject.Inject;

import java.io.File;
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
        if (deleteLogFile()) {
            System.out.println("The previous log file has been deleted. Hehe.\n");
        }

        try (Scanner scanner = new Scanner(System.in)) {
            this.scanner = scanner;
            workWithUser();
        } catch (NoSuchElementException e) {
            System.out.println("Thank you for using our logger services. :)");
        } catch (IllegalStateException  e) {
            System.out.println("We apologize. The problem is on the service side. :(");
        }
    }

    private boolean deleteLogFile() {
        File file = new File(logFilePath);
        return file.delete();
    }

    private void workWithUser() throws NoSuchElementException, IllegalStateException {
        printWelcome();
        while (true) {
            logData.setStrToLog(readLine());
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
                case FILE:
                    fileLogger.log(logData);
                    break;
                case CONSOLE:
                    consoleLogger.log(logData);
                    break;
                case COMBINED:
                    combineLogger.log(logData);
                    break;
            }
            logData.succesfulLogging();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printWelcome() {
        System.out.println(
            "Welcome to the log service.\n" +
            "We log your lines for you.\n" +
            "[Key in Ctrl+D to exit]"
        );
    }

    private String readLine() throws NoSuchElementException, IllegalStateException {
        System.out.print("Enter your line: ");
        return scanner.nextLine();
    }

    private LogMode readMode() throws NoSuchElementException, IllegalStateException {
        System.out.print(
            "Select the log mode:\n" +
            "1. Console.\n" +
            "2. File.\n" +
            "3. Combined.\n" +
            "Enter your choice (number): "
        );
        int modeNumber = tryGetModeNumber();
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
        int num = Integer.parseInt(scanner.nextLine());
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