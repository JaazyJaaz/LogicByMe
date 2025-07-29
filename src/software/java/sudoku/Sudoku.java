package sudoku;

/**
 * Suduko
 * 
 * @author jatkinson
 */
public class Sudoku 
{    
    /** current numbers stored in a string array of the Sudoku board **/
    private String[] currentRows;
    /** debugging flag **/
    private boolean debugFLAG = false;
    
    
// CONSTRUCTORS ///////////////////////////////////////////////////////////////    
    
    /**
     * Initializes the state to represent an empty sudoku puzzle
     */
    public Sudoku()
    {
        String start = "";start += strMultiplication("         \n", 9); 
        this.currentRows = start.split("\n");
    } 
    
    
    /**
     * Initializes the starting configuration of the puzzle
     * 
     * @param starting_configuration initial configuration of the puzzle
     */
    public Sudoku(String starting_configuration)
    {
        String start = ""; start += starting_configuration;
        this.currentRows = start.split("\n");
        
    } 
    
    
// METHODS //////////////////////////////////////////////////////////////////    
    
    /**
     * Returns the character at the given location in the puzzle
     * 
     * @param row row to access value at
     * @param col column to access value at
     * 
     * @return character value at location
     */
    public char getSquare(int row, int col)
    {  
        return this.getRow(row).charAt(col) ;
    
    } 
    
    
    /**
     * Takes a row, column and digit, and sets the puzzle to store value at 
     * the given location. 
     * 
     * @param row row to locate in the puzzle
     * @param col column to locate in the puzzle
     * @param value value to set at the location
     */
    public void setSquare(int row, int col, char value)
    {
        // saves the current row before update
        String currentRow = this.currentRows[row]; 
        // updates the value in the row
        this.currentRows[row] = currentRow.substring(0, col) + String.valueOf(value) + ( currentRow.length() == col ? "" : currentRow.substring(col+1) ) ;
        
    } 
    
    
    /**
     * Observes the three Sudoku rules and returns the boolean result. 
     * No duplicate numbers in each row, in each column, or in each sub-square.
     * 
     * @return boolean result of Sudoku rules being followed 
     */
    protected boolean isValid()
    {
        // checks the rows, columns, and box logic
        return ( rowChecker() && columnChecker() && boxChecker() );
       
    } 
    
    
    /**
     * Checks if there is no blank squares left in the puzzle and that all of
     * the three suduko rules have been followed.
     * 
     * @return boolean result if puzzle was successfully complete 
     */
    protected boolean isSolved()
    {
        // checks if valid logic and searches for spaces
        if ( isValid() )
        {
            // checking for blank spaces in each row
            for (String row : this.currentRows) 
                if ( row.contains(" ") )
                    return false;           // if blank space
            return true;               // if no blank spaces
        } 
        else  
            return false;   // if the board is not valid
        
        
    } 
    
    
////////////////////////////////////////////////////////////////////////////////    
// MY METHODS //////////////////////////////////////////////////////////////////   
    
    
    /**
     * Checks that all of the rows are valid.
     * Used by isValid(). Uses getRow() & doubleCheck().
     * 
     * @return boolean return of row validation 
     */
    private boolean rowChecker()
    {
        int rowNumber = 0; // current row to check
        int maxRows = 9;   // maximum rows + 1
        while(rowNumber < maxRows)
        {
            // save row [rowNumber]
            String currentRow = this.getRow(rowNumber);

            if (!doubleCheck(currentRow))
                return false; 
            else
                rowNumber++; 
        }
        return true; // if all rows are valid
        
    } 
    
    
     /**
     * Checks that all of the columns are valid.
     * Used by isValid(). Uses getColumn() & doubleCheck().
     * 
     * @return boolean return of column validation 
     */
    private boolean columnChecker()
    {
        int maxColumns = 9;   // maximum columns + 1
        // checks for dupicate values in columns
        for (int colNumber = 0; colNumber < maxColumns; colNumber++)  
        {
            String currentColumn = this.getColumn(colNumber);
            
            if (!doubleCheck(currentColumn))
                return false;
            else
                colNumber++;
        }
        return true; // if all columns are valid
        
    } 
    
    
     /**
     * Checks that all of the boxes are valid.
     * Used by isValid. Uses getBox() & doubleCheck().
     * 
     * @return boolean return of box validation 
     */
    private boolean boxChecker()
    {
        int boxNumber = 0; // current box number
        int maxRows = 9;   // maximum boxes + 1

        // box 1
        String currentBox = this.getBox(boxNumber, boxNumber);
        if (!doubleCheck(currentBox))
                return false;
        
        // box 2
        currentBox = this.getBox(boxNumber, boxNumber + 3);
        if (!doubleCheck(currentBox))
                return false;
       
        // box 3
        currentBox = this.getBox(boxNumber, boxNumber + 6);
        if (!doubleCheck(currentBox))
                return false;
        
        // box 4
        currentBox = this.getBox(boxNumber + 3, boxNumber);
        if (!doubleCheck(currentBox))
                return false;
        
        // box 5
        currentBox = this.getBox(boxNumber + 3, boxNumber + 3);
        if (!doubleCheck(currentBox))
                return false;
        
        // box 6
        currentBox = this.getBox(boxNumber + 3, boxNumber + 6);
        if (!doubleCheck(currentBox))
                return false;
        
        // box 7
        currentBox = this.getBox(boxNumber + 6, boxNumber);
        if (!doubleCheck(currentBox))
                return false;
        
        // box 8
        currentBox = this.getBox(boxNumber + 6, boxNumber + 3);
        if (!doubleCheck(currentBox))
                return false;
        
        // box 9
        currentBox = this.getBox(boxNumber + 6, boxNumber + 6);
        if (!doubleCheck(currentBox))
                return false;
        
        // of all boxes are valid return true
        return true;
        
    } 

    
    /**
     * Retrieves the box that belongs to a specific row and column. 
     * Used by boxChecker method.
     * 
     * @param row   row location 
     * @param col   column location
     * @return      a string of the numbers in the box with the given row and column
     */
    private String getBox(int row, int col)
    {  
        int rowNum, colNum, boxNum, rowStart, colStart;
        String[] rows = this.currentRows; 
        String box = ""; 
        
        // assigns the starting rows & indexes
        rowNum = (row < 3 ? 1 : (row < 6 ? 2 : 3));
        colNum = (col < 3 ? 1 : (col < 6 ? 2 : 3));
        rowStart = (rowNum == 1 ? 0 : rowNum == 2? 3 : 6);
        colStart = (colNum == 1 ? 0 : colNum == 2 ? 3 : 6);
        
        for (int idx = rowStart; idx < rowStart + 3; idx++)
        {
            box += String.valueOf(rows[idx].charAt(colStart));
            box += String.valueOf(rows[idx].charAt(colStart + 1));
            box += String.valueOf(rows[idx].charAt(colStart + 2));
        }
        return box;
        
    } 
    
    
    /**
     * retrieves the string containing the contents of the given row
     * 
     * @param   rowNum  the number of the row to retrieve 
     * @return          string of the character values in the row
     */
    private String getRow(int rowNum)
    {
        return currentRows[rowNum];
 
    } 
 
    
    /**
     * gets a string of all the values in the column.
     * Used by columnChecker().
     * 
     * @param colNum    the column number
     * @return          string of all the values in the column
     */
    private String getColumn(int colNum)
    {
        String column = "";
        for (String row : this.currentRows) 
        {
            column += String.valueOf(row.charAt(colNum));
        }
        System.out.println("Column: " + column);
        return column;
        
    } 
     
    
// MY GENERIC METHODS /////////////////////////////////////////////////////////
     
