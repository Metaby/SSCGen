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
    p_count_up : out std_logic_vector(7 DOWNTO 0);
    p_count_down : out std_logic_vector(7 DOWNTO 0)
  );
END processor;

ARCHITECTURE behavior OF processor IS
  COMPONENT counter1
    GENERIC (
      g_word_size : integer := 6
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_write : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT counter2
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
  COMPONENT output_register1
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
  COMPONENT output_register2
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
  COMPONENT decrementer
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
  SIGNAL s_counter1_out : std_logic_vector(6 DOWNTO 0);
  SIGNAL s_counter2_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_incrementer_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_decrementer_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_tmp_0 : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_tmp_1 : std_logic_vector(7 DOWNTO 0);
BEGIN
  counter1_instance : counter1 GENERIC MAP(g_word_size => 6)  PORT MAP(p_clk, p_reset, '1', s_incrementer_out(6 DOWNTO 0), s_counter1_out);
  counter2_instance : counter2 GENERIC MAP(g_word_size => 7)  PORT MAP(p_clk, p_reset, '1', s_decrementer_out, s_counter2_out);
  output_register1_instance : output_register1 GENERIC MAP(g_word_size => 7)  PORT MAP(p_clk, p_reset, '1', s_tmp_0, p_count_up);
  output_register2_instance : output_register2 GENERIC MAP(g_word_size => 7)  PORT MAP(p_clk, p_reset, '1', s_counter2_out, p_count_down);
  incrementer_instance : incrementer GENERIC MAP(g_word_size => 7)  PORT MAP(s_tmp_1, p_step, OPEN, s_incrementer_out, OPEN);
  decrementer_instance : decrementer GENERIC MAP(g_word_size => 7)  PORT MAP(s_counter2_out, p_step, OPEN, s_decrementer_out, OPEN);
  s_tmp_0 <= '0' & s_counter1_out;
  s_tmp_1 <= '0' & s_counter1_out;

END behavior;