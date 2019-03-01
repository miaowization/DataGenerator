import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Generator {

    private static String pathToResources = "src/main/resources";
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

    Random rnd = new Random();

    @Test
    public void testINN(){
        System.out.println(getINN());
    }

    private String getINN() {
        String resultINN = "77" + String.valueOf(rnd.nextInt(50) + 1);
        int[] INN = new int[12];

        INN[0] = 7;
        INN[1] = 7;
        INN[2] = 5;
        INN[3] = 1;
        resultINN += String.valueOf(INN[0]+INN[1]+INN[2]+INN[3]);
        for (int i = 4; i < 9; i++) {
            INN[i] = rnd.nextInt(9);
            resultINN += INN[i];
        }

        INN[10] = ((7 * INN[0] + 2 * INN[1] + 4 * INN[2] + 10 * INN[3] + 3 * INN[4] + 5 * INN[5] + 9 * INN[6] + 4 * INN[7] + 6 * INN[8] + 8 * INN[9]) % 11) % 10;
        resultINN += INN[10];


        INN[11] = ((3 * INN[0] + 7 * INN[1] + 2 * INN[2] + 4 * INN[3] + 10 * INN[4] + 3 * INN[5] + 5 * INN[6] + 9 * INN[7] + 4 * INN[8] + 6 * INN[9] + 8*INN[10]) % 11) % 10;
        resultINN += INN[11];
        return resultINN;
    }

    @Test
    public void ReadFiles() throws IOException {
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

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Контакты");

        List<Record> records = fillData(30);

        records.forEach(Record::print);


        int rowNum = 0;

        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Имя");
        row.createCell(1).setCellValue("Фамилия");
        row.createCell(2).setCellValue("Отчество");
        row.createCell(3).setCellValue("Возраст");
        row.createCell(4).setCellValue("Пол");
        row.createCell(5).setCellValue("Дата рождения");
        row.createCell(6).setCellValue("ИНН");
        row.createCell(7).setCellValue("Почтовый индекс");
        row.createCell(8).setCellValue("Страна");
        row.createCell(9).setCellValue("Область");
        row.createCell(10).setCellValue("Город");
        row.createCell(11).setCellValue("Улица");
        row.createCell(12).setCellValue("Дом");
        row.createCell(13).setCellValue("Квартира");

        for (Record record : records) {
            rowNum++;
            row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(record.getName());
            row.createCell(1).setCellValue(record.getSurname());
            row.createCell(2).setCellValue(record.getPatronymic());
            row.createCell(3).setCellValue(record.getAge());
            row.createCell(4).setCellValue(record.getGender());
            row.createCell(5).setCellValue(record.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            row.createCell(6).setCellValue(record.getInn());
            row.createCell(7).setCellValue(record.getZipCode());
            row.createCell(8).setCellValue(record.getCountry());
            row.createCell(9).setCellValue(record.getRegion());
            row.createCell(10).setCellValue(record.getCity());
            row.createCell(11).setCellValue(record.getStreet());
            row.createCell(12).setCellValue(record.getBuilding());
            row.createCell(13).setCellValue(record.getApartment());
        }

        try (FileOutputStream out = new FileOutputStream(new File(pathToResources + "/" + "excel.xls"))) {
            workbook.write(out);
        } catch (IOException e) {
            e.getMessage();
        }
    }




    private List<Record> fillData(int size) {
        List<Record> records = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Boolean male = rnd.nextBoolean();
            if (male) {
                records.add(new Record(
                        maleNamesList.get(rnd.nextInt(30)),
                        maleSurnamesList.get(rnd.nextInt(30)),
                        malePatronymicsList.get(rnd.nextInt(30)),
                        "М",
                        createRandomBirthDate(),
                        "",
                        "",
                        countriesList.get(rnd.nextInt(100)),
                        regionsList.get(rnd.nextInt(40)),
                        citiesList.get(rnd.nextInt(30)),
                        streetsList.get(rnd.nextInt(30)),
                        String.valueOf(rnd.nextInt(50)),
                        String.valueOf(rnd.nextInt(500))));
            } else {
                records.add(new Record(
                        femaleNamesList.get(rnd.nextInt(30)),
                        femaleSurnamesList.get(rnd.nextInt(30)),
                        femalePatronymicsList.get(rnd.nextInt(30)),
                        "Ж",
                        createRandomBirthDate(),
                        "",
                        "",
                        countriesList.get(rnd.nextInt(100)),
                        regionsList.get(rnd.nextInt(40)),
                        citiesList.get(rnd.nextInt(30)),
                        streetsList.get(rnd.nextInt(30)),
                        String.valueOf(rnd.nextInt(50)),
                        String.valueOf(rnd.nextInt(500))));
            }
        }
        return records;
    }


    private LocalDate createRandomBirthDate() {
        int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2000, 1, 1).toEpochDay();
        long randomDay = minDay + rnd.nextInt(maxDay - minDay);

        return LocalDate.ofEpochDay(randomDay);
    }


    private List<String> readDataAndWriteIntoList(String filePath) throws IOException {
        List<String> list = null;
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            list = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
