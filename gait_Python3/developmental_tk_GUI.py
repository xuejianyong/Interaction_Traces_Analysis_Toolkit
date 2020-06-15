# -*- coding: utf-8 -*-
"""
Created on Sat May  9 19:26:24 2020

@author: Administrator
"""
from tkinter import *
import tkinter as tk
from  PIL import Image, ImageTk
import time
import sys
import time                #used to keep track of time
import numpy as np         #array library
import math
import _thread
#import matplotlib as mpl   #used for image plotting
#import matplotlib.pyplot as plt


from function_actions import *
from Experiment import Experiment
from Interaction import Interaction
from Anticipation import Anticipation


imgs = []

EXPERIENCES = {}
INTERACTIONS = {}
intendedInteraction = None
enactedInteraction = None
superInteraction = None
startState = False
isIntendedInteractionComposite = False

marginRectangle = 3
FRAME_WIDTH = 1200
FRAME_HEIGHT = 600
CANVAS_WIDTH = 1050
CANVAS_HEIGHT = 400
INVERVAL = 50
TITLE = 100

xValue = 0
yValue = CANVAS_HEIGHT-85




interactionStrings = ['e00','e00','e00','e00','e00','e00','e00','e00','e00','e00']

interaction_images = {
    'e01':'images/move_forward_original.png',
    'e00':'images/bump_original.png',
    'e11':'images/turn_left.png',
    'e21':'images/turn_right.png',
    'e31':'images/feel_front_empty.png',
    'e30':'images/feel_front_wall.png',
    'e41':'images/feel_left_empty.png',
    'e40':'images/feel_left_wall.png',
    'e51':'images/feel_right_empty.png',
    'e50':'images/feel_right_wall.png'
}

def anticipate():
    anticipations = getDefaultAnticipations();
    #defaultAnticipations = getDefaultAnticipations();
    activatedInteractions =  getActivatedInteractions();
    
    anticipationIndex = False
    for activatedInteraction in activatedInteractions:
        #得到构建anticipation相应的experiment，proclivity，然后构建
        experimenttem = activatedInteraction.postInteraction.experiment
        proclivitytem = activatedInteraction.weight*activatedInteraction.postInteraction.valence
        anticipationtem = Anticipation(experimenttem,proclivitytem)
        
        for anticipation in anticipations:
            if anticipation.experiment == experimenttem:
                anticipation.addProclivity(proclivitytem)
                anticipationIndex = True
        if anticipationIndex == False:
            anticipations.append(anticipationtem)
        anticipationIndex = False
    return anticipations
                
                
    
def getDefaultAnticipations():
    anticipations = []
    for experimentLabel in EXPERIENCES:
        if EXPERIENCES[experimentLabel].isAbstract != True:
            anticipation = Anticipation(EXPERIENCES[experimentLabel],0)
            anticipations.append(anticipation)
    return anticipations

def getActivatedInteractions():
    contextInteractions = []
    if enactedInteraction is not None:
        contextInteractions.append(enactedInteraction)
        if not enactedInteraction.isPrimitive():
            contextInteractions.append(enactedInteraction.postInteraction)
        if superInteraction is not None:
            contextInteractions.append(superInteraction)
        
    activatedInteractions = []
    for interactionLabel in INTERACTIONS:
        if not INTERACTIONS[interactionLabel].isPrimitive():
            if INTERACTIONS[interactionLabel].preInteraction in contextInteractions:
                activatedInteractions.append(INTERACTIONS[interactionLabel])
    return activatedInteractions
            
def addOrGetExperience(label,actionType):
    experiment = Experiment(label,actionType)
    EXPERIENCES[label] = experiment
    return EXPERIENCES[label]

def addOrGetPrimitiveInteraction(experiment,result,valence):
    label = experiment.label+str(result)
    if label not in INTERACTIONS:
        inter = Interaction(label)
        inter.experiment = experiment
        inter.result = result
        inter.valence = valence
        INTERACTIONS[label] = inter
    return INTERACTIONS[label]

