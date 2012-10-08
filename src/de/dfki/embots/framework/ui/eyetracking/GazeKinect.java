/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.framework.ui.eyetracking;

import de.dfki.embots.bml.lex.BehaviorLexicon;
import de.dfki.embots.embrscript.EMBRScript;
import de.dfki.embots.embrscript.EMBRScriptReader;
import de.dfki.embots.framework.EMBOTSConstants;
import de.dfki.embots.framework.behavior.GestureGenerator;
import de.dfki.embots.framework.gaze.eytrackersim.RandomSetting;
import de.dfki.embots.framework.kinectLogger.KinectData;
import de.dfki.embots.framework.kinectLogger.UDPClient;
import de.dfki.embots.framework.ui.ModuleErrorDialog;
import de.dfki.embots.framework.ui.eyetracking.actions.ChangeStartNodeAction;
import de.dfki.visp.exception.ChannelNotExistException;
import de.dfki.visp.exception.NodeNotExistException;
import de.dfki.visp.graph.Machine;
import de.dfki.visp.graph.Node;
import de.dfki.visp.graph.Supernode;
import de.dfki.visp.graph.SupernodeStateChangeEvent;
import de.dfki.visp.listener.GraphListener;
import de.dfki.visp.graph.impl.MachineImpl;
import de.dfki.visp.graph.impl.ReadChannel;
import de.dfki.visp.graph.impl.condition.ConditionAnd;
import de.dfki.visp.graph.impl.condition.ConditionEquals;
import eu.semaine.components.Component;
import eu.semaine.jms.message.SEMAINEMessage;
import eu.semaine.jms.receiver.Receiver;
import eu.semaine.jms.sender.Sender;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import javax.jms.JMSException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class for gaze control using the Kinect
 * @author Daniel Puschmann
 */
public class GazeKinect extends Component implements GraphListener{

    public static final String AGENT = "Alfonse";
    private static final String NAME = "GazeKinect";
    private static final String LOOKED_AT_VARIABLE = "LookedAt";
    private Sender embrSender;
    private EMBRScript currentEmbrScript;
    private BehaviorLexicon _lexicon;
    private static final File TEMP_DIR = new File("../temp");
    private static final String LAST_EMBR_COMMAND_FILE = "last_embrscript.embr";
    private String currentGazeStratName;
    private GazeStrategy currentGazeStrat;
    private Hashtable<String,GazeStrategy> strategies = new Hashtable<String,GazeStrategy>();
    private RandomSetting randSet;
    private MachineImpl _fsm;
    private KinectDataSingleton data;
    private UserStartsLookingAtEvent lookAtEvent = new UserStartsLookingAtEvent();
    private UserStartsLookingAwayEvent lookAwayEvent = new UserStartsLookingAwayEvent();
    private boolean startUp = true;
    private boolean lookedAt = true;
    private Sender bmlSender;
    private UDPClient udp;
    private KinectConsole kinectConsole;
    private int count = 0;
    

