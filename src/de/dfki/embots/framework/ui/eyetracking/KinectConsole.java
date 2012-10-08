/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.ui.eyetracking;

import de.dfki.embots.framework.kinectLogger.KinectData;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author dapu01
 */
public class KinectConsole extends Frame implements ActionListener{

    private GazeKinect gaze;
    private Panel panel = new Panel();
    private Button dominantButton;
    private Button shyButton;
    private Button proxButton;
    private FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
    private TextField bodyAngle;
    private TextField distance;
    private TextField virtualDist;
    private TextField state;
    private TextField handHeightLeft;
    private TextField handHeightRight;
    private PropertiesLoader propLoader;


    /**
     * Consturctor used for testing purpose only
     */
    public KinectConsole(){
        propLoader = new PropertiesLoader();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            System.exit(0);
            }
        });
        setLocation(200, 200);
        setSize(200, 220);
        dominantButton = new Button("Dominant");
        dominantButton.setBackground(Color.red.darker());
        dominantButton.setFocusable(false);
        shyButton = new Button("Shy");
        shyButton.setBackground(Color.gray.brighter());
        shyButton.setFocusable(false);
        proxButton = new Button("Proxemic");
        proxButton.setBackground(Color.gray.brighter());
        proxButton.setFocusable(false);
        dominantButton.addActionListener(this);
        shyButton.addActionListener(this);
        proxButton.addActionListener(this);
        bodyAngle = new TextField("Body angle: ");
        distance = new TextField("Distance: ");
        virtualDist = new TextField("Virtual distance: ");
        state = new TextField("State: ");
        handHeightLeft = new TextField("Hand height left: ");
        handHeightRight = new TextField("Hand height right: ");
        bodyAngle.setEditable(false);
        distance.setEditable(false);
        virtualDist.setEditable(false);
        state.setEditable(false);
        handHeightLeft.setEditable(false);
        handHeightRight.setEditable(false);
        panel.setLayout(flowLayout);
        panel.add(dominantButton);
        panel.add(shyButton);
        panel.add(proxButton);
        panel.add(bodyAngle);
        panel.add(distance);
        panel.add(virtualDist);
        panel.add(state);
        panel.add(handHeightLeft);
        panel.add(handHeightRight);
        add(panel);
        setVisible(true);
    }

    /**
     * Constructor used when using the kinect
     * @param gaze main gazecontrol class
     */
    public KinectConsole(GazeKinect gaze){
        this.gaze = gaze;
        propLoader = new PropertiesLoader();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            System.exit(0);
            }
        });
        setLocation(600, 600);
        setSize(300,300);
        dominantButton = new Button("Dominant");
        dominantButton.setBackground(Color.red.darker());
        dominantButton.setFocusable(false);
        shyButton = new Button("Shy");
        shyButton.setBackground(Color.gray.brighter());
        shyButton.setFocusable(false);
        proxButton = new Button("Proxemic");
        proxButton.setBackground(Color.gray.brighter());
        proxButton.setFocusable(false);
        dominantButton.addActionListener(this);
        shyButton.addActionListener(this);
        proxButton.addActionListener(this);
        bodyAngle = new TextField("Body angle: ");
        distance = new TextField("Distance: ");
        virtualDist = new TextField("Virtual distance: ");
        state = new TextField("State:                           ");
        handHeightLeft = new TextField("Hand height left: ");
        handHeightRight = new TextField("Hand height right: ");
        bodyAngle.setEditable(false);
        distance.setEditable(false);
        virtualDist.setEditable(false);
        state.setEditable(false);
        handHeightLeft.setEditable(false);
        handHeightLeft.setEditable(false);
        panel.setLayout(flowLayout);
        panel.add(dominantButton);
        panel.add(shyButton);
        panel.add(proxButton);
        panel.add(bodyAngle);
        panel.add(distance);
        panel.add(virtualDist);
        panel.add(state);
        panel.add(handHeightLeft);
        panel.add(handHeightRight);
        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Button b = (Button) e.getSource();
        if(b.getLabel().equals("Dominant")){
            gaze.changeStrategy("Dom");
            dominantButton.setBackground(Color.red.darker());
            shyButton.setBackground(Color.lightGray.brighter());
            proxButton.setBackground(Color.lightGray.brighter());

        }else
        if(b.getLabel().equals("Shy")){
            gaze.changeStrategy("Shy");
            shyButton.setBackground(Color.red.darker());
            dominantButton.setBackground(Color.lightGray.brighter());
            proxButton.setBackground(Color.lightGray.brighter());
        }else
        if(b.getLabel().equals("Proxemic")){
            gaze.changeStrategy("Prox");
            proxButton.setBackground(Color.red.darker());
            dominantButton.setBackground(Color.lightGray.brighter());
            shyButton.setBackground(Color.lightGray.brighter());
        }
        this.repaint();
    }

    public void setKinectData(KinectData kdata){
        if(kdata.bodyAngleConf == 1)
            bodyAngle.setText("Body Angle: "+kdata.bodyAngle);
        if(kdata.headConf == 1){
            distance.setText("Distance: "+kdata.headZ);
            virtualDist.setText("Virtual Distance: "+kdata.virtualDistance);
        }
        if(kdata.leftHandConf == 1)
            handHeightLeft.setText("Hand height left: "+kdata.leftHandHeight);
        if(kdata.rightHandConf == 1)
            handHeightRight.setText("Hand height right: "+kdata.rightHandHeight);
        this.repaint();
    }

    public void setState(String state){
        this.state.setText("State: " + state);
        this.repaint();
    }



}
