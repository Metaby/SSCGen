//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.06 um 03:58:22 PM CEST 
//


package jaxbClasses;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r conditionEnum.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="conditionEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="zero"/>
 *     &lt;enumeration value="notZero"/>
 *     &lt;enumeration value="equal"/>
 *     &lt;enumeration value="notEqual"/>
 *     &lt;enumeration value="lower"/>
 *     &lt;enumeration value="higher"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "conditionEnum")
@XmlEnum
public enum ConditionEnum {

    @XmlEnumValue("zero")
    ZERO("zero"),
    @XmlEnumValue("notZero")
    NOT_ZERO("notZero"),
    @XmlEnumValue("equal")
    EQUAL("equal"),
    @XmlEnumValue("notEqual")
    NOT_EQUAL("notEqual"),
    @XmlEnumValue("lower")
    LOWER("lower"),
    @XmlEnumValue("higher")
    HIGHER("higher");
    private final String value;

    ConditionEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConditionEnum fromValue(String v) {
        for (ConditionEnum c: ConditionEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
