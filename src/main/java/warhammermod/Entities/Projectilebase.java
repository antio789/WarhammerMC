package warhammermod.Entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import warhammermod.util.reference;

public class Projectilebase extends AbstractArrowEntity {


    public float projectiledamage;
    protected float extradamage;
    protected int knocklevel;


    public void setpowerDamage(float powerIn){
        extradamage=1.5F*powerIn;
    }
    public void setknockbacklevel(int knockin){
        knocklevel=knockin;
    }


    public Projectilebase(EntityType<? extends Projectilebase> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }




    public Projectilebase(World worldin, LivingEntity shooter, ItemStack stack, float damageIn, EntityType<? extends Projectilebase> type) {
        super(type, shooter, worldin);
        projectiledamage = damageIn;

    }


    public Projectilebase(World p_i48791_1_, EntityType<? extends Projectilebase> entity, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
        super(entity, p_i48791_2_, p_i48791_4_, p_i48791_6_, p_i48791_1_);
    }

    protected void onHit(RayTraceResult raytraceResultIn) {
        RayTraceResult.Type raytraceresult$type = raytraceResultIn.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            this.dealdamage((EntityRayTraceResult)raytraceResultIn);
            this.remove();
        } else {
            this.remove();
        }

    }
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void dealdamage(EntityRayTraceResult p_213868_1_){}

    protected ItemStack getArrowStack() {
        return null;
    }



    public boolean getIsCritical() {
        return false;
    }

    protected void registerData() {
        super.registerData();
    }

    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, name);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getRenderDistanceWeight();
        return distance < d0 * d0;
    }



}
