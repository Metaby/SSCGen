-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY mcPcIncr IS
  GENERIC
  (
    g_wordSize : integer := 31
  );
  PORT
  (
    p_operandA : in std_logic_vector(g_wordSize DOWNTO 0);
    p_operandB : in std_logic_vector(g_wordSize DOWNTO 0);
    p_result : out std_logic_vector(g_wordSize DOWNTO 0)
  );
END mcPcIncr;

ARCHITECTURE behavior OF mcPcIncr IS
BEGIN

END behavior;