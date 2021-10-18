package guice.logger;

import guice.bind.ConsoleLog;
import guice.bind.FileLog;
import guice.LogData;

import com.google.inject.Singleton;
import com.google.inject.Inject;

import java.io.IOException;

@Singleton
public class CombinedLogger implements ILogger {

    @Inject @FileLog
    private ILogger fileLogger;
    @Inject @ConsoleLog
    private ILogger consoleLogger;

    @Override
    public void log(LogData data) throws IOException {
        consoleLogger.log(data);
        data.goodLog();
        fileLogger.log(data);
    }
}
