package rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Notebook on 09.12.2015.
 */
@XmlRootElement
public class EmailListVo {

    private List<EmailVO> emails;

    @XmlElementWrapper(name="emails")
    @XmlElement(name="email")
    public List<EmailVO> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailVO> emails) {
        this.emails = emails;
    }
}
