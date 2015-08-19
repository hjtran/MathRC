/* Math Problem Powered RC Car Program

   Receives a signal from an Android enabled device using the Bluetooth module.
   Signal will dictate the speed of the motor which will propel the car. */
   
#include <Servo.h>;   // library
#include <SoftwareSerial.h>


SoftwareSerial serial(1,0); // Pin 1 = tx, Pin 0 = rx
int serialInput = 0;
const int MOTORPIN = 10, SERVOPIN = 9;  RELAYPIN = 8;
Servo servo;
int currentAngle = 90, motorSpeed = 0;


void setup(){ 
  pinMode(MOTORPIN,OUTPUT);
  pinMode(SERVOPIN,OUTPUT);
  pinMode(RELAYPIN,OUTPUT);
  digitalWrite(RELAYPIN, 0);
  servo.attach(SERVOPIN);
  Serial.begin(9600);
  analogWrite(MOTORPIN,motorSpeed);
  servo.write(currentAngle);
  delay(2000);
}


void loop(){
  RCCar();

}



void RCCar(){
    // Wait  until information from Bluetooth is available
    while(Serial.available() > 0){
    
    // Reads information
    serialInput = Serial.parseInt();
    Serial.println(serialInput);
    // Changes servo direction or motor speed depending on input
    // '1', turn servo left
    // '2', turn servo center
    // '3', turn servo right
    // 'f', increase motor speed
    // 's', decreae motor speed
    // delay of 100ms after input
    if (serialInput == 1){
      if(currentAngle > 0){
        currentAngle -= 10;
        servo.write(currentAngle);
      }
    }
    else if (serialInput == 2){
      if(currentAngle > 90)
        currentAngle -= 10;
      else if(currentAngle < 90){
        currentAngle += 10;
        servo.write(currentAngle);
      }
    }
    else if (serialInput == 3){
      if(currentAngle < 180){
        currentAngle += 10;
        servo.write(currentAngle);
      }
    }
    else if (serialInput == 4 && motorSpeed < 255){
      motorSpeed += 5;
      //analogWrite(MOTORPIN,motorSpeed);
      analogWrite(MOTORPIN,255);
    }
    else if (serialInput == 5  && motorSpeed > 0){
      motorSpeed -= 5;
      analogWrite(MOTORPIN,motorSpeed);
    }

/**   if(motorSpeed == 255){ serialInput = 's'; delay(2500);}
   if(motorSpeed == 0){ serialInput = 'f'; }
**/   
   delay(100);
  }
  
}



