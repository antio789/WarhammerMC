package warhammermod.Entities;


import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.util.Handler.Entityinit;

public class SpearEntity extends Projectilebase {
    private ItemStack throwed_spear;

    public SpearEntity(FMLPlayMessages.SpawnEntity packet, World worldIn﻿) {
        super(Entityinit.spear, worldIn﻿);
    }

    public SpearEntity(EntityType<? extends SpearEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);

    }

    public SpearEntity(World worldin, LivingEntity shooter, ItemStack stack, float damageIn) {
        super(worldin, shooter, stack, damageIn, Entityinit.spear);
        this.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
        projectiledamage = damageIn;
        throwed_spear=stack;
    }

    @OnlyIn(Dist.CLIENT)
    public SpearEntity(World p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
        super(p_i48791_1_, Entityinit.spear, p_i48791_2_, p_i48791_4_, p_i48791_6_);
    }

    public SpearEntity(World world){
        super(Entityinit.spear,world);
    }


    protected void dealdamage(EntityRayTraceResult p_213868_1_){
        Entity entity_shot = p_213868_1_.getEntity();
        Entity shooter = this.getShooter();
        DamageSource damagesource;
        if (shooter == null) {
            damagesource = DamageSource.causeArrowDamage(this, this);
        } else {
            damagesource = DamageSource.causeArrowDamage(this, shooter);
            if (shooter instanceof LivingEntity) {
                ((LivingEntity)shooter).setLastAttackedEntity(entity_shot);
            }
        }
        float i = projectiledamage + extradamage;

        boolean flag = entity_shot.getType() == EntityType.ENDERMAN;
        int j = entity_shot.getFireTimer();
        if (this.isBurning() && !flag) {
            entity_shot.setFire(5);
        }

        if (entity_shot.attackEntityFrom(damagesource, i)) {
            if (flag) {
                return;
            }
            if (entity_shot instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity_shot;
                if (this.knocklevel > 0) {
                    Vec3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knocklevel * 0.6D);
                    if (vec3d.lengthSquared() > 0.0D) {
                        livingentity.addVelocity(vec3d.x, 0.1D, vec3d.z);
                    }
                }
                if (!this.world.isRemote && shooter instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingentity, shooter);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity)shooter, livingentity);
                }
                this.arrowHit(livingentity);
                if (shooter != null && livingentity != shooter && livingentity instanceof PlayerEntity && shooter instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity)shooter).connection.sendPacket(new SChangeGameStatePacket(6, 0.0F));
                }
            }

            this.playSound(SoundEvents.BLOCK_STONE_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.entityDropItem(throwed_spear);
                this.remove();
            }
        } else {
            entity_shot.setFireTimer(j);
            this.setMotion(this.getMotion().scale(-0.1D));
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
                this.entityDropItem(throwed_spear);
                this.remove();
            }
        }
    }




}