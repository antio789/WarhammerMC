package warhammermod.Entities.Living.Aimanager.DwarfTasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.AgeableEntity;;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import warhammermod.Entities.Living.DwarfEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;
import warhammermod.utils.inithandler.Entityinit;

import java.util.Optional;

public class WHCreateBabyVillagerTask extends Task<DwarfEntity> {
   private long birthTimestamp;

   public WHCreateBabyVillagerTask() {
      super(ImmutableMap.of(MemoryModuleType.BREED_TARGET, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.VISIBLE_LIVING_ENTITIES, MemoryModuleStatus.VALUE_PRESENT), 350, 350);
   }

   protected boolean checkExtraStartConditions(ServerWorld p_212832_1_, DwarfEntity p_212832_2_) {
      return this.isBreedingPossible(p_212832_2_);
   }

   protected boolean canStillUse(ServerWorld p_212834_1_, DwarfEntity p_212834_2_, long p_212834_3_) {
      return p_212834_3_ <= this.birthTimestamp && this.isBreedingPossible(p_212834_2_);
   }

   protected void start(ServerWorld p_212831_1_, DwarfEntity p_212831_2_, long p_212831_3_) {
      AgeableEntity ageableentity = p_212831_2_.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
      BrainUtil.lockGazeAndWalkToEachOther(p_212831_2_, ageableentity, 0.5F);
      p_212831_1_.broadcastEntityEvent(ageableentity, (byte)18);
      p_212831_1_.broadcastEntityEvent(p_212831_2_, (byte)18);
      int i = 275 + p_212831_2_.getRandom().nextInt(50);
      this.birthTimestamp = p_212831_3_ + (long)i;
   }

   protected void tick(ServerWorld p_212833_1_, DwarfEntity owner, long p_212833_3_) {
      DwarfEntity dwarfEntity = (DwarfEntity)owner.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
      if (!(owner.distanceToSqr(dwarfEntity) > 5.0D)) {
         BrainUtil.lockGazeAndWalkToEachOther(owner,dwarfEntity, 0.5F);
         if (p_212833_3_ >= this.birthTimestamp) {
            owner.eatAndDigestFood();
            dwarfEntity.eatAndDigestFood();
            this.tryToGiveBirth(p_212833_1_, owner, dwarfEntity);
         } else if (owner.getRandom().nextInt(35) == 0) {
            p_212833_1_.broadcastEntityEvent(dwarfEntity, (byte)12);
            p_212833_1_.broadcastEntityEvent(owner, (byte)12);
         }

      }
   }

   private void tryToGiveBirth(ServerWorld p_223521_1_, DwarfEntity p_223521_2_, DwarfEntity p_223521_3_) {
      Optional<BlockPos> optional = this.takeVacantBed(p_223521_1_, p_223521_2_);
      if (!optional.isPresent()) {
         p_223521_1_.broadcastEntityEvent(p_223521_3_, (byte)13);
         p_223521_1_.broadcastEntityEvent(p_223521_2_, (byte)13);
      } else {
         Optional<DwarfEntity> optional1 = this.breed(p_223521_1_, p_223521_2_, p_223521_3_);
         if (optional1.isPresent()) {
            this.giveBedToChild(p_223521_1_, optional1.get(), optional.get());
         } else {
            p_223521_1_.getPoiManager().release(optional.get());
            DebugPacketSender.sendPoiTicketCountPacket(p_223521_1_, optional.get());
         }
      }

   }

   protected void stop(ServerWorld p_212835_1_, DwarfEntity p_212835_2_, long p_212835_3_) {
      p_212835_2_.getBrain().eraseMemory(MemoryModuleType.BREED_TARGET);
   }

   private boolean isBreedingPossible(DwarfEntity p_220478_1_) {
      Brain<DwarfEntity> brain = p_220478_1_.getBrain();
      Optional<AgeableEntity> optional = brain.getMemory(MemoryModuleType.BREED_TARGET).filter((p_233999_0_) -> {
         return p_233999_0_.getType() == Entityinit.DWARF;
      });
      if (!optional.isPresent()) {
         return false;
      } else {
         return BrainUtil.targetIsValid(brain, MemoryModuleType.BREED_TARGET, Entityinit.DWARF) && p_220478_1_.canBreed() && optional.get().canBreed();
      }
   }

   private Optional<BlockPos> takeVacantBed(ServerWorld p_220479_1_, DwarfEntity p_220479_2_) {
      return p_220479_1_.getPoiManager().take(PointOfInterestType.HOME.getPredicate(), (p_220481_2_) -> {
         return this.canReach(p_220479_2_, p_220481_2_);
      }, p_220479_2_.blockPosition(), 48);
   }

   private boolean canReach(DwarfEntity p_223520_1_, BlockPos p_223520_2_) {
      Path path = p_223520_1_.getNavigation().createPath(p_223520_2_, PointOfInterestType.HOME.getValidRange());
      return path != null && path.canReach();
   }

   private Optional<DwarfEntity> breed(ServerWorld p_242307_1_, DwarfEntity p_242307_2_, DwarfEntity p_242307_3_) {
      DwarfEntity dwarfEntity = p_242307_2_.getBreedOffspring(p_242307_1_, p_242307_3_);
      if (dwarfEntity == null) {
         return Optional.empty();
      } else {
         p_242307_2_.setAge(8000);
         p_242307_3_.setAge(8000);
         dwarfEntity.setAge(-28000);
         dwarfEntity.moveTo(p_242307_2_.getX(), p_242307_2_.getY(), p_242307_2_.getZ(), 0.0F, 0.0F);
         p_242307_1_.addFreshEntityWithPassengers(dwarfEntity);
         p_242307_1_.broadcastEntityEvent(dwarfEntity, (byte)12);
         return Optional.of(dwarfEntity);
      }
   }

   private void giveBedToChild(ServerWorld p_220477_1_, DwarfEntity p_220477_2_, BlockPos p_220477_3_) {
      GlobalPos globalpos = GlobalPos.of(p_220477_1_.dimension(), p_220477_3_);
      p_220477_2_.getBrain().setMemory(MemoryModuleType.HOME, globalpos);
   }
}