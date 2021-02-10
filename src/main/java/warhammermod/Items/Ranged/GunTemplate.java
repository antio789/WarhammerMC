package warhammermod.Items.Ranged;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import warhammermod.Entities.BulletEntity;
import warhammermod.Items.GunBase;
import warhammermod.Items.Render.RenderRepeater;
import warhammermod.util.Handler.ItemsInit;

public class GunTemplate extends GunBase {
    float damage;

    public GunTemplate(Properties properties, String ammotype, int time, int magsize, float damagein){
        super(properties, ammotype, time, magsize);
        damage=damagein;
    }

    public void fire(PlayerEntity player, World world, ItemStack stack){
        world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.35F / (rand.nextFloat() * 0.4F + 1.2F) +  0.5F);
        if(world.isRemote) {
            for (int k = 0; k < 40; ++k) {

                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,player.getPosX() + player.getLookVec().x * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width,player.getPosY()+ 0.4 + (double) (this.rand.nextFloat() * this.height),player.getPosZ()+ player.getLookVec().z * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width,d2, d0, d1);
            }
        }
        if(!world.isRemote){
            BulletEntity bullet = new BulletEntity(world,player,stack,damage);
            bullet.setPosition(player.getPosX(), player.getPosYEye() - 0.26, player.getPosZ());
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
        else if(stack.getItem().equals(ItemsInit.repeater_handgun)){
            RenderRepeater.setrotationangle();
        }
    }


}
