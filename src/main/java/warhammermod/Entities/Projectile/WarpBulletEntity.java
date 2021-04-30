package warhammermod.Entities.Projectile;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.utils.inithandler.Entityinit;

public class WarpBulletEntity extends Projectilebase {

    public WarpBulletEntity(World world){
        super(Entityinit.WarpBullet,world);
    }
    public WarpBulletEntity(EntityType<? extends WarpBulletEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }
    public WarpBulletEntity(FMLPlayMessages.SpawnEntity packet, World worldIn﻿) {
        super(Entityinit.WarpBullet, worldIn﻿);
    }
    public WarpBulletEntity(World worldin, LivingEntity shooter, float damageIn, EntityType<? extends Projectilebase> type) {
        super(worldin, shooter,damageIn, type);
    }
    @OnlyIn(Dist.CLIENT)
    public WarpBulletEntity(World p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
        super(p_i48791_1_, Entityinit.WarpBullet, p_i48791_2_, p_i48791_4_, p_i48791_6_);
    }
    public WarpBulletEntity( LivingEntity shooter,World world, float damageIn) {
        super(world, shooter, damageIn, Entityinit.WarpBullet);

    }






    public void tick()
    {
        super.tick();

        if (this.level.isClientSide() && !this.inGround)
        {
            spawnColoredParticles();
        }
    }


    @Override
    public void applyspecialeffect(LivingEntity entity) {
        if(random.nextFloat()<0.3){
            entity.addEffect(new EffectInstance(Effects.WITHER, 60, 1));
        }
    }

    private void spawnColoredParticles()
    {
        int i = 65280;
            double d0 = (double)(i >> 16 & 255) / 255.0D;
            double d1 = (double)(i >> 8 & 255) / 255.0D;
            double d2 = (double)(i & 255) / 255.0D;

            for (int j = 0; j < 5; ++j)
            {
                this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (this.random.nextDouble() - 0.5D) * (double)this.getBbWidth(), this.getY() +this.random.nextDouble()*(double)this.getBbHeight(), this.getZ() + (this.random.nextDouble() - 0.5D) * (double)this.getBbWidth(), d0, d1, d2);
            }
    }

}
