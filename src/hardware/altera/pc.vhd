----------------------------------
-- Program Counter
-- 
-- Author: jatkinson
----------------------------------
---------------INPUTS
--           clk = KEY0
--         RESET = KEY1
--    addr_value = SW(7..0)
--       LOAD_PC = SW8
--       INCR_PC = SW9
-------------OUTPUTS
-- ProgramCounter = HEX0 & HEX1
-------------SIGNALS
--				pc_out
--				pc_store
-----------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;

entity pc is
	--declaring i/o ports
	port( RESET, clk : in std_logic;
		addr_value : in std_logic_vector(7 downto 0);	  --ir_lower
		LOAD_PC, INCR_PC : in std_logic;
		ProgramCounter : out std_logic_vector(7 downto 0) --pc bus
		);
end entity pc;

architecture behavior of PC is
	signal pc_out : std_logic_vector(7 downto 0);		--the variable stored to be outputted
	signal pc_store : std_logic_vector(7 downto 0);		--	TMP VARs for increments
begin
	process(clk, RESET, addr_value, INCR_PC, LOAD_PC, pc_out, pc_store)
	begin
		IF RESET = '0' then						 --IF reset active
			pc_out <= "00000000"; 					--clear register
			pc_store <= "00000000"; 			  	--clear store
		ELSIF clk'event AND clk = '1' then	 --ELSIF clk rising edge triggered
			IF LOAD_PC = '1' then					--IF LOAD_PC active
				pc_out <= addr_value;		  			--load addr_value
				pc_store <= addr_value;
			ELSIF INCR_PC = '1' then     			--IF INCR_PC active
				pc_store <= pc_store + 1;			  --incredment the temp var by 1
				pc_out <= pc_store;					  --store temp var to pc_out
			END IF;
		END IF;
	end process;
	ProgramCounter <= pc_out;						--store pc_out to output variable
END behavior;	
	
