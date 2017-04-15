-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY ProgramPath_JumpLogic IS
  GENERIC
  (
    g_wordSize : integer := 31
  );
  PORT
  (
    p_pathA0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_pathB0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_ctrl : in std_logic_vector(3 DOWNTO 0);
    p_ctrlSelect : in std_logic_vector(1 DOWNTO 0;
    p_pathOut : out std_logic_vector(g_wordSize DOWNTO 0)
  );
END ProgramPath_JumpLogic;

ARCHITECTURE behavior OF ProgramPath_JumpLogic IS
  SIGNAL s_pathAInput : std_logic_vector(g_wordSize DOWNTO 0;
  SIGNAL s_pathBInput : std_logic_vector(g_wordSize DOWNTO 0;
  SIGNAL s_pathSelect : std_logic;
BEGIN
  s_pathAInput <= p_pathA0;
  s_pathBInput <= p_pathB0;
  -- Behavior
  WITH p_ctrlSelect SELECT s_pathSelect <=
    p_ctrl(0)  WHEN "00",
    p_ctrl(1)  WHEN "01",
    p_ctrl(2)  WHEN "10",
    p_ctrl(3)  WHEN "11";
  WITH s_pathSelect SELECT p_pathOut <=
    s_pathAInput WHEN '0',
    s_pathBInput WHEN '1';
END behavior;