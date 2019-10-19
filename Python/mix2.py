from datetime import datetime
import time
import calendar
import RPi.GPIO as GPIO 

 
def fun3():
 servo = 12
 servo1 = 24
 bigservo = 23 
 DIR = 20   # Direction GPIO Pin
 STEP = 21  # Step GPIO Pin
 DIR1 = 26   # Direction GPIO Pin
 STEP1 = 19  # Step GPIO Pin
 DIR2 = 17   # Direction GPIO Pin
 STEP2 = 27  # Step GPIO Pin
 DIR3 = 22   # Direction GPIO Pin
 STEP3 = 5  # Step GPIO Pin
 DIR4 = 6   # Direction GPIO Pin
 STEP4 = 13  # Step GPIO Pin
 CW = 1     # Clockwise Rotation
 CCW = 0    # Counterclockwise Rotation
 SPR = 240   # Steps per Revolution (360 / 7.5)
 CONTROL_PIN = 18

 GPIO.setmode(GPIO.BCM)
 GPIO.setup(DIR, GPIO.OUT)
 GPIO.setup(STEP, GPIO.OUT)
 GPIO.setup(DIR1, GPIO.OUT)
 GPIO.setup(STEP1, GPIO.OUT)
 GPIO.setup(DIR2, GPIO.OUT)
 GPIO.setup(STEP2, GPIO.OUT)
 GPIO.setup(DIR3, GPIO.OUT)
 GPIO.setup(STEP3, GPIO.OUT)
 GPIO.setup(DIR4, GPIO.OUT)
 GPIO.setup(STEP4, GPIO.OUT)
 GPIO.output(DIR, GPIO.HIGH)
 GPIO.output(DIR1, GPIO.HIGH)
 GPIO.output(DIR2, GPIO.HIGH)
 GPIO.output(DIR3, GPIO.HIGH)
 GPIO.output(DIR4, GPIO.HIGH)
 GPIO.setup(servo1, GPIO.OUT)
 GPIO.setup(bigservo, GPIO.OUT)
 GPIO.setup(servo, GPIO.OUT)
 GPIO.setup(CONTROL_PIN, GPIO.OUT)
 GPIO.output(CONTROL_PIN, GPIO.LOW)


 step_count = SPR
 delay = 0.0005
 delay1 = 0.0008
 p = GPIO.PWM(servo, 50)
 p.start(0)
 q = GPIO.PWM(servo1, 50)
 q.start(0)
 r = GPIO.PWM(bigservo,50)
 r.start(0)
 print time.localtime()
 now= datetime.now()

 m= int(now.strftime("%M"))
 n= int(now.strftime("%S"))
 i=m
 j=n
 a=1
 t1=0  #minutes
 s1=3	#seconds
 t2=0
 s2=10


 GPIO.output(DIR,CCW)
 for x in range(10300):
		GPIO.output(STEP, GPIO.HIGH)
		time.sleep(delay)
		GPIO.output(STEP, GPIO.LOW)
		time.sleep(delay)
		
			
 GPIO.output(DIR,CW)
 for x in range(180):
		GPIO.output(STEP, GPIO.HIGH)
		time.sleep(delay)
		GPIO.output(STEP, GPIO.LOW)
		time.sleep(delay)
		

 time.sleep(2)
		
 GPIO.output(DIR4, CW)
 for x in range(50):
		GPIO.output(STEP4, GPIO.HIGH)
		time.sleep(delay)
		GPIO.output(STEP4, GPIO.LOW)
		time.sleep(delay)
		
 time.sleep(2)
		
 GPIO.output(DIR4, CCW)
 for x in range(50):
		GPIO.output(STEP4, GPIO.HIGH)
		time.sleep(delay)
		GPIO.output(STEP4, GPIO.LOW)
		time.sleep(delay)


 GPIO.output(DIR, CW)
 for x in range(2440):
		GPIO.output(STEP, GPIO.HIGH)
		time.sleep(delay)
		GPIO.output(STEP, GPIO.LOW)
		time.sleep(delay)
		
	
 time.sleep(2)
		
 GPIO.output(DIR3, CW)
 for x in range(50):
		GPIO.output(STEP3, GPIO.HIGH)
		time.sleep(delay)
		GPIO.output(STEP3, GPIO.LOW)
		time.sleep(delay)
		
 time.sleep(2)
		
 GPIO.output(DIR3, CCW)
 for x in range(50):
		GPIO.output(STEP3, GPIO.HIGH)
		time.sleep(delay)
		GPIO.output(STEP3, GPIO.LOW)
		time.sleep(delay)
		

 GPIO.output(DIR, CW)
 for x in range(2440):
		GPIO.output(STEP, GPIO.HIGH)
		time.sleep(delay)
		GPIO.output(STEP, GPIO.LOW)
		time.sleep(delay)
		
 time.sleep(2)
		
		
 GPIO.output(DIR2, CCW)
 for x in range(50):
		GPIO.output(STEP2, GPIO.HIGH)
		time.sleep(delay)
		GPIO.output(STEP2, GPIO.LOW)
		time.sleep(delay)
		
 time.sleep(2)
		
 GPIO.output(DIR2, CW)
 for x in range(50):
		GPIO.output(STEP2, GPIO.HIGH)
		time.sleep(delay)
		GPIO.output(STEP2, GPIO.LOW)
		time.sleep(delay)
		

 GPIO.output(DIR, CW)
 for x in range(4880):
		GPIO.output(STEP, GPIO.HIGH)
		time.sleep(delay)
		GPIO.output(STEP, GPIO.LOW)
		time.sleep(delay)
		


 q.ChangeDutyCycle(11)
 time.sleep(0.5)
 q.ChangeDutyCycle(5)
 time.sleep(0.5)


 GPIO.output(DIR1, CW)
 for x in range(400):
		GPIO.output(STEP1, GPIO.HIGH)
		time.sleep(delay1)
		GPIO.output(STEP1, GPIO.LOW)
		time.sleep(delay1)
		

 GPIO.output(DIR1, CCW)
 for x in range(400):
		GPIO.output(STEP1, GPIO.HIGH)
		time.sleep(delay1)
		GPIO.output(STEP1, GPIO.LOW)
		time.sleep(delay1)
		
		
 q.ChangeDutyCycle(5)
 time.sleep(0.5)
 q.ChangeDutyCycle(11)
 time.sleep(0.5)

 GPIO.output(DIR, CW)
 for x in range(360):
		GPIO.output(STEP, GPIO.HIGH)
		time.sleep(delay)
		GPIO.output(STEP, GPIO.LOW)
		time.sleep(delay)
		print(5)

 GPIO.output(DIR1, CW)
 for y in range(9640):
		GPIO.output(STEP1, GPIO.HIGH)
		time.sleep(delay1)
		GPIO.output(STEP1, GPIO.LOW)
		time.sleep(delay1)



 q.ChangeDutyCycle(11)
 time.sleep(0.5)
 q.ChangeDutyCycle(5)
 time.sleep(0.5)

 GPIO.output(DIR1, CW)
 for y in range(360):
		GPIO.output(STEP1, GPIO.HIGH)
		time.sleep(delay1)
		GPIO.output(STEP1, GPIO.LOW)
		time.sleep(delay1)
		
 GPIO.output(DIR1, CCW)
 for y in range(360):
		GPIO.output(STEP1, GPIO.HIGH)
		time.sleep(delay1)
		GPIO.output(STEP1, GPIO.LOW)
		time.sleep(delay1)
		
		
 q.ChangeDutyCycle(5)
 time.sleep(0.5)		
 q.ChangeDutyCycle(11)
 time.sleep(0.5)
 q.stop()









 try:
	p.ChangeDutyCycle(5)
	time.sleep(0.5)
	p.ChangeDutyCycle(8)
	time.sleep(0.5)
	GPIO.output(DIR1, CCW)
	for y in range(10000):
		GPIO.output(STEP1, GPIO.HIGH)
		time.sleep(delay1)
		GPIO.output(STEP1, GPIO.LOW)
		time.sleep(delay1)

 except KeyboardInterrupt:
  p.stop()
  q.stop()
  GPIO.cleanup()
 finally:
  GPIO.cleanup()
	

 return j,i






