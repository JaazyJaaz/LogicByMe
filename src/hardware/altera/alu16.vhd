-----------------------
-- 16-bit ALU
--
-- Author: jatkinson
-----------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity alu16 is		
	port( A, B : in std_logic_vector(15 downto 0);
		C9, C10, C11 : in std_logic;
		ALU_OUT : out std_logic_vector(15 downto 0));
end alu16;

architecture behav of alu16 is
	SIGNAL C_IN : std_logic_vector(2 downto 0) := (c11 & c10 & C9);
	signal A_signed : signed(15 downto 0) := signed(A);
	signal B_signed : signed(15 downto 0) := signed(B);
	signal A_integer : integer := to_integer(A_signed);
	signal ALU_REG : std_logic_vector(15 downto 0);
begin
	ALU_OUT <= ALU_REG;
	with C_IN select
		ALU_REG <= A and B when "000",
					  A or B when "001",
					  A Xor B when "010", 
					  not(A and B) when "011",
					  std_logic_vector(A_signed + B_signed) when "100",
					  std_logic_vector(A_signed - B_signed) when "101",
					  std_logic_vector(to_signed(2 * A_integer,16)) when "110", 
					  B when "111",
					  ALU_REG when others;
end behav;
