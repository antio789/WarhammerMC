package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;
import warhammermod.util.Handler.WarhammermodRegistry;

import java.util.Map;

public class TargetTask extends Task<CreatureEntity> {
   private final MemoryModuleType<? extends Entity> target;
   private final float Speed;

   public TargetTask(MemoryModuleType<? extends Entity> p_i50346_1_, float p_i50346_2_) {
      super(ImmutableMap.of(WarhammermodRegistry.ATTACK_TARGET,MemoryModuleStatus.VALUE_ABSENT,p_i50346_1_, MemoryModuleStatus.VALUE_PRESENT));
      this.target = p_i50346_1_;
      this.Speed = p_i50346_2_;
   }


   protected boolean shouldExecute(ServerWorld worldIn, CreatureEntity owner) {
      Entity entity = owner.getBrain().getMemory(this.target).get();
      System.out.println(entity);
      return owner.getDistanceSq(entity) < 36.0D && entity.isAlive() && entity instanceof LivingEntity;
   }

   protected void startExecuting(ServerWorld worldIn, CreatureEntity entityIn, long gameTimeIn) {
      Entity entity = entityIn.getBrain().getMemory(this.target).get();
      setTarget(entityIn, (LivingEntity) entity, this.Speed);
   }

   public static void setTarget(CreatureEntity Dwarf, LivingEntity enemy, float p_220540_2_) {
      Dwarf.getBrain().setMemory(WarhammermodRegistry.ATTACK_TARGET,enemy);

   }
}
