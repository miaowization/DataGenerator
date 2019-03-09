package info.gabi.datagenerator;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.DocumentException;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Requester {

    private static Random rnd = new Random();
    private String inn;
    private String zipcode;
    private JsonParser parser = new JsonParser();


    @Test
    public void getUser() throws DocumentException, IOException, URISyntaxException {

        int countOfRecords = rnd.nextInt(100) + 30;
        List<Record> recordsList = new ArrayList<>();

        HttpRequest request = HttpRequest.get("https://uinames.com/api");

        if (request.code() == 200) {

            String response = HttpRequest.get("https://uinames.com/api/?ext&region=russia&amount=" + countOfRecords + "").body();
            System.out.println(response);
            JsonElement jsonElement = parser.parse(response);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < countOfRecords; i++) {
                JsonObject object = jsonArray.get(i).getAsJsonObject();

                String name = object.get("name").getAsString();
                String surname = object.get("surname").getAsString();
                String gender = object.get("gender").getAsString().equals("male") ? "м" : "ж";
                String birthday = object.get("birthday").getAsJsonObject().get("dmy").getAsString();
                LocalDate birthDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int rndCountryNumber = rnd.nextInt(150);

                response = HttpRequest.get("https://vk.com/select_ajax.php?act=a_get_countries").body();
                jsonElement = parser.parse(response);
                object = jsonElement.getAsJsonObject();
                JsonArray countryArr = object.getAsJsonArray("countries").get(rndCountryNumber).getAsJsonArray();
                String country = countryArr.get(1).getAsString();
                String countryCode = countryArr.get(0).getAsString();



                response = HttpRequest.get("https://vk.com/select_ajax.php?act=a_get_cities&country="+countryCode+"&str=******").body();
                jsonElement = parser.parse(response);
                JsonArray cities = jsonElement.getAsJsonArray();
                JsonArray citiesArr = cities.get(rnd.nextInt(cities.size()-1)+1).getAsJsonArray();
                String city = citiesArr.get(1).getAsString();
                String region = citiesArr.get(2).getAsString();

                response = HttpRequest.get("http://localhost:8080/inn/").body();
                jsonElement = parser.parse(response);
                object = jsonElement.getAsJsonObject();
                inn = object.get("inn").getAsString();

                response = HttpRequest.get("http://localhost:8080/zipcode/").body();
                jsonElement = parser.parse(response);
                object = jsonElement.getAsJsonObject();
                zipcode = object.get("zipcode").getAsString();

                Record record1 = Record.builder()
                        .name(name)
                        .surname(surname)
                        .gender(gender)
                        .birthDate(birthDate)
                        .inn(inn)
                        .zipCode(zipcode)
                        .country(country)
                        .region(region)
                        .city(city)
                        .street("")
                        .building("")
                        .apartment("")
                        .patronymic("")
                        .build();

                recordsList.add(record1);
            }

            Generator.createPdfAndFill(recordsList);
            Generator.createXslAndFill(recordsList);


        } else {
            Generator.readFilesAndWriteDataIntoXlsAndPdf();
        }


//            private String name; +
//            private String surname; +
//            private String patronymic; +
//            private Integer age;
//            private String gender;
//            private LocalDate birthDate;
//            private String inn;
//            private String zipCode;
//            private String country;
//            private String region;
//            private String city;
//            private String street;
//            private String building;
//            private String apartment;

    }

}


