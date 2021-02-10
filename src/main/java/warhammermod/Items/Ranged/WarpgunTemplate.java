package warhammermod.Items.Ranged;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import warhammermod.Entities.WarpBulletEntity;
import warhammermod.Items.GunBase;

public class WarpgunTemplate  extends GunBase {
    float damage;

    public WarpgunTemplate(Properties properties, String ammotype, int time, int magsize, float damagein){
        super(properties, ammotype, time, magsize);
        damage=damagein;
    }

    public void fire(PlayerEntity player, World world, ItemStack stack){
        world.playSound(null, player.getPosX(), player.getPosYEye(), player.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.35F / (rand.nextFloat() * 0.4F + 1.2F) +  0.5F);
        if(world.isRemote) {
            for (int k = 0; k < 50; ++k) {

                int i = 65280;
                double d0 = (double)(i >> 16 & 255) / 255.0D;
                double d1 = (double)(i >> 8 & 255) / 255.0D;
                double d2 = (double)(i & 255) / 255.0D;
                world.addParticle(ParticleTypes.ENTITY_EFFECT,player.getPosX() + player.getLookVec().x * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width,player.getPosY()+ 0.4 + (double) (this.rand.nextFloat() * this.height),player.getPosZ()+ player.getLookVec().z * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width,d0, d1, d2);
            }
        }
        if(!world.isRemote){
            WarpBulletEntity bullet = new WarpBulletEntity(world,player,stack,damage);
            bullet.setPosition(player.getPosX(), player.getPosYEye()- 0.26, player.getPosZ());
            bullet.shoot(player.rotationPitch,player.rotationYaw,0,4,0.2F);

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
