/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.kinectLogger;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author dapu01
 */
public class KinectDataProcessor {

    public KinectData getKinectData(String inputstring) {
        // parse XML
        XMLParser parser = new XMLParser(inputstring);
        // get Kinect Data
        KinectData kData = parser.getKinectData();

        return kData;
    }

}
