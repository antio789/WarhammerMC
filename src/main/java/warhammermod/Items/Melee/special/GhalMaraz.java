package warhammermod.Items.Melee.special;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Items.Melee.heavytemplate;

import javax.annotation.Nullable;
import java.util.List;


public class GhalMaraz extends heavytemplate {
    ToolMaterial Material;
    public GhalMaraz(String name, ToolMaterial material, float damage, float attspeed, boolean enabled) {
        super(name, material, damage, attspeed);
        setMaxDamage((int) (material.getMaxUses() * 1.4));
        Material=material;
    }

    public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker) {
        return true;
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

        if (!player.world.isRemote && !player.getCooldownTracker().hasCooldown(stack.getItem()))
        {

            if (player.world.rand.nextFloat() < 0.4F)
            {
                if (player.world.rand.nextFloat() < 0.4F)
                {
                    if (player.world.rand.nextFloat() < 0.3F)
                    {
                        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,350,1));
                        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,400,2));
                        player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,250,2));
                        player.getCooldownTracker().setCooldown(this, 600);
                        return false;
                    }

                    player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,200,1));
                    player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,400,1));
                    player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,250,1));
                    player.getCooldownTracker().setCooldown(this, 600);
                    return false;
                }

                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,250,0));
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,80,1));
                player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,200,0));
                player.getCooldownTracker().setCooldown(this, 500);
                return false;
            }



        }
        return false;
    }
    public int getItemEnchantability()
    {
        return this.Material.getEnchantability()+15;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add("the legendary hammer, can give you effects in combat");
    }
}