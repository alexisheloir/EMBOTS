/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.ui.eyetracking;

import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dapu01
 */
public class PropertiesLoader {

    int thetaMin, thetaNeut, thetaMax, phiMin, phiNeut, phiMax, alphaMax;
    double dMin, dNeut, dMax, delta;

    public PropertiesLoader(){
        Properties prop = new Properties();
        BufferedInputStream buf = null;
        try {
            buf = new BufferedInputStream(new FileInputStream("C:\\EMBOTS\\Daniel\\EMBOTS2\\config\\kinect.properties"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            prop.load(buf);
            buf.close();
        } catch (IOException ex) {
            Logger.getLogger(PropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        thetaMin = Integer.parseInt(prop.getProperty("thetaMin"));
        thetaNeut = Integer.parseInt(prop.getProperty("thetaNeut"));
        thetaMax = Integer.parseInt(prop.getProperty("thetaMax"));
        phiMin = Integer.parseInt(prop.getProperty("phiMin"));
        phiNeut = Integer.parseInt(prop.getProperty("phiNeut"));
        phiMax = Integer.parseInt(prop.getProperty("phiMax"));
        alphaMax = Integer.parseInt(prop.getProperty("alphaMax"));
        dMin = Double.parseDouble(prop.getProperty("dMin"));
        dNeut = Double.parseDouble(prop.getProperty("dNeut"));
        dMax = Double.parseDouble(prop.getProperty("dMax"));
        delta = Double.parseDouble(prop.getProperty("delta"));
    }

    public int getAlphaMax() {
        return alphaMax;
    }

    public double getdMax() {
        return dMax;
    }

    public double getdMin() {
        return dMin;
    }

    public double getdNeut() {
        return dNeut;
    }

    public double getDelta() {
        return delta;
    }

    public int getPhiMax() {
        return phiMax;
    }

    public int getPhiMin() {
        return phiMin;
    }

    public int getPhiNeut() {
        return phiNeut;
    }

    public int getThetaMax() {
        return thetaMax;
    }

    public int getThetaMin() {
        return thetaMin;
    }

    public int getThetaNeut() {
        return thetaNeut;
    }

    
}
