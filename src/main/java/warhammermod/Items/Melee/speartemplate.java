package warhammermod.Items.Melee;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import warhammermod.Entities.Entityspear;
import warhammermod.util.Handler.inithandler.Itemsinit;
import warhammermod.util.confighandler.confighandler;

import java.util.Set;

public class speartemplate extends ItemTool {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet();
    private final ToolMaterial mat;
    private final float throwingdamage;

    public speartemplate(String name, ToolMaterial material) {
        super((float) confighandler.Config_values.spear_damage, (float)-confighandler.Config_values.spear_speed, material, EFFECTIVE_ON);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        setRegistryName(name);
        mat = material;
        float i = 1;
        switch (mat) {
            case WOOD:
                i = 2.8F;
                break;
            case STONE:
                i = 3.8F;
                break;
            case IRON:
                i = 4.8F;
                break;
            case GOLD:
                i = 2.8F;
                break;
            case DIAMOND:
                i = 5.8F;
                break;
            default:
                i = 1;
                break;
        }
        throwingdamage = i;

        if(confighandler.Config_enable.spears_included){
            Itemsinit.ITEMS.add(this);}
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
        return enchantment.type == EnumEnchantmentType.WEAPON || enchantment.type == EnumEnchantmentType.BREAKABLE;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);


        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote) {

            Entityspear entityspear = new Entityspear(worldIn, playerIn, itemstack, throwingdamage);
            entityspear.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.2F, 1.0F);
            int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, itemstack);

            if (j > 0) {
                entityspear.setpowerDamage(j);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, itemstack) + 1;
            if (k > 0) {
                entityspear.setknockbacklevel(k);

            }
            worldIn.spawnEntity(entityspear);
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                entityspear.setFire(100);
            }
            playerIn.inventory.deleteStack(itemstack);



        }

            playerIn.addStat(StatList.getObjectUseStats(this));
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }


}
