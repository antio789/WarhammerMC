package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.DwarfEntity;

import java.util.Optional;

public class FindPotentialJobTask extends Task<DwarfEntity> {
   final float field_233903_b_;

   public FindPotentialJobTask(float p_i231519_1_) {
      super(ImmutableMap.of(MemoryModuleType.field_234101_d_, MemoryModuleStatus.VALUE_PRESENT), 1200);
      this.field_233903_b_ = p_i231519_1_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, DwarfEntity owner) {
      return owner.getBrain().func_233716_f_().map((p_233904_0_) -> {
         return p_233904_0_ == Activity.IDLE || p_233904_0_ == Activity.WORK || p_233904_0_ == Activity.PLAY;
      }).orElse(true);
   }

   protected boolean shouldContinueExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      return entityIn.getBrain().hasMemory(MemoryModuleType.field_234101_d_);
   }

   protected void updateTask(ServerWorld worldIn, DwarfEntity owner, long gameTime) {
      BrainUtil.func_233866_a_(owner, owner.getBrain().getMemory(MemoryModuleType.field_234101_d_).get().getPos(), this.field_233903_b_, 1);
   }

   protected void resetTask(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      Optional<GlobalPos> optional = entityIn.getBrain().getMemory(MemoryModuleType.field_234101_d_);
      optional.ifPresent((p_233905_1_) -> {
         BlockPos blockpos = p_233905_1_.getPos();
         ServerWorld serverworld = worldIn.getServer().getWorld(p_233905_1_.func_239646_a_());
         if (serverworld != null) {
            PointOfInterestManager pointofinterestmanager = serverworld.getPointOfInterestManager();
            if (pointofinterestmanager.exists(blockpos, (p_241377_0_) -> {
               return true;
            })) {
               pointofinterestmanager.release(blockpos);
            }

            DebugPacketSender.func_218801_c(worldIn, blockpos);
         }
      });
      entityIn.getBrain().removeMemory(MemoryModuleType.field_234101_d_);
   }
}