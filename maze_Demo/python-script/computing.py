import numpy as np
import matplotlib.pyplot as plt
renu = np.linspace(0,1350,1000,endpoint=True)
result = np.linspace(100000,100000,1000,endpoint=True)
result1 = np.linspace(95000,95000,1000,endpoint=True)
rate = np.linspace(8.1,9.0,10,endpoint=True)
print renu
print rate
l1 = []
for r1 in rate:
    #print r1
    c = (11100+renu)*r1
    plt.plot(renu,c,label=r1)
    #l1.append(llabel,)
    

plt.plot(renu,result, '--')
plt.plot(renu,result1, '-.')

num1=1
num2=0
num3=3
num4=0

plt.legend()
plt.show()