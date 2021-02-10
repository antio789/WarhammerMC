package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.DwarfEntity;

public class PanicTask extends Task<DwarfEntity> {
   public PanicTask() {
      super(ImmutableMap.of());
   }

   protected boolean shouldContinueExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      return hasBeenHurt(entityIn) || hostileNearby(entityIn);
   }

   protected void startExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      if (hasBeenHurt(entityIn) || hostileNearby(entityIn)) {
         Brain<?> brain = entityIn.getBrain();
         if (!brain.hasActivity(Activity.PANIC)) {
            brain.removeMemory(MemoryModuleType.PATH);
            brain.removeMemory(MemoryModuleType.WALK_TARGET);
            brain.removeMemory(MemoryModuleType.LOOK_TARGET);
            brain.removeMemory(MemoryModuleType.BREED_TARGET);
            brain.removeMemory(MemoryModuleType.INTERACTION_TARGET);
         }

         brain.switchTo(Activity.PANIC);
      }

   }


   public static boolean hostileNearby(LivingEntity p_220513_0_) {
      return p_220513_0_.getBrain().hasMemory(MemoryModuleType.NEAREST_HOSTILE);
   }

   public static boolean hasBeenHurt(LivingEntity p_220512_0_) {
      return p_220512_0_.getBrain().hasMemory(MemoryModuleType.HURT_BY);
   }
}