import com.google.common.reflect.TypeToken;
import com.google.inject.name.Named;
import com.google.inject.Inject;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.io.BufferedReader;
import java.util.Collection;
import java.util.ArrayList;
import java.io.FileReader;

public class JsonBookFactory implements IBookFactory {

    @NotNull
    private static final Type bookListType = new TypeToken<ArrayList<Book>>(){}.getType();

    @NotNull
    private final String filePath;

    @Inject
    public JsonBookFactory(@NotNull @Named("filePath") String filePath) {
        this.filePath = filePath;
    }

    @NotNull
    @Override
    public Collection<Book> books() {
        try {
            return new Gson().fromJson(new BufferedReader(new FileReader(filePath)), bookListType);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File " + filePath + " not found.");
        }
    }
}
