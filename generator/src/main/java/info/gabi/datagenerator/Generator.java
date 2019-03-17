package info.gabi.datagenerator;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
class Generator {

    private static String pathToResources = "generator/src/main/resources";
    private static String femaleNames = pathToResources + "/fnames.txt";
    private static String maleNames = pathToResources + "/" + "mnames.txt";
    private static String femalePatronymics = pathToResources + "/" + "fpatronymics.txt";
    private static String malePatronymics = pathToResources + "/" + "mpatronymics.txt";
    private static String femaleSurnames = pathToResources + "/" + "fsurnames.txt";
    private static String maleSurnames = pathToResources + "/" + "msurnames.txt";
    private static String cities = pathToResources + "/" + "cities.txt";
    private static String countries = pathToResources + "/" + "countries.txt";
    private static String regions = pathToResources + "/" + "regions.txt";
    private static String streets = pathToResources + "/" + "streets.txt";

    private List<String> femaleNamesList;
    private List<String> maleNamesList;
    private List<String> malePatronymicsList;
    private List<String> femalePatronymicsList;
    private List<String> femaleSurnamesList;
    private List<String> maleSurnamesList;
    private List<String> regionsList;
    private List<String> streetsList;
    private List<String> countriesList;
    private List<String> citiesList;

    private Random rnd = new Random();

    Generator() {
        femaleNamesList = readDataAndWriteIntoList(femaleNames);
        femalePatronymicsList = readDataAndWriteIntoList(femalePatronymics);
        femaleSurnamesList = readDataAndWriteIntoList(femaleSurnames);
        maleNamesList = readDataAndWriteIntoList(maleNames);
        malePatronymicsList = readDataAndWriteIntoList(malePatronymics);
        maleSurnamesList = readDataAndWriteIntoList(maleSurnames);
        regionsList = readDataAndWriteIntoList(regions);
        streetsList = readDataAndWriteIntoList(streets);
        countriesList = readDataAndWriteIntoList(countries);
        citiesList = readDataAndWriteIntoList(cities);
    }

    private List<Record> generateRecordsFromFiles(int size) {
        List<Record> records = new ArrayList<>();
        List<String> namesList, surnamesList, patronymicsList;
        String gender;
        for (int i = 0; i < size; i++) {
            Boolean male = rnd.nextBoolean();
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

    List<Record> generateRecordsFromDb(int size){
        DatabaseWorker dbWorker = new DatabaseWorker();
        List<Record> records = dbWorker.getPersons(size);
        if(records.size() == 0) {
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


    private List<String> readDataAndWriteIntoList(String filePath) {
        List<String> list = null;
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            list = lines.collect(Collectors.toList());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return list;
    }
}
