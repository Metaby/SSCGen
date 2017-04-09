-- Auto generated Register
LIBRARY ieee;
USE ieee.std_logic_1164.all;

    
ENTITY Register IS
  Generic (
    g_wordSize : integer := 32
  );
  Port (
    p_clk   : in  std_logic;
    p_write : in  std_logic;
    p_in    : in  std_logic_vector(g_wordSize DOWNTO 0);
    p_out   : out std_logic_vector(g_wordSize DOWNTO 0)
  );
END Register;

ARCHITECTURE behavior OF Register IS
  
BEGIN
  PROCESS (p_clk) BEGIN
    IF rising_edge(p_clk) AND p_write = '1' THEN
      p_out <= p_in;
    ELSE
      p_out <= p_out;
    END IF;
  END PROCESS;
END behavior;
