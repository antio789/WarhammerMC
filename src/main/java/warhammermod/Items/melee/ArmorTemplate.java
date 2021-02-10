package warhammermod.Items.melee;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.reference;

public class ArmorTemplate extends ArmorItem {


    public ArmorTemplate(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builder){
        super(materialIn, slot, builder.group(reference.warhammer));
        ItemsInit.ITEMS.add(this);
    }


}
