package warhammermod.Entities.Projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.utils.inithandler.Entityinit;

public class ShotEntity extends Projectilebase {
    public BlockPos Playerpos;
    public ShotEntity(World world) {
        super(Entityinit.ShotEntity, world);
    }

    public ShotEntity(EntityType<? extends ShotEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }

    public ShotEntity(World worldin, LivingEntity shooter, float damageIn, EntityType<? extends Projectilebase> type) {
        super(worldin, shooter, damageIn, type);
    }

    public ShotEntity(World world, EntityType<? extends Projectilebase> entity, double x, double y, double z) {
        super(world, entity, x, y, z);
    }

    public ShotEntity(LivingEntity shooter, World world, float damage, BlockPos blockPos) {
        this(world, shooter, damage, Entityinit.ShotEntity);
        Playerpos = blockPos;
    }

    public ShotEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(Entityinit.ShotEntity, world);
    }

    protected void onHitEntity(EntityRayTraceResult entityhit) {
        double distance = Math.sqrt(distanceToSqr(Playerpos.getX(),Playerpos.getY(),Playerpos.getZ()));
        if (distance>23){
            this.remove();
        }else {
            modifydamage(distance);
            super.onHitEntity(entityhit);
        }
    }

    public double damagemodifier(double X){
        return Math.min( -0.0476*X+1.21,1);
    }

    public void modifydamage(double distance){
        double modifier = damagemodifier(distance);
        projectiledamage*=modifier;
        extradamage*=modifier;
    }
}
