package info.gabi.datagenerator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;
@Slf4j
 class DocumentCreator {

    private static String pathToOutput = ".";

    static void createXslAndFill(List<Record> records) {
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

        File file = new File(pathToOutput + "/" + "excel.xls");
        String path = file.getAbsolutePath();
        try (FileOutputStream out = new FileOutputStream(file)) {
            workbook.write(out);
            log.info("Файл создан. Путь: " + path);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    static void createPdfAndFill(List<Record> records) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate(), 0f, 0f, 10f, 10f);

        File file = new File(pathToOutput + "/pdf.pdf");
        String path = file.getAbsolutePath();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        log.info("Файл создан. Путь: " + path);
        document.open();
        PdfPTable table = new PdfPTable(14);
        addTableHeader(table);
        addRows(table, records);
        document.add(table);
        document.close();
    }

    private static void addTableHeader(PdfPTable table) {
        Font font = FontFactory.getFont("./ubuntu.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);

        Stream.of("Имя", "Фамилия", "Отчество", "Возраст", "Пол", "Дата рождения", "ИНН", "Почтовый индекс",
                "Страна", "Область", "Город", "Улица", "Дом", "Квартира")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    private static void addRows(PdfPTable table, List<Record> records) {
        Font font = FontFactory.getFont("./ubuntu.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);

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
}
