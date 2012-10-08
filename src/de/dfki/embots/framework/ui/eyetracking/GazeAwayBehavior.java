/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.ui.eyetracking;

import de.dfki.carmina.eyeTrackerLogger.dataProcessor.LogData;
import de.dfki.embots.embrscript.EMBRBodyGroup;
import de.dfki.embots.embrscript.EMBRJoint;
import de.dfki.embots.embrscript.EMBRLookAtConstraint;
import de.dfki.embots.embrscript.EMBRMorphKey;
import de.dfki.embots.embrscript.EMBRMorphTargetConstraint;
import de.dfki.embots.embrscript.EMBRNormal;
import de.dfki.embots.embrscript.EMBROrientationConstraint;
import de.dfki.embots.embrscript.EMBRPose;
import de.dfki.embots.embrscript.EMBRPoseSequence;
import de.dfki.embots.embrscript.EMBRScript;
import de.dfki.embots.embrscript.EMBRShaderConstraint;
import de.dfki.embots.embrscript.EMBRShaderKey;
import de.dfki.embots.embrscript.Triple;
import java.util.Random;

/**
 *
 * @author Daniel Puschmann
 */
public class GazeAwayBehavior implements GazeBehavior{

    private static final int TIME_TO_ASSUME_POSE = 200;
    private EMBRScript embrScript;
    private Random rand = new Random();
    private double offsetUpDown = 0.0;
    private double offsetRightLeft = 0.0;
    private boolean lookConstraint = false;
    private double range = 2.0;
    private boolean fear = false;




    @Override
    public EMBRScript generateScript(LogDataSingleton logdata) {

        embrScript = new EMBRScript();
        EMBRPoseSequence seq = new EMBRPoseSequence(Gaze.AGENT);
        seq.setASAP(true);
        seq.fadeIn=200;
        seq.fadeOut=200;
        Triple tripEyes = computeTrip(logdata);
        EMBRLookAtConstraint el = new EMBRLookAtConstraint(de.dfki.embots.embrscript.EMBRBodyGroup.EYES,tripEyes);
        float xHeadDirection = (float)tripEyes.x+((float)offsetRightLeft);
        float zHeadDirection = (float)tripEyes.z+((float)offsetUpDown);
        Triple tripHeadDirection = new Triple(xHeadDirection,-2.0,zHeadDirection);
        EMBROrientationConstraint eo = new EMBROrientationConstraint(de.dfki.embots.embrscript.EMBRBodyGroup.HEAD_THORAX,de.dfki.embots.embrscript.EMBRJoint.HEAD,EMBRNormal.Z_AXIS,tripHeadDirection);
        EMBROrientationConstraint et = new EMBROrientationConstraint(de.dfki.embots.embrscript.EMBRBodyGroup.HEAD_THORAX,de.dfki.embots.embrscript.EMBRJoint.HEAD,EMBRNormal.Y_AXIS,new Triple(0.0,0.0,0.0));
        EMBRPose pose = new EMBRPose(TIME_TO_ASSUME_POSE);
        pose.constraints.add(el);
        pose.constraints.add(eo);
        pose.constraints.add(et);
        pose.relativeTime = true;
        seq.addPose(pose);
        embrScript.addElement(seq);
        return embrScript;
    }

    @Override
    public void setOffsetUpDown(double offset) {
        this.offsetUpDown = offset;
    }

    @Override
    public void setOffsetRightLeft(double offset) {
        this.offsetRightLeft = offset;
    }

    public void setLookConstraint(boolean constraint){
        lookConstraint = constraint;
    }

    private Triple computeTrip(LogDataSingleton logdata){
        float x = rand.nextFloat() * (float) 3.0 - (float) 1.5;
        float z = rand.nextFloat() * (float) 2.0 - (float) 1.0;
        if(lookConstraint){
            double sideLookConstraint = -(range / 2.0 - logdata.x_eyepos_lefteye * range);
            while(z>=-0.2){
                z = rand.nextFloat() * (float) 2.0 - (float) 1.0;
            }
            while(x>=sideLookConstraint-0.5&&x<=sideLookConstraint+0.5){
                x = rand.nextFloat() * (float) 3.0 - (float) 1.5;
            }

        }
        return new Triple(x,-2.0,z);
    }

    @Override
    public EMBRScript generateScript(KinectDataSingleton kdata) {
        embrScript = new EMBRScript();
        EMBRPoseSequence seq = new EMBRPoseSequence(GazeKinect.AGENT);
        seq.setASAP(true);
        seq.fadeIn=200;
        seq.fadeOut=200;
        Triple tripEyes = computeTrip(kdata);
        EMBRLookAtConstraint el = new EMBRLookAtConstraint(de.dfki.embots.embrscript.EMBRBodyGroup.EYES,tripEyes);
        float xHeadDirection = (float)tripEyes.x+((float)offsetRightLeft);
        float zHeadDirection = (float)tripEyes.z+((float)offsetUpDown);
        Triple tripHeadDirection = new Triple(xHeadDirection,-2.0,zHeadDirection);
        EMBROrientationConstraint eo = new EMBROrientationConstraint(de.dfki.embots.embrscript.EMBRBodyGroup.HEAD_NECK,de.dfki.embots.embrscript.EMBRJoint.HEAD,EMBRNormal.Z_AXIS,tripHeadDirection);
//        EMBROrientationConstraint et = new EMBROrientationConstraint(de.dfki.embots.embrscript.EMBRBodyGroup.HEAD_THORAX,de.dfki.embots.embrscript.EMBRJoint.HEAD,EMBRNormal.Y_AXIS,new Triple(0.0,0.0,0.0));
        EMBRPose pose = new EMBRPose(TIME_TO_ASSUME_POSE);

        // fearing/smiling when user comes near
        double smileFearValue;
        if(kdata.headZ<=1.5){
            smileFearValue = 1.0;
        }else if(kdata.headZ>=3.0){
            smileFearValue = 0.0;
        }else{
            smileFearValue = -2*kdata.headZ/3 + 2;
        }
        EMBRMorphTargetConstraint sh = null,mt = null;
        if(fear){
            sh = new EMBRMorphTargetConstraint(EMBRMorphKey.EXP_FEAR,smileFearValue);
            mt = new EMBRMorphTargetConstraint(EMBRMorphKey.EXP_SMILE_OPEN, 0);
        }else{
            mt = new EMBRMorphTargetConstraint(EMBRMorphKey.EXP_SMILE_OPEN, smileFearValue);
            sh = new EMBRMorphTargetConstraint(EMBRMorphKey.EXP_FEAR,0);
        }

        pose.constraints.add(el);
        pose.constraints.add(eo);
//        pose.constraints.add(et);
        pose.constraints.add(sh);
        pose.constraints.add(mt);
        pose.relativeTime = true;
        seq.addPose(pose);
        embrScript.addElement(seq);
        return embrScript;
    }

    private Triple computeTrip(KinectDataSingleton kdata){
        float x = rand.nextFloat()*(float) 3.0 - (float) 1.5;
        float z = rand.nextFloat()*(float) 2.0 - (float) 1.0;
        if(lookConstraint){
            while(x>=(0.177*kdata.headX-0.0238)-0.5&&x<=(0.177*kdata.headX-0.0238)+0.5){
                x = rand.nextFloat();
            }
        }
        return new Triple(x,-2.0,z);
    }

    public void setFear(){
        fear = true;
    }

    public void setSmile(){
        fear = false;
    }



}
