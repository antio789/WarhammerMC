package warhammermod.Items.Ranged;


import net.minecraft.block.BlockDispenser;
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
import warhammermod.Entities.EntityBullet;
import warhammermod.Items.Melee.shieldtemplate;
import warhammermod.util.Handler.inithandler.Itemsinit;
import warhammermod.util.Itimetoreload;
import warhammermod.util.confighandler.confighandler;
import warhammermod.util.reference;

import javax.annotation.Nullable;
import java.util.Random;

public class gunswtemplate extends ItemSword implements Itimetoreload {
    public gunswtemplate(ToolMaterial material, String name, boolean enabled, int Timetoreload){
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        magsize = 1;
        timetoreload = Timetoreload;
        this.addPropertyOverride(new ResourceLocation("firing"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
        this.addPropertyOverride(new ResourceLocation("reloading"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                reloader=stack.getTagCompound();
                return entityIn != null && entityIn instanceof EntityPlayer && !((EntityPlayer) entityIn).capabilities.isCreativeMode && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack  && (reloader==null || reloader.getInteger("ammo")<=0) ? 2.0F : 0.0F;
            }
        });


        if(enabled){ Itemsinit.ITEMS.add(this);}
    }

    private int magsize;
    private int timetoreload;
    private int ammocount = 0;
    private NBTTagCompound ammocounter;
    private NBTTagCompound reloader;
    public boolean readytoFire=false;
    public boolean hasshield=false;
    public int damage = confighandler.Config_values.gunsword_damage;

    private float width = 1.2F;
    private float height = 1.2F;
    protected Random rand= new Random();
    public  int getTimetoreload(){
        return timetoreload;
    }
    public boolean isReadytoFire(){return readytoFire;}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

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
        if(player instanceof EntityPlayer) {
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
                player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
            }
            if (readytoFire && !player.getHeldItemOffhand().isEmpty()) {
                hasshield = player.getHeldItemOffhand().getItem() instanceof shieldtemplate && ((shieldtemplate) player.getHeldItemOffhand().getItem()).gettype() == 1;
            }else hasshield=false;
        }

    }


    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            if(readytoFire) {
                worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F);
                for (int k = 0; k < confighandler.Config_enable.smokeamount; ++k)
                {

                    double d2 = this.rand.nextGaussian() * 0.02D;
                    double d0 = this.rand.nextGaussian() * 0.02D;
                    double d1 = this.rand.nextGaussian() * 0.02D;
                    worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, entityplayer.posX + entityplayer.getLookVec().x*2 + (double)(this.rand.nextFloat() * this.width*2) - (double)this.width, entityplayer.posY+0.4 + (double)(this.rand.nextFloat() * this.height), entityplayer.posZ + entityplayer.getLookVec().z*2+ (double)(this.rand.nextFloat() * this.width*2) - (double)this.width, d2, d0, d1);
                }

                if (!worldIn.isRemote) {
                    EntityBullet entitybullet = new EntityBullet(worldIn, entityplayer, damage);
                    entitybullet.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 4F, 0.2F);

                    int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                    if (j > 0) {
                        entitybullet.setpowerDamage(j);
                    }
                    int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack) + 1;
                    if (k > 0) {
                        entitybullet.setknockbacklevel(k);
                    }

                    worldIn.spawnEntity(entitybullet);
                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                        entitybullet.setFire(100);
                    }
                    stack.damageItem(1, entityplayer);

                }
            }
            if(!entityplayer.capabilities.isCreativeMode && !worldIn.isRemote) {
                ammocounter = stack.getTagCompound();
                if (ammocounter == null) {
                    ammocounter = new NBTTagCompound();
                    ammocounter.setInteger("ammo", ammocount);
                    ammocount=0;
                    stack.setTagCompound(ammocounter);
                } else if (ammocount > 0) {
                    ammocounter.setInteger("ammo", ammocount);
                    stack.setTagCompound(ammocounter);
                    ammocount = 0;
                } else {
                    ammocounter.setInteger("ammo", ammocounter.getInteger("ammo") - 1);
                    stack.setTagCompound(ammocounter);
                }
            }
        }

    }





    private ItemStack findAmmo(EntityPlayer player) {
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

    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {

        return enchantment.type == EnumEnchantmentType.WEAPON || enchantment.type == EnumEnchantmentType.BREAKABLE || enchantment.type == EnumEnchantmentType.BOW;
    }

    private boolean isAmmo(ItemStack stack) {
        return stack.getItem().getRegistryName().toString().equals(reference.Cartridge);
    }
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }
    public EnumAction getItemUseAction(ItemStack stack) {
        if(readytoFire && hasshield){return EnumAction.BLOCK;}else return EnumAction.BOW;
    }

    public int getItemEnchantability() { return 1; }
}
