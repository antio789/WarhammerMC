package warhammermod.Items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.util.Handler.inithandler.Itemsinit;

import javax.annotation.Nullable;
import java.util.List;

public class BeerTemplate extends ItemFood {


    public BeerTemplate(String name){
        super(8,1F,false);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.FOOD);
        setRegistryName(name);
        Itemsinit.ITEMS.add(this);
        setAlwaysEdible();
        setMaxStackSize(16);

    }
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 80, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 200, 0));
            if (worldIn.rand.nextFloat() < 0.18F)
            {
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA,160,1));
            }
        }
    }
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add("Be careful it's strong");
    }


}
