//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.15 um 05:20:48 PM CEST 
//


package jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r rom complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="rom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="wordSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="addressSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="contentFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="addresses" type="{}addressList"/>
 *         &lt;element name="output" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rom", propOrder = {

})
public class Rom {

    @XmlElement(required = true)
    protected String id;
    protected int wordSize;
    protected int addressSize;
    @XmlElement(required = true)
    protected String contentFile;
    @XmlElement(required = true)
    protected AddressList addresses;
    @XmlElement(required = true)
    protected String output;

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
     * Ruft den Wert der wordSize-Eigenschaft ab.
     * 
     */
    public int getWordSize() {
        return wordSize;
    }

    /**
     * Legt den Wert der wordSize-Eigenschaft fest.
     * 
     */
    public void setWordSize(int value) {
        this.wordSize = value;
    }

    /**
     * Ruft den Wert der addressSize-Eigenschaft ab.
     * 
     */
    public int getAddressSize() {
        return addressSize;
    }

    /**
     * Legt den Wert der addressSize-Eigenschaft fest.
     * 
     */
    public void setAddressSize(int value) {
        this.addressSize = value;
    }

    /**
     * Ruft den Wert der contentFile-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentFile() {
        return contentFile;
    }

    /**
     * Legt den Wert der contentFile-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentFile(String value) {
        this.contentFile = value;
    }

    /**
     * Ruft den Wert der addresses-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AddressList }
     *     
     */
    public AddressList getAddresses() {
        return addresses;
    }

    /**
     * Legt den Wert der addresses-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressList }
     *     
     */
    public void setAddresses(AddressList value) {
        this.addresses = value;
    }

    /**
     * Ruft den Wert der output-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutput() {
        return output;
    }

    /**
     * Legt den Wert der output-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutput(String value) {
        this.output = value;
    }

}
