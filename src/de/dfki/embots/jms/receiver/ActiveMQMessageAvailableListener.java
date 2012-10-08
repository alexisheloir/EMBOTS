/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.jms.receiver;

import eu.semaine.jms.receiver.Receiver;

public interface ActiveMQMessageAvailableListener {

    public void messageAvailableFrom(SimpleJMSReceiver rcvr);
}
