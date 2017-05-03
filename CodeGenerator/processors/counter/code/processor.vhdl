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
    p_step : in std_logic_vector(7 DOWNTO 0);
    p_portA : out std_logic_vector(7 DOWNTO 0)
  );
END processor;

ARCHITECTURE behavior OF processor IS
  COMPONENT counter
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_write : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT port
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_write : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT incrementer
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_input_A0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_input_B0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_flag : out std_logic;
      p_output_1 : out std_logic_vector(g_word_size DOWNTO 0);
      p_output_2 : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  SIGNAL s_counter_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_incrementer_out : std_logic_vector(7 DOWNTO 0);
BEGIN
  counter_instance :  GENERIC MAP(g_word_size => 7)  PORT MAP(p_clk, p_rst, '1', s_incrementer_out, s_counter_out);
  port_instance :  GENERIC MAP(g_word_size => 7)  PORT MAP(p_clk, p_rst, '1', s_counter_out, p_portA);
  incrementer_instance :  GENERIC MAP(g_word_size => 7)  PORT MAP(s_counter_out, p_step, OPEN, s_incrementer_out, OPEN);

END behavior;