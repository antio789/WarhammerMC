package warhammermod.Entities.Projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.utils.inithandler.Entityinit;

public class BulletEntity extends Projectilebase{

    public BulletEntity(World world){
        super(Entityinit.Bullet,world);
    }

    public BulletEntity(EntityType<? extends BulletEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }

    public BulletEntity(World worldin, LivingEntity shooter, float damageIn, EntityType<? extends Projectilebase> type) {
        super(worldin, shooter,damageIn, type);
    }
    public BulletEntity(World world, EntityType<? extends Projectilebase> entity, double x, double y, double z) {
        super( world,entity, x, y, z);
    }

    public BulletEntity(LivingEntity shooter, World world, float damage) {
        this(world, shooter, damage, Entityinit.Bullet);
    }

    public BulletEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(Entityinit.Bullet, world);
    }

    public void tick()
    {

        if (this.level.isClientSide() && !this.inGround)
        {
            this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
        super.tick();
    }



}
