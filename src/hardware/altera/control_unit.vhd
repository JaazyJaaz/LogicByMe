------------------------------------
-- Control Unit
-- 5-State Finite State Machine
-- 
-- Author: jatkinson
------------------------------------

library ieee;
use ieee.std_logic_1164.all;

entity control_unit is
	port( NFLG, ZFLG, RESET, CLK : in std_logic;	
		OPCODE : in std_logic_vector(7 downto 0);	
		STATE : BUFFER std_logic_vector(2 downto 0);
		LOAD_AC, LOAD_IRU, LOAD_IRL, LOAD_PC, INCR_PC, FETCH, STORE_MEM : out std_logic);  
end control_unit;

architecture behavior of control_unit is
	type state_type is (start, PrepU, FetchU, PrepL, FetchL, EXECUTE);
	signal present_state, next_state, LAST_STATE : state_type;
	signal AC_LOAD, MEM_STORE, skip_FLG, class1, class5 : STD_LOGIC;
	signal control_signal,opcode_ctrl_sig, ctrl_sig_out : std_logic_vector(7 downto 0);
	
begin
	--UPDATE CONTROL SIGNAL OUTPUTS
	--(skip_flg, fetch, LOAD_IRU, LOAD_IRL, INCR_PC, LOAD_PC, LOAD_AC, STORE_MEM)
	STORE_MEM <=  ctrl_sig_out(0);
	LOAD_AC <=  ctrl_sig_out(1);
	LOAD_PC <=  ctrl_sig_out(2);
	INCR_PC <=  ctrl_sig_out(3);
	LOAD_IRL <=  ctrl_sig_out(4);
	LOAD_IRU <=  ctrl_sig_out(5);
	FETCH <= ctrl_sig_out(6);
	skip_FLg <= ctrl_sig_out(7);
	
	--toggle 1/0 for class variable
	class1 <= '1' when (opcode = x"04") else '0';
	with opcode select
		class5 <= '1' when x"10",
					NFLG  when x"11",
					NOT NFLG when x"12",
					ZFLG when x"13",
					NOT ZFLG when x"14",
					'0' when others;
		
	with present_state select
		control_signal <= 		
					('0','0','0','0','0','0','0','0') WHEN start,
					('0','1','0','0','0','0','0','0') when PrepU,
					('0','0','1','0','1','0','0','0') when FetchU,
					('0','1','0','0','0','0','0','0') when PrepL,
					('0','0','0','1','1','0','0','0') when FetchL,
					opcode_ctrl_sig when EXECUTE,		--loads decoded opcode signals
					unaffected when others;

	with opcode select
		opcode_ctrl_sig <= 
					('1','0','0','0','0','0',class1,'0') when x"00"|x"04",		--Class 1
					('0','0','0','0','0','0','1','0') when x"02"| x"06" | x"08" | x"0E" | x"0F"  ,		--Class 2
					('0','0','0','0','0','0','1','0') when x"01"| x"05" |x"07"| x"09" |x"0A"| x"0B" | x"0C" | x"0D",		--Class 3
					('0','0','0','0','0','0','0','1') when x"03",	--Class 4
					('0','0','0','0','0',class5,class5,'0') when x"10"| x"11" | x"12" | x"13" | x"14"  ,	--Class 5
					unaffected when others;
	sync_proc:
	process(CLK, RESET, control_signal)
	begin
		-- 
		if RESET = '1' then
			present_state <= start;
			ctrl_sig_out <= ('0','0','0','0','0','0','0','0') ; 	--resets outputs asyncrysiosly
		elsif (clk'event and CLK = '0') then 	-- falling edge		
			IF skip_Flg = '1' then
				if STATE = "010" then
					--transition to a new state
					present_state <= PrepU;
					ctrl_sig_out <= control_signal;
				ELSE
					present_state <= next_state;
				end if;
			else
					present_state <= next_state;
					ctrl_sig_out <= control_signal;		--updates outputs with the clock			
			end if;
		end if;
	end process;
			
	comb_proc:
	process(present_state, next_state)
	begin
		-- Switch between state types
		case present_state is
										-- start
			when start => STATE <= "000";
				next_state <= PrepU;				
										-- PrepU
			when PrepU => STATE <= "001";
				next_state <= FetchU;
										-- FetchU
			when FetchU => STATE <= "010";
				next_state <= PrepL;
				
										-- PrepL
			when PrepL => STATE <= "011";
				next_state <= FetchL;
										-- FetchL
			when FetchL => STATE <= "100";
				next_state <= EXECUTE;
							
			when EXECUTE => STATE <= "101";
				next_state <= PrepU;
				
		end case;
	end process;
end behavior;

	
