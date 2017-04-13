-- Datei:	tree_comparator.vhd
-- Autor:	Max Brand
-- Datum: 14.07.2016

-- Code-Konventionen
-- Variablenbenennung
-- g_<name> entspricht Generic
-- p_<name> entspricht Port
-- c_<name> entspricht Konstante
-- s_<name> entspricht Signal

-- Pakete
LIBRARY ieee;
USE ieee.std_logic_1164.all;

-- Entity Port und Generic
ENTITY alu IS
	GENERIC (
		g_size	:	integer	:=	7
	);
	PORT (
		p_op_1		:	in  std_logic_vector(g_size DOWNTO 0);		-- Operand 1
		p_op_2		:	in  std_logic_vector(g_size DOWNTO 0);		-- Operand 2
		p_cmd			:	in  std_logic_vector(4 DOWNTO 0);			-- Operation
		p_ovflw		:	out std_logic;										-- Overflow Bit ('1' falls Overflow)
		p_cmp_f		:	out std_logic;										-- Compare Bit ('1' falls Vergleich richtig)
		p_result_lo	:	out std_logic_vector(g_size DOWNTO 0);		-- Ergebnis, unteres Byte
		p_result_hi	:	out std_logic_vector(g_size DOWNTO 0)		-- Ergebnis, oberes Byte
	);
END alu;

-- Entity Architektur
ARCHITECTURE arch OF alu IS
	-- Komponenten
	COMPONENT carry_select_adder
	GENERIC (
		g_block_size	:	integer := 3;
		g_blocks			:	integer := 1
	);
	PORT (
		p_sgnd	:	in	 std_logic;
		p_sub		:	in  std_logic;
		p_op_1	:	in  std_logic_vector((g_block_size + 1) * (g_blocks + 1) - 1 DOWNTO 0);
		p_op_2	:	in  std_logic_vector((g_block_size + 1) * (g_blocks + 1) - 1 DOWNTO 0);
		p_result	:	out std_logic_vector((g_block_size + 1) * (g_blocks + 1) - 1 DOWNTO 0);
		p_ovflw	:	out std_logic
	);
	END COMPONENT;
	COMPONENT four_quadrant_multiplier
	GENERIC (
		g_size : integer := 7
	);
	PORT (
		p_sgnd		:	in  std_logic;
		p_op_1		:	in	 std_logic_vector(g_size DOWNTO 0);
		p_op_2		:	in  std_logic_vector(g_size DOWNTO 0);
		p_add			:	in  std_logic_vector(g_size DOWNTO 0);
		p_result_lo	:	out std_logic_vector(g_size DOWNTO 0);
		p_result_hi	:	out std_logic_vector(g_size DOWNTO 0)
	);
	END COMPONENT;
	COMPONENT divider
	GENERIC (
		g_size : integer := 7
	);
	PORT (
		p_sgnd		:	in	 std_logic;
		p_dividend	:	in  std_logic_vector(g_size DOWNTO 0);
		p_divisor	:	in  std_logic_vector(g_size DOWNTO 0);
		p_remain		:	out std_logic_vector(g_size DOWNTO 0);
		p_result		:	out std_logic_vector(g_size DOWNTO 0)
	);
	END COMPONENT;
	COMPONENT barrel_shifter
	GENERIC (
		g_size : integer := 7
	);
	PORT (
		p_cmd	   :	in	 std_logic_vector(1 DOWNTO 0);
		p_arith	:	in	 std_logic;
		p_rotate	:	in  std_logic;
		p_op_1	:	in  std_logic_vector(g_size DOWNTO 0);
		p_op_2	:	in  std_logic_vector(g_size DOWNTO 0);
		p_result	:	out std_logic_vector(g_size DOWNTO 0)
	);
	END COMPONENT;
	COMPONENT word_comparator
	GENERIC (
		g_size : integer := 7
	);
	PORT (
		p_op_1	:	in  std_logic_vector(g_size DOWNTO 0);
		p_op_2	:	in  std_logic_vector(g_size DOWNTO 0);
		p_cmd		:	in  std_logic_vector(2 DOWNTO 0);
		p_sgnd	:	in  std_logic;
		p_result	:	out std_logic
	);
	END COMPONENT;
	COMPONENT bit_manipulator
	GENERIC (
		g_size : integer := 7
	);
	PORT (
		p_op_1	:	in std_logic_vector(g_size DOWNTO 0);
		p_op_2	:	in std_logic_vector(g_size DOWNTO 0);
		p_cmd		:	in std_logic_vector(1 DOWNTO 0);
		p_result	:	out std_logic_vector(g_size DOWNTO 0)
	);
	END COMPONENT;
	-- Signale
	SIGNAL s_cmp_f			: std_logic;
	SIGNAL s_ovflw			: std_logic;
	SIGNAL s_cmd 			: std_logic_vector(7 DOWNTO 0);
	SIGNAL s_add_res_lo	: std_logic_vector(g_size DOWNTO 0);
	SIGNAL s_mul_res_lo	: std_logic_vector(g_size DOWNTO 0);
	SIGNAL s_mul_res_hi	: std_logic_vector(g_size DOWNTO 0);
	SIGNAL s_div_res_lo	: std_logic_vector(g_size DOWNTO 0);
	SIGNAL s_div_res_hi	: std_logic_vector(g_size DOWNTO 0);
	SIGNAL s_sft_res_lo	: std_logic_vector(g_size DOWNTO 0);
	SIGNAL s_bop_res_lo	: std_logic_vector(g_size DOWNTO 0);
