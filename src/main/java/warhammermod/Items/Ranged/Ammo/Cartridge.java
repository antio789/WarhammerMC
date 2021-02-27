package warhammermod.Items.Ranged.Ammo;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.util.Handler.inithandler.Itemsinit;

public class Cartridge extends Item {

        public Cartridge(String cartridge){
            setUnlocalizedName(cartridge);
            setCreativeTab(CreativeTabs.COMBAT);
            setRegistryName(cartridge);
            Itemsinit.ITEMS.add(this);
        }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return (this== Itemsinit.Warpstone || stack.isItemEnchanted());
    }
}

