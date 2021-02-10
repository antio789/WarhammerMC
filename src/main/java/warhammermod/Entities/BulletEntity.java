package warhammermod.Entities;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.util.Handler.Entityinit;


public class BulletEntity extends Projectilebase {



    public BulletEntity(FMLPlayMessages.SpawnEntity packet, World worldIn﻿) {
        super(Entityinit.Bullet, worldIn﻿);
    }


    public BulletEntity(EntityType<? extends BulletEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }


    public BulletEntity(World worldin, LivingEntity shooter, ItemStack stack, float damageIn) {
        super(worldin, shooter, stack, damageIn, Entityinit.Bullet);
        projectiledamage = damageIn;
        this.pickupStatus= PickupStatus.DISALLOWED;
    }

    public BulletEntity(World world){
        super(Entityinit.Bullet,world);
    }

    @OnlyIn(Dist.CLIENT)
    public BulletEntity(World p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
        super(p_i48791_1_, Entityinit.Bullet, p_i48791_2_, p_i48791_4_, p_i48791_6_);
    }

    protected void registerData() {
        super.registerData();
    }

    public void tick()
    {

        if (this.world.isRemote && !this.inGround)
        {
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        }
        super.tick();
    }


}
