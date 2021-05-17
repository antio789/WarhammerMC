package warhammermod.Items.Ranged;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.world.World;
import warhammermod.Entities.Projectile.BulletEntity;
import warhammermod.Items.Render.RenderRepeater;
import warhammermod.Items.melee.ShieldTemplate;
import warhammermod.utils.inithandler.IReloadItem;
import warhammermod.utils.inithandler.ItemsInit;
import warhammermod.utils.inithandler.WarhammermodRegistry;
import warhammermod.utils.reference;


import java.util.Random;


public class GunSwordTemplate extends SwordItem implements IReloadItem {
    float damage;
    public int timetoreload;
    public int Magsize;
    protected Random rand= new Random();
    public boolean hasshield=false; //use player.getitemoffhand instead
    public float width = 1.5F;
    public float height = 1.5F;



    public GunSwordTemplate(ItemTier tier, int time,int magsize,float damagein){
        super(tier,3 ,-2.4F,(new Properties()).tab(reference.warhammer));
        damage=damagein;
        timetoreload=time;
        Magsize=magsize;
        ItemModelsProperties.register(this,new ResourceLocation("firing"),(stack, worldIn, entityIn) ->  {
            ;
            return stack != null && entityIn instanceof PlayerEntity && entityIn.isUsingItem() && entityIn.getUseItem() == stack && (isCharged(stack) || ((PlayerEntity) entityIn).isCreative())? 1.0F : 0.0F;

        });
        ItemModelsProperties.register(this,new ResourceLocation("aimwithshield"),(stack, worldIn, entityIn) -> {
            CompoundNBT ammocounter = stack.getTag();
            if (entityIn != null && entityIn instanceof PlayerEntity && !(((PlayerEntity) entityIn).isCreative()) && entityIn.isUsingItem() && entityIn.getUseItem() == stack && isCharged(stack) && hasshield((PlayerEntity) entityIn)) {
                return 1.0F;
            }
            else return 0.0F;

        });

        ItemsInit.ITEMS.add(this);
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
        return stack.getItem().equals(ItemsInit.Cartridge);
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

    public void fire(PlayerEntity player, World world, ItemStack stack) {
        if(world.isClientSide()) {
            for (int k = 0; k < 40; ++k) {
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, player.getX() + player.getLookAngle().x * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, player.getY() + 0.4 + (double) (this.rand.nextFloat() * this.height), player.getZ() + player.getLookAngle().z * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, d2, d0, d1);
            }
        }
        if(!world.isClientSide()) {
            world.playSound(null,player.blockPosition(),SoundEvents.GENERIC_EXPLODE,SoundCategory.PLAYERS,1,1.35F/(rand.nextFloat()*0.4F+1.2F)+0.5F);

            BulletEntity bullet = new BulletEntity(player, world, damage);
            bullet.setPos(player.getX(), player.getEyeY() - 0.26, player.getZ());
            bullet.shootFromRotation(player,player.xRot, player.yRot, 0, 3.5F, 0.3F);

            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
            if (i > 0) {
                bullet.setpowerDamage(i);
            }
            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack) + 1;
            if (k > 0) {
                bullet.setknockbacklevel(k);
            }

            world.addFreshEntity(bullet);
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                bullet.setSecondsOnFire(100);
            }

            stack.hurtAndBreak(1, player, (p_220009_1_) -> {
                p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
            });
        }
        else if(stack.getItem().equals(ItemsInit.repeater_handgun)){
            RenderRepeater.setrotationangle();
        }
    }

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

    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        return enchantment.category == EnchantmentType.BOW || super.canApplyAtEnchantingTable(stack, enchantment);
    }



}
