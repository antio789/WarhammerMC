package warhammermod.Items.Ranged.Ammo;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import warhammermod.util.Handler.inithandler.Itemsinit;

public class Grenade extends Item {

    public Grenade(String cartridge,boolean enabled){
        setUnlocalizedName(cartridge);
        setCreativeTab(CreativeTabs.COMBAT);
        setRegistryName(cartridge);
        if(enabled){ Itemsinit.ITEMS.add(this);}
    }


}