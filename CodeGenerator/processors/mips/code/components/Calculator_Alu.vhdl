-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY Calculator_Alu IS
  GENERIC
  (
    g_wordSize : integer := 31
  );
  PORT
  (
    p_inputA0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputA1 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputA2 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputA3 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputB0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputB1 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputB2 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputASelect : in std_logic_vector(1 DOWNTO 0);
    p_inputBSelect : in std_logic_vector(1 DOWNTO 0);
    p_operation : in std_logic_vector(3 DOWNTO 0);
    p_status : out std_logic_vector(4 DOWNTO 0);
    p_output : out std_logic_vector(g_wordSize DOWNTO 0);
    p_sec_output : out std_logic_vector(g_wordSize DOWNTO 0)
  );
END Calculator_Alu;

ARCHITECTURE behavior OF Calculator_Alu IS
  COMPONENT carry_select_adder
    GENERIC (
      g_block_size : integer := 7;
      g_blocks     : integer := 3;
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
      g_size : integer := 31;
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
      g_size : integer := 31;
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
      g_size : integer := 31;
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
      g_size : integer := 31;
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
      g_size : integer := 31;
    );
    PORT (
      p_cmd    : in  std_logic;
      p_arith  : in  std_logic;
      p_rotate : in  std_logic;
      p_op_1   : in  std_logic_vector(g_size DOWNTO 0);
      p_op_2   : in  std_logic_vector(g_size DOWNTO 0);
      p_result : out std_logic_vector(g_size DOWNTO 0)
    );
  END COMPONENT;
  SIGNAL s_inputAInput : std_logic_vector(g_wordSize DOWNTO 0;
  SIGNAL s_inputBInput : std_logic_vector(g_wordSize DOWNTO 0;
  SIGNAL s_sgnd : std_logic;
  SIGNAL s_adder_sub : std_logic;
  SIGNAL s_adder_ovflw : std_logic;
  SIGNAL s_adder_result : std_logic_vector(g_wordSize DOWNTO 0);
  SIGNAL s_logic_cmd : std_logic_vector(1 DOWNTO 0;
  SIGNAL s_logic_result : std_logic_vector(g_wordSize DOWNTO 0);
  SIGNAL s_div_remain : std_logic_vector(g_wordSize DOWNTO 0);
  SIGNAL s_div_result : std_logic_vector(g_wordSize DOWNTO 0);
  SIGNAL s_mul_result_hi : std_logic_vector(g_wordSize DOWNTO 0);
  SIGNAL s_mul_result_lo : std_logic_vector(g_wordSize DOWNTO 0);
  SIGNAL s_comp_g : std_logic;
  SIGNAL s_comp_l : std_logic;
  SIGNAL s_shft_cmd : std_logic;
  SIGNAL s_shft_ari : std_logic;
  SIGNAL s_shft_rot : std_logic;
  SIGNAL s_shft_result : std_logic_vector(g_wordSize DOWNTO 0);
  SIGNAL s_alu_cmd : std_logic_vector(9 DOWNTO 0);
BEGIN
  WITH p_inputASelect SELECT s_inputAInput <=
    p_inputA0 WHEN "00",
    p_inputA1 WHEN "01",
    p_inputA2 WHEN "10",
    p_inputA3 WHEN "11";
  WITH p_inputBSelect SELECT s_inputBInput <=
    p_inputB0 WHEN "00",
    p_inputB1 WHEN "01",
    p_inputB2 WHEN "10";
  -- Command-Vector
  s_sgnd <= s_alu_cmd(0)
  s_adder_sub <= s_alu_cmd(1);
  s_logic_cmd(0) <= s_alu_cmd(2);
  s_logic_cmd(1) <= s_alu_cmd(3);
  s_shft_cmd <= s_alu_cmd(4);
  s_shft_ari <= s_alu_cmd(5);
  s_shft_rot <= s_alu_cmd(6);
  -- Instances of ALU-Components
  adder : carry_select_adder GENERIC MAP (g_block_size => 7, g_blocks => 3) PORT MAP (s_sgnd, s_adder_sub, s_inputAInput, s_inputBinput, s_adder_ovflw, s_adder_result);
  logic : bit_manipulator GENERIC MAP (g_size => g_wordSize) PORT MAP (s_inputAInput, s_inputBInput, s_logic_cmd, s_logic_result);
  div : divider GENERIC MAP (g_size => g_wordSize) PORT MAP (s_sgnd, s_inputAInput, s_inputBInput, s_div_remain, s_div_result);
  mul : four_quadrant_multiplier GENEIRC MAP (g_size => g_wordSize) PORT MAP (s_sgnd, s_inputAInput, s_inputBInput, 0, s_mul_result_lo, s_mul_result_hi);
  comp : tree_comparator GENERIC MAP (g_size => g_wordSize) PORT MAP (s_inputAInput, s_inputBInput, s_sgnd, s_comp_g, s_comp_l);
  shft : barrel_shifter GENERIC MAP (g_size => g_wordSize) PORT MAP (s_shft_cmd, s_shft_ari, s_shft_rot, s_inputAInput, s_inputBInput, s_shft_result);
  -- Behavior
  -- Adder
  p_status(0) <= s_adder_ovflw;
  -- Comparator
  p_status(1) <= AND "00000000000000000000000000000000"; -- A == 0
  p_status(2) <= NOT s_comp_g AND NOT s_comp_l; -- A == B
  p_status(3) <= NOT s_comp_g AND s_comp_l; -- A < B
  p_status(4) <= s_comp_g AND NOT s_comp_l; -- A > B
  WITH s_alu_cmd(9 DOWNTO 7) SELECT p_output <= 
    s_adder_result WHEN "000",
    s_logic_result WHEN "001",
    s_div_result WHEN "010",
    s_mul_result_lo WHEN "011",
    s_shft_result WHEN "100";

  -- Command-Table
END behavior;