def enact(intendedInteraction):
    result = None
    isWall = False
    if intendedInteraction.isPrimitive() == True:
        actionType = intendedInteraction.experiment.actionType
        if actionType == 0:
            isWall = moveForward()
            if isWall:
                result = 0
            else:
                result = 1
        elif actionType == 1:
            isWall = turnLeft()
            result = 1
        elif actionType == 2:
            isWall = turnRight()
            result = 1
        elif actionType == 3:
            isWall = feelFront()
            if isWall:
                result = 0
            else:
                result = 1
        elif actionType == 4:
            isWall = feelLeft()
            if isWall:
                result = 0
            else:
                result = 1
        elif actionType == 5:
            isWall = feelRight()
            if isWall:
                result = 0
            else:
                result = 1
        
        inter = addOrGetPrimitiveInteraction(intendedInteraction.experiment,result,6)
        return inter

    else:
        enactPreInteraction = enact(intendedInteraction.preInteraction)
        if enactPreInteraction != intendedInteraction.preInteraction:
            return enactPreInteraction
        else:
            enactedPostInteraction = enact(intendedInteraction.postInteraction)
            return addOrGetAndReinforceCompositeInteraction(enactPreInteraction, enactedPostInteraction,False)

def learnCompositeInteraction(currentEnactedInteraction):
    print
    print("function learnCompositeInteraction")
    global enactedInteraction,superInteraction
    if enactedInteraction != None:
        print('previous enactedInteraction is:'+enactedInteraction.strInteraction())
    print('current  enactedInteraction is:'+currentEnactedInteraction.strInteraction())
    if superInteraction != None:
        print('superInteraction is:'+superInteraction.strInteraction() )
    previousEnactedInteraction = enactedInteraction
    lastEnactedInteraction = currentEnactedInteraction
    previousSuperInteraction = superInteraction
    lastSuperInteraction = None
    if previousEnactedInteraction != None:
        lastSuperInteraction = addOrGetAndReinforceCompositeInteraction(previousEnactedInteraction, lastEnactedInteraction,True)
    if previousSuperInteraction != None:
        addOrGetAndReinforceCompositeInteraction(previousSuperInteraction.preInteraction, lastSuperInteraction,True)
        addOrGetAndReinforceCompositeInteraction(previousSuperInteraction, lastEnactedInteraction,True)
    superInteraction = lastSuperInteraction
        

def addOrGetAndReinforceCompositeInteraction(previousInteraction,lastInteraction,isFlag):
    label = "<"+previousInteraction.label+lastInteraction.label + ">"
    if label in INTERACTIONS:
        if isFlag:
            print("enforce interaction:"+INTERACTIONS[label].strInteraction())
            INTERACTIONS[label].incrementWeight()
    else:
        inter = Interaction(label)
        inter.preInteraction = previousInteraction
        inter.postInteraction = lastInteraction
        inter.incrementWeight()
        inter.valence = previousInteraction.valence+lastInteraction.valence
        inter.experiment = addOrGetAbstractExperiment(inter)
        INTERACTIONS[label] = inter
        print("learn new interaction:"+INTERACTIONS[label].strInteraction() )
    return INTERACTIONS[label]
        
        
def addOrGetAbstractExperiment(interaction):
    label = interaction.label.replace('e', 'E').replace('>', '|')
    if label not in EXPERIENCES:
        abstractExperiment = Experiment(label,6)
        abstractExperiment.intendedInteraction = interaction
        EXPERIENCES[label] = abstractExperiment
    return EXPERIENCES[label]
        

def initial():
    e0 = addOrGetExperience("e0", 0);
    e0.resetAbstract()
    e1 = addOrGetExperience("e1", 1);
    e1.resetAbstract()
    e2 = addOrGetExperience("e2", 2);
    e2.resetAbstract()
    e3 = addOrGetExperience("e3", 3);
    e3.resetAbstract()
    e4 = addOrGetExperience("e4", 4);
    e4.resetAbstract()
    e5 = addOrGetExperience("e5", 5);
    e5.resetAbstract()

    v_moveSucess = 5
    v_moveFailture = -10
    v_turn = -3
    v_feelEmpty = -1
    v_feelWall = -2
    i01 = addOrGetPrimitiveInteraction(e0, 1, v_moveSucess);
    i00 = addOrGetPrimitiveInteraction(e0, 0, v_moveFailture);
    i11 = addOrGetPrimitiveInteraction(e1, 1, v_turn);
    i21 = addOrGetPrimitiveInteraction(e2, 1, v_turn);
    i31 = addOrGetPrimitiveInteraction(e3, 1, v_feelEmpty);
    i30 = addOrGetPrimitiveInteraction(e3, 0, v_feelWall);
    i41 = addOrGetPrimitiveInteraction(e4, 1, v_feelEmpty);
    i40 = addOrGetPrimitiveInteraction(e4, 0, v_feelWall);
    i51 = addOrGetPrimitiveInteraction(e5, 1, v_feelEmpty);
    i50 = addOrGetPrimitiveInteraction(e5, 0, v_feelWall);

    e0.intendedInteraction = i01;
    e1.intendedInteraction = i11;
    e2.intendedInteraction = i21;
    e3.intendedInteraction = i30;
    e4.intendedInteraction = i40;
    e5.intendedInteraction = i50;

