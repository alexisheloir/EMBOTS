/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.ui.eyetracking.actions;

import de.dfki.embots.framework.EMBOTSConstants;
import de.dfki.embots.framework.ui.eyetracking.GazeStrategy;
import de.dfki.visp.actions.Action;
import eu.semaine.jms.sender.Sender;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;

/**
 *
 * @author dapu01
 */
public class GenerateBMLAction implements Action {

    private String bml = "<bml><speech id=\"s\"><text>Why are you looking at me?</text></speech></bml>";
    private GazeStrategy gstrat;

    public GenerateBMLAction(GazeStrategy gstrat){
        this.gstrat = gstrat;
    }

    @Override
    public void run() {
        gstrat.setBMLInput(bml);
    }

}
