/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.bml.feedback;

/**
 *
 * @author alhe01
 */
public class BML1_0WarningFeedback extends BML1_0Feedback {

    protected Subtype _subtype;

    public enum Subtype
    {

       WARNING01 , WARNING02, WARNING03, WARNING04
    }

    public BML1_0WarningFeedback(Type _type, Subtype _subtype, String _characterID, String bmlBlockRef, String _description) {
        super(_type, bmlBlockRef, _characterID);
        this._subtype = _subtype;
        this._description = _description;
    }

    public Subtype getSubtype() {
        return _subtype;
    }

    public void setSubtype(Subtype _subtype) {
        this._subtype = _subtype;
    }

    public String toXML()
    {
        String type = "";

        switch(_subtype)
        {
            case WARNING01:
                type = "PARSINGFAILURE";
                break;
            case WARNING02:
                type = "NOSUCHTARGET";
                break;
            case WARNING03:
                type = "ANIMATIONENGINENOTCONNECTED";
                break;
            case WARNING04:
                type = "UNABLETORESOLVETIMINGCONSTRAINTS";
                break;
            default:
                type = "undetermined";
                break;
        }

        StringBuilder sb = new StringBuilder("<warning ");
        if (_referenceToBmlBlock != null) {
            sb.append(" bmlid=\"" + _referenceToBmlBlock+ "\" ");
        }
        if (_characterID != null) {
            sb.append(" characterid=\"" + _characterID + "\" ");
        }
        sb.append("type=\"" + type + "\" >\n");
        sb.append(_description);
        sb.append("\n </warning>");
        return sb.toString();
    }
}
