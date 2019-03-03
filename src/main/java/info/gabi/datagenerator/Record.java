package info.gabi.datagenerator;

import java.time.LocalDate;
import java.time.Period;

public class Record {

    private String name;
    private String surname;
    private String patronymic;
    private Integer age;
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
                ", age=" + age +
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

    public void print(){
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
        this.age = calculateAge(this.birthDate);
        this.inn = inn;
        this.zipCode = zipCode;
        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
    }

    private Integer calculateAge(LocalDate birthDate)
    {

        return Period.between(birthDate, LocalDate.now()).getYears();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
