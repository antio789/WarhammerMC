package warhammermod.Entities;

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
import warhammermod.util.Handler.Entityinit;

public class FlameEntity extends AbstractFireballEntity {
    private int limitedlifespan=30;

    public FlameEntity(FMLPlayMessages.SpawnEntity packet, World worldIn﻿) {
        super(Entityinit.flame, worldIn﻿);
    }

    public FlameEntity(EntityType<? extends FlameEntity> p_i50160_1_, World p_i50160_2_) {
        super(p_i50160_1_, p_i50160_2_);
    }

    public FlameEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(Entityinit.flame, shooter, accelX, accelY, accelZ, worldIn);
    }

    public FlameEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(Entityinit.flame, x, y, z, accelX, accelY, accelZ, worldIn);
    }

    public FlameEntity(World world){
        super(Entityinit.flame,world);
    }



    public void tick()
    {

        --limitedlifespan;
        if(limitedlifespan<=0){remove();}
        super.tick();
    }

    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
        if (!this.world.isRemote) {
            Entity entity = p_213868_1_.getEntity();
            if (!entity.func_230279_az_()) {
                Entity entity1 = this.func_234616_v_();
                int i = entity.getFireTimer();
                entity.setFire(5);
                boolean flag = entity.attackEntityFrom(DamageSource.func_233547_a_(this, entity1), 5.0F);
                if (!flag) {
                    entity.func_241209_g_(i);
                } else if (entity1 instanceof LivingEntity) {
                    this.applyEnchantments((LivingEntity)entity1, entity);
                }
            }

        }
    }

    protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
        super.func_230299_a_(p_230299_1_);
        if (!this.world.isRemote) {
            Entity entity = this.func_234616_v_();
            if (entity == null || !(entity instanceof MobEntity) || this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.getEntity())) {
                BlockPos blockpos = p_230299_1_.getPos().offset(p_230299_1_.getFace());
                if (this.world.isAirBlock(blockpos)) {
                    this.world.setBlockState(blockpos, AbstractFireBlock.func_235326_a_(this.world, blockpos));
                }
            }

        }
    }


    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            this.remove();
        }

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

}
