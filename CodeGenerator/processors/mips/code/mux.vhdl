-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY mux IS
  PORT
  (
    p_address    :  in  std_logic_vector(1 DOWNTO 0);
    p_input0     :  in  std_logic_vector(7 DOWNTO 0);
    p_input1     :  in  std_logic_vector(7 DOWNTO 0);
    p_input2     :  in  std_logic_vector(7 DOWNTO 0);
    p_input3     :  in  std_logic_vector(7 DOWNTO 0);
    p_output     :  out std_logic_vector(7 DOWNTO 0)
  );
END mux;

ARCHITECTURE behavior OF mux IS
BEGIN
  WITH p_address SELECT p_output <=
    p_input0 WHEN "00",
    p_input1 WHEN "01",
    p_input2 WHEN "10",
    p_input3 WHEN "11";
END behavior;