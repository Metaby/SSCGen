//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.05.05 um 10:46:20 AM CEST 
//


package de.uulm.cyv17.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für alu complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="alu">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="wordSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="inputsOperandA" type="{}inputList"/>
 *         &lt;element name="inputsOperandB" type="{}inputList"/>
 *         &lt;element name="output1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="output2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operations" type="{}operationList"/>
 *         &lt;element name="conditions" type="{}conditionList" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="control" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *       &lt;attribute name="x" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="y" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alu", propOrder = {

})
public class Alu {

    @XmlElement(required = true)
    protected String id;
    protected int wordSize;
    @XmlElement(required = true)
    protected InputList inputsOperandA;
    @XmlElement(required = true)
    protected InputList inputsOperandB;
    @XmlElement(required = true)
    protected String output1;
    @XmlElement(required = true)
    protected String output2;
    @XmlElement(required = true)
    protected OperationList operations;
    protected ConditionList conditions;
    protected String status;
    @XmlElement(required = true)
    protected String control;
    @XmlAttribute(name = "x")
    protected Integer x;
    @XmlAttribute(name = "y")
    protected Integer y;

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
     * Ruft den Wert der inputsOperandA-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link InputList }
     *     
     */
    public InputList getInputsOperandA() {
        return inputsOperandA;
    }

    /**
     * Legt den Wert der inputsOperandA-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link InputList }
     *     
     */
    public void setInputsOperandA(InputList value) {
        this.inputsOperandA = value;
    }

    /**
     * Ruft den Wert der inputsOperandB-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link InputList }
     *     
     */
    public InputList getInputsOperandB() {
        return inputsOperandB;
    }

    /**
     * Legt den Wert der inputsOperandB-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link InputList }
     *     
     */
    public void setInputsOperandB(InputList value) {
        this.inputsOperandB = value;
    }

    /**
     * Ruft den Wert der output1-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutput1() {
        return output1;
    }

    /**
     * Legt den Wert der output1-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutput1(String value) {
        this.output1 = value;
    }

    /**
     * Ruft den Wert der output2-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutput2() {
        return output2;
    }

    /**
     * Legt den Wert der output2-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutput2(String value) {
        this.output2 = value;
    }

    /**
     * Ruft den Wert der operations-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link OperationList }
     *     
     */
    public OperationList getOperations() {
        return operations;
    }

    /**
     * Legt den Wert der operations-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationList }
     *     
     */
    public void setOperations(OperationList value) {
        this.operations = value;
    }

    /**
     * Ruft den Wert der conditions-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ConditionList }
     *     
     */
    public ConditionList getConditions() {
        return conditions;
    }

    /**
     * Legt den Wert der conditions-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionList }
     *     
     */
    public void setConditions(ConditionList value) {
        this.conditions = value;
    }

    /**
     * Ruft den Wert der status-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Legt den Wert der status-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Ruft den Wert der control-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControl() {
        return control;
    }

    /**
     * Legt den Wert der control-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControl(String value) {
        this.control = value;
    }

    /**
     * Ruft den Wert der x-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getX() {
        return x;
    }

    /**
     * Legt den Wert der x-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setX(Integer value) {
        this.x = value;
    }

    /**
     * Ruft den Wert der y-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getY() {
        return y;
    }

    /**
     * Legt den Wert der y-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setY(Integer value) {
        this.y = value;
    }

}
