package warhammermod.Entities.Living.Aimanager.brain.sensor;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.Living.Aimanager.Data.DwarfProfession;
import warhammermod.Entities.Living.DwarfEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LordLastSeenSensor extends Sensor<LivingEntity> {


   public LordLastSeenSensor() {
      super(200);
   }

   protected void doTick(ServerWorld p_212872_1_, LivingEntity p_212872_2_) {
      checkForNearbyGolem(p_212872_2_);
   }

   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(MemoryModuleType.LIVING_ENTITIES);
   }

   public static void checkForNearbyGolem(LivingEntity entity) {
      Optional<List<LivingEntity>> optional = entity.getBrain().getMemory(MemoryModuleType.LIVING_ENTITIES);
      if (optional.isPresent()) {
         boolean flag = optional.get().stream().anyMatch((livingEntity) -> {
            return livingEntity instanceof DwarfEntity && ((DwarfEntity) livingEntity).getProfession().equals(DwarfProfession.Lord);
         });
         if (flag) {
            golemDetected(entity);
         }

      }
   }

   public static void golemDetected(LivingEntity p_242313_0_) {
      p_242313_0_.getBrain().setMemoryWithExpiry(MemoryModuleType.GOLEM_DETECTED_RECENTLY, true, 600L);
   }
}