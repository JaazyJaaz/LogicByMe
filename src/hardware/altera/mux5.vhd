----------------------------
-- 5-to-1 16 bit bus MUX
--
-- Author: jatkinson
----------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
library altera_mf;
use altera_mf.altera_mf_components.all;

entity mux5 is		
	port( C1, C2, C3, C4, C5, C6 : in std_logic;
		REG0, REG1, REG2, REG3, Din : in std_logic_vector(15 downto 0);
		M1_OUT, M2_OUT : out std_logic_vector(15 downto 0) 
		);
end entity mux5;

architecture behav of mux5 is
	signal c_in : std_logic_vector(3 downto 0) := (C5 & C4 & C2 & C1 );
	alias C_M1 : std_logic_vector(1 downto 0) is c_in(1 downto 0);
	alias C_M2 : std_logic_vector(1 downto 0) is c_in(3 downto 2);
begin			
	process(REG0, REG1, REG2, REG3, C3, C6, C_M1, C_M2, Din)
	begin
		-- Determining the input/output for M1_OUT/M2_OUT
		-- Highest priority
		if ((C3 = '1') OR (C6 = '1')) then
			if (c3 = '1') then
				M1_OUT <= Din;
			else
				case C_M1 is
					when "00" => M1_OUT <= REG0;	
					when "01" => M1_OUT <= REG1;
					when "10" => M1_OUT <= REG2;
					when "11" => M1_OUT <= REG3;
				end case;
			end if;
			if (C6 = '1') then
				M2_OUT <= Din;
			else
				case C_M2 is
					when "00" => M2_OUT <= REG0;
					when "01" => M2_OUT <= REG1;
					when "10" => M2_OUT <= REG2;
					when "11" => M2_OUT <= REG3;
				end case;
			end if;
		-- if C3 or C6 /=1 then
		else
			case C_M1 is
				when "00" => M1_OUT <= REG0;	
				when "01" => M1_OUT <= REG1;
				when "10" => M1_OUT <= REG2;
				when "11" => M1_OUT <= REG3;
			end case;
			case C_M2 is
				when "00" => M2_OUT <= REG0;
				when "01" => M2_OUT <= REG1;
				when "10" => M2_OUT <= REG2;
				when "11" => M2_OUT <= REG3;
			end case;
		end if;
	end process;
end behav;
