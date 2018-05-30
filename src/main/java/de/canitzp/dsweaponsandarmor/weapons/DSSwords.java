package de.canitzp.dsweaponsandarmor.weapons;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author canitzp
 *
 * This class contains all weapons that are
 * Daggers, Straight Swords, Greatswords, Ultra Greatswords, Curved Swords, Katanas, Curved Greatswords, Piercing Swords
 */
@Mod.EventBusSubscriber
public enum DSSwords {

    // Daggers
    D_DAGGER(140, 131, 105, 26, 134, 200, 140, 0.5F, false, true),
    D_PARRYING_DAGGER(135, 131, 102, 26, 135, 200, 129, 22F, true, true),
    // Straight Swords

    // Greatswords

    // Ultra Greatswords

    // Curved Sword

    // Katanas

    // Curved Greatswords

    // Piercing Swords
    ;

    private int physicalDamage, criticalDamage, magicDamage, stability, fireDamage, durability, lightningDamage;
    private float weight;
    private boolean twoHand, enchantable;
    private ItemDSSword item;

    DSSwords(int physicalDamage, int criticalDamage, int magicDamage, int stability, int fireDamage, int durability, int lightningDamage, float weight, boolean twoHand, boolean enchantable) {
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
        float weight = 0;
        for(DSSwords swordType : DSSwords.values()){
            weight = Math.max(weight, swordType.getWeight());
        }
        return weight;
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
}
