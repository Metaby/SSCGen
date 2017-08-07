-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY program_rom IS
  GENERIC (
    g_address_size : integer := 15;
    g_word_size : integer := 15
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
    "0000000000010100" WHEN "0000000000000000",
    "0000000000000011" WHEN "0000000000000001",
    "0000000000000000" WHEN "0000000000000010",
    "0000000000010100" WHEN "0000000000000011",
    "0000000000000100" WHEN "0000000000000100",
    "0000000000000000" WHEN "0000000000000101",
    "0000000000010010" WHEN "0000000000000110",
    "0000000000000011" WHEN "0000000000000111",
    "0000000000010000" WHEN "0000000000001000",
    "0000000000000000" WHEN "0000000000001001",
    "0000000000010001" WHEN "0000000000001010",
    "0000000000000001" WHEN "0000000000001011",
    "0000000001000001" WHEN "0000000000001100",
    "0000000000000001" WHEN "0000000000001101",
    "0000000000000011" WHEN "0000000000001110",
    "0000000000000110" WHEN "0000000000001111",
    "0000000000100011" WHEN "0000000000010000",
    "0000000000000000" WHEN "0000000000010001",
    "0000000000000001" WHEN "0000000000010010",
    "0000000000000010" WHEN "0000000000010011",
    "0000000000010010" WHEN "0000000000010100",
    "0000000000000010" WHEN "0000000000010101",
    "0000000001000000" WHEN "0000000000010110",
    "0000000000001000" WHEN "0000000000010111",
    "0000000000000000" WHEN OTHERS;

END behavior;