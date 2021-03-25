package warhammermod.Entities.living;


import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Biomes;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import warhammermod.Entities.EntityBullet;
import warhammermod.Entities.living.Emanager.EntityAIAttackRangedmusket;
import warhammermod.Items.GunBase;
import warhammermod.Items.Ranged.gunswtemplate;
import warhammermod.util.Handler.Loottable;
import warhammermod.util.Handler.inithandler.Itemsinit;
import warhammermod.util.utils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static warhammermod.util.confighandler.confighandler.Config_enable.Mob_enchanted_equipment;

public class EntityPirateSkeleton extends AbstractSkeleton {
    public EntityPirateSkeleton(World worldIn)
    {
        super(worldIn);
    }
    //private final EntityAIAttackRangedBow<AbstractSkeleton> aiArrowAttack = new EntityAIAttackRangedBow<AbstractSkeleton>(this, 1.0D, 20, 15.0F);
    private final EntityAIAttackRangedmusket<AbstractSkeleton> aigunattack = new EntityAIAttackRangedmusket<>(this, 1.0D, 20, 15.0F);

    public static List<Biome> skeleton_SPAWN_BIOMES = Arrays.asList(Biomes.BEACH,Biomes.COLD_BEACH,Biomes.DEEP_OCEAN,Biomes.OCEAN,Biomes.MESA,Biomes.STONE_BEACH);


    public static void registerFixesSkeleton(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntitySkeleton.class);
    }
    protected ResourceLocation getLootTable() { return Loottable.Pirate_Skeleton; }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);

        if (cause.getTrueSource() instanceof EntityCreeper)
        {
            EntityCreeper entitycreeper = (EntityCreeper)cause.getTrueSource();

            if (entitycreeper.getPowered() && entitycreeper.ableToCauseSkullDrop())
            {
                entitycreeper.incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 0), 0.0F);
            }
        }
    }
    private void setEnchantments(EntityBullet bullet){
        int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, this);
        if (j > 0) {
            bullet.setpowerDamage(j);
        }
        int k = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, this) + 1;
        if (k > 0) {
            bullet.setknockbacklevel(k);
        }


        if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, this) > 0) {
            bullet.setFire(100);
        }
    }



    public void setCombatTask()
    {
        if (this.world != null && !this.world.isRemote)
        {
            this.tasks.removeTask(this.aigunattack);
            ItemStack itemstack = this.getHeldItemMainhand();

            if (itemstack.getItem() instanceof GunBase || itemstack.getItem() instanceof gunswtemplate)
            {
                int i = 20;

                if (this.world.getDifficulty() != EnumDifficulty.HARD)
                {
                    i = 40;
                }

                this.aigunattack.setAttackCooldown(i);
                this.tasks.addTask(4, this.aigunattack);
            }
        }
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);
        if(Mob_enchanted_equipment)this.setEnchantmentBasedOnDifficulty(difficulty);
        this.setCombatTask();
        return livingdata;
    }


    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, utils.getRandomPirateWeapon());
    }

    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
        EntityBullet entitybullet = this.getArrow();
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - entitybullet.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        entitybullet.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - this.world.getDifficulty().getDifficultyId() * 4));
        this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.35F / (this.getRNG().nextFloat() * 0.4F + 1.2F)+0.5F);
        this.world.spawnEntity(entitybullet);

    }
    protected EntityBullet getArrow()
    {
        {
            EntityBullet entitybullet = new EntityBullet(this.world,this,getDamagefromWeapon()+(float)(1.5*this.world.getDifficulty().getDifficultyId()));
            setEnchantments(entitybullet);
            return entitybullet;
        }
    }
    public float getDamagefromWeapon(){
        if(this.getHeldItemMainhand().getItem() == Itemsinit.musket)return 6;
        else return 4;
    }
}

/* not working because attack ranged only called server side
if(this.world.isRemote){
            for (int k = 0; k < 60; ++k) {

                double s2 = this.rand.nextGaussian() * 0.02D;
                double s0 = this.rand.nextGaussian() * 0.02D;
                double s1 = this.rand.nextGaussian() * 0.02D;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX + getLookVec().x * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, posY + 0.4 + (double) (this.rand.nextFloat() * this.height), posZ + getLookVec().z * 2 + (double) (this.rand.nextFloat() * this.width * 2) - (double) this.width, s2, s0, s1);
            }
        }

 */
