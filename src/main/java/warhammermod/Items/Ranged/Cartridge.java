package warhammermod.Items.Ranged;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.utils.inithandler.ItemsInit;
import warhammermod.utils.reference;

public class Cartridge extends Item {

    public Cartridge(){
        super((new Properties()).tab(reference.warhammer));
        ItemsInit.ITEMS.add(this);

    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return (stack.isEnchanted() || this == ItemsInit.Warpstone);
    }

}
