----------------------------------------
-- Implements the instruction set
-- for the uP3 microprocessor
--
-- Author: jatkinson
--
-----------INPUTS
--      blank : SW8
--       test : SW9
--   DataEntry: switch[7..0]
--		LOAD : KEY[3..0]
--   LOAD_MDR : KEY 3
--    LOAD_AC : KEY 2
-- LOAD_VALUE : KEY 1
--LOAD_OPCODE : KEY0
----------OUTPUTS
--  STORE_MEM :LEDR0
--    LOAD_PC : LEDR1
--   value_OUT : LEDR[7..0]
--	      	Z : LEDG[7..0]
--			  AC : HEX0, HEX1
--		  	 MDR : HEX2, HEX3
--	opcode_out : -
-----------------------------------------
------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
Use ieee.std_logic_arith.all;
Use ieee.std_logic_unsigned.all;
library altera_mf;
use altera_mf.altera_mf_components.all;

--USE WORK.ALU_LIBRARY.ALL;

--Entity
entity ALU is
	--generic (  N : INTEGER := 7 ); --JUMP, STORE : STD_LOGIC;
--		-- put port list here, use type SIGNED for the data busses
--		--signal FLAG_CARRY : std_logic;
--		--signal Z_LAST : STD_LOGIC;
		
	port(Z, VALUE_OUT, AC, MDR : out std_logic_vector(7 downto 0);--HEX[3..0],LEDG[7..0],LEDR[7..0]
		LOAD_PC, STORE_MEM : out std_logic;	-->LEDR[9..8]
		DataEntry : in std_logic_vector(7 downto 0);-->SW[7..0]
		BLANK,TEST : in std_logic;-->KEY[3..0],SW[9..8]
		LOAD : in std_logic_vector(3 downto 0)
			-->IDEA TO PUT ALL LOAD INTO ONE VARIABLE
			--LOAD_OPCODE,LOAD_VALUE,LOAD_AC,LOAD_MDR,
		);
end ALU;

architecture behav of alu is
	-- XTRA FOR ADDING / FLAGS
	type bus_arr is array (7 downto 0)
			of std_ulogic; --range -250 to 255;
	type bus_var is range -250 to 255;
	signal mdr_bus : bus_arr;
	-- REGISTER VARIABLES
	SIGNAL AC_REG, MDR_REG, VALUE_REG, OPCODE_REG : std_logic_vector(7 downto 0);
	-- ARITHMETHIC VARIABLES
	SIGNAL AC_INT,MDR_INT,VALUE_INT : integer;
	SIGNAL AC_SIGNED : std_logic_vector(7 downto 0);
	-- INSTRUCTION VARIABLES
	SIGNAL NOP, CLR : std_logic_vector(7 downto 0);
	SIGNAL ADD, ADDI, SUBT, SUBTI : std_logic_vector(11 downto 0);
	SIGNAL NEG : std_logic_vector(7 DOWNTO 0);
	SIGNAL JNEG, JPOSZ, JZERO, JNZER : STD_LOGIC;
	
	SIGNAL JUMP, STORE : STD_LOGIC;
	--SIGNAL S : unsigned(7 DOWNTO 0);
	ALIAS LOAD_OPCODE : std_logic IS LOAD(0);
	ALIAS LOAD_VALUE : std_logic IS LOAD(1);
	ALIAS LOAD_AC : std_logic IS LOAD(2);
	ALIAS LOAD_MDR : std_logic IS LOAD(3);

		
