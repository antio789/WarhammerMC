package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.DwarfEntity;

public class DwarfTradeTask extends Task<DwarfEntity> {
   private final float field_220476_a;

   public DwarfTradeTask(float p_i50359_1_) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED), Integer.MAX_VALUE);
      this.field_220476_a = p_i50359_1_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, DwarfEntity owner) {
      PlayerEntity playerentity = owner.getCustomer();
      return owner.isAlive() && playerentity != null && !owner.isInWater() && !owner.velocityChanged && owner.getDistanceSq(playerentity) <= 16.0D && playerentity.openContainer != null;
   }

   protected boolean shouldContinueExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      return this.shouldExecute(worldIn, entityIn);
   }

   protected void startExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      this.func_220475_a(entityIn);
   }

   protected void resetTask(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      Brain<?> brain = entityIn.getBrain();
      brain.removeMemory(MemoryModuleType.WALK_TARGET);
      brain.removeMemory(MemoryModuleType.LOOK_TARGET);
   }

   protected void updateTask(ServerWorld worldIn, DwarfEntity owner, long gameTime) {
      this.func_220475_a(owner);
   }

   protected boolean isTimedOut(long gameTime) {
      return false;
   }

   private void func_220475_a(DwarfEntity p_220475_1_) {
      EntityPosWrapper entityposwrapper = new EntityPosWrapper(p_220475_1_.getCustomer());
      Brain<?> brain = p_220475_1_.getBrain();
      brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(entityposwrapper, this.field_220476_a, 2));
      brain.setMemory(MemoryModuleType.LOOK_TARGET, entityposwrapper);
   }
}