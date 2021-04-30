package warhammermod.Entities.Projectile;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class Projectilebase extends AbstractArrowEntity{

    public float projectiledamage;
    protected float extradamage;
    protected int knocklevel;

    public void setpowerDamage(float powerIn){
        extradamage=1.5F*powerIn;
    }
    public void setknockbacklevel(int knockin){
        knocklevel=knockin;
    }


    public Projectilebase(EntityType<? extends Projectilebase> entityType, World world) {
        super(entityType, world);
    }
    public Projectilebase(World worldin, LivingEntity shooter, float damageIn, EntityType<? extends Projectilebase> type) {
        super(type, shooter, worldin);
        projectiledamage = damageIn;
    }
    public Projectilebase(World world, EntityType<? extends Projectilebase> entity, double x, double y, double z) {
        super(entity, x, y, z, world);
    }




    protected ItemStack getPickupItem(){return null;}

    protected ItemStack getArrowStack() {
        return null;
    }



    public boolean getIsCritical() {
        return false;
    }

    public float totaldamage;



    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        this.playSound(SoundEvents.STONE_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.remove();
    }


    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.arrow(this, this);
        } else {
            damagesource = DamageSource.arrow(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastHurtMob(entity);
            }
        }
        float i = TotalDamage();

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            entity.setSecondsOnFire(5);
        }

        if (entity.hurt(damagesource, i)) {
            if (flag) {
                return;
            }
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                applyspecialeffect(livingentity);
                if (this.knocklevel > 0) {
                    Vector3d vec3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knocklevel * 0.6D);
                    if (vec3d.lengthSqr() > 0.0D) {
                        livingentity.push(vec3d.x, 0.1D, vec3d.z);
                    }
                }
                if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity);
                }

                this.doPostHurtEffects(livingentity);
                if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
                }

            }

            this.playSound(SoundEvents.STONE_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.remove();
        } else {
            entity.setRemainingFireTicks(j);
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.yRot += 180.0F;
            this.yRotO += 180.0F;
            if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                this.remove();
            }
        }

    }

    public float TotalDamage(){
        return projectiledamage+extradamage;
    }

    public void applyspecialeffect(LivingEntity entity){
    }

    @Override
    public  IPacket<?> getAddEntityPacket(){
        return NetworkHooks.getEntitySpawningPacket(this);
    }




}
