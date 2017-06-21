//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.05.05 um 10:46:20 AM CEST 
//


package de.uulm.cyv17.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="register" type="{}register" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="rom" type="{}rom" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="registerFile" type="{}registerFile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="alu" type="{}alu" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="multiplexer" type="{}multiplexer" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="wordSize" type="{http://www.w3.org/2001/XMLSchema}int" />
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
    "rom",
    "registerFile",
    "alu",
    "multiplexer"
})
@XmlRootElement(name = "architecture")
public class Architecture {

    protected List<Register> register;
    protected List<Rom> rom;
    protected List<RegisterFile> registerFile;
    protected List<Alu> alu;
    protected List<Multiplexer> multiplexer;
    @XmlAttribute(name = "wordSize")
    protected Integer wordSize;

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
     * Gets the value of the rom property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rom property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRom().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Rom }
     * 
     * 
     */
    public List<Rom> getRom() {
        if (rom == null) {
            rom = new ArrayList<Rom>();
        }
        return this.rom;
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
     * Gets the value of the multiplexer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the multiplexer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMultiplexer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Multiplexer }
     * 
     * 
     */
    public List<Multiplexer> getMultiplexer() {
        if (multiplexer == null) {
            multiplexer = new ArrayList<Multiplexer>();
        }
        return this.multiplexer;
    }

    /**
     * Ruft den Wert der wordSize-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWordSize() {
        return wordSize;
    }

    /**
     * Legt den Wert der wordSize-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWordSize(Integer value) {
        this.wordSize = value;
    }

}