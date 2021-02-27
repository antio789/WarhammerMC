package warhammermod.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.util.Handler.inithandler.Itemsinit;
import warhammermod.util.Itimetoreload;

import javax.annotation.Nullable;

public class AutoGunBase extends ItemBow implements Itimetoreload {
    private int magsize;
    private int timetoreload;
    private int ammocount = 0;
    NBTTagCompound ammocounter;
    private NBTTagCompound reloader;
    private String AmmoType;

    public int getFireRate() {
        return FireRate;
    }

    private int FireRate;
    private int AmmoConsumption;


    public boolean isFiring() {
        return firing;
    }

    public boolean firing;

    public int getTimetoreload(){
        return timetoreload;
    }
    public boolean isReadytoFire(){return readytofire;}



    public boolean readytofire;


    public AutoGunBase(String name, int MagSize,int ammoconsumption, int time, int Durability, String item,int firerate, boolean enabled) {
        setUnlocalizedName(name);
        setRegistryName(name);
        if(enabled){ Itemsinit.ITEMS.add(this);}
        setCreativeTab(CreativeTabs.COMBAT);
        magsize = MagSize;
        timetoreload = time;
        this.maxStackSize = 1;
        this.setMaxDamage(Durability);
        AmmoType=item;
        FireRate=firerate;
        AmmoConsumption=ammoconsumption;

        this.addPropertyOverride(new ResourceLocation("reloading"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                reloader=stack.getTagCompound();
                return entityIn != null && entityIn instanceof EntityPlayer && !((EntityPlayer) entityIn).capabilities.isCreativeMode && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack  && (reloader==null || reloader.getInteger("ammo")<=0) ? 1.0F : 0.0F;
            }
        });
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
            ammocounter = itemstack.getTagCompound();
            if (ammocounter == null) {
                ammocounter = new NBTTagCompound();
                ammocounter.setInteger("ammo", 0);
                ammocount=0;
            }

            ammocount=ammocounter.getInteger("ammo");

            readytofire=ammocount>0 || playerIn.capabilities.isCreativeMode;
            firing=readytofire;
            reload=false;




        boolean flag = !this.findAmmo(playerIn).isEmpty() && !readytofire;
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;

        if (!playerIn.capabilities.isCreativeMode && !flag && !readytofire) {
            return flag ? new ActionResult<>(EnumActionResult.PASS, itemstack) : new ActionResult<>(EnumActionResult.FAIL, itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
    }





    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {

        if(player instanceof EntityPlayer) {
            EntityPlayer playerIn = (EntityPlayer)player;
            if((readytofire &&( getMaxItemUseDuration(stack)-count)/getFireRate()<=ammocounter.getInteger("ammo") && ammocounter.getInteger("ammo")>0)|| (playerIn.capabilities.isCreativeMode)) {
                if((getMaxItemUseDuration(stack)-count)!=0 && (getMaxItemUseDuration(stack)-count)%getFireRate()==0){
                    fire(playerIn.world, playerIn);
                    if (!playerIn.capabilities.isCreativeMode && !player.world.isRemote)ammocount--;
                }
            }

             else if (!readytofire && count == getMaxItemUseDuration(stack) - timetoreload) {
                    if (!player.world.isRemote) {
                        reload=true;

                    }
                    player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 1.0F, true);

             }
        }



    }
    private Boolean reload;


    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer && !worldIn.isRemote) {
            if(readytofire) {
                ammocounter.setInteger("ammo", ammocount);
            }else if(reload){
                reload=false;
                EntityPlayer playerIn = (EntityPlayer)entityLiving;
                stack.damageItem(1, playerIn);
                int ammoreserve = this.findAmmo(playerIn).getCount();
                if ((ammoreserve >= 0)) {
                    this.findAmmo(playerIn).shrink(Math.min(ammoreserve,AmmoConsumption));
                    ammocounter.setInteger("ammo",(int)((float)magsize/AmmoConsumption*Math.min(ammoreserve,AmmoConsumption)));
                }
            }
            stack.setTagCompound(ammocounter);
        }
        firing=false;

    }

    @SideOnly(Side.CLIENT)
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if(entityIn instanceof EntityPlayer && !(((EntityPlayer) entityIn).getActiveItemStack().getItem() instanceof AutoGunBase)){
            firing=false;
        }
    }



    public void fire(World worldIn,EntityPlayer playerIn){}
    public void PlayaSound(World worldIn,EntityPlayer playerIn){}



    public ItemStack findAmmo(EntityPlayer player) {
        if (this.isAmmo(player.getHeldItem(EnumHand.OFF_HAND))) {
            return player.getHeldItem(EnumHand.OFF_HAND);
        } else if (this.isAmmo(player.getHeldItem(EnumHand.MAIN_HAND))) {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isAmmo(itemstack)) {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }


    boolean isAmmo(ItemStack stack) {
        return stack.getItem().getRegistryName().toString().contains(AmmoType);
    }
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }
    public int getItemEnchantability() { return 1; }
    private int getAmmocount(ItemStack item,int ammo){
        return 0;
    }
}

