LIBRARY ieee ;
USE ieee.std_logic_1164.all ;

ENTITY hex_decoder IS
	PORT (
		p_clk	 : IN std_logic;
		p_bits : IN std_logic_vector(3 DOWNTO 0);
		p_wr   : IN std_logic;
		p_hex  : OUT std_logic_vector(6 DOWNTO 0)
	);
END hex_decoder;

ARCHITECTURE Behavior OF hex_decoder  IS
SIGNAL s_out : std_logic_vector(6 DOWNTO 0);
BEGIN
	p_hex <= s_out;
	PROCESS (p_clk)
	BEGIN
		IF rising_edge(p_clk) AND p_wr = '1' THEN
			IF p_bits = "0000" THEN
				s_out <= not "0111111";
			ELSIF p_bits = "0001" THEN
				s_out <= not "0000110";
			ELSIF p_bits = "0010" THEN
				s_out <= not "1011011";
			ELSIF p_bits = "0011" THEN
				s_out <= not "1001111";
			ELSIF p_bits = "0100" THEN
				s_out <= not "1100110";
			ELSIF p_bits = "0101" THEN
				s_out <= not "1101101";
			ELSIF p_bits = "0110" THEN
				s_out <= not "1111101";
			ELSIF p_bits = "0111" THEN
				s_out <= not "0000111";
			ELSIF p_bits = "1000" THEN
				s_out <= not "1111111";		
			ELSIF p_bits = "1001" THEN
				s_out <= not "1101111";
			ELSIF p_bits = "1010" THEN
				s_out <= not "1110111";
			ELSIF p_bits = "1011" THEN
				s_out <= not "1111100";
			ELSIF p_bits = "1100" THEN
				s_out <= not "0111001";
			ELSIF p_bits = "1101" THEN
				s_out <= not "1011110";
			ELSIF p_bits = "1110" THEN
				s_out <= not "1111001";
			ELSE
				s_out <= not "1110001";
			END IF;
		ELSE
			s_out <= s_out;
		END IF;
	END PROCESS;
END Behavior ;