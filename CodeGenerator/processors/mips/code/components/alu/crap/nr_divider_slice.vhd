LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY nr_divider_slice IS
	GENERIC (
		size : integer := 7
	);
	PORT (
		sub  			  :	in	std_logic := '0';
		dividend	 :	in std_logic_vector(size DOWNTO 0);
		divisor	  :	in std_logic_vector(size DOWNTO 0);
		c_out		   :	out std_logic;
		remain	   :	out std_logic_vector(size DOWNTO 0)
	);
END nr_divider_slice;

ARCHITECTURE arch OF nr_divider_slice IS
	COMPONENT carry_ripple_adder
	GENERIC (
		size : integer := 7
	);
	PORT (
		cin	:	in	std_logic;
		op1	:	in std_logic_vector(size DOWNTO 0);
		op2	:	in std_logic_vector(size DOWNTO 0);
		res	:	out std_logic_vector(size DOWNTO 0);
		car	:	out std_logic
	);
	END COMPONENT;
	SIGNAL neg_divisor : std_logic_vector(size DOWNTO 0) := (OTHERS => '0');
	SIGNAL res     : std_logic_vector(size DOWNTO 0) := (OTHERS => '0');
	SIGNAL trash   : std_logic;
BEGIN
  neg_divisor <= not divisor when (sub = '1') else divisor;
	cra	:	carry_ripple_adder GENERIC MAP (size => size) PORT MAP (sub, dividend, neg_divisor, res, trash);
	remain <= res(size - 1 DOWNTO 0) & '0';
	c_out <= not res(size);
END arch;
