package warhammermod.Entities.Projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.utils.inithandler.Entityinit;

public class ShotEntity extends Projectilebase {
    public BlockPos Playerpos;
    private int timer;
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
        if(modifydamage(timer)) {
            super.onHitEntity(entityhit);
        }else this.remove();

    }

    public void tick()
    {
        super.tick();
        timer++;
    }

    public boolean modifydamage(double distance){
        double modifier = damagemodifier(distance);
        projectiledamage*=modifier;
        extradamage*=modifier;
        return !(modifier==0);
    }

    public double damagemodifier(double X){
        if(X==0)X=1;
        return Math.max( -0.3*X+1.3,0);
    }
}
