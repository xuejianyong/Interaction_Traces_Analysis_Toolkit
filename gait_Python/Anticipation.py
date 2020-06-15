# -*- coding: utf-8 -*-
"""
Created on Wed May  6 22:00:21 2020

@author: Administrator
"""

class Anticipation:
    
    def __init__(self,experiment,proclivity):
        self.experiment = experiment
        self.proclivity = proclivity
        
    def addProclivity(self,i):
        self.proclivity = self.proclivity+i
    
    def strAnticipation(self):
        self.experiment.label+","+str(self.proclivity)
        