    public GazeKinect() throws JMSException{
        super(NAME);
         embrSender = new Sender(EMBOTSConstants.EMBRSCRIPT_TYPE, "String", NAME);
        senders.add(embrSender);
        currentEmbrScript = new EMBRScript();
        data = KinectDataSingleton.getInstance();
        _fsm = new MachineImpl("GazeKinect");
        _fsm.createChannel("State");
        _fsm.createChannel("StrategyDom");
        _fsm.createChannel("StrategyShy");
        _fsm.createChannel("StrategyProx");
        Supernode shy = _fsm.createSupernode("Shy");
        Supernode dom = _fsm.createSupernode("Dom");
        Supernode prox = _fsm.createSupernode("Prox");
        _fsm.setStartNode(dom);
        _fsm.addVariable(LOOKED_AT_VARIABLE,true);
        _fsm.addListener(this);
        strategies.put(shy.getName(),new ShyGazeStrategyKinect(this,_fsm, shy));
        strategies.put(dom.getName(),new DominantGazeStrategyKinect(this,_fsm,dom));
        strategies.put(prox.getName(),new ProxemicStrategy(this, _fsm, prox));
        try{
            ConditionAnd domLook = new ConditionAnd();
            domLook.addCondition(new ConditionEquals(new ReadChannel(_fsm, "StrategyDom"), new StrategyChangeEvent("Dom")));
            domLook.addCondition(new ConditionEquals(MachineImpl._variables.get(LOOKED_AT_VARIABLE), true));
            ConditionAnd domNotLook = new ConditionAnd();
            domNotLook.addCondition(new ConditionEquals(new ReadChannel(_fsm, "StrategyDom"), new StrategyChangeEvent("Dom")));
            domNotLook.addCondition(new ConditionEquals(MachineImpl._variables.get(LOOKED_AT_VARIABLE), false));
            ConditionAnd shyLook = new ConditionAnd();
            shyLook.addCondition(new ConditionEquals(new ReadChannel(_fsm, "StrategyShy"), new StrategyChangeEvent("Shy")));
            shyLook.addCondition(new ConditionEquals(MachineImpl._variables.get(LOOKED_AT_VARIABLE), true));
            ConditionAnd shyNotLook = new ConditionAnd();
            shyNotLook.addCondition(new ConditionEquals(new ReadChannel(_fsm, "StrategyShy"), new StrategyChangeEvent("Shy")));
            shyNotLook.addCondition(new ConditionEquals(MachineImpl._variables.get(LOOKED_AT_VARIABLE), false));
            _fsm.createInterruptEdge(dom, shy, shyLook).addAction(new ChangeStartNodeAction(shy));
            _fsm.createInterruptEdge(dom, shy, shyNotLook).addAction(new ChangeStartNodeAction(shy));
            _fsm.createInterruptEdge(shy,dom,domLook).addAction(new ChangeStartNodeAction(dom));
            _fsm.createInterruptEdge(shy,dom,domNotLook).addAction(new ChangeStartNodeAction(dom));
            _fsm.createInterruptEdge(dom, prox, new ConditionEquals(new ReadChannel(_fsm,"StrategyProx"), new StrategyChangeEvent("Prox")));
            _fsm.createInterruptEdge(shy, prox, new ConditionEquals(new ReadChannel(_fsm,"StrategyProx"), new StrategyChangeEvent("Prox")));
            _fsm.createInterruptEdge(prox, dom, domLook).addAction(new ChangeStartNodeAction(dom));
            _fsm.createInterruptEdge(prox, dom, domNotLook).addAction(new ChangeStartNodeAction(dom));
            _fsm.createInterruptEdge(prox, shy, shyLook).addAction(new ChangeStartNodeAction(shy));
            _fsm.createInterruptEdge(prox, shy, shyNotLook).addAction(new ChangeStartNodeAction(shy));
        }catch (NodeNotExistException e){
            e.printStackTrace();
        }
        currentGazeStratName = dom.getName();
        currentGazeStrat = strategies.get(dom.getName());
    }

