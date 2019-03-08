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

    public void print() {
        System.out.println(this.toString());
    }

    public Record(String name, String surname, String patronymic, String gender,
                  LocalDate birthDate, String inn, String zipCode, String country,
                  String region, String city, String street,
                  String building, String apartment) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.gender = gender;
        this.birthDate = birthDate;
        this.inn = inn;
        this.zipCode = zipCode;
        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
    }

    private Integer calculateAge(LocalDate birthDate) {

        return Period.between(birthDate, LocalDate.now()).getYears();
    }


    public Integer getAge() {
        return calculateAge(this.birthDate);
    }
}