    /**
     * Takes in a string and checks for any duplicate char values in the string
     * 
     * @return boolean result of duplicate characters in string
     */
    private boolean doubleCheck( String str )
    {
        String currentStr = "";
        String currentChar;
        for (int idx = 0; idx < str.length(); idx++)
        {
            currentChar = str.substring(idx, idx + 1); 
            
            // checks for a blank space
            if (!currentChar.equals(" ") )
            {   // checks if the current string contains the char at idx
                if (currentStr.contains(currentChar))
                    return false;
                else
                    currentStr += currentChar;
            }    
        }
       
        return true; // if no duplicates chars were in the string
    } 
    
    
    /**
     * Concatenates the inputted string to itself the number of times inputted.
     * Used by constructor().
     * 
     * @param str       string that needs to be multiplied
     * @param numTimes  number of times to multiply the string by
     * 
     * @return concatenated string 
     */
    private String strMultiplication(String str, int numTimes)
    {
        String newStr = ""; 
        
        for (int idx = 0; idx < numTimes; idx++)
            newStr += str;
        
        return newStr;
    } 
    
    
    /**
     * Prints the debugging messages
     * 
     * @param method    name of method printing the debug message
     * @param desc      description of what is printed to the console
     * @param val       the value to be printed to the console
     */
    private void debugger(String method, String desc, String val)
    {
        String RED = "\u001B[31m";
        String RESET = "\u001B[0m"; 
        String BLUE = "\u001B[34m"; 
        
        String printError = RED;   
        String prtWrite = BLUE;     

        if (debugFLAG)
        {    
            System.out.print(prtWrite + "DEBUGGER-- [" + printError + method + prtWrite 
                    + "]--- [" + printError + desc + prtWrite + "]--START--->>");
            System.out.println( printError + val + prtWrite + "<<---END" + RESET );
        }
    
    } 
    
    
    /**
     * Prints the current suduko board to user console
     */
    private void printBoard()
    {
        System.out.println("Suduko Board");
        for (String currentRow : this.currentRows) 
            System.out.println(currentRow);
        
        System.out.println();
        
    } 
}
