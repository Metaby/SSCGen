//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.05 um 05:22:40 PM CEST 
//


package jaxbClasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r alu complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="alu">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inputsOperandA" type="{}inputList"/>
 *         &lt;element name="inputsOperandB" type="{}inputList"/>
 *         &lt;element name="output" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operations" type="{}operationList"/>
 *         &lt;element name="conditions" type="{}conditionList"/>
 *       &lt;/all>
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
    @XmlElement(required = true)
    protected InputList inputsOperandA;
    @XmlElement(required = true)
    protected InputList inputsOperandB;
    @XmlElement(required = true)
    protected String output;
    @XmlElement(required = true)
    protected OperationList operations;
    @XmlElement(required = true)
    protected ConditionList conditions;

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

}