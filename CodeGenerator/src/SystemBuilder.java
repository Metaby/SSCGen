import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Components.*;

public class SystemBuilder {
	
	private int wordSize;
	
	public List<BaseComponent> ReadSpecification(String SpecPath) {
		// TODO: XSD schreiben und mit JAXB einlesen
		List<BaseComponent> components = new LinkedList<BaseComponent>();
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		try {
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			Document spec = builder.parse(new File(SpecPath));
//			wordSize = Integer.parseInt(spec.getDocumentElement().getAttribute("WordSize"));
//			NodeList specChilds = spec.getDocumentElement().getChildNodes();
//			for (int i = 0; i < specChilds.getLength(); i++) {
//				Node n = specChilds.item(i);
//				if (n.getNodeType() == Node.ELEMENT_NODE) {
//					components.add(ReadComponent((Element)n));
//				}
//			}
//			return components;
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return components;
	}
	
	public Boolean VerifyConnections(List<BaseComponent> components) {
		Set<String> systemInputs = new HashSet<String>();
		Set<String> systemOutputs = new HashSet<String>();
		for (int i = 0; i < components.size(); i++) {
			BaseComponent bc = components.get(i);
			if (bc instanceof Memory) {
				Memory mem = (Memory)bc;
				for (int j = 0; j < mem.getPorts().size(); j++) {
					if (mem.getPorts().get(j).getInputs() != null) {
						systemInputs.addAll(mem.getPorts().get(j).getInputs());						
					}
					if (mem.getPorts().get(j).getAddresses() != null) {
						systemInputs.addAll(mem.getPorts().get(j).getAddresses());
					}
					if (mem.getPorts().get(j).getOutput() != null) {
						systemOutputs.add(mem.getId() + "." + mem.getPorts().get(j).getOutput());						
					}		
				}
			} else if (bc instanceof RegisterFile) {
				RegisterFile rf = (RegisterFile)bc;
				for (int j = 0; j < rf.getPorts().size(); j++) {
					if (rf.getPorts().get(j).getInputs() != null) {
						systemInputs.addAll(rf.getPorts().get(j).getInputs());						
					}
					if (rf.getPorts().get(j).getAddresses() != null) {
						systemInputs.addAll(rf.getPorts().get(j).getAddresses());
					}
					if (rf.getPorts().get(j).getOutput() != null) {
						systemOutputs.add(rf.getId() + "." + rf.getPorts().get(j).getOutput());						
					}		
				}
			} else if (bc instanceof Register) {
				Register reg = (Register)bc;
				systemInputs.addAll(reg.getInputs());
				systemOutputs.add(reg.getId() + "." + reg.getOutput());
			} else if (bc instanceof Stack) {
				Stack stk = (Stack)bc;
				systemInputs.addAll(stk.getInputs());
				systemOutputs.add(stk.getId() + "." + stk.getOutput());
			} else if (bc instanceof ALU) {
				ALU alu = (ALU)bc;
				systemInputs.addAll(alu.getInputsA());
				systemInputs.addAll(alu.getInputsB());
				systemOutputs.add(alu.getId() + "." + alu.getOutput());
			}
		}
		systemInputs.remove("");
		systemOutputs.remove("");
		for (int i = 0; i < systemInputs.size(); i++) {
			if (!systemInputs.toArray()[i].toString().startsWith("const")) {
				String con = (String)systemInputs.toArray()[i];
				if (con.contains("[")) {
					con = con.substring(0, con.indexOf('['));				
				}
				if (!systemOutputs.contains(con)) {
					System.out.println("Could not resolve input \"" + systemInputs.toArray()[i] + "\"");
					return false;
				}				
			}
		}
		return true;
	}
	
	private Map<String, String> connectionAliases;
	
	private String CreateNode(BaseComponent bc) {
		String node = "";
		if (bc instanceof Memory) {
			Memory mem = (Memory)bc;
			int f = 0;
			String mrecord = "{";
			for (int i = 0; i < mem.getPorts().size(); i++) {
				Port p = mem.getPorts().get(i);
				if (p.getType().equals("In")) {
					mrecord += "<p" + i + "in> IPort." + i + " In | <p" + i + "addr> IPort." + i + " Addr";
				} else if (p.getType().equals("Out")) {
					mrecord += "<p" + i + "out> OPort." + i + " Out | <p" + i + "addr> OPort." + i + " Addr";
					connectionAliases.put(mem.getId() + "." + p.getOutput(), mem.getId() + ":p" + i + "out");
				} else if (p.getType().equals("InOut")) {
					mrecord += "<p" + i + "in> IOPort." + i + " In | <p" + i + "out> IOPort." + i + " Out | <p" + i + "addr> IOPort." + i + " Addr";
					connectionAliases.put(mem.getId() + "." + p.getOutput(), mem.getId() + ":p" + i + "out");
				}
				if (i < mem.getPorts().size() - 1) {
					mrecord += "|";
				}
			}
			mrecord += "}";
			node += "\t" + mem.getId() + " [label=\"" + mrecord + " | <f" + f + "> " + mem.getId() + " (Memory)\"];";				
		} else if (bc instanceof RegisterFile) {
			RegisterFile rf = (RegisterFile)bc;
			int f = 0;
			String mrecord = "{";
			for (int i = 0; i < rf.getPorts().size(); i++) {
				Port p = rf.getPorts().get(i);
				if (p.getType().equals("In")) {
					mrecord += "<p" + i + "in> IPort." + i + " In | <p" + i + "addr> IPort." + i + " Addr";
				} else if (p.getType().equals("Out")) {
					mrecord += "<p" + i + "out> OPort." + i + " Out | <p" + i + "addr> OPort." + i + " Addr";
					connectionAliases.put(rf.getId() + "." + p.getOutput(), rf.getId() + ":p" + i + "out");		
				} else if (p.getType().equals("InOut")) {
					mrecord += "<p" + i + "in> IOPort." + i + " In | <p" + i + "out> IOPort." + i + " Out | <p" + i + "addr> IOPort." + i + " Addr";
					connectionAliases.put(rf.getId() + "." + p.getOutput(), rf.getId() + ":p" + i + "out");
				}
				if (i < rf.getPorts().size() - 1) {
					mrecord += "|";
				}
			}
			mrecord += "}";
			node += "\t" + rf.getId() + " [label=\"" + mrecord + " | <f" + f + "> " + rf.getId() + " (RegisterFile)\"];";			
		} else if (bc instanceof Register) {
			Register reg = (Register)bc;
			node += "\t" + reg.getId() + " [label=\"<in> in | <name> " + reg.getId() + " (Register)" + " | <out> " + reg.getOutput().trim() + "\"];";
			connectionAliases.put(reg.getId() + "." + reg.getOutput(), reg.getId() + ":out");
		} else if (bc instanceof Stack) {
			Stack stk = (Stack)bc;
			node += "\t" + stk.getId() + " [label=\"<in> in | <name> " + stk.getId() + " (Stack)" + " | <out> " + stk.getOutput().trim() + "\"];";
			connectionAliases.put(stk.getId() + "." + stk.getOutput(), stk.getId() + ":out");
		} else if (bc instanceof ALU) {
			ALU alu = (ALU)bc;
			node += "\t" + alu.getId() + " [label=\"{<inA> in A | <inB> in B} | <name> " + alu.getId() + " (ALU)" + " | <out> " + alu.getOutput().trim() + "\"];";
			connectionAliases.put(alu.getId() + "." + alu.getOutput(), alu.getId() + ":out");		
		}
		return node + System.lineSeparator();
	}
	
	private String ParseInput(String input) {
		if (input.contains("[")) {
			input = input.substring(0, input.indexOf('['));
		}
		if (input.startsWith("const.")) {
			input = input.substring(input.indexOf('.') + 1);
			return input;
		}
		return connectionAliases.get(input).trim();
	}
	
	private String CreateEdges(BaseComponent bc) {
		String edges = "";
		int mux = 0;
		if (bc instanceof Memory) {
			Memory mem = (Memory)bc;
			for (int i = 0; i < mem.getPorts().size(); i++) {
				Port p = mem.getPorts().get(i);
				if (p.getType().equals("In")) {
					for (int j = 0; j < p.getInputs().size(); j++) {
						String inp = p.getInputs().get(j);
						if (inp.length() > 0) {
							edges += "\t" + ParseInput(inp) + "\t->\t" + mem.getId() + ":p" + i + "in;" + System.lineSeparator();						
						}						
					}
					for (int j = 0; j < p.getAddresses().size(); j++) {
						String inp = p.getAddresses().get(j);
						if (inp.length() > 0) {
							edges += "\t" + ParseInput(inp) + "\t->\t" + mem.getId() + ":p" + i + "addr [style=dotted];" + System.lineSeparator();							
						}						
					}
				} else if (p.getType().equals("Out")) {
					for (int j = 0; j < p.getAddresses().size(); j++) {
						String addr = p.getAddresses().get(j);
						if (addr.length() > 0) {
							edges += "\t" + ParseInput(addr) + "\t->\t" + mem.getId() + ":p" + i + "addr [style=dotted];" + System.lineSeparator();							
						}						
					}					
				} else if (p.getType().equals("InOut")) {
					for (int j = 0; j < p.getInputs().size(); j++) {
						String inp = p.getInputs().get(j);
						if (inp.length() > 0) {
							edges += "\t" + ParseInput(inp) + "\t->\t" + mem.getId() + ":p" + i + "in;" + System.lineSeparator();							
						}											
					}	
					for (int j = 0; j < p.getAddresses().size(); j++) {
						String addr = p.getAddresses().get(j);
						if (addr.length() > 0) {
							edges += "\t" + ParseInput(addr) + "\t->\t" + mem.getId() + ":p" + i + "addr [style=dotted];" + System.lineSeparator();							
						}						
					}				
				}
			}
		} else if (bc instanceof RegisterFile) {
			RegisterFile rf = (RegisterFile)bc;
			for (int i = 0; i < rf.getPorts().size(); i++) {
				Port p = rf.getPorts().get(i);
				if (p.getType().equals("In")) {
					String sMux = "mux" + (mux++);
					edges += "\t" + sMux + " [label=Mux]"; 
					edges += "\t" + sMux + "\t->\t" + rf.getId() + ":p" + i + "in;" + System.lineSeparator();
					for (int j = 0; j < p.getInputs().size(); j++) {
						String inp = p.getInputs().get(j);
						if (inp.length() > 0) {	
							edges += "\t" + ParseInput(inp) + "\t->\t" + sMux + ";" + System.lineSeparator();	
//							edges += "\t" + ParseInput(inp) + "\t->\t" + rf.getId() + ":p" + i + "in;" + System.lineSeparator();							
						}						
					}
					for (int j = 0; j < p.getAddresses().size(); j++) {
						String addr = p.getAddresses().get(j);
						if (addr.length() > 0) {
							if (addr.contains("[")) {
								addr = addr.substring(0, addr.indexOf('['));								
							}
							edges += "\t" + ParseInput(addr) + "\t->\t" + rf.getId() + ":p" + i + "addr [style=dotted];" + System.lineSeparator();							
						}						
					}
				} else if (p.getType().equals("Out")) {
					for (int j = 0; j < p.getAddresses().size(); j++) {
						String addr = p.getAddresses().get(j);
						if (addr.length() > 0) {
							edges += "\t" + ParseInput(addr) + "\t->\t" + rf.getId() + ":p" + i + "addr [style=dotted];" + System.lineSeparator();							
						}						
					}					
				} else if (p.getType().equals("InOut")) {
					for (int j = 0; j < p.getInputs().size(); j++) {
						String inp = p.getInputs().get(j);
						if (inp.length() > 0) {
							edges += "\t" + ParseInput(inp) + "\t->\t" + rf.getId() + ":p" + i + "in;" + System.lineSeparator();							
						}											
					}	
					for (int j = 0; j < p.getAddresses().size(); j++) {
						String addr = p.getAddresses().get(j);
						if (addr.length() > 0) {
							edges += "\t" + ParseInput(addr) + "\t->\t" + rf.getId() + ":p" + i + "addr [style=dotted];" + System.lineSeparator();							
						}						
					}				
				}
			}			
		} else if (bc instanceof Register) {
			Register reg = (Register)bc;
			for (int i = 0; i < reg.getInputs().size(); i++) {
				edges += "\t" + ParseInput(reg.getInputs().get(i)) + "\t->\t" + reg.getId() + ":in;" + System.lineSeparator();
			}
		} else if (bc instanceof Stack) {
			Stack stk = (Stack)bc;
			for (int i = 0; i < stk.getInputs().size(); i++) {
				edges += "\t" + ParseInput(stk.getInputs().get(i)) + "\t->\t" + stk.getId() + ":in;" + System.lineSeparator();
			}			
		} else if (bc instanceof ALU) {
			ALU alu = (ALU)bc;
			for (int i = 0; i < alu.getInputsA().size(); i++) {
				String con = ParseInput(alu.getInputsA().get(i));
				edges += "\t" + con + "\t->\t" + alu.getId() + ":inA;" + System.lineSeparator();				
			}
			for (int i = 0; i < alu.getInputsB().size(); i++) {
				String con = ParseInput(alu.getInputsB().get(i));
				edges += "\t" + con + "\t->\t" + alu.getId() + ":inB;" + System.lineSeparator();				
			}
		}
		return edges + System.lineSeparator();
	}
	
	public void GenerateSystemGraph(String fileName, List<BaseComponent> components) {
		// TODO: Graph verbessern mit Multiplexern, evtl sogar mux als comp-input benennen und nicht mehr records für den graph verwenden
		connectionAliases = new HashMap<String, String>();
		String dotGraph = "digraph system {" + System.lineSeparator();
		dotGraph += "\t// Settings;" + System.lineSeparator();
		dotGraph += "\tnode[shape=record];" + System.lineSeparator();	
		dotGraph += "\tedge[headclip=true, tailclip=true];" + System.lineSeparator();	
		dotGraph += "\tgraph [splines=ortho, nodesep=1];" + System.lineSeparator();
		dotGraph += "\t// Nodes;" + System.lineSeparator();		
		for (int i = 0; i < components.size(); i++) {
			dotGraph += CreateNode(components.get(i));
		}			
		dotGraph += "\t// Edges;" + System.lineSeparator();
		for (int i = 0; i < components.size(); i++) {
			dotGraph += CreateEdges(components.get(i));
		}		
		dotGraph += "}";
		dotGraph = dotGraph.replaceAll("(?m)^[ \t]*\r?\n", "");
		File f = new File(fileName);
		PrintWriter writer;
		try {
			writer = new PrintWriter(f, "UTF-8");
			writer.println(dotGraph);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private BaseComponent ReadComponent(Element n) {
		if (n.getNodeName() == "RegisterFile") {
			return HandleRegisterFileNode(n);
		} else if (n.getNodeName() == "Memory") {
			return HandleMemoryNode(n);
		} else if (n.getNodeName() == "Register") {
			return HandleRegisterNode(n);
		} else if (n.getNodeName() == "Stack") {
			return HandleStackNode(n);
		} else if (n.getNodeName() == "ALU") {
			return HandleAluNode(n);
		}
		return null;
	}
	
	private List<Port> ReadPorts(Element n) {
		NodeList nl = n.getElementsByTagName("Port");
		List<Port> ports = new LinkedList<Port>();
		for (int i = 0; i < nl.getLength(); i++) {
			Port p = new Port();
			List<String> addressing = new LinkedList<String>();
			List<String> inputs = new LinkedList<String>();
			p.setType(((Element)nl.item(i)).getElementsByTagName("Type").item(0).getTextContent().trim());
			NodeList addrNl = ((Element)nl.item(i)).getElementsByTagName("Addressing").item(0).getChildNodes();
			for (int j = 0; j < addrNl.getLength(); j++) {
				addressing.add(addrNl.item(j).getTextContent().trim());
			}
			p.setAddresses(addressing);
			if (((Element)nl.item(i)).getElementsByTagName("Inputs").getLength() > 0) {
				NodeList inputsNl = ((Element)nl.item(i)).getElementsByTagName("Inputs").item(0).getChildNodes();
				for (int j = 0; j < inputsNl.getLength(); j++) {
					inputs.add(inputsNl.item(j).getTextContent().trim());			
					p.setInputs(inputs);
				}
			}
			if (((Element)nl.item(i)).getElementsByTagName("Output").getLength() > 0) {
				p.setOutput(((Element)nl.item(i)).getElementsByTagName("Output").item(0).getTextContent().trim());				
			}
			ports.add(p);
		}
		return ports;
	}
	
	private BaseComponent HandleRegisterFileNode(Element n) {
		RegisterFile rf = new RegisterFile(n.getElementsByTagName("ID").item(0).getTextContent().trim());
		rf.setAddressSize(Integer.parseInt(n.getElementsByTagName("AddressSize").item(0).getTextContent().trim()));
		rf.setPorts(ReadPorts(n));
		return rf;
	}
	
	private BaseComponent HandleMemoryNode(Element n) {
		Memory mem = new Memory(n.getElementsByTagName("ID").item(0).getTextContent().trim());
		mem.setAddressSize(Integer.parseInt(n.getElementsByTagName("AddressSize").item(0).getTextContent().trim()));
		mem.setPorts(ReadPorts(n));
		return mem;		
	}
	
	private BaseComponent HandleRegisterNode(Element n) {
		Register reg = new Register(n.getElementsByTagName("ID").item(0).getTextContent().trim());
		reg.setOutput(n.getElementsByTagName("Output").item(0).getTextContent().trim());
		reg.setGpio(Boolean.parseBoolean(n.getElementsByTagName("GPIO").item(0).getTextContent().trim()));
		NodeList nl = ((Element)n.getElementsByTagName("Inputs").item(0)).getElementsByTagName("Input");
		List<String> inputs = new LinkedList<String>();
		for (int i = 0; i < nl.getLength(); i++) {
			inputs.add(nl.item(i).getTextContent().trim());
		}
		reg.setInputs(inputs);
		return reg;
	}
	
	private BaseComponent HandleStackNode(Element n) {
		Stack stk = new Stack(n.getElementsByTagName("ID").item(0).getTextContent().trim());
		stk.setOutput(n.getElementsByTagName("Output").item(0).getTextContent().trim());
		stk.setSize(Integer.parseInt(n.getElementsByTagName("Size").item(0).getTextContent().trim()));
		NodeList nl = ((Element)n.getElementsByTagName("Inputs").item(0)).getElementsByTagName("Input");
		List<String> inputs = new LinkedList<String>();
		for (int i = 0; i < nl.getLength(); i++) {
			inputs.add(nl.item(i).getTextContent().trim());
		}
		stk.setInputs(inputs);
		return stk;
	}
	
	private BaseComponent HandleAluNode(Element n) {
		ALU alu = new ALU(n.getElementsByTagName("ID").item(0).getTextContent().trim());
		alu.setOutput(n.getElementsByTagName("Output").item(0).getTextContent().trim());
		NodeList nl = ((Element)n.getElementsByTagName("Inputs").item(0)).getElementsByTagName("Input");
		List<String> inputsA = new LinkedList<String>();
		List<String> inputsB = new LinkedList<String>();
		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i).getAttributes().item(0).getTextContent().trim().contains("A")) {
				inputsA.add(nl.item(i).getTextContent().trim());				
			}
			if (nl.item(i).getAttributes().item(0).getTextContent().trim().contains("B")) {
				inputsB.add(nl.item(i).getTextContent().trim());
			}
		}
		alu.setInputsA(inputsA);
		alu.setInputsB(inputsB);
		nl = ((Element)n.getElementsByTagName("Operations").item(0)).getElementsByTagName("Operation");
		List<String> operations = new LinkedList<String>();
		for (int i = 0; i < nl.getLength(); i++) {
			operations.add(nl.item(i).getTextContent().trim());
		}
		alu.setOperations(operations);
		nl = ((Element)n.getElementsByTagName("Conditionals").item(0)).getElementsByTagName("Condition");
		List<String> conditions = new LinkedList<String>();
		for (int i = 0; i < nl.getLength(); i++) {
			conditions.add(nl.item(i).getTextContent().trim());
		}
		alu.setConditions(conditions);
		return alu;
	}

	public int getWordSize() {
		return wordSize;
	}
}
