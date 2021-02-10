package warhammermod.Items.melee.special;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import warhammermod.Items.melee.Hammertemplate;
import warhammermod.util.reference;

public class Ghal_Maraz extends Hammertemplate {
    public Ghal_Maraz(Properties builder){
        super(ItemTier.DIAMOND,builder.group(reference.warhammer));
        attackDamage=9;
        attackSpeed=-2.35F;
    }

    public int getItemEnchantability()
    {
        return this.getTier().getEnchantability()+15;
    }

    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        if (!attacker.world.isRemote && attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            if (!player.getCooldownTracker().hasCooldown(stack.getItem())) {

                if (player.world.rand.nextFloat() < 0.4F) {
                    if (player.world.rand.nextFloat() < 0.4F) {
                        if (player.world.rand.nextFloat() < 0.3F) {
                            //livingentity.addPotionEffect(new EffectInstance(Effects.WITHER, 60, 1));
                            player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 350, 1));
                            player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 400, 2));
                            player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 250, 2));
                            player.getCooldownTracker().setCooldown(this, 600);
                            return false;
                        }

                        player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 1));
                        player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 400, 1));
                        player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 250, 1));
                        player.getCooldownTracker().setCooldown(this, 600);
                        return false;
                    }

                    player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 250, 0));
                    player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 80, 1));
                    player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 200, 0));
                    player.getCooldownTracker().setCooldown(this, 500);
                    return false;
                }


            }
        }
        return true;
    }



    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }


}
