-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY ir IS
  GENERIC
  (
    g_wordSize : integer := 31
  );
  PORT
  (
    p_clk : in std_logic;
    p_write : in std_logic;
    p_input0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_word : out std_logic_vector(g_wordSize DOWNTO 0)
  );
END ir;

ARCHITECTURE behavior OF ir IS
  SIGNAL s_input : std_logic_vector(g_wordSie DOWNTO 0;
BEGIN
  s_input <= p_input0;
  -- Behavior
  PROCESS (p_clk) BEGIN
    IF rising_edge(p_clk) AND p_write = '1' THEN
      p_out <= s_input;
    ELSE
      p_out <= p_out;
    END IF;
  END PROCESS;
END behavior;