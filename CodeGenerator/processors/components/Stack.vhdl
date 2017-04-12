-- Auto generated Register
LIBRARY ieee;
USE ieee.std_logic_1164.all;

    
ENTITY Register IS
  Generic (
    g_addressSize : integer := 4;
    g_wordSize    : integer := 32
  );
  Port (
    p_clk   : in  std_logic;
    p_incr  : in  std_logic;
    p_decr  : in  std_logic;
    p_write : in  std_logic;
    p_in    : in  std_logic_vector(g_wordSize DOWNTO 0);
    p_out   : out std_logic_vector(g_wordSize DOWNTO 0)
  );
END Register;

ARCHITECTURE behavior OF Register IS
  TYPE vector_map IS ARRAY (g_addressSize - 1 DOWNTO 0) OF std_logic_vector(g_wordSize - 1 DOWNTO 0);
  SIGNAL s_registers : vector_map;
  SIGNAL s_pointer   : std_logic_vector(g_addressSize - 1 DOWNTO 0);
  BEGIN
  PROCESS (p_clk) BEGIN
    IF rising_edge(p_clk) THEN
      IF p_incr = '1' THEN
        s_pointer <= s_pointer + 1;        
      ELSIF p_decr = '1' THEN
        s_pointer <= s_pointer - 1;
      ELSIF p_write = '1' THEN
        s_registers(s_pointer) <= p_in;
      END IF;
      p_out <= s_registers(s_pointer);
    END IF;
  END PROCESS;
END behavior;