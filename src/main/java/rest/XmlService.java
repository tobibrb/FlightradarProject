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

    /**
     * Erstellt eine XML Datei
     *
     * @param filename Dateiname
     * @param email    Dateiinhalt in Form eines AEmail Objektes
     * @return XML File
     */
    public static File createEmailXml(String filename, AEmail email) {
        File file = new File(filename);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AEmail.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(email, file);
        } catch (JAXBException e) {
            logger.error(e.getMessage());
        }
        return file;
    }

    /**
     * Liest ein File vom Typ XML aus und gibt ein Objekt in der Form der XML Datei zurück.
     *
     * @param file XML File
     * @return Gibt ein AEmail Objekt zurück
     */
    public static AEmail readEmailXml(File file) {
        AEmail email = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AEmail.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            email = (AEmail) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            logger.error(e.getMessage());
        }
        return email;
    }


}
