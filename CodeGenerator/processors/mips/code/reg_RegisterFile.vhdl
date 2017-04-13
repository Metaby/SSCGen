-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY reg IS
  GENERIC
  (
    g_addressSize : integer := 31;
    g_wordSize : integer := -1
  );
  PORT
  (
    p_clk : in std_logic;
    p_port0_address : in _std_logic_vector(g_addressSize DOWNTO 0);
    p_port0_word : out _std_logic_vector(g_wordSize DOWNTO 0);
    p_port1_address : in _std_logic_vector(g_addressSize DOWNTO 0);
    p_port1_word : out _std_logic_vector(g_wordSize DOWNTO 0);
    p_port2_write : in std_logic;
    p_port2_word : in _std_logic_vector(g_wordSize DOWNTO 0);
    p_port2_address : in _std_logic_vector(g_addressSize DOWNTO 0)
  );
END reg;

ARCHITECTURE behavior OF reg IS
BEGIN
null
END behavior;