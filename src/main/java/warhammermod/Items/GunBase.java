package warhammermod.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Items.Melee.shieldtemplate;
import warhammermod.util.Handler.inithandler.Itemsinit;
import warhammermod.util.Itimetoreload;
import warhammermod.util.confighandler.confighandler;

import javax.annotation.Nullable;
import java.util.Random;

public class GunBase extends Item implements Itimetoreload {
    public int timetoreload;
    public int magsize;
    protected NBTTagCompound ammocounter;
    public boolean readytoFire;
    protected Random rand= new Random();
    protected float width = 1.5F;
    protected float height = 1.5F;
    private String AmmoType;
    public int ammocount=-1;
    protected boolean reloaded=false;
    public boolean hasshield=false;


    public GunBase(String name, int MagSize, int time, int durability,String ammoType, boolean enabled) {
        setUnlocalizedName(name);
        setRegistryName(name);
        AmmoType=ammoType;
        if (enabled) {
            Itemsinit.ITEMS.add(this);
        }
        setCreativeTab(CreativeTabs.COMBAT);
        magsize = MagSize;
        timetoreload = time;
        this.maxStackSize = 1;
        this.setMaxDamage(durability);

        this.addPropertyOverride(new ResourceLocation("reloading"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {

                if (entityIn != null && entityIn instanceof EntityPlayer && !((EntityPlayer) entityIn).capabilities.isCreativeMode && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && (ammocounter == null || ammocounter.getInteger("ammo") <= 0)) {
                    if (ammocounter!=null && reloaded) return 2.0F;
                    else return 1.0F;
                }
                else return 0.0F;
            }
        });
        this.addPropertyOverride(new ResourceLocation("aimwithshield"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {

                if (entityIn != null && entityIn instanceof EntityPlayer && !(((EntityPlayer) entityIn).capabilities.isCreativeMode) && !((EntityPlayer) entityIn).capabilities.isCreativeMode && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && !(ammocounter == null) && ammocounter.getInteger("ammo") > 0 && hasshield) {
                    return 1.0F;
                }
                else return 0.0F;
            }
        });

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        reloaded=false;
            ammocounter = itemstack.getTagCompound();
            if (ammocounter == null) {
                ammocounter = new NBTTagCompound();
            }
            if (playerIn.capabilities.isCreativeMode) {
                readytoFire = true;
            } else readytoFire = ammocounter.getInteger("ammo") > 0;





        boolean flag = !this.findAmmo(playerIn).isEmpty();
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;

        if (!playerIn.capabilities.isCreativeMode && !flag && !readytoFire) {
            return flag ? new ActionResult<>(EnumActionResult.PASS, itemstack) : new ActionResult<>(EnumActionResult.FAIL, itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
    }

    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        if (player instanceof EntityPlayer){
            if (readytoFire && !player.getHeldItemOffhand().isEmpty()) {
                hasshield = player.getHeldItemOffhand().getItem() instanceof shieldtemplate && ((shieldtemplate) player.getHeldItemOffhand().getItem()).gettype() == 1;
            }else hasshield=false;
            if (!readytoFire && !player.world.isRemote) {
                if (count == getMaxItemUseDuration(stack) - timetoreload) {
                    EntityPlayer entityplayer = (EntityPlayer) player;
                    int ammoreserve = this.findAmmo(entityplayer).getCount();
                    if ((ammoreserve > 0) && (!entityplayer.capabilities.isCreativeMode)) {
                        int infinitylevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack);
                        if (ammoreserve < magsize) {
                            ammocount = ammoreserve;

                            if (infinitylevel == 0) {
                                this.findAmmo(entityplayer).shrink(ammoreserve);
                            }
                        } else {
                            ammocount = magsize;

                            if (infinitylevel == 0) {
                                this.findAmmo(entityplayer).shrink(magsize);
                            }

                        }
                    }
                }
            }
            if ((count == getMaxItemUseDuration(stack) - timetoreload) && !readytoFire) {
                reloaded = true;
                player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
            }
        }
    }


    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            if (readytoFire) {
                worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.35F / (itemRand.nextFloat() * 0.4F + 1.2F) +  0.5F);
                if(worldIn.isRemote) {
                    for (int k = 0; k < confighandler.Config_enable.smokeamount; ++k) {

                        double d2 = this.rand.nextGaussian() * 0.02D;
                        double d0 = this.rand.nextGaussian() * 0.02D;
                        double d1 = this.rand.nextGaussian() * 0.02D;
                        worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, entityplayer.posX + entityplayer.getLookVec().x * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, entityplayer.posY + 0.4 + (double) (this.rand.nextFloat() * this.height), entityplayer.posZ + entityplayer.getLookVec().z * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, d2, d0, d1);
                    }
                }

            }
            if(!entityplayer.capabilities.isCreativeMode && !worldIn.isRemote){
                if(!readytoFire){ammocounter.setInteger("ammo",ammocount);ammocount=0;}
                else {ammocounter.setInteger("ammo",ammocounter.getInteger("ammo")-1);}
            }
            reloaded=false;
        }

    }

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

    private boolean isAmmo(ItemStack stack) {
        return stack.getItem().getRegistryName().toString().equals(AmmoType);
    }
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }
    public int getTimetoreload(){
        return timetoreload;
    }
    public boolean isReadytoFire(){
        return readytoFire;
    }


    public EnumAction getItemUseAction(ItemStack stack) {
        if(readytoFire && hasshield){return EnumAction.BLOCK;}else return EnumAction.BOW;
    }
    public int getItemEnchantability() { return 1; }

    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        return enchantment.type == EnumEnchantmentType.BREAKABLE || enchantment.type == EnumEnchantmentType.WEAPON;

    }
}
//return EnumAction.BOW;