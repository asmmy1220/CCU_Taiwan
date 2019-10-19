import os
import socket
import numpy as np
import simpletest3
import mix2

#hi = open('tsl2561.py')
#exec(hi.read(),None,None)

BUFSIZE=4096
tcpServerSocket=socket.socket()#create socket
hostname= socket.gethostname()#get local host IP name
sysinfo = socket.gethostbyname_ex(hostname)
hostip='192.168.1.32'
port=12567#set prot
tcpServerSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)#could re use
tcpServerSocket.bind((hostip,port))
tcpServerSocket.listen(5) 


while True:
    print("Waiting for conneting...")
    print(hostname)
    print(sysinfo)
    print(hostip)
    
    clientSocket, addr = tcpServerSocket.accept()  
    print('print connect address:',addr)
    
    while True:
        mix2.fun3()
        data=clientSocket.recv(BUFSIZE).decode()
        temp = []
        print(data)
        
        for i in range(5):
         if not data == "do":
            #hi = open('tsl2561.py')
            #exec(hi.read(),None,None)
            
            
             value = simpletest3.fun2()
             print(value)    
                
             temp.append(value)
                    
                
                    #clientSocket.send(str(i).encode()+str('.No ').encode())
                
                
    
        avg = sum(temp)/len(temp)
        clientSocket.send(str(avg).encode()+str(',').encode())
        
        if avg >= 800: 
            message = "Negative"
            clientSocket.send(str(message).encode())        
        else:
                
            message2 ="Postive"
            clientSocket.send(str(message2).encode()) 
            
            #clientSocket.send(str(round(avg,3)).encode()+str(',').encode())
            #clientSocket.send(str(round(Max,3)).encode()+str(',').encode())
        
        
            #print (temp)
            #mes='This is Python Server'
            #clientSocket.send(mes.encode())
            #clientSocket.send(str(temp).encode())
        break
        
            
            
        
    clientSocket.close() 
tcpServerSocket.close()

#
