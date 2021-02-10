package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.Data.DwarfProfession;
import warhammermod.Entities.living.DwarfEntity;

import java.util.Optional;

public class AssignProfessionTask extends Task<DwarfEntity> {
   public AssignProfessionTask() {
      super(ImmutableMap.of(MemoryModuleType.field_234101_d_, MemoryModuleStatus.VALUE_PRESENT));
   }

   protected boolean shouldExecute(ServerWorld worldIn, DwarfEntity owner) {
      BlockPos blockpos = owner.getBrain().getMemory(MemoryModuleType.field_234101_d_).get().getPos();
      return blockpos.withinDistance(owner.getPositionVec(), 2.0D) || owner.func_234552_eW_();
   }

   protected void startExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      GlobalPos globalpos = entityIn.getBrain().getMemory(MemoryModuleType.field_234101_d_).get();
      entityIn.getBrain().removeMemory(MemoryModuleType.field_234101_d_);
      entityIn.getBrain().setMemory(MemoryModuleType.JOB_SITE, globalpos);
      if (entityIn.getProfession() == DwarfProfession.Warrior) {
         MinecraftServer minecraftserver = worldIn.getServer();
         Optional<PointOfInterestType> POI =  minecraftserver.getWorld(globalpos.func_239646_a_()).getPointOfInterestManager().getType(globalpos.getPos());
         if(POI.isPresent()){
            for(DwarfProfession prof:DwarfProfession.Profession){
               if(prof.getPointOfInterest().equals(POI.get())){
                  entityIn.setVillagerProfession(prof);
                  entityIn.resetBrain(worldIn);
               }
            }
         }

         }
      }
}


