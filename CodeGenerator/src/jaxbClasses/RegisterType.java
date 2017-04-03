//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.03 um 05:20:44 PM CEST 
//


package Schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für registerType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="registerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gpio" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="inputs" type="{}inputsType"/>
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
@XmlType(name = "registerType", propOrder = {

})
public class RegisterType {

    @XmlElement(required = true)
    protected String id;
    protected boolean gpio;
    @XmlElement(required = true)
    protected InputsType inputs;
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
     * Ruft den Wert der gpio-Eigenschaft ab.
     * 
     */
    public boolean isGpio() {
        return gpio;
    }

    /**
     * Legt den Wert der gpio-Eigenschaft fest.
     * 
     */
    public void setGpio(boolean value) {
        this.gpio = value;
    }

    /**
     * Ruft den Wert der inputs-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link InputsType }
     *     
     */
    public InputsType getInputs() {
        return inputs;
    }

    /**
     * Legt den Wert der inputs-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link InputsType }
     *     
     */
    public void setInputs(InputsType value) {
        this.inputs = value;
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
