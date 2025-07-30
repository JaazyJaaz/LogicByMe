import java.io.*;
import java.text.SimpleDateFormat;

/** File stuff
 * displays the file's name, size, and time of last modification.
 * 
 * @author jatkinson
 */
public class FileStatistics 
{
    // NOTE: change file path to YOUR pc
    private static String file_path = "C:\\Users\\Jessica\\Documents\\NetBeansProjects\\example-filepath.txt"; 
   
    
    public static void main(String args[])
    {
        // initializes variables
        String file_name, file_timeModStr = "";
        long file_size, file_timeModified = 0;

        // initializes handles
        File myFile = new File(file_path);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        
        if (myFile.exists()) // checks if the file exisits
        {
            // saves the file information to the variables
            file_name = myFile.getName();
            file_size =  myFile.length();
            file_timeModified = myFile.lastModified();
            // formats the time from milliseconds
            file_timeModStr = sdf.format(file_timeModified);
            
            // prints file information
            String print_message = String.format("File name: %s\tSize: %s\t Last Modified: %s", file_name, String.valueOf(file_size), file_timeModStr);
            System.out.println(print_message);
        
        }
    else
        System.out.println("Sorry, but the file does not exist.");
    
    } 
}
