package warhammermod.Items.Melee;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import warhammermod.util.Handler.inithandler.Itemsinit;
import warhammermod.util.reference;

public class Armortemplate extends ItemArmor {
    public static final ArmorMaterial diamondchainmail = EnumHelper.addArmorMaterial("diamond2", reference.modid+":diamond",33,new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);

    public Armortemplate(String name,EntityEquipmentSlot itemin){
        super(diamondchainmail,0,itemin);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        setRegistryName(name);
        Itemsinit.ITEMS.add(this);
    }

    public ArmorMaterial setRepairItem(ItemStack stack)
    {
        return ArmorMaterial.DIAMOND;
    }

}
