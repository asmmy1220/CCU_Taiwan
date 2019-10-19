# CCU_Taiwan
## Python program
When the device is booted and connected to an Android phone, the Python program will stay idle pending a command Once it receives the trigger signal from the Android phone, it will start the test procedure. The first thing it does is to control all the motors in our device to complete test procedure. Then, it will process the fluorescence test data once the test is finished. Lastly, it sends the processed test result back to the Android application.

!

```sh
$ cd dillinger
$ npm install -d
$ node app
```

## Android application

 An Android application to cooperate with Python. It serves as a connection between the clients and online database and also records the identity of test subject for each particular test. Furthermore, it also uploads the test result to the online database immediately after every single test procedure is done. Finally, it will maintain a list of recent test results to provide longitudinal tracking.

[](https://github.com/igemsoftware2019/CCU_Taiwan/blob/master/androidapp2.png)
 
 ## Google Firebase
 
 Our software team employed Google Firebase to demonstrate the concept of integrating an online database. When a new subject is tested, Firebase will create a new index to store its test result. If further tests are conducted, the result shall be recorded in the related index. Moreover, the Firebase can be accessed by authorized personal so that they will get updated with the fresh test result instantly.
 
 ## Network Socket
 
 Since a WiFi connection may not be available on a pig farm, the ASFAST device is unable to upload test results directly to the online database. Thus, the microcomputer in the ASFAST device must connect with an Android phone and let the Android phone upload the data. During the development stage, we found that connecting two devices running different operating system is never an easy task. Finally, we developed a solution by integrating the TCP network socket framework to exchange information between these programs running on two different systems.

Network socket is a way of connecting two devices in a local area network (LAN) to transfer data between each other. Under our network socket framework, the Android phone acts as a server, while the Raspberry Pi 3 microcomputer act as a client, so they communicate with each other just as a common client computer communicates with a server.

## Operation procedure

Before conducting the test, the operator will have to complete following procedures. First, the operator has to take out an Android phone and host a WiFi hotspot. Next, the operator has to insert the reagent cartridges and boot up the ASFAST device. The device will automatically form up a connection with Android phone and open up Sundae program. At this point, the user can enter the identity information of the pig in the Android application, touch the ‘Start’ button, sit back and wait for the test to be done. Once the result is obtained, it will be uploaded directly to the online database before showing up on the screen of the phone.
