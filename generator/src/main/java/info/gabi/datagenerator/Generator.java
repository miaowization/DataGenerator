package info.gabi.datagenerator;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@NoArgsConstructor
class Generator {

    private static ClassLoader loader = Generator.class.getClassLoader();

    private final static InputStream FEMALE_NAMES = loader.getResourceAsStream("fnames.txt");
    private final static InputStream MALE_NAMES = loader.getResourceAsStream("mnames.txt");
    private final static InputStream FEMALE_PATRONYMICS = loader.getResourceAsStream("fpatronymics.txt");
    private final static InputStream MALE_PATRONYMICS = loader.getResourceAsStream("mpatronymics.txt");
    private final static InputStream FEMALE_SURNAMES = loader.getResourceAsStream("fsurnames.txt");
    private final static InputStream MALE_SURNAMES = loader.getResourceAsStream("msurnames.txt");
    private final static InputStream CITIES = loader.getResourceAsStream("cities.txt");
    private final static InputStream COUNTRIES = loader.getResourceAsStream("countries.txt");
    private final static InputStream REGIONS = loader.getResourceAsStream("regions.txt");
    private final static InputStream STREETS = loader.getResourceAsStream("streets.txt");

    private Random rnd = new Random();

    private List<Record> generateRecordsFromFiles(int size) {
        List<String> femaleNamesList = readDataAndWriteIntoList(FEMALE_NAMES);
        List<String> femalePatronymicsList = readDataAndWriteIntoList(FEMALE_PATRONYMICS);
        List<String> femaleSurnamesList = readDataAndWriteIntoList(FEMALE_SURNAMES);
        List<String> maleNamesList = readDataAndWriteIntoList(MALE_NAMES);
        List<String> malePatronymicsList = readDataAndWriteIntoList(MALE_PATRONYMICS);
        List<String> maleSurnamesList = readDataAndWriteIntoList(MALE_SURNAMES);
        List<String> regionsList = readDataAndWriteIntoList(REGIONS);
        List<String> streetsList = readDataAndWriteIntoList(STREETS);
        List<String> countriesList = readDataAndWriteIntoList(COUNTRIES);
        List<String> citiesList = readDataAndWriteIntoList(CITIES);
        List<Record> records = new ArrayList<>();
        List<String> namesList, surnamesList, patronymicsList;
        String gender;

        for (int i = 0; i < size; i++) {
            boolean male = rnd.nextBoolean();
            namesList = male ? maleNamesList : femaleNamesList;
            surnamesList = male ? maleSurnamesList : femaleSurnamesList;
            patronymicsList = male ? malePatronymicsList : femalePatronymicsList;
            gender = male ? "М" : "Ж";

            records.add(new Record(
                    namesList.get(rnd.nextInt(namesList.size())),
                    surnamesList.get(rnd.nextInt(surnamesList.size())),
                    patronymicsList.get(rnd.nextInt(patronymicsList.size())),
                    gender,
                    createRandomBirthDate(),
                    new InnMock().getInn(),
                    String.valueOf(rnd.nextInt(100000) + 100000),
                    countriesList.get(rnd.nextInt(countriesList.size())),
                    regionsList.get(rnd.nextInt(regionsList.size())),
                    citiesList.get(rnd.nextInt(citiesList.size())),
                    streetsList.get(rnd.nextInt(streetsList.size())),
                    String.valueOf(rnd.nextInt(50)),
                    String.valueOf(rnd.nextInt(500))));
        }
        return records;
    }

    List<Record> generateRecordsFromDb(int size) {
        DatabaseWorker dbWorker = new DatabaseWorker();
        List<Record> records = dbWorker.getPersons(size);
        if (records.size() == 0) {
            log.info("База данных пустая, генерируем файлы из файлов-справочников");
            records = generateRecordsFromFiles(size);
        }
        return records;
    }


    private LocalDate createRandomBirthDate() {
        int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2000, 1, 1).toEpochDay();
        long randomDay = minDay + rnd.nextInt(maxDay - minDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    private List<String> readDataAndWriteIntoList(InputStream filePath) {
        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return list;
    }
}

