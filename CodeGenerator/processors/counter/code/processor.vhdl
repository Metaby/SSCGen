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
    p_counter_out : out std_logic_vector(7 DOWNTO 0);
    p_rom_out : out std_logic_vector(7 DOWNTO 0);
    p_mem_out : out std_logic_vector(7 DOWNTO 0)
  );
END processor;

ARCHITECTURE behavior OF processor IS
  COMPONENT counter1
    GENERIC (
      g_word_size : integer := 7
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_ctrl : in std_logic;
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
      p_ctrl : in std_logic;
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
      p_ctrl : in std_logic;
      p_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_word : out std_logic_vector(g_word_size DOWNTO 0)
    );
  END COMPONENT;
  COMPONENT program_rom
    GENERIC (
      g_address_size : integer := 7;
      g_word_size : integer := 7
    );
    PORT (
      p_address0 : in std_logic_vector(g_address_size DOWNTO 0);
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
  COMPONENT memory
    GENERIC (
      g_address_size : integer := 7;
      g_word_size : integer := 7
    );
    PORT (
      p_clk : in std_logic;
      p_rst : in std_logic;
      p_port0_input0 : in std_logic_vector(g_word_size DOWNTO 0);
      p_port0_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_port1_address0 : in std_logic_vector(g_address_size DOWNTO 0);
      p_port1_output : out std_logic_vector(g_word_size DOWNTO 0);
      p_ctrl : in std_logic
    );
  END COMPONENT;
  SIGNAL s_counter1_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_program_rom_out : std_logic_vector(7 DOWNTO 0);
  SIGNAL s_incrementer_out : std_logic_vector(7 DOWNTO 0);
BEGIN
  counter1_instance : counter1
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      p_clk,
      p_reset,
      '1',
      s_incrementer_out,
      s_counter1_out
    );
  output_register1_instance : output_register1
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      p_clk,
      p_reset,
      '1',
      s_counter1_out,
      p_counter_out
    );
  output_register2_instance : output_register2
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      p_clk,
      p_reset,
      '1',
      s_program_rom_out,
      p_rom_out
    );
  program_rom_instance : program_rom
    GENERIC MAP (g_address_size => 7, g_word_size => 7)
    PORT MAP (
      s_counter1_out,
      s_program_rom_out
    );
  incrementer_instance : incrementer
    GENERIC MAP (g_word_size => 7)
    PORT MAP (
      s_counter1_out,
      "00000001",
      OPEN,
      s_incrementer_out,
      OPEN
    );
  memory_instance : memory
    GENERIC MAP (g_address_size => 7, g_word_size => 7)
    PORT MAP (
      p_clk,
      p_reset,
      p_mem_in,
      p_mem_in_adr,
      p_mem_out_adr,
      p_mem_out,
      p_mem_control
    );
END behavior;