package de.dfki.embots.embrscript;

import java.util.HashMap;
import java.util.Map;

/**
 * DEPRECATED !!!
 *
 * These are the basic morph targets available in EMBRScript. They were
 * taken from FaceGen.
 *
 * This has been replace by EMBRMorphKey which includes all available
 * morph targets, including visemes.
 *
 * @author Michael Kipp
 */
public enum EMBRFacialExpression {
    BASIS ("Basis"),
    SMILE_CLOSED ("ExpSmileClosed"),
    ANGER ("ExpAnger"),
    DISGUST ("ExpDisgust"),
    FEAR ("ExpFear"),
    SAD ("ExpSad"),
    SURPRISE ("ExpSurprise"),
    SMILE_OPEN ("ExpSmileOpen"), // looks evil
    BLINK_LEFT ("ModBlinkLeft"),
    BLINK_RIGHT ("ModBlinkRight"),
    BROW_DOWN_LEFT ("ModBrowDownLeft"),
    BROW_DOWN_RIGHT ("ModBrowDownRight"),
    BROW_UP_LEFT ("ModBrowUpLeft"),
    BROW_UP_RIGHT ("ModBrowUpRight"),
    BROW_IN_LEFT ("ModBrowInLeft"),
    BROW_IN_RIGHT ("ModBrowInRight"),
    EYE_SQUINT_LEFT ("ModEyeSquintLeft"),
    EYE_SQUINT_RIGHT ("ModEyeSquintRight"),
    LOWER_EYELIDS_RAISE ("ModLookUp"),
    EYELASHES_DOWN ("ModLookDown"),
    AU26_MT_Jaw_Open ("AU26_MT_Jaw_Open"),
    AU30_MT_Jaw_L ("AU30_MT_Jaw_L"),
    AU30_MT_Jaw_R ("AU30_MT_Jaw_R"),
    AU31_MT_Jaw_Fwd ("AU31_MT_Jaw_Fwd"),
    AU27_MT_WideL ("AU27_MT_WideL"),
    AU27_MT_WideR ("AU27_MT_WideR"),
    AU18_MT_NarrowL ("AU18_MT_NarrowL"),
    AU18_MT_NarrowR ("AU18_MT_NarrowR"),
    AU42_MT_FrownL ("AU42_MT_FrownL"),
    AU42_MT_FrownR ("AU42_MT_FrownR"),
    AU9_MT_SneerL ("AU9_MT_SneerL"),
    AU9_MT_SneerR ("AU9_MT_SneerR"),
    AU46_MT_SquintL ("AU46_MT_SquintL"),
    AU46_MT_SquintR ("AU46_MT_SquintR"),
    AU2_MT_BrowUpL ("AU2_MT_BrowUpL"),
    AU2_MT_BrowUpR ("AU2_MT_BrowUpR"),
    AU4_MT_BrowDnL ("AU4_MT_BrowDnL"),
    AU4_MT_BrowDnR ("AU4_MT_BrowDnR"),
    AU4_MT_MBrowUp ("AU4_MT_MBrowUp"),
    AU1_MT_BrowDnR ("AU1_MT_BrowDnR"),
    AU1_MT_BrowDnL ("AU1_MT_BrowDnL"),
    AU1_MT_MBrowDn ("AU1_MT_MBrowDn"),
    AU44_MT_BrowSqueeze ("AU44_MT_BrowSqueeze"),
    AU12_MT_MouthL ("AU12_MT_MouthL"),
    AU12_MT_MouthRMT ("AU12_MT_MouthRMT"),
    AU5_MT_UprLipUpL ("AU5_MT_UprLipUpL"),
    AU5_MT_UprLipUpR ("AU5_MT_UprLipUpR"),
    MT_UprLipDnL ("MT_UprLipDnL"),
    MT_UprLipDnR ("MT_UprLipDnR"),
    MT_LwrLipUpL ("MT_LwrLipUpL"),
    MT_LwrLipUpR ("MT_LwrLipUpR"),
    MT_LwrLipDnL ("MT_LwrLipDnL"),
    MT_LwrLipDnR ("MT_LwrLipDnR"),
    AU33_MT_BlowCheeksL ("AU33_MT_BlowCheeksL"),
    AU33_MT_BlowCheeksR ("AU33_MT_BlowCheeksR"),
    AU36_MT_TongueOut ("AU36_MT_TongueOut"),
    AU36_MT_TongueUp ("AU36_MT_TongueUp"),
    AU36_MT_TongueTipUp ("AU36_MT_TongueTipUp"),
    AU36_MT_TongueL ("AU36_MT_TongueL"),
    AU36_MT_TongueR ("AU36_MT_TongueR"),
    AU45_MT_Blink_L ("AU45_MT_Blink_L"),
    AU45_MT_Blink_R ("AU45_MT_Blink_R");


    private String _symbol;
    private final static Map<String, EMBRFacialExpression> _symbol2enum = new HashMap<String, EMBRFacialExpression>();

    static {
        for (EMBRFacialExpression f: values())
            _symbol2enum.put(f.toSymbol(), f);
    }

    private EMBRFacialExpression(String symbol)
    {
        _symbol = symbol;
    }

    public String toSymbol() {
        return _symbol;
    }

    public static EMBRFacialExpression parseFacialExpression(String symbol) {
        return _symbol2enum.get(symbol);
    }


}
