package warhammermod.Entities.Living.Aimanager.DwarfTasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.Living.DwarfEntity;
import warhammermod.utils.inithandler.WarhammermodRegistry;

public class TargetTask extends Task<CreatureEntity> {
   private final MemoryModuleType<? extends LivingEntity> target;


   public TargetTask(MemoryModuleType<? extends LivingEntity> p_i50346_1_) {
      super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleStatus.VALUE_ABSENT,p_i50346_1_, MemoryModuleStatus.VALUE_PRESENT));
      System.out.println("here1");
      this.target = p_i50346_1_;
   }



   public void start(ServerWorld p_212831_1_, CreatureEntity entityIn, long p_212831_3_) {
      System.out.println("here2");
      LivingEntity entity = entityIn.getBrain().getMemory(this.target).get();
      setTarget(entityIn,entity);
   }

   public static void setTarget(CreatureEntity Dwarf, LivingEntity enemy) {
      Dwarf.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET,enemy);
   }

   private static boolean isValidAttackTarget(CreatureEntity dwarf, LivingEntity target) {
      return isAttackAllowed(target) && getTargetIfWithinRange(dwarf,target);
   }

   private static boolean isAttackAllowed(LivingEntity p_242347_0_) {
      return EntityPredicates.ATTACK_ALLOWED.test(p_242347_0_);
   }

   private static boolean getTargetIfWithinRange(CreatureEntity p_242351_0_, LivingEntity p_242351_1_) {
         return p_242351_1_.closerThan(p_242351_0_, 12.0D);
   }

}
