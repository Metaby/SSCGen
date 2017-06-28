-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY processor IS
  GENERIC (
    g_word_size : integer := 7
  );
  PORT (
    p_clk : in std_logic;
    p_reset : in std_logic;
    p_output : out std_logic_vector(7 DOWNTO 0);
    p_hex1 : out std_logic_vector(6 DOWNTO 0);
    p_hex2 : out std_logic_vector(6 DOWNTO 0)
  );
END processor;

ARCHITECTURE behavior OF processor IS
  COMPONENT pcReg
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in  std_logic_vector(1 DOWNTO 0);
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input1 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT mpcReg
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in  std_logic_vector(1 DOWNTO 0);
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input1 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT op1_register
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT op2_register
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT stackPtr
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in  std_logic_vector(1 DOWNTO 0);
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input1 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT output
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT progMem
    GENERIC (
      g_address_size : integer := 7;
      g_word_size : integer := 7
    );
    PORT (
      p_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT mProgMem
    GENERIC (
      g_address_size : integer := 7;
      g_word_size : integer := 15
    );
    PORT (
      p_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT stack
    GENERIC (
      g_address_size : integer := 7;
      g_word_size : integer := 7
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_port0_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_port0_input1 : in std_logic_vector(g_word_size DOWNTO 0);
      p_port0_input2 : in std_logic_vector(g_word_size DOWNTO 0);
      p_port0_input3 : in std_logic_vector(g_word_size DOWNTO 0);
      p_port0_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_port1_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_port1_output : out std_logic_vector(g_word_size DOWNTO 0);
      p_ctrl : in std_logic_vector(2 DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT pcInc
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_input_A0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_flag : out std_logic;
      p_output_1 : out std_logic_vector(g_word_size DOWNTO 0);
      p_output_2 : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT mpcInc
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_input_A0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_flag : out std_logic;
      p_output_1 : out std_logic_vector(g_word_size DOWNTO 0);
      p_output_2 : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT math
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_input_A0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_ctrl : in std_logic;
      p_flag : out std_logic;
      p_output_1 : out std_logic_vector(g_word_size DOWNTO 0);
      p_output_2 : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT stackAlu
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_input_A0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_ctrl : in std_logic;
      p_flag : out std_logic;
      p_output_1 : out std_logic_vector(g_word_size DOWNTO 0);
      p_output_2 : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT hex_decoder
    PORT (
      p_clk	 : IN std_logic;
      p_bits : IN std_logic_vector(3 DOWNTO 0);
      p_wr   : IN std_logic;
      p_hex  : OUT std_logic_vector(6 DOWNTO 0)
    );
  END COMPONENT;

  SIGNAL s_pcReg_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_mpcReg_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_op1_register_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_op2_register_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_stackPtr_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_progMem_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_stack_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_pcInc_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_mpcInc_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_math_low : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_math_high : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_math_status : std_logic;
  SIGNAL s_stackAlu_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_ctrl_vector : std_logic_vector(15 DOWNTO 0);
BEGIN
  pcReg_instance : pcReg
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(1 DOWNTO 0),
      s_pcReg_out,
      s_pcInc_out,
      s_pcReg_out
    );
  mpcReg_instance : mpcReg
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(3 DOWNTO 2),
      s_mpcInc_out,
      s_progMem_out,
      s_mpcReg_out
    );
  op1_register_instance : op1_register
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(4),
      s_stack_out,
      s_op1_register_out
    );
  op2_register_instance : op2_register
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(5),
      s_stack_out,
      s_op2_register_out
    );
  stackPtr_instance : stackPtr
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(7 DOWNTO 6),
      s_stackPtr_out,
      s_stackAlu_out,
      s_stackPtr_out
    );
  output_instance : output
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(8),
      s_stack_out,
      p_output
    );
  progMem_instance : progMem
    GENERIC MAP (g_address_size => 7, g_word_size => 7)
    PORT MAP (
      s_pcReg_out,
      s_progMem_out
    );
  mProgMem_instance : mProgMem
    GENERIC MAP (g_address_size => 7, g_word_size => 15)
    PORT MAP (
      s_mpcReg_out,
      s_ctrl_vector
    );
  stack_instance : stack
    GENERIC MAP (g_address_size => 7, g_word_size => 7)
    PORT MAP (
      p_clk,
      p_reset,
      s_progMem_out,
      s_math_low,
      s_math_high,
      "00000000",
      s_stackPtr_out,
      s_stackPtr_out,
      s_stack_out,
      s_ctrl_vector(11 DOWNTO 9)
    );  pcInc_instance : pcInc
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      s_pcReg_out,
      "00000001",
      OPEN,
      s_pcInc_out,
      OPEN
    );
  mpcInc_instance : mpcInc
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      s_mpcReg_out,
      "00000001",
      OPEN,
      s_mpcInc_out,
      OPEN
    );
  math_instance : math
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      s_op1_register_out,
      s_op2_register_out,
      s_ctrl_vector(12),
      s_math_status,
      s_math_low,
      s_math_high
    );
  stackAlu_instance : stackAlu
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      s_stackPtr_out,
      "00000001",
      s_ctrl_vector(13),
      OPEN,
      s_stackAlu_out,
      OPEN
    );
  hexdigit_1_instance : hex_decoder
    PORT MAP(
      p_clk,
      s_stack_out(3 DOWNTO 0),
      s_ctrl_vector(14),
      p_hex1
    );
  hexdigit_2_instance : hex_decoder
    PORT MAP(
      p_clk,
      s_stack_out(7 DOWNTO 4),
      s_ctrl_vector(15),
      p_hex2
    );

END behavior;