----------------------------------
-- Program Counter
--
-- Author: jatkinson
----------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
library altera_mf;
use altera_mf.altera_mf_components.all;


entity pc_tb is
	--declaring i/o ports
	port( RESET, clk : in std_logic;
		addr_value : in std_logic_vector(7 downto 0);	  --ir_lower
		LOAD_PC, INCR_PC : in std_logic;
		--ProgramCounter : out std_logic_vector(7 downto 0)); --pc bus
		pc_out : out std_logic_vector(7 downto 0)); --pc bus
end entity pc_tb;

architecture behavior of pc_tb is
	--signal pc_out : std_logic_vector(7 downto 0);		--	TMP VARs for increments
	signal pc_store : std_logic_vector(7 downto 0);
begin
	pc_out <= pc_store;
	process(clk, RESET, addr_value, INCR_PC, LOAD_PC, pc_store)
	begin
		IF RESET = '1' then						 --IF reset active
			--pc_out <= "00000000"; 					--clear register
			pc_store <= "00000000"; 			--clear store
		ELSIF clk'event AND clk = '1' then	--ELSIF clk rising edge triggered
			IF LOAD_PC = '1' then					--IF LOAD_PC active
				--pc_out <= addr_value;		   --load addr_value
				pc_store <= addr_value;
			ELSIF INCR_PC = '1' then     		--IF INCR_PC active
				--pc_out <= pc_store + 1;			--increment + 1 PER KEY0 ACTIVE
				pc_store <= std_logic_vector(unsigned(pc_store) + 1);
				
			END IF;
		END IF;
	end process;
END behavior;	
	