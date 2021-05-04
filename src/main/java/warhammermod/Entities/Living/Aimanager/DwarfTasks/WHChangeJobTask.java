package warhammermod.Entities.Living.Aimanager.DwarfTasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import warhammermod.Entities.Living.Aimanager.Data.DwarfProfession;
import warhammermod.Entities.Living.DwarfEntity;
import net.minecraft.world.server.ServerWorld;

public class WHChangeJobTask extends Task<DwarfEntity> {
   public WHChangeJobTask() {
      super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_ABSENT));
   }

   protected boolean checkExtraStartConditions(ServerWorld p_212832_1_, DwarfEntity entity) {
      DwarfProfession prof = entity.getProfession();
      return prof != DwarfProfession.Warrior && prof != DwarfProfession.Lord && entity.getVillagerXp() == 0 && entity.getProfessionLevel() <= 1;
   }

   protected void start(ServerWorld p_212831_1_, DwarfEntity entity, long p_212831_3_) {
      entity.setVillagerProfession(DwarfProfession.Warrior);
      entity.refreshBrain(p_212831_1_);
   }
}