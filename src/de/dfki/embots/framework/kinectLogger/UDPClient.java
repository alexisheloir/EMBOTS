/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.kinectLogger;

import de.dfki.embots.framework.ui.eyetracking.Gaze;
import de.dfki.embots.framework.ui.eyetracking.GazeKinect;
import de.dfki.embots.framework.ui.eyetracking.KinectConsole;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 * @author dapu01
 */
public class UDPClient extends Thread{

        // Settings
	public static final int UDP_PORT = 2020;
	public static final int PACKET_SIZE = 4048;
    	private boolean stoprequested;
	private DatagramPacket packet;
	private DatagramSocket incomingSocket;
        private GazeKinect gaze;
        private KinectConsole console;

        
        /**
         * Constructor for testing purpose only
         */
        public UDPClient(){
            super();
            stoprequested = false;
            console = new KinectConsole();
            console.setVisible(true);
            try {

			// open socket connection and listen for incoming packets
			incomingSocket = new DatagramSocket(UDP_PORT);
			byte data[] = new byte[PACKET_SIZE];
			packet = new DatagramPacket(data,PACKET_SIZE);

			// set time to wait after an unsuccessful receive attempt
			incomingSocket.setSoTimeout(300);

			System.out.println("UDP connection established");
//                        this.gaze = gaze;

		} catch (SocketException e) {
			System.err.println("UDPThread_Constructor: " + e.toString());

                }

        }
        /**
         * Constructor used when using the kinect
         * @param gaze main gazecontrol class
         * @param console displays values/offers buttons to change strategy
         */
        public UDPClient(GazeKinect gaze,KinectConsole console) {
		super();
		stoprequested = false;
                this.gaze = gaze;
                this.console = console;



		try {

			// open socket connection and listen for incoming packets
			incomingSocket = new DatagramSocket(UDP_PORT);
			byte data[] = new byte[PACKET_SIZE];
			packet = new DatagramPacket(data,PACKET_SIZE);
                        
			// set time to wait after an unsuccessful receive attempt
			incomingSocket.setSoTimeout(300);
			System.out.println("UDP connection established");
		} catch (SocketException e) {
			System.err.println("UDPThread_Constructor: " + e.toString());

                }
        }

        public synchronized void requestStop(){
            stoprequested = true;
        }

        	@Override
	public void run() {
		KinectDataProcessor processor = new KinectDataProcessor();
		int packetsize;
		byte[] packetdata;
		String datastring;
		KinectData kinectdata = new KinectData();
		while (!stoprequested) {
			try {
				// read data and get length
				incomingSocket.receive(packet);
				packetsize = packet.getLength();
				packetdata = packet.getData();

			} catch (SocketTimeoutException e) {
				// suppress error output if no data available at incomingSocket
				// wait 300ms as defined in SoTimeout and try again
				continue;
			} catch(NullPointerException e){
				// suppress error output if no data available at incomingSocket
				continue;
			} catch (Exception e) {
				System.err.println("UDPThread_run(): " + e.toString());
				continue;
			}

			// transform incoming data to string of variable length
			datastring = new String(packetdata, 0, packetsize);
                        datastring = datastring.trim().replaceFirst(" ^[^<]*<\\?x", "<?x");
                        datastring = datastring.substring(0,datastring.indexOf("utf-8")+6)+datastring.substring(datastring.indexOf("utf-8")+7);
                        datastring = datastring.replaceAll("\\x0A", "");
                        datastring = datastring.replaceAll("\\x0D", "");
                        datastring = datastring.replaceAll(">    <", "><");
			kinectdata = processor.getKinectData(datastring);
                        console.setKinectData(kinectdata);
                        if(gaze!=null)
                            gaze.setKinectData(kinectdata);
		}


		// close socket connection
		try {
			if (null != incomingSocket)
				incomingSocket.close();
		} catch (Exception ex) {
		}

		System.out.println("logging terminated");
        }
                

        public static void main(String[] args){
            new UDPClient().start();
        }

}
