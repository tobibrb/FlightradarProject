package rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Notebook on 09.12.2015.
 */
@XmlRootElement
public class EmailVO {
    private String email;
    private List<String> airport;

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElementWrapper(name="airports")
    @XmlElement(name="airport")
    public void setAirport(List<String> airport) {
        this.airport = airport;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getAirport() {
        return airport;
    }
}
