# Simple example of reading the MCP3008 analog input channels and printing
# them all out.
# Author: Tony DiCola
# License: Public Domain
import time
from firebase import firebase
import numpy as np

# Import SPI library (for hardware SPI) and MCP3008 library.
import Adafruit_GPIO.SPI as SPI
import Adafruit_MCP3008

# set up firebase connection
#url = "https://test-3a47e.firebaseio.com/"
#fb = firebase.FirebaseApplication(url,None)
def fun2():
# Software SPI configuration:
 CLK  = 11
 MISO = 9
 MOSI = 10
 CS   = 8
 mcp = Adafruit_MCP3008.MCP3008(clk=CLK, cs=CS, miso=MISO, mosi=MOSI)



 print('Reading MCP3008 values, press Ctrl-C to quit...')
# Print nice channel column headers.
 print('| {0:>4} | {1:>4} | '.format(*range(2)))
 print('-' * 57)
# Main program loo
 a = 0

 while True:
  values = [0]*8
  values[1] = mcp.read_adc(1)
  values[0] = mcp.read_adc(0)
  inputValue = values[1]
 
  
  if inputValue == 0:
  # Read all the ADC channel values in a list.
      values = [0]*2
      temp = []
      values[0] = mcp.read_adc(0)
      values[1] = mcp.read_adc(1)
    
    # Print the ADC values.
      for i in range(3):
          print('| {0:>4} | {1:>4} | '.format(*values))
          temp.append(values[0])
    
      
      avg = sum(temp)/len(temp)
      print('Average: '+str(avg))
      #print('Max Value: '+str(max(temp)))
      #print('Min Value: '+str(min(temp)))
      a=a+1
    #data = [{"voltage":values[0]}]
    #
    #fb.post("LDR/",data)
    # Pause for half a second.
      time.sleep(1)
      if a == 1:
        break
  
    
   
  
 return avg

