package warhammermod.Items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
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
import warhammermod.Items.melee.ShieldTemplate;
import warhammermod.utils.inithandler.IReloadItem;
import warhammermod.utils.inithandler.ItemsInit;
import warhammermod.utils.reference;

import java.util.Random;

public class GunBase extends Item implements IReloadItem {
    public int timetoreload;
    public int Magsize;
    private final Item AmmoType;
    protected Random rand= new Random();
    public boolean hasshield=false; //use player.getitemoffhand instead
    public float width = 1.5F;
    public float height = 1.5F;

    public GunBase(Properties properties, Item ammotype, int time, int magsize) {
        super(properties.tab(reference.warhammer));
        Magsize=magsize;
        timetoreload=time;
        AmmoType=ammotype;
        ItemsInit.ITEMS.add(this);
        ItemModelsProperties.register(this,new ResourceLocation("reloading"),(stack, worldIn, entityIn) ->  {
            CompoundNBT ammocounter = stack.getTag();
            if (entityIn != null && entityIn instanceof PlayerEntity && !((PlayerEntity) entityIn).isCreative() && entityIn.isUsingItem() && entityIn.getUseItem() == stack && (ammocounter == null || ammocounter.getInt("ammo") <= 0)) {
                if (ammocounter!=null && !isCharged(stack) && (entityIn.getTicksUsingItem())>timetoreload) return 2.0F;
                else return 1.0F;
            }
            else return 0.0F;
        });
        ItemModelsProperties.register(this,new ResourceLocation("aimwithshield"),(stack, worldIn, entityIn) -> {
            if (entityIn != null && entityIn instanceof PlayerEntity && !(((PlayerEntity) entityIn).isCreative()) && entityIn.isUsingItem() && entityIn.getUseItem() == stack && isCharged(stack) && hasshield((PlayerEntity) entityIn)) {
                return 1.0F;
            }
            else return 0.0F;

        });
    }

    public boolean isReadytoFire(ItemStack stack){
        return isCharged(stack);
    }

    public ItemStack findAmmo(PlayerEntity player) {
        if (this.isAmmo(player.getItemInHand(Hand.OFF_HAND))) {
            return player.getItemInHand(Hand.OFF_HAND);
        } else if (this.isAmmo(player.getItemInHand(Hand.MAIN_HAND))) {
            return player.getItemInHand(Hand.MAIN_HAND);
        } else {
            for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
                ItemStack itemstack = player.inventory.getItem(i);
                if (this.isAmmo(itemstack)) {
                    return itemstack;
                }
            }
            return ItemStack.EMPTY;
        }
    }

    private boolean isAmmo(ItemStack stack) {
        return stack.getItem().equals(AmmoType);
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity playerIn, Hand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        if(isCharged(stack) || playerIn.isCreative()|| !findAmmo(playerIn).isEmpty()){
            playerIn.startUsingItem(hand);
            return ActionResult.consume(stack);
        }
        return ActionResult.fail(stack);
    }

    @Override
    public void onUseTick(World world, LivingEntity player, ItemStack stack, int count) {
        if (player instanceof PlayerEntity){
            if ((count == getUseDuration(stack) - timetoreload) && !isCharged(stack) && !((PlayerEntity) player).isCreative() && !world.isClientSide()) {
                world.playSound(null,player.blockPosition(),SoundEvents.FLINTANDSTEEL_USE,SoundCategory.PLAYERS,1,1);
            }
        }
    }


    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity entityplayer = (PlayerEntity) entityLiving;
            CompoundNBT tag = stack.getOrCreateTag();
            if (isCharged(stack) || entityplayer.isCreative()) {
                fire(entityplayer,worldIn,stack);
                if(!entityplayer.isCreative())setCharge(stack,getCharge(stack,tag)-1,tag);
            }
            else if(timetoreload<=getUseDuration(stack)-timeLeft && !worldIn.isClientSide()) {
                int ammoreserve = this.findAmmo(entityplayer).getCount();
                int infinitylevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack);
                if (ammoreserve < Magsize) {
                    if (infinitylevel == 0) {
                        this.findAmmo(entityplayer).shrink(ammoreserve);
                    }
                    setCharge(stack,ammoreserve,tag);
                } else {
                    if (infinitylevel == 0) {
                        this.findAmmo(entityplayer).shrink(Magsize);
                    }
                    setCharge(stack,Magsize,tag);
                }
            }
        }

    }

    public void inventoryTick(ItemStack p_77663_1_, World p_77663_2_, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        if(entity instanceof PlayerEntity)  setshield((PlayerEntity) entity);
    }

    public void fire(PlayerEntity player, World world, ItemStack stack){}

    public static boolean isCharged(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.getInt("ammo")>0;
    }

    public static int getCharge(ItemStack stack, CompoundNBT nbt) {
        CompoundNBT compoundnbt = stack.getTag();
        if(compoundnbt == null){return 0;}
        return compoundnbt.getInt("ammo");
    }

    public static void setCharge(ItemStack stack, int ammo, CompoundNBT nbt) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putInt("ammo", ammo);
    }

    public int getUseDuration(ItemStack stack) { return 72000; }

    public UseAction getUseAnimation(ItemStack stack) {
        return (isCharged(stack) && hasshield) ? UseAction.BLOCK:UseAction.BOW;
    }

    public Boolean hasshield(PlayerEntity player){
        return player.getOffhandItem().getItem() instanceof ShieldTemplate;
    }

   private void setshield(PlayerEntity player){ hasshield = hasshield(player); }

    public int getTimetoreload(){ return timetoreload; }

    public int getEnchantmentValue() { return 1; }

    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        return enchantment.category == EnchantmentType.BOW || super.canApplyAtEnchantingTable(stack, enchantment);

    }


}
