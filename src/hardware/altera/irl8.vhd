----------------------------------
-- Instruction Register LOWER
--
-- Author: jatkinson
----------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
library altera_mf;
use altera_mf.altera_mf_components.all;

entity IRL8 is		
	port( LOAD_IRL, CLK, RESET : in std_logic;
		MDR : in std_logic_vector(7 downto 0);
		ADDR_VALUE : out std_logic_vector(7 downto 0));
end IRL8;

architecture behav of IRL8 is
begin	
	process(CLK, LOAD_IRL, MDR, RESET)
	begin
		if RESET = '1' then
			ADDR_VALUE <= x"00";
		--if CLK on FALLING edge and LOAD_IRU =1 then MDR = OPCODE
		elsif (LOAD_IRL = '1') then
			if clk'event AND clk = '1' then
				ADDR_VALUE <= MDR;
			end if;
		end if;
	end process;
end behav;
