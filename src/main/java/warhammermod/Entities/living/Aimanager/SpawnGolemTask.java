package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.DwarfEntity;

import java.util.Optional;

public class SpawnGolemTask extends Task<DwarfEntity> {
   private long field_225461_a;

   public SpawnGolemTask() {
      super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED));
   }

   protected boolean shouldExecute(ServerWorld worldIn, DwarfEntity owner) {
      if (worldIn.getGameTime() - this.field_225461_a < 300L) {
         return false;
      } else if (worldIn.rand.nextInt(2) != 0) {
         return false;
      } else {
         this.field_225461_a = worldIn.getGameTime();
         GlobalPos globalpos = owner.getBrain().getMemory(MemoryModuleType.JOB_SITE).get();
         return globalpos.func_239646_a_() == worldIn.func_234923_W_() && globalpos.getPos().withinDistance(owner.getPositionVec(), 1.73D);
      }
   }

   protected void startExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      Brain<DwarfEntity> brain = entityIn.getBrain();
      brain.setMemory(MemoryModuleType.LAST_WORKED_AT_POI, gameTimeIn);
      brain.getMemory(MemoryModuleType.JOB_SITE).ifPresent((p_225460_1_) -> {
         brain.setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(p_225460_1_.getPos()));
      });
      entityIn.playWorkstationSound();
      this.func_230251_a_(worldIn, entityIn);
      if (entityIn.func_223721_ek()) {
         entityIn.func_213766_ei();
      }

   }

   protected void func_230251_a_(ServerWorld p_230251_1_, DwarfEntity p_230251_2_) {
   }

   protected boolean shouldContinueExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      Optional<GlobalPos> optional = entityIn.getBrain().getMemory(MemoryModuleType.JOB_SITE);
      if (!optional.isPresent()) {
         return false;
      } else {
         GlobalPos globalpos = optional.get();
         return globalpos.func_239646_a_() == worldIn.func_234923_W_() && globalpos.getPos().withinDistance(entityIn.getPositionVec(), 1.73D);
      }
   }
}