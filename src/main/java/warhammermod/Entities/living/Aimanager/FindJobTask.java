package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.Data.DwarfProfession;
import warhammermod.Entities.living.DwarfEntity;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FindJobTask extends Task<DwarfEntity> {
   private final float field_234017_b_;

   public FindJobTask(float p_i231545_1_) {
      super(ImmutableMap.of(MemoryModuleType.field_234101_d_, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.MOBS, MemoryModuleStatus.VALUE_PRESENT));
      this.field_234017_b_ = p_i231545_1_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, DwarfEntity owner) {
      if (owner.isChild()) {
         return false;
      } else {
         return owner.getProfession() == DwarfProfession.Warrior;
      }
   }

   protected void startExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      BlockPos blockpos = entityIn.getBrain().getMemory(MemoryModuleType.field_234101_d_).get().getPos();
      Optional<PointOfInterestType> optional = worldIn.getPointOfInterestManager().getType(blockpos);
      if (optional.isPresent()) {
         this.func_233872_a_(entityIn, (p_234021_3_) -> {
            return this.func_234018_a_(optional.get(), p_234021_3_, blockpos);
         }).findFirst().ifPresent((p_234023_4_) -> {
            this.func_234022_a_(worldIn, entityIn, p_234023_4_, blockpos, p_234023_4_.getBrain().getMemory(MemoryModuleType.JOB_SITE).isPresent());
         });
      }
   }

   public Stream<DwarfEntity> func_233872_a_(DwarfEntity p_233872_0_, Predicate<DwarfEntity> p_233872_1_) {
      return p_233872_0_.getBrain().getMemory(MemoryModuleType.MOBS).map((p_233873_2_) -> {
         return p_233873_2_.stream().filter((p_233871_1_) -> {
            return p_233871_1_ instanceof DwarfEntity && p_233871_1_ != p_233872_0_;
         }).map((p_233859_0_) -> {
            return (DwarfEntity)p_233859_0_;
         }).filter(LivingEntity::isAlive).filter(p_233872_1_);
      }).orElseGet(Stream::empty);
   }

   private boolean func_234018_a_(PointOfInterestType p_234018_1_, DwarfEntity p_234018_2_, BlockPos p_234018_3_) {
      boolean flag = p_234018_2_.getBrain().getMemory(MemoryModuleType.field_234101_d_).isPresent();
      if (flag) {
         return false;
      } else {
         Optional<GlobalPos> optional = p_234018_2_.getBrain().getMemory(MemoryModuleType.JOB_SITE);
         DwarfProfession villagerprofession = p_234018_2_.getProfession();
         if (p_234018_2_.getProfession() != DwarfProfession.Warrior && villagerprofession.getPointOfInterest().getPredicate().test(p_234018_1_)) {
            return !optional.isPresent() ? this.func_234020_a_(p_234018_2_, p_234018_3_, p_234018_1_) : optional.get().getPos().equals(p_234018_3_);
         } else {
            return false;
         }
      }
   }

   private void func_234022_a_(ServerWorld p_234022_1_, DwarfEntity p_234022_2_, DwarfEntity p_234022_3_, BlockPos p_234022_4_, boolean p_234022_5_) {
      this.func_234019_a_(p_234022_2_);
      if (!p_234022_5_) {
         BrainUtil.func_233866_a_(p_234022_3_, p_234022_4_, this.field_234017_b_, 1);
         p_234022_3_.getBrain().setMemory(MemoryModuleType.field_234101_d_, GlobalPos.func_239648_a_(p_234022_1_.func_234923_W_(), p_234022_4_));
         DebugPacketSender.func_218801_c(p_234022_1_, p_234022_4_);
      }

   }

   private boolean func_234020_a_(DwarfEntity p_234020_1_, BlockPos p_234020_2_, PointOfInterestType p_234020_3_) {
      Path path = p_234020_1_.getNavigator().getPathToPos(p_234020_2_, p_234020_3_.getValidRange());
      return path != null && path.reachesTarget();
   }

   private void func_234019_a_(DwarfEntity p_234019_1_) {
      p_234019_1_.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
      p_234019_1_.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
      p_234019_1_.getBrain().removeMemory(MemoryModuleType.field_234101_d_);
   }
}