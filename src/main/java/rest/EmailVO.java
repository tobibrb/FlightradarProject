package rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

/**
 * Created by Notebook on 09.12.2015.
 */
@XmlRootElement
public class EmailVO extends AEmail {
    private String uuid;
    private String email;
    private List<String> airport;

    public EmailVO() {
        this.uuid = UUID.randomUUID().toString();
    }

    public EmailVO(String email, List<String> airports) {
        this.uuid = UUID.randomUUID().toString();
        this.email = email;
        this.airport = airports;
    }

    @XmlElement
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElementWrapper(name = "airports")
    @XmlElement(name = "airport")
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
