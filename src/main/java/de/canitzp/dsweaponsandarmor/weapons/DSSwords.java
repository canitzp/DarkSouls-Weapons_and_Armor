package de.canitzp.dsweaponsandarmor.weapons;

import de.canitzp.dsweaponsandarmor.CreativeTab;
import de.canitzp.dsweaponsandarmor.WeaponTypes;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static de.canitzp.dsweaponsandarmor.WeaponTypes.*;


/**
 * @author canitzp
 *
 * This class contains all weapons that are
 * Daggers, Straight Swords, Greatswords, Ultra Greatswords, Curved Swords, Katanas, Curved Greatswords, Piercing Swords
 */
@Mod.EventBusSubscriber
public enum DSSwords {

    // Daggers
    D_DAGGER(DAGGER, 140, 131, 105, 26, 134, 200, 140, 0.5F, false, true),
    D_GHOST_BLADE(DAGGER, 165, 127, 0, 26, 0, 100, 0, 0.5F, false, false){
        @Override
        public String getNameAddition() {
            return TextFormatting.DARK_AQUA.toString();
        }
        @Override
        public int getMagicDamage() {
            return this.getPhysicalDamage();
        }
    },
    D_PARRYING_DAGGER(DAGGER, 135, 131, 102, 26, 135, 200, 129, 0.5F, true, true){
        @Override
        public String getNameAddition() {
            return TextFormatting.GOLD.toString();
        }
    },
    // Straight Swords
    S_ASTORAS_STRAIGHT_SWORD(STRAIGHT_SWORDS, 120, 100, 120, 32, 0, 160, 0, 3.0F, false, true){
        @Override
        public String getNameAddition() {
            return TextFormatting.GOLD.toString();
        }
    },

    // Greatswords

    // Ultra Greatswords

    // Curved Sword

    // Katanas

    // Curved Greatswords

    // Piercing Swords
    ;

    private WeaponTypes weaponType;
    private int physicalDamage, criticalDamage, magicDamage, stability, fireDamage, durability, lightningDamage;
    private float weight;
    private boolean twoHand, enchantable;
    private ItemDSSword item;

    DSSwords(WeaponTypes type, int physicalDamage, int criticalDamage, int magicDamage, int stability, int fireDamage, int durability, int lightningDamage, float weight, boolean twoHand, boolean enchantable) {
        this.weaponType = type;
        this.physicalDamage = physicalDamage;
        this.criticalDamage = criticalDamage;
        this.magicDamage = magicDamage;
        this.stability = stability;
        this.fireDamage = fireDamage;
        this.durability = durability;
        this.lightningDamage = lightningDamage;
        this.weight = weight;
        this.twoHand = twoHand;
        this.enchantable = enchantable;
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event){
        for(DSSwords swordType : DSSwords.values()){
            event.getRegistry().register(swordType.item = new ItemDSSword(swordType));
            CreativeTab.add(swordType.weaponType, swordType.item);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void bake(ModelBakeEvent event){
        for(DSSwords swordType : DSSwords.values()){
            ModelLoader.setCustomModelResourceLocation(swordType.item, 0, new ModelResourceLocation(swordType.item.getRegistryName(), "inventory"));
        }
    }

    public static float getHeaviestWeapon(){
        if(true) return 24; //TODO remove line if all swords are added
        float weight = 0;
        for(DSSwords swordType : DSSwords.values()){
            weight = Math.max(weight, swordType.getWeight());
        }
        return weight;
    }

    public WeaponTypes getWeaponType() {
        return weaponType;
    }

    public int getPhysicalDamage() {
        return physicalDamage;
    }

    public int getCriticalDamage() {
        return criticalDamage;
    }

    public int getMagicDamage() {
        return magicDamage;
    }

    public int getStability() {
        return stability;
    }

    public int getFireDamage() {
        return fireDamage;
    }

    public int getDurability() {
        return durability;
    }

    public int getLightningDamage() {
        return lightningDamage;
    }

    public float getWeight() {
        return weight;
    }

    public boolean isTwoHand() {
        return twoHand;
    }

    public boolean isEnchantable() {
        return enchantable;
    }

    public ItemDSSword getItem() {
        return item;
    }

    public String getNameAddition() {
        return null;
    }
}
