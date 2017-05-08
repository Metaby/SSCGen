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
    p_port0_address0 : in std_logic_vector(g_address_size DOWNTO 0);
    p_port0_address1 : in std_logic_vector(g_address_size DOWNTO 0);
    p_port0_address2 : in std_logic_vector(g_address_size DOWNTO 0);
    p_port0_output : out std_logic_vector(g_word_size DOWNTO 0);
    p_port1_input0 : in std_logic_vector(g_word_size DOWNTO 0);
    p_port1_input1 : in std_logic_vector(g_word_size DOWNTO 0);
    p_port1_input2 : in std_logic_vector(g_word_size DOWNTO 0);
    p_port1_address0 : in std_logic_vector(g_address_size DOWNTO 0);
    p_port1_address1 : in std_logic_vector(g_address_size DOWNTO 0);
    p_port1_address2 : in std_logic_vector(g_address_size DOWNTO 0);
    p_port2_address0 : in std_logic_vector(g_address_size DOWNTO 0);
    p_port2_address1 : in std_logic_vector(g_address_size DOWNTO 0);
    p_port2_address2 : in std_logic_vector(g_address_size DOWNTO 0);
    p_port2_output : out std_logic_vector(g_word_size DOWNTO 0);
    p_ctrl : in std_logic_vector(8 DOWNTO 0)
  );
END memory;

ARCHITECTURE behavior OF memory IS
  TYPE registerArray IS ARRAY(256 DOWNTO 0) OF std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_port0_asel : std_logic_vector(1 DOWNTO 0);
  SIGNAL s_port0_addressSelect : std_logic_vector(g_address_size DOWNTO 0);
  SIGNAL s_port1_isel : std_logic_vector(1 DOWNTO 0);
  SIGNAL s_port1_asel : std_logic_vector(1 DOWNTO 0);
  SIGNAL s_port1_inputSelect : std_logic_vector(g_word_size DOWNTO 0);
  SIGNAL s_port1_addressSelect : std_logic_vector(g_address_size DOWNTO 0);
  SIGNAL s_port1_write : std_logic;
  SIGNAL s_port2_asel : std_logic_vector(1 DOWNTO 0);
  SIGNAL s_port2_addressSelect : std_logic_vector(g_address_size DOWNTO 0);
  SIGNAL s_registers : registerArray;
BEGIN
  WITH s_port0_addressSelect SELECT s_port0_addressSelect <=
    p_port0_address0 WHEN "00",
    p_port0_address1 WHEN "01",
    p_port0_address2 WHEN "10",
    (OTHERS => '0') WHEN OTHERS;
  WITH s_port1_isel SELECT s_port1_inputSelect <=
    p_port1_input0 WHEN "00",
    p_port1_input1 WHEN "01",
    p_port1_input2 WHEN "10",
    (OTHERS => '0') WHEN OTHERS;
  WITH s_port1_asel SELECT s_port1_addressSelect <=
    p_port1_address0 WHEN "00",
    p_port1_address1 WHEN "01",
    p_port1_address2 WHEN "10",
    (OTHERS => '0') WHEN OTHERS;
  WITH s_port2_addressSelect SELECT s_port2_addressSelect <=
    p_port2_address0 WHEN "00",
    p_port2_address1 WHEN "01",
    p_port2_address2 WHEN "10",
    (OTHERS => '0') WHEN OTHERS;
  s_port0_asel <= p_ctrl(1 DOWNTO 0);
  s_port1_isel <= p_ctrl(3 DOWNTO 2);
  s_port1_asel <= p_ctrl(5 DOWNTO 4);
  s_port1_write <= p_ctrl(6);
  s_port2_asel <= p_ctrl(8 DOWNTO 7);
  PROCESS (p_clk) BEGIN
    IF rising_edge(p_clk) THEN
      p_port0_output <= s_registers(to_integer(unsigned(s_port0_addressSelect)));
      IF s_port1_write = '1' THEN
        s_registers(to_integer(unsigned(s_port1_addressSelect))) <= s_port1_inputSelect;
      END IF;
      p_port2_output <= s_registers(to_integer(unsigned(s_port2_addressSelect)));
    END IF;
  END PROCESS;

END behavior;