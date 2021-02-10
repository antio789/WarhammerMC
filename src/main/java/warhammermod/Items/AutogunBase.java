package warhammermod.Items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.world.World;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.Itimetoreload;

import java.util.Random;


public class AutogunBase extends Item implements Itimetoreload {

    public int timetoreload;
    public int Magsize;
    private String AmmoType;
    protected Random rand= new Random();
    public boolean hasshield=false;
    private int FireRate;
    private int AmmoConsumption;

    public AutogunBase(Properties properties, String ammotype, int time, int magsize, int ammoconsumption, int firerate) {
        super(properties);
        Magsize=magsize;
        timetoreload=time;
        AmmoType=ammotype;
        FireRate=firerate;
        AmmoConsumption=ammoconsumption;
        ItemsInit.ITEMS.add(this);

        this.addPropertyOverride(new ResourceLocation("reloading"), (stack, worldIn, entityIn) ->  {
            CompoundNBT ammocounter = stack.getTag();
                if (entityIn != null && entityIn instanceof PlayerEntity && !((PlayerEntity) entityIn).isCreative() && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && (ammocounter == null || ammocounter.getInt("ammo") <= 0)) {
                    if (ammocounter!=null && isCharged(stack) && (72000-entityIn.getItemInUseCount())>timetoreload) return 2.0F;
                    else return 1.0F;
                }
                else return 0.0F;

        });
        this.addPropertyOverride(new ResourceLocation("aiming3d"), (p_210309_0_, p_210309_1_, p_210309_2_) -> {
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
            PlayerEntity entityplayer = (PlayerEntity) player;
            if((isCharged(stack) && (getUseDuration(stack)-count)/getFireRate()<=getCharge(stack))|| entityplayer.isCreative()) {
                if((getUseDuration(stack)-count)!=0 && (getUseDuration(stack)-count)%getFireRate()==0){
                    fire( entityplayer,player.world,stack);
                }
            }
            else if ((count == getUseDuration(stack) - timetoreload) && !isCharged(stack)) {
                player.world.playSound(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
            }
        }
    }



    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity && !worldIn.isRemote && !((PlayerEntity) entityLiving).isCreative()) {

            if(isCharged(stack)) {
                setCharged(stack,getCharge(stack)-(getUseDuration(stack)-timeLeft)/getFireRate());
            }else{
                PlayerEntity playerIn = (PlayerEntity)entityLiving;
                stack.damageItem(1,playerIn,(p_220040_1_) -> {
                    p_220040_1_.sendBreakAnimation(playerIn.getActiveHand());
                });
                int ammoreserve = this.findAmmo(playerIn).getCount();
                if ((ammoreserve >= 0)) {
                    this.findAmmo(playerIn).shrink(Math.min(ammoreserve,AmmoConsumption));
                    setCharged(stack,(int)((float)Magsize/AmmoConsumption*Math.min(ammoreserve,AmmoConsumption)));
                }
            }
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

    public int getFireRate() {
        return FireRate;
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

}
