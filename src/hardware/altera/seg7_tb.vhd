----------------------------------------
--Seven-Segment Decoder
--
-- Author: jatkinson
----------------------------------------
--This file includes Blank/Test Inputs--
----------------------------------------


library ieee;
use ieee.std_logic_1164.all;

entity seg7 is
	--declaring all input and output 
	port ( vector_in : in std_logic_vector(7 downto 0);
		blank : in std_logic;
		test : in std_logic;
		vector_L : out std_logic_vector(6 downto 0);
		vector_R : out std_logic_vector(6 downto 0)
		);
end entity seg7;
	
architecture behavior of seg7 is
		--DECLARING ALL TMP VARIABLES USED
	signal seg_L : std_logic_vector(6 downto 0);
	signal seg_R : std_logic_vector(6 downto 0);
	signal nibble_L : std_logic_vector(3 downto 0);
	signal nibble_R : std_logic_vector(3 downto 0);
	--signal nibble_tmp : std_logic_vector(3 downto 0);
	--signal seg_tmp : std_logic_vector(7 downto 1);
begin
	process(vector_in, blank, test)
	begin
	
		nibble_L <= vector_in(7 downto 4);	--taking first 4 digits
		nibble_R <= vector_in(3 downto 0);	--taking last 4 digits
		
		IF blank = '1' then						--BLANK TURNS ALL OFF
			vector_L <= "1111111";
			vector_R <= "1111111";
		ELSIF  test = '1' then					--TEST TURNS ALL ON
			vector_L <= "0000000";
			vector_R <= "0000000";
		ELSE									--OTHERWISE 
			--case statements to decode binary to 7-seg display
			case nibble_L is 
				when "0000" => seg_L <= "1000000";	--0
				when "0001" => seg_L <= "1111001";	--1
				when "0010" => seg_L <= "0100100";	--2
				when "0011" => seg_L <= "0110000";	--3
				when "0100" => seg_L <= "0011001";	--4
				when "0101" => seg_L <= "0010010";	--5
				when "0110" => seg_L <= "0000011";	--6
				when "0111" => seg_L <= "1111000";	--7
				when "1000" => seg_L <= "0000000";	--8
				when "1001" => seg_L <= "0011000";	--9
				when "1010" => seg_L <= "0001000";	--A
				when "1011" => seg_L <= "0000011";	--B
				when "1100" => seg_L <= "1000110";	--C
				when "1101" => seg_L <= "0100001";	--D
				when "1110" => seg_L <= "0000110";	--E
				when "1111" => seg_L <= "0001110";	--F
				when OTHERS => seg_L <= "0111111";	--INVALID--
			end case;
			vector_L <= seg_L;
			
			--case statements to decode binary to 7-seg display
			case nibble_R is 
				when "0000" => seg_R <= "1000000";	--0
				when "0001" => seg_R <= "1111001";	--1
				when "0010" => seg_R <= "0100100";	--2
				when "0011" => seg_R <= "0110000";	--3
				when "0100" => seg_R <= "0011001";	--4
				when "0101" => seg_R <= "0010010";	--5
				when "0110" => seg_R <= "0000011";	--6
				when "0111" => seg_R <= "1111000";	--7
				when "1000" => seg_R <= "0000000";	--8
				when "1001" => seg_R <= "0011000";	--9
				when "1010" => seg_R <= "0001000";	--A
				when "1011" => seg_R <= "0000011";	--B
				when "1100" => seg_R <= "1000110";	--C
				when "1101" => seg_R <= "0100001";	--D
				when "1110" => seg_R <= "0000110";	--E
				when "1111" => seg_R <= "0001110";	--F
				when OTHERS => seg_R <= "0111111";	--INVALID--
			end case;
			vector_R <= seg_R;
		END IF;			
	end process;
END behavior;
