package info.gabi.datagenerator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
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
    private static List<String> femaleNamesList;
    private static List<String> maleNamesList;
    private static List<String> malePatronymicsList;
    private static List<String> femalePatronymicsList;
    private static List<String> femaleSurnamesList;
    private static List<String> maleSurnamesList;
    private static List<String> regionsList;
    private static List<String> streetsList;
    private static List<String> countriesList;
    private static List<String> citiesList;

    private static Random rnd = new Random();

    private static String getINN() {
        int[] INN = new int[12];
        INN[0] = 7;
        INN[1] = 7;
        INN[2] = 5;
        INN[3] = 1;
        String resultINN = "" + INN[0] + INN[1] + INN[2] + INN[3];
        for (int i = 4; i < 10; i++) {
            INN[i] = rnd.nextInt(9);
            resultINN += INN[i];
        }

        INN[10] = ((7 * INN[0] + 2 * INN[1] + 4 * INN[2] + 10 * INN[3] + 3 * INN[4] + 5 * INN[5] + 9 * INN[6] + 4 * INN[7] + 6 * INN[8] + 8 * INN[9]) % 11) % 10;
        resultINN += INN[10];


        INN[11] = ((3 * INN[0] + 7 * INN[1] + 2 * INN[2] + 4 * INN[3] + 10 * INN[4] + 3 * INN[5] + 5 * INN[6] + 9 * INN[7] + 4 * INN[8] + 6 * INN[9] + 8 * INN[10]) % 11) % 10;
        resultINN += INN[11];
        return resultINN;
    }

    public static void main(String[] args) throws IOException, URISyntaxException, DocumentException {
        readFilesAndWriteDataIntoXlsAndPdf();
    }


    private static void readFilesAndWriteDataIntoXlsAndPdf() throws IOException, URISyntaxException, DocumentException {
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
        List<Record> records = fillData(rnd.nextInt(80)+30);


        createXslAndFill(records);

        createPdfAndFill(records);
    }

    private static void createXslAndFill(List<Record> records) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Контакты");
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
            System.out.println("Файл создан. Путь: " + pathToResources + "/excel.xsl");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private static void createPdfAndFill(List<Record> records) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate(), 0f, 0f, 10f, 10f);

        PdfWriter.getInstance(document, new FileOutputStream(pathToResources + "/" + "pdf.pdf"));
        System.out.println("Файл создан. Путь: " + pathToResources + "/pdf.pdf");
        document.open();
        PdfPTable table = new PdfPTable(14);
        addTableHeader(table);
        addRows(table, records);

        document.add(table);
        document.close();
    }

    private static void addTableHeader(PdfPTable table) {
        Font font = FontFactory.getFont(pathToResources+"/ubuntu.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED,  8);

        Stream.of("Имя", "Фамилия", "Отчество", "Age", "Пол", "Дата рождения", "ИНН", "Почтовый индекс",
                "Страна", "Область", "Город", "Улица", "Дом", "Квартира")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setPhrase(new Phrase(columnTitle,font));
                    table.addCell(header);
                });
    }

    private static void addRows(PdfPTable table, List<Record> records) {
        Font font = FontFactory.getFont(pathToResources+"/ubuntu.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);

        PdfPCell row = new PdfPCell();
        for (Record record : records) {
            row.setPhrase(new Phrase(record.getName(), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getSurname(), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getPatronymic(), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getAge().toString(), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getGender(), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getInn(), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getZipCode(), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getCountry(), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getRegion(), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getCity(), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getStreet(), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getBuilding(), font));
            table.addCell(row);
            row.setPhrase(new Phrase(record.getApartment(), font));
            table.addCell(row);

            table.completeRow();
        }
    }


    private static List<Record> fillData(int size) {
        List<Record> records = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Boolean male = rnd.nextBoolean();
            if (male) {
                records.add(new Record(
                        maleNamesList.get(rnd.nextInt(maleNamesList.size())),
                        maleSurnamesList.get(rnd.nextInt(maleSurnamesList.size())),
                        malePatronymicsList.get(rnd.nextInt(malePatronymicsList.size())),
                        "М",
                        createRandomBirthDate(),
                        getINN(),
                        String.valueOf(rnd.nextInt(100000) + 100000),
                        countriesList.get(rnd.nextInt(countriesList.size())),
                        regionsList.get(rnd.nextInt(regionsList.size())),
                        citiesList.get(rnd.nextInt(citiesList.size())),
                        streetsList.get(rnd.nextInt(streetsList.size())),
                        String.valueOf(rnd.nextInt(50)),
                        String.valueOf(rnd.nextInt(500))));
            } else {
                records.add(new Record(
                        femaleNamesList.get(rnd.nextInt(30)),
                        femaleSurnamesList.get(rnd.nextInt(30)),
                        femalePatronymicsList.get(rnd.nextInt(30)),
                        "Ж",
                        createRandomBirthDate(),
                        getINN(),
                        String.valueOf(rnd.nextInt(100000) + 100000),
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


    private static LocalDate createRandomBirthDate() {
        int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2000, 1, 1).toEpochDay();
        long randomDay = minDay + rnd.nextInt(maxDay - minDay);

        return LocalDate.ofEpochDay(randomDay);
    }


    private static List<String> readDataAndWriteIntoList(String filePath) throws IOException {
        List<String> list = null;
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            list = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
