package info.gabi.datagenerator;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.*;
import java.util.Random;

public class Requester {

    private static JsonParser parser = new JsonParser();
    private static Random rnd = new Random();

    Record getUser() {
        String response = getResponseBody("https://randus.org/api.php");
        JsonElement jsonElement = parser.parse(response);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Record.class, new RecordGsonDeserializer())
                .create();
        return gson.fromJson(jsonElement, Record.class);
    }

    String getInn() {
        String response = getResponseBody("http://localhost:59403/inn/");
        JsonElement jsonElement = parser.parse(response);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject.get("inn").getAsString();
    }

    String[] getGeoInfo() {
        JsonArray cities;
        String country;
        String countryCode;
        do {
            int rndCountryNumber = rnd.nextInt(150) + 1;
            String response = getResponseBody("https://vk.com/select_ajax.php?act=a_get_countries");
            JsonElement jsonElement = parser.parse(response);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray countryArr = jsonObject.getAsJsonArray("countries").get(rndCountryNumber).getAsJsonArray();
            country = countryArr.get(1).getAsString();
            countryCode = countryArr.get(0).getAsString();
            response = getResponseBody("https://vk.com/select_ajax.php?act=a_get_cities&country=" + countryCode + "&str=******");
            jsonElement = parser.parse(response);
            cities = jsonElement.getAsJsonArray();
        }
        while (cities.size() == 0);
        JsonArray citiesArr = cities.get(rnd.nextInt(cities.size()) + 1).getAsJsonArray();
        String city = citiesArr.get(1).getAsString();
        city = city.replaceAll("[^\\pL|\\-| ]", "");
        String region = citiesArr.get(2).getAsString();
        region = region.replaceAll("[^\\pL|\\-| ]", "");
        if (region.isEmpty()) region = "-";
        return new String[]{country, region, city};
    }

    private static String getResponseBody(String url) {
        HttpRequest request = HttpRequest.get(url);
        if (request.code() == 200) {
            return request.body();
        } else return "Internet address " + url + " is not available";
    }
}



