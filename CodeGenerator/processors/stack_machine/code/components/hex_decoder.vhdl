LIBRARY ieee ;
USE ieee.std_logic_1164.all ;

ENTITY hex_decoder IS
	PORT (
		clk	 : IN std_logic;
		bits : IN std_logic_vector(3 DOWNTO 0);
		wr   : IN std_logic;
		hex  : OUT std_logic_vector(6 DOWNTO 0)
	);
END hex_decoder;

ARCHITECTURE Behavior OF hex_decoder  IS
BEGIN
	PROCESS (bits)
	BEGIN
		IF clk'event AND clk = '1' THEN
			IF bits = "0000" THEN
				segbits <= not "0111111";
			ELSIF bits = "0001" THEN
				segbits <= not "0000110";
			ELSIF bits = "0010" THEN
				segbits <= not "1011011";
			ELSIF bits = "0011" THEN
				segbits <= not "1001111";
			ELSIF bits = "0100" THEN
				segbits <= not "1100110";
			ELSIF bits = "0101" THEN
				segbits <= not "1101101";
			ELSIF bits = "0110" THEN
				segbits <= not "1111101";
			ELSIF bits = "0111" THEN
				segbits <= not "0000111";
			ELSIF bits = "1000" THEN
				segbits <= not "1111111";		
			ELSIF bits = "1001" THEN
				segbits <= not "1101111";
			ELSIF bits = "1010" THEN
				segbits <= not "1110111";
			ELSIF bits = "1011" THEN
				segbits <= not "1111100";
			ELSIF bits = "1100" THEN
				segbits <= not "0111001";
			ELSIF bits = "1101" THEN---
				segbits <= not "1011110";
			ELSIF bits = "1110" THEN
				segbits <= not "1111001";
			ELSIF bits = "1111" THEN
				segbits <= not "1110001";
			END IF;
		END IF;
	END PROCESS;
END Behavior ;