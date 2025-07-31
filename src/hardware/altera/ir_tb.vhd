------------------------------------
-- Instruction Register
--
-- Author: jatkinson
------------------------------------
-----------------------INPUTS
--		 LOAD_URL = SW8 
--     	  	 LOAD_URU = SW9
-- 		      MDR = SW(7..0)
--                    CLK = KEY0
--                  RESET = KEY1
--             BLANK\test = KEY2
-----------------------OUTPUTS
--ir_lower ( addr_value ) = Hex0 & HEX1 
--    ir_upper ( opcode ) = HEX2 & HEX3
------------------------------------------

--importing library
library ieee;
use ieee.std_logic_1164.all;

entity ir_tb is
	--declaring variable properties
	port( RESET, clk, LOAD_IRU, LOAD_IRL : in std_logic;	
		MDR : in std_logic_vector(7 downto 0);	
		ir_upper, ir_lower : out std_logic_vector(7 downto 0));
end entity ir_tb;

architecture behavior of ir_tb is
begin
	process(RESET, clk, MDR, LOAD_IRU, LOAD_IRL)	--Sensitivity list
	begin
		--IF statement prioritizes RESET		
		IF RESET = '0' then						--IF reset active
			ir_upper <= "00000000";				  --immediate clear of both registers
			ir_lower <= "00000000";
		ELSIF clk'event AND clk = '1' then	--IF clk rising edge trigger
			IF LOAD_IRU = '1' then				   --IF LOAD_IRU active 
				ir_upper <= MDR(7 downto 0);		   --load MDR into upper register
				IF LOAD_IRL = '1' then				--IF LOAD_IRL active 
					ir_lower <= MDR(7 downto 0);	   --load MDR into lower register
				END IF;
			ELSIF LOAD_IRL = '1' then			--if LOAD_URL load MDR into lower register
				ir_lower <= MDR(7 downto 0);
			END IF;
		END IF;			
	end process;
END behavior;	
