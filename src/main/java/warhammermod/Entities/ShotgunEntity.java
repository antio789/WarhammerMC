package warhammermod.Entities;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.util.Handler.Entityinit;

public class ShotgunEntity extends Projectilebase {



    public double[] firepos;
    public double[] hitpos;

    public ShotgunEntity(FMLPlayMessages.SpawnEntity packet, World worldIn﻿) {
        super(Entityinit.shotgun, worldIn﻿);
    }

    public ShotgunEntity(World worldIn, LivingEntity throwerIn, ItemStack stack, int damagein){
        super(worldIn,throwerIn,stack,damagein, Entityinit.shotgun);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    public ShotgunEntity(EntityType<? extends ShotgunEntity> p_i50148_1_, World p_i50148_2_){
        super(p_i50148_1_,p_i50148_2_);
    }

    @OnlyIn(Dist.CLIENT)
    public ShotgunEntity(World p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
        super(p_i48791_1_, Entityinit.shotgun,p_i48791_2_, p_i48791_4_, p_i48791_6_);
    }

    public ShotgunEntity(World world){
        super(Entityinit.shotgun,world);
    }



    protected void onHit(RayTraceResult raytraceResultIn) {
        Entity entity;
        if (raytraceResultIn.getType() == RayTraceResult.Type.ENTITY && !world.isRemote) {
            entity = ((EntityRayTraceResult)raytraceResultIn).getEntity();
            hitpos= new double[]{entity.getPosX(), entity.getPosY() - 0.2, entity.getPosZ()};
            double tot=0;
            for(int i=0;i<3;i++){
                tot+=(hitpos[i]-firepos[i])*(hitpos[i]-firepos[i]);
            }
            tot=Math.sqrt(tot);
            float i;

            if(tot<4.2) i = projectiledamage + extradamage;
            else if(tot<23) i = (projectiledamage+extradamage)/((float)tot*0.26F);
            else {this.remove();return;}
            DamageSource damagesource;

            if (this.getShooter() == null) {
                damagesource = DamageSource.causeArrowDamage(this,this);
            } else {
                damagesource = DamageSource.causeArrowDamage(this, getShooter());
            }

            if (this.isBurning() && !(entity instanceof EndermanEntity)) {
                entity.setFire(5);
            }

            if (entity.attackEntityFrom(damagesource, i)) {
                if (entity instanceof LivingEntity) {
                    LivingEntity entitylivingbase = (LivingEntity) entity;


                    if (knocklevel > 0) {
                        Vec3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knocklevel * 0.6D);
                        if (vec3d.lengthSquared() > 0.0D) {
                            entitylivingbase.addVelocity(vec3d.x, 0.1D, vec3d.z);
                        }
                    }

                    if (this.getShooter() instanceof LivingEntity) {
                        EnchantmentHelper.applyThornEnchantments(entitylivingbase, getShooter());
                        EnchantmentHelper.applyArthropodEnchantments((LivingEntity) this.getShooter(), entitylivingbase);
                    }

                    this.arrowHit(entitylivingbase);

                    if (getShooter() != null && entitylivingbase != getShooter() && entitylivingbase instanceof PlayerEntity && getShooter() instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity)getShooter()).connection.sendPacket(new SChangeGameStatePacket(6, 0.0F));
                    }
                }

                this.playSound(SoundEvents.BLOCK_STONE_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));


            }

        }this.remove();

    }

}
