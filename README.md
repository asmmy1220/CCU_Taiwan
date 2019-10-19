# CCU_Taiwan ASFAST
In this repository, not only contain all Android Studio codes about our app, but also python program running on Raspberry Pi.
## Python program
When the device is booted and connected to an Android phone, the Python program will stay idle pending a command Once it receives the trigger signal from the Android phone, it will start the test procedure. The first thing it does is to control all the motors in our device to complete test procedure. Then, it will process the fluorescence test data once the test is finished. Lastly, it sends the processed test result back to the Android application.

![](https://github.com/igemsoftware2019/CCU_Taiwan/blob/master/screen.png)

Download the file "Python" and click "TCP.py" to run our program.
### Notice: This Python program only can be use in Raspberry Pi, you also need MCP3008 and photoresistance to run this program. 

## Android application

 An Android application to cooperate with Python. It serves as a connection between the clients and online database and also records the identity of test subject for each particular test. Furthermore, it also uploads the test result to the online database immediately after every single test procedure is done. Finally, it will maintain a list of recent test results to provide longitudinal tracking.
 
 Clone or download our repository and you can run in Android Studio or your Android phone.
 
 ## Google Firebase
 
 Our software team employed Google Firebase to demonstrate the concept of integrating an online database. When a new subject is tested, Firebase will create a new index to store its test result. If further tests are conducted, the result shall be recorded in the related index. Moreover, the Firebase can be accessed by authorized personal so that they will get updated with the fresh test result instantly.
 
The connetion program between Android app and google Firebase is including in our app document, but you need to use your own Firebase project to connect. 


