package rest;

import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Notebook on 09.12.2015.
 */
public class XmlService {
    final static Logger logger = Logger.getLogger(XmlService.class);

    public static File createEmailXml(String filename, EmailVO email){
        File file = new File(filename+".xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(EmailVO.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(email, file);
        } catch (JAXBException e) {
            logger.error(e.getMessage());
        }
        return file;
    }

    public static EmailVO readEmailXml(File file){
        EmailVO email = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(EmailVO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            email = (EmailVO) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            logger.error(e.getMessage());
        }
        return email;
    }


}
