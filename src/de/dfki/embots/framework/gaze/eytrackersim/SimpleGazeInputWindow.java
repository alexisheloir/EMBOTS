/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.gaze.eytrackersim;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import de.dfki.carmina.eyeTrackerLogger.dataProcessor.LogData;
import de.dfki.embots.framework.ui.eyetracking.*;

/**
 * Window where user can input eyetracker simulation points.
 *
 * @author Daniel Puschmann
 */
public class SimpleGazeInputWindow extends Frame{




    private Gaze gaze;
    private Rectangle bounds = this.getGraphicsConfiguration().getBounds();
    private FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
    private Panel panel = new Panel();
    private UserEyesEMBRHead userEyes = new UserEyesEMBRHead();



    public SimpleGazeInputWindow(Gaze gaze)
    {
        this.gaze = gaze;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            System.exit(0);
            }
        });


        setLocation(0+bounds.x, 0+bounds.y);
        setSize((int)bounds.getWidth(),(int)bounds.getHeight());


        panel.setLayout(flowLayout);
        panel.add(userEyes);
        add(panel);
        setVisible(true);


    }


    public void run(){


                //simulates EyetrackerData
                Rectangle rect = userEyes.getRectangle();
                Point eyePos = rect.getLocation();
//                shyStrat.getGazeAwayBehavior().setLookConstraints((float)0.5,eyePos.x);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String timestamp = dateFormat.format(date);
                float x_gazepos_lefteye = 1-((float)eyePos.getX())/((float)bounds.getWidth());
                float y_gazepos_lefteye = ((float)eyePos.getY())/((float)bounds.getHeight());
                float x_gazepos_righteye = 1-((float)eyePos.getX())/((float)bounds.getWidth());
                float y_gazepos_righteye = ((float)eyePos.getY())/((float)bounds.getHeight());
                float x_eyepos_lefteye = ((float)eyePos.getX())/((float)bounds.getWidth());
                float y_eyepos_lefteye = ((float)eyePos.getY())/((float)bounds.getHeight());
                float x_eyepos_righteye = ((float)eyePos.getX())/((float)bounds.getWidth());
                float y_eyepos_righteye = ((float)eyePos.getY())/((float)bounds.getHeight());
                float diameter_pupil_lefteye = 3;
                float diameter_pupil_righteye = 3;
                float distance_lefteye = 500;
                float distance_righteye = 500;
                long validity_lefteye = 0;
                long validity_righteye = 0;
                LogData logdata = new LogData(timestamp,x_gazepos_lefteye,y_gazepos_lefteye,x_gazepos_righteye,
                                              y_gazepos_righteye,x_eyepos_lefteye,y_eyepos_lefteye,x_eyepos_righteye,
                                              y_eyepos_righteye,diameter_pupil_lefteye,diameter_pupil_righteye,
                                              distance_lefteye,distance_righteye,validity_lefteye,validity_righteye);
                gaze.setLogData(logdata);


    }

    public void changeUserColor(boolean lookedAt){
        userEyes.changeUserColor(lookedAt);

    }


}


