package warhammermod.Entities.Living.Aimanager.DwarfTasks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import warhammermod.Entities.Living.DwarfEntity;
import warhammermod.Entities.Living.Aimanager.Data.DwarfProfession;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import warhammermod.utils.functions;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WHGiveHeroGiftsTask extends Task<DwarfEntity> {
   private static ResourceLocation Lordloot = functions.location("gameplay/hero_of_the_village/lord_gift");
   private static final Map<DwarfProfession, ResourceLocation> gifts = Util.make(Maps.newHashMap(), (p_220395_0_) -> {
      p_220395_0_.put(DwarfProfession.Miner, LootTables.CLERIC_GIFT);
      p_220395_0_.put(DwarfProfession.FARMER, LootTables.FARMER_GIFT);
      p_220395_0_.put(DwarfProfession.Slayer, LootTables.BUTCHER_GIFT);
      p_220395_0_.put(DwarfProfession.Engineer, LootTables.ARMORER_GIFT);
      p_220395_0_.put(DwarfProfession.Builder, LootTables.MASON_GIFT);
      p_220395_0_.put(DwarfProfession.Lord,Lordloot);
      p_220395_0_.put(DwarfProfession.Warrior, LootTables.FARMER_GIFT);
   });
   private int timeUntilNextGift = 600;
   private boolean giftGivenDuringThisRun;
   private long timeSinceStart;

   public WHGiveHeroGiftsTask(int p_i50366_1_) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.INTERACTION_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleStatus.VALUE_PRESENT), p_i50366_1_);
   }

   protected boolean checkExtraStartConditions(ServerWorld p_212832_1_, DwarfEntity p_212832_2_) {
      if (!this.isHeroVisible(p_212832_2_)) {
         return false;
      } else if (this.timeUntilNextGift > 0) {
         --this.timeUntilNextGift;
         return false;
      } else {
         return true;
      }
   }

   protected void start(ServerWorld p_212831_1_, DwarfEntity p_212831_2_, long p_212831_3_) {
      this.giftGivenDuringThisRun = false;
      this.timeSinceStart = p_212831_3_;
      PlayerEntity playerentity = this.getNearestTargetableHero(p_212831_2_).get();
      p_212831_2_.getBrain().setMemory(MemoryModuleType.INTERACTION_TARGET, playerentity);
      BrainUtil.lookAtEntity(p_212831_2_, playerentity);
   }

   protected boolean canStillUse(ServerWorld p_212834_1_, DwarfEntity p_212834_2_, long p_212834_3_) {
      return this.isHeroVisible(p_212834_2_) && !this.giftGivenDuringThisRun;
   }

   protected void tick(ServerWorld p_212833_1_, DwarfEntity p_212833_2_, long p_212833_3_) {
      PlayerEntity playerentity = this.getNearestTargetableHero(p_212833_2_).get();
      BrainUtil.lookAtEntity(p_212833_2_, playerentity);
      if (this.isWithinThrowingDistance(p_212833_2_, playerentity)) {
         if (p_212833_3_ - this.timeSinceStart > 20L) {
            this.throwGift(p_212833_2_, playerentity);
            this.giftGivenDuringThisRun = true;
         }
      } else {
         BrainUtil.setWalkAndLookTargetMemories(p_212833_2_, playerentity, 0.5F, 5);
      }

   }

   protected void stop(ServerWorld p_212835_1_, DwarfEntity p_212835_2_, long p_212835_3_) {
      this.timeUntilNextGift = calculateTimeUntilNextGift(p_212835_1_);
      p_212835_2_.getBrain().eraseMemory(MemoryModuleType.INTERACTION_TARGET);
      p_212835_2_.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      p_212835_2_.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
   }

   private void throwGift(DwarfEntity p_220398_1_, LivingEntity p_220398_2_) {
      for(ItemStack itemstack : this.getItemToThrow(p_220398_1_)) {
         BrainUtil.throwItem(p_220398_1_, itemstack, p_220398_2_.position());
      }

   }

   private List<ItemStack> getItemToThrow(DwarfEntity p_220399_1_) {
      if (p_220399_1_.isBaby()) {
         return ImmutableList.of(new ItemStack(Items.POPPY));
      } else {
         DwarfProfession villagerprofession = p_220399_1_.getProfession();
         if (gifts.containsKey(villagerprofession)) {
            LootTable loottable = p_220399_1_.level.getServer().getLootTables().get(gifts.get(villagerprofession));
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)p_220399_1_.level)).withParameter(LootParameters.ORIGIN, p_220399_1_.position()).withParameter(LootParameters.THIS_ENTITY, p_220399_1_).withRandom(p_220399_1_.getRandom());
            return loottable.getRandomItems(lootcontext$builder.create(LootParameterSets.GIFT));
         } else {
            return ImmutableList.of(new ItemStack(Items.WHEAT_SEEDS));
         }
      }
   }

   private boolean isHeroVisible(DwarfEntity p_220396_1_) {
      return this.getNearestTargetableHero(p_220396_1_).isPresent();
   }

   private Optional<PlayerEntity> getNearestTargetableHero(DwarfEntity p_220400_1_) {
      return p_220400_1_.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).filter(this::isHero);
   }

   private boolean isHero(PlayerEntity p_220402_1_) {
      return p_220402_1_.hasEffect(Effects.HERO_OF_THE_VILLAGE);
   }

   private boolean isWithinThrowingDistance(DwarfEntity p_220401_1_, PlayerEntity p_220401_2_) {
      BlockPos blockpos = p_220401_2_.blockPosition();
      BlockPos blockpos1 = p_220401_1_.blockPosition();
      return blockpos1.closerThan(blockpos, 5.0D);
   }

   private static int calculateTimeUntilNextGift(ServerWorld p_220397_0_) {
      return 600 + p_220397_0_.random.nextInt(6001);
   }
}