-- Verhalten
BEGIN
	-- Command Mapping
	--
	--		   DONT CHANGE				Edit for own IS
	--			V			V					V		V
	s_cmd <= "10000000" when p_cmd = "00000" else	-- add
				"00000000" when p_cmd = "00001" else	-- add_u
				"10001000" when p_cmd = "00010" else	-- sub
				"00001000" when p_cmd = "00011" else	-- sub_u
				"10000001" when p_cmd = "00100" else	-- mul
				"00000001" when p_cmd = "00101" else	-- mul_u
				"10000010" when p_cmd = "00110" else	-- div
				"00000010" when p_cmd = "00111" else	-- div_u
				"01010011" when p_cmd = "01000" else	-- rr
				"01001011" when p_cmd = "01001" else	-- rl
				"00010011" when p_cmd = "01010" else	-- srl
				"00001011" when p_cmd = "01011" else	-- sll
				"00110011" when p_cmd = "01100" else	-- sra
				"00101011" when p_cmd = "01101" else	-- sla
				"00000100" when p_cmd = "01110" else	-- and
				"00001100" when p_cmd = "01111" else	-- or
				"00010100" when p_cmd = "10000" else	-- xor
				"00011100" when p_cmd = "10001" else	-- not
				"10000101" when p_cmd = "10010" else	-- gt
				"00000101" when p_cmd = "10011" else	-- gt_u
				"10001101" when p_cmd = "10100" else	-- lt
				"00001101" when p_cmd = "10101" else	-- lt_u
				"10010101" when p_cmd = "10110" else	-- geq
				"00010101" when p_cmd = "10111" else	-- geq_u
				"10011101" when p_cmd = "11000" else	-- leq
				"00011101" when p_cmd = "11001" else	-- leq_u
				"10100101" when p_cmd = "11010" else	-- eq
				"00100101" when p_cmd = "11011" else	-- eq_u
				"11111111";
	-- Komponenten
	csa	:	carry_select_adder GENERIC MAP (g_block_size => 3, g_blocks => ((g_size + 1) / 4) - 1) PORT MAP (s_cmd(7), s_cmd(3), p_op_1, p_op_2, s_add_res_lo, s_ovflw);
	mul	:	four_quadrant_multiplier GENERIC MAP (g_size => g_size) PORT MAP (s_cmd(7), p_op_1, p_op_2, (OTHERS => '0'), s_mul_res_lo, s_mul_res_hi);
	div	:	divider GENERIC MAP (g_size => g_size) PORT MAP (s_cmd(7), p_op_1, p_op_2, s_div_res_hi, s_div_res_lo);
	sft	:	barrel_shifter GENERIC MAP (g_size => g_size) PORT MAP (s_cmd(4 DOWNTO 3), s_cmd(5), s_cmd(6), p_op_1, p_op_2, s_sft_res_lo);
	cmp	:	word_comparator GENERIC MAP (g_size => g_size) PORT MAP (p_op_1, p_op_2, s_cmd(5 DOWNTO 3), s_cmd(7), s_cmp_f);
	bop	:	bit_manipulator GENERIC MAP (g_size => g_size) PORT MAP (p_op_1, p_op_2, s_cmd(4 DOWNTO 3), s_bop_res_lo);
	-- Ausgabe
	p_result_lo <= s_add_res_lo when s_cmd(2 DOWNTO 0) = "000" else
						s_mul_res_lo when s_cmd(2 DOWNTO 0) = "001" else
						s_div_res_lo when s_cmd(2 DOWNTO 0) = "010" else						
						s_sft_res_lo when s_cmd(2 DOWNTO 0) = "011" else						
						s_bop_res_lo when s_cmd(2 DOWNTO 0) = "100" else
						(OTHERS => '0');
	p_result_hi <= s_mul_res_hi when s_cmd(2 DOWNTO 0) = "001" else
						s_div_res_hi when s_cmd(2 DOWNTO 0) = "010" else
						(OTHERS => '0');
	p_cmp_f <= s_cmp_f when s_cmd(2 DOWNTO 0) = "101" else '0';
	p_ovflw <= s_ovflw when s_cmd(2 DOWNTO 0) = "000" else '0';
END arch;