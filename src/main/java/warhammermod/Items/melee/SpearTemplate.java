package warhammermod.Items.melee;


import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import warhammermod.Entities.SpearEntity;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.reference;

public class SpearTemplate extends SwordItem {



    public SpearTemplate(IItemTier tier){
        super(tier, 2,-2F,(new Properties()).group(reference.warhammer));
        ItemsInit.ITEMS.add(this);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (itemstack.getDamage() >= itemstack.getMaxDamage() - 2) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if(entityLiving instanceof PlayerEntity){
            PlayerEntity playerIn = (PlayerEntity)entityLiving;

            SpearEntity entity = new SpearEntity(worldIn, playerIn, stack,getAttackDamage());
            entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.3F, 1.0F);
            entity.pickupStatus= AbstractArrowEntity.PickupStatus.ALLOWED;
            if (playerIn.abilities.isCreativeMode) {
                entity.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
            }
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
            if (i > 0) {
                entity.setpowerDamage(i);
            }
            int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack);
            if (k > 0) {
                entity.setknockbacklevel(k);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0) {
                entity.setFire(100);
            }

            worldIn.addEntity(entity);
            worldIn.playMovingSound(null, entity, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F);



            stack.damageItem(2,entityLiving, (p_220040_1_) -> {
                p_220040_1_.sendBreakAnimation(playerIn.getActiveHand());
            });
            playerIn.inventory.deleteStack(stack);

        }
    }

    public int getUseDuration(ItemStack stack) {
        return 1000;
    }



    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }
}
