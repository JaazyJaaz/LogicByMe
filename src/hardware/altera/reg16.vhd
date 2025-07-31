---------------------------
-- 5-to-1 16 bit bus MUX
--
-- Author: jatkinson
---------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
library altera_mf;
use altera_mf.altera_mf_components.all;

entity reg16 is		
	port( T_IN, CLK : in std_logic;
		ALU_IN : in std_logic_vector(15 downto 0);
		REG_OUT : out std_logic_vector(15 downto 0) );
end entity reg16;

architecture behav of reg16 is
begin			
	process(CLK, T_IN, ALU_IN)
	begin
		--if clk on rising edge and T_IN =1 then ALU_IN = REG_OUT
		if clk'event AND clk = '1' then
			if (T_IN = '1') then
				REG_OUT <= ALU_IN;
			end if;
		end if;
	end process;
end behav;
