package wrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Architecture {
	
	private List<Register> registers;
	private List<Stack> stacks;
	private List<Rom> roms;
	private List<RegisterFile> registerFiles;
	private List<Memory> memories;
	private List<Alu> alus;
	private List<JumpLogic> jumpLogics;
	
	public Architecture(jaxb.Architecture arch) {
		registers = new ArrayList<Register>();
		stacks = new ArrayList<Stack>();
		roms = new ArrayList<Rom>();
		registerFiles = new ArrayList<RegisterFile>();
		memories = new ArrayList<Memory>();
		alus = new ArrayList<Alu>();
		jumpLogics = new ArrayList<JumpLogic>();
		for (jaxb.Register reg : arch.getRegister()) {
			registers.add(new Register(reg));
		}
		for (jaxb.Stack stk : arch.getStack()) {
			stacks.add(new Stack(stk));
		}
		for (jaxb.Rom rom : arch.getRom()) {
			roms.add(new Rom(rom));
		}
		for (jaxb.RegisterFile rf : arch.getRegisterFile()) {
			registerFiles.add(new RegisterFile(rf));
		}
		for (jaxb.Memory mem : arch.getMemory()) {
			memories.add(new Memory(mem));
		}
		for (jaxb.Alu alu : arch.getAlu()) {
			alus.add(new Alu(alu));
		}
		for (jaxb.JumpLogic jl : arch.getJumpLogic()) {
			jumpLogics.add(new JumpLogic(jl));
		}
	}
	
	public List<Connector> getInputConnectors() {
		Set<Connector> connectors = new HashSet<Connector>();
		for (Register reg : registers) {
			connectors.addAll(reg.getInputs());
		}
		for (Stack stk : stacks) {
			connectors.addAll(stk.getInputs());
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
		for (Memory mem : memories) {
			for (Port p : mem.getPorts()) {
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
		return new ArrayList<Connector>(connectors);
	}
	
	public List<Connector> getOutputConnectors() {
		Set<Connector> connectors = new HashSet<Connector>();
		for (Register reg : registers) {
			connectors.add(reg.getOutput());
		}
		for (Stack stk : stacks) {
			connectors.add(stk.getOutput());
		}
		for (Rom rom : roms) {
			connectors.add(rom.getOutput());
		}
		for (RegisterFile rf : registerFiles) {
			for (Port p : rf.getPorts()) {
				connectors.add(p.getOutput());
			}
		}
		for (Memory mem : memories) {
			for (Port p : mem.getPorts()) {
				connectors.add(p.getOutput());
			}			
		}
		for (Alu alu : alus) {
			connectors.add(alu.getOutput());
			connectors.add(alu.getStatus());
		}
		for (JumpLogic jl : jumpLogics) {
			connectors.add(jl.getOutput());
		}
		return new ArrayList<Connector>(connectors);
	}

	public List<Register> getRegisters() {
		return registers;
	}

	public List<Stack> getStacks() {
		return stacks;
	}

	public List<Rom> getRoms() {
		return roms;
	}

	public List<RegisterFile> getRegisterFiles() {
		return registerFiles;
	}

	public List<Memory> getMemories() {
		return memories;
	}

	public List<Alu> getAlus() {
		return alus;
	}

	public List<JumpLogic> getJumpLogics() {
		return jumpLogics;
	}
	
}
