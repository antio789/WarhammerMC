package warhammermod.Items.Ranged;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import warhammermod.Entities.WarpBulletEntity;
import warhammermod.Items.AutogunBase;
import warhammermod.Items.Render.RenderRatlingGun;
import warhammermod.util.reference;

public class RatlingGun extends AutogunBase {

    public RatlingGun(Properties properties, int MagSize, int time) {
        super(properties, reference.Warpstone,time,MagSize,64, 6);
    }


    public void fire(PlayerEntity player, World world, ItemStack stack){
        if(!world.isRemote) {
            WarpBulletEntity bullet = new WarpBulletEntity(world, player, player.getActiveItemStack(), 11);
            bullet.setPosition(player.getPosX(), player.getPosYEye() - 0.26, player.getPosZ());
            bullet.shoot( player.rotationPitch, player.rotationYaw, 0, 4, 0.2F);

            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
            if (i > 0) {
                bullet.setpowerDamage(i);
            }
            int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack) + 1;
            if (k > 0) {
                bullet.setknockbacklevel(k);
            }

            player.world.addEntity(bullet);
        }else {
            RenderRatlingGun.setrotationangle();
        }
        PlayaSound(world, player);
    }

    public void PlayaSound(World worldIn, PlayerEntity playerIn){
        worldIn.playSound(playerIn.getPosX(),playerIn.getPosYEye(),playerIn.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS,0.6F,1.55F / (rand.nextFloat() * 0.4F + 1.2F) +  0.5F,true);

    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        if(enchantment.getName().equals(Enchantments.FLAME.getName()))return false;
        switch(enchantment.type){
            case BOW:
            case BREAKABLE:return true;
            default:return false;
        }
    }


    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (player instanceof PlayerEntity){
            PlayerEntity entityplayer = (PlayerEntity) player;
            if((isCharged(stack) && (getUseDuration(stack)-count)/getFireRate()<=getCharge(stack))|| entityplayer.isCreative()) {
                if((getUseDuration(stack)-count)!=0 && (getUseDuration(stack)-count)%getFireRate()==0){
                    fire( entityplayer,player.world,stack);
                }
            }
            else if ((count == getUseDuration(stack) - timetoreload) && !isCharged(stack)) {
                player.world.playSound(player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
            }
        }
    }
}
