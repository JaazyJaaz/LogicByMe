--------------------------
-- Accumulator
--
-- Author: jatkinson
--------------------------

library ieee;
use ieee.std_logic_1164.all;

entity accu_tb is
	port( z : in std_logic_vector(7 downto 0);
		LOAD_AC, clk : in std_logic;
		AC : out std_logic_vector(7 downto 0));
end entity accu_tb;

architecture behavior of accu_tb is
begin
	process(z, clk, LOAD_AC)   --sensitivity list
	begin
		IF LOAD_AC = '1' then	       --ELSIF LOAD_AC (SW8) is on 
			IF clk'event AND clk = '1' then	 --IF rising edge of clk
				AC <= z;		  			 --store z bus data into the AC register
			END IF;
		END IF;
	end process;
END behavior;	
