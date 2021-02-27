package warhammermod.Items.Ranged;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import warhammermod.Entities.Entityflame;
import warhammermod.Items.AutoGunBase;
import warhammermod.util.Handler.soundhandler.sounds;
import warhammermod.util.Itimetoreload;

import static net.minecraft.init.Items.BLAZE_ROD;

public class DrakeGunTemplate extends AutoGunBase implements Itimetoreload {

    public DrakeGunTemplate(String name, int MagSize, int time, boolean enabled) {
        super(name,MagSize,1,time,200, BLAZE_ROD.getRegistryName().toString(),5,enabled);
    }

    @Override
    public void fire(World worldIn,EntityPlayer playerIn){
        Entityflame entitysmallfireball = new Entityflame(worldIn, playerIn, playerIn.getLookVec().x*5 /*+ playerIn.getRNG().nextGaussian()*10*/, playerIn.getLookVec().y*5, playerIn.getLookVec().z*5 /*+ playerIn.getRNG().nextGaussian()*10*/);
        entitysmallfireball.posY = playerIn.posY + (double)(playerIn.height / 2.0F -0.1F);
        playerIn.world.spawnEntity(entitysmallfireball);

        PlayaSound(worldIn,playerIn);
    }
    @Override
    public void PlayaSound(World worldIn,EntityPlayer playerIn){
        worldIn.playSound(playerIn.posX,playerIn.posY,playerIn.posZ, sounds.flame,SoundCategory.PLAYERS,0.3F,1.05F+(itemRand.nextFloat()+itemRand.nextFloat())*0.1F,true);

    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        switch(Enchantment.getEnchantmentID(enchantment)){
            case 34:
            case 70:return true;
            default:return false;

        }
    }



}

