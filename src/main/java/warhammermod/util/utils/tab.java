package warhammermod.util.utils;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class tab extends ItemGroup {
    public tab(String name)
    {
        super(name);
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(Items.EMERALD_BLOCK);
    }
}
