package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.village.PointOfInterestType;
import warhammermod.Entities.living.Data.DwarfProfession;
import warhammermod.Entities.living.DwarfEntity;
import warhammermod.util.Handler.Entityinit;

public class DwarfVillagerTasks {
   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> core(DwarfProfession profession, float p_220638_1_) {
      return ImmutableList.of(Pair.of(0, new SwimTask(0.8F)), Pair.of(0, new InteractWithDoorTask()), Pair.of(0, new LookTask(45, 90)), Pair.of(0, new PanicTask()), Pair.of(0, new WakeUpTask()), Pair.of(0, new HideFromRaidOnBellRingTask()), Pair.of(0, new BeginRaidTask()), Pair.of(0, new ExpirePOITask(profession.getPointOfInterest(), MemoryModuleType.JOB_SITE)), Pair.of(0, new ExpirePOITask(profession.getPointOfInterest(), MemoryModuleType.field_234101_d_)), Pair.of(1, new WalkToTargetTask(200)),Pair.of(1,new AttackTaskredowithminecraftoneifpossible(200)), Pair.of(2, new SwitchVillagerJobTask(profession)), Pair.of(3, new TradeTask(p_220638_1_)), Pair.of(5, new PickupWantedItemTask(p_220638_1_, false, 4)), Pair.of(6, new GatherPOITask(profession.getPointOfInterest(), MemoryModuleType.JOB_SITE, MemoryModuleType.field_234101_d_, true)), Pair.of(7, new FindPotentialJobTask(p_220638_1_)), Pair.of(8, new FindJobTask(p_220638_1_)), Pair.of(10, new GatherPOITask(PointOfInterestType.HOME, MemoryModuleType.HOME, false)), Pair.of(10, new GatherPOITask(PointOfInterestType.MEETING, MemoryModuleType.MEETING_POINT, true)), Pair.of(10, new AssignProfessionTask()), Pair.of(10, new ChangeJobTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> work(DwarfProfession profession, float p_220639_1_) {
      SpawnGolemTask spawngolemtask;
      if (profession == DwarfProfession.Farmer) {
         spawngolemtask = new FarmerWorkTask();
      } else {
         spawngolemtask = new SpawnGolemTask();
      }

      return ImmutableList.of(lookAtPlayerOrVillager(), Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(spawngolemtask, 7), Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 1, 10), 5), Pair.of(new WalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, p_220639_1_, 1, 6, MemoryModuleType.JOB_SITE), 5), Pair.of(new FarmTask(), profession == DwarfProfession.Farmer ? 2 : 5), Pair.of(new BoneMealCropsTask(), profession == DwarfProfession.Farmer ? 4 : 7)))), Pair.of(10, new FindInteractionAndLookTargetTask(Entityinit.DWARF, 4)), Pair.of(2, new StayNearPointTask(MemoryModuleType.JOB_SITE, p_220639_1_, 9, 100, 1200)), Pair.of(3, new GiveHeroGiftsTask(100)), Pair.of(99, new UpdateActivityTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> play(float walkingSpeed) {
      return ImmutableList.of(Pair.of(0, new WalkToTargetTask(100)), lookAtMany(), Pair.of(5, new WalkToVillagerBabiesTask()), Pair.of(5, new FirstShuffledTask<>(ImmutableMap.of(MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(InteractWithEntityTask.func_220445_a(Entityinit.DWARF, 8, MemoryModuleType.INTERACTION_TARGET, walkingSpeed, 2), 2), Pair.of(InteractWithEntityTask.func_220445_a(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, walkingSpeed, 2), 1), Pair.of(new FindWalkTargetTask(walkingSpeed), 1), Pair.of(new WalkTowardsLookTargetTask(walkingSpeed, 2), 1), Pair.of(new JumpOnBedTask(walkingSpeed), 2), Pair.of(new DummyTask(20, 40), 2)))), Pair.of(99, new UpdateActivityTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> rest(DwarfProfession profession, float walkingSpeed) {
      return ImmutableList.of(Pair.of(2, new StayNearPointTask(MemoryModuleType.HOME, walkingSpeed, 1, 150, 1200)), Pair.of(3, new ExpirePOITask(PointOfInterestType.HOME, MemoryModuleType.HOME)), Pair.of(3, new SleepAtHomeTask()), Pair.of(5, new FirstShuffledTask<>(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(new WalkToHouseTask(walkingSpeed), 1), Pair.of(new WalkRandomlyInsideTask(walkingSpeed), 4), Pair.of(new WalkToPOITask(walkingSpeed, 4), 2), Pair.of(new DummyTask(20, 40), 2)))), lookAtPlayerOrVillager(), Pair.of(99, new UpdateActivityTask()));
   }


      public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> meet(DwarfProfession profession, float p_220637_1_) {
      return ImmutableList.of(Pair.of(2, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.MEETING_POINT, 40), 2), Pair.of(new CongregateTask(), 2)))), Pair.of(10, new FindInteractionAndLookTargetTask(Entityinit.DWARF, 4)), Pair.of(2, new StayNearPointTask(MemoryModuleType.MEETING_POINT, p_220637_1_, 6, 100, 200)), Pair.of(3, new GiveHeroGiftsTask(100)), Pair.of(3, new ExpirePOITask(PointOfInterestType.MEETING, MemoryModuleType.MEETING_POINT)), Pair.of(3, new ShareItemsTask()), lookAtMany(), Pair.of(99, new UpdateActivityTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> idle(DwarfProfession profession, float p_220641_1_) {
      return ImmutableList.of(Pair.of(2, new FirstShuffledTask<>(ImmutableList.of(Pair.of(InteractWithEntityTask.func_220445_a(Entityinit.DWARF, 8, MemoryModuleType.INTERACTION_TARGET, p_220641_1_, 2), 2), Pair.of(new InteractWithEntityTask<>(Entityinit.DWARF, 8, AgeableEntity::canBreed, AgeableEntity::canBreed, MemoryModuleType.BREED_TARGET, p_220641_1_, 2), 1), Pair.of(InteractWithEntityTask.func_220445_a(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, p_220641_1_, 2), 1), Pair.of(new FindWalkTargetTask(p_220641_1_), 1), Pair.of(new WalkTowardsLookTargetTask(p_220641_1_, 2), 1), Pair.of(new JumpOnBedTask(p_220641_1_), 1), Pair.of(new DummyTask(30, 60), 1)))), Pair.of(3, new GiveHeroGiftsTask(100)), Pair.of(3, new FindInteractionAndLookTargetTask(Entityinit.DWARF, 4)), Pair.of(3,  new ShareItemsTask()), Pair.of(3, new CreateBabyVillagerTask()), lookAtMany(), Pair.of(99, new UpdateActivityTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> fight(DwarfProfession profession, float p_220636_1_) {
      float f = p_220636_1_ * 1.25F;
      return ImmutableList.of(Pair.of(0, new ClearHurtTask()), Pair.of(1, new TargetTask(MemoryModuleType.NEAREST_HOSTILE,f)), Pair.of(1, new TargetTask(MemoryModuleType.HURT_BY_ENTITY,f)), Pair.of(3, new FindWalkTargetTask(f, 2, 2)), lookAtPlayerOrVillager());
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> panic(DwarfProfession profession, float p_220636_1_) {
      float f = p_220636_1_ * 1.25F;
      return ImmutableList.of(Pair.of(0, new ClearHurtTask()), Pair.of(1, RunAwayTask.func_233965_b_(MemoryModuleType.NEAREST_HOSTILE, f, 6, false)), Pair.of(1, RunAwayTask.func_233965_b_(MemoryModuleType.HURT_BY_ENTITY, f, 6, false)), Pair.of(3, new FindWalkTargetTask(f, 2, 2)), lookAtPlayerOrVillager());
   }


    //p_234330_0_.func_233699_a_(Activity.field_234621_k_, 10, ImmutableList.<net.minecraft.entity.ai.brain.task.Task<? super ZoglinEntity>>of(new MoveToTargetTask(1.0F), new SupplementedTask<>(ZoglinEntity::func_234331_eI_, new AttackTargetTask(40)), new SupplementedTask<>(ZoglinEntity::isChild, new AttackTargetTask(15)), new FindNewAttackTargetTask()), MemoryModuleType.field_234103_o_);


   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> preRaid(DwarfProfession profession, float p_220642_1_) {
      return ImmutableList.of(Pair.of(0, new RingBellTask()), Pair.of(0, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new StayNearPointTask(MemoryModuleType.MEETING_POINT, p_220642_1_ * 1.5F, 2, 150, 200), 6), Pair.of(new FindWalkTargetTask(p_220642_1_ * 1.5F), 2)))), lookAtPlayerOrVillager(), Pair.of(99, new ForgetRaidTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> raid(DwarfProfession profession, float p_220640_1_) {
      return ImmutableList.of(Pair.of(0, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new GoOutsideAfterRaidTask(p_220640_1_), 5), Pair.of(new FindWalkTargetAfterRaidVictoryTask(p_220640_1_ * 1.1F), 2)))), Pair.of(0, new CelebrateRaidVictoryTask(600, 600)), Pair.of(2, new FindHidingPlaceDuringRaidTask(24, p_220640_1_ * 1.4F)), lookAtPlayerOrVillager(), Pair.of(99, new ForgetRaidTask()));
   }

   public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> hide(DwarfProfession profession, float p_220644_1_) {
      int i = 2;
      return ImmutableList.of(Pair.of(0, new ExpireHidingTask(15, 3)), Pair.of(1, new FindHidingPlaceTask(32, p_220644_1_ * 1.25F, 2)), lookAtPlayerOrVillager());
   }

   private static Pair<Integer, Task<LivingEntity>> lookAtMany() {
      return Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new LookAtEntityTask(EntityType.CAT, 8.0F), 8), Pair.of(new LookAtEntityTask(Entityinit.DWARF, 8.0F), 2), Pair.of(new LookAtEntityTask(Entityinit.DWARF, 8.0F), 2), Pair.of(new LookAtEntityTask(EntityClassification.CREATURE, 8.0F), 1), Pair.of(new LookAtEntityTask(EntityClassification.WATER_CREATURE, 8.0F), 1), Pair.of(new LookAtEntityTask(EntityClassification.WATER_AMBIENT, 8.0F), 1), Pair.of(new LookAtEntityTask(EntityClassification.MONSTER, 8.0F), 1), Pair.of(new DummyTask(30, 60), 2))));
   }

   private static Pair<Integer, Task<LivingEntity>> lookAtPlayerOrVillager() {
      return Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new LookAtEntityTask(Entityinit.DWARF, 8.0F), 2), Pair.of(new LookAtEntityTask(Entityinit.DWARF, 8.0F), 2), Pair.of(new DummyTask(30, 60), 8))));
   }
}