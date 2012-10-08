/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dfki.embots.bml.feedback;

/**
 *
 * @author alhe01
 */
public class BML1_0SolutionFeedback extends BML1_0Feedback {
    
    protected Subtype _subtype;
    private String _end;
    private String _start;

    public enum Subtype
    {

       BLOCK , BEHAVIORS
    }

    public BML1_0SolutionFeedback(Type _type, Subtype _subType, String _characterID, String bmlBlockRef, String _description) {
        super(_type, _characterID, bmlBlockRef);
        this._subtype = _subType;
        this._description = _description;
    }

    public BML1_0SolutionFeedback(Type _type, Subtype _subType, String _characterID, String bmlBlockRef, String _description, String _start, String _end) {
        super(_type, _characterID, bmlBlockRef);
        this._subtype = _subType;
        this._description = _description;
    }

    public Subtype getSubtype() {
        return _subtype;
    }

    public void setSubtype(Subtype _subtype) {
        this._subtype = _subtype;
    }

    public String getEnd() {
        return _end;
    }

    public void setEnd(String _end) {
        this._end = _end;
    }

    public String getStart() {
        return _start;
    }

    public void setStart(String _start) {
        this._start = _start;
    }


    public String toXML()
    {
        String type = "";


        StringBuilder sb = new StringBuilder("<solutionFeedback>\n");

        switch(_subtype)
        {
            case BLOCK:
                sb.append("<bml id=\"" + _referenceToBmlBlock +"\"");
                sb.append(" globalStart=\"" + _start + "\"");
                sb.append(" globalEnd=\"" + _end + "\"");
                sb.append("/>");
                break;
            case BEHAVIORS:
                sb.append(_description);//todo: change times to relative (wrt. behavoir block start)
                break;
            default:
                type = "undetermined";
                break;
        }
        sb.append("\n </solutonFeedback>");
        return sb.toString();
    }
}
