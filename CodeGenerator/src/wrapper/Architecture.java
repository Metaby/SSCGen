package wrapper;

import java.util.ArrayList;
import java.util.List;

public class Architecture {
	
	private List<Register> registers;
	private List<Rom> roms;
	private List<RegisterFile> registerFiles;
	private List<Alu> alus;
	private List<Multiplexer> multiplexers;
	private int wordSize;
	
	public Architecture(jaxb.Architecture arch) {
		registers = new ArrayList<Register>();
		roms = new ArrayList<Rom>();
		registerFiles = new ArrayList<RegisterFile>();
		alus = new ArrayList<Alu>();
		multiplexers = new ArrayList<Multiplexer>();
		wordSize = arch.getWordSize();
		for (jaxb.Register reg : arch.getRegister()) {
			registers.add(new Register(reg));
		}
		for (jaxb.Rom rom : arch.getRom()) {
			roms.add(new Rom(rom));
		}
		for (jaxb.RegisterFile rf : arch.getRegisterFile()) {
			registerFiles.add(new RegisterFile(rf));
		}
		for (jaxb.Alu alu : arch.getAlu()) {
			alus.add(new Alu(alu));
		}
		for (jaxb.Multiplexer mux : arch.getMultiplexer()) {
			multiplexers.add(new Multiplexer(mux));
		}
	}
	
	public List<Connector> getInputConnectors(Boolean complete) {
		List<Connector> connectors = new ArrayList<Connector>();
		for (Register reg : registers) {
			connectors.addAll(reg.getInputs());
			connectors.add(reg.getControl());
		}
		for (Rom rom : roms) {
			connectors.addAll(rom.getAddresses());
			connectors.add(rom.getControl());
		}
		for (RegisterFile rf : registerFiles) {
			for (Port p : rf.getPorts()) {
				connectors.addAll(p.getInputs());
				connectors.addAll(p.getAddresses());
			}
			connectors.add(rf.getControl());
		}
		for (Alu alu : alus) {
			connectors.addAll(alu.getInputsA());
			connectors.addAll(alu.getInputsB());
			connectors.add(alu.getControl());
		}
		for (Multiplexer mux : multiplexers) {
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
		for (Register reg : registers) {
			connectors.add(reg.getOutput());
		}
		for (Rom rom : roms) {
			connectors.add(rom.getOutput());
		}
		for (RegisterFile rf : registerFiles) {
			for (Port p : rf.getPorts()) {
				connectors.add(p.getOutput());
			}
		}
		for (Alu alu : alus) {
			connectors.add(alu.getOutput1());
			connectors.add(alu.getOutput2());
			connectors.add(alu.getStatus());
		}
		for (Multiplexer mux : multiplexers) {
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

	public List<Register> getRegisters() {
		return registers;
	}

	public List<Rom> getRoms() {
		return roms;
	}

	public List<RegisterFile> getRegisterFiles() {
		return registerFiles;
	}

	public List<Alu> getAlus() {
		return alus;
	}

	public List<Multiplexer> getMultiplexers() {
		return multiplexers;
	}

	public int getWordSize() {
		return wordSize;
	}
	
}
