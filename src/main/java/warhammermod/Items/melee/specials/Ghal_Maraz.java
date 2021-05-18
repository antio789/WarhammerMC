package warhammermod.Items.melee.specials;

import com.google.common.collect.Lists;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Items.melee.HammerTemplate;
import warhammermod.utils.reference;

import javax.annotation.Nullable;
import java.util.List;

public class Ghal_Maraz extends HammerTemplate {
    public Ghal_Maraz(Item.Properties builder){
        super(ItemTier.NETHERITE,builder.tab(reference.warhammer),2.5F,-2.4F);
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

    public Rarity getRarity(ItemStack p_77613_1_) {
        return Rarity.EPIC;
    }


    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {

            p_77624_3_.add((new TranslationTextComponent("The Legendary Warhammer, use wisely")));

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
        if( enchantment.equals(Enchantments.MENDING)) return false;
        return super.canApplyAtEnchantingTable(stack,enchantment);

    }


}
