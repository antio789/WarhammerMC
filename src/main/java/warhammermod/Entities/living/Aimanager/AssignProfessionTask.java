package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.Data.DwarfProfession;
import warhammermod.Entities.living.DwarfEntity;

public class AssignProfessionTask extends Task<DwarfEntity> {
   public AssignProfessionTask() {
      super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT));
   }

   protected boolean shouldExecute(ServerWorld worldIn, DwarfEntity owner) {
      return owner.getProfession() == DwarfProfession.Warrior;
   }

   protected void startExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      GlobalPos globalpos = entityIn.getBrain().getMemory(MemoryModuleType.JOB_SITE).get();
      MinecraftServer minecraftserver = worldIn.getServer();
      minecraftserver.getWorld(globalpos.getDimension()).getPointOfInterestManager().getType(globalpos.getPos()).ifPresent((p_220390_2_) -> {
         for(DwarfProfession prof:DwarfProfession.Profession){
            if(prof.getPointOfInterest().equals(p_220390_2_)){
               entityIn.setVillagerProfession(prof);
               entityIn.resetBrain(worldIn);
            }
         }
      });
   }
}