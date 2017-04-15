-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY Calculator_Alu IS
  GENERIC
  (
    g_wordSize : integer := 31
  );
  PORT
  (
    p_inputA0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputA1 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputA2 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputA3 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputB0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputB1 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputB2 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_inputASelect : in std_logic_vector(1 DOWNTO 0);
    p_inputBSelect : in std_logic_vector(1 DOWNTO 0);
    p_operation : in std_logic_vector(3 DOWNTO 0);
    p_status : out std_logic_vector(5 DOWNTO 0);
    p_output : out std_logic_vector(g_wordSize DOWNTO 0)
  );
END Calculator_Alu;

ARCHITECTURE behavior OF Calculator_Alu IS
  SIGNAL s_inputAInput : std_logic_vector(g_wordSize DOWNTO 0;
  SIGNAL s_inputBInput : std_logic_vector(g_wordSize DOWNTO 0;
BEGIN
  -- input multiplexer
  WITH p_inputASelect SELECT s_inputAInput <=
    p_inputA0 WHEN "00",
    p_inputA1 WHEN "01",
    p_inputA2 WHEN "10",
    p_inputA3 WHEN "11";
  -- input multiplexer
  WITH p_inputBSelect SELECT s_inputBInput <=
    p_inputB0 WHEN "00",
    p_inputB1 WHEN "01",
    p_inputB2 WHEN "10";
  -- Behavior

END behavior;