//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.13 um 03:09:49 AM CEST 
//


package jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für conditionEnum.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="conditionEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="zero"/>
 *     &lt;enumeration value="gt"/>
 *     &lt;enumeration value="gt_u"/>
 *     &lt;enumeration value="lt"/>
 *     &lt;enumeration value="lt_u"/>
 *     &lt;enumeration value="geq"/>
 *     &lt;enumeration value="geq_u"/>
 *     &lt;enumeration value="leq"/>
 *     &lt;enumeration value="leq_u"/>
 *     &lt;enumeration value="eq"/>
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
    @XmlEnumValue("gt")
    GT("gt"),
    @XmlEnumValue("gt_u")
    GT_U("gt_u"),
    @XmlEnumValue("lt")
    LT("lt"),
    @XmlEnumValue("lt_u")
    LT_U("lt_u"),
    @XmlEnumValue("geq")
    GEQ("geq"),
    @XmlEnumValue("geq_u")
    GEQ_U("geq_u"),
    @XmlEnumValue("leq")
    LEQ("leq"),
    @XmlEnumValue("leq_u")
    LEQ_U("leq_u"),
    @XmlEnumValue("eq")
    EQ("eq");
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
