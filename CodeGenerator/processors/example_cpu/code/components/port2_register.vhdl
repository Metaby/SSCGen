-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY port2_register IS
  GENERIC (
    g_word_size : integer := 15
  );
  PORT (
    p_clk : in std_logic;
    p_rst : in std_logic;
    p_ctrl : in std_logic;
    p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
    p_word : out std_logic_vector(g_word_size DOWNTO 0)
  );
END port2_register;

ARCHITECTURE behavior OF port2_register IS
  SIGNAL s_write : std_logic;
  SIGNAL s_input : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_out : std_logic_vector(g_word_size DOWNTO 0);
BEGIN
  -- Behavior
  s_write <= p_ctrl;
  s_input <= p_input0;
  p_word <= s_out;
  PROCESS (p_clk) BEGIN
    IF rising_edge(p_clk) THEN
      IF p_rst = '1' THEN
        s_out <= (OTHERS => '0');
      ELSIF s_write = '1' THEN
        s_out <= s_input;
      ELSE
        s_out <= s_out;
      END IF;
    END IF;
  END PROCESS;
END behavior;