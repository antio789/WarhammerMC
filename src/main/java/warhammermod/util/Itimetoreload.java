package warhammermod.util;

import net.minecraft.item.ItemStack;

public interface Itimetoreload {
    int getTimetoreload();
    boolean isReadytoFire(ItemStack stack);
}
