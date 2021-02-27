package warhammermod.Items.Melee;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import warhammermod.util.Handler.inithandler.Itemsinit;
import warhammermod.util.confighandler.confighandler;
import warhammermod.util.confighandler.confighandler.Config_values;

import java.util.Set;

public class knifetemplate extends ItemTool {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet();

    public knifetemplate(String name, ToolMaterial material){
        super((float)Config_values.knife_damage, (float)-Config_values.knife_speed, material, EFFECTIVE_ON);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        setRegistryName(name);


        if(confighandler.Config_enable.knife_included){
            Itemsinit.ITEMS.add(this);}
    }
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        return enchantment.type == EnumEnchantmentType.WEAPON || enchantment.type == EnumEnchantmentType.BREAKABLE;
    }



}
