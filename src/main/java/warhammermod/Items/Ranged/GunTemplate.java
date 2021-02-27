package warhammermod.Items.Ranged;


import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import warhammermod.Entities.EntityBullet;
import warhammermod.Items.GunBase;

public class GunTemplate extends GunBase {
    public int damage;

    public GunTemplate(String name, int MagSize, int time, int damagein, int durability,String ammotype, boolean enabled){
        super(name,MagSize,time,durability,ammotype,enabled);
        damage=damagein;
    }



    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack,worldIn,entityLiving,timeLeft);
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            if(!worldIn.isRemote) {
                if(readytoFire) {
                    EntityBullet entitybullet = new EntityBullet(worldIn, entityplayer, damage);
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

