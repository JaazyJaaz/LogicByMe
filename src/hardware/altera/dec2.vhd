---------------------------
-- 2 bit binary decoder
--
-- Author: jatkinson
---------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
library altera_mf;
use altera_mf.altera_mf_components.all;

entity dec2 is		
	port( C7, C8 : in std_logic;
		T0_OUT, T1_OUT, T2_OUT, T3_OUT : out std_logic );
end dec2;

architecture behav of dec2 is
	SIGNAL C_IN : std_logic_vector(1 downto 0) := (C8 & C7);
begin
	-- set Tx_OUT to one depending on input
	T0_OUT <= '1' WHEN (C_IN = "00") ELSE '0';
	T1_OUT <= '1' WHEN (C_IN = "01") ELSE '0';
	T2_OUT <= '1' WHEN (C_IN = "10") ELSE '0';
	T3_OUT <= '1' WHEN (C_IN = "11") ELSE '0';
end behav;

