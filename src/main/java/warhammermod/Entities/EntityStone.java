package warhammermod.Entities;


import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class EntityStone extends EntityArrow {

    private double damagetot=2.5;

    public EntityStone(World worldIn, EntityLivingBase throwerIn, float damagein) {
        super(worldIn, throwerIn);

    }

    public EntityStone(World worldIn) {
        super(worldIn);
    }

    public EntityStone(World worldIn, double x, double y, double z)
    {
        this(worldIn);
    }

    private int knockback;

    public void setKnockbackStrength(int knockbackStrengthIn)
    {
        this.knockback = knockbackStrengthIn;
    }

    public void setDamage(double damageIn)
    {
        this.damagetot = damageIn;
    }

    public double getDamage()
    {
        return this.damagetot;
    }



    protected ItemStack getArrowStack(){return new ItemStack(Item.getItemFromBlock(Blocks.COBBLESTONE));}

    protected void onHit(RayTraceResult raytraceResultIn)
    {
        Entity entity = raytraceResultIn.entityHit;

        if (entity != null)
        {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            int i = MathHelper.ceil((double)f * damagetot);

            if (this.getIsCritical())
            {
                i += this.rand.nextInt(i / 2 + 2);
            }

            DamageSource damagesource;

            if (this.shootingEntity == null)
            {
                damagesource = DamageSource.causeArrowDamage(this, this);
            }
            else
            {
                damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
            }

            if (this.isBurning() && !(entity instanceof EntityEnderman))
            {
                entity.setFire(5);
            }

            if (entity.attackEntityFrom(damagesource, (float)i))
            {
                if (entity instanceof EntityLivingBase)
                {
                    EntityLivingBase entitylivingbase = (EntityLivingBase)entity;

                    if (!this.world.isRemote)
                    {
                        entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
                    }

                    if (this.knockback > 0)
                    {
                        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                        if (f1 > 0.0F)
                        {
                            entitylivingbase.addVelocity(this.motionX * (double)this.knockback * 0.6000000238418579D / (double)f1, 0.1D, this.motionZ * (double)this.knockback * 0.6000000238418579D / (double)f1);
                        }
                    }

                    if (this.shootingEntity instanceof EntityLivingBase)
                    {
                        EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
                        EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entitylivingbase);
                    }

                }

                this.playSound(SoundEvents.BLOCK_STONE_BREAK, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                if (!(entity instanceof EntityEnderman))
                {
                    this.setDead();
                }
            }
            else
            {

                if (!this.world.isRemote && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ < 0.0010000000474974513D)
                {
                    if (this.pickupStatus == PickupStatus.ALLOWED)
                    {
                        this.entityDropItem(getArrowStack(), 0.1F);
                    }

                    this.setDead();
                }
            }
        }
        else {
            this.setDead();
            if (this.pickupStatus == PickupStatus.ALLOWED)
            {
                this.entityDropItem(getArrowStack(), 0.1F);
            }
        }

    }





}
