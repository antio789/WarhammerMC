package warhammermod.Entities.living.Emanager;

import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import warhammermod.Entities.living.EntityDwarf;

public class EntityAIDLookAtTradePlayer extends EntityAIWatchClosest
{
    private final EntityDwarf villager;

    public EntityAIDLookAtTradePlayer(EntityDwarf villagerIn)
    {
        super(villagerIn, EntityPlayer.class, 8.0F);
        this.villager = villagerIn;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.villager.isTrading())
        {
            this.closestEntity = this.villager.getCustomer();
            return true;
        }
        else
        {
            return false;
        }
    }
}