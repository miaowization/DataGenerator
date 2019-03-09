package info.gabi.apigenerator;

public class ZipCode {
    private final String zipcode;

    public ZipCode(String zipcode) {
        this.zipcode = zipcode;
    }

    ZipCode(int zipCode) {
        this.zipcode = Integer.toString(zipCode);
    }

    public String getZipcode() {
        return zipcode;
    }
}