-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY alu IS
  GENERIC
  (
    g_wordSize : integer := 31
  );
  PORT
  (
    p_operation : in std_logic_vector(3 DOWNTO 0);
    p_operandA : in std_logic_vector(g_wordSize DOWNTO 0);
    p_operandB : in std_logic_vector(g_wordSize DOWNTO 0);
    p_result : out std_logic_vector(g_wordSize DOWNTO 0);
    p_status : out std_logic_vector(5 DOWNTO 0)
  );
END alu;

ARCHITECTURE behavior OF alu IS
BEGIN
null
END behavior;