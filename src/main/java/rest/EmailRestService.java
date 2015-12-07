package rest;


import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Notebook on 07.12.2015.
 */

@RestController

public class EmailRestService {

    final static Logger logger = Logger.getLogger(EmailRestService.class);
    final static String FROM = "toni.p.anders@gmail.com";
    @RequestMapping("/flight-radar/sub-email/")
    private void subscribeEmail(@RequestParam(value="email") String email, @RequestParam(value="flughafen") String flughafen){


        //TODO E-Mail registration
    }
    private void unSubscribeEmail(){
        // TODO E-Mail unregister
    }

    //TODO email reg mit Ziel Flughafen

    protected static boolean sendEmail(String destinationName, String subjectContent, String bobyContent){
        boolean sendEmail = false;

        Destination destination = new Destination().withToAddresses(new String[]{destinationName});
        Content subject = new Content().withData(subjectContent);
        Content textBody = new Content().withData(bobyContent);
        Body body = new Body().withText(textBody);
        Message message = new Message().withSubject(subject).withBody(body);
        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);


        try {
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(new DefaultAWSCredentialsProviderChain());
            Region REGION = Region.getRegion(Regions.US_EAST_1);
            client.setRegion(REGION);

            logger.debug("Send E-Mail");
            client.sendEmail(request);
            sendEmail = true;
        } catch (Exception e) {
            logger.error(e.getMessage());

        }
        return sendEmail;

    }
}
