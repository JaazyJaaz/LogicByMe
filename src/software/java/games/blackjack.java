import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.Color;

/** Blackjack
 * 
 * create an array that holds 52 cards and use JFrame to create the game of blackjack. 
 * there will be three buttons, hit, stay, reset
 * @author jatkinson
 */
public class Blackjack extends JPanel implements ActionListener {
    
    Color color;
    Random rand = new Random();
    JFrame frame = new JFrame("Blackjack");
    // deck data
    private String[] deck = new String[52];
    private int[] deck_values = new int[52];
    private boolean[] deck_FLAG = new boolean[52]; // false = unused; true = used
    private final int[] init_values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    private final String[] init_str = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private final String[] suits = {"❤", "♦", "♣", "♠"}; // H:hearts, D:diamonds, C:clubs, S:spades

    JButton button_hit = new JButton("Hit");
    JButton button_stay = new JButton("Stay");
    JButton button_reset = new JButton("Reset");
    
    // initalizes the user and computer hand
    int card_count_comp = 2; // current # of cards in computer's hand
    int card_count_user = 2; // current # of cards in user's hand
    int[] curr_cards_comp = new int[20]; // holds cards index
    int[] curr_cards_user = new int[20]; // holds card index
    int total_value_comp = 0;
    int total_value_user = 0;
    boolean dealer_turn = false;
    
    JLabel user_cards = new JLabel("user");
    JLabel dealer_cards = new JLabel("dealer");

///////////////////////////////////////////////////////////////////////////////
    
    public static void main(String[] args) {
        Blackjack bkjk = new Blackjack();
    } 
    
    /**
     * initalizes the JFrame and sets up the JButtons and the JLabels
     */
    public Blackjack(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(400, 200);
        frame.setVisible(true);
        
        JPanel panel_test = new JPanel(new GridLayout(1,1));
        panel_test.setBackground(Color.BLUE);
        panel_test.add(button_hit);//, BorderLayout.CENTER);
        
       frame.add(button_hit, BorderLayout.WEST);
       frame.add(button_stay, BorderLayout.CENTER);
       frame.add(button_reset, BorderLayout.EAST);
       frame.add(user_cards, BorderLayout.SOUTH);
       frame.add(dealer_cards, BorderLayout.NORTH);
       
       button_hit.addActionListener(this);
       button_stay.addActionListener(this);
       button_reset.addActionListener(this);
       
       // start the game
       newGame();
       //System.out.println(); printDecks();
       
    } 
    
////////////////////////////////////////////////////////////////////////////////

