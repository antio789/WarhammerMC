package warhammermod.Items.Ranged;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import warhammermod.Entities.EntityWarpBullet;
import warhammermod.Items.GunBase;
import warhammermod.util.confighandler.confighandler;

public class SkavenGuns extends GunBase {
    float damage;

    public SkavenGuns(String name, int MagSize, int time,float damageIn, int durability,String ammotype, boolean enabled){
        super(name,MagSize,time,durability,ammotype,enabled);
        damage=damageIn;
    }
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            if (readytoFire) {
                worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 2F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
                if (worldIn.isRemote) {
                    for (int k = 0; k < confighandler.Config_enable.smokeamount/2; ++k) {

                        int i = 65280;
                        double d0 = (double)(i >> 16 & 255) / 255.0D;
                        double d1 = (double)(i >> 8 & 255) / 255.0D;
                        double d2 = (double)(i >> 0 & 255) / 255.0D;
                        worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB, entityplayer.posX + entityplayer.getLookVec().x * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, entityplayer.posY + 0.4 + (double) (this.rand.nextFloat() * this.height), entityplayer.posZ + entityplayer.getLookVec().z * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, d0, d1, d2);
                    }
                }

            }
            if (!entityplayer.capabilities.isCreativeMode && !worldIn.isRemote) {
                if (!readytoFire) {
                    ammocounter.setInteger("ammo", ammocount);
                    ammocount = 0;
                } else {
                    ammocounter.setInteger("ammo", ammocounter.getInteger("ammo") - 1);
                }
            }
            reloaded = false;



                if (readytoFire) {
                    EntityWarpBullet entitybullet = new EntityWarpBullet(worldIn, entityplayer, damage);
                    entitybullet.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 4F, 0.1F);

                    int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                    if (j > 0) {
                        entitybullet.setpowerDamage(j);
                    }
                    int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack) + 1;
                    if (k > 0) {
                        entitybullet.setknockbacklevel(k);
                    }
                    worldIn.spawnEntity(entitybullet);
                    stack.damageItem(1, entityplayer);

                }
                stack.setTagCompound(ammocounter);

        }

    }
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        if(enchantment.getName().equals(Enchantments.FLAME.getName()))return false;
        else return enchantment.type == EnumEnchantmentType.BOW || enchantment.type == EnumEnchantmentType.BREAKABLE;
    }

}
