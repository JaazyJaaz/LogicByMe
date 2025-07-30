package bumblebee;
import java.awt.Robot; // my Bumblebee :)
import java.awt.AWTException; // boo exceptions
import java.awt.event.*; // events
/**
 * Accessing the mouse on the PC anywhere...booyeah! :) 
 * June 9, 2020
 * @author Jessica
 */
public class MyMouse
{
    /*
    * methods to use:
    *      mouseMove(x,y)
    *      keyPress
    */
    public static void main(String[] args) throws AWTException 
    {
        
        System.out.println("test");
    } 
    
    
    public static void click(int x, int y) throws AWTException{
    Robot bot = new Robot();
    bot.mouseMove(x, y);    
    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
}
    
    public void myBot()throws AWTException {
        Robot bot = new Robot();
    
    } 
    
    
}
