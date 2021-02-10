package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.Data.DwarfProfession;
import warhammermod.Entities.living.DwarfEntity;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class SwitchVillagerJobTask extends Task<DwarfEntity> {
   final DwarfProfession field_233929_b_;

   public SwitchVillagerJobTask(DwarfProfession p_i231525_1_) {
      super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.MOBS, MemoryModuleStatus.VALUE_PRESENT));
      this.field_233929_b_ = p_i231525_1_;
   }

   protected void startExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      GlobalPos globalpos = entityIn.getBrain().getMemory(MemoryModuleType.JOB_SITE).get();
      worldIn.getPointOfInterestManager().getType(globalpos.getPos()).ifPresent((p_233933_3_) -> {
         this.func_233872_a_(entityIn, (p_233935_3_) -> {
            return this.func_233934_a_(globalpos, p_233933_3_, p_233935_3_);
         }).reduce(entityIn, SwitchVillagerJobTask::func_233932_a_);
      });
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

   private static DwarfEntity func_233932_a_(DwarfEntity p_233932_0_, DwarfEntity p_233932_1_) {
      DwarfEntity villagerentity;
      DwarfEntity villagerentity1;
      if (p_233932_0_.getXp() > p_233932_1_.getXp()) {
         villagerentity = p_233932_0_;
         villagerentity1 = p_233932_1_;
      } else {
         villagerentity = p_233932_1_;
         villagerentity1 = p_233932_0_;
      }

      villagerentity1.getBrain().removeMemory(MemoryModuleType.JOB_SITE);
      return villagerentity;
   }

   private boolean func_233934_a_(GlobalPos p_233934_1_, PointOfInterestType p_233934_2_, DwarfEntity p_233934_3_) {
      return this.func_233931_a_(p_233934_3_) && p_233934_1_.equals(p_233934_3_.getBrain().getMemory(MemoryModuleType.JOB_SITE).get()) && this.func_233930_a_(p_233934_2_, p_233934_3_.getProfession());
   }

   private boolean func_233930_a_(PointOfInterestType p_233930_1_, DwarfProfession p_233930_2_) {
      return p_233930_2_.getPointOfInterest().getPredicate().test(p_233930_1_);
   }

   private boolean func_233931_a_(DwarfEntity p_233931_1_) {
      return p_233931_1_.getBrain().getMemory(MemoryModuleType.JOB_SITE).isPresent();
   }
}