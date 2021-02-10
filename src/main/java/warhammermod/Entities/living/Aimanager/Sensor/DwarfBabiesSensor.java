package warhammermod.Entities.living.Aimanager.Sensor;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.world.server.ServerWorld;
import warhammermod.util.Handler.Entityinit;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DwarfBabiesSensor extends Sensor<LivingEntity> {
   public Set<MemoryModuleType<?>> getUsedMemories() {
      return ImmutableSet.of(MemoryModuleType.VISIBLE_VILLAGER_BABIES);
   }

   protected void update(ServerWorld p_212872_1_, LivingEntity p_212872_2_) {
      p_212872_2_.getBrain().setMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES, this.func_220994_a(p_212872_2_));
   }

   private List<LivingEntity> func_220994_a(LivingEntity p_220994_1_) {
      return this.func_220992_c(p_220994_1_).stream().filter(this::func_220993_b).collect(Collectors.toList());
   }

   private boolean func_220993_b(LivingEntity p_220993_1_) {
      return p_220993_1_.getType() == Entityinit.DWARF && p_220993_1_.isChild();
   }

   private List<LivingEntity> func_220992_c(LivingEntity p_220992_1_) {
      return p_220992_1_.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).orElse(Lists.newArrayList());
   }
}