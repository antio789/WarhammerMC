package warhammermod.Entities.Projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.utils.inithandler.Entityinit;

public class SpearEntity extends Projectilebase{
    private ItemStack throwed_spear;
    public SpearEntity(EntityType<? extends SpearEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }
    public SpearEntity(World worldin, LivingEntity shooter, float damageIn, EntityType<? extends Projectilebase> type) {
        super(worldin, shooter,damageIn, type);
    }
    public SpearEntity(World world, EntityType<? extends Projectilebase> entity, double x, double y, double z) {
        super( world,entity, x, y, z);
    }
    public SpearEntity(LivingEntity shooter, World world, float damage,ItemStack stack) {
        this(world, shooter, damage, Entityinit.Spear);
        throwed_spear =stack;

    }
    public SpearEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(Entityinit.Spear, world);
    }


    protected void onHitEntity(EntityRayTraceResult p_213868_1_){
        if(throwed_spear!=null) this.spawnAtLocation(throwed_spear);
        super.onHitEntity(p_213868_1_);

    }

    protected void onHitBlock(BlockRayTraceResult p_230299_1_){
        if(throwed_spear!=null)this.spawnAtLocation(throwed_spear);
        super.onHitBlock(p_230299_1_);
    }





}
