package warhammermod.Entities.Living.Aimanager.DwarfTasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import warhammermod.Entities.Living.DwarfEntity;
import warhammermod.Entities.Living.Aimanager.Data.DwarfProfession;
import warhammermod.Entities.Living.DwarfEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.Living.DwarfEntity;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class WHFindJobTask extends Task<DwarfEntity> {
   private final float speedModifier;

   public WHFindJobTask(float p_i231545_1_) {
      super(ImmutableMap.of(MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.LIVING_ENTITIES, MemoryModuleStatus.VALUE_PRESENT));
      this.speedModifier = p_i231545_1_;
   }

   protected boolean checkExtraStartConditions(ServerWorld p_212832_1_, DwarfEntity p_212832_2_) {
      if (p_212832_2_.isBaby()) {
         return false;
      } else {
         return p_212832_2_.getProfession() == DwarfProfession.Warrior;
      }
   }

   protected void start(ServerWorld p_212831_1_, DwarfEntity p_212831_2_, long p_212831_3_) {
      BlockPos blockpos = p_212831_2_.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get().pos();
      Optional<PointOfInterestType> optional = p_212831_1_.getPoiManager().getType(blockpos);
      if (optional.isPresent()) {
         this.getNearbyVillagersWithCondition(p_212831_2_, (p_234021_3_) -> {
            return this.nearbyWantsJobsite(optional.get(), p_234021_3_, blockpos);
         }).findFirst().ifPresent((p_234023_4_) -> {
            this.yieldJobSite(p_212831_1_, p_212831_2_, p_234023_4_, blockpos, p_234023_4_.getBrain().getMemory(MemoryModuleType.JOB_SITE).isPresent());
         });
      }
   }

   public static Stream<DwarfEntity> getNearbyVillagersWithCondition(DwarfEntity p_233872_0_, Predicate<DwarfEntity> p_233872_1_) {
      return p_233872_0_.getBrain().getMemory(MemoryModuleType.LIVING_ENTITIES).map((p_233873_2_) -> {
         return p_233873_2_.stream().filter((p_233871_1_) -> {
            return p_233871_1_ instanceof DwarfEntity && p_233871_1_ != p_233872_0_;
         }).map((p_233859_0_) -> {
            return (DwarfEntity)p_233859_0_;
         }).filter(LivingEntity::isAlive).filter(p_233872_1_);
      }).orElseGet(Stream::empty);
   }

   private boolean nearbyWantsJobsite(PointOfInterestType p_234018_1_, DwarfEntity entity, BlockPos p_234018_3_) {
      boolean flag = entity.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).isPresent();
      if (flag) {
         return false;
      } else {
         Optional<GlobalPos> optional = entity.getBrain().getMemory(MemoryModuleType.JOB_SITE);
         DwarfProfession profession = entity.getProfession();
         if (entity.getProfession() != DwarfProfession.Warrior && profession.getPointOfInterest().getPredicate().test(p_234018_1_)) {
            return !optional.isPresent() ? this.canReachPos(entity, p_234018_3_, p_234018_1_) : optional.get().pos().equals(p_234018_3_);
         } else {
            return false;
         }
      }
   }

   private void yieldJobSite(ServerWorld p_234022_1_, DwarfEntity p_234022_2_, DwarfEntity p_234022_3_, BlockPos p_234022_4_, boolean p_234022_5_) {
      this.eraseMemories(p_234022_2_);
      if (!p_234022_5_) {
         BrainUtil.setWalkAndLookTargetMemories(p_234022_3_, p_234022_4_, this.speedModifier, 1);
         p_234022_3_.getBrain().setMemory(MemoryModuleType.POTENTIAL_JOB_SITE, GlobalPos.of(p_234022_1_.dimension(), p_234022_4_));
         DebugPacketSender.sendPoiTicketCountPacket(p_234022_1_, p_234022_4_);
      }

   }

   private boolean canReachPos(DwarfEntity p_234020_1_, BlockPos p_234020_2_, PointOfInterestType p_234020_3_) {
      Path path = p_234020_1_.getNavigation().createPath(p_234020_2_, p_234020_3_.getValidRange());
      return path != null && path.canReach();
   }

   private void eraseMemories(DwarfEntity p_234019_1_) {
      p_234019_1_.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      p_234019_1_.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
      p_234019_1_.getBrain().eraseMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
   }
}