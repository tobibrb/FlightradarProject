package rest;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Notebook on 07.12.2015.
 */

@RestController
public class EmailServiceRest {

    @RequestMapping("/flight-radar/sub-email/")
    private void subscribeEmail(@RequestParam(value="email") String email, @RequestParam(value="flughafen") String flughafen){


        //TODO E-Mail registration
    }
    private void unSubscribeEmail(){
        // TODO E-Mail unregister
    }

    //TODO email reg mit Ziel Flughafen
}
