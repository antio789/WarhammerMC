package warhammermod.Entities.Projectile;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.utils.inithandler.Entityinit;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class StoneEntity extends Projectilebase implements IRendersAsItem {
    public StoneEntity(World world){
        super(Entityinit.StoneEntity,world);
    }

    public StoneEntity(EntityType<? extends StoneEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }

    public StoneEntity(World worldin, LivingEntity shooter, float damageIn, EntityType<? extends Projectilebase> type) {
        super(worldin, shooter,damageIn, type);
    }
    public StoneEntity(World world, EntityType<? extends Projectilebase> entity, double x, double y, double z) {
        super( world,entity, x, y, z);
    }

    public StoneEntity(LivingEntity shooter, World world, float damage) {
        this(world, shooter, damage, Entityinit.StoneEntity);
    }

    public StoneEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(Entityinit.StoneEntity, world);
    }





    public ItemStack getItem() {
        return new ItemStack(Items.COBBLESTONE);
    }

    public void setTotaldamage(float sum) {
        this.totaldamage = sum;
    }

    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        float f = (float)this.getDeltaMovement().length();
        double damageSum = projectiledamage+extradamage;
        System.out.println(damageSum+ " damagesum");
        int i = MathHelper.ceil(MathHelper.clamp((double)f * damageSum, 0.0D, 2.147483647E9D));
        System.out.println(damageSum+ " i");
        if (this.isCritArrow()) {
            long j = this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(j + (long)i, 2147483647L);
        }
        System.out.println(damageSum+ " i");
        setTotaldamage(i);
        super.onHitEntity(p_213868_1_);

    }

    @Override
    public float getTotalDamage(){
        return totaldamage;
    }









}
