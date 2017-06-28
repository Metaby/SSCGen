-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY pcReg IS
  GENERIC (
    g_word_size : integer := 7
  );
  PORT (
    p_clk : in std_logic;
    p_rst : in std_logic;
    p_ctrl : in  std_logic_vector(1 DOWNTO 0);
    p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
    p_input1 : in std_logic_vector(g_word_size DOWNTO 0);
    p_word : out std_logic_vector(g_word_size DOWNTO 0)
  );
END pcReg;

ARCHITECTURE behavior OF pcReg IS
  SIGNAL s_write : std_logic;
  SIGNAL s_isel : std_logic;
  SIGNAL s_input : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_out : std_logic_vector(g_word_size DOWNTO 0);
BEGIN
  -- Behavior
  s_write <= p_ctrl(0);
  s_isel <= p_ctrl(1);
  WITH s_isel SELECT s_input <=
    p_input0 WHEN '0',
    p_input1 WHEN '1',
    (OTHERS => '0') WHEN OTHERS;
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