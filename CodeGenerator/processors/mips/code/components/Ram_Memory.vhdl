-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY Ram_Memory IS
  GENERIC
  (
    g_wordSize : integer := -1;
    g_addressSize : integer := 31
  );
  PORT
  (
    p_port0_input0 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_port0_input1 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_port0_input2 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_port0_input3 : in std_logic_vector(g_wordSize DOWNTO 0);
    p_port0_address0 : in std_logic_vector(g_addressSize DOWNTO 0);
    p_port0_address1 : in std_logic_vector(g_addressSize DOWNTO 0);
    p_port0_inputSelect : in std_logic_vector(1 DOWNTO 0);
    p_port0_addressSelect : in std_logic;
    p_port0_write : in std_logic;
    p_port1_address0 : in std_logic_vector(g_addressSize DOWNTO 0);
    p_port1_address1 : in std_logic_vector(g_addressSize DOWNTO 0);
    p_port1_addressSelect : in std_logic;
    p_port1_output : out std_logic_vector(g_wordSize DOWNTO 0);
    p_clk : in std_logic
  );
END Ram_Memory;

ARCHITECTURE behavior OF Ram_Memory IS
  SIGNAL s_port0_inputSelect : std_logic_vector(g_addressSize DOWNTO 0);
  SIGNAL s_port0_addressSelect : std_logic_vector(g_addressSize DOWNTO 0);
  SIGNAL s_port1_addressSelect : std_logic_vector(g_addressSize DOWNTO 0);
BEGIN
  WITH p_port0_inputSelect SELECT s_port0_inputSelect <=
    p_port0_input0 WHEN "00",
    p_port0_input1 WHEN "01",
    p_port0_input2 WHEN "10",
    p_port0_input3 WHEN "11";
  WITH p_port0_addressSelect SELECT s_port0_addressSelect <=
    p_port0_address0 WHEN '0',
    p_port0_address1 WHEN '1';
  WITH p_port1_addressSelect SELECT s_port1_addressSelect <=
    p_port1_address0 WHEN '0',
    p_port1_address1 WHEN '1';
  -- this vhdl-component is only a interface for a custom memory --
  -- please insert memory control logic here --
  -- for a simple on-chip memory use RegisterFile instead --
END behavior;