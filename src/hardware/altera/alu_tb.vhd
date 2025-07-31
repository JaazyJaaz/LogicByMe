------------------------------------------------
-- ALU
-- Implements the instruction set
-- for the uP3 microprocessor 
--
-- Author: jatkinson
------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
library altera_mf;
use altera_mf.altera_mf_components.all;

--Entity
entity ALU_tb is
	port( AC_IN, MDR_IN, VALUE_IN, OPCODE_IN : IN std_logic_vector(7 downto 0);
		    Z_OUT : out std_logic_vector(7 downto 0);	
			--LOAD_PC, STORE_MEM : out std_logic
			ZFLG, NFLG : BUFFER std_logic);
end ALU_tb;

architecture behav of ALU_tb is
	SIGNAL  OPCODE_REG, VALUE_OUT, AC_out, MDR_out : std_logic_vector(7 downto 0);
	SIGNAL AC_SIGNED : signed(7 downto 0);
	-- INSTRUCTION VARIABLES
	SIGNAL Z, NOP,  L0AD, LOADI, CLR, ADD, SUBT, NEG, N0T, ANDD, ORR, X0R, SHL, SHR, jump_instr : std_logic_vector(7 downto 0);
	SIGNAL STORE, JUMP, JNEG, JPOSZ, JZERO, JNZER : std_logic;
	signal ADDI, SUBTI : std_logic_vector(8 downto 0);
	signal MEM_STORE, PC_LOAD : std_logic;
begin
		--LOADING TMP REGISTER TO OUTPUTS
		Z_OUT <= Z;
		AC_OUT <= AC_IN;
		MDR_OUT <= MDR_IN;
		VALUE_OUT <= VALUE_IN;
		OPCODE_REG <= OPCODE_IN;
		AC_SIGNED <= signed(AC_IN);
--		STORE_MEM <= MEM_STORE; 
--		LOAD_PC <= PC_LOAD ;
		ZFLG <= '1' WHEN (AC_IN = x"00") ELSE '0';	
		NFLG <= '1' WHEN (AC_SIGNED(7) = '1') ELSE '0';	
		---------------------------------
		-- CREATING THE INSTRUCTION SET
		NOP <= Z;													   --00
		L0AD <= MDR_IN;															--01
		LOADI <= VALUE_IN;														--02
		STORE <= '1';																--03
		CLR <= x"00";																--04
		ADD <= std_logic_vector(to_signed(to_integer(signed(AC_IN)) + to_integer(signed(MDR_IN)), 8));
		ADDI <= std_logic_vector(('0' & UNsigned(AC_IN)) + ('0' & UNsigned(VALUE_IN)));
		SUBT <= std_logic_vector(to_signed(to_integer(signed(AC_IN)) - to_integer(signed(MDR_IN)),8));
		SUBTI <= std_logic_vector(('0' & UNsigned(AC_IN)) - ('0' & UNsigned(VALUE_IN))); 
		NEG <= std_logic_vector(to_signed(to_integer(signed(CLR)) - to_integer(signed(MDR_IN)), 8));
		N0T <= NOT MDR_IN;														--0A
		ANDD <= STD_LOGIC_VECTOR(AC_IN AND MDR_IN);
		ORR <= (AC_IN OR MDR_IN);
		X0R <= (AC_IN XOR MDR_IN);												--0D
		SHL <= std_logic_vector( unsigned(AC_IN) sll to_integer(unsigned(VALUE_IN(2 downto 0))));
		SHR <= std_logic_vector( unsigned(AC_IN) srl to_integer(unsigned(value_IN(2 downto 0))));
		JNEG <= '1' WHEN (AC_SIGNED(7) = '1') ELSE '0';					--11	BELOW ZERO
		JPOSZ <= '1' WHEN (JNEG='0') ELSE '0';								--12	>=0
		JZERO <= '1' WHEN (AC_IN = CLR) ELSE '0';							--13	IF AC EQUALS 00 RETURN 1
		JNZER <= '1' WHEN (AC_IN /= CLR) ELSE '0';						--14	IF AC != 00 THEN RETURN 1
		
--		-- EXECUTING THE INSTRUCTION SET
		MEM_STORE <= STORE when (OPCODE_REG = X"03") else '0';
		--JUMP <= '1' WHEN (OPCODE_IN = x"10") ELSE '0';
		
		--executing the jump instruction
		jump_instr <= VALUE_IN when (JUMP = '1') else NOP;
		with opcode_REG select
			JUMP <= '1' when x"10",
					NFLG when x"11",
					NOT NFLG when x"12",
					ZFLG when x"13",
					NOT ZFLG when x"14",
					'0' when others;
		
		-- OPCODE AFFECTING Z
		with OPCODE_REG select
			Z <= NOP when x"00", 
				  L0AD when x"01",
				  LOADI when x"02",
				  CLR when x"04",
				  ADD when x"05",
				  ADDI(7 downto 0) when x"06",
				  SUBT when x"07",
				  SUBTI(7 downto 0) when x"08",
				  NEG when x"09",
				  N0T when x"0A",
				  ANDD when x"0B",
				  ORR when x"0C",
				  X0R when x"0D",
				  SHL when x"0E",
				  SHR when x"0F",
				  jump_instr when x"10"|x"11"|x"12"|x"13"|x"14",
				  NOP when OTHERS; 
		-- OPCODE AFFECTING LOAD_PC
		with OPCODE_REG select
			PC_LOAD <= JUMP when x"10",
						  JNEG when x"11",
						  JPOSZ when x"12",
						  JZERO when x"13",
						  JNZER when x"14",
						   '0' when OTHERS;
end behav;

