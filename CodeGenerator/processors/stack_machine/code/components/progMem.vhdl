-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY progMem IS
  GENERIC (
    g_address_size : integer := 7;
    g_word_size : integer := 7
  );
  PORT (
    p_address0 : in std_logic_vector(g_address_size DOWNTO 0);
    p_word : out std_logic_vector(g_word_size DOWNTO 0)
  );
END progMem;

ARCHITECTURE behavior OF progMem IS
  SIGNAL s_address : std_logic_vector(g_address_size DOWNTO 0);
BEGIN
  s_address <= p_address0;
  -- Behavior
  WITH s_address SELECT p_word <=
    "00010000" WHEN "00000000",
    "00000001" WHEN "00000001",
    "00010000" WHEN "00000010",
    "00000010" WHEN "00000011",
    "00100000" WHEN "00000100",
    "00000000" WHEN OTHERS;

END behavior;