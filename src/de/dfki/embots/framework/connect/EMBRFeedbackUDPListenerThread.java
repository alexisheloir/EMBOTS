

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.connect;

import eu.semaine.components.meta.MetaMessenger;
import eu.semaine.jms.sender.Sender;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author alhe01
 */
public class EMBRFeedbackUDPListenerThread extends Thread{

private static final int INPUTPORT = 5556;
private Sender _bmlFeedbackSender;
private MetaMessenger _meta;


EMBRFeedbackUDPListenerThread(Sender bmlFeedbackSender, MetaMessenger meta)
{
    _bmlFeedbackSender = bmlFeedbackSender;
    _meta = meta;
}


@Override
public void run()
{
    try {

      // Create a socket to listen on the port.
      DatagramSocket dsocket = new DatagramSocket(INPUTPORT);

      // Create a buffer to read datagrams into. If a
      // packet is larger than this buffer, the
      // excess will simply be discarded!
      byte[] buffer = new byte[2048];

      // Create a packet to receive data into the buffer
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

      // Now loop forever, waiting to receive packets and printing them.
      while (true) {
        // Wait to receive a datagram
        dsocket.receive(packet);

        // Convert the contents to a string, and display them
        String msg = new String(buffer, 0, packet.getLength());
        System.out.println(packet.getAddress().getHostName() + ": "
            + msg);
        _bmlFeedbackSender.sendTextMessage(msg,_meta.getTime());

        // Reset the length of the packet before reusing it.
        packet.setLength(buffer.length);
      }
    } catch (Exception e) {
      System.err.println(e);
    }

}

}
