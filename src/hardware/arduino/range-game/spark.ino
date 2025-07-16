#include "AccelStepper.h"
#include <SoftwareSerial.h>

// pin numbers ----
#define upBtn_pin 8 // in works
#define upBtnLed_pin 6 // out
#define lowBtn_pin 7 // in works 
#define lowBtnLed_pin 13 // out
bool isUpBtnPressed = false;
bool isLowBtnPressed = false;
// roller
//#define topStop_pin 7 // top stopper MicroSwitch
#define lowStop_pin 2 // bot stopper MicroSwitch works
bool isTopStopPressed = false;
bool isLowStopPressed = false;
// stepper motor
#define motorIn1_pin 9 // out
#define motorIn2_pin 10 // out
#define motorIn3_pin 11 // out
#define motorIn4_pin 12 // out

#define motorSpeed_stepPerRot 100 // steps per rotation
bool isMotorMoving = false;
bool isFrozen = false;
bool isInit = true;
int motorSpeed = 500;
bool dirUp = true;
// Define the AccelStepper interface type:
#define MotorInterfaceType 4

// Create a new instance of the AccelStepper class:
AccelStepper stepper = AccelStepper(MotorInterfaceType, motorIn1_pin, motorIn2_pin, motorIn3_pin, motorIn4_pin);

void setup() {
//  Serial.begin(9600); // buad
  // output: push button LEDs
  pinMode(upBtnLed_pin, OUTPUT);
  pinMode(lowBtnLed_pin, OUTPUT);
  // input: push buttons switch
  pinMode(upBtn_pin, INPUT_PULLUP);
  pinMode(lowBtn_pin, INPUT_PULLUP);

  // top & lower stopper micro switches: input
  //  pinMode(topStop_pin, INPUT);
  pinMode(lowStop_pin, INPUT);


  stepper.setMaxSpeed(1000); // Set the maximum speed in steps per second
}

void loop() {
//  stepper.setSpeed(motorSpeed);
  stepper.setSpeed(isFrozen ? 0 : motorSpeed);
  stepper.runSpeed();
  // push arcade buttons
  isUpBtnPressed = digitalRead(upBtn_pin) == 0;
  isLowBtnPressed = digitalRead(lowBtn_pin) == 0;
  Serial.print("isUpBtn: "); Serial.print(isUpBtnPressed);
  Serial.print("--isLowBtn: "); Serial.print(isLowBtnPressed);

  // stopper micro switches
  //  isTopStopPressed = digitalRead(topStop_pin) == 1;
  isLowStopPressed = digitalRead(lowStop_pin) == 1;
  Serial.print("--TopStop: "); Serial.print(isTopStopPressed);
  Serial.print("--LowStop: "); Serial.print(isLowStopPressed);


  if (isLowBtnPressed) {
    // stop start
    isFrozen = !isFrozen;
    stepper.setSpeed(isFrozen ? 0 : motorSpeed);
    stepper.runSpeed();
    delay(500);
  }


  if (isUpBtnPressed) {
    //change direction
      dirUp = !dirUp;
      motorSpeed = dirUp ? 500 : -500;
    
    stepper.setSpeed(motorSpeed); // Set the speed of the motor in steps per second
    stepper.runSpeed(); // Step the motor with constant speed as set by setSpeed()
    delay(400);
  }

  Serial.println();
}