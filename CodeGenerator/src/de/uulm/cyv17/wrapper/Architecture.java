package de.uulm.cyv17.wrapper;

import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.entities.*;

/**
 * This class represents a complete computer architecture.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class Architecture {
	
	private List<RegisterEntity> registers;
	private List<RomEntity> roms;
	private List<RegisterFileEntity> registerFiles;
	private List<AluEntity> alus;
	private List<MultiplexerEntity> multiplexers;
	private List<CustomEntity> customs;
	private int wordSize;
	
	/**
	 * The constructor of the architecture class. It takes an
	 * architecture object from the jaxb package and converts
	 * it into a architecture object of the wrapper package.
	 * 
	 * @param arch the jaxb architecture object
	 */
	public Architecture(de.uulm.cyv17.jaxb.Architecture arch) {
		registers = new ArrayList<RegisterEntity>();
		roms = new ArrayList<RomEntity>();
		registerFiles = new ArrayList<RegisterFileEntity>();
		alus = new ArrayList<AluEntity>();
		multiplexers = new ArrayList<MultiplexerEntity>();
		customs = new ArrayList<CustomEntity>();
		wordSize = arch.getWordSize();
		for (de.uulm.cyv17.jaxb.Register reg : arch.getRegister()) {
			registers.add(new RegisterEntity(reg));
		}
		for (de.uulm.cyv17.jaxb.Rom rom : arch.getRom()) {
			roms.add(new RomEntity(rom));
		}
		for (de.uulm.cyv17.jaxb.RegisterFile rf : arch.getRegisterFile()) {
			registerFiles.add(new RegisterFileEntity(rf));
		}
		for (de.uulm.cyv17.jaxb.Alu alu : arch.getAlu()) {
			alus.add(new AluEntity(alu));
		}
		for (de.uulm.cyv17.jaxb.Multiplexer mux : arch.getMultiplexer()) {
			multiplexers.add(new MultiplexerEntity(mux));
		}
		for (de.uulm.cyv17.jaxb.Custom cus : arch.getCustom()) {
			customs.add(new CustomEntity(cus));
		}
		for (BaseEntity entity : getAllEntites()) {
			entity.setWordSize(replaceWordSize(entity.getWordSize()));			
		}
	}
	
	/**
	 * Retrieves the correct word size if -1 or -2 is
	 * used as the word size in the architectural
	 * specification.
	 * 
	 * @param wordSize the given word size
	 * @return an integer containing the correct word size
	 */
	private int replaceWordSize(int wordSize) {
		if (wordSize == -1) {
			return getControlVector().getSize();
		}
		if (wordSize == -2) {
			return this.wordSize;
		}
		return wordSize;
	}
	
	/**
	 * Builds and returns the complete control vector
	 * of the architecture.
	 * 
	 * @return the control vector of the architecture
	 */
	public ControlVector getControlVector() {
		ControlVector cv = new ControlVector(0);
		for (BaseEntity be : getAllEntites()) {
			cv = ControlVector.concatenate(cv, be.getControlVector());
		}
		return cv;
	}
	
	/**
	 * Retrieves all input connectors used in components
	 * in the architecture.
	 * 
	 * @param complete if true, removes all non-standard connectors from the list before returning
	 * @return a list of connectors containing the input connectors
	 */
	public List<Connector> getInputConnectors(Boolean complete) {
		List<Connector> connectors = new ArrayList<Connector>();
		for (RegisterEntity reg : registers) {
			connectors.addAll(reg.getInputs());
			connectors.add(reg.getControl());
		}
		for (RomEntity rom : roms) {
			connectors.addAll(rom.getAddresses());
			connectors.add(rom.getControl());
		}
		for (RegisterFileEntity rf : registerFiles) {
			for (Port p : rf.getPorts()) {
				connectors.addAll(p.getInputs());
				connectors.addAll(p.getAddresses());
			}
			connectors.add(rf.getControl());
		}
		for (AluEntity alu : alus) {
			connectors.addAll(alu.getInputsA());
			connectors.addAll(alu.getInputsB());
			connectors.add(alu.getControl());
		}
		for (MultiplexerEntity mux : multiplexers) {
			connectors.addAll(mux.getInputs());
			connectors.add(mux.getControl());
		}
		for (CustomEntity cus : customs) {
			connectors.addAll(cus.getInputConnectors());
		}
		if (!complete) {
			for (int i = 0; i < connectors.size(); i++) {
				if (connectors.get(i) != null) {
					if (connectors.get(i).type != ConnectorType.STANDARD) {
						connectors.remove(i);
						i--;
					}
				}
			}
		}
		return connectors;
	}
	
	/**
	 * Retrieves all output connectors used in components
	 * in the architecture.
	 * 
	 * @param complete if true, removes all non-standard connectors from the list before returning
	 * @return a list of connectors containing the output connectors
	 */
	public List<Connector> getOutputConnectors(Boolean complete) {
		List<Connector> connectors = new ArrayList<Connector>();
		for (RegisterEntity reg : registers) {
			connectors.add(reg.getOutput());
		}
		for (RomEntity rom : roms) {
			connectors.add(rom.getOutput());
		}
		for (RegisterFileEntity rf : registerFiles) {
			for (Port p : rf.getPorts()) {
				connectors.add(p.getOutput());
			}
		}
		for (AluEntity alu : alus) {
			connectors.add(alu.getOutput1());
			connectors.add(alu.getOutput2());
			connectors.add(alu.getStatus());
		}
		for (MultiplexerEntity mux : multiplexers) {
			connectors.add(mux.getOutput());
		}
		for (CustomEntity cus : customs) {
			connectors.addAll(cus.getOutputConnectors());
		}
		if (!complete) {
			for (int i = 0; i < connectors.size(); i++) {
				if (connectors.get(i) != null) {
					if (connectors.get(i).type != ConnectorType.STANDARD) {
						connectors.remove(i);
						i--;
					}
				}
			}	
		}
		return connectors;
	}
	
	/**
	 * Returns all entities of the architecture.
	 * 
	 * @return a list of base entites containing all entities of the architecture
	 */
	public List<BaseEntity> getAllEntites() {
		List<BaseEntity> entities = new ArrayList<BaseEntity>();
		entities.addAll(registers);
		entities.addAll(roms);
		entities.addAll(registerFiles);
		entities.addAll(alus);
		entities.addAll(multiplexers);
		entities.addAll(customs);
		return entities;
	}

	/**
	 * Returns all register entities of the architecture.
	 * @return a list containing the register entities
	 */
	public List<RegisterEntity> getRegisters() {
		return registers;
	}

	/**
	 * Returns all rom entities of the architecture.
	 * @return a list containing the register rom
	 */
	public List<RomEntity> getRoms() {
		return roms;
	}

	/**
	 * Returns all register file entities of the architecture.
	 * @return a list containing the register file entities
	 */
	public List<RegisterFileEntity> getRegisterFiles() {
		return registerFiles;
	}

	/**
	 * Returns all alu entities of the architecture.
	 * @return a list containing the alu entities
	 */
	public List<AluEntity> getAlus() {
		return alus;
	}

	/**
	 * Returns all multiplexer entities of the architecture.
	 * @return a list containing the multiplexer entities
	 */
	public List<MultiplexerEntity> getMultiplexers() {
		return multiplexers;
	}

	/**
	 * Returns all custom entities of the architecture.
	 * @return a list containing the custom entities
	 */
	public List<CustomEntity> getCustoms() {
		return customs;
	}

	/**
	 * Returns the word size of the architecture.
	 * 
	 * @return an integer containing the word size
	 */
	public int getWordSize() {
		return wordSize;
	}
	
}
