package rest;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.michelboudreau.alternator.AlternatorDB;
import com.michelboudreau.alternatorv2.AlternatorDBClientV2;
import dataanalyser.Flight;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Notebook on 12.12.2015.
 */
public class S3ServiveIT {
    private static EmailListVo list;
    private static EmailVO first;
    private static EmailVO second;
    private static EmailVO third;
    private static File file;

    @Before
    public void setUp() throws Exception {
        List<String> list1 = new ArrayList<String>();
        list1.add("TXL");

        List<String> list2 = new ArrayList<String>();
        list2.add("TXL");
        list2.add("SHF");

        first = new EmailVO("first@test.de", null);
        second = new EmailVO("second@test.de", list1);
        third = new EmailVO("third@test.de", list2);
        List<EmailVO> emailListe = new ArrayList<EmailVO>();
        emailListe.add(first);
        emailListe.add(second);
        emailListe.add(third);

        list = new EmailListVo();
        list.setEmails(emailListe);

        file = XmlService.createEmailXml("Test", list);

    }


    @Test
    public void putToS3Test() {
        assertTrue(S3Service.putToS3(file));
    }
    @After
    public void delete(){S3Service.deleteFromS3(file.getName());}
}
