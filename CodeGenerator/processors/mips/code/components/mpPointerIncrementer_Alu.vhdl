-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY mpPointerIncrementer_Alu IS
  GENERIC
  (
    g_wordSize : integer := 31
  );
  PORT
  (
    p_inputA0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputB0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_output : out std_logic_vector(g_wordSize DOWNTO 0)
  );
END mpPointerIncrementer_Alu;

ARCHITECTURE behavior OF mpPointerIncrementer_Alu IS
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
  SIGNAL s_inputAInput : std_logic_vector(g_wordSize DOWNTO 0;
  SIGNAL s_inputBInput : std_logic_vector(g_wordSize DOWNTO 0;
  SIGNAL s_sgnd : std_logic;
  SIGNAL s_adder_sub : std_logic;
  SIGNAL s_adder_ovflw : std_logic;
  SIGNAL s_adder_result : std_logic_vector(g_wordSize DOWNTO 0;
BEGIN
  s_inputAInput <= p_inputA0;
  s_inputBInput <= p_inputB0;
  -- Behavior
  -- Instances of ALU-Components
  adder : carry_select_adder GENERIC MAP (g_block_size => 7, g_blocks => 3) PORT MAP (s_sgnd, s_adder_sub, s_inputAInput, s_inputBinput, s_adder_ovflw, s_adder_result);
  -- Adder
  p_status(0) <= s_adder_ovflw;

END behavior;