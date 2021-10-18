import com.google.inject.Singleton;

import java.io.IOException;
import java.io.FileWriter;

@Singleton
public class FileLogger implements ILogger {

    @Override
    public void log(LogData data) throws IOException {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.append(data.getTaggedStrToLog());
            writer.flush();
        }
    }
}
