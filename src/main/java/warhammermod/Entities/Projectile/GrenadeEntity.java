package warhammermod.Entities.Projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.utils.inithandler.Entityinit;

public class GrenadeEntity extends Projectilebase{



    public GrenadeEntity(EntityType<? extends GrenadeEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }

    public GrenadeEntity(World worldin, LivingEntity shooter, float damageIn, EntityType<? extends Projectilebase> type) {
        super(worldin, shooter,damageIn, type);
    }
    public GrenadeEntity(World world, EntityType<? extends Projectilebase> entity, double x, double y, double z) {
        super( world,entity, x, y, z);
    }

    public GrenadeEntity(LivingEntity shooter, World world) {
        this(world, shooter, 0, Entityinit.Grenade);
    }

    public GrenadeEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(Entityinit.Grenade, world);
    }


    protected void onHit(RayTraceResult p_70227_1_) {
        if (!this.level.isClientSide()) {
            this.level.explode(null, p_70227_1_.getLocation().x, p_70227_1_.getLocation().y, p_70227_1_.getLocation().z, 2 + knocklevel, Explosion.Mode.BREAK);
            this.remove();

        }
    }
        protected void onHitBlock(BlockRayTraceResult p_230299_1_) {}
        protected void onHitEntity(EntityRayTraceResult p_213868_1_){}



}