def drawLine(canvas):
    global  xValue,yValue
    canvas.create_rectangle(10, 10, 110, 110)
    canvas.create_line(xValue, yValue, xValue+100, yValue),

def drawImage(canvas,label,xvalue,yvalue):
    source_image = Image.open(interaction_images[label])
    source_image = source_image.resize((50, 50), Image.ANTIALIAS)
    img = ImageTk.PhotoImage(source_image)
    x = canvas.create_image(xvalue,yvalue,anchor="nw", image=img)
    imgs.append(img)
    return img

def start(canvas):
    global startState
    print('start the process')
    if not startState:
        startState = True
        _thread.start_new_thread(interaction, (canvas,1))

def interaction(canvas,count):
    global startState,xValue,yValue,enactedInteraction,CANVAS_HEIGHT,INVERVAL,isIntendedInteractionComposite
    intendedInteractionWidth = 0
    if startState:
        loopNum = 1
        while loopNum < 200:
            print('------------------------------------')
            print('loopNum is:'+str(loopNum))
            if loopNum == 1:
                initial()
            anticipations = anticipate()
            sortedAnticipations = sorted(anticipations, key=lambda anticipation: anticipation.proclivity,reverse=True)
            intendedInteraction = sortedAnticipations[0].experiment.intendedInteraction

            print('intendedInteraction is:' + intendedInteraction.strInteraction())
            if not intendedInteraction.isPrimitive():
                isIntendedInteractionComposite = True
            intendedInteractionWidth = drawInteraction(canvas,intendedInteraction,xValue,yValue)

            actionType = intendedInteraction.experiment.actionType
            pretentResult = intendedInteraction.result
            currentEnactedInteraction = enact(intendedInteraction)
            
            print('enactedInteraction is:' + currentEnactedInteraction.strInteraction())
            if enactedInteraction == None:
                print('previous enactedInteraction is:None')
            else:
                print('previous enactedInteraction is:' + enactedInteraction.strInteraction())
            learnCompositeInteraction(currentEnactedInteraction)

            enactedInteraction = currentEnactedInteraction
            drawInteraction(canvas, enactedInteraction, xValue+12, yValue+12)
            if isIntendedInteractionComposite:
                isIntendedInteractionComposite = False

            #drawIntendedEnactedInteraction(canvas, intendedInteraction, enactedInteraction)
            canvas.create_text(xValue+(intendedInteractionWidth-25)/2, CANVAS_HEIGHT-INVERVAL/2+7, anchor="nw", text=str(loopNum))

            loopNum += 1
            xValue+=intendedInteractionWidth
            if xValue > 1050:
                canvas["scrollregion"] = "%d %d %d %d" % (0, 0, xValue, 500)
                canvas.xview_moveto(1)
            print


