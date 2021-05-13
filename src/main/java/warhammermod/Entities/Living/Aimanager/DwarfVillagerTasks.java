package warhammermod.Entities.Living.Aimanager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.village.PointOfInterestType;
import warhammermod.Entities.Living.Aimanager.Data.DwarfProfession;
import warhammermod.Entities.Living.Aimanager.DwarfTasks.*;
import warhammermod.Entities.Living.DwarfEntity;
import warhammermod.utils.inithandler.Entityinit;

import java.util.Optional;


public class DwarfVillagerTasks {
   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> getCorePackage(DwarfProfession profession, float p_220638_1_) {
      return ImmutableList.of(Pair.of(0, new SwimTask(0.8F)), Pair.of(0, new InteractWithDoorTask()), Pair.of(0, new LookTask(45, 90)), Pair.of(0, new WHPanicTask()), Pair.of(0, new WakeUpTask()), Pair.of(0, new HideFromRaidOnBellRingTask()), Pair.of(0, new BeginRaidTask()), Pair.of(0, new ExpirePOITask(profession.getPointOfInterest(), MemoryModuleType.JOB_SITE)), Pair.of(0, new ExpirePOITask(profession.getPointOfInterest(), MemoryModuleType.POTENTIAL_JOB_SITE)), Pair.of(1, new WalkToTargetTask()), Pair.of(2, new WHSwitchVillagerJobTask(profession)), Pair.of(3, new WHTradeTask(p_220638_1_)), Pair.of(5, new PickupWantedItemTask(p_220638_1_, false, 4)), Pair.of(6, new GatherPOITask(profession.getPointOfInterest(), MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, true, Optional.empty())), Pair.of(7, new WHFindPotentialJobTask(p_220638_1_)), Pair.of(8, new WHFindJobTask(p_220638_1_)), Pair.of(10, new GatherPOITask(PointOfInterestType.HOME, MemoryModuleType.HOME, false, Optional.of((byte)14))), Pair.of(10, new GatherPOITask(PointOfInterestType.MEETING, MemoryModuleType.MEETING_POINT, true, Optional.of((byte)14))), Pair.of(10, new WHAssignProfessionTask()), Pair.of(10, new WHChangeJobTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> getWorkPackage(DwarfProfession p_220639_0_, float p_220639_1_) {
      WHSpawnGolemTask spawngolemtask;
      if (p_220639_0_ == DwarfProfession.FARMER) {
         spawngolemtask = new WHFarmerWorkTask();
      } else {
         spawngolemtask = new WHSpawnGolemTask();
      }

      return ImmutableList.of(getMinimalLookBehavior(), Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(spawngolemtask, 7), Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 0.4F, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 0.4F, 1, 10), 5), Pair.of(new WHWalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, p_220639_1_, 1, 6, MemoryModuleType.JOB_SITE), 5), Pair.of(new WHFarmTask(), p_220639_0_ == DwarfProfession.FARMER ? 2 : 5), Pair.of(new WHBoneMealCropsTask(), p_220639_0_ == DwarfProfession.FARMER ? 4 : 7)))), Pair.of(10, new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)), Pair.of(2, new WHStayNearPointTask(MemoryModuleType.JOB_SITE, p_220639_1_, 9, 100, 1200)), Pair.of(3, new WHGiveHeroGiftsTask(100)), Pair.of(99, new UpdateActivityTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> getPlayPackage(float p_220645_0_) {
      return ImmutableList.of(Pair.of(0, new WalkToTargetTask(80, 120)), getFullLookBehavior(), Pair.of(5, new WalkToVillagerBabiesTask()), Pair.of(5, new FirstShuffledTask<>(ImmutableMap.of(MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(InteractWithEntityTask.of(Entityinit.DWARF, 8, MemoryModuleType.INTERACTION_TARGET, p_220645_0_, 2), 2), Pair.of(InteractWithEntityTask.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, p_220645_0_, 2), 1), Pair.of(new FindWalkTargetTask(p_220645_0_), 1), Pair.of(new WalkTowardsLookTargetTask(p_220645_0_, 2), 1), Pair.of(new JumpOnBedTask(p_220645_0_), 2), Pair.of(new DummyTask(20, 40), 2)))), Pair.of(99, new UpdateActivityTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> getRestPackage(DwarfProfession p_220635_0_, float p_220635_1_) {
      return ImmutableList.of(Pair.of(2, new WHStayNearPointTask(MemoryModuleType.HOME, p_220635_1_, 1, 150, 1200)), Pair.of(3, new ExpirePOITask(PointOfInterestType.HOME, MemoryModuleType.HOME)), Pair.of(3, new SleepAtHomeTask()), Pair.of(5, new FirstShuffledTask<>(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(new WalkToHouseTask(p_220635_1_), 1), Pair.of(new WalkRandomlyInsideTask(p_220635_1_), 4), Pair.of(new WHWalkToPOITask(p_220635_1_, 4), 2), Pair.of(new DummyTask(20, 40), 2)))), getMinimalLookBehavior(), Pair.of(99, new UpdateActivityTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> getMeetPackage(DwarfProfession p_220637_0_, float p_220637_1_) {
      return ImmutableList.of(Pair.of(2, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.MEETING_POINT,0.4F, 40), 2), Pair.of(new CongregateTask(), 2)))), Pair.of(10, new FindInteractionAndLookTargetTask(Entityinit.DWARF, 4)), Pair.of(2, new WHStayNearPointTask(MemoryModuleType.MEETING_POINT, p_220637_1_, 6, 100, 200)), Pair.of(3, new WHGiveHeroGiftsTask(100)), Pair.of(3, new ExpirePOITask(PointOfInterestType.MEETING, MemoryModuleType.MEETING_POINT)), Pair.of(3, new WHShareItemsTask()), getFullLookBehavior(), Pair.of(99, new UpdateActivityTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> getIdlePackage(DwarfProfession p_220641_0_, float p_220641_1_) {
      return ImmutableList.of(Pair.of(2, new FirstShuffledTask<>(ImmutableList.of(Pair.of(InteractWithEntityTask.of(Entityinit.DWARF, 8, MemoryModuleType.INTERACTION_TARGET, p_220641_1_, 2), 2), Pair.of(new InteractWithEntityTask<>(Entityinit.DWARF, 8, AgeableEntity::canBreed, AgeableEntity::canBreed, MemoryModuleType.BREED_TARGET, p_220641_1_, 2), 1), Pair.of(InteractWithEntityTask.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, p_220641_1_, 2), 1), Pair.of(new FindWalkTargetTask(p_220641_1_), 1), Pair.of(new WalkTowardsLookTargetTask(p_220641_1_, 2), 1), Pair.of(new JumpOnBedTask(p_220641_1_), 1), Pair.of(new DummyTask(30, 60), 1)))), Pair.of(3, new WHGiveHeroGiftsTask(100)), Pair.of(3, new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)), Pair.of(3, new WHShareItemsTask()), Pair.of(3,new WHCreateBabyVillagerTask()), getFullLookBehavior(), Pair.of(99, new UpdateActivityTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> getPanicPackage(DwarfProfession p_220636_0_, float p_220636_1_) {
      float f = p_220636_1_ * 1.5F;
      return ImmutableList.of(Pair.of(0, new WHClearHurtTask()), Pair.of(1, RunAwayTask.entity(MemoryModuleType.NEAREST_HOSTILE, f, 6, false)), Pair.of(1, RunAwayTask.entity(MemoryModuleType.HURT_BY_ENTITY, f, 6, false)), Pair.of(3, new FindWalkTargetTask(f, 2, 2)), getMinimalLookBehavior());
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> getPreRaidPackage(DwarfProfession p_220642_0_, float p_220642_1_) {
      return ImmutableList.of(Pair.of(0, new RingBellTask()), Pair.of(0, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WHStayNearPointTask(MemoryModuleType.MEETING_POINT, p_220642_1_ * 1.5F, 2, 150, 200), 6), Pair.of(new FindWalkTargetTask(p_220642_1_ * 1.5F), 2)))), getMinimalLookBehavior(), Pair.of(99, new ForgetRaidTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> getRaidPackage(DwarfProfession p_220640_0_, float p_220640_1_) {
      return ImmutableList.of(Pair.of(0, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new GoOutsideAfterRaidTask(p_220640_1_), 5), Pair.of(new FindWalkTargetAfterRaidVictoryTask(p_220640_1_ * 1.1F), 2)))), Pair.of(0, new WHCelebrateRaidVictoryTask(600, 600)), Pair.of(2, new FindHidingPlaceDuringRaidTask(24, p_220640_1_ * 1.4F)), getMinimalLookBehavior(), Pair.of(99, new ForgetRaidTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> getHidePackage(DwarfProfession p_220644_0_, float p_220644_1_) {
      int i = 2;
      return ImmutableList.of(Pair.of(0, new ExpireHidingTask(15, 3)), Pair.of(1, new FindHidingPlaceTask(32, p_220644_1_ * 1.25F, 2)), getMinimalLookBehavior());
   }

   private static Pair<Integer, Task<LivingEntity>> getFullLookBehavior() {
      return Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new LookAtEntityTask(EntityType.CAT, 8.0F), 8), Pair.of(new LookAtEntityTask(Entityinit.DWARF, 8.0F), 2), Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0F), 2), Pair.of(new LookAtEntityTask(EntityClassification.CREATURE, 8.0F), 1), Pair.of(new LookAtEntityTask(EntityClassification.WATER_CREATURE, 8.0F), 1), Pair.of(new LookAtEntityTask(EntityClassification.WATER_AMBIENT, 8.0F), 1), Pair.of(new LookAtEntityTask(EntityClassification.MONSTER, 8.0F), 1), Pair.of(new DummyTask(30, 60), 2))));
   }

   public static Pair<Integer, Task<LivingEntity>> getMinimalLookBehavior() {
      return Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new LookAtEntityTask(Entityinit.DWARF, 8.0F), 2), Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0F), 2), Pair.of(new DummyTask(30, 60), 8))));
   }
}