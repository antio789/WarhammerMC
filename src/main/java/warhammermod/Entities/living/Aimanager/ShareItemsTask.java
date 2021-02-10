package warhammermod.Entities.living.Aimanager;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import warhammermod.Entities.living.Data.DwarfProfession;
import warhammermod.Entities.living.DwarfEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.server.ServerWorld;
import warhammermod.util.Handler.Entityinit;

import java.util.Set;
import java.util.stream.Collectors;

public class ShareItemsTask extends Task<DwarfEntity> {
   private Set<Item> field_220588_a = ImmutableSet.of();

   public ShareItemsTask() {
      super(ImmutableMap.of(MemoryModuleType.INTERACTION_TARGET, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.VALUE_PRESENT));
   }

   protected boolean shouldExecute(ServerWorld worldIn, DwarfEntity owner) {
      return BrainUtil.isCorrectVisibleType(owner.getBrain(), MemoryModuleType.INTERACTION_TARGET, Entityinit.DWARF);
   }

   protected boolean shouldContinueExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      return this.shouldExecute(worldIn, entityIn);
   }

   protected void startExecuting(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      DwarfEntity dwarf = (DwarfEntity)entityIn.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
      BrainUtil.lookApproachEachOther(entityIn, dwarf);
      this.field_220588_a = func_220585_a(entityIn, dwarf);
   }

   protected void updateTask(ServerWorld worldIn, DwarfEntity owner, long gameTime) {
      DwarfEntity dwarf = (DwarfEntity)owner.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
      if (!(owner.getDistanceSq(dwarf) > 5.0D)) {
         BrainUtil.lookApproachEachOther(owner, dwarf);
         owner.func_213746_a(dwarf, gameTime);
         if (owner.canAbondonItems() && (owner.getProfession() == DwarfProfession.Farmer || dwarf.wantsMoreFood())) {
            func_220586_a(owner, DwarfEntity.foodsource.keySet(), dwarf);
         }

         if (!this.field_220588_a.isEmpty() && owner.getVillagerInventory().hasAny(this.field_220588_a)) {
            func_220586_a(owner, this.field_220588_a, dwarf);
         }

      }
   }

   protected void resetTask(ServerWorld worldIn, DwarfEntity entityIn, long gameTimeIn) {
      entityIn.getBrain().removeMemory(MemoryModuleType.INTERACTION_TARGET);
   }

   private static Set<Item> func_220585_a(DwarfEntity p_220585_0_, DwarfEntity p_220585_1_) {
      ImmutableSet<Item> immutableset = p_220585_1_.getProfession().getShareitem();
      ImmutableSet<Item> immutableset1 = p_220585_0_.getProfession().getShareitem();
      return immutableset.stream().filter((p_220587_1_) -> {
         return !immutableset1.contains(p_220587_1_);
      }).collect(Collectors.toSet());
   }

   private static void func_220586_a(DwarfEntity p_220586_0_, Set<Item> p_220586_1_, LivingEntity p_220586_2_) {
      Inventory inventory = p_220586_0_.getVillagerInventory();
      ItemStack itemstack = ItemStack.EMPTY;
      int i = 0;

      while(i < inventory.getSizeInventory()) {
         ItemStack itemstack1;
         Item item;
         int j;
         label28: {
            itemstack1 = inventory.getStackInSlot(i);
            if (!itemstack1.isEmpty()) {
               item = itemstack1.getItem();
               if (p_220586_1_.contains(item)) {
                  if (itemstack1.getCount() > itemstack1.getMaxStackSize() / 2) {
                     j = itemstack1.getCount() / 2;
                     break label28;
                  }

                  if (itemstack1.getCount() > 24) {
                     j = itemstack1.getCount() - 24;
                     break label28;
                  }
               }
            }

            ++i;
            continue;
         }

         itemstack1.shrink(j);
         itemstack = new ItemStack(item, j);
         break;
      }

      if (!itemstack.isEmpty()) {
         BrainUtil.throwItemAt(p_220586_0_, itemstack, p_220586_2_);
      }

   }
}