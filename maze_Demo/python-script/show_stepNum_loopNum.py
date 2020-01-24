import matplotlib.pyplot as plt
import os
import numpy as np
from scipy.io.tests.test_wavfile import datafile
from _cffi_backend import typeof


loopNums = []
stepNums = []
stepBump = []
bumpNums = []

handles = []

fp=open("threshold-4.txt")
lines = fp.readlines()
addFlag = True
addStepBump= True
lineIndex = 1

for line in lines:
    line=line.strip('\n')
    if lineIndex%3 == 1:
        lineStrs = line.split(':')
        loopNumber = lineStrs[1]
        if lineStrs[0] == 'loop_number':
            if len(loopNums) == 0:
                loopNums.append(int(loopNumber))
                addFlag = True
            elif loopNums[len(loopNums)-1] < int(loopNumber):
                loopNums.append(int(loopNumber))
                addFlag = True
    elif lineIndex%3 == 2:
        lineStrs = line.split(':')
        stepNumber = lineStrs[1]
        if lineStrs[0] == 'step_number':
            if len(stepNums) == 0:
                stepNums.append(int(stepNumber))
                addStepBump = True
            elif stepNums[len(stepNums)-1] < int(stepNumber):
                stepNums.append(int(stepNumber))
                addStepBump = True
    elif lineIndex%3 == 0:
        if addFlag == True:
            lineStrs = line.split(':')
            bumpNums.append(int(lineStrs[1]))
            addFlag = False
        if addStepBump == True:
            lineStrs = line.split(':')
            stepBump.append(int(lineStrs[1]))
            addStepBump = False
    lineIndex+=1
    
#print loopNums
#print bumpNums
plt.plot(loopNums,bumpNums,label="loop number with bump times")
plt.plot(stepNums,stepBump,label="Step number with bump times")



labels = ['threshold-1', 'threshold-2','threshold-3','threshold-4','threshold-5','threshold-6','threshold-7']
plt.xlabel('Interaction steps')
plt.ylabel('Bump times')
x_ticks = np.arange(0, 3000, 300)
y_ticks = np.arange(0, 100, 10)
plt.xticks(x_ticks)
plt.yticks(y_ticks)
plt.legend()
plt.show()

        
    





#plt.plot(stepNums,bumpNums)
#plt.show()
    