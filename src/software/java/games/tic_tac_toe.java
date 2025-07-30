import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


 /** Tic-Tac-Toe
 * A game using Java and GridLayout
 * 
 * Features:
 * - 3x3 grid layout using buttons
 * - Player (X) goes first by selecting a cell
 * - Computer (O) plays by choosing a random empty cell
 * - Game ends when a player gets 3 in a row (horizontal, vertical, or diagonal)
 * - No handling for ties (game does not detect draws)
 * 
 * @author jatkinson
 */
public class TicTacToe extends JFrame implements ActionListener
{
    Random rand = new Random();
    private final int FRAME_WIDTH = 300;
    private final int FRAME_HEIGHT = 300;
    
    private JButton b1 = new JButton();
    private JButton b2 = new JButton();
    private JButton b3 = new JButton();
    private JButton b4 = new JButton();
    private JButton b5 = new JButton();
    private JButton b6 = new JButton();
    private JButton b7 = new JButton();
    private JButton b8 = new JButton();
    private JButton b9 = new JButton();
    
    private JButton[][] bm = { {b1, b2, b3}, { b4, b5, b6}, {b7, b8, b9} }; // bm=button matrix
    private JButton[] button_list = {b1, b2, b3, b4, b5, b6, b7, b8, b9};  // used to select computer button
    
    public static void main(String[] args)
    {
        TicTacToe ttt = new TicTacToe();
        
    } 
    
    /**
     * Constructor that sets up the JFrame and adds the components and action listeners
     */
    public TicTacToe()
    {
        super("TicTacToe");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLayout(new GridLayout(3,3));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
        add(b7);
        add(b8);
        add(b9);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
    
    } 
    
    @Override
    /**
     * Overrides the action listener that is performed when the button is pressed.
     * places the users move as an 'X', checks for a winner then it calls for the 
     * computer to move then checks for a winner again. If there is a winner, all
     * buttons become disabled and the game ends. 
     */
    public void actionPerformed(ActionEvent e)
    {
        JButton bt = (JButton) e.getSource();
        // initalizes no winner
        boolean winner = false;
        // makes the move for the user
        makeMove(bt, 0);
        // checks if there is a winner
        winner = getWinner();
        if (winner) 
            endGame(); // disables all buttons
        else{
            // lets the computer make a move
            makeMove(computerPlay(),1);
            // checks if there is a winner
            winner = getWinner();
            if (winner)
                endGame(); // disables all buttons if a winner
        }
    } 
    
    /**
     * adds a move to the board, sets the text and disables the button
     * 
     * @param button    button to be altered
     * @param user      user = 0, computer = 1
     */
    public void makeMove(JButton button, int user)
    {
        // assigns the text 
        String checkStr = (user == 0)?"X":"O";
        // sets the text and disables the button
        button.setText(checkStr);
        button.setEnabled(false);
    } 
    
    
    /**
     * Ends the game by not allowing any more moves to be made by disabling 
     * all the buttons
     */
    public void endGame()
    {
        for (JButton button : button_list){
            button.setEnabled(false);
        }
    } 
    
    
    /**
     * Chooses the computer move at random
     */
    public JButton computerPlay()
    {
        boolean flag = true; 
        JButton button = null;
        // chooses a button that hasn't already been chosen
        while (flag){
            button = button_list[rand.nextInt(button_list.length)];
            flag = ! button.isEnabled();
        }
        return button;
    } 
    
    
    /**
     * Determines if there is a winner by checking all the combinations that win
     * 
     * @return  boolean value if there a winner 
     */
    public boolean getWinner()
    {
        // checks top row
        if (bm[0][0].getText().equals(bm[0][1].getText()) && bm[0][0].getText().equals(bm[0][2].getText()) && !bm[0][0].getText().equals(""))
            return true;
        // checks middle row
        else if (bm[1][0].getText().equals(bm[1][1].getText()) && bm[1][0].getText().equals(bm[1][2].getText()) && !bm[1][0].getText().equals(""))
            return true;
        // checks bottom row
        else if (bm[2][0].getText().equals(bm[2][1].getText()) && bm[2][0].getText().equals(bm[2][2].getText()) && !bm[2][0].getText().equals(""))
            return true;
        // checks first column
        else if (bm[0][0].getText().equals(bm[1][0].getText()) && bm[0][0].getText().equals(bm[2][0].getText()) && !bm[0][0].getText().equals(""))
            return true;
        // checks middle column
        else if (bm[0][1].getText().equals(bm[1][1].getText()) && bm[0][1].getText().equals(bm[2][1].getText()) && !bm[0][1].getText().equals(""))
            return true;
        // checks right column
        else if (bm[0][2].getText().equals(bm[1][2].getText()) && bm[0][2].getText().equals(bm[2][2].getText()) && !bm[0][2].getText().equals(""))
            return true;
        // checks diagonal
        else if (bm[0][0].getText().equals(bm[1][1].getText()) && bm[0][0].getText().equals(bm[2][2].getText()) && !bm[0][0].getText().equals(""))
            return true;
        //checks diagonal
        else if (bm[0][2].getText().equals(bm[1][1].getText()) && bm[0][2].getText().equals(bm[2][0].getText()) && !bm[0][2].getText().equals(""))
            return true;
        else
            return false;
    } 
    
} 
