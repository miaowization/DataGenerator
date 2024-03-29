package info.gabi.apigenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private InnService innService;

    @GetMapping(path = "/inn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Inn> inn() {
        return new ResponseEntity<Inn>(innService.getINN(), HttpStatus.OK);
    }

}