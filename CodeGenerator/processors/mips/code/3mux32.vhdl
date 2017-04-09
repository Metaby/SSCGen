-- Auto generated Multiplexer

LIBRARY ieee;
USE ieee.std_logic_1164.all;

    
ENTITY 3mux32 IS
  
  PORT
  (
    p_address    :  in  std_logic_vector(2 DOWNTO 0);
    p_input0     :  in  std_logic_vector(31 DOWNTO 0);
    p_input1     :  in  std_logic_vector(31 DOWNTO 0);
    p_input2     :  in  std_logic_vector(31 DOWNTO 0);
    p_input3     :  in  std_logic_vector(31 DOWNTO 0);
    p_input4     :  in  std_logic_vector(31 DOWNTO 0);
    p_input5     :  in  std_logic_vector(31 DOWNTO 0);
    p_input6     :  in  std_logic_vector(31 DOWNTO 0);
    p_input7     :  in  std_logic_vector(31 DOWNTO 0);
    p_output     :  out std_logic_vector(31 DOWNTO 0)
  );

END 3mux32;

ARCHITECTURE behavior OF 3mux32 IS
  
BEGIN
  WITH p_address SELECT p_output <=
    p_input0 WHEN "000",
    p_input1 WHEN "001",
    p_input2 WHEN "010",
    p_input3 WHEN "011",
    p_input4 WHEN "100",
    p_input5 WHEN "101",
    p_input6 WHEN "110",
    p_input7 WHEN "111";

END behavior;
