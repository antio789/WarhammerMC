package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.entity.TippedArrowRenderer;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.DwarfEntity;
import warhammermod.util.Handler.WarhammermodRegistry;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class AttackTask extends Task<MobEntity> {
    @Nullable
    private Path path;
    @Nullable
    private BlockPos targetpos;
    private double speed;
    private int pathupdateCD;
    protected int attackTick;
    protected LivingEntity Target;
    private double targetX;
    private double targetY;
    private double targetZ;

    public AttackTask(int p_i50356_1_) {
        super(ImmutableMap.of(MemoryModuleType.PATH, MemoryModuleStatus.VALUE_ABSENT, WarhammermodRegistry.ATTACK_TARGET, MemoryModuleStatus.VALUE_PRESENT), p_i50356_1_);
    }

    protected boolean shouldExecute(ServerWorld worldIn, MobEntity owner) {
        speed = owner.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()*1.5F;
        Brain<?> brain = owner.getBrain();
        LivingEntity livingentity = brain.getMemory(WarhammermodRegistry.ATTACK_TARGET).get();
        if (livingentity == null || !livingentity.isAlive()) {
            brain.removeMemory(WarhammermodRegistry.ATTACK_TARGET);
            return false;
        }
        else {
            this.Target= livingentity;
            this.path = owner.getNavigator().getPathToEntity(livingentity, 0);
            if (this.path != null) {
                return true;
            } else {
                return this.getAttackReachSqr(livingentity,owner) >= owner.getDistanceSq(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ());
            }
            }
        }


    protected boolean shouldContinueExecuting(ServerWorld worldIn, MobEntity entityIn, long gameTimeIn) {
        LivingEntity livingentity = Target;
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        }  else if (!entityIn.isWithinHomeDistanceFromPosition(new BlockPos(livingentity))) {
            return false;
        } else {
            return !(livingentity instanceof PlayerEntity) || !livingentity.isSpectator() && !((PlayerEntity)livingentity).isCreative();
        }

    }

    protected void resetTask(ServerWorld worldIn, MobEntity entityIn, long gameTimeIn) {
        LivingEntity livingentity = Target;
        if (!EntityPredicates.CAN_AI_TARGET.test(livingentity)) {
            entityIn.getBrain().removeMemory(WarhammermodRegistry.ATTACK_TARGET);
        }

        entityIn.setAggroed(false);
        entityIn.getNavigator().clearPath();
        entityIn.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
        entityIn.getBrain().removeMemory(MemoryModuleType.PATH);

        this.path = null;
    }

    protected void startExecuting(ServerWorld worldIn, MobEntity entityIn, long gameTimeIn) {
        entityIn.getBrain().setMemory(MemoryModuleType.PATH, this.path);
        entityIn.getNavigator().setPath(this.path, (double)this.speed);
        this.pathupdateCD = worldIn.getRandom().nextInt(10);
        entityIn.setAggroed(true);
        alertOthers(entityIn);
    }

    protected void updateTask(ServerWorld worldIn, MobEntity owner, long gameTime) {
        owner.getLookController().setLookPositionWithEntity(Target, 30.0F, 30.0F);
        double d0 = owner.getDistanceSq(Target.getPosX(), Target.getPosY(), Target.getPosZ());

        --this.pathupdateCD;
        if (this.pathupdateCD <= 0 && owner.getEntitySenses().canSee(Target) && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || Target.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || owner.getRNG().nextFloat() < 0.05F)) {
            Path path = owner.getNavigator().getPath();
            Brain<?> brain = owner.getBrain();
            if (this.path != path) {
                this.path = path;
                brain.setMemory(MemoryModuleType.PATH, path);
                this.startExecuting(worldIn, owner, gameTime);
            }
            this.targetX = Target.getPosX();
            this.targetY = Target.getPosY();
            this.targetZ = Target.getPosZ();


            if (!owner.getNavigator().tryMoveToEntityLiving(Target, this.speed)) {
                this.pathupdateCD += 15;
            }
        }
        this.attackTick = Math.max(this.attackTick - 1, 0);
        this.checkAndPerformAttack(Target, d0,owner);
    }

    private boolean canMoveto(MobEntity entity, WalkTarget target, long gametime) {
        BlockPos blockpos = target.getTarget().getBlockPos();
        this.path = entity.getNavigator().getPathToPos(blockpos, 0);
        this.speed = target.getSpeed();
        if (!this.hasReachedTarget(entity, target)) {
            Brain<?> brain = entity.getBrain();
            boolean flag = this.path != null && this.path.isFinished();
            if (flag) {
                brain.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, Optional.empty());
            } else if (!brain.hasMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)) {
                brain.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, gametime);
            }

            if (this.path != null) {
                return true;
            }

            Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards((CreatureEntity)entity, 10, 7, new Vec3d(blockpos));
            if (vec3d != null) {
                this.path = entity.getNavigator().getPathToPos(vec3d.x, vec3d.y, vec3d.z, 0);
                return this.path != null;
            }
        }

        return false;
    }

    private boolean hasReachedTarget(MobEntity p_220486_1_, WalkTarget p_220486_2_) {
        return p_220486_2_.getTarget().getBlockPos().manhattanDistance(new BlockPos(p_220486_1_)) <= p_220486_2_.getDistance();
    }

    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr, LivingEntity attacker) {
        double d0 = this.getAttackReachSqr(enemy,attacker)+0.5;
        if (distToEnemySqr <= d0 && this.attackTick <= 0) {
            this.attackTick = 20;
            attacker.swingArm(Hand.MAIN_HAND);
            attacker.attackEntityAsMob(enemy);
        }

    }

    protected double getAttackReachSqr(LivingEntity attackTarget, LivingEntity attacker) {
        return (double)(attacker.getWidth() * 2.0F * attacker.getWidth() * 2.0F + attackTarget.getWidth());
    }

    protected void alertOthers(MobEntity dwarf) {
        double d0 = this.getTargetDistance(dwarf);
        List<MobEntity> list = dwarf.world.getLoadedEntitiesWithinAABB(dwarf.getClass(), (new AxisAlignedBB(dwarf.getPosX(), dwarf.getPosY(), dwarf.getPosZ(), dwarf.getPosX() + 1.0D, dwarf.getPosY() + 1.0D, dwarf.getPosZ() + 1.0D)).grow(d0, 10.0D, d0));
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            MobEntity mobentity = (MobEntity) iterator.next();
            while (dwarf != mobentity && Target == null && !(mobentity instanceof AbstractVillagerEntity)) {

                mobentity = (MobEntity) iterator.next();

            }
            if(!mobentity.getBrain().getMemory(WarhammermodRegistry.ATTACK_TARGET).isPresent()) mobentity.getBrain().setMemory(WarhammermodRegistry.ATTACK_TARGET,Target);

        }
    }

        protected double getTargetDistance(LivingEntity dwarf) {
            IAttributeInstance iattributeinstance = dwarf.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
            return iattributeinstance == null ? 16.0D : iattributeinstance.getValue();
        }
    }
