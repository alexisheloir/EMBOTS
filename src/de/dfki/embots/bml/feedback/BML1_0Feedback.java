package de.dfki.embots.bml.feedback;

/**
 * Represents a feedback message following the (hopefully )latest BML 1.0
 * specification. Can be of the following types:
 *
 * Status, Exception or Solution (later Warning). The feedback has the following components:
 *
 * - type (exception, status or solution)
 * - subtype TBD
 * - reference to BML block that caused this feedback (optional)
 * - description (should be non-empty)
 *
 * @author Alexis Heloir
 */



public abstract class BML1_0Feedback {
    protected Type _type;
    protected String _characterID;
    protected String _source = null; // optional
    protected String _referenceToBmlBlock = null; // optional
    protected String _description; // compulsory

    public enum Type
    {

        PROGRESS, EXCEPTION, SOLUTION, WARNING;
    }

    public BML1_0Feedback(Type _type, String _characterID, String bmlBlockRef) {
        this._referenceToBmlBlock = bmlBlockRef;
        this._type = _type;
        this._characterID = _characterID;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String _description) {
        this._description = _description;
    }

    public String getReferenceToBmlBlock() {
        return _referenceToBmlBlock;
    }

    public void setReferenceToBmlBlock(String _referenceToBmlBlock) {
        this._referenceToBmlBlock = _referenceToBmlBlock;
    }

    public String getSource() {
        return _source;
    }

    public void setSource(String _source) {
        this._source = _source;
    }

    public Type getType() {
        return _type;
    }

    public void setType(Type _type) {
        this._type = _type;
    }

    public String getCharacterID() {
        return _characterID;
    }

    public void setCharacterID(String _characterID) {
        this._characterID = _characterID;
    }

    public abstract String toXML();
}