begin
--		generic map (STORE, JUMP)
--		port map (STORE_MEM, LOAD_PC);
--		generic map (NOP,LOAD,LOADI,STORE,CLR,ADD,ADDI,SUBT,AUBTI,NEG,NOT,AND,OR,XOR,SHL,SHR,JUMP,JNEG,JPOSZ,JZERO,JNZER)
--		port map (STORE_MEM, LOAD_PC);
		--JUMP <= '1' WHEN (STORE = '1') ELSE '0';
		
		LOAD_PC <= '1' WHEN (JUMP = '1') ELSE '0';
		STORE_MEM <= '1' WHEN (STORE = '1') ELSE '0';
		AC_REG <= DataEntry WHEN (LOAD_AC = '1') ELSE UNAFFECTED;
		MDR_REG <= DataEntry WHEN (LOAD_MDR = '1') ELSE UNAFFECTED;
		VALUE_REG <= DataEntry WHEN (LOAD_VALUE = '1') ELSE UNAFFECTED;
		OPCODE_REG <= DataEntry WHEN (LOAD_OPCODE = '1') ELSE UNAFFECTED;
		
		--JUMP <= '1' WHEN (STORE_PC = '1') ELSE '0';
		AC <= AC_REG;
		MDR <= MDR_REG;
		VALUE_OUT <= VALUE_REG;
		
		-- CREATING THE INSTRUCTION SET
		NOP <= unaffected;
		CLR <= x"00";
		JZERO <= '1' WHEN (AC_REG = CLR) ELSE '0';		--IF AC EQUALS 00 RETURN 1
		JNZER <= '1' WHEN (AC_REG /= CLR) ELSE '0';	--IF AC != 00 THEN RETURN 1
		
		-- DECLARING INTEGERS
--		S <= SIGNED(MDR_REG);
		--MDR_int <= to_integer(bus_arr(mdr_reg));
--		VALUE_INT <= signed'(VALUE_REG);
		AC_SIGNED <= AC_REG;
		
		--if any loads are active it will update the busses
		--AC <= DataEntry when (LOAD(2) = '1') else 'X';
		--VALUE_OUT <= DataEntry when (LOAD(1) = '1') else 'x';
		--OPCODE <= DataEntry when (LOAD(0) = '1') else 'x';
--		--only instruction to activate STORE_MEM
		--STORE_MEM <= '1' when (OPCODE = x"03") else '0';
		
		--JNEG <= '1' when (AC(7) AND '1');
--		JPOSZ <= '1' when  ((AC_SIGNED(7) ='0') OR JZERO);
--		JZERO <= '1' when (AC = x"00");
--		JNZER <= '1' WHEN (AC = (NOT x"00"));
	process(LOAD_MDR, LOAD_VALUE, LOAD_AC, LOAD_OPCODE, DataEntry, blank, test, OPCODE_REG, AC_REG, AC_INT, AC_SIGNED, VALUE_REG, VALUE_INT, MDR_REG, MDR_INT, NOP, CLR, ADD, ADDI, SUBT, SUBTI, NEG, JNEG, JPOSZ, JZERO, JNZER ) 
	-- SHL, SHR,
	begin
		---------------------------------