    @Override
    public void act() throws JMSException {
        if(startUp){
            kinectConsole = new KinectConsole(this);
            kinectConsole.setVisible(true);
            _fsm.start();
            udp = new UDPClient(this,kinectConsole);
            udp.start();
            startUp = false;
            return;
        }
        if(!currentEmbrScript.getElements().isEmpty()){
            if(data.bodyAngleConf==1){
                if(data.bodyAngle<20&&!lookedAt){
                    lookedAt = true;
                    try {
                        _fsm.addToChannel("State", lookAtEvent);
                        _fsm.assignVariable(LOOKED_AT_VARIABLE, lookedAt);
                    } catch (ChannelNotExistException ex) {
                        Logger.getLogger(GazeKinect.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(data.bodyAngle>20&&lookedAt){
                    lookedAt = false;
                    try {
                        _fsm.addToChannel("State", lookAwayEvent);
                        ((MachineImpl)_fsm).assignVariable(LOOKED_AT_VARIABLE, lookedAt);
                    } catch (ChannelNotExistException ex) {
                        Logger.getLogger(GazeKinect.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }



            sendCurrentScriptToEMBR();



        }
    }

    @Override
    public void react(SEMAINEMessage m) throws Exception{
        String help="";
        String strategy="";
        try{
            if(m.getTopicName().equals(EMBOTSConstants.BML_INPUT_TYPE)){
                String bml = m.getText();
                if(bml.contains("strategy")){
                    help = bml.substring(bml.indexOf("strategy=\"")+10);
                    strategy = help.substring(0,help.indexOf("\""));
                    if(strategy!=null&&!strategy.equals("")){
                        changeStrategy(strategy);
                    }
                }
            }
        }catch(RuntimeException e){
            ModuleErrorDialog.show(NAME, e.getStackTrace());
            log.error(NAME + " failed! " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void customStartIO() throws Exception {
//        randSet = new RandomSetting(this);
//        randSet.setVisible(true);
        

        try {
            bmlSender = new Sender(EMBOTSConstants.BML_INPUT_TYPE, "String", NAME);
            receivers.add(new Receiver(EMBOTSConstants.BML_INPUT_TYPE));
        } catch (JMSException ex) {
            Logger.getLogger(Gaze.class.getName()).log(Level.SEVERE, null, ex);
        }
        senders.add(bmlSender);

        // read lexicon
        EMBRScriptReader rd1 = new EMBRScriptReader();
        _lexicon = rd1.readLexicon(new File(EMBOTSConstants.EMBRSCRIPT_LEXICON_DIR));

        // print all lexemes
        log.info("Loaded lexicon [" + _lexicon.getLexemes().size() + " entries]");
    }

    /**
     * sendToEMBR sends current script to EMBR and clears current script.
     */
    private void sendCurrentScriptToEMBR() {
        sendCurrentScriptToEMBR(true);
    }

    /**
     * sendToEMBR sends current script to EMBR
     * @param absolute true = clears current script
     */
    private void sendCurrentScriptToEMBR(boolean absolute) {

        log.debug("EMBR message dispatched " + ((absolute) ? "(absolute)" : "(relative)"));
        try {

//            String script = (absolute) ? currentEmbrScript.toScript() : currentEmbrScript.toRelScript();
//            String script = (absolute) ? embrScript.toScript() : embrScript.toRelScript();
            String script = currentEmbrScript.createScript(absolute);

            // send to EMBR component
            try {
                embrSender.sendTextMessage(script, meta.getTime());
            } catch (JMSException ex) {
                Logger.getLogger(GestureGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }

            // also write to file for debugging
            if (!TEMP_DIR.exists()) {
                TEMP_DIR.mkdir();
            }

            BufferedWriter wr = new BufferedWriter(new FileWriter(new File(TEMP_DIR, LAST_EMBR_COMMAND_FILE)));
            wr.write(script);
            wr.newLine();
            wr.flush();
            wr.close();

            currentEmbrScript.clear();
        } catch (IOException ex) {
            Logger.getLogger(GestureGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeStrategy(String strategy) {
        this.currentGazeStrat = strategies.get(strategy);
        if(!strategy.equals("Prox"))
            currentGazeStrat.setOffset();
//        randSet.setToInitialValues();
        try{
            _fsm.addToChannel("Strategy"+strategy, new StrategyChangeEvent(strategy));
        }catch (ChannelNotExistException e){
            e.printStackTrace();
        }
     }

     public GazeStrategy getGazeStrategy(){
        return currentGazeStrat;
     }

     public void setEMBRScript(EMBRScript e){
        currentEmbrScript = e;
     }

     public void setBMLInput(String bml){
        try {
            bmlSender.sendTextMessage(bml, System.currentTimeMillis());
        } catch (JMSException ex) {
            Logger.getLogger(Gaze.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

     public void setKinectData(KinectData kdata){
         data.setData(kdata);
//         kinectConsole.setKinectData(kdata);
//         SupernodeStateChangeEvent event = _fsm.getCurrentEvent();
//         Node currentNode = ((Node)event.getCurrentGraphObject());
//         kinectConsole.setState(currentNode.getName());
     }

    @Override
    public void stateChanged(String string) {
        kinectConsole.setState(string);
    }

}
