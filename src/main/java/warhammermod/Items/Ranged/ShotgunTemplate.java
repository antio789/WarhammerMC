package warhammermod.Items.Ranged;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import warhammermod.Entities.Projectile.ShotEntity;
import warhammermod.Items.GunBase;
import warhammermod.Items.Render.RenderRepeater;
import warhammermod.utils.inithandler.ItemsInit;

public class ShotgunTemplate extends GunBase {
    float damage;
    public ShotgunTemplate(Item.Properties properties, Item ammotype, int time, int magsize, float damagein){
        super(properties, ammotype, time, magsize);
        damage=damagein;
    }

    public void fire(PlayerEntity player, World world, ItemStack stack) {
        if(world.isClientSide()) {
            for (int k = 0; k < 40; ++k) {
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, player.getX() + player.getLookAngle().x * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, player.getY() + 0.4 + (double) (this.rand.nextFloat() * this.height), player.getZ() + player.getLookAngle().z * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, d2, d0, d1);
            }
        }
        if(!world.isClientSide()) {
            world.playSound(null,player.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundCategory.PLAYERS,1,1.35F/(rand.nextFloat()*0.4F+1.2F)+0.5F);

            ShotEntity bullet = new ShotEntity(player, world, damage,player.blockPosition());
            bullet.setPos(player.getX(), player.getEyeY() - 0.26, player.getZ());
            bullet.shootFromRotation(player,player.xRot, player.yRot, 0, 3.5F, 0.3F);

            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
            if (i > 0) {
                bullet.setpowerDamage(i);
            }
            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack) + 1;
            if (k > 0) {
                bullet.setknockbacklevel(k);
            }

            world.addFreshEntity(bullet);
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                bullet.setSecondsOnFire(100);
            }

            stack.hurtAndBreak(1, player, (p_220009_1_) -> {
                p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
            });
        }
        else if(stack.getItem().equals(ItemsInit.repeater_handgun)){
            RenderRepeater.setrotationangle();
        }
    }
}
