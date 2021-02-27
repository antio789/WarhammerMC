package warhammermod.Items.Melee;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Entities.EntityHalberd;
import warhammermod.util.Handler.inithandler.Itemsinit;
import warhammermod.util.confighandler.confighandler;

import javax.annotation.Nullable;

public class halberdtemplate extends ItemSword {

    private float attackSpeed;
    private float attackdamage;
    private int cooldown=20;
    private int itemUseDuration = 72000;
    private ItemStack stack;
    private boolean canhit;
    private boolean damagesourcethrow;

    public halberdtemplate(String name, ToolMaterial material){
        super(material);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        setRegistryName(name);
        attackSpeed=(float)-confighandler.Config_values.halberd_speed;
        this.attackdamage = (float)confighandler.Config_values.halberd_damage + material.getAttackDamage();
        damagesourcethrow = confighandler.Config_enable.headshotmod_compatibility;

        if(confighandler.Config_enable.halberds_included){
            Itemsinit.ITEMS.add(this);}

        this.addPropertyOverride(new ResourceLocation("powerhit"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && canhit &&entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        boolean flag = true;
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);

    }

    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {

            if (count == getMaxItemUseDuration(stack) - cooldown){
                canhit=true;
            }
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
            if(canhit &&  entityLiving instanceof EntityPlayer){

                EntityPlayer playerIn = (EntityPlayer) entityLiving;
                if(!worldIn.isRemote){
                EntityHalberd halberdstrike = new EntityHalberd(worldIn, playerIn,attackdamage*1.3F,damagesourcethrow);
                halberdstrike.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3F, 0.2F);
                int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
                if (j > 0) {
                    halberdstrike.setpowerDamage(j);
                }
                int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack) + 1;
                if (k > 0) {
                    halberdstrike.setknockbacklevel(k);
                }
                worldIn.spawnEntity(halberdstrike);
                stack.damageItem(3, playerIn);
                playerIn.getCooldownTracker().setCooldown(this, 40);
                canhit=false;}
                playerIn.world.playSound(playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK , SoundCategory.PLAYERS, 1.0F, 1.0F, true);


            }
    }





    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackdamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.attackSpeed, 0));
        }

        return multimap;
    }

    public int getMaxItemUseDuration(ItemStack stack) { return 72000; }
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }


}
