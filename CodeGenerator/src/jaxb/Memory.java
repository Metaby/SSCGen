//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.13 um 03:09:49 AM CEST 
//


package jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r memory complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="memory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="addressSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="wordSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type" type="{}memoryType"/>
 *         &lt;element name="ports" type="{}portList"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "memory", propOrder = {

})
public class Memory {

    @XmlElement(required = true)
    protected String id;
    protected int addressSize;
    protected int wordSize;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected MemoryType type;
    @XmlElement(required = true)
    protected PortList ports;

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
     * Ruft den Wert der type-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MemoryType }
     *     
     */
    public MemoryType getType() {
        return type;
    }

    /**
     * Legt den Wert der type-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MemoryType }
     *     
     */
    public void setType(MemoryType value) {
        this.type = value;
    }

    /**
     * Ruft den Wert der ports-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PortList }
     *     
     */
    public PortList getPorts() {
        return ports;
    }

    /**
     * Legt den Wert der ports-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PortList }
     *     
     */
    public void setPorts(PortList value) {
        this.ports = value;
    }

}
