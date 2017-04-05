//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.05 um 05:22:40 PM CEST 
//


package jaxbClasses;

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
 *     &lt;enumeration value="subtract"/>
 *     &lt;enumeration value="lShiftLeft"/>
 *     &lt;enumeration value="lShiftRight"/>
 *     &lt;enumeration value="aShiftRight"/>
 *     &lt;enumeration value="divide"/>
 *     &lt;enumeration value="multiply"/>
 *     &lt;enumeration value="rotateL"/>
 *     &lt;enumeration value="rotateR"/>
 *     &lt;enumeration value="bitAnd"/>
 *     &lt;enumeration value="bitOr"/>
 *     &lt;enumeration value="bitXor"/>
 *     &lt;enumeration value="bitNot"/>
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
    @XmlEnumValue("subtract")
    SUBTRACT("subtract"),
    @XmlEnumValue("lShiftLeft")
    L_SHIFT_LEFT("lShiftLeft"),
    @XmlEnumValue("lShiftRight")
    L_SHIFT_RIGHT("lShiftRight"),
    @XmlEnumValue("aShiftRight")
    A_SHIFT_RIGHT("aShiftRight"),
    @XmlEnumValue("divide")
    DIVIDE("divide"),
    @XmlEnumValue("multiply")
    MULTIPLY("multiply"),
    @XmlEnumValue("rotateL")
    ROTATE_L("rotateL"),
    @XmlEnumValue("rotateR")
    ROTATE_R("rotateR"),
    @XmlEnumValue("bitAnd")
    BIT_AND("bitAnd"),
    @XmlEnumValue("bitOr")
    BIT_OR("bitOr"),
    @XmlEnumValue("bitXor")
    BIT_XOR("bitXor"),
    @XmlEnumValue("bitNot")
    BIT_NOT("bitNot");
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
