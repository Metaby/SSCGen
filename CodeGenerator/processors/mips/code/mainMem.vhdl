-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY mainMem IS
  GENERIC
  (
    g_addressSize : integer := 31;
    g_wordSize : integer := -1
  );
  PORT
  (
    p_clk : in std_logic;
    p_port0_write : in std_logic;
    p_port0_word : in _std_logic_vector(g_wordSize DOWNTO 0);
    p_port0_address : in _std_logic_vector(g_addressSize DOWNTO 0);
    p_port1_address : in _std_logic_vector(g_addressSize DOWNTO 0);
    p_port1_word : out _std_logic_vector(g_wordSize DOWNTO 0)
  );
END mainMem;

ARCHITECTURE behavior OF mainMem IS
BEGIN
null
END behavior;