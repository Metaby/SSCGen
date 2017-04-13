LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY nr_divider IS
	GENERIC (
		size : integer := 7
	);
	PORT (
		dividend	:	in std_logic_vector(size DOWNTO 0);
		divisor	:	in std_logic_vector(size DOWNTO 0);
		remain	:	out std_logic_vector(size DOWNTO 0);
		res		:	out std_logic_vector(size DOWNTO 0)
	);
  constant double_size : integer := ((size + 1) * 2) - 1;
END nr_divider;

ARCHITECTURE arch OF nr_divider IS
  COMPONENT nr_divider_slice
    GENERIC (
      size : integer := 7
    );
    PORT (
      sub       :	in	std_logic := '0';
      dividend	 :	in std_logic_vector(size DOWNTO 0);
		  divisor	  :	in std_logic_vector(size DOWNTO 0);
		  c_out		   :	out std_logic;
      remain	   :	out std_logic_vector(size DOWNTO 0)
    );
	END COMPONENT;
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
	TYPE vector_map IS ARRAY(size DOWNTO 0) OF std_logic_vector(double_size DOWNTO 0);
	SIGNAL long_divisor : std_logic_vector(double_size DOWNTO 0) := (OTHERS => '0');
	SIGNAL long_divdend : std_logic_vector(double_size DOWNTO 0) := (OTHERS => '0');
	SIGNAL carries : std_logic_vector(size DOWNTO 0);
	SIGNAL wires : vector_map;
BEGIN
  long_divisor(double_size DOWNTO size + 1) <= divisor;
	long_divdend(size + 1 DOWNTO 1) <= dividend;
	gen_d	:	FOR i IN 0 TO size GENERATE
		lsb	:	IF i = 0 GENERATE
		  nrds0   :   nr_divider_slice GENERIC MAP (size => double_size) PORT MAP ('1', long_divdend, long_divisor, carries(i), wires(i));
		  res(i) <= carries(size - i);
		END GENERATE lsb;
		msbs	:	IF i > 0 GENERATE		 
		  nrdsi   :   nr_divider_slice GENERIC MAP (size => double_size) PORT MAP (carries(i - 1), wires(i - 1), long_divisor, carries(i), wires(i));		  
		    res(i) <= carries(size - i);
		END GENERATE msbs;
 	END GENERATE gen_d;
 	remain <= wires(size)(double_size DOWNTO size + 1);
END arch;
