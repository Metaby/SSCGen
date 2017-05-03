package wrapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Architecture {
	
	private List<Register> registers;
	private List<Rom> roms;
	private List<RegisterFile> registerFiles;
	private List<Alu> alus;
	private List<JumpLogic> jumpLogics;
	private int wordSize;
	
	public Architecture(jaxb.Architecture arch) {
		registers = new ArrayList<Register>();
		roms = new ArrayList<Rom>();
		registerFiles = new ArrayList<RegisterFile>();
		alus = new ArrayList<Alu>();
		jumpLogics = new ArrayList<JumpLogic>();
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
		for (jaxb.JumpLogic jl : arch.getJumpLogic()) {
			jumpLogics.add(new JumpLogic(jl));
		}
	}
	
	public List<Connector> getInputConnectors(Boolean complete) {
		List<Connector> connectors = new ArrayList<Connector>();
		for (Register reg : registers) {
			connectors.addAll(reg.getInputs());
		}
		for (Rom rom : roms) {
			connectors.addAll(rom.getAddresses());
		}
		for (RegisterFile rf : registerFiles) {
			for (Port p : rf.getPorts()) {
				connectors.addAll(p.getInputs());
				connectors.addAll(p.getAddresses());
			}
		}
		for (Alu alu : alus) {
			connectors.addAll(alu.getInputsA());
			connectors.addAll(alu.getInputsB());
		}
		for (JumpLogic jl : jumpLogics) {
			connectors.addAll(jl.getProgramTargetsA());
			connectors.addAll(jl.getProgramTargetsB());
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
		for (JumpLogic jl : jumpLogics) {
			connectors.add(jl.getOutput());
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

	public List<JumpLogic> getJumpLogics() {
		return jumpLogics;
	}

	public int getWordSize() {
		return wordSize;
	}
	
}
