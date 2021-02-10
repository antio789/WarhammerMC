package warhammermod.Entities;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.util.Handler.Entityinit;


public class HalberdEntity extends Projectilebase {
    int fuse=2;

    public HalberdEntity(FMLPlayMessages.SpawnEntity packet, World worldIn﻿) {
        super(Entityinit.HalberdEntity, worldIn﻿);
    }


    public HalberdEntity(EntityType<? extends HalberdEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }

    public HalberdEntity(World worldin, LivingEntity shooter, ItemStack stack, float damageIn) {
        super(worldin, shooter, stack, damageIn, Entityinit.HalberdEntity);
        projectiledamage = damageIn;
        this.pickupStatus= AbstractArrowEntity.PickupStatus.DISALLOWED;

    }

    public HalberdEntity(World world){
        super(Entityinit.HalberdEntity,world);
    }



    @OnlyIn(Dist.CLIENT)
    public HalberdEntity(World p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
        super(p_i48791_1_, Entityinit.HalberdEntity, p_i48791_2_, p_i48791_4_, p_i48791_6_);
    }

    protected void dealdamage(EntityRayTraceResult p_213868_1_){
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
        if (entity != getShooter() &&  entity.attackEntityFrom(damagesource, projectiledamage)) {


            if (p_213868_1_.getType() == RayTraceResult.Type.ENTITY && !world.isRemote) {
                projectiledamage += extradamage;
                if (entity instanceof LivingEntity && getDistance(getShooter()) * 2 < 7) {
                    LivingEntity entitylivingbase = (LivingEntity) entity;
                    if (knocklevel > 0) {
                        Vec3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double) this.knocklevel * 0.6D);
                        if (vec3d.lengthSquared() > 0.0D) {
                            entitylivingbase.addVelocity(vec3d.x, 0.1D, vec3d.z);
                        }
                    }
                    if (entity != getShooter()) {
                        entity.attackEntityFrom(damagesource, projectiledamage);
                    }

                }
            }
        }
/*                  // position incorrect
            if(world.isRemote){
                for (int k = 0; k < 10; ++k)
                    this.world.addParticle(RedstoneParticleData.REDSTONE_DUST, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), 0.0D, 0.0D, 0.0D);}
*/
        if (!this.world.isRemote) {
            this.remove();
        }

    }


    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRender3d(double x, double y, double z) {
        return false;
    }

    public void tick()
    {
        super.tick();
        if(fuse==0){
            this.remove();
        }
    }


}
