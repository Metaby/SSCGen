package de.uulm.cyv17.wrapper;

import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.entities.*;

public class Architecture {
	
	private List<RegisterEntity> registers;
	private List<RomEntity> roms;
	private List<RegisterFileEntity> registerFiles;
	private List<AluEntity> alus;
	private List<MultiplexerEntity> multiplexers;
	private List<CustomEntity> customs;
	private int wordSize;
	
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
	
	private int replaceWordSize(int wwordSize) {
		if (wwordSize == -1) {
			return getControlVector().getSize();
		}
		if (wwordSize == -2) {
			return this.wordSize;
		}
		return wwordSize;
	}
	
	public ControlVector getControlVector() {
		ControlVector cv = new ControlVector(0);
		for (BaseEntity be : getAllEntites()) {
			cv = ControlVector.concatenate(cv, be.getControlVector());
		}
		return cv;
	}
	
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

	public List<RegisterEntity> getRegisters() {
		return registers;
	}

	public List<RomEntity> getRoms() {
		return roms;
	}

	public List<RegisterFileEntity> getRegisterFiles() {
		return registerFiles;
	}

	public List<AluEntity> getAlus() {
		return alus;
	}

	public List<MultiplexerEntity> getMultiplexers() {
		return multiplexers;
	}

	public List<CustomEntity> getCustoms() {
		return customs;
	}

	public int getWordSize() {
		return wordSize;
	}
	
}
