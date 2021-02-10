package warhammermod.Entities;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import warhammermod.util.Handler.Entityinit;

public class GrenadeEntity extends Projectilebase {

    public GrenadeEntity(FMLPlayMessages.SpawnEntity packet, World worldIn﻿) {
        super(Entityinit.grenade, worldIn﻿);
    }

    public GrenadeEntity(EntityType<? extends GrenadeEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }
    public GrenadeEntity(World worldin, LivingEntity shooter, ItemStack stack) {
        super(worldin, shooter, stack, 0, Entityinit.grenade);
        this.pickupStatus= PickupStatus.DISALLOWED;

    }

    public GrenadeEntity(World world){
        super(Entityinit.grenade,world);
    }


    @OnlyIn(Dist.CLIENT)
    public GrenadeEntity(World p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
        super(p_i48791_1_, Entityinit.grenade, p_i48791_2_, p_i48791_4_, p_i48791_6_);
    }

    protected void onImpact(RayTraceResult raytraceResultIn) {
        if(!world.isRemote){
            world.createExplosion(null,raytraceResultIn.getHitVec().x,raytraceResultIn.getHitVec().y,raytraceResultIn.getHitVec().z,2+knocklevel, Explosion.Mode.BREAK);}
        this.remove();

    }




}
