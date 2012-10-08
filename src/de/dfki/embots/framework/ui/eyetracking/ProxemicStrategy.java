/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.ui.eyetracking;

import de.dfki.embots.embrscript.EMBRScript;
import de.dfki.embots.framework.ui.eyetracking.actions.GenerateBMLAction;
import de.dfki.embots.framework.ui.eyetracking.actions.GenerateScriptActionKinect;
import de.dfki.visp.exception.NodeNotExistException;
import de.dfki.visp.graph.Machine;
import de.dfki.visp.graph.Node;
import de.dfki.visp.graph.Supernode;
import de.dfki.visp.graph.impl.ReadChannel;
import de.dfki.visp.graph.impl.condition.ConditionEquals;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dapu01
 */
public class ProxemicStrategy implements GazeStrategy{

    private GazeKinect gaze;
    private ProxemicBehavior proxB = new ProxemicBehavior();
    private Random rand = new Random();
    private int lowerRandomBound = 3000;
    private int upperRandomBound = 8000;
    private boolean isLookingAtUser;
    Node proxState;

    public ProxemicStrategy(GazeKinect gaze,Machine fsm,Supernode sn){
        this.gaze = gaze;
        proxState = sn.createNode("Proxemic_State");
        sn.setStartNode(proxState);
        try {
            sn.createEpsilonEdge(proxState, proxState);
        } catch (NodeNotExistException ex) {
            Logger.getLogger(ProxemicStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        proxState.addAction(new GenerateScriptActionKinect(this, proxB));        
    }

    @Override
    public void setOffset() {
        throw new UnsupportedOperationException("Not used in this Strategy.");
    }

    @Override
    public boolean isLookingAtUser() {
        return isLookingAtUser;
    }

    @Override
    public void setLowerRandomBound(int min) {
        lowerRandomBound = min;
    }

    @Override
    public void setUpperRandomBound(int max) {
        upperRandomBound = max;
    }

    @Override
    public void setEMBRScript(EMBRScript s) {
        gaze.setEMBRScript(s);
    }

    @Override
    public void setBMLInput(String bml) {
        gaze.setBMLInput(bml);
    }

}
