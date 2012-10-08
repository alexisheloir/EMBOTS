
package de.dfki.embots.framework.kinectLogger;

/**
 *
 * @author Daniel Puschmann
 */
public class KinectData {

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

    public KinectData(){
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
        addToOutput("Head",headX,headY,headZ,headConf);
        addToOutput("Body angle", bodyAngle,bodyAngleConf);
        return output;
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
