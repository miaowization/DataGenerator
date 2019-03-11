package info.gabi.datagenerator;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Period;

@Builder
@Data
public class Record {

    private String name;
    private String surname;
    private String patronymic;
    private String gender;
    private LocalDate birthDate;
    private String inn;
    private String zipCode;
    private String country;
    private String region;
    private String city;
    private String street;
    private String building;
    private String apartment;

    @Override
    public String toString() {
        return "info.gabi.datagenerator.Record{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", age=" + getAge() +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", inn='" + inn + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", apartment='" + apartment + '\'' +
                '}';
    }

    Integer getAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }
}
