-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY mux1 IS
  GENERIC (
    g_word_size : integer := 7
  );
  PORT (
    p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
    p_input1 : in std_logic_vector(g_word_size DOWNTO 0);
    p_input2 : in std_logic_vector(g_word_size DOWNTO 0);
    p_isel : in std_logic_vector(1 DOWNTO 0);
    p_word : out std_logic_vector(g_word_size DOWNTO 0)
  );
END mux1;

ARCHITECTURE behavior OF mux1 IS
BEGIN
  WITH p_isel SELECT p_word <=
    p_input0 WHEN "00",
    p_input1 WHEN "01",
    p_input2 WHEN "10",
    (OTHERS => '0') WHEN OTHERS;

END behavior;