package warhammermod.Entities.Projectile;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import warhammermod.utils.inithandler.Entityinit;

public class FlameEntity extends AbstractFireballEntity {
    private int limitedlifespan=30;

    public FlameEntity(FMLPlayMessages.SpawnEntity packet, World worldIn﻿) {
        super(Entityinit.Flame, worldIn﻿);
    }

    public FlameEntity(EntityType<? extends FlameEntity> p_i50160_1_, World p_i50160_2_) {
        super(p_i50160_1_, p_i50160_2_);
    }

    public FlameEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(Entityinit.Flame, shooter, accelX, accelY, accelZ, worldIn);
    }

    public FlameEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(Entityinit.Flame, x, y, z, accelX, accelY, accelZ, worldIn);
    }

    public FlameEntity(World world){
        super(Entityinit.Flame,world);
    }



    public void tick()
    {

        --limitedlifespan;
        if(limitedlifespan<=0){remove();}
        super.tick();
    }

    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        if (!this.level.isClientSide) {
            Entity entity = p_213868_1_.getEntity();
            if (!entity.fireImmune()) {
                Entity entity1 = this.getOwner();
                int i = entity.getRemainingFireTicks();
                entity.setSecondsOnFire(5);
                boolean flag = entity.hurt(DamageSource.fireball(this, entity1), 5.0F);
                if (!flag) {
                    entity.setRemainingFireTicks(i);
                } else if (entity1 instanceof LivingEntity) {
                    this.doEnchantDamageEffects((LivingEntity)entity1, entity);
                }
            }

        }
    }
    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        if (!this.level.isClientSide) {
            Entity entity = this.getOwner();
            if (entity == null || !(entity instanceof MobEntity) || this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getEntity())) {
                BlockPos blockpos = p_230299_1_.getBlockPos().relative(p_230299_1_.getDirection());
                if (this.level.isEmptyBlock(blockpos)) {
                    this.level.setBlockAndUpdate(blockpos, AbstractFireBlock.getState(this.level, blockpos));
                }
            }

        }
    }

    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        if (!this.level.isClientSide) {
            this.remove();
        }

    }


    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean isPickable() {
        return false;
    }

    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }

    @Override
    public IPacket<?> getAddEntityPacket(){
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
