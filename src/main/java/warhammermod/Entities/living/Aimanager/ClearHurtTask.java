package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.PanicTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.DwarfEntity;
import warhammermod.util.Handler.WarhammermodRegistry;

public class ClearHurtTask extends Task<DwarfEntity> {
   public ClearHurtTask() {
      super(ImmutableMap.of());
   }

   protected void startExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      boolean flag = PanicTask.hasBeenHurt(entityIn) || PanicTask.hostileNearby(entityIn) || func_220394_a(entityIn);
      if (!flag) {
         entityIn.getBrain().removeMemory(MemoryModuleType.HURT_BY);
         entityIn.getBrain().removeMemory(MemoryModuleType.HURT_BY_ENTITY);
         entityIn.getBrain().updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
      } else if(!entityIn.getBrain().getMemory(WarhammermodRegistry.ATTACK_TARGET).isPresent()){

      }

   }

   private static boolean func_220394_a(DwarfEntity entity) {
      if( entity.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).filter((p_223523_1_) -> {
         return p_223523_1_.getDistanceSq(entity) <= 43.0D;
      }).isPresent()){
         entity.getBrain().setMemory(WarhammermodRegistry.ATTACK_TARGET,entity.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).get());
         return true;
      }else return false;
   }
}