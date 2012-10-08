/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.ui.eyetracking;

import de.dfki.carmina.eyeTrackerLogger.dataProcessor.LogData;
import de.dfki.embots.embrscript.*;


/**
 *
 * @author Daniel Puschmann
 */
public class HeadAwayGazeTowardBehavior implements GazeBehavior{

    private static final int TIME_TO_ASSUME_POSE = 200;
    private EMBRScript embrScript;
    double range = 2.0;
    double offsetUpDown = 0.0;
    double offsetRightLeft = 0.0;
    private boolean fear;


    @Override
    public EMBRScript generateScript(LogDataSingleton logdata) {

        embrScript =new EMBRScript();
        EMBRPoseSequence seq = new EMBRPoseSequence(Gaze.AGENT);
        seq.setASAP(true);
        seq.fadeIn = 200;
        seq.fadeOut = 200;

        //First turn away head, eyes still in contact with user
        

        Triple tripEyes = computeTriple(logdata);
        EMBRLookAtConstraint el = new EMBRLookAtConstraint(EMBRBodyGroup.EYES, tripEyes);
        Triple tripHeadDirection = new Triple(tripEyes.x+5.0,tripEyes.y,tripEyes.z-10.0);
        EMBROrientationConstraint eo = new EMBROrientationConstraint(EMBRBodyGroup.HEAD_NECK, EMBRJoint.HEAD, EMBRNormal.Z_AXIS, tripHeadDirection);
        EMBROrientationConstraint et = new EMBROrientationConstraint(EMBRBodyGroup.HEAD_NECK, EMBRJoint.HEAD, EMBRNormal.Y_AXIS, new Triple(0.0,0.0,0.0));
        EMBRPose pose = new EMBRPose(TIME_TO_ASSUME_POSE);
        pose.setHoldDuration(500);
        pose.constraints.add(el);
        pose.constraints.add(eo);
        pose.constraints.add(et);
        pose.relativeTime = true;
        seq.addPose(pose);

        //Now eyes look at same direction as the head orientation

        EMBRLookAtConstraint el2 = new EMBRLookAtConstraint(EMBRBodyGroup.EYES, tripHeadDirection);
        EMBROrientationConstraint eo2 = new EMBROrientationConstraint(EMBRBodyGroup.HEAD_NECK, EMBRJoint.HEAD, EMBRNormal.Z_AXIS, tripHeadDirection);
        EMBROrientationConstraint et2 = new EMBROrientationConstraint(EMBRBodyGroup.HEAD_NECK, EMBRJoint.HEAD, EMBRNormal.Y_AXIS, new Triple(0.0,0.0,0.0));
        EMBRPose pose2 = new EMBRPose(TIME_TO_ASSUME_POSE+1000);
        pose2.constraints.add(el2);
        pose2.constraints.add(eo2);
        pose2.constraints.add(et2);
        pose2.relativeTime = true;
        seq.addPose(pose2);

        embrScript.addElement(seq);
        return embrScript;
    }

    public Triple computeTriple(LogDataSingleton logdata) {
        double y = -2.0;
        double x = -(range / 2.0 - logdata.x_eyepos_lefteye * range);
        double z = range / 2.0 - logdata.y_eyepos_lefteye * range;
        return new Triple(x, y, z);
    }

    @Override
    public void setOffsetUpDown(double offset) {
        this.offsetUpDown = offset;
    }

    @Override
    public void setOffsetRightLeft(double offset) {
        this.offsetRightLeft = offset;
    }

    @Override
    public EMBRScript generateScript(KinectDataSingleton kdata) {
        embrScript =new EMBRScript();
        EMBRPoseSequence seq = new EMBRPoseSequence(GazeKinect.AGENT);
        seq.setASAP(true);
        seq.fadeIn = 200;
        seq.fadeOut = 200;

        //First turn away head, eyes still in contact with user


        Triple tripEyes = computeTriple(kdata);
        EMBRLookAtConstraint el = new EMBRLookAtConstraint(EMBRBodyGroup.EYES, tripEyes);
        Triple tripHeadDirection = new Triple(tripEyes.x+5.0,tripEyes.y,tripEyes.z-10.0);
        EMBROrientationConstraint eo = new EMBROrientationConstraint(EMBRBodyGroup.HEAD_NECK, EMBRJoint.HEAD, EMBRNormal.Z_AXIS, tripHeadDirection);
//        EMBROrientationConstraint et = new EMBROrientationConstraint(EMBRBodyGroup.HEAD_THORAX, EMBRJoint.HEAD, EMBRNormal.Y_AXIS, new Triple(0.0,0.0,0.0));
       
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

        EMBRPose pose = new EMBRPose(TIME_TO_ASSUME_POSE);
        pose.setHoldDuration(500);
        pose.constraints.add(el);
        pose.constraints.add(eo);
//        pose.constraints.add(et);
        pose.constraints.add(sh);
        pose.constraints.add(mt);
        pose.relativeTime = true;
        seq.addPose(pose);

        //Now eyes look at same direction as the head orientation

        EMBRLookAtConstraint el2 = new EMBRLookAtConstraint(EMBRBodyGroup.EYES, tripHeadDirection);
        EMBROrientationConstraint eo2 = new EMBROrientationConstraint(EMBRBodyGroup.HEAD_NECK, EMBRJoint.HEAD, EMBRNormal.Z_AXIS, tripHeadDirection);
        //EMBROrientationConstraint et2 = new EMBROrientationConstraint(EMBRBodyGroup.HEAD_THORAX, EMBRJoint.HEAD, EMBRNormal.Y_AXIS, new Triple(0.0,0.0,0.0));
        EMBRPose pose2 = new EMBRPose(TIME_TO_ASSUME_POSE+1000);
        pose2.constraints.add(el2);
        pose2.constraints.add(eo2);
//        pose2.constraints.add(et2);
        pose2.constraints.add(sh);
        pose2.constraints.add(mt);
        pose2.relativeTime = true;
        seq.addPose(pose2);

        embrScript.addElement(seq);
        return embrScript;
    }

    private Triple computeTriple(KinectDataSingleton kdata) {
        double x = 0.177*kdata.headX-0.0238;
        double y = -kdata.headZ;
        double z = kdata.headY + 0.25;
        return new Triple(x,y,z);
    }

    public void setFear(){
        fear = true;
    }

    public void setSmile(){
        fear = false;
    }

}
