package warhammermod.Items;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.reference;


public class Beertemplate extends Item {
    public Beertemplate(){
        super((new Properties()).group(reference.warhammer).maxStackSize(16).food(new Food.Builder().hunger(6).saturation(1F).effect(new EffectInstance(Effects.REGENERATION, 80, 0), 1.0F).effect(new EffectInstance(Effects.STRENGTH, 160, 0), 1.0F).effect(new EffectInstance(Effects.NAUSEA, 160, 1), 0.18F).setAlwaysEdible().build()));
        ItemsInit.ITEMS.add(this);
    }
}
