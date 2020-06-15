# -*- coding: utf-8 -*-
"""
Created on Tue Jan 06 22:00:39 2015
@author: Nikolai K.

Updated on Wed Oct 03 16:10:11 2018
@author: Jianyong XUE


"""
#Import Libraries:
import vrep                  #V-rep library
import sys
import time                #used to keep track of time
import numpy as np         #array library
import math
import matplotlib as mpl   #used for image plotting
import matplotlib.pyplot as plt

#Pre-Allocation

PI=math.pi  #pi=3.14..., constant

vrep.simxFinish(-1) # just in case, close all opened connections

clientID=vrep.simxStart('127.0.0.1',19997,True,True,5000,5)

if clientID!=-1:  #check if client connection successful
    print 'Connected to remote API server'
    res,objs=vrep.simxGetObjects(clientID,vrep.sim_handle_all,vrep.simx_opmode_blocking)
    if res==vrep.simx_return_ok:
        print ('Number of objects in the scene: ',len(objs))
    else:
        print ('Remote API function call returned with error code: ',res)
    
    time.sleep(2)
    startTime=time.time()
    returnCode,left_motor_handle=vrep.simxGetObjectHandle(clientID,"Pioneer_p3dx_leftMotor",vrep.simx_opmode_blocking)
    returnCode,right_motor_handle=vrep.simxGetObjectHandle(clientID,"Pioneer_p3dx_rightMotor",vrep.simx_opmode_blocking)
    
    returnCode=vrep.simxSetJointTargetVelocity(clientID,left_motor_handle,0.2,vrep.simx_opmode_streaming)
    returnCode=vrep.simxSetJointTargetVelocity(clientID,right_motor_handle,-0.2,vrep.simx_opmode_streaming)
    
    returnCode,sensor8=vrep.simxGetObjectHandle(clientID,"Pioneer_p3dx_ultrasonicSensor8",vrep.simx_opmode_blocking)
    returnCode,detectionState,detectedPoint,detectedObjectHandle,detectedSurfaceNormalVector=vrep.simxReadProximitySensor(clientID,sensor8,vrep.simx_opmode_streaming)
    
    
    returnCode,vision_sensor=vrep.simxGetObjectHandle(clientID,"Vision_sensor",vrep.simx_opmode_blocking)
    returnCode,resolution,image=vrep.simxGetVisionSensorImage(clientID,vision_sensor,0,vrep.simx_opmode_streaming)
    im = np.array(image,dtype=np.uint8)
    print im.shape
    im.resize([resolution[0],resolution[1],3])
    plt.imshow(abs(im),origin="lower)
    
    
    #time.sleep(10)

    
    
    #returnCode=vrep.simxSetJointTargetVelocity(clientID,right_motor_handle,0,vrep.simx_opmode_streaming)
    #returnCode=vrep.simxSetJointTargetVelocity(clientID,left_motor_handle,0,vrep.simx_opmode_streaming)
    
    
    
    
    
    
else:
    print 'Connection not successful'
    sys.exit('Could not connect') # exit the system
    
