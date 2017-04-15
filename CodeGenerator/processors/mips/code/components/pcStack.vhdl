-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY pcStack IS
  GENERIC
  (
    g_addressSize : integer := 31;
    g_wordSize : integer := 31
  );
  PORT
  (
    p_clk : in std_logic;
    p_write : in std_logic;
    p_incr : in std_logic;
    p_decr : in std_logic;
    p_input0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_word : out std_logic_vector(g_wordSize DOWNTO 0)
  );
END pcStack;

ARCHITECTURE behavior OF pcStack IS
  SIGNAL s_input : std_logic_vector(g_wordSie DOWNTO 0;
BEGIN
  s_input <= p_input0;
  -- Behavior
  PROCESS (p_clk) BEGIN
    IF rising_edge(p_clk)
      IF p_incr = '1' THEN
        s_pointer <= s_pointer + 1;
      ELSIF p_decr = '1' THEN
        s_pointer <= s_pointer - 1;
      ELSIF p_write = '1' THEN
        s_registers(s_pointer) <= s_input;
      END IF;
      p_word <= s_registers(s_pointer);
    END IF;
  END PROCESS;
END behavior;