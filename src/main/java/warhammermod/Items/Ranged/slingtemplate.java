package warhammermod.Items.Ranged;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import warhammermod.Entities.EntityStone;
import warhammermod.util.Handler.inithandler.Itemsinit;

import static warhammermod.Main.proxy;


public class slingtemplate extends ItemBow {
    //public boolean firing;
    public float charging;
    public EntityLivingBase user;
    public slingtemplate(String name,int durability,boolean enabled){
        super();
        setUnlocalizedName(name);
        setRegistryName(name);
        if (enabled) {
            Itemsinit.ITEMS.add(this);
        }
        this.setMaxDamage(durability);
        proxy.settheteisr(name,this);
        /*

        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    return entityIn.getActiveItemStack().getItem() != Itemsinit.sling ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("warhammermod:using"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? Math.min(1,(stack.getMaxItemUseDuration()- entityIn.getItemInUseCount()) / 20.0F) : 0.0F;
            }
        });*/
    }

    protected boolean isAmmo(ItemStack stack)
    {
        Item item= Item.getItemFromBlock(Blocks.COBBLESTONE);
        return (stack.getItem().getRegistryName().equals(item.getRegistryName()));
    }

    public ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isAmmo(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isAmmo(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isAmmo(itemstack))
                {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }



    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean flag = !this.findAmmo(playerIn).isEmpty();

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;

        if (!playerIn.capabilities.isCreativeMode && !flag)
        {
            return flag ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        else
        {
            playerIn.setActiveHand(handIn);
            //firing=true;
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
    }

    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {

        charging = Math.min(1,(stack.getMaxItemUseDuration()- count) / 20.0F);


    }



    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        //firing=false;
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean consumeammo = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = this.findAmmo(entityplayer);

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !itemstack.isEmpty() || consumeammo);
            if (i < 0) return;

            if (!itemstack.isEmpty() || consumeammo)
            {
                if (itemstack.isEmpty())
                {
                    itemstack = new ItemStack(Item.getItemFromBlock(Blocks.COBBLESTONE));
                }

                float f = getArrowVelocity(i);

                if ((double)f >= 0.1D)
                {

                    if (!worldIn.isRemote)
                    {
                        EntityStone entitystone = new EntityStone(worldIn,entityplayer,f*3);
                        entitystone.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 2.15F, 1.0F);

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if (j > 0) {
                            entitystone.setDamage(entitystone.getDamage() + (double)j * 0.5D + 0.5D);
                        }
                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack) + 1;
                        if (k > 0) {
                            entitystone.setKnockbackStrength(k);
                        }
                        if(consumeammo){
                            entitystone.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
                        }

                        worldIn.spawnEntity(entitystone);
                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            entitystone.setFire(100);
                        }
                        stack.damageItem(1, entityplayer);
                    }

                    worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!consumeammo)
                    {
                        itemstack.shrink(1);

                        if (itemstack.isEmpty())
                        {
                            entityplayer.inventory.deleteStack(itemstack);
                        }
                    }


                }
            }
        }
    }


}
