package de.uulm.cyv17.wrapper.entities;

import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.tool.ControlField;
import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.Connector;
import de.uulm.cyv17.wrapper.ConnectorType;
import de.uulm.cyv17.wrapper.Wrapper;

/**
 * This class represents the multiplexer entity.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class MultiplexerEntity extends BaseEntity {
	private List<Connector> inputs;
	private Connector output;
	
	/**
	 * The constructor of the multiplexer entity. It takes
	 * an multiplexer object of the jaxb packages and converts
	 * it into an object of the wrapper.entities 
	 * package.
	 * 
	 * @param alu the jaxb multiplexer object
	 */
	public MultiplexerEntity(de.uulm.cyv17.jaxb.Multiplexer mux) {
		id = mux.getId();
		wordSize = mux.getWordSize();
		output = new Connector(mux.getOutput(), wordSize);
		inputs = new ArrayList<Connector>();
		for (int i = 0; i < mux.getInputs().getInput().size(); i++) {
			inputs.add(new Connector(mux.getInputs().getInput().get(i), wordSize));
		}
		control = new Connector(mux.getControl(), (int)Math.ceil(Math.log(inputs.size()) / Math.log(2)));
	}

	/**
	 * This method is used to set the word size of the entity.
	 * 
	 * @param wordSize the word size to be set
	 */
	@Override
	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
		for (Connector c : inputs) {
			c.size = wordSize;
		}
	}
	
	/**
	 * Returns the control vector of the entity
	 * 
	 * @return the control vector
	 */
	public ControlVector getControlVector() {
		if (control.type == ConnectorType.SYSTEM_AUTO) {
			int iselSize = Wrapper.log2(inputs.size());
			ControlVector cv = new ControlVector(iselSize);
			if (iselSize > 0) {
				ControlField iselField = new ControlField(id + "_isel", 0, iselSize - 1);
				for (int i = 0; i < inputs.size(); i++) {
					iselField.addParameter(inputs.get(i).toString(), i);		
				}
				cv.addField(iselField);
			}
			return cv;			
		}
		return new ControlVector(0);
	}
	
	/**
	 * Returns the input connectors of the entity.
	 * 
	 * @return a list containing the input connectors
	 */
	public List<Connector> getInputs() {
		return inputs;
	}
	
	/**
	 * Returns the output connector of the entity.
	 * 	
	 * @return the output connector
	 */
	public Connector getOutput() {
		return output;
	}
}
