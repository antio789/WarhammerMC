package warhammermod.Entities.Living.Aimanager;


import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.util.EntityPredicates;
import warhammermod.Entities.Living.Aimanager.Data.DwarfProfession;
import warhammermod.Entities.Living.Aimanager.DwarfTasks.TargetTask;
import warhammermod.Entities.Living.Aimanager.DwarfTasks.WHAttackTargetTask;
import warhammermod.Entities.Living.Aimanager.DwarfTasks.WHClearHurtTask;
import warhammermod.Entities.Living.DwarfEntity;

import java.util.List;
import java.util.Optional;

import static warhammermod.Entities.Living.Aimanager.DwarfVillagerTasks.getMinimalLookBehavior;

public class DwarfCombattasks {
    public static void updateActivity(DwarfEntity p_234486_0_) {
        Brain<DwarfEntity> brain = p_234486_0_.getBrain();
        p_234486_0_.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }


    public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> getAttackPackage(DwarfProfession p_220636_0_, float p_220636_1_) {
        float f = p_220636_1_ * 1.5F;
        return ImmutableList.of(Pair.of(0, new WHClearHurtTask()), Pair.of(1, new TargetTask(MemoryModuleType.NEAREST_HOSTILE)),Pair.of(1, new TargetTask(MemoryModuleType.HURT_BY_ENTITY)),Pair.of(3,new MoveToTargetTask(1.0F)),Pair.of(2,new AttackTargetTask(20)), getMinimalLookBehavior());
    }
    public static ImmutableList<Pair<Integer, ? extends Task<? super DwarfEntity>>> getPanicPackage(DwarfProfession p_220636_0_, float p_220636_1_) {
        float f = p_220636_1_ * 1.5F;
        return ImmutableList.of(Pair.of(0, new WHClearHurtTask()),Pair.of(1, new TargetTask(MemoryModuleType.NEAREST_HOSTILE)),Pair.of(1, new TargetTask(MemoryModuleType.HURT_BY_ENTITY)), Pair.of(1, RunAwayTask.entity(MemoryModuleType.NEAREST_HOSTILE, f, 6, false)), Pair.of(1, RunAwayTask.entity(MemoryModuleType.HURT_BY_ENTITY, f, 6, false)), Pair.of(3, new FindWalkTargetTask(f, 2, 2)), getMinimalLookBehavior());
    }




// ,Pair.of(1, new TargetTask(MemoryModuleType.NEAREST_HOSTILE)), Pair.of(3, new MoveToTargetTask(f)), Pair.of(3, new AttackTargetTask(20))




}
