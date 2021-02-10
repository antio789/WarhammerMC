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
import warhammermod.Entities.ShotgunEntity;
import warhammermod.Items.GunBase;

public class ShotgunTemplate extends GunBase {
    int damage;

    public ShotgunTemplate(Item.Properties properties, String ammotype, int time, int magsize, int damagein){
        super(properties, ammotype, time, magsize);
        damage=damagein;
    }

    public void fire(PlayerEntity player, World world, ItemStack stack){
        world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.35F / (rand.nextFloat() * 0.4F + 1.2F) +  0.5F);
        if(world.isRemote) {
            for (int k = 0; k < 35; ++k) {

                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,player.getPosition().getX() + player.getLookVec().x * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width,player.getPosition().getY()+ 0.4 + (double) (this.rand.nextFloat() * this.height),player.getPosition().getZ()+ player.getLookVec().z * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width,d2, d0, d1);
            }
        }
        if(!world.isRemote){
            ShotgunEntity bullet = new ShotgunEntity(world,player,stack,damage);
            bullet.setPosition(player.getPosX(), player.getPosYEye() - 0.2, player.getPosZ());
            bullet.firepos = new double[]{player.getPosX(), player.getPosYEye() - 0.26, player.getPosZ()};
            bullet.shoot(player,player.rotationPitch,player.rotationYaw,0,4,0.2F);

            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
            if (i > 0) {
                bullet.setpowerDamage(i);
            }
            int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack) + 1;
            if (k > 0) {
                bullet.setknockbacklevel(k);
            }

            world.addEntity(bullet);
            if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME,stack)>0){bullet.setFire(100);}

            stack.damageItem(1,player,(p_220040_1_) -> {
                p_220040_1_.sendBreakAnimation(player.getActiveHand());
            });
        }
    }
}
