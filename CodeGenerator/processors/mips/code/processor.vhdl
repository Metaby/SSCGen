-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY processor IS
  GENERIC (
    g_word_size : integer := 31
  );
  PORT (
    p_clk : in std_logic;
    p_reset : in std_logic;
    p_PortA : out std_logic_vector(31 DOWNTO 0)
  );
END processor;

ARCHITECTURE behavior OF processor IS
  COMPONENT PortA_Register
    GENERIC (
      g_word_size : integer := 31
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_write : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input1 : in std_logic_vector(g_word_size DOWNTO 0);
      p_isel : in std_logic;
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT ProgramPointer_Register
    GENERIC (
      g_word_size : integer := 31
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_write : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT InstructionWord_Register
    GENERIC (
      g_word_size : integer := 31
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_write : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT MicroProgrammPointer
    GENERIC (
      g_word_size : integer := 31
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_write : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input1 : in std_logic_vector(g_word_size DOWNTO 0);
      p_isel : in std_logic;
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT JumpBuffer_Register
    GENERIC (
      g_word_size : integer := 31
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_write : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT Microcode_Rom
    GENERIC (
      g_addressSize : integer := 31;
      g_wordSize : integer := 30
    );
    PORT (
      p_address0 : in std_logic_vector(g_addressSize DOWNTO 0);
      p_word : out std_logic_vector(g_wordSize DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT ProgramRom
    GENERIC (
      g_addressSize : integer := 31;
      g_wordSize : integer := 31
    );
    PORT (
      p_address0 : in std_logic_vector(g_addressSize DOWNTO 0);
      p_word : out std_logic_vector(g_wordSize DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT ProgramPath_JumpLogic
    GENERIC (
      g_wordSize : integer := 31
    );
    PORT (
      p_pathA0 : in std_logic_vector(g_wordSize DOWNTO 0);
      p_pathB0 : in std_logic_vector(g_wordSize DOWNTO 0);
      p_ctrl : in std_logic_vector(3 DOWNTO 0);
      p_ctrlSelect : in std_logic_vector(1 DOWNTO 0;
      p_pathOut : out std_logic_vector(g_wordSize DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT mpPointerIncrementer_Alu
    GENERIC (
      g_word_size : integer := 31
    );
    PORT (
      p_input_A0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_flag : out std_logic;
      p_output_1 : out std_logic_vector(g_word_size DOWNTO 0);
      p_output_2 : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT Calculator_Alu
    GENERIC (
      g_word_size : integer := 31
    );
    PORT (
      p_input_A0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_A1 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_A2 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B1 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B2 : in std_logic_vector(g_word_size DOWNTO 0);
      p_isel_A : in std_logic_vector(1 DOWNTO 0);
      p_isel_B : in std_logic_vector(1 DOWNTO 0);
      p_csel : in std_logic_vector(3 DOWNTO 0);
      p_flag : out std_logic;
      p_output_1 : out std_logic_vector(g_word_size DOWNTO 0);
      p_output_2 : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT Cache_RegisterFile
    GENERIC (
      g_wordSize : integer := 31;
      g_addressSize : integer := 4
    );
    PORT (
      p_port0_address0 : in std_logic_vector(g_addressSize DOWNTO 0);
      p_port0_output : out std_logic_vector(g_wordSize DOWNTO 0);
      p_port1_address0 : in std_logic_vector(g_addressSize DOWNTO 0);
      p_port1_output : out std_logic_vector(g_wordSize DOWNTO 0);
      p_port2_input0 : in std_logic_vector(g_wordSize DOWNTO 0);
      p_port2_input1 : in std_logic_vector(g_wordSize DOWNTO 0);
      p_port2_input2 : in std_logic_vector(g_wordSize DOWNTO 0);
      p_port2_input3 : in std_logic_vector(g_wordSize DOWNTO 0);
      p_port2_input4 : in std_logic_vector(g_wordSize DOWNTO 0);
      p_port2_address0 : in std_logic_vector(g_addressSize DOWNTO 0);
      p_port2_inputSelect : in std_logic_vector(2 DOWNTO 0);
      p_port2_write : in std_logic;
      p_clk : in std_logic
    );
  END COMPONENT;
  SIGNAL s_ProgramPointer_Register_pc : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_InstructionWord_Register_inst : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_MicroProgrammPointer_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_JumpBuffer_Register_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_ProgramRom_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_Cache_RegisterFile_out1 : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_Cache_RegisterFile_out2 : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_mpPointerIncrementer_Alu_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_Calculator_Alu_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_Calculator_Alu_flag : std_logic;
  SIGNAL s_ProgramPath_JumpLogic_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_ctrl_vector : std_logic_vector(14 DOWNTO 0);
BEGIN
  PortA_Register_instance :  GENERIC MAP(g_word_size => 31)  PORT MAP(p_clk, p_rst, s_ctrl_vector(0), s_Cache_RegisterFile_out1, s_Cache_RegisterFile_out2, s_ctrl_vector(1), p_PortA);
  ProgramPointer_Register_instance :  GENERIC MAP(g_word_size => 31)  PORT MAP(p_clk, p_rst, s_ctrl_vector(2), s_Calculator_Alu_out, s_ProgramPointer_Register_pc);
  InstructionWord_Register_instance :  GENERIC MAP(g_word_size => 31)  PORT MAP(p_clk, p_rst, s_ctrl_vector(3), s_ProgramRom_out, s_InstructionWord_Register_inst);
  MicroProgrammPointer_instance :  GENERIC MAP(g_word_size => 31)  PORT MAP(p_clk, p_rst, s_ctrl_vector(4), s_mpPointerIncrementer_Alu_out, s_Calculator_Alu_out, s_ctrl_vector(5), s_MicroProgrammPointer_out);
  JumpBuffer_Register_instance :  GENERIC MAP(g_word_size => 31)  PORT MAP(p_clk, p_rst, s_ctrl_vector(6), s_Calculator_Alu_out, s_JumpBuffer_Register_out);
  mpPointerIncrementer_Alu_instance :  GENERIC MAP(g_word_size => 31)  PORT MAP(s_MicroProgrammPointer_out, "00000000000000000000000000000001", s_mpPointerIncrementer_Alu_out, OPEN);
  Calculator_Alu_instance :  GENERIC MAP(g_word_size => 31)  PORT MAP(s_Cache_RegisterFile_out1, s_Calculator_Alu_out, s_ProgramPointer_Register_pc, s_Cache_RegisterFile_out2, "00000000000000000000000000000000", "00000000000000000000000000000001", s_ctrl_vector(8 DOWNTO 7), s_ctrl_vector(10 DOWNTO 9), s_ctrl_vector(14 DOWNTO 11), s_Calculator_Alu_flag, s_Calculator_Alu_out, OPEN);

END behavior;