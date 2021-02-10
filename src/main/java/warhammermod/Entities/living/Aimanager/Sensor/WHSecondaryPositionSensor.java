package warhammermod.Entities.living.Aimanager.Sensor;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.DwarfEntity;

import java.util.List;
import java.util.Set;

public class WHSecondaryPositionSensor extends Sensor<DwarfEntity> {
   public WHSecondaryPositionSensor() {
      super(40);
   }

   protected void update(ServerWorld p_212872_1_, DwarfEntity dwarf) {
      DimensionType dimensiontype = p_212872_1_.getDimension().getType();
      BlockPos blockpos = new BlockPos(dwarf);
      List<GlobalPos> list = Lists.newArrayList();
      int i = 4;

      for(int j = -4; j <= 4; ++j) {
         for(int k = -2; k <= 2; ++k) {
            for(int l = -4; l <= 4; ++l) {
               BlockPos blockpos1 = blockpos.add(j, k, l);
               if (dwarf.getProfession().getFarmblock().contains(p_212872_1_.getBlockState(blockpos1).getBlock())) {
                  list.add(GlobalPos.of(dimensiontype, blockpos1));
               }
            }
         }
      }

      Brain<?> brain = dwarf.getBrain();
      if (!list.isEmpty()) {
         brain.setMemory(MemoryModuleType.SECONDARY_JOB_SITE, list);
      } else {
         brain.removeMemory(MemoryModuleType.SECONDARY_JOB_SITE);
      }

   }

   public Set<MemoryModuleType<?>> getUsedMemories() {
      return ImmutableSet.of(MemoryModuleType.SECONDARY_JOB_SITE);
   }
}