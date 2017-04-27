-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY Cache_RegisterFile IS
  GENERIC
  (
    g_wordSize : integer := -1;
    g_addressSize : integer := 31
  );
  PORT
  (
    p_port0_address0 : in std_logic_vector(g_addressSize DOWNTO 0);
    p_port0_address1 : in std_logic_vector(g_addressSize DOWNTO 0);
    p_port0_address2 : in std_logic_vector(g_addressSize DOWNTO 0);
    p_port0_addressSelect : in std_logic_vector(1 DOWNTO 0);
    p_port0_output : out std_logic_vector(g_wordSize DOWNTO 0);
    p_port1_address0 : in std_logic_vector(g_addressSize DOWNTO 0);
    p_port1_address1 : in std_logic_vector(g_addressSize DOWNTO 0);
    p_port1_address2 : in std_logic_vector(g_addressSize DOWNTO 0);
    p_port1_addressSelect : in std_logic_vector(1 DOWNTO 0);
    p_port1_output : out std_logic_vector(g_wordSize DOWNTO 0);
    p_port2_input0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_port2_input1 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_port2_input2 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_port2_input3 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_port2_input4 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_port2_address0 : in std_logic_vector(g_addressSize DOWNTO 0);
    p_port2_inputSelect : in std_logic_vector(2 DOWNTO 0);
    p_port2_write : in std_logic;
    p_clk : in std_logic
  );
END Cache_RegisterFile;

ARCHITECTURE behavior OF Cache_RegisterFile IS
  SIGNAL s_port0_addressSelect : std_logic_vector(g_addressSize DOWNTO 0);
  SIGNAL s_port1_addressSelect : std_logic_vector(g_addressSize DOWNTO 0);
  SIGNAL s_port2_inputSelect : std_logic_vector(g_wordSize DOWNTO 0);
  SIGNAL s_port2_addressSelect : std_logic_vector(g_addressSize DOWNTO 0);
  SIGNAL s_registers : registerArray;
  TYPE TYPE registerArray IS ARRAY(2147483647 DOWNTO 0) OF std_logic_vector(g_wordSize DOWNTO 0);
BEGIN
  WITH p_port0_addressSelect SELECT s_port0_addressSelect <=
    p_port0_address0 WHEN "00",
    p_port0_address1 WHEN "01",
    p_port0_address2 WHEN "10";
  WITH p_port1_addressSelect SELECT s_port1_addressSelect <=
    p_port1_address0 WHEN "00",
    p_port1_address1 WHEN "01",
    p_port1_address2 WHEN "10";
  WITH p_port2_inputSelect SELECT s_port2_inputSelect <=
    p_port2_input0 WHEN "000",
    p_port2_input1 WHEN "001",
    p_port2_input2 WHEN "010",
    p_port2_input3 WHEN "011",
    p_port2_input4 WHEN "100";
  s_port2_addressSelect <= p_port2_address0;
  PROCESS (p_clk) BEGIN
    IF rising_edge(p_clk)
      p_port0_output <= s_registers(s_port0_addressSelect)
      p_port1_output <= s_registers(s_port1_addressSelect)
      IF p_port2_write = '1'
        s_registers(s_port2_addressSelect) <= s_port2_inputSelect;
      ELSE
        s_registers(s_port2_addressSelect) <= s_registers(s_port2_addressSelect);
      END IF;
    END IF;
  END PROCESS;

END behavior;