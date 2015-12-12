package rest;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Notebook on 10.12.2015.
 */
@XmlSeeAlso({EmailVO.class,EmailListVo.class})
@XmlType
public abstract class AEmail {

}
