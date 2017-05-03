-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY Calculator_Alu IS
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
END Calculator_Alu;

ARCHITECTURE behavior OF Calculator_Alu IS
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
  COMPONENT bit_manipulator
    GENERIC (
      g_size : integer := 31
    );
    PORT (
      p_op_1   : in  std_logic_vector(g_size DOWNTO 0);
      p_op_2   : in  std_logic_vector(g_size DOWNTO 0);
      p_cmd    : in  std_logic_vector(1 DOWNTO 0);
      p_result : out std_logic_vector(g_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT tree_comparator
    GENERIC (
      g_size : integer := 31
    );
    PORT (
      p_op_1 : in  std_logic_vector(g_size DOWNTO 0);
      p_op_2 : in  std_logic_vector(g_size DOWNTO 0);
      p_sgnd : in  std_logic;
      p_g    : out std_logic;
      p_l    : out std_logic
    );
  END COMPONENT;
  COMPONENT divider 
    GENERIC (
      g_size : integer := 31
    );
    PORT (
      p_sgnd     : in  std_logic;
      p_dividend : in  std_logic_vector(g_size DOWNTO 0);
      p_divisor  : in  std_logic_vector(g_size DOWNTO 0);
      p_remain   : out std_logic_vector(g_size DOWNTO 0);
      p_result   : out std_logic_vector(g_size DOWNTO 0)
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
  COMPONENT barrel_shifter 
    GENERIC (
      g_size : integer := 31
    );
    PORT (
      p_arith  : in  std_logic;
      p_rotate : in  std_logic;
      p_cmd    : in  std_logic_vector(1 DOWNTO 0);
      p_op_1   : in  std_logic_vector(g_size DOWNTO 0);
      p_op_2   : in  std_logic_vector(g_size DOWNTO 0);
      p_result : out std_logic_vector(g_size DOWNTO 0)
    );
  END COMPONENT;
  SIGNAL s_input_A : std_logic_vector(g_word_size DOWNTO 0;
  SIGNAL s_input_B : std_logic_vector(g_word_size DOWNTO 0;
  SIGNAL s_sgnd : std_logic;
  SIGNAL s_adder_sub : std_logic;
  SIGNAL s_adder_ovflw : std_logic;
  SIGNAL s_adder_result : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_logic_cmd : std_logic_vector(1 DOWNTO 0;
  SIGNAL s_logic_result : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_div_remain : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_div_result : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_mul_result_hi : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_mul_result_lo : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_comp_cmd : std_logic_vector(2 DOWNTO 0);
  SIGNAL s_comp_result : std_logic;
  SIGNAL s_shft_ari : std_logic;
  SIGNAL s_shft_rot : std_logic;
  SIGNAL s_shft_cmd : std_logic_vector(1 DOWNTO 0;
  SIGNAL s_shft_ctrl : std_logic_vector(3 DOWNTO 0);
  SIGNAL s_shft_result : std_logic_vector(g_word_size DOWNTO 0);
BEGIN
  -- Input A Multiplexing
  WITH p_isel_A SELECT s_input_A <=
    p_input_A0 WHEN "00",
    p_input_A1 WHEN "01",
    p_input_A2 WHEN "10";
  -- Input B Multiplexing
  WITH p_isel_B SELECT s_input_B <=
    p_input_B0 WHEN "00",
    p_input_B1 WHEN "01",
    p_input_B2 WHEN "10";
  -- Instances of Sub-Components
  adder : carry_select_adder GENERIC MAP (g_block_size => 7, g_blocks => 3) PORT MAP (s_sgnd, s_adder_sub, s_input_A, s_input_B, s_adder_ovflw, s_adder_result);
  logic : bit_manipulator GENERIC MAP (g_size => g_word_size) PORT MAP (s_input_A, s_input_B, s_logic_cmd, s_logic_result);
  div : divider GENERIC MAP (g_size => g_word_size) PORT MAP (s_sgnd, s_input_A, s_input_B, s_div_remain, s_div_result);
  mul : four_quadrant_multiplier GENEIRC MAP (g_size => g_word_size) PORT MAP (s_sgnd, s_input_A, s_input_B, 0, s_mul_result_lo, s_mul_result_hi);
  comp : word_comparator GENERIC MAP (g_size => g_word_size) PORT MAP (s_input_A, s_input_B, s_comp_cmd, s_sgnd, s_comp_result);
  shft : barrel_shifter GENERIC MAP (g_size => g_word_size) PORT MAP (s_shft_cmd, s_shft_ari, s_shft_rot, s_input_A, s_input_B, s_shft_result);
  -- Output Multiplexers
  --
  -- Command	Index	Bitvector
  -- ADD 	0	0000
  -- SUB 	1	0001
  -- SRL 	2	0010
  -- SLL 	3	0011
  -- AND 	4	0100
  -- OR 	5	0101
  -- NOT 	6	0110
  -- DIV 	7	0111
  -- MUL 	8	1000
  -- EQ 	9	1001
  -- LT 	10	1010
  -- GT 	11	1011
  --
  -- Output 1 Multiplexing
  WITH p_csel SELECT p_output_1 <=
    s_adder_result WHEN "0000",
    s_adder_result WHEN "0001",
    s_shft_result WHEN "0010",
    s_shft_result WHEN "0011",
    s_logic_resul WHEN "0100",
    s_logic_resul WHEN "0101",
    s_logic_resul WHEN "0110",
    s_div_result WHEN "0111",
    s_mul_result_lo WHEN "1000",
    "00000000000000000000000000000000" WHEN OTHERS;
  -- Output 2 Multiplexing
  WITH p_csel SELECT p_output_2 <=
    s_div_remain WHEN "0111",
    s_mul_result_hi WHEN "1000",
    "00000000000000000000000000000000" WHEN OTHERS;
  -- Flag Multiplexing
  WITH p_csel SELECT p_flag <=
    s_adder_ovflw WHEN "0000",
    s_adder_ovflw WHEN "0001",
    s_comp_result WHEN "1001",
    s_comp_result WHEN "1010",
    s_comp_result WHEN "1011",
    '0' WHEN OTHERS;
  -- Command Tables
  WITH p_csel SELECT s_sgnd <=
    '1' WHEN "0000",
    '1' WHEN "0001",
    '1' WHEN "0111",
    '1' WHEN "1000",
    '1' WHEN "1010",
    '1' WHEN "1011",
    '0' WHEN OTHERS;
  -- Adder Control
  WITH p_csel SELECT s_adder_sub <=
    '0' WHEN "0000",
    '1' WHEN "0001",
    '0' WHEN OTHERS;
  -- Bitlogic Control
  WITH p_csel SELECT s_logic_cmd <=
    "00" WHEN "0100",
    "01" WHEN "0101",
    "11" WHEN "0110",
    "00" WHEN OTHERS;
  -- Shifter/Rotater Control
  s_shft_cmd(0) <= s_shft_ctrl(0)
  s_shft_cmd(1) <= s_shft_ctrl(1)
  s_shft_ari <= s_shft_ctrl(2)
  s_shft_rot <= s_shft_ctrl(3)
  WITH p_csel SELECT s_shft_ctrl <=
    "0010" WHEN "0010",
    "0001" WHEN "0011",
    "0000" WHEN OTHERS;
  -- Comparator Control
  WITH p_csel SELECT s_comp_cmd <=
    "100" WHEN "1001",
    "001" WHEN "1010",
    "000" WHEN "1011",
    "000" WHEN OTHERS;

END behavior;