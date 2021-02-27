package warhammermod.Entities.living.Emanager;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import warhammermod.Entities.living.EntityDwarf;

import java.util.List;

public class EntityAIDwarfMate extends EntityAIBase
{
    private final EntityDwarf villager;
    private EntityDwarf mate;
    private final World world;
    private int matingTimeout;
    Village village;

    public EntityAIDwarfMate(EntityDwarf villagerIn)
    {
        this.villager = villagerIn;
        this.world = villagerIn.world;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.villager.getGrowingAge() != 0)
        {

            return false;
        }
        else if (this.villager.getRNG().nextInt(550) != 0)
        {
            return false;
        }
        else
        {
            if (this.checkSufficientDoorsPresentForNewVillager() && this.villager.getIsWillingToMate(true))
            {
                Entity entity = this.world.findNearestEntityWithinAABB(EntityDwarf.class, this.villager.getEntityBoundingBox().grow(8.0D, 3.0D, 8.0D), this.villager);

                if (entity == null)
                {
                    return false;
                }
                else
                {
                    this.mate = (EntityDwarf)entity;
                    return this.mate.getGrowingAge() == 0 && this.mate.getIsWillingToMate(true);
                }
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.matingTimeout = 300;
        this.villager.setMating(true);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.village = null;
        this.mate = null;
        this.villager.setMating(false);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.matingTimeout >= 0 && this.villager.getGrowingAge() == 0 && this.villager.getIsWillingToMate(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        --this.matingTimeout;
        this.villager.getLookHelper().setLookPositionWithEntity(this.mate, 10.0F, 30.0F);

        if (this.villager.getDistanceSq(this.mate) > 2.25D)
        {
            this.villager.getNavigator().tryMoveToEntityLiving(this.mate, 0.25D);
        }
        else if (this.matingTimeout == 0 && this.mate.isMating())
        {
            this.giveBirth();
        }

        if (this.villager.getRNG().nextInt(35) == 0)
        {
            this.world.setEntityState(this.villager, (byte)12);
        }
    }

    private boolean checkSufficientDoorsPresentForNewVillager()
    {
            return CountDoorsAround(villager.getPos(),world)*0.35F > CountDwarfsAround(world);
    }

    private void giveBirth()
    {
        net.minecraft.entity.EntityAgeable entityvillager = this.villager.createChild(this.mate);
        this.mate.setGrowingAge(7500);
        this.villager.setGrowingAge(7500);
        this.mate.setIsWillingToMate(false);
        this.villager.setIsWillingToMate(false);

        final net.minecraftforge.event.entity.living.BabyEntitySpawnEvent event = new net.minecraftforge.event.entity.living.BabyEntitySpawnEvent(villager, mate, entityvillager);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event) || event.getChild() == null) { return; }
        entityvillager = event.getChild();
        entityvillager.setGrowingAge(-25000);
        entityvillager.setLocationAndAngles(this.villager.posX, this.villager.posY, this.villager.posZ, 0.0F, 0.0F);
        this.world.spawnEntity(entityvillager);
        this.world.setEntityState(entityvillager, (byte)12);
    }

    private boolean isWoodDoor(BlockPos doorPos,World world)
    {
        IBlockState iblockstate = world.getBlockState(doorPos);
        Block block = iblockstate.getBlock();

        if (block instanceof BlockDoor)
        {
            return iblockstate.getMaterial() == Material.WOOD;
        }
        else
        {
            return false;
        }
    }

    public float CountDoorsAround(BlockPos central,World world)
    {
        if (!world.isAreaLoaded(central, 16)) return 0; // Forge: prevent loading unloaded chunks when checking for doors
        float sum = 0;
        int i = 32;
        int j = 4;
        int k = 32;

        for (int l = -i; l < i; ++l)
        {
            for (int i1 = -j; i1 < j; i1+=2)
            {
                for (int j1 = -k; j1 < k; ++j1)
                {
                    BlockPos blockpos = central.add(l, i1-2, j1);

                    if (isWoodDoor(blockpos,world))
                    {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }
    public int CountDwarfsAround(World world){
        List<EntityDwarf> list = world.getEntitiesWithinAABB(EntityDwarf.class, this.villager.getEntityBoundingBox().grow(32.0D, 4.0D, 32.0D));
        return list.size();
    }
}