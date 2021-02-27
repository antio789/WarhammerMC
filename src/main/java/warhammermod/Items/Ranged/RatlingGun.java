package warhammermod.Items.Ranged;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import warhammermod.Entities.EntityWarpBullet;
import warhammermod.Items.AutoGunBase;
import warhammermod.util.confighandler.confighandler;
import warhammermod.util.reference;

import java.util.Random;

import static warhammermod.Main.proxy;

public class RatlingGun extends AutoGunBase {

    protected Random rand= new Random();
    protected float width = 1.5F;
    protected float height = 1.5F;


    public RatlingGun(String name, int MagSize,int ammoconsumption, int timetoreload,int firerate,int Durability, boolean enabled) {
        super(name,MagSize,ammoconsumption,timetoreload,Durability, reference.Warpstone,firerate,enabled);
        proxy.settheteisr("ratling gun",this);

    }




    @Override
    public void fire(World worldIn, EntityPlayer playerIn){

        if (worldIn.isRemote) {
            for (int k = 0; k < confighandler.Config_enable.smokeamount/2; ++k) {

                int i = 65280;
                double d0 = (double)(i >> 16 & 255) / 255.0D;
                double d1 = (double)(i >> 8 & 255) / 255.0D;
                double d2 = (double)(i >> 0 & 255) / 255.0D;
                worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB, playerIn.posX + playerIn.getLookVec().x * 2 + (double) (rand.nextFloat() * this.width * 2) - (double) this.width, playerIn.posY + 0.4 + (double) (this.rand.nextFloat() * this.height), playerIn.posZ + playerIn.getLookVec().z * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, d0, d1, d2);
            }
            PlayaSound(worldIn,playerIn);
        }
         {


            EntityWarpBullet entity = new EntityWarpBullet(worldIn, playerIn, confighandler.Config_values.rifle_damage - 2F);
            entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 5.2F, 7F);


            int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, playerIn.getActiveItemStack());
            if (j > 0) {
                entity.setpowerDamage(j);
            }
            int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, playerIn.getActiveItemStack()) + 1;
            if (k > 0) {
                entity.setknockbacklevel(k);
            }
            worldIn.spawnEntity(entity);
        }

    }
    @Override
    public void PlayaSound(World worldIn,EntityPlayer playerIn){
        worldIn.playSound(playerIn.posX,playerIn.posY,playerIn.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS,0.6F,2F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F,true);

    }


    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        if(enchantment.getName().equals(Enchantments.FLAME.getName()))return false;
        else return enchantment.type.canEnchantItem(stack.getItem());
    }











}
