package warhammermod.Items.melee.specials;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import warhammermod.Items.melee.HammerTemplate;
import warhammermod.utils.reference;

public class Ghal_Maraz extends HammerTemplate {
    public Ghal_Maraz(Item.Properties builder){
        super(ItemTier.DIAMOND,builder.tab(reference.warhammer));
        attackDamage=9;
        attackSpeed=-2.35F;
    }

    public int getItemEnchantability()
    {
        return this.getTier().getEnchantmentValue()+15;
    }

    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (p_220038_0_) -> {
            p_220038_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });

        if (!attacker.level.isClientSide && attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
                if (player.level.random.nextFloat() < 0.4F) {
                    if (player.level.random.nextFloat() < 0.4F) {
                        if (player.level.random.nextFloat() < 0.3F) {
                            addeffects(player,2);
                            return false;
                        }
                        addeffects(player,1);
                        return false;
                    }
                    addeffects(player,0);
                    return false;
                }


            }
        }
        return true;
    }

    public void addeffects(PlayerEntity player,int tier){
        player.addEffect(new EffectInstance(Effects.REGENERATION, 60+(tier*50), tier));
        player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 200+(tier*60), tier));
        player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 200+(tier*60), tier));
        player.getCooldowns().addCooldown(this, 600);
    }


    public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack p_82789_2_) {
        return false;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        if( !enchantment.equals(Enchantments.MENDING)) super.canApplyAtEnchantingTable(stack,enchantment);
        return false;
    }


}
