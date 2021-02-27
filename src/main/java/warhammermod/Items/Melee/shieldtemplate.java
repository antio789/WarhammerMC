package warhammermod.Items.Melee;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Items.GunBase;
import warhammermod.Items.Ranged.gunswtemplate;
import warhammermod.util.Handler.inithandler.Itemsinit;
import warhammermod.util.confighandler.confighandler;
import warhammermod.util.utils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;
import static warhammermod.Main.proxy;


public class shieldtemplate extends ItemShield {
    private String shield_name;
    private Boolean useshield=false;
    private Boolean issprinting=false;
    private int Type; // type 0 = can sprint , type 1 = can block
    private static final UUID BLOCKING_SPEED_BOOST_ID = UUID.fromString("662A6B8D-DA3B-4F1C-8813-96EA6097278D");
    private static final AttributeModifier BLOCKING_SPEED_BOOST = (new AttributeModifier(BLOCKING_SPEED_BOOST_ID, "blocking speed boost", 1.1000001192092896D, 2)).setSaved(false);
    private static final UUID SPRINT_SPEED_BOOST_ID = UUID.fromString("662A6B8D-DA3C-4F1D-8814-96EA6197278D");
    private static final AttributeModifier SPRINT_SPEED_BOOST = (new AttributeModifier(BLOCKING_SPEED_BOOST_ID, "sprint speed boost", 3.000001192092896D, 2)).setSaved(false);



    public shieldtemplate(String name,int type,boolean enabled){
        super();
        Type=type;
        setMaxDamage(confighandler.Config_values.shields_durability);
        setUnlocalizedName(name);
        setRegistryName(name);
        shield_name=name;
        proxy.settheteisr(shield_name,this);
        if(enabled){
            Itemsinit.ITEMS.add(this);}
        this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && (entityIn.getActiveItemStack() == stack || ( hasshield(entityIn))) ? 1.0F : 0.0F;
            }
        });

    }
    private boolean hasshield(EntityLivingBase entityIn){
        if(entityIn.getHeldItemMainhand().getItem() instanceof GunBase)

        if(entityIn.getHeldItemMainhand().getItem() instanceof GunBase && ((GunBase) entityIn.getHeldItemMainhand().getItem()).hasshield)return true;
        return entityIn.getHeldItemMainhand().getItem() instanceof gunswtemplate && ((gunswtemplate) entityIn.getHeldItemMainhand().getItem()).hasshield;
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return shield_name;
    }
    public int gettype(){
        return Type;
    }
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    }
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity)
    {
        return(stack.getItem() == Items.SHIELD || stack.getItem()== shieldtemplate.this);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        useshield=true;
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);

    }

    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        if(player instanceof EntityPlayer) {
            issprinting = player.world.isRemote && Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown();
            if (Type == 0) {
                IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

                if (iattributeinstance.getModifier(BLOCKING_SPEED_BOOST_ID) != null) {
                    iattributeinstance.removeModifier(BLOCKING_SPEED_BOOST);
                }
                if (issprinting) {
                    if (iattributeinstance.getModifier(SPRINT_SPEED_BOOST_ID) != null) {
                        iattributeinstance.removeModifier(SPRINT_SPEED_BOOST);
                    }
                    iattributeinstance.applyModifier(SPRINT_SPEED_BOOST);
                } else iattributeinstance.applyModifier(BLOCKING_SPEED_BOOST);
            }
        }
    }
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if(entityLiving instanceof EntityPlayer && Type==0) {
            IAttributeInstance iattributeinstance = entityLiving.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            iattributeinstance.removeModifier(BLOCKING_SPEED_BOOST);
            iattributeinstance.removeModifier(SPRINT_SPEED_BOOST);
        }
        useshield=false;
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(useshield && entityIn instanceof EntityPlayer){
            EntityLivingBase player = (EntityLivingBase) entityIn;
            if(!(player.getActiveItemStack().getItem() instanceof shieldtemplate)) {

                IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
                if (iattributeinstance.getModifier(BLOCKING_SPEED_BOOST_ID) != null) {
                    iattributeinstance.removeModifier(BLOCKING_SPEED_BOOST);
                }
                if(iattributeinstance.getModifier(SPRINT_SPEED_BOOST_ID)!=null) {
                    iattributeinstance.removeModifier(SPRINT_SPEED_BOOST);
                }
        }
        }
    }




}
