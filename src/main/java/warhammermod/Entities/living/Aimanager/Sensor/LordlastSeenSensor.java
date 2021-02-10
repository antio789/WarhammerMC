package warhammermod.Entities.living.Aimanager.Sensor;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.Data.DwarfProfession;
import warhammermod.Entities.living.DwarfEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LordlastSeenSensor extends Sensor<LivingEntity> {
   public LordlastSeenSensor() {
      this(200);
   }

   public LordlastSeenSensor(int p_i51525_1_) {
      super(p_i51525_1_);
   }

   protected void update(ServerWorld p_212872_1_, LivingEntity p_212872_2_) {
      func_223545_a(p_212872_1_.getGameTime(), p_212872_2_);
   }

   public Set<MemoryModuleType<?>> getUsedMemories() {
      return ImmutableSet.of(MemoryModuleType.MOBS);
   }

   public static void func_223545_a(long p_223545_0_, LivingEntity p_223545_2_) {
      Brain<?> brain = p_223545_2_.getBrain();
      Optional<List<LivingEntity>> optional = brain.getMemory(MemoryModuleType.MOBS);
      if (optional.isPresent()) {
         boolean flag = optional.get().stream().anyMatch((p_223546_0_) -> {
            return p_223546_0_ instanceof DwarfEntity && ((DwarfEntity) p_223546_0_).getProfession().equals(DwarfProfession.Lord);
         });
         if (flag) {
            brain.setMemory(MemoryModuleType.GOLEM_LAST_SEEN_TIME, p_223545_0_);
         }

      }
   }
}