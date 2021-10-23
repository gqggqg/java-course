package guice;

import com.google.inject.name.Named;
import guice.bind.ConsoleLog;
import guice.bind.FileLog;

import guice.logger.ILogger;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Application {

    enum LogMode {

        NONE,
        CONSOLE,
        FILE,
        COMBINED,
    }

    public static final String CONSOLE_MODE = "console";
    public static final String FILE_MODE = "file";
    public static final String COMBINED_MODE = "combined";
    public static final String LOG_FILE_PATH = "log.txt";

    @Inject @ConsoleLog
    private ILogger consoleLogger;
    @Inject @FileLog
    private ILogger fileLogger;
    @Inject
    private ILogger combineLogger;
    @Inject
    private LogData logData;

    private LogMode mode = LogMode.NONE;
    private String tag = "";

    @Inject
    public Application(@NotNull @Named("args") String[] args) {
        if (parseInputArguments(args)) {
            deleteLogFile();
        }
    }

    public void waitForInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            if (mode == LogMode.NONE) {
                throw new IllegalArgumentException("Invalid command line argument.");
            }
            logData.setTag(tag);
            System.out.println("Waiting for new lines. Key in Ctrl+D to exit.");
            while (true) {
                logData.setStrToLog(scanner.nextLine());
                log();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Exit");
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean parseInputArguments(@NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }

        if (!args[0].equals(CONSOLE_MODE) &&
            !args[0].equals(FILE_MODE) &&
            !args[0].equals(COMBINED_MODE)) {
            return false;
        }

        if (args[0].equals(CONSOLE_MODE)) {
            mode = LogMode.CONSOLE;
            return true;
        }

        if (args.length < 2) {
            return false;
        }

        if (args[0].equals(FILE_MODE)) {
            mode = LogMode.FILE;
        } else {
            mode = LogMode.COMBINED;
        }

        tag = args[1];

        return true;
    }

    private void deleteLogFile() {
        File file = new File(LOG_FILE_PATH);
        file.delete();
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
}
