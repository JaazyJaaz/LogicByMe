-----------------------------------
-- Multiplexer - Fetch
--
-- Author: jatkinson
-----------------------------------

library ieee;
use ieee.std_logic_1164.all;

entity fetch_tb is
	--declaring variable properties
	port( FETCH : in std_logic;	
		addr_value, pc : in std_logic_vector(7 downto 0);	
		address : out std_logic_vector(7 downto 0));
end entity fetch_tb;

architecture behavior of fetch_tb is
begin
	address <= PC when (FETCH = '1') else addr_value;		
END behavior;	
