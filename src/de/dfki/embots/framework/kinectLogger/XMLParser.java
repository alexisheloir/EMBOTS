/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.kinectLogger;

import de.dfki.embots.framework.ui.eyetracking.PropertiesLoader;



/**
 *
 * @author Daniel Puschmann
 */
public class XMLParser {

    private String datastring;
    private PropertiesLoader propLoader = new PropertiesLoader();

    public XMLParser(String xmlstring) {
            this.datastring = xmlstring;
    }

    public KinectData getKinectData(){
        KinectData kData = new KinectData();
        kData.rightFootX = getFloatAttribute("Right_foot","X");
        kData.rightFootY = getFloatAttribute("Right_foot","Y");
        kData.rightFootZ = getFloatAttribute("Right_foot","Z");
        kData.rightFootConf = getIntAttribute("Right_foot","Confidence");
        kData.leftFootX = getFloatAttribute("Left_foot","X");
        kData.leftFootY = getFloatAttribute("Left_foot","Y");
        kData.leftFootZ = getFloatAttribute("Left_foot","Z");
        kData.leftFootConf = getIntAttribute("Left_foot","Confidence");
        kData.rightHipX = getFloatAttribute("Right_hip","X");
        kData.rightHipY = getFloatAttribute("Right_hip","Y");
        kData.rightHipZ = getFloatAttribute("Right_hip","Z");
        kData.rightHipConf = getIntAttribute("Right_hip","Confidence");
        kData.leftHipX = getFloatAttribute("Left_hip","X");
        kData.leftHipY = getFloatAttribute("Left_hip","Y");
        kData.leftHipZ = getFloatAttribute("Left_hip","Z");
        kData.leftHipConf = getIntAttribute("Left_hip","Confidence");
        kData.rightHandHeight = getFloatAttribute("Right_hand","Height");
        kData.rightHandConf = getIntAttribute("Right_hand","Confidence");
        kData.leftHandHeight = getFloatAttribute("Left_hand","Height");
        kData.leftHandConf = getIntAttribute("Left_hand","Confidence");
        kData.headX = getFloatAttribute("Head","X");
        kData.headY = getFloatAttribute("Head","Y");
        kData.headZ = getFloatAttribute("Head","Z");
        kData.headConf = getIntAttribute("Head","Confidence");
        kData.movementX = getFloatAttribute("Movement_vector","X");
        kData.movementY = getFloatAttribute("Movement_vector","Y");
        kData.movementZ = getFloatAttribute("Movement_vector","Z");
        kData.bodyAngle = getFloatAttribute("Body_angle", "Angle");
        kData.bodyAngleConf = getIntAttribute("Body_angle","Confidence");
        kData.virtualDistance = kData.headZ - (Math.abs(propLoader.getAlphaMax()-Math.min(Math.abs(kData.bodyAngle),propLoader.getAlphaMax()))/propLoader.getAlphaMax())*propLoader.getDelta();
        return kData;
    }

    private float getFloatAttribute(String nodeId,String attributeId){
        String node;
        node = datastring.substring(datastring.indexOf(nodeId));
        node = node.substring(0,node.indexOf("/>"));
        String attribute = node.substring(node.indexOf(attributeId)+attributeId.length()+2);
        attribute = attribute.substring(0, attribute.indexOf("\""));
        float value;
        try{
            value = Float.parseFloat(attribute);
        }catch(NumberFormatException ex){
            return -100;
        }
        return value;
    }

        private int getIntAttribute(String nodeId,String attributeId){
        String node = datastring.substring(datastring.indexOf(nodeId));
        node = node.substring(0,node.indexOf("/>"));
        String attribute = node.substring(node.indexOf(attributeId)+attributeId.length()+2);
        attribute = attribute.substring(0, attribute.indexOf("\""));
        int value = -100;
        try{
            value = Integer.parseInt(attribute);
        }catch(NumberFormatException ex){
            //do nothing
        }
        return value;
    }

}
