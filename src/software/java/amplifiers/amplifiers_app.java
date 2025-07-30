package amplifiers;

import java.util.ArrayList;
import amplifiers.InvertingAmplifier;
import amplifiers.NoninvertingAmplifier;
import amplifiers.VdivAmplifier;
import java.util.ArrayList;

/**
 * Runs the Amplifier application, main method
 * 
 * @author jatkinson
 */
public class AmplifiersApp
{
   /**
    * Main method to run the program
    * 
    * @param args input 
    */ 
   public static void main(String[] args)
   {
      double r1 = 4;
      double r2 = 20;
      ArrayList<Lab10.Amplifier> amplifiers = new ArrayList<Lab10.Amplifier>();

      amplifiers.add(new InvertingAmplifier(r1, r2));
      amplifiers.add(new NoninvertingAmplifier(r1, r2));
      amplifiers.add(new VdivAmplifier(r1, r2));
      for (int i = 0; i < amplifiers.size(); i++)
      {
         System.out.println(amplifiers.get(i).getDescription() + "\n"
               + "Gain: " + amplifiers.get(i).getGain());
      }
   }
    
} 
