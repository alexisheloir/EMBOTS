package de.dfki.embots.embrscript;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of the EMBR facial morph targets.
 * 
 * @author Oliver Schoenleben
 * @author Michael Kipp
 */
public enum EMBRMorphKey
{

    UNDEFINED("undefined"), //~ keep?
    BASIS("Basis"),
    EXP_SMILE_CLOSED("ExpSmileClosed"),
    EXP_ANGER("ExpAnger"),
    EXP_DISGUST("ExpDisgust"),
    EXP_FEAR("ExpFear"),
    EXP_SAD("ExpSad"),
    EXP_HAPPY("ExpHappy"),
    EXP_SURPRISE("ExpSurprise"),
    EXP_SMILE_OPEN("ExpSmileOpen"),
    MOD_BLINK_LEFT("ModBlinkLeft"),
    MOD_BLINK_RIGHT("ModBlinkRight"),
    MOD_SQUINT_RIGHT("ModEyeSquintRight"),
    MOD_SQUINT_LEFT("ModEyeSquintLeft"),
    MOD_BROW_DOWN_LEFT("ModBrowDownLeft"),
    MOD_BROW_DOWN_RIGHT("ModBrowDownRight"),
    MOD_BROW_IN_RIGHT("ModBrowInRight"),
    MOD_BROW_IN_LEFT("ModBrowInLeft"),
    MOD_BROW_UP_LEFT("ModBrowUpLeft"),
    MOD_BROW_UP_RIGHT("ModBrowUpRight"),
    MOD_LOOK_DOWN("ModLookDown"),
    MOD_LOOK_LEFT("ModLookLeft"),
    MOD_LOOK_RIGHT("ModLookRight"),
    MOD_LOOK_UP("ModLookUp"),
    PHON_AAH("Phonaah"),
    PHON_B_M_P("PhonB,M,P"),
    PHON_BIG_AAH("Phonbigaah"),
    PHON_CH_J_SH("Phonch,J,sh"),
    PHON_D_S_T("PhonD,S,T"),
    PHON_EE("Phonee"),
    PHON_EH("Phoneh"),
    PHON_F_V("PhonF,V"),
    PHON_I("Phoni"),
    PHON_K("PhonK"),
    PHON_N("PhonN"),
    PHON_OH("Phonoh"),
    PHON_OOH_Q("Phonooh,Q"),
    PHON_R("PhonR"),
    PHON_TH("Phonth"),
    PHON_W("PhonW"),
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

    private static final Map<String,EMBRMorphKey> lookup
          = new HashMap<String,EMBRMorphKey>();

    static
    {
        for(EMBRMorphKey s : EnumSet.allOf(EMBRMorphKey.class))
            lookup.put(s.toScript(), s);
    }

    EMBRMorphKey(String s)
    {
        _symbol = s;
    }

    public String toScript()
    {
        return _symbol;
    }

    /**
     * Maps string to enum entity.
     */
    public static EMBRMorphKey get(String scriptChunck)
    {
          return lookup.get(scriptChunck);
    }
    
}
