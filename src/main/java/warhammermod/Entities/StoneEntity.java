package warhammermod.Entities;


import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.util.Handler.Entityinit;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class StoneEntity extends Projectilebase implements IRendersAsItem {

    public StoneEntity(FMLPlayMessages.SpawnEntity packet, World worldIn﻿) {
        super(Entityinit.stone, worldIn﻿);
    }

    public StoneEntity(EntityType<? extends StoneEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }
    public StoneEntity(World worldin, LivingEntity shooter, ItemStack stack, float damageIn) {
        super(worldin, shooter, stack, damageIn, Entityinit.stone);
        this.pickupStatus= PickupStatus.DISALLOWED;

    }

    @OnlyIn(Dist.CLIENT)
    public StoneEntity(World p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
        super(p_i48791_1_, Entityinit.stone, p_i48791_2_, p_i48791_4_, p_i48791_6_);
    }

    public StoneEntity(World world){
        super(Entityinit.stone,world);
    }

    private static final DataParameter<ItemStack> field_213864_b = EntityDataManager.createKey(StoneEntity.class, DataSerializers.ITEMSTACK);

    private ItemStack func_213861_i() {
        return this.getDataManager().get(field_213864_b);
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.func_213861_i();
        return itemstack.isEmpty() ? new ItemStack(Items.COBBLESTONE) : itemstack;
    }


    protected ItemStack getArrowStack(){return new ItemStack(Item.getItemFromBlock(Blocks.COBBLESTONE));}

    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        Entity entity1 = this.func_234616_v_();

        float f = MathHelper.sqrt(this.getMotion().x * this.getMotion().x + this.getMotion().y * this.getMotion().y + this.getMotion().z * this.getMotion().z);

        int i = MathHelper.ceil((double)f * projectiledamage);

        if (this.getIsCritical())
        {
            i += this.rand.nextInt(i / 2 + 2);
        }

        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.causeArrowDamage(this, this);
        } else {
            damagesource = DamageSource.causeArrowDamage(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastAttackedEntity(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTimer();
        if (this.isBurning() && !flag) {
            entity.setFire(5);
        }

        if (entity.attackEntityFrom(damagesource, i)) {
            if (flag) {
                return;
            }
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                if (this.knocklevel > 0) {
                    Vector3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knocklevel * 0.6D);
                    if (vec3d.lengthSquared() > 0.0D) {
                        livingentity.addVelocity(vec3d.x, 0.1D, vec3d.z);
                    }
                }
                if (!this.world.isRemote && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingentity, entity1);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity1, livingentity);
                }
                this.arrowHit(livingentity);
                if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity)entity1).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241770_g_, 0.0F));
                }
            }

            this.playSound(SoundEvents.BLOCK_STONE_BREAK, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            entity.func_241209_g_(j);
            this.setMotion(this.getMotion().scale(-0.1D));
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
                this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.COBBLESTONE)));
                this.remove();
            }
        }

    }

    protected void registerData() {
        super.registerData();
        this.getDataManager().register(field_213864_b, ItemStack.EMPTY);
    }

    public void func_213863_b(ItemStack p_213863_1_) {
        if (p_213863_1_.getItem() != Items.ENDER_EYE || p_213863_1_.hasTag()) {
            this.getDataManager().set(field_213864_b, Util.make(p_213863_1_.copy(), (p_213862_0_) -> {
                p_213862_0_.setCount(1);
            }));
        }

    }

    public void readAdditional(CompoundNBT compound) {
        ItemStack itemstack = ItemStack.read(compound.getCompound("Item"));
        this.func_213863_b(itemstack);
    }







}
