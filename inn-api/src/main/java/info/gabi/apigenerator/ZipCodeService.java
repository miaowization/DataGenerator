package info.gabi.apigenerator;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ZipCodeService {

    private Random rnd;

    public ZipCodeService() {
        this.rnd = new Random();
    }

    public ZipCode getZipcode() {
        return new ZipCode(rnd.nextInt(100000) + 100000);
    }

}