package warhammermod.Items.melee;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import warhammermod.Entities.Projectile.SpearEntity;
import warhammermod.utils.inithandler.ItemsInit;
import warhammermod.utils.reference;

public class SpearTemplate extends SwordItem {
    public SpearTemplate(IItemTier tier){
        super(tier, 2,-2F,(new Properties()).tab(reference.warhammer));
        ItemsInit.ITEMS.add(this);
    }
    public ActionResult<ItemStack> use(World world, PlayerEntity playerIn, Hand handIn)  {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (stack.getDamageValue() >= stack.getMaxDamage() - 2) {
            return ActionResult.fail(stack);
        } else {
            playerIn.startUsingItem(handIn);
            return ActionResult.consume(stack);
        }
    }

        public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if(entityLiving instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity)entityLiving;

            SpearEntity entity = new SpearEntity(player,worldIn,getDamage(),stack);
            entity.shoot(player.xRot, player.yRot, 0.0F, 1.3F, 1.0F);

            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, stack);
            if (i > 0) {
                entity.setpowerDamage(i);
            }
            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, stack);
            if (k > 0) {
                entity.setknockbacklevel(k);
            }
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0) {
                entity.setSecondsOnFire(100);
            }
            worldIn.addFreshEntity(entity);
            worldIn.playSound(null,player.blockPosition(),SoundEvents.ARROW_SHOOT,SoundCategory.PLAYERS,1,1.35F/(random.nextFloat()*0.4F+1.2F)+0.5F);

            stack.hurtAndBreak(1, player, (p_220009_1_) -> {
                p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
            });
            player.inventory.removeItem(stack);
        }
    }

    public int getUseDuration(ItemStack stack) {
        return 1000;
    }



    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.SPEAR;
    }


}
