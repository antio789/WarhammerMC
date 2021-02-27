package warhammermod.Entities;


import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityWarpBullet extends EntityBullet {

    int xTile;
    int yTile;
    int zTile;
    protected boolean inGround;
    public Entity shootingEntity;
    double damage;
    protected float bulletdamage;
    protected float extradamage;
    protected int knocklevel;
    EntityLivingBase entityshooter;

    public EntityWarpBullet(World worldIn, EntityLivingBase throwerIn, float damagein) {
        super(worldIn, throwerIn,damagein);

        entityshooter=throwerIn;
        bulletdamage=damagein;
    }

    public EntityWarpBullet(World worldIn) {
        super(worldIn);

    }

    public EntityWarpBullet(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);

    }

    public void onUpdate()
    {
        super.onUpdate();

        if (this.world.isRemote && !this.inGround)
        {
            spawnColoredParticles();
        }
    }

    public void setpowerDamage(int powerIn){
        extradamage=1.5F*powerIn;
    }
    public void setknockbacklevel(int knockin){
        knocklevel=knockin;
    }

    protected void onHit(RayTraceResult raytraceResultIn) {
        Entity entity = raytraceResultIn.entityHit;
        if (entity != null) {
            float i = bulletdamage + extradamage;

            DamageSource damagesource;

            if (this.shootingEntity == null) {
                damagesource = DamageSource.causeArrowDamage(this,entityshooter);
            } else {
                damagesource = DamageSource.causeArrowDamage(this, entityshooter);

            }

            if (this.isBurning() && !(entity instanceof EntityEnderman)) {
                entity.setFire(5);
            }


            if (entity.attackEntityFrom(damagesource, i)) {
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
                    if(rand.nextFloat()<0.3){
                        entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.WITHER, 60));
                    }

                    if (knocklevel > 0) {
                        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                        if (f1 > 0.0F) {
                            entitylivingbase.addVelocity(this.motionX * (double) knocklevel * 0.6000000238418579D / (double) f1, 0.1D, this.motionZ * (double) knocklevel * 0.6000000238418579D / (double) f1);
                        }
                    }

                    if (this.shootingEntity instanceof EntityLivingBase) {
                        EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
                        EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.shootingEntity, entitylivingbase);
                    }

                    this.arrowHit(entitylivingbase);

                    if (this.shootingEntity != null && entitylivingbase != this.shootingEntity && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                        ((EntityPlayerMP) this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                    }
                }

                this.playSound(SoundEvents.BLOCK_STONE_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                if (!(entity instanceof EntityEnderman)) {
                    this.setDead();
                }
            } else {
                this.setDead();
            }

        } else {
            this.setDead();
        }

    }


    private void spawnColoredParticles()
    {
        int i = 65280;
            double d0 = (double)(i >> 16 & 255) / 255.0D;
            double d1 = (double)(i >> 8 & 255) / 255.0D;
            double d2 = (double)(i >> 0 & 255) / 255.0D;

            for (int j = 0; j < 5; ++j)
            {
                this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, d0, d1, d2);
            }
    }

    protected ItemStack getArrowStack() {
        return null;
    }
}
