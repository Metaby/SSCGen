//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.03 um 05:20:44 PM CEST 
//


package Schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für portDirectionType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="portDirectionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="in"/>
 *     &lt;enumeration value="out"/>
 *     &lt;enumeration value="inOut"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "portDirectionType")
@XmlEnum
public enum PortDirectionType {

    @XmlEnumValue("in")
    IN("in"),
    @XmlEnumValue("out")
    OUT("out"),
    @XmlEnumValue("inOut")
    IN_OUT("inOut");
    private final String value;

    PortDirectionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PortDirectionType fromValue(String v) {
        for (PortDirectionType c: PortDirectionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
