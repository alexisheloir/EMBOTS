/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.jms.receiver;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Topic;

import eu.semaine.exceptions.MessageFormatException;
import eu.semaine.jms.IOBase.Event;
import eu.semaine.jms.message.SEMAINEMessage;
import eu.semaine.jms.message.SEMAINEMessageTestUtils;
import eu.semaine.jms.message.SEMAINEXMLMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

/**
 * A representation of a message as used in SEMAINE code.
 * It is a higher-level access method for JMS messages,
 * providing interpretations of information accessible in the message headers.
 *
 * This base class provides access to the Message body only as plain text
 * (for text messages). Subclasses should:
 * <ul>
 * <li>add any specific message format verification code in their constructor;</li>
 * <li>provide their own API for accessing the message content.</li>
 * </ul>
 *
 * @author marc
 *
 */



public class EMBOTSMessage extends SEMAINEMessage
{

    /**
     * Ugly static method whose only purpose is to fill in the Message message
     * fields which are required by the constructor of SEMAINEMessage
     * @param message
     * @return the message, with its filled fields
     */
    static Message fillMessage(Message message)
    {
        ActiveMQTextMessage myMessage = (ActiveMQTextMessage)message;//new ActiveMQTextMessage();
        //myMessage.copy((ActiveMQTextMessage)message);
        System.out.println("filling missing fields for message");
        try {
            System.out.println(((TextMessage)message).getText());
            myMessage.setLongProperty(SEMAINEMessage.USERTIME, System.currentTimeMillis());
            myMessage.setStringProperty(SEMAINEMessage.DATATYPE, "TEST");
            myMessage.setStringProperty(SEMAINEMessage.SOURCE, "JUnit");
            myMessage.setStringProperty(SEMAINEMessage.EVENT, Event.single.toString());
            myMessage.setBooleanProperty(SEMAINEXMLMessage.IS_XML, true);

            //myMessage = SEMAINEMessageTestUtils.createActiveMQTextMessage();
            //myMessage.setText(((TextMessage)message).getText());
        } catch (JMSException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(EMBOTSMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myMessage;
    }

    public EMBOTSMessage(Message message) throws MessageFormatException
    {
        super(EMBOTSMessage.fillMessage(message));
    }

}

