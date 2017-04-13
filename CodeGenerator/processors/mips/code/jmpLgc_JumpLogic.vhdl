-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY jmpLgc IS
  GENERIC
  (
    g_wordSize : integer := 31
  );
  PORT
  (
    p_targetA : in std_logic_vector(g_wordSize DOWNTO 0);
    p_targetB : in std_logic_vector(g_wordSize DOWNTO 0);
    p_ctrl : in std_logic_vector(3 DOWNTO 0);
    p_ctrlSelect : in std_logic_vector(1 DOWNTO 0);
    p_result : out std_logic_vector(g_wordSize DOWNTO 0)
  );
END jmpLgc;

ARCHITECTURE behavior OF jmpLgc IS
BEGIN
null
END behavior;