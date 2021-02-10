package warhammermod.Items.Ranged;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import warhammermod.Entities.FlameEntity;
import warhammermod.Items.AutogunBase;
import warhammermod.util.Handler.WarhammermodRegistry;

public class DrakeGunTemplate extends AutogunBase {

    public DrakeGunTemplate(Properties properties, int MagSize, int time) {
        //super(properties,MagSize,1,time, Items.BLAZE_ROD.getName().toString(),5);
        super(properties, Items.BLAZE_ROD.getRegistryName().toString(), time, MagSize,1,4);
    }

    public void fire(PlayerEntity player, World world, ItemStack stack){
        world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), WarhammermodRegistry.flame, SoundCategory.PLAYERS, 0.3F, 1.05F+(rand.nextFloat()+rand.nextFloat())*0.1F);

        if(!world.isRemote){
            FlameEntity smallfireballentity = new FlameEntity(world, player, player.getLookVec().x*5 + rand.nextGaussian() * 0.1, player.getLookVec().y*5, player.getLookVec().z*5 + rand.nextGaussian() * 0.1);
            smallfireballentity.setPosition(player.getPosX(), (int)(player.getPosYEye()- 0.35F), player.getPosZ());

            world.addEntity(smallfireballentity);
        }

    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        for(Enchantment type:types){
            if(type==enchantment)return true;
        }return false;

    }
    private Enchantment[] types={Enchantments.UNBREAKING, Enchantments.MENDING};
}
