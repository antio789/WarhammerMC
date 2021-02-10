package warhammermod.Items.Ranged;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import warhammermod.Entities.StoneEntity;
import warhammermod.util.Handler.ItemsInit;

public class Slingtemplate extends BowItem {
    public float charging;

    public Slingtemplate(Item.Properties properties){
        super(properties);
        ItemsInit.ITEMS.add(this);
    }


    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean flag = !findAmmo(playerIn).isEmpty();

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;

        if (!playerIn.abilities.isCreativeMode && !flag) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
    }


    public ItemStack findAmmo(PlayerEntity player) {
        if (this.isAmmo(player.getHeldItem(Hand.OFF_HAND))) {
            return player.getHeldItem(Hand.OFF_HAND);
        } else if (this.isAmmo(player.getHeldItem(Hand.MAIN_HAND))) {
            return player.getHeldItem(Hand.MAIN_HAND);
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isAmmo(itemstack)) {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }
    private boolean isAmmo(ItemStack stack) {
        return stack.getItem().equals(Blocks.COBBLESTONE.asItem());
    }

    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        charging = Math.min(1,(stack.getUseDuration()- count) / 20.0F);
        stack.getItem().getItemStackTileEntityRenderer();
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {

        if (entityLiving instanceof PlayerEntity) {

            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean consumeammo = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = findAmmo(playerentity);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || consumeammo);
            if (i < 0) return;

            if (!itemstack.isEmpty() || consumeammo) {

                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Blocks.COBBLESTONE.asItem());
                }

                float f = getArrowVelocity(i);
                if ((double)f > 0.1D) {

                    if (!worldIn.isRemote) {

                        StoneEntity stone = new StoneEntity(worldIn,playerentity,stack,f*3);
                        stone.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 2.75F, 1.5F);


                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if (j > 0) {
                            stone.setpowerDamage((float)stone.getDamage() + (float)j * 0.5F + 0.5F);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if (k > 0) {
                            stone.setknockbacklevel(k);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            stone.setFire(100);
                        }

                        stack.damageItem(1, playerentity, (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                        });
                        if(consumeammo){
                            stone.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
                        }

                        worldIn.addEntity(stone);
                    }

                    worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!consumeammo) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.deleteStack(itemstack);
                        }
                    }
                }
            }
        }
    }

}
