import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.event.ActionListener;

/** Catch the Mouse!!
 * a game that helps a new mouse user improve their hand-eye coordination.
 * 
 * @author jatkinson
 */
public class JCatchTheMouse extends JFrame implements ActionListener {
    Random rand = new Random();
    JFrame frame = new JFrame("JCatchTheMouse");
    private Container con = getContentPane();
    
    JButton[][] jbm = new JButton[8][6]; // JButton Matrix
    JButton curr_bt = null;
    int hit, click =0; // hit means selected correct, click means incorrect 
    
    public JCatchTheMouse(){
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(8,6));
        frame.setSize(400, 400);
        frame.setVisible(true);
        createJButtons();
        startGame();
        
    } 
    
    public static void main(String[] args){
        JCatchTheMouse jctm = new JCatchTheMouse();
    } 
    
    
    /**
     * determines if the user selected the correctly marked "X" button, adds to the 
     * hit or click counter, then assigns the next button. After 10 correct hits
     * it will display the message dialog with my name, MEID,class and class#
     * @param e button pressed 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bt = (JButton) e.getSource();

        if (bt == curr_bt) {
            // deletes text and assigns the new button
            curr_bt.setText(""); 
            hit++; // adds a hit to the counter
            if (hit == 10) { // show JOptionPane
                JOptionPane.showMessageDialog(frame, "CatchTheMouse");
//                double perc = (hit/click)*100; // calculates accuracy
//                JOptionPane.showMessageDialog(frame,String.format("%.2f%", perc));
            }
            JButton tmp_bt = getNextJB();
            // cannot assign same button
            while (tmp_bt == curr_bt) {
                tmp_bt = getNextJB();
            }
            curr_bt = tmp_bt;
            curr_bt.setText("X");
        } else {  // user missed
            click++;
        }

    } 
    
    
    /**
     * initializes the game variables. sets the first marked JButton.
     */
    public void startGame(){
        hit = 0; click = 0;
        curr_bt = getNextJB();
        curr_bt.setText("X");
    } 
    
    
    /**
     * randomly generates values for the column and rows variables to randomly 
     * select the next JButton
     * @return randomly selected JButton 
     */
    private JButton getNextJB(){
        return jbm[rand.nextInt(8)][rand.nextInt(6)];
    } 
    
    
    /**
     * generates the buttons onto the JFrame and sets the action listeners
     */
    private void createJButtons(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 6; j++){
                jbm[i][j] = new JButton("");
                frame.add(jbm[i][j]);
                jbm[i][j].addActionListener(this);
            }
        }
    } 
    
} 
