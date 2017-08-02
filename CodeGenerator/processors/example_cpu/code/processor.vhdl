-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY processor IS
  GENERIC (
    g_word_size : integer := 15
  );
  PORT (
    p_clk : in std_logic;
    p_reset : in std_logic;
    p_port0 : in std_logic_vector(15 DOWNTO 0);
    p_port1 : in std_logic_vector(15 DOWNTO 0);
    p_port2 : out std_logic_vector(15 DOWNTO 0);
    p_port3 : out std_logic_vector(15 DOWNTO 0)
  );
END processor;

ARCHITECTURE behavior OF processor IS
  COMPONENT pc_register
    GENERIC (
      g_word_size : integer := 15
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT mpc_register
    GENERIC (
      g_word_size : integer := 15
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT port2_register
    GENERIC (
      g_word_size : integer := 15
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT port3_register
    GENERIC (
      g_word_size : integer := 15
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT operand1_register
    GENERIC (
      g_word_size : integer := 15
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT operand2_register
    GENERIC (
      g_word_size : integer := 15
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT address_register
    GENERIC (
      g_word_size : integer := 15
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT program_rom
    GENERIC (
      g_address_size : integer := 15;
      g_word_size : integer := 15
    );
    PORT (
      p_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT microprogram_rom
    GENERIC (
      g_address_size : integer := 15;
      g_word_size : integer := 19
    );
    PORT (
      p_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT cmd_trans_rom
    GENERIC (
      g_address_size : integer := 15;
      g_word_size : integer := 15
    );
    PORT (
      p_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT cache_register_file
    GENERIC (
      g_address_size : integer := 15;
      g_word_size : integer := 15
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_port0_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_port0_input1 : in std_logic_vector(g_word_size DOWNTO 0);
      p_port0_input2 : in std_logic_vector(g_word_size DOWNTO 0);
      p_port0_input3 : in std_logic_vector(g_word_size DOWNTO 0);
      p_port0_input4 : in std_logic_vector(g_word_size DOWNTO 0);
      p_port0_input5 : in std_logic_vector(g_word_size DOWNTO 0);
      p_port0_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_port1_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_port1_output : out std_logic_vector(g_word_size DOWNTO 0);
      p_ctrl : in std_logic_vector(3 DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT pc_inc_alu
    GENERIC (
      g_word_size : integer := 15
    );
    PORT (
      p_input_A0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_flag : out std_logic;
      p_output_1 : out std_logic_vector(g_word_size DOWNTO 0);
      p_output_2 : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT mpc_inc_alu
    GENERIC (
      g_word_size : integer := 15
    );
    PORT (
      p_input_A0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_flag : out std_logic;
      p_output_1 : out std_logic_vector(g_word_size DOWNTO 0);
      p_output_2 : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT math_alu
    GENERIC (
      g_word_size : integer := 15
    );
    PORT (
      p_input_A0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B1 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B2 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B3 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B4 : in std_logic_vector(g_word_size DOWNTO 0);
      p_ctrl : in std_logic_vector(7 DOWNTO 0);
      p_flag : out std_logic;
      p_output_1 : out std_logic_vector(g_word_size DOWNTO 0);
      p_output_2 : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT m1_multiplexer
    GENERIC (
      g_word_size : integer := 15
    );
    PORT (
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input1 : in std_logic_vector(g_word_size DOWNTO 0);
      p_isel : in std_logic;
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT m2_multiplexer
    GENERIC (
      g_word_size : integer := 15
    );
    PORT (
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input1 : in std_logic_vector(g_word_size DOWNTO 0);
      p_isel : in std_logic;
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  SIGNAL s_pc_register_out : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_mpc_register_out : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_operand1_register_out : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_operand2_register_out : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_address_register_out : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_program_rom_out : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_cmd_trans_rom_out : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_cache_register_file_out : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_pc_inc_alu_out : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_mpc_inc_alu_out : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_math_alu_low : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_math_alu_high : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_m1_multiplexer_out : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_m2_multiplexer_out : std_logic_vector(15 DOWNTO 0);
  SIGNAL s_ctrl_vector : std_logic_vector(19 DOWNTO 0);
BEGIN
  pc_register_instance : pc_register
    GENERIC MAP (g_word_size => 15)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(0),
      s_m1_multiplexer_out,
      s_pc_register_out
    );
  mpc_register_instance : mpc_register
    GENERIC MAP (g_word_size => 15)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(1),
      s_m2_multiplexer_out,
      s_mpc_register_out
    );
  port2_register_instance : port2_register
    GENERIC MAP (g_word_size => 15)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(2),
      s_cache_register_file_out,
      p_port2
    );
  port3_register_instance : port3_register
    GENERIC MAP (g_word_size => 15)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(3),
      s_cache_register_file_out,
      p_port3
    );
  operand1_register_instance : operand1_register
    GENERIC MAP (g_word_size => 15)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(4),
      s_cache_register_file_out,
      s_operand1_register_out
    );
  operand2_register_instance : operand2_register
    GENERIC MAP (g_word_size => 15)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(5),
      s_cache_register_file_out,
      s_operand2_register_out
    );
  address_register_instance : address_register
    GENERIC MAP (g_word_size => 15)
    PORT MAP (
      p_clk,
      p_reset,
      s_ctrl_vector(6),
      s_program_rom_out,
      s_address_register_out
    );
  program_rom_instance : program_rom
    GENERIC MAP (g_address_size => 15, g_word_size => 15)
    PORT MAP (
      s_pc_register_out,
      s_program_rom_out
    );
  microprogram_rom_instance : microprogram_rom
    GENERIC MAP (g_address_size => 15, g_word_size => 19)
    PORT MAP (
      s_mpc_register_out,
      s_ctrl_vector
    );
  cmd_trans_rom_instance : cmd_trans_rom
    GENERIC MAP (g_address_size => 15, g_word_size => 15)
    PORT MAP (
      s_program_rom_out,
      s_cmd_trans_rom_out
    );
  cache_register_file_instance : cache_register_file
    GENERIC MAP (g_address_size => 15, g_word_size => 15)
    PORT MAP (
      p_clk,
      p_reset,
      p_port0,
      p_port1,
      "0000000000000000",
      s_program_rom_out,
      s_math_alu_low,
      s_math_alu_high,
      s_address_register_out,
      s_address_register_out,
      s_cache_register_file_out,
      s_ctrl_vector(10 DOWNTO 7)
    );  pc_inc_alu_instance : pc_inc_alu
    GENERIC MAP (g_word_size => 15)
    PORT MAP (
      s_pc_register_out,
      "0000000000000001",
      OPEN,
      s_pc_inc_alu_out,
      OPEN
    );
  mpc_inc_alu_instance : mpc_inc_alu
    GENERIC MAP (g_word_size => 15)
    PORT MAP (
      s_mpc_register_out,
      "0000000000000001",
      OPEN,
      s_mpc_inc_alu_out,
      OPEN
    );
  math_alu_instance : math_alu
    GENERIC MAP (g_word_size => 15)
    PORT MAP (
      s_operand1_register_out,
      s_operand2_register_out,
      "0000000000000000",
      "0000000000000001",
      s_math_alu_low,
      s_math_alu_high,
      s_ctrl_vector(18 DOWNTO 11),
      OPEN,
      s_math_alu_low,
      s_math_alu_high
    );
  m1_multiplexer_instance : m1_multiplexer
    GENERIC MAP (g_word_size => 15)
    PORT MAP (
      s_pc_inc_alu_out,
      s_pc_register_out,
      '0',
      s_m1_multiplexer_out
    );
  m2_multiplexer_instance : m2_multiplexer
    GENERIC MAP (g_word_size => 15)
    PORT MAP (
      s_mpc_inc_alu_out,
      s_cmd_trans_rom_out,
      s_ctrl_vector(19),
      s_m2_multiplexer_out
    );

END behavior;