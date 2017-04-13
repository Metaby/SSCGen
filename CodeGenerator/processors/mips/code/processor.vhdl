-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY processor IS
END processor;

ARCHITECTURE behavior OF processor IS
  s_reg_out2 : std_logic_vector(31 DOWNTO 0);
  s_alu_out : std_logic_vector(31 DOWNTO 0);
  s_pcStack_out : std_logic_vector(31 DOWNTO 0);
  s_reg_out1 : std_logic_vector(31 DOWNTO 0);
  s_mainMem_out : std_logic_vector(31 DOWNTO 0);
  s_jmpLgc_out : std_logic_vector(31 DOWNTO 0);
  s_mcPcIncr_out : std_logic_vector(31 DOWNTO 0);
  s_portA_out : std_logic_vector(31 DOWNTO 0);
  s_ir_inst : std_logic_vector(31 DOWNTO 0);
  s_pp_pc : std_logic_vector(31 DOWNTO 0);
  s_mcPc_out : std_logic_vector(31 DOWNTO 0);
  s_jmpBuffer_out : std_logic_vector(31 DOWNTO 0);
BEGIN

END behavior;