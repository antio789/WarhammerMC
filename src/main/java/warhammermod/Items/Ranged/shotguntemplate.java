package warhammermod.Items.Ranged;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import warhammermod.Entities.Entityshotgun;
import warhammermod.Items.GunBase;
import warhammermod.util.confighandler.confighandler;

public class shotguntemplate extends GunBase {
    public int damage = confighandler.Config_values.blunderbusses_damage;

    public shotguntemplate(String name,int Magsize,int time,int durability,String ammotype,boolean enabled){
        super(name,Magsize,time,durability,ammotype,enabled);
    }



    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack,worldIn,entityLiving,timeLeft);
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            if(!worldIn.isRemote) {
                if (readytoFire) {
                    Entityshotgun entitybullet = new Entityshotgun(worldIn, entityplayer, damage);
                    entitybullet.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 4F, 0.2F);

                    int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                    if (j > 0) {
                        entitybullet.setpowerDamage(j);
                    }
                    int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack) + 1;
                    if (k > 0) {
                        entitybullet.setknockbacklevel(k);
                    }

                    worldIn.spawnEntity(entitybullet);
                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                        entitybullet.setFire(100);
                    }
                    stack.damageItem(1, entityplayer);


                }stack.setTagCompound(ammocounter);
            }
        }

    }

}
