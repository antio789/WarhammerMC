package warhammermod.Items.Ranged;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import warhammermod.Entities.Projectile.WarpBulletEntity;
import warhammermod.Items.AutogunBase;
import warhammermod.Items.Render.RenderRatlingGun;
import warhammermod.utils.inithandler.ItemsInit;

import static warhammermod.utils.reference.Warpstone;

public class RatlingGun extends AutogunBase {

    public RatlingGun(Properties properties, int MagSize, int time) {
        super(properties, ItemsInit.Warpstone,time,MagSize,64, 6);
    }


    public void fire(PlayerEntity player, World world, ItemStack stack){
        if(!world.isClientSide()) {
            WarpBulletEntity bullet = new WarpBulletEntity(player,world, 11);
            bullet.setPos(player.getX(), player.getEyeY() - 0.26, player.getZ());
            bullet.shootFromRotation(player,player.xRot, player.yRot, 0, 3.5F, 6F);

            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
            if (i > 0) {
                bullet.setpowerDamage(i);
            }
            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack) + 1;
            if (k > 0) {
                bullet.setknockbacklevel(k);
            }

            world.addFreshEntity(bullet);
        }else {
            RenderRatlingGun.setrotationangle();
        }
        PlayaSound(world, player);
    }

    public void PlayaSound(World world, PlayerEntity player){
        world.playSound(null,player.blockPosition(),SoundEvents.GENERIC_EXPLODE,SoundCategory.PLAYERS,0.6F,1.55F/(rand.nextFloat()*0.4F+1.2F)+0.5F);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        if(enchantment == Enchantments.FLAMING_ARROWS){return false;}
        return enchantment.category == EnchantmentType.BOW || super.canApplyAtEnchantingTable(stack,enchantment);
    }

}
