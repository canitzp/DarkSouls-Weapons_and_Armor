package de.canitzp.dsweaponsandarmor;

import de.canitzp.dsweaponsandarmor.weapons.DSSwords;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author canitzp
 */
public class CreativeTab extends CreativeTabs {

    private static Map<WeaponTypes, NonNullList<ItemStack>> tabRowsUnfinished = new HashMap<>();

    public CreativeTab() {
        super(DSWA.MODID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(DSSwords.D_DAGGER.getItem());
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }

    @Override
    public void displayAllRelevantItems(NonNullList<ItemStack> stacks) {
        for(WeaponTypes weaponType : WeaponTypes.values()){
            NonNullList<ItemStack> weapons = tabRowsUnfinished.get(weaponType);
            if(weapons != null){
                int i = weapons.size();
                for(int j = 0; (i + j) % 9 != 0; j++){
                    weapons.add(ItemStack.EMPTY);
                }
                stacks.addAll(weapons);
            }
        }
    }

    public static void add(WeaponTypes weaponType, ItemStack stack){
        NonNullList<ItemStack> stacks = tabRowsUnfinished.getOrDefault(weaponType, NonNullList.create());
        stacks.add(stack);
        tabRowsUnfinished.put(weaponType, stacks);
    }

    public static void add(WeaponTypes weaponType, Item item){
        add(weaponType, new ItemStack(item));
    }
}
