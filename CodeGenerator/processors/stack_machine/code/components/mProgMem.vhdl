-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY mProgMem IS
  GENERIC (
    g_address_size : integer := 7;
    g_word_size : integer := 15
  );
  PORT (
    p_address0 : in std_logic_vector(g_address_size DOWNTO 0);
    p_word : out std_logic_vector(g_word_size DOWNTO 0)
  );
END mProgMem;

ARCHITECTURE behavior OF mProgMem IS
  SIGNAL s_address : std_logic_vector(g_address_size DOWNTO 0);
BEGIN
  s_address <= p_address0;
  -- Behavior
  WITH s_address SELECT p_word <=
    "0000000000001100" WHEN "00000000",
    "0000000000000000" WHEN "00000001",
    "0000000000000000" WHEN "00000010",
    "0000000000000000" WHEN "00000011",
    "0000000000000000" WHEN "00000100",
    "0000000000000000" WHEN "00000101",
    "0000000000000000" WHEN "00000110",
    "0000000000000000" WHEN "00000111",
    "0000000000000000" WHEN "00001000",
    "0000000000000000" WHEN "00001001",
    "0000000000000000" WHEN "00001010",
    "0000000000000000" WHEN "00001011",
    "0000000000000000" WHEN "00001100",
    "0000000000000000" WHEN "00001101",
    "0000000000000000" WHEN "00001110",
    "0000000000000000" WHEN "00001111",
    "0000000000000111" WHEN "00010000",
    "0000000010000100" WHEN "00010001",
    "0000000011000100" WHEN "00010010",
    "0000000000000100" WHEN "00010011",
    "0000100000000100" WHEN "00010100",
    "0000000000000111" WHEN "00010101",
    "0000000000001100" WHEN "00010110",
    "0000000000000000" WHEN "00010111",
    "0000000000000000" WHEN "00011000",
    "0000000000000000" WHEN "00011001",
    "0000000000000000" WHEN "00011010",
    "0000000000000000" WHEN "00011011",
    "0000000000000000" WHEN "00011100",
    "0000000000000000" WHEN "00011101",
    "0000000000000000" WHEN "00011110",
    "0000000000000000" WHEN "00011111",
    "0000000000010100" WHEN "00100000",
    "0010000010000100" WHEN "00100001",
    "0010000011000100" WHEN "00100010",
    "0000000000000100" WHEN "00100011",
    "0000000000100100" WHEN "00100100",
    "0000001010000100" WHEN "00100101",
    "0000001011000100" WHEN "00100110",
    "0000001000000100" WHEN "00100111",
    "0000101000000100" WHEN "00101000",
    "0000001000000111" WHEN "00101001",
    "0000001000001100" WHEN "00101010",
    "0000000000000000" WHEN "00101011",
    "0000000000000000" WHEN "00101100",
    "0000000000000000" WHEN "00101101",
    "0000000000000000" WHEN "00101110",
    "0000000000000000" WHEN "00101111",
    "0000000000000000" WHEN "00110000",
    "0000000000000000" WHEN "00110001",
    "0000000000000000" WHEN "00110010",
    "0000000000000000" WHEN "00110011",
    "0000000000000000" WHEN "00110100",
    "0000000000000000" WHEN "00110101",
    "0000000000000000" WHEN "00110110",
    "0000000000000000" WHEN "00110111",
    "0000000000000000" WHEN "00111000",
    "0000000000000000" WHEN "00111001",
    "0000000000000000" WHEN "00111010",
    "0000000000000000" WHEN "00111011",
    "0000000000000000" WHEN "00111100",
    "0000000000000000" WHEN "00111101",
    "0000000000000000" WHEN "00111110",
    "0000000000000000" WHEN "00111111",
    "0000000000010100" WHEN "01000000",
    "0010000010000100" WHEN "01000001",
    "0010000011000100" WHEN "01000010",
    "0000000000000100" WHEN "01000011",
    "0000000000100100" WHEN "01000100",
    "0001001010000100" WHEN "01000101",
    "0001001011000100" WHEN "01000110",
    "0001001000000100" WHEN "01000111",
    "0001101000000100" WHEN "01001000",
    "0001001000000111" WHEN "01001001",
    "0001001000001100" WHEN "01001010",
    "0000000000000000" WHEN "01001011",
    "0000000000000000" WHEN "01001100",
    "0000000000000000" WHEN "01001101",
    "0000000000000000" WHEN "01001110",
    "0000000000000000" WHEN "01001111",
    "0000000000000000" WHEN "01010000",
    "0000000000000000" WHEN "01010001",
    "0000000000000000" WHEN "01010010",
    "0000000000000000" WHEN "01010011",
    "0000000000000000" WHEN "01010100",
    "0000000000000000" WHEN "01010101",
    "0000000000000000" WHEN "01010110",
    "0000000000000000" WHEN "01010111",
    "0000000000000000" WHEN "01011000",
    "0000000000000000" WHEN "01011001",
    "0000000000000000" WHEN "01011010",
    "0000000000000000" WHEN "01011011",
    "0000000000000000" WHEN "01011100",
    "0000000000000000" WHEN "01011101",
    "0000000000000000" WHEN "01011110",
    "0000000000000000" WHEN "01011111",
    "1100000100000100" WHEN "01100000",
    "0010000010000100" WHEN "01100001",
    "0010000011000100" WHEN "01100010",
    "0000000000000100" WHEN "01100011",
    "0000000000000111" WHEN "01100100",
    "0000000000001100" WHEN "01100101",
    "0000000000000000" WHEN "01100110",
    "0000000000000000" WHEN "01100111",
    "0000000000000000" WHEN "01101000",
    "0000000000000000" WHEN "01101001",
    "0000000000000000" WHEN "01101010",
    "0000000000000000" WHEN "01101011",
    "0000000000000000" WHEN "01101100",
    "0000000000000000" WHEN "01101101",
    "0000000000000000" WHEN "01101110",
    "0000000000000000" WHEN "01101111",
    "1100000100000100" WHEN "01110000",
    "0000000000000111" WHEN "01110001",
    "0000000000001100" WHEN "01110010",
    "0000000000000000" WHEN "01110011",
    "0000000000000000" WHEN "01110100",
    "0000000000000000" WHEN "01110101",
    "0000000000000000" WHEN "01110110",
    "0000000000000000" WHEN "01110111",
    "0000000000000000" WHEN "01111000",
    "0000000000000000" WHEN "01111001",
    "0000000000000000" WHEN "01111010",
    "0000000000000000" WHEN "01111011",
    "0000000000000000" WHEN "01111100",
    "0000000000000000" WHEN "01111101",
    "0000000000000000" WHEN "01111110",
    "0000000000000000" WHEN "01111111",
    "0000000000010100" WHEN "10000000",
    "0010000010000100" WHEN "10000001",
    "0010000011000100" WHEN "10000010",
    "0000000000000100" WHEN "10000011",
    "0000000000100100" WHEN "10000100",
    "0000101000000100" WHEN "10000101",
    "0000001010000100" WHEN "10000110",
    "0000001011000100" WHEN "10000111",
    "0000001000000100" WHEN "10001000",
    "0000001000000111" WHEN "10001001",
    "0000001000001100" WHEN "10001010",
    "0000000000000000" WHEN OTHERS;

END behavior;