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
import warhammermod.Entities.Projectile.WarpBulletEntity;
import warhammermod.Items.GunBase;

public class WarpgunTemplate extends GunBase {
    float damage;

    public WarpgunTemplate(Item.Properties properties, Item ammotype, int time, int magsize, float damagein){
        super(properties, ammotype, time, magsize);
        damage=damagein;
    }

    public void fire(PlayerEntity player, World world, ItemStack stack) {
        if(world.isClientSide()) {
            for (int k = 0; k < 40; ++k) {
                int i = 65280;
                double d0 = (double)(i >> 16 & 255) / 255.0D;
                double d1 = (double)(i >> 8 & 255) / 255.0D;
                double d2 = (double)(i & 255) / 255.0D;
                world.addParticle(ParticleTypes.ENTITY_EFFECT, player.getX() + player.getLookAngle().x * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, player.getY() + 0.4 + (double) (this.rand.nextFloat() * this.height), player.getZ() + player.getLookAngle().z * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, d0, d1, d2);
            }
        }
        if(!world.isClientSide()) {
            world.playSound(null,player.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundCategory.PLAYERS,1,1.35F/(rand.nextFloat()*0.4F+1.2F)+0.5F);

            WarpBulletEntity bullet = new WarpBulletEntity(player, world, damage);
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
    }


}