def drawInteraction(canvas,intendedOrenactedInteraction,xvalue,yvalue):
    global marginRectangle,INVERVAL,isIntendedInteractionComposite
    intendedInteractionWidth = 0
    xvalueSource = xvalue
    yvalueSource = yvalue
    if intendedOrenactedInteraction.isPrimitive():
        image = drawImage(canvas, intendedOrenactedInteraction.label, xvalue, yvalue)
        intendedInteractionWidth = 75
        #canvas.create_rectangle(xvalueSource - marginRectangle,
        #                        yvalueSource - marginRectangle,
        #                        xvalueSource - marginRectangle + 50 + 2 * marginRectangle,
        #                        yvalueSource - marginRectangle + 50 + 2 * marginRectangle)
        if isIntendedInteractionComposite:
            canvas.create_rectangle(xvalueSource - marginRectangle,
                                    yvalueSource - marginRectangle,
                                    xvalueSource - marginRectangle + 50 + 2 * marginRectangle,
                                    yvalueSource - marginRectangle + 50 + 2 * marginRectangle)

    else:
        print('intendedOrenactedInteraction is not primitive')
        primitiveInteractionList = geInteractionList(intendedOrenactedInteraction)
        for primitiveInteraction in primitiveInteractionList:
            print('primitive interaction is:'+primitiveInteraction.strInteraction())
            image = drawImage(canvas, primitiveInteraction.label, xvalue, yvalue)
            xvalue += 75
        intendedInteractionWidth = len(primitiveInteractionList) * 75
        canvas.create_rectangle(xvalueSource-marginRectangle,
                                yvalueSource-marginRectangle,
                                xvalueSource-marginRectangle+intendedInteractionWidth-25+2*marginRectangle,
                                yvalueSource-marginRectangle+ INVERVAL+ 2*marginRectangle)
    return intendedInteractionWidth


def geInteractionList(interaction):
    interactionList = []
    tempList = []
    tempList.append(interaction)
    while len(tempList):
        tempInteraction = tempList.pop()
        if not tempInteraction.isPrimitive():
            tempList.append(tempInteraction.postInteraction)
            tempList.append(tempInteraction.preInteraction)
        else:
            interactionList.append(tempInteraction)
    return interactionList


def stop():
    print('stop the process')
    global startState
    interaction()

def close(root):
    root.quit()     # stops mainloop
    root.destroy()  # this is necessary on Windows to prevent
                    # Fatal Python Error: PyEval_RestoreThread: NULL tstate



window = tk.Tk()
frame = tk.Frame(window,width=FRAME_WIDTH,height=FRAME_HEIGHT)
frame.pack(expand=True,fill=BOTH)


window_height = 600
window_width = 1200
screen_width = window.winfo_screenwidth()
screen_height = window.winfo_screenheight()
x_cordinate = int((screen_width/2) - (window_width/2))
y_cordinate = int((screen_height/2) - (window_height/2))
window.geometry("{}x{}+{}+{}".format(window_width, window_height, x_cordinate, y_cordinate))
window.title = "GAIT"

canvas = tk.Canvas(frame, width=CANVAS_WIDTH, height=CANVAS_HEIGHT, bg='white',scrollregion=(0,0,CANVAS_WIDTH,CANVAS_HEIGHT))
canvas.place(x=150,y=TITLE)
hbar=Scrollbar(frame,orient=HORIZONTAL)
hbar.pack(side=BOTTOM,fill=X)
hbar.config(command=canvas.xview)
canvas.config(xscrollcommand=hbar.set)
tk.Label(frame,text="Generating and Analyzing Interaction Traces Toolkit (GAIT)", font=("Helvetica",16)).place(x=350,y=30)
tk.Label(frame,text="loop number:").place(x=50,y=TITLE+CANVAS_HEIGHT-INVERVAL/2)
tk.Label(frame,text="Enacted Interaction:").place(x=20,y=TITLE+CANVAS_HEIGHT-INVERVAL)
tk.Label(frame,text="Intended Interaction:").place(x=20,y=TITLE+CANVAS_HEIGHT-(INVERVAL/2)*3)
tk.Label(frame,text="Composite Interaction:").place(x=15,y=TITLE+CANVAS_HEIGHT-(INVERVAL/2)*7)

startButton = tk.Button(frame, text="Start", width=10,height=1, command=lambda:start(canvas)).place(x=500,y=TITLE+CANVAS_HEIGHT+INVERVAL/2)
stopButton = tk.Button(frame, text="Stop", width=10,height=1, command=stop).place(x=600,y=TITLE+CANVAS_HEIGHT+INVERVAL/2)
closeButton = tk.Button(frame, text="Close", width=10,height=1, command=close).place(x=700,y=TITLE+CANVAS_HEIGHT+INVERVAL/2)

window.mainloop()