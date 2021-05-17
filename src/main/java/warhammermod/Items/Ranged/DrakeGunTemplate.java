package warhammermod.Items.Ranged;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import warhammermod.Entities.Projectile.FlameEntity;
import warhammermod.Items.AutogunBase;
import warhammermod.utils.inithandler.WarhammermodRegistry;

public class DrakeGunTemplate extends AutogunBase {

    public DrakeGunTemplate(Properties properties, int MagSize, int time) {
        //super(properties,MagSize,1,time, Items.BLAZE_ROD.getName().toString(),5);
        super(properties, Items.BLAZE_ROD, time, MagSize,1,4);
    }

    public void fire(PlayerEntity player, World world, ItemStack stack){
        world.playSound(null, player.getX(), player.getY(), player.getZ(), WarhammermodRegistry.flame, SoundCategory.PLAYERS, 0.3F, 1.05F+(rand.nextFloat()+rand.nextFloat())*0.1F);

        if(!world.isClientSide()){
            FlameEntity smallfireballentity = new FlameEntity(world, player, player.getLookAngle().x*5 + rand.nextGaussian() * 0.1, player.getLookAngle().y*5, player.getLookAngle().z*5 + rand.nextGaussian() * 0.1);
            smallfireballentity.setPos(player.getX(), (int)(player.getEyeY()- 0.35F), player.getZ());
            world.addFreshEntity(smallfireballentity);
        }

    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        for(Enchantment type:types){
            if(type==enchantment)return true;
        }return false;

    }
    private final Enchantment[] types={Enchantments.UNBREAKING, Enchantments.MENDING};
}
