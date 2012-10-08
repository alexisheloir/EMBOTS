package de.dfki.embots.bml.feedback;

/**
 * Represents a feedback message following the (hopefully )latest BML 1.0
 * specification. Can be of the following types:
 *
 * Status, Exception or Solution (later Warning). The feedback has the following
 * components:
 *
 * - type (exception, status or solution)
 * - subtype TBD
 * - reference to BML block that caused this feedback (optional)
 * - description (should be non-empty)
 *
 * @author Alexis Heloir
 */

public class BML1_0ProgressFeedback extends BML1_0Feedback {

    protected Subtype _subtype;
    protected String _time;

    public enum Subtype
    {

        STARTED, FINISHED, SYNC_POINT_PROGRESS
    }


    public BML1_0ProgressFeedback(Type _type, Subtype _subtype, String bmlBlockRef, String _characterID, String _time ) {
        super(_type, bmlBlockRef, _characterID);
        this._subtype = _subtype;
        this._time = _time;

    }

    public Subtype getSubtype() {
        return _subtype;
    }

    public void setSubtype(Subtype _subtype) {
        this._subtype = _subtype;
    }

    /**
     * @return XML representation of the feedback message.
     */
    public String toXML()
    {
        String type = "";
        
        switch(_subtype)
        {
            case STARTED:
                type = "blockstart";
                break;
            case FINISHED:
                type = "blockend";
                break;
            case SYNC_POINT_PROGRESS:
                type = "syncPointProgress";
                break;
            default:
                type = "undetermined";
                break;
        }
        //"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        StringBuilder sb = new StringBuilder( "<" + type + " ");
        if (_referenceToBmlBlock != null) {
            sb.append(" id=\"" + _referenceToBmlBlock+ "\" ");
        }
        if (_characterID != null) {
            sb.append(" characterId=\"" + _characterID + "\"");
        }
        sb.append(" time=\""+ _time +"\"");

        sb.append("/>");

        return sb.toString();
    }    
}
