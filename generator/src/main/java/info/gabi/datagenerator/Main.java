package info.gabi.datagenerator;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static Random rnd = new Random();

    public static void main(String[] args) throws DocumentException, IOException {

        List<Record> recordsList;
        int countOfRecords = rnd.nextInt(30) + 1;

        if (isInternetEnabled()) {
            recordsList = generateOnline(countOfRecords);
        } else {
            recordsList = generateOffline(countOfRecords);
        }

        DocumentCreator.createPdfAndFill(recordsList);
        DocumentCreator.createXslAndFill(recordsList);
    }

    private static List<Record> generateOnline(int countOfRecords) {
        List<Record> recordsList = new ArrayList<>();
        Requester requester = new Requester();

        System.out.println("Начали запрашивать данные");
        for (int i = 0; i < countOfRecords; i++) {
            Record record = requester.getUser();
            record.setInn(requester.getInn());
            String[] arr = requester.getGeoInfo();
            record.setCountry(arr[0]);
            record.setRegion(arr[1]);
            record.setCity(arr[2]);

            recordsList.add(record);
        }

        return recordsList;
    }

    private static List<Record> generateOffline(int countOfRecords) throws IOException, DocumentException {
        System.out.println("Соединение с интернетом отсутствует, " +
                "файлы будут сгенерированы по старой схеме");
        Generator generator = new Generator();
        return generator.generateRecords(countOfRecords);
    }

    private static boolean isInternetEnabled() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
}
