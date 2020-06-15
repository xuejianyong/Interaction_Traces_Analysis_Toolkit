# -*- coding: utf-8 -*-
"""
Created on Wed May  6 17:44:33 2020

@author: Administrator
"""

class Interaction:
    
    experiment = None
    result = 2
    valence = 0
    weight = 0
    preInteraction = None
    postInteraction = None
    
    def __init__(self,label):
        self.label = label
        
    def isPrimitive(self):
        if self.preInteraction is None:
            return True
        else:
            return False
    
    def incrementWeight(self):
        self.weight+=1
    
    def strInteraction(self):
        interStr = self.label+","+str(self.result)+","+str(self.weight)+","+str(self.valence)
        return interStr
        
        
    