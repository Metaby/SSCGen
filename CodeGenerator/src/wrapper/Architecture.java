package wrapper;

import java.util.ArrayList;
import java.util.List;

import wrapper.entities.*;

public class Architecture {
	
	private List<RegisterEntity> registers;
	private List<RomEntity> roms;
	private List<RegisterFileEntity> registerFiles;
	private List<AluEntity> alus;
	private List<MultiplexerEntity> multiplexers;
	private int wordSize;
	
	public Architecture(jaxb.Architecture arch) {
		registers = new ArrayList<RegisterEntity>();
		roms = new ArrayList<RomEntity>();
		registerFiles = new ArrayList<RegisterFileEntity>();
		alus = new ArrayList<AluEntity>();
		multiplexers = new ArrayList<MultiplexerEntity>();
		wordSize = arch.getWordSize();
		for (jaxb.Register reg : arch.getRegister()) {
			registers.add(new RegisterEntity(reg));
		}
		for (jaxb.Rom rom : arch.getRom()) {
			roms.add(new RomEntity(rom));
		}
		for (jaxb.RegisterFile rf : arch.getRegisterFile()) {
			registerFiles.add(new RegisterFileEntity(rf));
		}
		for (jaxb.Alu alu : arch.getAlu()) {
			alus.add(new AluEntity(alu));
		}
		for (jaxb.Multiplexer mux : arch.getMultiplexer()) {
			multiplexers.add(new MultiplexerEntity(mux));
		}
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
		entities.addAll(multiplexers);
		entities.addAll(alus);
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

	public int getWordSize() {
		return wordSize;
	}
	
}
