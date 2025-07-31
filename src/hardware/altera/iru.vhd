---------------------------------
-- Instruction Register UPPER
--
-- Author: jatkinson
---------------------------------

--declaring libraries used
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
library altera_mf;
use altera_mf.altera_mf_components.all;

entity IRU8 is		
	port( LOAD_IRU, CLK, RESET : in std_logic;
		MDR : in std_logic_vector(7 downto 0);
		OPCODE : out std_logic_vector(7 downto 0));
end IRU8;

architecture behav of IRU8 is
begin	
	process(CLK, LOAD_IRU, MDR, RESET)
	begin
		if RESET = '1' then
			OPCODE <= x"00";
		--if CLK on FALLING edge and LOAD_IRU =1 then MDR = OPCODE
		elsif (LOAD_IRU = '1') then
			if clk'event AND clk = '1' then
				OPCODE <= MDR;
			end if;
		end if;
	end process;
end behav;


