package warhammermod.Entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityGrenade extends EntityBullet {
    protected ItemStack getArrowStack() {
        return null;
    }

    private int knocklevel=0;

    public EntityGrenade(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn,0);
    }

    public EntityGrenade(World worldIn) {
        super(worldIn);
        this.setSize(0.3F, 0.3F);

    }

    public EntityGrenade(World worldIn, double x, double y, double z) {
        this(worldIn);
        this.setPosition(x, y, z);
    }
    public void setknockbacklevel(int knockin) {
        knocklevel = knockin;
    }

    protected void onHit(RayTraceResult raytraceResultIn) {
        if(!world.isRemote){
        world.createExplosion(null,raytraceResultIn.hitVec.x,raytraceResultIn.hitVec.y,raytraceResultIn.hitVec.z,2+knocklevel,true);}
        this.setDead();
    }
}



