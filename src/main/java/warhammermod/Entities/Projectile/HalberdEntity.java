package warhammermod.Entities.Projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.utils.inithandler.Entityinit;

public class HalberdEntity extends Projectilebase{
    int fuse=20;
    public HalberdEntity(EntityType<? extends HalberdEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }
    public HalberdEntity(World worldin, LivingEntity shooter, float damageIn, EntityType<? extends Projectilebase> type) {
        super(worldin, shooter,damageIn, type);
    }
    public HalberdEntity(World world, EntityType<? extends Projectilebase> entity, double x, double y, double z) {
        super( world,entity, x, y, z);
    }
    public HalberdEntity(LivingEntity shooter, World world, float damage) {
        this(world, shooter, damage, Entityinit.halberdentity);
        System.out.println("created");
    }
    public HalberdEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(Entityinit.halberdentity, world);
    }
/*
    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderAtSqrDistance(double p_70112_1_) { return false; }
*/
    public void tick()
    {
        super.tick();
        fuse--;
        if(fuse==18){
            this.remove();
        }
    }
}
