-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY PortA_Register IS
  GENERIC (
    g_word_size : integer := 31
  );
  PORT (
    p_clk : in std_logic;
    p_rst : in std_logic;
    p_write : in std_logic;
    p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
    p_input1 : in std_logic_vector(g_word_size DOWNTO 0);
    p_isel : in std_logic;
    p_word : out std_logic_vector(g_word_size DOWNTO 0)
  );
END PortA_Register;

ARCHITECTURE behavior OF PortA_Register IS
  SIGNAL s_input : std_logic_vector(g_word_size DOWNTO 0;
BEGIN
  WITH p_isel SELECT s_input <=
    p_input0 WHEN '0',
    p_input1 WHEN '1';
  -- Behavior
  PROCESS (p_clk) BEGIN
    IF rising_edge(p_clk) THEN
      IF p_rst = '1' THEN
        p_word <= (OTHERS => '0');
      ELSIF p_write = '1' THEN
        p_word <= s_input;
      ELSE
        p_word <= p_word;
      END IF;;
    END IF;
  END PROCESS;
END behavior;