    /**
     * starts a new game by creating new decks, sets all buttons to enabled, creates
     * new empty arrays for the user and dealer, initalizes their hands to 2 cards, 
     * and deals the first two cards, then updates the display text and the current
     * values in both hands. 
     */
    public void newGame(){

        createNewDeck();
        // printDecks();  
        
        dealer_turn = false; // hides the dealer cards
        
        button_hit.setEnabled(true);
        button_stay.setEnabled(true);
        
        curr_cards_comp = new int[20];
        curr_cards_user = new int[20];
        card_count_comp = 2;
        card_count_user = 2;
        
       curr_cards_user[0] = getCard();
       curr_cards_comp[0] = getCard();
       curr_cards_user[1] = getCard();
       curr_cards_comp[1] = getCard();
       
       updateValue();
       updateText();
    } 
    
    
    /**
     * This is a player or dealer turn who chose to hit and add an additional card
     * to their hand. This assigns the card index to their personal hand arrays 
     * and adds +1 to number of cards in their hand. Then updates their personal 
     * total value and updates the display text
     * @param player_code user = 1, dealer = 2 
     */
    public void hitMe( int player_code ){
        int newCard = getCard(); // gets a new unique card
        
        if (player_code == 1){
        curr_cards_user[card_count_user] = newCard;
        ++card_count_user;
        
       } else {
            curr_cards_comp[card_count_comp] = newCard;
            ++card_count_comp;
        }

        updateValue();
        updateText();
        
    } 
    
    
    /**
     * Checks the total value that the dealer has, if that value is less than 17, 
     * the dealer is required to hit, calling the hitMe method. Then recursively
     * calls this this class until the dealer has card values of 17 or higher then
     * ends the game. 
     */
    private void dealerTurn(){
        dealer_turn = true;
        if (total_value_comp < 17){ 
            hitMe(2);
            dealerTurn();
        }
        else{ endGame();}
    } 
    
    
    /**
     * updates the JLables for the current hands of each the user and the dealer
     */
    private void updateText(){
        // updates the user text for current cards
        String user_txt = "User : (" + total_value_user + ") " ;
        for (int idx = 0; idx < card_count_user; idx++){
            user_txt += "[" + deck[curr_cards_user[idx]] + "]   ";
        }
        user_cards.setText(user_txt);
        //updates the dealer cards
        String comp_txt = "Dealer : (" + total_value_comp + ") " ;
        for (int idx = 0; idx < card_count_comp; idx++){
            comp_txt += "  [" + deck[curr_cards_comp[idx]] + "]   ";
        }
        comp_txt = !dealer_turn ? ("Dealer : ( ?? ) [?]  [?]"): comp_txt;
        dealer_cards.setText(comp_txt);
    
    }
    
    
    /**
     * updates the total values of the playing cards in the user and dealer's hand
     */
    private void updateValue() {
        total_value_user = 0;
        total_value_comp = 0;
        for (int i = 0; i < card_count_user; i++) {
            total_value_user += deck_values[curr_cards_user[i]];
        }
        for (int i = 0; i < card_count_comp; i++) {
            total_value_comp += deck_values[curr_cards_comp[i]];
        }

    } 
    
    
    /**
     * deals a card out of the deck, return the index to locate the card information
     * from the deck arrays. Sets the card value to false. If the value is already 
     * used (index location = true) the method will recursively call itself until
     * a unique card is found.
     * @return card index 
     */
    private int getCard(){
        // gets a random card from the deck
        int idx = rand.nextInt(deck.length);
        
        if (!deck_FLAG[idx]){
            deck_FLAG[idx] = true;
            return idx;
        } 
        return getCard();
    } 
    
    
    /**
     * creates a new deck. There are three decks used, one for numerical values, 
     * one for the deck the user will see, and one boolean deck to tell if the 
     * card has been used or not. This is called to start a new game. This resets
     * the cards in the dealer's and user's hand to 2 cards each, and resets their 
     * personal deck values to empty new array. The for loops assign the values to 
     * the 52 array decks using the smaller arrays. 
     */
    public void createNewDeck(){
        card_count_comp = 2;
        card_count_user = 2;
        curr_cards_comp = new int[20];
        curr_cards_user = new int[20];
        int idx = 0;
        for (String s : suits){
            for (int i = 0; i < init_values.length; i++){
                deck_values[idx] = init_values[i];
                deck[idx] = init_str[i] + s; 
                deck_FLAG[idx] = false; 
                idx++;
            } 
        } 
        
    } 
    
    
    
    /**
     * Prints the two current decks for debugging to the console. 
     * First prints the string version of the deck then the numerical value deck
     * 
     * Note: debugging purpose only
     */
    public void printDecks(){
        // prints out the string deck
        for(String i : deck){
            System.out.print(i + ", ");
        }
        System.out.println();
        // prints out the numerical value deck
        for (int j : deck_values){
            System.out.print(j + ",  ");
        }
        System.out.println();
        // prints the deck flag
        for (boolean flg : deck_FLAG){
            System.out.print(flg);
        }
        
    } 
    
    /**
     * This method ends the game by not allowing any further moves to be made by
     * disabling the hit and stay buttons, leaving only the reset button to be used.
     */
    private void endGame(){
        dealer_turn = true;
        updateText(); updateValue();
        button_hit.setEnabled(false);
        button_stay.setEnabled(false);
    }
    
    
    /**
     * Overrides the action listener that is performed when a button is pressed.
     * If the hit button was selected then it calls the hitMe method, and checks
     * if the user 'busted', meaning that they went over 21 total value. if the 
     * reset button is selected then the game is reset, newGame is called and the
     * text is updated. If stay is selected then the dealer takes their turn.
     */
    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if ((JButton) source == button_hit){
            hitMe(1);
            if(total_value_user > 21) { 
                endGame(); 
                dealer_turn = true; 
            }
        }
        else if ((JButton) source == button_reset){
            newGame();
            updateText();
        } else {
            dealerTurn();
        }
        
    } 
    
} 
