package guice.logger;

import guice.LogData;

import java.io.IOException;

public interface ILogger {

    void log(LogData data) throws IOException;
}
