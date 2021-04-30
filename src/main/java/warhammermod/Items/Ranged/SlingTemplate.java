package warhammermod.Items.Ranged;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import warhammermod.Entities.Projectile.StoneEntity;
import warhammermod.utils.inithandler.ItemsInit;

public class SlingTemplate extends BowItem {
    public float charging;

    private final double baseDamage = 1.0D;

    public SlingTemplate(Item.Properties p_i50052_1_) {
        super(p_i50052_1_);
        ItemsInit.ITEMS.add(this);
    }

    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        charging = Math.min(1,(stack.getUseDuration()- count) / 20.0F);
    }


    public ItemStack findAmmo(PlayerEntity player) {
        if (this.isAmmo(player.getItemInHand(Hand.OFF_HAND))) {
            return player.getItemInHand(Hand.OFF_HAND);
        } else if (this.isAmmo(player.getItemInHand(Hand.MAIN_HAND))) {
            return player.getItemInHand(Hand.MAIN_HAND);
        } else {
            for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
                ItemStack itemstack = player.inventory.getItem(i);
                if (this.isAmmo(itemstack)) {
                    return itemstack;
                }
            }
            return ItemStack.EMPTY;
        }
    }
    private boolean isAmmo(ItemStack stack) {
        return stack.getItem().equals(Items.COBBLESTONE);
    }

    public void releaseUsing(ItemStack stack, World world, LivingEntity entity, int timeleft) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entity;
            boolean shoulduseammo = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack itemstack = findAmmo(playerentity);

            int i = this.getUseDuration(stack) - timeleft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, playerentity, i, !itemstack.isEmpty() || shoulduseammo);
            if (i < 0) return;

            if (!itemstack.isEmpty() || shoulduseammo) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.COBBLESTONE);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {

                    if (!world.isClientSide) {

                        StoneEntity stone = new StoneEntity(playerentity,world,0);
                        stone.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, f * 2.75F, 1.0F);
                        if (f == 1.0F) {
                            stone.setCritArrow(true);
                        }

                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                        if (j > 0) {
                            stone.setBaseDamage(stone.getBaseDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                        if (k > 0) {
                            stone.setKnockback(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                            stone.setSecondsOnFire(100);
                        }

                        stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> {
                            p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand());
                        });

                        world.addFreshEntity(stone);
                    }

                    world.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!shoulduseammo) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.removeItem(itemstack);
                        }
                    }

                    playerentity.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemstack = playerEntity.getItemInHand(hand);
        boolean hasammo = !findAmmo(playerEntity).isEmpty();

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, world, playerEntity, hand, hasammo);
        if (ret != null) return ret;

        if (!playerEntity.abilities.instabuild && !hasammo) {
            return ActionResult.fail(itemstack);
        } else {
            playerEntity.startUsingItem(hand);
            return ActionResult.consume(itemstack);
        }
    }





}
