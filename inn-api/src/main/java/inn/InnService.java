package inn;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class InnService {
    private Random rnd;

    public InnService(){
        this.rnd = new Random();
    }

    InnObject getINN() {
        int[] INN = new int[12];
        INN[0] = 7;
        INN[1] = 7;
        INN[2] = 5;
        INN[3] = 1;
        String resultINN = "" + INN[0] + INN[1] + INN[2] + INN[3];
        for (int i = 4; i < 10; i++) {
            INN[i] = rnd.nextInt(9);
            resultINN += INN[i];
        }

        INN[10] = ((7 * INN[0] + 2 * INN[1] + 4 * INN[2] + 10 * INN[3] + 3 * INN[4] + 5 * INN[5] + 9 * INN[6] + 4 * INN[7] + 6 * INN[8] + 8 * INN[9]) % 11) % 10;
        resultINN += INN[10];


        INN[11] = ((3 * INN[0] + 7 * INN[1] + 2 * INN[2] + 4 * INN[3] + 10 * INN[4] + 3 * INN[5] + 5 * INN[6] + 9 * INN[7] + 4 * INN[8] + 6 * INN[9] + 8 * INN[10]) % 11) % 10;
        resultINN += INN[11];
        return new InnObject(resultINN);
    }
}

