package amplifiers;

/**
 * Amplifier superclass
 * 
 * @author jatkinson
 */
public class Amplifier 
{
    /** resistor 1 value of the op amp */
    private double R1 = 0.0;
    /** resistor 2 value of the op amp */
    private double R2 = 0.0;
    /** the gain of the amplifier */
    private double gain = 0.0;
    /** string of the type of amplifier */
    private String descriptor = null;
    

    /**
     * constructor class
     * 
     * @param r1    resistor 1
     * @param r2    resistor 2
     */
    public void Amplifier (double r1, double r2)
    {
        R1 = r1; R2 = r2; // sets resistor values
        // sub classes set the gain
        // gain remains 0.0 if amplifier is not specified
    }
    

    /**
     * gets a double value of the gain of the amplifier. 
     * the gain is calculated in the constructor
     * 
     * @return  double gain 
     */
    public double getGain()
    {
        return gain;
    }
    
    
    /**
     * gets the string of the description of the type of op amp.
     * 
     * @return  string op amp type 
     */
    public String getDescription()
    {
        return descriptor;
    }
    
}
