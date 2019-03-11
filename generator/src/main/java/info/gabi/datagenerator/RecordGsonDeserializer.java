package info.gabi.datagenerator;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RecordGsonDeserializer implements JsonDeserializer<Record> {

    @Override
    public Record deserialize(JsonElement json, Type type,
                              JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement jsonName = jsonObject.get("fname");
        JsonElement jsonSurname = jsonObject.get("lname");
        JsonElement jsonPatronymic = jsonObject.get("patronymic");
        String gender = jsonObject.get("gender").getAsString().equals("w") ? "ж" : "м";
        String birthdate = jsonObject.get("date").getAsString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru"));
        LocalDate date = LocalDate.parse(birthdate, formatter);

        JsonElement jsonZipCode = jsonObject.get("postcode");
        JsonElement jsonStreet = jsonObject.get("street");
        JsonElement jsonBuilding = jsonObject.get("house");
        JsonElement jsonApartment = jsonObject.get("apartment");

        return new Record(jsonName.getAsString(), jsonSurname.getAsString(),
                jsonPatronymic.getAsString(), gender, date, "", jsonZipCode.getAsString(),
                "", "", "", jsonStreet.getAsString(),
                jsonBuilding.getAsString(), jsonApartment.getAsString());
    }
}
