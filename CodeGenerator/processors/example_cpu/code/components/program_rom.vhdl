-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY program_rom IS
  GENERIC (
    g_address_size : integer := 7;
    g_word_size : integer := 7
  );
  PORT (
    p_address0 : in std_logic_vector(g_address_size DOWNTO 0);
    p_word : out std_logic_vector(g_word_size DOWNTO 0)
  );
END program_rom;

ARCHITECTURE behavior OF program_rom IS
  SIGNAL s_address : std_logic_vector(g_address_size DOWNTO 0);
BEGIN
  s_address <= p_address0;
  -- Behavior
  WITH s_address SELECT p_word <=
    "00010100" WHEN "00000000",
    "00000011" WHEN "00000001",
    "00000000" WHEN "00000010",
    "00010010" WHEN "00000011",
    "00000011" WHEN "00000100",
    "00010000" WHEN "00000101",
    "00000000" WHEN "00000110",
    "00010001" WHEN "00000111",
    "00000001" WHEN "00001000",
    "01000001" WHEN "00001001",
    "00000001" WHEN "00001010",
    "00000011" WHEN "00001011",
    "00000011" WHEN "00001100",
    "00100011" WHEN "00001101",
    "00000000" WHEN "00001110",
    "00000001" WHEN "00001111",
    "00000010" WHEN "00010000",
    "00010010" WHEN "00010001",
    "00000010" WHEN "00010010",
    "01000000" WHEN "00010011",
    "00000101" WHEN "00010100",
    "00000000" WHEN OTHERS;

END behavior;