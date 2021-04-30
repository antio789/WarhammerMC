package warhammermod.utils;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import warhammermod.utils.inithandler.ItemsInit;

public class tab extends ItemGroup {
    public tab(String name)
    {
        super(name);
    }



    public ItemStack makeIcon() {
        return new ItemStack(ItemsInit.Warpstone);
    }
}
