// 11-25-2024
#include <Stepper.h>
#include <SoftwareSerial.h>

// pin numbers ----
// todo: -IRL: need to make pull down resistor
// btn leds
#define upBtn_pin 6 // in
#define upBtnLed_pin 8 // out
#define lowBtn_pin 13 // in
#define lowBtnLed_pin 7 // out
bool isUpBtnPressed = false;
bool isLowBtnPressed = false;
// roller
#define topStop_pin 4 // top stopper MicroSwitch
#define lowStop_pin 5 // bot stopper MicroSwitch
bool isTopStopPressed = false;
bool isLowStopPressed = false;
// stepper motor
#define motorIn1_pin 10 // out
#define motorIn2_pin 11 // out
#define motorIn3_pin 12 // out
#define motorIn4_pin 9 // out
#define motorSpeedPot_pin 1 // input potentiometer pin A1
int motorSpeedPot_now = 0;
#define motorSpeed_stepPerRot 100 // steps per rotation
int motorSpeed_set = 30;
int motorSpeed_prev = 0;
int motorSpeed_now = 0;

Stepper motor = Stepper(motorSpeed_stepPerRot, motorIn1_pin, motorIn3_pin, motorIn2_pin, motorIn4_pin);

void setup() {
  Serial.begin(9600); // buad
  // set pins
  // output: push button LEDs
  pinMode(upBtnLed_pin, OUTPUT);
  pinMode(lowBtnLed_pin, OUTPUT);
  // input: push buttons switch
  pinMode(upBtnLed_pin, INPUT);
  pinMode(lowBtnLed_pin, INPUT);
 
  // stepper motor: output
  motor.setSpeed(motorSpeed_set);
  pinMode(motorIn1_pin, OUTPUT);
  pinMode(motorIn2_pin, OUTPUT);
  pinMode(motorIn3_pin, OUTPUT);
  pinMode(motorIn4_pin, OUTPUT);
  // stepper motor: input analogue speed
  pinMode(motorSpeedPot_pin, INPUT); // potentiometer
  
  // top & lower stopper micro switches: input
  pinMode(topStop_pin, INPUT);
  pinMode(lowStop_pin, INPUT);
}


void loop() {
  Serial.println("~~~ loop start:");
  digitalWrite(upBtnLed_pin, HIGH); 
  digitalWrite(lowBtnLed_pin, HIGH); 
  // check input status of everything ----------------
  // push arcade buttons
  isUpBtnPressed = digitalRead(upBtn_pin) == 1;
  isLowBtnPressed = digitalRead(lowBtn_pin) == 1;
  Serial.print("isUpBtnPressed: "); Serial.println(isUpBtnPressed);
  Serial.print("isLowBtnPressed: "); Serial.println(isLowBtnPressed);

  // stopper micro switches
  isTopStopPressed = digitalRead(topStop_pin) == 1;
  isLowStopPressed = digitalRead(lowStop_pin) == 1;
  Serial.println("-");
  Serial.print("isTopStopPressed: "); Serial.println(isTopStopPressed);
  Serial.print("isLowStopPressed: "); Serial.println(isLowStopPressed);

  // motor stuff
  motorSpeedPot_now = analogRead(motorSpeedPot_pin); // motor speed
  // motorSpeed_now = motorSpeed_now - motorSpeed_prev; 
  motor.step(motorSpeed_now);
  // motorSpeed_prev = motorSpeed_now; // save prev val
  Serial.println("-");
  Serial.print("motorSpeedPot_now: "); Serial.println(motorSpeedPot_now);
  Serial.print("motorSpeed_now: "); Serial.println(motorSpeed_now);

// -- code ---------------------------------
/* control the motor
 * if game not in play --> do not move
 * if in game and btn pressed --> stop the motor
 *     - do nothing else 
 * how do we reset the motor to the bottom?
 * rollers
 *  if (isTopStopPressed || isLowStopPressed){ 
 *     // change direction;
 *     // stepper.step(-stepsPerRevolution);
 *   }
 */

 /* is the game in play and the button was pressed? 
 * Top Btn:
 *    • someone hit it, now what?
 *    • what is the LED doing? 
 * Low Btn:
 *    • someone hit it, now what?
 *    • what is the LED doing?
 */

 /* potentiometer: control motor speed
 *     • how does it affect the speed (formula)
 *
 */

} // end of main loop
