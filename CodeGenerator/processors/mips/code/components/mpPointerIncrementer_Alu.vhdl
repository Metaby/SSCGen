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
BEGIN
  s_inputAInput <= p_inputA0;
  s_inputBInput <= p_inputB0;
  -- Behavior

END behavior;