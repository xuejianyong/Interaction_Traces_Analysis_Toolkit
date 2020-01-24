import matplotlib.pyplot as plt
import os
import numpy as np
from scipy.io.tests.test_wavfile import datafile
from _cffi_backend import typeof


loopNums = []
stepNums = []
bumpNums = []

handles = []
scriptPath = os.getcwd()
dataPath = os.path.join(scriptPath,'data')
dir_files = os.listdir(dataPath)
for datafilename in dir_files:
    lineIndex = 1
    addFlag = False
    
    datafile = os.path.join(dataPath,datafilename)
    legendName = datafilename.split('.')[0]
    print datafile
    fp=open(datafile)
    lines = fp.readlines()
    for line in lines:
        
        line=line.strip('\n')
        #print lineIndex
        #print line
        
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
        elif lineIndex%3 == 0:
            if addFlag == True:
                lineStrs = line.split(':')
                bumpNums.append(int(lineStrs[1]))
                addFlag = False
        lineIndex+=1
        
    #print loopNums
    #print bumpNums
    l1 = plt.plot(loopNums,bumpNums,label=legendName)
    handles.append(l1)
    
    loopNums = []
    bumpNums = []
    

labels = ['threshold-1', 'threshold-2','threshold-3','threshold-4','threshold-5','threshold-6','threshold-7']
plt.xlabel('Interaction steps')
plt.ylabel('Bump times')
x_ticks = np.arange(0, 1230, 50)
y_ticks = np.arange(0, 250, 20)
plt.xticks(x_ticks)
plt.yticks(y_ticks)
plt.legend()
plt.show()

        
    





#plt.plot(stepNums,bumpNums)
#plt.show()
    