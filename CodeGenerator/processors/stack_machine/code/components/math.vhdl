-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY math IS
  GENERIC (
    g_word_size : integer := 7
  );
  PORT (
    p_input_A0 : in std_logic_vector(g_word_size DOWNTO 0);
    p_input_B0 : in std_logic_vector(g_word_size DOWNTO 0);
    p_ctrl : in std_logic_vector(1 DOWNTO 0);
    p_flag : out std_logic;
    p_output_1 : out std_logic_vector(g_word_size DOWNTO 0);
    p_output_2 : out std_logic_vector(g_word_size DOWNTO 0)
  );
END math;

ARCHITECTURE behavior OF math IS
  COMPONENT carry_select_adder
    GENERIC (
      g_block_size : integer := 7;
      g_blocks     : integer := 3
    );
    PORT (
      p_sgnd   : in  std_logic;
      p_sub    : in  std_logic;
      p_op_1   : in  std_logic_vector((g_block_size + 1) * (g_blocks + 1) - 1 DOWNTO 0);
      p_op_2   : in  std_logic_vector((g_block_size + 1) * (g_blocks + 1) - 1 DOWNTO 0);
      p_result : out std_logic_vector((g_block_size + 1) * (g_blocks + 1) - 1 DOWNTO 0);
      p_ovflw  : out std_logic
    );
  END COMPONENT;
  COMPONENT four_quadrant_multiplier 
    GENERIC (
      g_size : integer := 31
    );
    PORT (
      p_sgnd      : in  std_logic;
      p_op_1      : in  std_logic_vector(g_size DOWNTO 0);
      p_op_2      : in  std_logic_vector(g_size DOWNTO 0);
      p_add       : in  std_logic_vector(g_size DOWNTO 0);
      p_result_lo : out std_logic_vector(g_size DOWNTO 0);
      p_result_hi : out std_logic_vector(g_size DOWNTO 0)
    );
  END COMPONENT;
  SIGNAL s_csel : std_logic_vector(1 DOWNTO 0);
  SIGNAL s_input_A : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_input_B : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_sgnd : std_logic;
  SIGNAL s_adder_sub : std_logic;
  SIGNAL s_adder_ovflw : std_logic;
  SIGNAL s_adder_result : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_mul_result_hi : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_mul_result_lo : std_logic_vector(g_word_size DOWNTO 0);
BEGIN
  -- Control Vector Binding
  s_csel <= p_ctrl(1 DOWNTO 0);
  -- Input A Multiplexing
  s_input_A <= p_input_A0;
  -- Input B Multiplexing
  s_input_B <= p_input_B0;
  -- Instances of Sub-Components
  adder : carry_select_adder GENERIC MAP (g_block_size => 7, g_blocks => 0) PORT MAP (s_sgnd, s_adder_sub, s_input_A, s_input_B, s_adder_result, s_adder_ovflw);
  mul : four_quadrant_multiplier GENERIC MAP (g_size => g_word_size) PORT MAP (s_sgnd, s_input_A, s_input_B, (OTHERS => '0'), s_mul_result_lo, s_mul_result_hi);
  -- Output Multiplexers
  --
  -- Command	Index	Bitvector
  -- ADD_U 	0	00
  -- SUB_U 	1	01
  -- MUL_U 	2	10
  --
  -- Output 1 Multiplexing
  WITH s_csel SELECT p_output_1 <=
    s_adder_result WHEN "00",
    s_adder_result WHEN "01",
    s_mul_result_lo WHEN "10",
    "00000000" WHEN OTHERS;
  -- Output 2 Multiplexing
  WITH s_csel SELECT p_output_2 <=
    s_mul_result_hi WHEN "10",
    "00000000" WHEN OTHERS;
  -- Flag Multiplexing
  WITH s_csel SELECT p_flag <=
    s_adder_ovflw WHEN "00",
    s_adder_ovflw WHEN "01",
    '0' WHEN OTHERS;
  -- Command Tables
  WITH s_csel SELECT s_sgnd <=
    '0' WHEN "10",
    '0' WHEN OTHERS;
  -- Adder Control
  WITH s_csel SELECT s_adder_sub <=
    '0' WHEN "00",
    '1' WHEN "01",
    '0' WHEN OTHERS;

END behavior;