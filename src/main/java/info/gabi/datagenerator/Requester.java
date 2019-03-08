package info.gabi.datagenerator;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.*;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class Requester {

    private static Random rnd = new Random();

    @Test
    public void getUser() {

        int responseCode = HttpRequest.get("https://uinames.com/api").code();

        if (responseCode == 200) {
            JsonParser parser = new JsonParser();
            String response = HttpRequest.get("https://uinames.com/api/?ext&region=russia").body();
            System.out.println(response);
            JsonElement jsonElement = parser.parse(response);
            JsonObject rootObject = jsonElement.getAsJsonObject();
            String name = rootObject.get("name").getAsString();
            String surname = rootObject.get("surname").getAsString();
            String gender = rootObject.get("gender").getAsString().equals("male") ? "м" : "ж";
            String birthday = rootObject.get("birthday").getAsJsonObject().get("dmy").getAsString();
            LocalDate birthDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int rndCountryNumber = rnd.nextInt(150);
            response = HttpRequest.get("https://vk.com/select_ajax.php?act=a_get_countries").body();
            jsonElement = parser.parse(response);
            rootObject = jsonElement.getAsJsonObject();
            JsonArray arr = rootObject.getAsJsonArray("countries");
            JsonElement country  = arr.get(rndCountryNumber);



            Record record1 = Record.builder()
                    .name(name)
                    .surname(surname)
                    .gender(gender)
                    .birthDate(birthDate)
                    .build();

            System.out.println(record1.toString());


//            private String name;
//            private String surname;
//            private String patronymic;
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


}
