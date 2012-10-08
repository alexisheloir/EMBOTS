package de.dfki.embots.framework.ui.eyetracking.actions;

import de.dfki.embots.framework.ui.eyetracking.*;
import de.dfki.visp.actions.Action;

/**
 *
 * @author Daniel Puschmann
 */
public class GenerateScriptActionKinect implements Action{

    private GazeStrategy gazeStrat;
    private GazeBehavior gazeBehavior;
    private KinectDataSingleton kData;

    public GenerateScriptActionKinect(GazeStrategy gStrat,GazeBehavior gBehavior){
        gazeStrat = gStrat;
        gazeBehavior = gBehavior;
    }

    @Override
    public void run() {
        kData = KinectDataSingleton.getInstance();
        gazeStrat.setEMBRScript(gazeBehavior.generateScript(kData));
    }

}
