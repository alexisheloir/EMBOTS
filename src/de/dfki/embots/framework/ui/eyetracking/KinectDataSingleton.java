/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.ui.eyetracking;

import de.dfki.embots.framework.kinectLogger.KinectData;

/**
 *
 * @author Daniel Puschmann
 */
public class KinectDataSingleton {

    private static KinectDataSingleton instance = new KinectDataSingleton();

    //data provided by the kinect
    public float rightFootX;
    public float rightFootY;
    public float rightFootZ;
    public int rightFootConf;
    public float leftFootX;
    public float leftFootY;
    public float leftFootZ;
    public int leftFootConf;
    public float rightHipX;
    public float rightHipY;
    public float rightHipZ;
    public int rightHipConf;
    public float leftHipX;
    public float leftHipY;
    public float leftHipZ;
    public int leftHipConf;
    public float rightHandHeight;
    public int rightHandConf;
    public float leftHandHeight;
    public int leftHandConf;
    public float headX;
    public float headY;
    public float headZ;
    public int headConf;
    public float movementX;
    public float movementY;
    public float movementZ;
    public float bodyAngle;
    public int bodyAngleConf;
    public double virtualDistance;

    private String output;

    private KinectDataSingleton(){
        //shouldn´t be called from outside
    }

    public static KinectDataSingleton getInstance(){
        return instance;
    }

    public void setData(KinectData data){
        this.rightFootX = data.rightFootX;
        this.rightFootY = data.rightFootY;
        this.rightFootZ = data.rightFootZ;
        this.rightFootConf = data.rightFootConf;
        this.leftFootX = data.leftFootX;
        this.leftFootY = data.leftFootY;
        this.leftFootZ = data.leftFootZ;
        this.leftFootConf = data.leftFootConf;
        this.rightHipX = data.rightHipX;
        this.rightHipY = data.rightHipY;
        this.rightHipZ = data.rightHipZ;
        this.rightHipConf = data.rightHipConf;
        this.leftHipX = data.leftHipX;
        this.leftHipY = data.leftHipY;
        this.leftHipZ = data.leftHipZ;
        this.leftHipConf = data.leftHipConf;
        this.rightHandHeight = data.rightHandHeight;
        this.rightHandConf = data.rightHandConf;
        this.leftHandHeight = data.leftHandHeight;
        this.leftHandConf = data.leftHandConf;
        this.headX = data.headX;
        this.headY = data.headY;
        this.headZ = data.headZ;
        this.headConf = data.headConf;
        this.movementX = data.movementX;
        this.movementY = data.movementY;
        this.movementZ = data.movementZ;
        this.bodyAngle = Math.abs(data.bodyAngle);
        this.bodyAngleConf = data.bodyAngleConf;
        this.virtualDistance = data.virtualDistance;
    }

    @Override
    public String toString(){
        output = "";
        addToOutput("Right foot", rightFootX,rightFootY,rightFootZ,rightFootConf);
        addToOutput("Left foot", leftFootX, leftFootY, leftFootZ, leftFootConf);
        addToOutput("Right hip", rightHipX, rightHipY, rightHipZ, rightHipConf);
        addToOutput("Left hip", leftHipX, leftHipY, leftHipZ, leftHipConf);
        addToOutput("Movement vector", movementX, movementY,movementZ);
        addToOutput("Left hand height", leftHandHeight, leftHandConf);
        addToOutput("Right hand height", rightHandHeight, rightHandConf);
        addToOutput("Head", headX, headY, headZ, headConf);
        addToOutput("Body angle", bodyAngle,bodyAngleConf);
        addToOutput("Virtual Distance",virtualDistance,headConf);
        return output;
    }

    private void addToOutput(String name,double value,int conf){
            output += name + ": " + value + " Confidence:" + conf +"\n";
    }

    private void addToOutput(String name, float value,int conf)
    {
            output += name + ": " + value + "  Confidence:" + conf + "\n";
    }

    private void addToOutput(String name, float value1,float value2,float value3,int conf)
    {
            output += name + ": (" + value1 + "," + value2 + "," + value3 +  ")  Confidence:" + conf + "\n";
    }

    private void addToOutput(String name, float value1,float value2,float value3)
    {
            output += name + ": (" + value1 + "," + value2 + "," + value3 +  ")\n";
    }

}
