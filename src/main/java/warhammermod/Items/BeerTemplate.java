package warhammermod.Items;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import warhammermod.utils.inithandler.ItemsInit;
import warhammermod.utils.reference;

public class BeerTemplate extends Item {
    public BeerTemplate(){
        super((new Properties()).tab(reference.warhammer).stacksTo(16).food(new Food.Builder().nutrition(5).saturationMod(0.7F)
                .effect(new EffectInstance(Effects.REGENERATION, 80, 0), 0.8F)
                .effect(new EffectInstance(Effects.DAMAGE_BOOST, 120, 0), 0.8F)
                .effect(new EffectInstance(Effects.CONFUSION, 160, 1), 0.18F).alwaysEat().build()));
        ItemsInit.ITEMS.add(this);
    }
}
