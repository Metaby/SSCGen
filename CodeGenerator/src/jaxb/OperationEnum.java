//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.15 um 05:20:48 PM CEST 
//


package jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für operationEnum.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="operationEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="add"/>
 *     &lt;enumeration value="add_u"/>
 *     &lt;enumeration value="sub"/>
 *     &lt;enumeration value="sub_u"/>
 *     &lt;enumeration value="mul"/>
 *     &lt;enumeration value="mul_u"/>
 *     &lt;enumeration value="div"/>
 *     &lt;enumeration value="div_u"/>
 *     &lt;enumeration value="rr"/>
 *     &lt;enumeration value="rl"/>
 *     &lt;enumeration value="srl"/>
 *     &lt;enumeration value="sll"/>
 *     &lt;enumeration value="sra"/>
 *     &lt;enumeration value="and"/>
 *     &lt;enumeration value="or"/>
 *     &lt;enumeration value="xor"/>
 *     &lt;enumeration value="not"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "operationEnum")
@XmlEnum
public enum OperationEnum {

    @XmlEnumValue("add")
    ADD("add"),
    @XmlEnumValue("add_u")
    ADD_U("add_u"),
    @XmlEnumValue("sub")
    SUB("sub"),
    @XmlEnumValue("sub_u")
    SUB_U("sub_u"),
    @XmlEnumValue("mul")
    MUL("mul"),
    @XmlEnumValue("mul_u")
    MUL_U("mul_u"),
    @XmlEnumValue("div")
    DIV("div"),
    @XmlEnumValue("div_u")
    DIV_U("div_u"),
    @XmlEnumValue("rr")
    RR("rr"),
    @XmlEnumValue("rl")
    RL("rl"),
    @XmlEnumValue("srl")
    SRL("srl"),
    @XmlEnumValue("sll")
    SLL("sll"),
    @XmlEnumValue("sra")
    SRA("sra"),
    @XmlEnumValue("and")
    AND("and"),
    @XmlEnumValue("or")
    OR("or"),
    @XmlEnumValue("xor")
    XOR("xor"),
    @XmlEnumValue("not")
    NOT("not");
    private final String value;

    OperationEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OperationEnum fromValue(String v) {
        for (OperationEnum c: OperationEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
