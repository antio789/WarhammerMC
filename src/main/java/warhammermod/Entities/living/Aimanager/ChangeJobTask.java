package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.Data.DwarfProfession;
import warhammermod.Entities.living.DwarfEntity;

public class ChangeJobTask extends Task<DwarfEntity> {
   public ChangeJobTask() {
      super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_ABSENT));
   }

   protected boolean shouldExecute(ServerWorld worldIn, DwarfEntity owner) {
      DwarfProfession prof = owner.getProfession();
      return prof != DwarfProfession.Warrior && prof != DwarfProfession.Lord && owner.getXp() == 0 && owner.getLevel() <= 1;
   }

   protected void startExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      entityIn.setVillagerProfession(DwarfProfession.Warrior);
      entityIn.resetBrain(worldIn);
   }
}