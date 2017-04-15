-- Auto generated

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY processor IS
END processor;

ARCHITECTURE behavior OF processor IS
  SIGNAL s_Calculator_Alu_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_MicroProgrammPointer_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_Ram_Memory_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_mpPointerIncrementer_Alu_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_InstructionWord_Register_inst : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_ProgramPath_JumpLogic_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_JumpBuffer_Register_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_Cache_RegisterFile_out2 : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_ProgrammCounter_Stack_out : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_Cache_RegisterFile_out1 : std_logic_vector(31 DOWNTO 0);
  SIGNAL s_ProgramPointer_Register_pc : std_logic_vector(31 DOWNTO 0);
BEGIN

END behavior;