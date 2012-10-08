/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.ui.eyetracking;

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
import de.dfki.embots.embrscript.Triple;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 *
 * @author Daniel Puschmann
 */
public class ProxemicBehavior implements GazeBehavior{

    private static final int TIME_TO_ASSUME_POSE = 200;
    private EMBRScript embrScript;
    private PropertiesLoader propLoader= new PropertiesLoader();
    private File file;
    private FileWriter writer;

    public ProxemicBehavior(){
            file = new File("Kinect2Skript");
            try {
                writer = new FileWriter(file, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }


    public double computeTheta(KinectDataSingleton kdata){
        double a,b,v,theta;
        b = propLoader.getThetaMax();
        a = -b/3;
        v = kdata.headZ - (Math.abs(propLoader.getAlphaMax()-Math.min(kdata.bodyAngle,propLoader.getAlphaMax()))/propLoader.getAlphaMax())*propLoader.getDelta();
        theta = a*v+b;
        return theta;
    }
    
    public double computePhi(KinectDataSingleton kdata){
        double a,b,v,phi;
        b = propLoader.getPhiMax();
        a = -b/3;
        v = kdata.headZ - (Math.abs(propLoader.getAlphaMax()-Math.min(kdata.bodyAngle,propLoader.getAlphaMax()))/propLoader.getAlphaMax())*propLoader.getDelta();
        phi = a*v+b;
        return phi;
    }

    @Override
    public EMBRScript generateScript(KinectDataSingleton kdata) {
        if(kdata.headConf==1){
            embrScript = new EMBRScript();
            EMBRPoseSequence seq = new EMBRPoseSequence(GazeKinect.AGENT);
            seq.setASAP(true);
            seq.fadeIn = 200;
            seq.fadeOut = 200;
            Triple trip = computeTriple(kdata);
//            trip.x = (kdata.headX<0?kdata.headX+2:kdata.headX-2);
//            trip.z = trip.y*Math.tan(computePhi(kdata));
            EMBRLookAtConstraint el = new EMBRLookAtConstraint(EMBRBodyGroup.EYES, trip);
            EMBROrientationConstraint eo = new EMBROrientationConstraint(EMBRBodyGroup.HEAD_NECK, EMBRJoint.HEAD, EMBRNormal.Z_AXIS, trip);
            EMBRMorphTargetConstraint smile = null,fear = null;
            if(kdata.virtualDistance>1.0){
                double smileValue = kdata.virtualDistance-1.0;
                smileValue = clamp(smileValue,0,1);
                smile = new EMBRMorphTargetConstraint(EMBRMorphKey.EXP_SMILE_OPEN, smileValue);
                fear = new EMBRMorphTargetConstraint(EMBRMorphKey.EXP_FEAR,0);
            }else if(kdata.virtualDistance<0.7){
                double fearValue = -10/7*kdata.virtualDistance+1;
                fearValue = clamp(fearValue,0,1);
                fear = new EMBRMorphTargetConstraint(EMBRMorphKey.EXP_FEAR, fearValue);
                smile = new EMBRMorphTargetConstraint(EMBRMorphKey.EXP_SMILE_OPEN, 0);
            }else{
                fear = new EMBRMorphTargetConstraint(EMBRMorphKey.EXP_FEAR, 0);
                smile = new EMBRMorphTargetConstraint(EMBRMorphKey.EXP_SMILE_OPEN, 0);
            }
            EMBRPose pose = new EMBRPose(TIME_TO_ASSUME_POSE);
            pose.constraints.add(el);
            pose.constraints.add(eo);
            pose.constraints.add(smile);
            pose.constraints.add(fear);
            pose.relativeTime = true;
            seq.addPose(pose);
            embrScript.addElement(seq);
            return embrScript;
        }else{
            return new EMBRScript();
        }
    }

    @Override
    public EMBRScript generateScript(LogDataSingleton logdata) {
        throw new UnsupportedOperationException("Not used in eyetracking");
    }

    @Override
    public void setOffsetUpDown(double offset) {
        throw new UnsupportedOperationException("Method not used in Proxemic behavior");
    }

    @Override
    public void setOffsetRightLeft(double offset) {
        throw new UnsupportedOperationException("Method not used in Proxemic behavior");
    }

    public Triple computeTriple(KinectDataSingleton kdata){
        double x = 0.177*kdata.headX-0.0238;
        double v,offSetRightLeft,offSetUpDown;
        v = kdata.headZ - (Math.abs(propLoader.getAlphaMax()-Math.min(kdata.bodyAngle,propLoader.getAlphaMax()))/propLoader.getAlphaMax())*propLoader.getDelta();
        offSetRightLeft = -1*v+2;
        offSetRightLeft = (offSetRightLeft<0?0:offSetRightLeft);
        x = (x<0?x+offSetRightLeft:x-offSetRightLeft);
        double y = -kdata.headZ;
        double z = kdata.headY + 0.25;
        offSetUpDown = 0.33*v+1;
        offSetUpDown = (offSetUpDown<0?0:offSetUpDown);
        z = z-offSetUpDown;
        z = (z<0?0:z);
        try{
            writer.write("("+v+") -> oUD: ("+offSetUpDown+") target: ("+x+","+y+","+z+")");
            writer.write("\n\n");
            //writer.close();
        }catch ( IOException ex){
            ex.printStackTrace();
        }

        return new Triple(x,y,z);
    }

    public double clamp(double value,double min, double max){
        if(value<min)
            return min;
        else if(value>max)
            return max;
        else
            return value;
    }





}
