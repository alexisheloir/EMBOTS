package de.dfki.embots.embrscript;

/**
 *
 * @author Michael Kipp
 * @author Oliver Schoenleben
 */
public abstract class EMBRConstraint implements EMBRElement
{

    public EMBRBodyGroup bodyGroup;

    public EMBRConstraint() {}

    public abstract String toScript(String ID);// enable feedback for EMBRScript script

    public EMBRConstraint(EMBRBodyGroup bodyGroup) {
        this.bodyGroup = bodyGroup;
    }

    @Override
    public void offset(long d)
    {
    }
}
