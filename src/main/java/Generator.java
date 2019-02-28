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
    private static String maleNames = pathToResources+"/"+"mnames.txt";
    private static String femalePatronymics = pathToResources+"/"+"fpatronymics.txt";
    private static String malePatronymics = pathToResources+"/"+"mpatronymics.txt";
    private static String femaleSurnames = pathToResources+"/"+"fsurnames.txt";
    private static String maleSurnames = pathToResources+"/"+"msurnames.txt";
    private static String cities = pathToResources+"/"+"cities.txt";
    private static String countries = pathToResources+"/"+"countries.txt";
    private static String regions = pathToResources+"/"+"regions.txt";
    private static String streets = pathToResources+"/"+"streets.txt";
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

        for (Record record: records) {
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

        try (FileOutputStream out = new FileOutputStream(new File(pathToResources+"/"+"excel.xls"))) {
            workbook.write(out);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    
    private void generateInn(){
        String s = "77"+String.valueOf(rnd.nextInt(50)+1);
        System.out.println("Первая часть инн " + s);

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

    private void createSheetHeader(HSSFSheet sheet, int rowNum, Record record) {

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
