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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.util.Handler.Entityinit;


public class BulletEntity extends Projectilebase {



    public BulletEntity(FMLPlayMessages.SpawnEntity packet, World worldIn﻿) {
        super(Entityinit.Bullet, worldIn﻿);
    }


    public BulletEntity(EntityType<? extends BulletEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }


    public BulletEntity(World worldin, LivingEntity shooter, ItemStack stack, float damageIn) {
        super(worldin, shooter, stack, damageIn, Entityinit.Bullet);
        projectiledamage = damageIn;
        this.pickupStatus= AbstractArrowEntity.PickupStatus.DISALLOWED;
    }

    public BulletEntity(World world){
        super(Entityinit.Bullet,world);
    }

    @OnlyIn(Dist.CLIENT)
    public BulletEntity(World p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
        super(p_i48791_1_, Entityinit.Bullet, p_i48791_2_, p_i48791_4_, p_i48791_6_);
    }


    protected void registerData() {
        super.registerData();
    }





    protected void dealdamage(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        Entity entity1 = this.getShooter();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.causeArrowDamage(this, this);
        } else {
            damagesource = DamageSource.causeArrowDamage(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastAttackedEntity(entity);
            }
        }
        float i = projectiledamage + extradamage;

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTimer();
        if (this.isBurning() && !flag) {
            entity.setFire(5);
        }

        if (entity.attackEntityFrom(damagesource, (float)i)) {
            if (flag) {
                return;
            }
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                if (this.knocklevel > 0) {
                    Vec3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knocklevel * 0.6D);
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
                    ((ServerPlayerEntity)entity1).connection.sendPacket(new SChangeGameStatePacket(6, 0.0F));
                }
            }

            this.playSound(SoundEvents.BLOCK_STONE_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            entity.setFireTimer(j);
            this.setMotion(this.getMotion().scale(-0.1D));
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
                this.remove();
            }
        }

    }

    public void tick()
    {

        if (this.world.isRemote && !this.inGround)
        {
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), 0.0D, 0.0D, 0.0D);
        }
        super.tick();
    }


}
