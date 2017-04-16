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
    p_status : out std_logic_vector(5 DOWNTO 0);
    p_output : out std_logic_vector(g_wordSize DOWNTO 0)
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
      p_add    : in  std_logic_vector(g_size DOWNTO 0);
      p_result : out std_logic_vector(g_size DOWNTO 0)
    );
  END COMPONENT;
  SIGNAL s_inputAInput : std_logic_vector(g_wordSize DOWNTO 0;
  SIGNAL s_inputBInput : std_logic_vector(g_wordSize DOWNTO 0;
BEGIN
  -- input multiplexer
  WITH p_inputASelect SELECT s_inputAInput <=
    p_inputA0 WHEN "00",
    p_inputA1 WHEN "01",
    p_inputA2 WHEN "10",
    p_inputA3 WHEN "11";
  -- input multiplexer
  WITH p_inputBSelect SELECT s_inputBInput <=
    p_inputB0 WHEN "00",
    p_inputB1 WHEN "01",
    p_inputB2 WHEN "10";
  -- Behavior

END behavior;