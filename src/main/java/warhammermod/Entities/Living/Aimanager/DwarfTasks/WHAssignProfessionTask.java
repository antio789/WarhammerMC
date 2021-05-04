package warhammermod.Entities.Living.Aimanager.DwarfTasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.village.PointOfInterestType;
import warhammermod.Entities.Living.Aimanager.Data.DwarfProfession;
import warhammermod.Entities.Living.DwarfEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;

public class WHAssignProfessionTask extends Task<DwarfEntity> {
   public WHAssignProfessionTask() {
      super(ImmutableMap.of(MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleStatus.VALUE_PRESENT));
   }

   protected boolean checkExtraStartConditions(ServerWorld p_212832_1_, DwarfEntity p_212832_2_) {
      BlockPos blockpos = p_212832_2_.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get().pos();
      return blockpos.closerThan(p_212832_2_.position(), 2.0D) || p_212832_2_.assignProfessionWhenSpawned();
   }

   protected void start(ServerWorld world, DwarfEntity entity, long p_212831_3_) {
      GlobalPos globalpos = entity.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get();
      entity.getBrain().eraseMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
      entity.getBrain().setMemory(MemoryModuleType.JOB_SITE, globalpos);
      world.broadcastEntityEvent(entity, (byte)14);
      if (entity.getProfession() == DwarfProfession.Warrior) {
         MinecraftServer minecraftserver = world.getServer();
         Optional<PointOfInterestType> POI =  minecraftserver.getLevel(globalpos.dimension()).getPoiManager().getType(globalpos.pos());
         if(POI.isPresent()){
            for(DwarfProfession prof:DwarfProfession.Profession){
               if(prof.getPointOfInterest().equals(POI.get())){
                  entity.setVillagerProfession(prof);
                  entity.refreshBrain(world);
               }
            }
         }
      }
   }
}