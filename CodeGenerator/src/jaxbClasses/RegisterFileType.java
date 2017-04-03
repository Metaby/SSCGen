//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.03 um 05:20:44 PM CEST 
//


package Schema;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für registerFileType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="registerFileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="addressSize" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ports" type="{}portsType"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registerFileType", propOrder = {

})
public class RegisterFileType {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected BigInteger addressSize;
    @XmlElement(required = true)
    protected PortsType ports;

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Ruft den Wert der addressSize-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAddressSize() {
        return addressSize;
    }

    /**
     * Legt den Wert der addressSize-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAddressSize(BigInteger value) {
        this.addressSize = value;
    }

    /**
     * Ruft den Wert der ports-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PortsType }
     *     
     */
    public PortsType getPorts() {
        return ports;
    }

    /**
     * Legt den Wert der ports-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PortsType }
     *     
     */
    public void setPorts(PortsType value) {
        this.ports = value;
    }

}
