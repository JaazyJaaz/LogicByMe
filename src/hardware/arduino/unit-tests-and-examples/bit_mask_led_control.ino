/**
 * Blink: Blink LED using bit-masking
 *** Arduino Uno on-board LED: PortB dataRegister:bit5 
 ***
 *** DDRB = Data Direction Register for portB
 */

#define MASK(x) ((unsigned char) (1<<(x)))
void setup() {
  // maps each bit to the corresponding input output pins of port B
  // set pins as 0/input or 1/output
  DDRB |= MASK(5); // set LED pin as output with Mask 00100000
}

void loop() {
  // PORTB: setting bit 5 of a separate data register called port B
  // setting 
  PORTB |= MASK(5); // LED ON: set bit 5 high
  delay(500); // milliseconds 

  PORTB &= ~MASK(5); // LED off: clear the bit that was just set
  delay(500); 
}