--		IF (JUMP = '1') THEN 
--			LOAD_PC <= '1';
--		ELSE 
--			LOAD_PC <= '0';
--		END IF;
--		
--		-- LOAD INPUTS TO REGISTERS--
--		IF (LOAD_MDR = '1') THEN
--	  	  MDR_REG <= DataEntry;
--		END IF;
--		IF LOAD_VALUE = '1' then
--		  VALUE_REG <= DataEntry;
--		END IF;
--		IF LOAD_OPCODE = '1' then
--		  OPCODE_REG <= DataEntry;
--		END IF;
--		IF LOAD_AC = '1' then
--		  AC_REG <= DataEntry;
--		END IF;
		--------------------------------
		
		
		-- CREATING THE INSTRUCTION SET
		NOP <= unaffected;
		CLR <= x"00";
		--ADD <= unsigned'("0000" & AC_INT + "0000" & MDR_INT);
		--ADDI <= "0000" & unsigned'(AC_INT + VALUE_INT);
		--SUBT <= "0000" & unsigned'(AC_INT - MDR_INT);
		--SUBTI <= "0000" & unsigned'(AC_INT - VALUE_INT);
		--NEG <= unsigned'( x"00" - MDR_REG);
		--SHL <= AC rol to_integer(unsigned'(VALUE_OUT[2..0]));
		--SHR <= AC ror to_integer(unsigned'(VALUE_OUT(2..0)));

		------------------------------------
		--CASE STATEMENT
		case OPCODE_REG is
			when x"00" => Z <= NOP;
			when x"01" => Z <= MDR_REG;
			when x"02" => Z <= VALUE_REG;
			when x"04" => Z <= CLR;
			--when x"05" => Z <= ADD;
			--when x"06" => Z <= ADDI;
			--when x"07" => Z <= SUBT;
			--when x"08" => Z <= SUBTI;
			--when x"09" => Z <= NEG;
			when x"0A" => Z <= NOT MDR_REG;
			when x"0B" => Z <= (AC_REG AND MDR_REG);
			when x"0C" => Z <= (AC_REG OR MDR_REG);
			when x"0D" => Z <= (AC_REG XOR MDR_REG);
			--when x"0E" => Z <= SHL;
			--when x"0F" => Z <= SHR;
			when x"11" => JUMP <= JNEG;
			when x"12" => JUMP <= JPOSZ;
			when x"13" => JUMP <= JZERO;
			when x"14" => JUMP <= JNZER;
			when others => null;						 --Z <= 'X'; Z <= 'X';
		end case;
		
		
	--	--update the Z bus
	--	with OPCODE select
	--		Z <= MDR when X"01",
	--			  VALUE_OUT when X"02",
	--			  X"00" when X"04",
	--			  ADD when X"05",
	--			  ADDI when X"06",
	--			  SUBT when X"07",
	--			  SUBTIwhen X"08",
	--			  NEG when X"09",
	--			  (NOT MDR) when X"0A",
	--			  (AC AND MDR) when X"0B",
	--			  (AC OR MDR) when X"0C",
	--			  (AC XOR MDR) when X"0D",
	--			  SHL when X"0E",
	--			  SHR when X"0F",
	--			  unaffected when OTHERS;		  
	--	
	--	with OPCODE select
	--		LOAD_PC <= JNEG when X"11",
	--					  JPOSZ when X"12",
	--					  JZERO when X"13",
	--					  JNZER when X"14",
		
	--MDR'left returns first or letmost bit
	--MDR'right
		--function "+" (AC_INT, MDR_INT : SIGNED) return UNSIGNED;
		--assert LOAD_PC 
		--Z_LAST = Z'LAST_VALUE;
		--LOAD_PC <= '1' WHEN Z_LAST /= z;

	
	end process;
end behav;



--package cpu_lib is
--	type t_shift is (shftpass, shl, shr, rotl, rotr);
--	subtype t_alu is unsigned(3 downto 0);
--	constant alupass : unsigned(3 downto 0) := “0000”;
--	constant andOp : unsigned(3 downto 0) := “0001”;
--	constant orOp : unsigned(3 downto 0) := “0010”;
--	constant notOp : unsigned(3 downto 0) := “0011”;
--	constant xorOp : unsigned(3 downto 0) := “0100”;
--	constant plus : unsigned(3 downto 0) := “0101”;
--	constant alusub : unsigned(3 downto 0) := “0110”;
--	constant inc : unsigned(3 downto 0) := “0111”;
--	constant dec : unsigned(3 downto 0) := “1000”;
--	constant zero : unsigned(3 downto 0) := “1001”;
--	type t_comp is (eq, neq, gt, gte, lt, lte);
--	subtype t_reg is std_logic_vector(2 downto 0);
--	type state is (reset1, reset2, reset3, reset4, reset5, reset6, execute, nop, load, store, move,
--	load2, load3, load4, store2, store3, store4, move2, move3, move4,incPc, incPc2,incPc3, incPc4, incPc5, incPc6, loadPc,
--	loadPc2,loadPc3, loadPc4, bgtI2, bgtI3,bgtI4, bgtI5, bgtI6, bgtI7,bgtI8, bgtI9,bgtI10, braI2, braI3, braI4, braI5,
--	braI6,loadI2,loadI3, loadI4, loadI5, loadI6,inc2, inc3, inc4);
--	
--	
--	subtype bit16 is std_logic_vector(15 downto 0);
--end cpu_lib;
