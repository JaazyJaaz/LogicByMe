package amplifiers;

/** Noninverting Amplifier 
 * subclass of the Amplifier class
 * 
 * @author jatkinson
 */
public class NoninvertingAmplifier extends Amplifier {
    /** resistor 1 value of the op amp */
    private double R1 = 0.0;
    /** resistor 2 value of the op amp */
    private double R2 = 0.0;
   /** the gain of the amplifier */
    private double gain = 0.0;
    /** string of the type of amplifier */
    private String descriptor = "Noninverting Amplifier";
    
    
    /**
     * constructor class sets the gain of the amplifier
     * 
     * @param r1    resistor 1
     * @param r2    resistor 2
     */
    public NoninvertingAmplifier(double r1, double r2) 
    {
        gain = 1 + (r2/r1); // gain
        R1 = r1; R2 = r2;
        descriptor = "Inverting Amplifier:" + "R1 = " + R1 + ", R2 = " + R2;
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
