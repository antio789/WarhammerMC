package warhammermod.Items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.world.World;
import warhammermod.utils.inithandler.IReloadItem;
import warhammermod.utils.inithandler.ItemsInit;

import java.util.Random;

public class AutogunBase extends Item implements IReloadItem {
    public int timetoreload;
    public int Magsize;
    private final String AmmoType;
    protected Random rand= new Random();
    public boolean hasshield=false;
    private final int FireRate;
    private final int AmmoConsumption;

    public AutogunBase(Properties properties, String ammotype, int time, int magsize, int ammoconsumption, int firerate) {
        super(properties);
        Magsize=magsize;
        timetoreload=time;
        AmmoType=ammotype;
        FireRate=firerate;
        AmmoConsumption=ammoconsumption;
        ItemsInit.ITEMS.add(this);

        ItemModelsProperties.register(this,new ResourceLocation("reloading"),(stack, worldIn, entityIn) ->  {
            CompoundNBT ammocounter = stack.getTag();
            if (entityIn != null && entityIn instanceof PlayerEntity && !((PlayerEntity) entityIn).isCreative() && entityIn.isUsingItem() && entityIn.getUseItem() == stack && (ammocounter == null || ammocounter.getInt("ammo") <= 0)) {
                if (ammocounter!=null && !isCharged(stack) && (entityIn.getTicksUsingItem())<timetoreload) return 2.0F;
                else return 1.0F;
            }
            else return 0.0F;

        });

        ItemModelsProperties.register(this,new ResourceLocation("aiming3d"),(p_210309_0_, p_210309_1_, p_210309_2_) -> {
            return p_210309_2_ != null && p_210309_2_.isUsingItem() && p_210309_2_.getUseItem() == p_210309_0_ ? 1.0F : 0.0F;
        });
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity playerIn, Hand hand) {

        ItemStack stack = playerIn.getItemInHand(hand);
        if(isCharged(stack) ||playerIn.isCreative()|| !findAmmo(playerIn).isEmpty()){
            playerIn.startUsingItem(hand);
            return ActionResult.consume(stack);
        }
        return ActionResult.fail(stack);
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

    public void onUseTick(World world, LivingEntity player, ItemStack stack, int count) {
        if (player instanceof PlayerEntity){
            PlayerEntity entityplayer = (PlayerEntity) player;
            if((isCharged(stack) && (getUseDuration(stack)-count)/getFireRate()<=getCharge(stack))|| entityplayer.isCreative()) {
                if((getUseDuration(stack)-count)!=0 && (getUseDuration(stack)-count)%getFireRate()==0){
                    fire( entityplayer,world,stack);
                }
            }
            else if ((count == getUseDuration(stack) - timetoreload) && !isCharged(stack)) {
                world.playSound(null,player.blockPosition(),SoundEvents.FLINTANDSTEEL_USE,SoundCategory.PLAYERS,1,1);
            }
        }
    }
    public int getFireRate() {
        return FireRate;
    }
    public void fire(PlayerEntity player, World world, ItemStack stack){}

    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity && !worldIn.isClientSide() && !((PlayerEntity) entityLiving).isCreative()) {

            if(isCharged(stack)) {
                setCharge(stack,getCharge(stack)-(getUseDuration(stack)-timeLeft)/getFireRate());
            }else{
                PlayerEntity playerIn = (PlayerEntity)entityLiving;
                stack.hurtAndBreak(1, playerIn, (p_220009_1_) -> {
                    p_220009_1_.broadcastBreakEvent(playerIn.getUsedItemHand());
                });
                int ammoreserve = this.findAmmo(playerIn).getCount();
                if ((ammoreserve >= 0)) {
                    this.findAmmo(playerIn).shrink(Math.min(ammoreserve,AmmoConsumption));
                    setCharge(stack,(int)((float)Magsize/AmmoConsumption* Math.min(ammoreserve,AmmoConsumption)));
                }
            }
        }
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

    public static void setCharge(ItemStack stack, int ammo) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putInt("ammo", ammo);
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

    public int getTimetoreload(){ return timetoreload; }

    public boolean isReadytoFire(ItemStack stack){
        return isCharged(stack);
    }

    public int getItemEnchantability() {
        return 1;
    }
}
