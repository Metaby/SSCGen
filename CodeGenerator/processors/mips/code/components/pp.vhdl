-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY pp IS
  GENERIC
  (
    g_wordSize : integer := 31
  );
  PORT
  (
    p_clk : in std_logic;
    p_write : in std_logic;
    p_input0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_input1 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputSelect : in std_logic;
    p_word : out std_logic_vector(g_wordSize DOWNTO 0)
  );
END pp;

ARCHITECTURE behavior OF pp IS
  SIGNAL s_input : std_logic_vector(g_wordSie DOWNTO 0;
BEGIN
  -- input multiplexer
  WITH p_inputSelect SELECT s_input <=
    p_input0 WHEN '0',
    p_input1 WHEN '1';
  -- Behavior
  PROCESS (p_clk) BEGIN
    IF rising_edge(p_clk) AND p_write = '1' THEN
      p_out <= s_input;
    ELSE
      p_out <= p_out;
    END IF;
  END PROCESS;
END behavior;