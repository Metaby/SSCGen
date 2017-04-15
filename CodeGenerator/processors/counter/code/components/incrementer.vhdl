-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY incrementer IS
  GENERIC
  (
    g_wordSize : integer := 31
  );
  PORT
  (
    p_inputA0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputB0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_output : out std_logic_vector(g_wordSize DOWNTO 0)
  );
END incrementer;

ARCHITECTURE behavior OF incrementer IS
  SIGNAL s_inputAInput : std_logic_vector(g_wordSize DOWNTO 0;
  SIGNAL s_inputBInput : std_logic_vector(g_wordSize DOWNTO 0;
BEGIN
  s_inputAInput <= p_inputA0;
  s_inputBInput <= p_inputB0;
  -- Behavior

END behavior;