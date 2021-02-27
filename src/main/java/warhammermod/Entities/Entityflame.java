package warhammermod.Entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.world.World;

public class Entityflame extends EntitySmallFireball {
    private int limitedlifespan;
    public Entityflame(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, shooter, accelX, accelY, accelZ);
        limitedlifespan=30;
    }

    public Entityflame(World worldIn)
    {
        super(worldIn);
    }
    public void onEntityUpdate()
    {

        --limitedlifespan;
        if(limitedlifespan<=0){setDead();}
        super.onEntityUpdate();
    }

    public Entityflame(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
    {
        super(worldIn, x, y, z, accelX, accelY, accelZ);

    }

}
