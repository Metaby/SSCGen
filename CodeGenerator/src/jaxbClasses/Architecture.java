//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.05 um 05:22:40 PM CEST 
//


package jaxbClasses;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="register" type="{}register" maxOccurs="unbounded"/>
 *         &lt;element name="stack" type="{}stack" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="registerFile" type="{}registerFile" maxOccurs="unbounded"/>
 *         &lt;element name="alu" type="{}alu" maxOccurs="unbounded"/>
 *         &lt;element name="memory" type="{}memory" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="wordSize" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "register",
    "stack",
    "registerFile",
    "alu",
    "memory"
})
@XmlRootElement(name = "architecture")
public class Architecture {

    @XmlElement(required = true)
    protected List<Register> register;
    protected List<Stack> stack;
    @XmlElement(required = true)
    protected List<RegisterFile> registerFile;
    @XmlElement(required = true)
    protected List<Alu> alu;
    protected List<Memory> memory;
    @XmlAttribute(name = "wordSize")
    protected BigInteger wordSize;

    /**
     * Gets the value of the register property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the register property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegister().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Register }
     * 
     * 
     */
    public List<Register> getRegister() {
        if (register == null) {
            register = new ArrayList<Register>();
        }
        return this.register;
    }

    /**
     * Gets the value of the stack property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stack property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStack().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Stack }
     * 
     * 
     */
    public List<Stack> getStack() {
        if (stack == null) {
            stack = new ArrayList<Stack>();
        }
        return this.stack;
    }

    /**
     * Gets the value of the registerFile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the registerFile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegisterFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RegisterFile }
     * 
     * 
     */
    public List<RegisterFile> getRegisterFile() {
        if (registerFile == null) {
            registerFile = new ArrayList<RegisterFile>();
        }
        return this.registerFile;
    }

    /**
     * Gets the value of the alu property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alu property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlu().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Alu }
     * 
     * 
     */
    public List<Alu> getAlu() {
        if (alu == null) {
            alu = new ArrayList<Alu>();
        }
        return this.alu;
    }

    /**
     * Gets the value of the memory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the memory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMemory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Memory }
     * 
     * 
     */
    public List<Memory> getMemory() {
        if (memory == null) {
            memory = new ArrayList<Memory>();
        }
        return this.memory;
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

}