# -*- coding: utf-8 -*-
"""
Created on Wed May  6 19:54:47 2020

@author: Administrator
"""

class Experiment:
    
    isAbstract = True;
    intendedInteraction = None
    
    def __init__(self,label,actionType):
        self.label = label
        self.actionType = actionType
        
    def resetAbstract(self):
        self.isAbstract = False
        
        
        
    