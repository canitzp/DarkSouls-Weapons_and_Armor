package de.canitzp.dsweaponsandarmor;

import net.minecraftforge.fml.common.Mod;

/**
 * @author canitzp
 */
@Mod(modid = DSWA.MODID, name = DSWA.MODNAME, version = DSWA.MODVERSION)
public class DSWA {

    public static final String MODID = "darksoulsweaponsandarmor";
    public static final String MODNAME = "Dark Souls - Weapons & Armor";
    public static final String MODVERSION = "@Version@";

    public static float map(float input, float minIn, float maxIn, float minOut, float maxOut) {
        return (input - minIn) * (maxOut - minOut) / (maxIn - minIn) + minOut;
    }

}
