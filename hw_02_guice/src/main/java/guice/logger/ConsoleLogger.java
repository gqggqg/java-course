package guice.logger;

import guice.LogData;

import com.google.inject.Singleton;

@Singleton
public class ConsoleLogger implements ILogger {

    @Override
    public void log(LogData data) {
        System.out.println(data.getStrToLog());
    }
}
