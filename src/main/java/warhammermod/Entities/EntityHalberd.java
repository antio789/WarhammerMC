package warhammermod.Entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHalberd extends EntityBullet
{
    private float damage;
    private int fuse;
    private EntityPlayer entityplayer;
    private boolean Throwon;

    public EntityHalberd(World worldIn)
    {
        super(worldIn);

    }

    public EntityHalberd(World worldIn, EntityLivingBase throwerIn,float AttackDamage,boolean throwon) {
        super(worldIn, throwerIn, (int) AttackDamage);
        damage=AttackDamage;
        entityplayer = (EntityPlayer) throwerIn;
        Throwon=throwon;
        fuse=2;
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {

    }

    public EntityHalberd(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public static void registerFixesSnowball(DataFixer fixer)
    {
        EntityThrowable.registerFixesThrowable(fixer, "Snowball");
    }

    private float extradamage;
    private int knocklevel;

    public void setpowerDamage(int powerIn){
        extradamage=1.4F*powerIn;
    }
    public void setknockbacklevel(int knockin){
        knocklevel=knockin;
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return false;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onHit(RayTraceResult result)
    {

        if (result.entityHit != null){
            if( !world.isRemote) {

                damage += extradamage;
                if (result.entityHit instanceof EntityLivingBase && getDistance(entityplayer)*2<7) {
                    EntityLivingBase entitylivingbase = (EntityLivingBase) result.entityHit;
                    if (knocklevel > 0) {
                        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                        if (f1 > 0.0F) {
                            entitylivingbase.addVelocity(this.motionX * (double) knocklevel * 0.6000000238418579D / (double) f1, 0.1D, this.motionZ * (double) knocklevel * 0.6000000238418579D / (double) f1);
                        }
                    }
                    if (result.entityHit != entityplayer){
                        if(Throwon){ result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this,entityplayer), damage);}
                        else result.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(entityplayer), damage);
                    }

                }
            }
            if(world.isRemote){
                for (int k = 0; k < 10; ++k)
                this.world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);}
        }
        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
    public void onEntityUpdate()
    {
        this.world.profiler.startSection("entityBaseTick");

        if (this.isRiding() && this.getRidingEntity().isDead)
        {
            this.dismountRidingEntity();
        }

        if (this.rideCooldown > 0)
        {
            --this.rideCooldown;
        }

        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;

        if (!this.world.isRemote && this.world instanceof WorldServer)
        {

        }

        this.spawnRunningParticles();
        this.handleWaterMovement();

        if (this.world.isRemote)
        {
            this.extinguish();
        }


        if (this.isInLava())
        {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5F;
        }

        if (this.posY < -64.0D)
        {
            this.outOfWorld();
        }


        this.firstUpdate = false;
        this.world.profiler.endSection();

        if(!this.world.isRemote){
        --fuse;
        if(fuse<=0){setDead();}}



    }
}
