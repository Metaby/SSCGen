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
 * <p>Java-Klasse für memoryTypeType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="memoryTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="intern"/>
 *     &lt;enumeration value="extern"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "memoryTypeType")
@XmlEnum
public enum MemoryTypeType {

    @XmlEnumValue("intern")
    INTERN("intern"),
    @XmlEnumValue("extern")
    EXTERN("extern");
    private final String value;

    MemoryTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MemoryTypeType fromValue(String v) {
        for (MemoryTypeType c: MemoryTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
