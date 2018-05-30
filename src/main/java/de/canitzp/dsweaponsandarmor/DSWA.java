package de.canitzp.dsweaponsandarmor;

import de.canitzp.dsweaponsandarmor.weapons.DSSwords;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

/**
 * @author canitzp
 */
@Mod(modid = DSWA.MODID, name = DSWA.MODNAME, version = DSWA.MODVERSION)
public class DSWA {

    public static final String MODID = "darksoulsweaponsandarmor";
    public static final String MODNAME = "Dark Souls - Weapons & Armor";
    public static final String MODVERSION = "@Version@";

    public static CreativeTabs TAB = new CreativeTabs(MODID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(DSSwords.D_DAGGER.getItem());
        }
    };

    public static float map(float input, float minIn, float maxIn, float minOut, float maxOut) {
        return (input - minIn) * (maxOut - minOut) / (maxIn - minIn) + minOut;
    }

}
