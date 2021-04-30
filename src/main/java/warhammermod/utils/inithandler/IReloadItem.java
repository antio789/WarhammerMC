package warhammermod.utils.inithandler;

import net.minecraft.item.ItemStack;

public interface IReloadItem {
    int getTimetoreload();
    boolean isReadytoFire(ItemStack stack);
}
