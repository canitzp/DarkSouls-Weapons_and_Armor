package de.canitzp.dsweaponsandarmor.weapons;

import com.google.common.collect.Multimap;
import de.canitzp.dsweaponsandarmor.DSWA;
import de.canitzp.dsweaponsandarmor.EntityUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

/**
 * @author canitzp
 */
public class ItemDSSword extends Item {

    private DSSwords swordType;
    private double attackSpeed;

    public ItemDSSword(DSSwords swordType) {
        this.swordType = swordType;
        this.setRegistryName(DSWA.MODID, "sword_" + swordType.name().toLowerCase());
        this.setUnlocalizedName(this.getRegistryName().toString());
        this.setMaxStackSize(1);
        this.setMaxDamage(swordType.getDurability() * 5);

        this.attackSpeed = DSWA.map(swordType.getWeight(), 0, DSSwords.getHeaviestWeapon(), -3.9F, 0);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if(world != null && player != null){
            tooltip.add("Physical Damage: " + this.getPhysicalDamage(player));
            tooltip.add("Magical Damage:  " + this.getMagicDamage(player));
            tooltip.add("Knockback:       " + this.getKnockback(player));
            tooltip.add("Weight:          " + this.swordType.getWeight());
        }
    }

    public float getDamageModifier(EntityPlayer player){
        return 20.0F;
    }

    public float getPhysicalDamage(EntityPlayer player){
        return this.swordType.getPhysicalDamage() / this.getDamageModifier(player) + this.getCritAddition(player);
    }

    public float getMagicDamage(EntityPlayer player){
        return this.swordType.getMagicDamage() / this.getDamageModifier(player) + this.getCritAddition(player);
    }

    public float getCritAddition(EntityPlayer player){
        if(!player.onGround){
            return (this.swordType.getCriticalDamage() - 100) / (this.getDamageModifier(player) / 4.0F);
        }
        return 0;
    }

    public float getCooldownAddition(EntityPlayer player, float oldCooldown){
        if(this.swordType.isTwoHand()){
            ItemStack main = player.getHeldItem(EnumHand.MAIN_HAND);
            ItemStack off = player.getHeldItem(EnumHand.OFF_HAND);
            if(!main.isEmpty() && !off.isEmpty()){
                return oldCooldown * 4;
            }
        }
        return oldCooldown;
    }

    public float getKnockback(EntityPlayer player){
        return this.swordType.getStability() / 50.0F + EnchantmentHelper.getKnockbackModifier(player);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return Items.DIAMOND_SWORD.getDestroySpeed(stack, state);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return Items.DIAMOND_SWORD.hitEntity(stack, target, attacker);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return Items.DIAMOND_SWORD.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return Items.DIAMOND_SWORD.canHarvestBlock(blockIn);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public int getItemEnchantability() {
        return this.swordType.isEnchantable() ? ToolMaterial.IRON.getEnchantability() : super.getItemEnchantability();
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return OreDictionary.itemMatches(new ItemStack(Items.IRON_INGOT), repair, false);
    }

    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND) {
            // Done in onLeftClickEntity
            //multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.swordType.getPhysicalDamage(), 0));
            // min: -3.9 => 9-10 seconds; -3 => 2 seconds; -2 => 1.5 seconds
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.attackSpeed, 0));
        }
        return multimap;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        float knockback = this.getKnockback(player);
        Function<Float, Float> cooldownLogic = cooldown -> ItemDSSword.this.getCooldownAddition(player, cooldown);
        if(EntityUtils.isMagic(entity)){
            return EntityUtils.damageEntity(player, stack, entity, this.getMagicDamage(player), knockback, cooldownLogic);
        } else if(EntityUtils.isPhysical(entity)){
            return EntityUtils.damageEntity(player, stack, entity, this.getPhysicalDamage(player), knockback, cooldownLogic);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}
