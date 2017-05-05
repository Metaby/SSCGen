//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.05.05 um 10:46:20 AM CEST 
//


package jaxb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Architecture }
     * 
     */
    public Architecture createArchitecture() {
        return new Architecture();
    }

    /**
     * Create an instance of {@link Register }
     * 
     */
    public Register createRegister() {
        return new Register();
    }

    /**
     * Create an instance of {@link Rom }
     * 
     */
    public Rom createRom() {
        return new Rom();
    }

    /**
     * Create an instance of {@link RegisterFile }
     * 
     */
    public RegisterFile createRegisterFile() {
        return new RegisterFile();
    }

    /**
     * Create an instance of {@link Alu }
     * 
     */
    public Alu createAlu() {
        return new Alu();
    }

    /**
     * Create an instance of {@link Multiplexer }
     * 
     */
    public Multiplexer createMultiplexer() {
        return new Multiplexer();
    }

    /**
     * Create an instance of {@link InputList }
     * 
     */
    public InputList createInputList() {
        return new InputList();
    }

    /**
     * Create an instance of {@link OperationList }
     * 
     */
    public OperationList createOperationList() {
        return new OperationList();
    }

    /**
     * Create an instance of {@link ConditionList }
     * 
     */
    public ConditionList createConditionList() {
        return new ConditionList();
    }

    /**
     * Create an instance of {@link Port }
     * 
     */
    public Port createPort() {
        return new Port();
    }

    /**
     * Create an instance of {@link AddressList }
     * 
     */
    public AddressList createAddressList() {
        return new AddressList();
    }

    /**
     * Create an instance of {@link PortList }
     * 
     */
    public PortList createPortList() {
        return new PortList();
    }

}
