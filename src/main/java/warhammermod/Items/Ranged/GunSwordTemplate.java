package warhammermod.Items.Ranged;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.world.World;
import warhammermod.Entities.BulletEntity;
import warhammermod.Items.melee.shieldtemplate;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.Itimetoreload;
import warhammermod.util.reference;

import java.util.Random;


public class GunSwordTemplate extends SwordItem implements Itimetoreload {
    public int timetoreload;
    public int Magsize=1;
    public boolean hasshield;
    CompoundNBT ammocounter;
    protected Random rand= new Random();
    public float width = 1.5F;
    public float height = 1.5F;



    public GunSwordTemplate(ItemTier tier, int time){
        super(tier,3 ,-2.4F,(new Item.Properties()).group(reference.warhammer));
        timetoreload=time;
        this.addPropertyOverride(new ResourceLocation("firing"), (p_210309_0_, p_210309_1_, p_210309_2_) -> {
            return p_210309_2_ != null && p_210309_2_.isHandActive() && p_210309_2_.getActiveItemStack() == p_210309_0_ && isCharged(p_210309_0_)? 1.0F : 0.0F;
        });

        this.addPropertyOverride(new ResourceLocation("aimwithshield"), (stack, worldIn, entityIn) -> {
            CompoundNBT ammocounter = stack.getTag();
            if (entityIn != null && entityIn instanceof PlayerEntity && !(((PlayerEntity) entityIn).isCreative()) && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && !(ammocounter == null) && ammocounter.getInt("ammo") > 0 && hasshield((PlayerEntity) entityIn)) {
                return 1.0F;
            }
            else return 0.0F;
        });
        ItemsInit.ITEMS.add(this);
    }

    public void fire(PlayerEntity player, World world, ItemStack stack){
        world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.35F / (rand.nextFloat() * 0.4F + 1.2F) +  0.5F);
        if(world.isRemote) {
            for (int k = 0; k < 30; ++k) {

                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,player.getPosition().getX() + player.getLookVec().x * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width,player.getPosition().getY()+ 0.4 + (double) (this.rand.nextFloat() * this.height),player.getPosition().getZ()+ player.getLookVec().z * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width,d2, d0, d1);
            }
        }
        if(!world.isRemote){
            BulletEntity bullet = new BulletEntity(world,player,stack,8);
            bullet.setPosition(player.getPosX(), player.getPosYEye() - 0.2, player.getPosZ());
            bullet.shoot(player,player.rotationPitch,player.rotationYaw,0,4,0.2F);

            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
            if (i > 0) {
                bullet.setpowerDamage(i);
            }
            int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack) + 1;
            if (k > 0) {
                bullet.setknockbacklevel(k);
            }

            world.addEntity(bullet);
            if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME,stack)>0){bullet.setFire(100);}

            stack.damageItem(1,player,(p_220040_1_) -> {
                p_220040_1_.sendBreakAnimation(player.getActiveHand());
            });
        }
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
                player.world.playSound(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
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
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    private boolean isAmmo(ItemStack stack) {
        return stack.getItem()== ItemsInit.Cartridge;
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

    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(entityIn instanceof PlayerEntity){
            setshield((PlayerEntity) entityIn);
        }
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        switch(enchantment.type){
            case WEAPON:
            case BREAKABLE:
            case BOW:return true;
            default: return false;
        }
    }

    private void setshield(PlayerEntity player){
        hasshield = player.getHeldItemOffhand().getItem() instanceof shieldtemplate && ((shieldtemplate) player.getHeldItemOffhand().getItem()).gettype() == 1;
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
}
