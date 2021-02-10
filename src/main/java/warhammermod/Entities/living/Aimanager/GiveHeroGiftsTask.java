package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import warhammermod.Entities.living.Data.DwarfProfession;
import warhammermod.Entities.living.DwarfEntity;
import warhammermod.util.utils.utils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GiveHeroGiftsTask extends Task<DwarfEntity> {
   private static ResourceLocation Lordloot = utils.location("gameplay/hero_of_the_village/lord_gift");



   private static final Map<DwarfProfession, ResourceLocation> GIFTS = Util.make(Maps.newHashMap(), (p_220395_0_) -> {
      p_220395_0_.put(DwarfProfession.Miner, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_CLERIC_GIFT);
      p_220395_0_.put(DwarfProfession.Farmer, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_FARMER_GIFT);
      p_220395_0_.put(DwarfProfession.Slayer, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_BUTCHER_GIFT);
      p_220395_0_.put(DwarfProfession.Engineer, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_ARMORER_GIFT);
      p_220395_0_.put(DwarfProfession.Builder, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_MASON_GIFT);
      p_220395_0_.put(DwarfProfession.Lord,Lordloot);
      p_220395_0_.put(DwarfProfession.Warrior, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_FARMER_GIFT);

   });
   private int cooldown = 600;
   private boolean done;
   private long startTime;

   public GiveHeroGiftsTask(int p_i50366_1_) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.INTERACTION_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleStatus.VALUE_PRESENT), p_i50366_1_);
   }

   protected boolean shouldExecute(ServerWorld worldIn, DwarfEntity owner) {
      if (!this.hasNearestPlayer(owner)) {
         return false;
      } else if (this.cooldown > 0) {
         --this.cooldown;
         return false;
      } else {
         return true;
      }
   }

   protected void startExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      this.done = false;
      this.startTime = gameTimeIn;
      PlayerEntity playerentity = this.getNearestPlayer(entityIn).get();
      entityIn.getBrain().setMemory(MemoryModuleType.INTERACTION_TARGET, playerentity);
      BrainUtil.lookAt(entityIn, playerentity);
   }

   protected boolean shouldContinueExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      return this.hasNearestPlayer(entityIn) && !this.done;
   }

   protected void updateTask(ServerWorld worldIn, DwarfEntity owner, long gameTime) {
      PlayerEntity playerentity = this.getNearestPlayer(owner).get();
      BrainUtil.lookAt(owner, playerentity);
      if (this.isCloseEnough(owner, playerentity)) {
         if (gameTime - this.startTime > 20L) {
            this.giveGifts(owner, playerentity);
            this.done = true;
         }
      } else {
         BrainUtil.func_233860_a_(owner, playerentity, 0.5F, 5);
      }

   }

   protected void resetTask(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      this.cooldown = getNextCooldown(worldIn);
      entityIn.getBrain().removeMemory(MemoryModuleType.INTERACTION_TARGET);
      entityIn.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
      entityIn.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
   }

   private void giveGifts(DwarfEntity p_220398_1_, LivingEntity p_220398_2_) {
      for(ItemStack itemstack : this.getGifts(p_220398_1_)) {
         BrainUtil.func_233865_a_(p_220398_1_, itemstack, p_220398_2_.getPositionVec());
      }

   }

   private List<ItemStack> getGifts(DwarfEntity p_220399_1_) {
      if (p_220399_1_.isChild()) {
         return ImmutableList.of(new ItemStack(Items.POPPY));
      } else {
         DwarfProfession profession = p_220399_1_.getProfession();
         if (GIFTS.containsKey(profession)) {
            LootTable loottable = p_220399_1_.world.getServer().getLootTableManager().getLootTableFromLocation(GIFTS.get(profession));
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)p_220399_1_.world)).withParameter(LootParameters.POSITION, p_220399_1_.func_233580_cy_()).withParameter(LootParameters.THIS_ENTITY, p_220399_1_).withRandom(p_220399_1_.getRNG());
            return loottable.generate(lootcontext$builder.build(LootParameterSets.GIFT));
         } else {
            return ImmutableList.of(new ItemStack(Items.WHEAT_SEEDS));
         }
      }
   }

   private boolean hasNearestPlayer(DwarfEntity p_220396_1_) {
      return this.getNearestPlayer(p_220396_1_).isPresent();
   }

   private Optional<PlayerEntity> getNearestPlayer(DwarfEntity p_220400_1_) {
      return p_220400_1_.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).filter(this::isHero);
   }

   private boolean isHero(PlayerEntity p_220402_1_) {
      return p_220402_1_.isPotionActive(Effects.HERO_OF_THE_VILLAGE);
   }

   private boolean isCloseEnough(DwarfEntity p_220401_1_, PlayerEntity p_220401_2_) {
      BlockPos blockpos = p_220401_2_.func_233580_cy_();
      BlockPos blockpos1 = p_220401_1_.func_233580_cy_();
      return blockpos1.withinDistance(blockpos, 5.0D);
   }

   private static int getNextCooldown(ServerWorld p_220397_0_) {
      return 600 + p_220397_0_.rand.nextInt(6001);
   }
}