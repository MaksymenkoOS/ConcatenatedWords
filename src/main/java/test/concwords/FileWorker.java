package test.concwords;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class FileWorker {

    public static LinkedList<String> read() throws IOException {

        LinkedList<String> rows = new LinkedList<>();

        Files.lines(Paths.get("src/main/resources/words.txt"), StandardCharsets.UTF_8).forEach(str -> {
            rows.add(str);
        });

        return rows;
    }

}
