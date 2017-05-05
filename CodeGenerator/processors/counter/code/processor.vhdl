-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY processor IS
  GENERIC (
    g_word_size : integer := 31
  );
  PORT (
    p_clk : in std_logic;
    p_reset : in std_logic;
    p_mem_in : in std_logic_vector(7 DOWNTO 0);
    p_mem_in_adr : in std_logic_vector(7 DOWNTO 0);
    p_mem_out_adr : in std_logic_vector(7 DOWNTO 0);
    p_mem_control : in std_logic;
    p_mem_out : out std_logic_vector(7 DOWNTO 0)
  );
END processor;

ARCHITECTURE behavior OF processor IS
  COMPONENT memory
    GENERIC (
      g_address_size : integer := 7;
      g_word_size : integer := 7
    );
    PORT (
      p_clk : in std_logic;
      p_port0_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_port0_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_port0_write : in std_logic;
      p_port1_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_port1_output : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
BEGIN
  memory_instance : memory GENERIC MAP(g_address_size => 7, g_word_size => 7)  PORT MAP(p_clk, p_mem_in, p_mem_in_adr, p_mem_control, p_mem_out_adr, p_mem_out);

END behavior;