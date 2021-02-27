package warhammermod.Items.Ranged;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import warhammermod.Entities.EntityGrenade;
import warhammermod.Items.GunBase;


public class grenadelaunchertemplate extends GunBase {
    public grenadelaunchertemplate(String name, int MagSize, int time,int durability,String ammotype, boolean enabled) {
        super(name,MagSize,time,durability,ammotype,enabled);
    }
    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        if (!readytoFire && !player.world.isRemote) {
            if (count == getMaxItemUseDuration(stack) - timetoreload) {
                EntityPlayer entityplayer = (EntityPlayer) player;
                int ammoreserve = this.findAmmo(entityplayer).getCount();
                if ((ammoreserve > 0) && (!entityplayer.capabilities.isCreativeMode) ) {
                    int infinitylevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack);
                    if (ammoreserve < magsize) {
                        ammocount=ammoreserve;

                        if (infinitylevel == 0 || rand.nextFloat()<0.4F) {
                            this.findAmmo(entityplayer).shrink(ammoreserve);
                        }
                    } else {
                        ammocount=magsize;

                        if (infinitylevel == 0 || rand.nextFloat()<0.4F) {
                            this.findAmmo(entityplayer).shrink(magsize);
                        }

                    }
                }
            }
        }
        if ((count == getMaxItemUseDuration(stack) - timetoreload) && !readytoFire) {
            player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
        }

    }



    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack,worldIn,entityLiving,timeLeft);
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            if (!worldIn.isRemote) {
                if (readytoFire) {
                    EntityGrenade entitybullet = new EntityGrenade(worldIn, entityplayer);
                    float j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                    entitybullet.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 1.2F + 0.13F * j, 0.5F);
                    int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                    if (k > 0) {
                        entitybullet.setknockbacklevel(k);
                    }
                    worldIn.spawnEntity(entitybullet);
                    stack.damageItem(1, entityplayer);

                }stack.setTagCompound(ammocounter);
            }

        }

    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        switch(Enchantment.getEnchantmentID(enchantment)){
            case 48:return true;
            case 49:return true;
            case 34:return true;
            case 70:return true;
            case 51:return true;
            default:return false;

        }
    }

}
