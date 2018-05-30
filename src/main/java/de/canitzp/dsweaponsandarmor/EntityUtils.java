package de.canitzp.dsweaponsandarmor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author canitzp
 */
public class EntityUtils {

    public static boolean isPhysical(Entity entity){
        return !(entity instanceof EntityVex);
    }

    public static boolean isMagic(Entity entity){
        return entity instanceof EntityWitch;
    }

    public static boolean damageEntity(EntityPlayer attacker, ItemStack weapon, Entity target, float damage, float knockback, Function<Float, Float> cooldownCalculation){
        if(attacker != null && target != null && !weapon.isEmpty() && damage > 0.0F){
            float cooldownModifier = attacker.getCooledAttackStrength(0.5F);
            cooldownModifier = cooldownCalculation.apply(cooldownModifier);
            damage *= 0.2F + cooldownModifier * cooldownModifier * 0.8F;
            attacker.resetCooldown();
            attacker.swingArm(EnumHand.MAIN_HAND);
            if(target.attackEntityFrom(DamageSource.causePlayerDamage(attacker), damage) && target instanceof EntityLivingBase && knockback > 0.0F){
                if(attacker.isSprinting()){
                    knockback *= 1.5F;
                }
                ((EntityLivingBase) target).knockBack(attacker, knockback, MathHelper.sin(attacker.rotationYaw * 0.017453292F), -MathHelper.cos(attacker.rotationYaw * 0.017453292F));
            }
            return true;
        }
        return false;
    }

}
