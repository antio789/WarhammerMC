package warhammermod.Items.Ranged.Ammo;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.reference;

public class Cartridge extends Item {

    public Cartridge(){
        super((new Item.Properties()).group(reference.warhammer));
        ItemsInit.ITEMS.add(this);

    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return (stack.isEnchanted() || this == ItemsInit.Warpstone);
    }

}
