//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.06 um 03:58:22 PM CEST 
//


package jaxbClasses;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r jumpLogic complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="jumpLogic">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="wordSize" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="programTargetA" type="{}inputList"/>
 *         &lt;element name="programTargetB" type="{}inputList"/>
 *         &lt;element name="output" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inputFlagsCnt" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="inputFlags" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jumpLogic", propOrder = {

})
public class JumpLogic {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected BigInteger wordSize;
    @XmlElement(required = true)
    protected InputList programTargetA;
    @XmlElement(required = true)
    protected InputList programTargetB;
    @XmlElement(required = true)
    protected String output;
    @XmlElement(required = true)
    protected BigInteger inputFlagsCnt;
    @XmlElement(required = true)
    protected String inputFlags;

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
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getWordSize() {
        return wordSize;
    }

    /**
     * Legt den Wert der wordSize-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setWordSize(BigInteger value) {
        this.wordSize = value;
    }

    /**
     * Ruft den Wert der programTargetA-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link InputList }
     *     
     */
    public InputList getProgramTargetA() {
        return programTargetA;
    }

    /**
     * Legt den Wert der programTargetA-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link InputList }
     *     
     */
    public void setProgramTargetA(InputList value) {
        this.programTargetA = value;
    }

    /**
     * Ruft den Wert der programTargetB-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link InputList }
     *     
     */
    public InputList getProgramTargetB() {
        return programTargetB;
    }

    /**
     * Legt den Wert der programTargetB-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link InputList }
     *     
     */
    public void setProgramTargetB(InputList value) {
        this.programTargetB = value;
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

    /**
     * Ruft den Wert der inputFlagsCnt-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getInputFlagsCnt() {
        return inputFlagsCnt;
    }

    /**
     * Legt den Wert der inputFlagsCnt-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setInputFlagsCnt(BigInteger value) {
        this.inputFlagsCnt = value;
    }

    /**
     * Ruft den Wert der inputFlags-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputFlags() {
        return inputFlags;
    }

    /**
     * Legt den Wert der inputFlags-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputFlags(String value) {
        this.inputFlags = value;
    }

}
