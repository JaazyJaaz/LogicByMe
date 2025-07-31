#include <Stepper.h> // Arduino Stepper Library

const int stepsPerRevolution = 2038;

// Pins entered in sequence IN1-IN3-IN2-IN4 for proper step sequence
Stepper myStepper = Stepper(stepsPerRevolution, 10, 12,11, 13);

void setup() {
    // Nothing to do (Stepper Library sets pins as outputs)
}

void loop() {
  // Rotate CW slowly at 5 RPM
  myStepper.setSpeed(15);
  myStepper.step(stepsPerRevolution);
  delay(1000);
  
  // Rotate CCW quickly at 10 RPM
  myStepper.setSpeed(10);
  myStepper.step(-stepsPerRevolution);
  delay(1000);
}