import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generator {

    @Test
    public void ReadFiles() {
        String femaleNames = "/Users/aigulgabu/IdeaProjects/DataGenerator/src/main/resources/fnames.txt";
        String maleNames = "/Users/aigulgabu/IdeaProjects/DataGenerator/src/main/resources/mnames.txt";
        String femalePatronymics = "/Users/aigulgabu/IdeaProjects/DataGenerator/src/main/resources/fpatronymics.txt";
        String malePatronymics = "/Users/aigulgabu/IdeaProjects/DataGenerator/src/main/resources/mpatronymics.txt";
        String femaleSurnames = "/Users/aigulgabu/IdeaProjects/DataGenerator/src/main/resources/fsurnames.txt";
        String maleSurnames = "/Users/aigulgabu/IdeaProjects/DataGenerator/src/main/resources/msurnames.txt";
        List<String> femaleNamesList = readDataAndWriteIntoList(femaleNames);
        List<String> maleNamesList = readDataAndWriteIntoList(maleNames);
    }

    private List<String> readDataAndWriteIntoList(String filePath) {
        List<String> list = null;
        try {
            Stream<String> lines = Files.lines(Paths.get(filePath));
            list = lines.collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
