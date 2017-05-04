-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY output_register2 IS
  GENERIC (
    g_word_size : integer := 7
  );
  PORT (
    p_clk : in std_logic;
    p_rst : in std_logic;
    p_write : in std_logic;
    p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
    p_word : out std_logic_vector(g_word_size DOWNTO 0)
  );
END output_register2;

ARCHITECTURE behavior OF output_register2 IS
  SIGNAL s_input : std_logic_vector(g_word_size DOWNTO 0);
BEGIN
  s_input <= p_input0;
  -- Behavior
  PROCESS (p_clk) BEGIN
    IF rising_edge(p_clk) THEN
      IF p_rst = '1' THEN
        p_word <= (OTHERS => '0');
      ELSIF p_write = '1' THEN
        p_word <= s_input;
      END IF;
    END IF;
  END PROCESS;
END behavior;