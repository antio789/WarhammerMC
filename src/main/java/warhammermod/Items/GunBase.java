package warhammermod.Items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.world.World;
import warhammermod.Items.melee.shieldtemplate;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.Itimetoreload;

import java.util.Random;


public class GunBase extends Item implements Itimetoreload {

    public int timetoreload;
    public int Magsize;
    private String AmmoType;
    protected Random rand= new Random();
    public boolean hasshield=false; //use player.getitemoffhand instead
    public float width = 1.5F;
    public float height = 1.5F;


    public GunBase(Properties properties, String ammotype, int time, int magsize) {
        super(properties);
        Magsize=magsize;
        timetoreload=time;
        AmmoType=ammotype;
        ItemsInit.ITEMS.add(this);

        ItemModelsProperties.func_239418_a_(this,new ResourceLocation("reloading"),(stack, worldIn, entityIn) ->  {
            CompoundNBT ammocounter = stack.getTag();
            if (entityIn != null && entityIn instanceof PlayerEntity && !((PlayerEntity) entityIn).isCreative() && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && (ammocounter == null || ammocounter.getInt("ammo") <= 0)) {
                if (ammocounter!=null && isCharged(stack) && (72000-entityIn.getItemInUseCount())>timetoreload) return 2.0F;
                else return 1.0F;
            }
            else return 0.0F;

        });

        ItemModelsProperties.func_239418_a_(this,new ResourceLocation("aimwithshield"),(stack, worldIn, entityIn) -> {
            if (entityIn != null && entityIn instanceof PlayerEntity && !(((PlayerEntity) entityIn).isCreative()) && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && isCharged(stack) && hasshield((PlayerEntity) entityIn)) {
                return 1.0F;
            }
            else return 0.0F;

        });

        ItemModelsProperties.func_239418_a_(this,new ResourceLocation("aiming3d"),(p_210309_0_, p_210309_1_, p_210309_2_) -> {
            return p_210309_2_ != null && p_210309_2_.isHandActive() && p_210309_2_.getActiveItemStack() == p_210309_0_ ? 1.0F : 0.0F;
        });
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack stack = playerIn.getHeldItem(handIn);
        if(isCharged(stack) ||playerIn.isCreative()|| !findAmmo(playerIn).isEmpty()){
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(stack);
        }
        return ActionResult.resultFail(stack);

    }


    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (player instanceof PlayerEntity){


            if ((count == getUseDuration(stack) - timetoreload) && !isCharged(stack) && !((PlayerEntity) player).isCreative()) {
                player.world.playSound(player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
            }
        }
    }



    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity entityplayer = (PlayerEntity) entityLiving;
            if (isCharged(stack) || entityplayer.isCreative()) {
                fire(entityplayer,worldIn,stack);
                if(!entityplayer.isCreative())setCharged(stack,getCharge(stack)-1);
            }
            else if(timetoreload<=getUseDuration(stack)-timeLeft && !worldIn.isRemote()) {
                        int ammoreserve = this.findAmmo(entityplayer).getCount();
                            int infinitylevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack);
                            if (ammoreserve < Magsize) {
                                if (infinitylevel == 0) {
                                    this.findAmmo(entityplayer).shrink(ammoreserve);
                                }
                                setCharged(stack,ammoreserve);
                            } else {
                                if (infinitylevel == 0) {
                                    this.findAmmo(entityplayer).shrink(Magsize);
                                }
                                setCharged(stack,Magsize);

                            }
                        }
            }

    }

    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(entityIn instanceof PlayerEntity){
            setshield((PlayerEntity) entityIn);
        }
    }

    public ItemStack findAmmo(PlayerEntity player) {
        if (this.isAmmo(player.getHeldItem(Hand.OFF_HAND))) {
            return player.getHeldItem(Hand.OFF_HAND);
        } else if (this.isAmmo(player.getHeldItem(Hand.MAIN_HAND))) {
            return player.getHeldItem(Hand.MAIN_HAND);
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

    public Boolean hasshield(PlayerEntity player){
        return player.getHeldItemOffhand().getItem() instanceof shieldtemplate && ((shieldtemplate) player.getHeldItemOffhand().getItem()).gettype() == 1;
    }
    private Boolean useshield(PlayerEntity player, ItemStack stack){
        return hasshield(player) && isCharged(stack);
    }

    private void setshield(PlayerEntity player){
        hasshield = player.getHeldItemOffhand().getItem() instanceof shieldtemplate && ((shieldtemplate) player.getHeldItemOffhand().getItem()).gettype() == 1;
    }


    private boolean isAmmo(ItemStack stack) {
        return stack.getItem().getRegistryName().toString().contains(AmmoType);
    }

    public void fire(PlayerEntity player, World world, ItemStack stack){}

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public int getTimetoreload(){
        return timetoreload;
    }
    public boolean isReadytoFire(ItemStack stack){
        return isCharged(stack);
    }

    public UseAction getUseAction(ItemStack stack) {
        if(isCharged(stack) && hasshield)return UseAction.BLOCK;else return UseAction.BOW;
    }

    public static boolean isCharged(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.getInt("ammo")>0;
    }

    public static int getCharge(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        if(compoundnbt == null){return 0;}
        return compoundnbt.getInt("ammo");
    }

    public static void setCharged(ItemStack stack, int ammo) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putInt("ammo", ammo);
    }

    public int getItemEnchantability() {
        return 1;
    }


    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        if(enchantment.getName().equals(Enchantments.FLAME.getName()))return false;
        switch(enchantment.type){
            case BOW:
            case BREAKABLE:return true;
            default:return false;
        }
    }

}
