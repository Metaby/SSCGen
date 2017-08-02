-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY microprogram_rom IS
  GENERIC (
    g_address_size : integer := 15;
    g_word_size : integer := 19
  );
  PORT (
    p_address0 : in std_logic_vector(g_address_size DOWNTO 0);
    p_word : out std_logic_vector(g_word_size DOWNTO 0)
  );
END microprogram_rom;

ARCHITECTURE behavior OF microprogram_rom IS
  SIGNAL s_address : std_logic_vector(g_address_size DOWNTO 0);
BEGIN
  s_address <= p_address0;
  -- Behavior
  WITH s_address SELECT p_word <=
    "10000000000000000010" WHEN "0000000000000000",
    "00000000000000000000" WHEN "0000000000000001",
    "00000000000000000000" WHEN "0000000000000010",
    "00000000000000000000" WHEN "0000000000000011",
    "00000000000000000000" WHEN "0000000000000100",
    "00000000000000000000" WHEN "0000000000000101",
    "00000000000000000000" WHEN "0000000000000110",
    "00000000000000000000" WHEN "0000000000000111",
    "00000000000000000000" WHEN "0000000000001000",
    "00000000000000000000" WHEN "0000000000001001",
    "00000000000000000000" WHEN "0000000000001010",
    "00000000000000000000" WHEN "0000000000001011",
    "00000000000000000000" WHEN "0000000000001100",
    "00000000000000000000" WHEN "0000000000001101",
    "00000000000000000000" WHEN "0000000000001110",
    "00000000000000000000" WHEN "0000000000001111",
    "00000000000000000011" WHEN "0000000000010000",
    "00000000000000000010" WHEN "0000000000010001",
    "00000000000000000010" WHEN "0000000000010010",
    "00000000010000000010" WHEN "0000000000010011",
    "00000000000000000011" WHEN "0000000000010100",
    "00000000000000000010" WHEN "0000000000010101",
    "10000000000000000010" WHEN "0000000000010110",
    "00000000000000000000" WHEN "0000000000010111",
    "00000000000000000000" WHEN "0000000000011000",
    "00000000000000000000" WHEN "0000000000011001",
    "00000000000000000000" WHEN "0000000000011010",
    "00000000000000000000" WHEN "0000000000011011",
    "00000000000000000000" WHEN "0000000000011100",
    "00000000000000000000" WHEN "0000000000011101",
    "00000000000000000000" WHEN "0000000000011110",
    "00000000000000000000" WHEN "0000000000011111",
    "00000000000000000011" WHEN "0000000000100000",
    "00000000000001000010" WHEN "0000000000100001",
    "00000000000010000010" WHEN "0000000000100010",
    "00000000010010000010" WHEN "0000000000100011",
    "00000000000000000011" WHEN "0000000000100100",
    "00000000000000000010" WHEN "0000000000100101",
    "10000000000000000010" WHEN "0000000000100110",
    "00000000000000000000" WHEN "0000000000100111",
    "00000000000000000000" WHEN "0000000000101000",
    "00000000000000000000" WHEN "0000000000101001",
    "00000000000000000000" WHEN "0000000000101010",
    "00000000000000000000" WHEN "0000000000101011",
    "00000000000000000000" WHEN "0000000000101100",
    "00000000000000000000" WHEN "0000000000101101",
    "00000000000000000000" WHEN "0000000000101110",
    "00000000000000000000" WHEN "0000000000101111",
    "00000000000000000011" WHEN "0000000000110000",
    "00000000000001000010" WHEN "0000000000110001",
    "00000000000000000110" WHEN "0000000000110010",
    "00000000000000000011" WHEN "0000000000110011",
    "00000000000000000010" WHEN "0000000000110100",
    "10000000000000000010" WHEN "0000000000110101",
    "00000000000000000000" WHEN "0000000000110110",
    "00000000000000000000" WHEN "0000000000110111",
    "00000000000000000000" WHEN "0000000000111000",
    "00000000000000000000" WHEN "0000000000111001",
    "00000000000000000000" WHEN "0000000000111010",
    "00000000000000000000" WHEN "0000000000111011",
    "00000000000000000000" WHEN "0000000000111100",
    "00000000000000000000" WHEN "0000000000111101",
    "00000000000000000000" WHEN "0000000000111110",
    "00000000000000000000" WHEN "0000000000111111",
    "00000000000000000011" WHEN "0000000001000000",
    "00000000000001000010" WHEN "0000000001000001",
    "00000000000000001010" WHEN "0000000001000010",
    "00000000000000000011" WHEN "0000000001000011",
    "00000000000000000010" WHEN "0000000001000100",
    "10000000000000000010" WHEN "0000000001000101",
    "00000000000000000000" WHEN "0000000001000110",
    "00000000000000000000" WHEN "0000000001000111",
    "00000000000000000000" WHEN "0000000001001000",
    "00000000000000000000" WHEN "0000000001001001",
    "00000000000000000000" WHEN "0000000001001010",
    "00000000000000000000" WHEN "0000000001001011",
    "00000000000000000000" WHEN "0000000001001100",
    "00000000000000000000" WHEN "0000000001001101",
    "00000000000000000000" WHEN "0000000001001110",
    "00000000000000000000" WHEN "0000000001001111",
    "00000000000000000011" WHEN "0000000001010000",
    "00000000000001000010" WHEN "0000000001010001",
    "00000000000000000010" WHEN "0000000001010010",
    "00000000000000010010" WHEN "0000000001010011",
    "00000000000000000011" WHEN "0000000001010100",
    "00000000000001000010" WHEN "0000000001010101",
    "00000000000000000010" WHEN "0000000001010110",
    "00000000000000100010" WHEN "0000000001010111",
    "00000000001000000011" WHEN "0000000001011000",
    "00000000001000000010" WHEN "0000000001011001",
    "00000000001001000010" WHEN "0000000001011010",
    "00000000001000000010" WHEN "0000000001011011",
    "00000000011000000010" WHEN "0000000001011100",
    "00000000000000000011" WHEN "0000000001011101",
    "00000000000000000010" WHEN "0000000001011110",
    "10000000000000000010" WHEN "0000000001011111",
    "00000000000000000011" WHEN "0000000001100000",
    "00000000000001000010" WHEN "0000000001100001",
    "00000000000000000010" WHEN "0000000001100010",
    "00000000000000010010" WHEN "0000000001100011",
    "00000000000000000011" WHEN "0000000001100100",
    "00000000000001000010" WHEN "0000000001100101",
    "00000000000000000010" WHEN "0000000001100110",
    "00000000000000100010" WHEN "0000000001100111",
    "00000000101000000011" WHEN "0000000001101000",
    "00000000101000000010" WHEN "0000000001101001",
    "00000000101001000010" WHEN "0000000001101010",
    "00000000101000000010" WHEN "0000000001101011",
    "00000000111000000010" WHEN "0000000001101100",
    "00000000000000000011" WHEN "0000000001101101",
    "00000000000000000010" WHEN "0000000001101110",
    "10000000000000000010" WHEN "0000000001101111",
    "00000000000000000011" WHEN "0000000001110000",
    "00000000000001000010" WHEN "0000000001110001",
    "00000000000000000010" WHEN "0000000001110010",
    "00000000000000010010" WHEN "0000000001110011",
    "00000000000000000011" WHEN "0000000001110100",
    "00000000000001000010" WHEN "0000000001110101",
    "00000000000000000010" WHEN "0000000001110110",
    "00000000000000100010" WHEN "0000000001110111",
    "00000001001000000011" WHEN "0000000001111000",
    "00000001001000000010" WHEN "0000000001111001",
    "00000001001001000010" WHEN "0000000001111010",
    "00000001001000000010" WHEN "0000000001111011",
    "00000001011000000010" WHEN "0000000001111100",
    "00000000000000000011" WHEN "0000000001111101",
    "00000000000000000010" WHEN "0000000001111110",
    "10000000000000000010" WHEN "0000000001111111",
    "00000000000000000011" WHEN "0000000010000000",
    "00000000000001000010" WHEN "0000000010000001",
    "00000000000000000010" WHEN "0000000010000010",
    "00000000000000010010" WHEN "0000000010000011",
    "00000000000000000011" WHEN "0000000010000100",
    "00000000000001000010" WHEN "0000000010000101",
    "00000000000000000010" WHEN "0000000010000110",
    "00000000000000100010" WHEN "0000000010000111",
    "00000001101000000011" WHEN "0000000010001000",
    "00000001101000000010" WHEN "0000000010001001",
    "00000001101001000010" WHEN "0000000010001010",
    "00000001101000000010" WHEN "0000000010001011",
    "00000001111000000010" WHEN "0000000010001100",
    "00000000000000000011" WHEN "0000000010001101",
    "00000000000000000010" WHEN "0000000010001110",
    "10000000000000000010" WHEN "0000000010001111",
    "00000000000000000000" WHEN OTHERS;

END behavior;