import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/** Battleship
 * A similar version to the game, Battleship. 
 * Made on a 6-by-6 grid. 
 * The ships are represented by a a randomly chosen row or column given the value 1, 
 * while the rest remain 0. The game ends when the user selects all the enemy
 * ships then disables and prevents any more moves to take place. The values
 * are exposed after the user selects a square. 
 * 
 * Note: To restart the game, you have to exit the JFrame and run the application again
 * 
 * @author jatkinson
 */
public class Battleship extends JFrame implements ActionListener
{
    Random rand = new Random();
    private final int FRAME_WIDTH = 300;
    private final int FRAME_HEIGHT = 300;
    private JButton[][] buttons = new JButton[6][6];
    private int[][] buttons_value = new int[6][6];
    
    private int rowcol = 0; // 0 = row 1 = column
    private int rowcol_num = 0; // 0-5 the index of the row
    
    public static void main(String[] args)
    {
        Battleship bs = new Battleship();
    
    } 
    
    
    /**
     * Constructor, initalizes the JFrame settings and adds the buttons to a 
     * matrix. 
     */
    public Battleship()
    {
        super("Battleship");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLayout(new GridLayout(6,6));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // picks a random row or column and sets the values to one
        getInitialBoardValues(chooseColumnOrRow(), chooseColumnOrRowIndex());
        
        for (int i=0; i<6;i++){
            for (int j = 0; j<6; j++){
                JButton button = new JButton();
                buttons[i][j] = button;
                add(button);
                button.addActionListener(this);
            }
        }
    } 
    
    /**
     * gets the index for the action button and returns a list of the row and 
     * column index where it is located
     * @param btn   JButton that was pressed in the JFrame
     * @return      list of the row/column value
     */
    private int[] getButtonIndexs(JButton btn){
        int[] idxs = new int[2];
        
        for (int i=0; i<6;i++){
            for (int j = 0; j<6; j++){
                if (buttons[i][j] == btn){
                    idxs[0] = i;
                    idxs[1] = j;
                    return idxs;
                }
            }
        }
        return null;        
    } 
    
    /**
     * gets the inital board values and stores them into a 6 by 6 matrix
     * fills the 6 by 6 matrix with zeroes and fills the selected row or column 
     * to all ones     * 
     * @param rowORcol  the row or column value 0 = row 1 = column
     * @param rowORcol_num  the index that will be set to all 1
     */
    private void getInitialBoardValues(int rowORcol, int rowORcol_num){
        // updates global variables
        rowcol = rowORcol;
        rowcol_num = rowORcol_num;
        
        for (int row_index = 0; row_index < 6; row_index++){
            for (int col_index = 0; col_index < 6; col_index++){
                if (rowcol == 0){
                    if (row_index == rowcol_num)
                        buttons_value[row_index][col_index] = 1; 
                    else
                        buttons_value[row_index][col_index] = 0;
                }
                else if (rowcol == 1){
                    if (col_index == rowcol_num)
                        buttons_value[row_index][col_index] = 1;
                    else
                        buttons_value[row_index][col_index] = 0;
                }
                System.out.print(buttons_value[row_index][col_index] + "\t");
            } 
            System.out.println();
        } 
        
    } 
    
    @Override
    /**
     * Overrides the action listener that is performed when the button is pressed.
     * disables the button pressed, reveals the value, and checks if the game was won. 
     * if the game was won, all buttons are disabled.
     */
    public void actionPerformed(ActionEvent e){
        JButton bt = (JButton) e.getSource(); // saves teh action to a Jbutton handle
        int[] idxs = getButtonIndexs(bt); // gets the indexs of the pressed button
        bt.setEnabled(false); // disables the button that was selected
        bt.setText(String.valueOf(buttons_value[idxs[0]][idxs[1]])); // reveals the value
        isWinner(); // checks for winner
    } 
    
    /**
     * Chooses whether a column or row will be set to value 1 by picking a random 
     * number from 0 to 1. 0 = row, 1 = column
     * @return the numerical value for row or column
     */
    private int chooseColumnOrRow(){
        return rand.nextInt(2);
    } 
    
    /**
     * Chooses a random number between 0-5 to choose which random row or column is to set to value 1
     * @return  the index value
     */
    private int chooseColumnOrRowIndex(){
        return rand.nextInt(6);
    } 
    
    /**
     * Checks if user won by seeing if all buttons with value 1 are disabled
     * @return  boolean value if there is a winner
     */
    private boolean isWinner(){
        for (int i=0; i<6;i++){
            for (int j = 0; j<6; j++){
                if (buttons_value[i][j] == 1 && buttons[i][j].isEnabled())
                    return false;
            }
        }
        endGame(); // ends the game because there is a winner
        return true;
    } 
    
    /**
     * Ends the game by disabling all buttons
     */
    private void endGame(){
        for (int i=0; i<6;i++){
            for (int j = 0; j<6; j++){
                buttons[i][j].setEnabled(false);
            }
        }
    } 
        
} 
