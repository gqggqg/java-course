package json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public final class JsonConverter {

    public static String convertListToJson(List<Product> list) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, list);
        return out.toString();
    }
}
