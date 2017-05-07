-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;
USE ieee.numeric_std.all;

ENTITY memory IS
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
END memory;

ARCHITECTURE behavior OF memory IS
  TYPE registerArray IS ARRAY(256 DOWNTO 0) OF std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_port0_inputSelect : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_port0_addressSelect : std_logic_vector(g_address_size DOWNTO 0);
  SIGNAL s_port0_write : std_logic;
  SIGNAL s_port1_addressSelect : std_logic_vector(g_address_size DOWNTO 0);
  SIGNAL s_registers : registerArray;
BEGIN
  s_port0_inputSelect <= p_port0_input0;
  s_port0_addressSelect <= p_port0_address0;
  s_port1_addressSelect <= p_port1_address0;
  s_port0_write <= p_ctrl;
  PROCESS (p_clk) BEGIN
    IF rising_edge(p_clk) THEN
      IF s_port0_write = '1' THEN
        s_registers(to_integer(unsigned(s_port0_addressSelect))) <= s_port0_inputSelect;
      END IF;
      p_port1_output <= s_registers(to_integer(unsigned(s_port1_addressSelect)));
    END IF;
  END PROCESS;

END behavior;