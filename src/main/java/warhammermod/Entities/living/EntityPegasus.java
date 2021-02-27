package warhammermod.Entities.living;


import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import warhammermod.util.proxy.clientproxy;
import warhammermod.util.utils;

public class EntityPegasus extends EntityHorse {
    public EntityPegasus(World worldIn){
        super(worldIn);
    }

    public boolean isIselytrafly() {
        return iselytrafly;
    }

    public boolean isIsstationaryflying() {
        return isstationaryflying;
    }

    private boolean iselytrafly = false;
    private boolean isstationaryflying = true;

    private int jumpcounter;
    private boolean lastpressjump;

    private void changeflymode(){
        if(iselytrafly==isstationaryflying) {
            isstationaryflying=true;
            iselytrafly=false;
        }else{
            iselytrafly = !iselytrafly;
            isstationaryflying = !isstationaryflying;
        }
    }
    private void setFlyFalse(){
        iselytrafly=false;
        isstationaryflying=false;
    }
    private int Timer;

    public boolean isFlying(){
        return iselytrafly ||isstationaryflying;
    }




    public void travel(float strafe, float vertical, float forward)
    {
        if (this.isBeingRidden() && this.canBeSteered() && this.isHorseSaddled())
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)this.getControllingPassenger();

            this.rotationYaw = entitylivingbase.rotationYaw;
            this.prevRotationYaw = this.rotationYaw;
            this.rotationPitch = entitylivingbase.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.renderYawOffset;
            strafe = entitylivingbase.moveStrafing * 0.5F;
            forward = entitylivingbase.moveForward;

            if(world.isRemote){
                EntityPlayerSP player = (EntityPlayerSP)entitylivingbase;
                if(isstationaryflying ||(iselytrafly && player.rotationPitch<0)){
                    if(Timer>25){
                        world.playSound(player.posX,player.posY,player.posZ,SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS,0.65F,3F,true);
                        Timer=0;
                    }
                    else Timer++;
                }
                boolean flag=player.movementInput.jump;
                if(flag){
                    if(jumpcounter==0 && !lastpressjump) {jumpcounter=10;}
                    else if(!lastpressjump){changeflymode();jumpcounter=0;}
                    lastpressjump=true;
                }
                else lastpressjump=false;

            }

            if (forward <= 0.0F)
            {
                forward *= 0.25F;
                this.gallopTime = 0;
            }

            if (this.onGround && this.jumpPower == 0.0F && this.isRearing())
            {
                strafe = 0.0F;
                forward = 0.0F;
            }

            if (this.jumpPower > 0.0F && !this.isHorseJumping() && this.onGround)
            {
                this.motionY = this.getHorseJumpStrength() * (double)this.jumpPower;

                if (this.isPotionActive(MobEffects.JUMP_BOOST))
                {
                    this.motionY += (double)((float)(this.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
                }

                this.setHorseJumping(true);
                this.isAirBorne = true;

                if (forward > 0.0F)
                {
                    float f = MathHelper.sin(this.rotationYaw * 0.017453292F);
                    float f1 = MathHelper.cos(this.rotationYaw * 0.017453292F);
                    this.motionX += (double)(-0.4F * f * this.jumpPower);
                    this.motionZ += (double)(0.4F * f1 * this.jumpPower);
                    this.playSound(SoundEvents.ENTITY_HORSE_JUMP, 0.4F, 1.0F);
                }

                this.jumpPower = 0.0F;
            }

            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

            if(iselytrafly){
                if (this.motionY > -0.5D)
                {
                    this.fallDistance = 1.0F;
                }

                Vec3d vec3d = this.getControllingPassenger().getLookVec();
                float f = this.rotationPitch * 0.017453292F; //rotationpitch is in degrees. pass in radians
                double hor_dir = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
                double hor_speed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                double speed = vec3d.lengthVector();
                float lim_pitch = MathHelper.cos(f); // from radians to 0-1
                lim_pitch = (float)((double)lim_pitch * (double)lim_pitch * Math.min(1.0D, speed / 0.4D));
                this.motionY += -0.08D + (double)lim_pitch * 0.06D;

                if ((this.motionY < 0.0D) && hor_dir > 0.0D)
                {
                    double d2 = this.motionY * -0.1D * (double)lim_pitch;
                    this.motionY += d2*0.3;
                    this.motionX += vec3d.x * d2 / hor_dir*1.4;
                    this.motionZ += vec3d.z * d2 / hor_dir*1.4;
                }

                if (f < 0.0F)
                {
                    double d10 = hor_speed * (double)(-MathHelper.sin(f)) * 0.4D;
                    utils.printer(MathHelper.sin(f));
                        if(hor_speed>1){
                            this.motionY +=  d10 * 2.5;
                            this.motionX -= vec3d.x * d10 / hor_dir;
                            this.motionZ -= vec3d.z * d10 / hor_dir;
                        }
                        else{
                            this.motionX += vec3d.x * 0.025;
                            this.motionZ += vec3d.z * 0.025;
                            this.motionY = 0.45*getHorseJumpStrength()*-MathHelper.sin(f);
                        }

                }

                if (hor_dir > 0.0D)
                {
                    this.motionX += (vec3d.x / hor_dir * hor_speed - this.motionX) * 0.1D;
                    this.motionZ += (vec3d.z / hor_dir * hor_speed - this.motionZ) * 0.1D;
                }



                this.motionX *= 0.9950000095367432D;
                this.motionY *= 0.9850000190734863D;
                this.motionZ *= 0.9950000095367432D;
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

                if (this.collidedHorizontally && !this.world.isRemote)
                {
                    double d11 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    double d3 = hor_speed - d11;
                    float f5 = (float)(d3 * 10.0D - 3.0D);

                    if (f5 > 0.0F)
                    {
                        this.playSound(this.getFallSound((int)f5), 1.0F, 1.0F);
                        this.attackEntityFrom(DamageSource.FLY_INTO_WALL, f5);
                    }
                }
                if(onGround){
                    setFlyFalse();
                }

            }
            else if(isstationaryflying){
                motionY=-0.0005;
                if(jumpcounter>0){
                    jumpcounter--;
                }

                if(entitylivingbase instanceof EntityPlayerSP){
                    EntityPlayerSP player = (EntityPlayerSP)entitylivingbase;
                    if(player.movementInput.jump){
                        motionY=0.4*getHorseJumpStrength();
                    }
                    if(clientproxy.horsedown.isKeyDown()){
                        motionY=-0.4*getHorseJumpStrength();
                    }

                }
                if(onGround){setFlyFalse();}


            }

            if (this.canPassengerSteer())
            {
                this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
                EntityLivingBasetravel(strafe,vertical,forward);
            }
            else if (entitylivingbase instanceof EntityPlayer)
            {
                this.motionX = 0.0D;
                this.motionY = 0.0D;
                this.motionZ = 0.0D;
            }

            if (this.onGround)
            {
                this.jumpPower = 0.0F;
                this.setHorseJumping(false);
            }
            if(this.onGround) {
                this.prevLimbSwingAmount = this.limbSwingAmount;
                double speed = this.posX - this.prevPosX;
                double d0 = this.posZ - this.prevPosZ;
                float f2 = MathHelper.sqrt(speed * speed + d0 * d0) * 4.0F;

                if (f2 > 1.0F) {
                    f2 = 1.0F;
                }

                this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
                this.limbSwing += this.limbSwingAmount;
            }
        }
        else
        {
            setFlyFalse();
            this.jumpMovementFactor = 0.02F;
            EntityLivingBasetravel(strafe, vertical, forward);
        }
    }

    public void EntityLivingBasetravel(float strafe, float vertical, float forward)
    {
        if (this.isServerWorld() || this.canPassengerSteer())
        {
            if (!this.isInWater() )//|| this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying)
            {
                if (!this.isInLava() )//|| this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying)
                {

                    if(!iselytrafly)
                    {
                        float f6 = 0.91F;
                        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(this.posX, this.getEntityBoundingBox().minY - 1.0D, this.posZ);

                        if (this.onGround)
                        {
                            IBlockState underState = this.world.getBlockState(blockpos$pooledmutableblockpos);
                            f6 = underState.getBlock().getSlipperiness(underState, this.world, blockpos$pooledmutableblockpos, this) * 0.91F;
                        }

                        float f7 = 0.16277136F / (f6 * f6 * f6);
                        float f8;

                        if (this.onGround)
                        {
                            f8 = this.getAIMoveSpeed() * f7;
                        }
                        else
                        {
                            f8 = this.jumpMovementFactor;
                        }

                        this.moveRelative(strafe, vertical, forward, f8);
                        f6 = 0.91F;

                        if (this.onGround)
                        {
                            IBlockState underState = this.world.getBlockState(blockpos$pooledmutableblockpos.setPos(this.posX, this.getEntityBoundingBox().minY - 1.0D, this.posZ));
                            f6 = underState.getBlock().getSlipperiness(underState, this.world, blockpos$pooledmutableblockpos, this) * 0.91F;
                        }

                        if (this.isOnLadder())
                        {
                            float f9 = 0.15F;
                            this.motionX = MathHelper.clamp(this.motionX, -0.15000000596046448D, 0.15000000596046448D);
                            this.motionZ = MathHelper.clamp(this.motionZ, -0.15000000596046448D, 0.15000000596046448D);
                            this.fallDistance = 0.0F;

                            if (this.motionY < -0.15D)
                            {
                                this.motionY = -0.15D;
                            }

                        }

                        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

                        if (this.collidedHorizontally && this.isOnLadder())
                        {
                            this.motionY = 0.2D;
                        }

                        if (this.isPotionActive(MobEffects.LEVITATION))
                        {
                            this.motionY += (0.05D * (double)(this.getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1) - this.motionY) * 0.2D;
                        }
                        else
                        {
                            blockpos$pooledmutableblockpos.setPos(this.posX, 0.0D, this.posZ);

                            if (!this.world.isRemote || this.world.isBlockLoaded(blockpos$pooledmutableblockpos) && this.world.getChunkFromBlockCoords(blockpos$pooledmutableblockpos).isLoaded())
                            {
                                if (!this.hasNoGravity())
                                {
                                    this.motionY -= 0.08D;
                                }
                            }
                            else if (this.posY > 0.0D)
                            {
                                this.motionY = -0.1D;
                            }
                            else
                            {
                                this.motionY = 0.0D;
                            }
                        }

                        this.motionY *= 0.9800000190734863D;
                        this.motionX *= (double)f6;
                        this.motionZ *= (double)f6;
                        blockpos$pooledmutableblockpos.release();
                    }
                    if(!isFlying() && fallDistance>3)
                    {

                            if (this.motionY > -0.5D)
                            {
                                this.fallDistance = 1.0F;
                            }

                            Vec3d vec3d = this.getLookVec();
                            float f = this.rotationPitch * 0.017453292F;
                            double d6 = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
                            double d8 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                            double d1 = vec3d.lengthVector();
                            float f4 = MathHelper.cos(f);
                            f4 = (float)((double)f4 * (double)f4 * Math.min(1.0D, d1 / 0.4D));
                            this.motionY += -0.08D + (double)f4 * 0.06D;

                            if (this.motionY < 0.0D && d6 > 0.0D)
                            {
                                double d2 = this.motionY * -0.1D * (double)f4;
                                this.motionY += d2*5;
                                this.motionX += vec3d.x * d2 / d6*2;
                                this.motionZ += vec3d.z * d2 / d6*2;
                            }

                            if (f < 0.0F)
                            {
                                double d10 = d8 * (double)(-MathHelper.sin(f)) * 0.04D;
                                this.motionY += d10 * 3.2D;
                                this.motionX -= vec3d.x * d10 / d6;
                                this.motionZ -= vec3d.z * d10 / d6;
                            }

                            if (d6 > 0.0D)
                            {
                                this.motionX += (vec3d.x / d6 * d8 - this.motionX) * 0.1D;
                                this.motionZ += (vec3d.z / d6 * d8 - this.motionZ) * 0.1D;
                            }

                            this.motionX *= 0.9900000095367432D;
                            this.motionY *= 0.9800000190734863D;
                            this.motionZ *= 0.9900000095367432D;
                            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

                            if (this.collidedHorizontally && !this.world.isRemote)
                            {
                                double d11 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                                double d3 = d8 - d11;
                                float f5 = (float)(d3 * 10.0D - 3.0D);

                                if (f5 > 0.0F)
                                {
                                    this.playSound(this.getFallSound((int)f5), 1.0F, 1.0F);
                                    this.attackEntityFrom(DamageSource.FLY_INTO_WALL, f5);
                                }
                            }

                            if (this.onGround && !this.world.isRemote)
                            {
                                this.setFlag(7, false);
                            }

                    }
                }
                else
                {
                    double d4 = this.posY;
                    this.moveRelative(strafe, vertical, forward, 0.02F);
                    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                    this.motionX *= 0.5D;
                    this.motionY *= 0.5D;
                    this.motionZ *= 0.5D;

                    if (!this.hasNoGravity())
                    {
                        this.motionY -= 0.02D;
                    }

                    if (this.collidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d4, this.motionZ))
                    {
                        this.motionY = 0.30000001192092896D;
                    }
                }
            }
            else
            {
                double d0 = this.posY;
                float f1 = this.getWaterSlowDown();
                float f2 = 0.02F;
                float f3 = (float) EnchantmentHelper.getDepthStriderModifier(this);

                if (f3 > 3.0F)
                {
                    f3 = 3.0F;
                }

                if (!this.onGround)
                {
                    f3 *= 0.5F;
                }

                if (f3 > 0.0F)
                {
                    f1 += (0.54600006F - f1) * f3 / 3.0F;
                    f2 += (this.getAIMoveSpeed() - f2) * f3 / 3.0F;
                }

                this.moveRelative(strafe, vertical, forward, f2);
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                this.motionX *= (double)f1;
                this.motionY *= 0.800000011920929D;
                this.motionZ *= (double)f1;

                if (!this.hasNoGravity())
                {
                    this.motionY -= 0.02D;
                }

                if (this.collidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d0, this.motionZ))
                {
                    this.motionY = 0.30000001192092896D;
                }
            }
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d5 = this.posX - this.prevPosX;
        double d7 = this.posZ - this.prevPosZ;
        double d9 = this instanceof net.minecraft.entity.passive.EntityFlying ? this.posY - this.prevPosY : 0.0D;
        float f10 = MathHelper.sqrt(d5 * d5 + d9 * d9 + d7 * d7) * 4.0F;

        if (f10 > 1.0F)
        {
            f10 = 1.0F;
        }

        this.limbSwingAmount += (f10 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }


    public void fall(float distance, float damageMultiplier)
    {
        super.fall(distance, 0);

    }

    public EntityAgeable createChild(EntityAgeable ageable)
    {
        AbstractHorse abstracthorse;

        if (ageable instanceof EntityDonkey)
        {
            abstracthorse = new EntityMule(this.world);
        }

        else if(ageable instanceof EntityPegasus)
        {
            EntityPegasus entityhorse = (EntityPegasus) ageable;
            abstracthorse = new EntityPegasus(this.world);
            int j = this.rand.nextInt(9);
            int i;

            if (j < 4)
            {
                i = this.getHorseVariant() & 255;
            }
            else if (j < 8)
            {
                i = entityhorse.getHorseVariant() & 255;
            }
            else
            {
                i = this.rand.nextInt(7);
            }

            int k = this.rand.nextInt(5);

            if (k < 2)
            {
                i = i | this.getHorseVariant() & 65280;
            }
            else if (k < 4)
            {
                i = i | entityhorse.getHorseVariant() & 65280;
            }
            else
            {
                i = i | this.rand.nextInt(5) << 8 & 65280;
            }

            ((EntityHorse)abstracthorse).setHorseVariant(i);
        }
        else{
            EntityHorse entityhorse = (EntityHorse)ageable;
            abstracthorse = new EntityHorse(this.world);
            int j = this.rand.nextInt(9);
            int i;

            if (j < 4)
            {
                i = this.getHorseVariant() & 255;
            }
            else if (j < 8)
            {
                i = entityhorse.getHorseVariant() & 255;
            }
            else
            {
                i = this.rand.nextInt(7);
            }

            int k = this.rand.nextInt(5);

            if (k < 2)
            {
                i = i | this.getHorseVariant() & 65280;
            }
            else if (k < 4)
            {
                i = i | entityhorse.getHorseVariant() & 65280;
            }
            else
            {
                i = i | this.rand.nextInt(5) << 8 & 65280;
            }

            ((EntityHorse)abstracthorse).setHorseVariant(i);

        }

        this.setOffspringAttributes(ageable, abstracthorse);
        return abstracthorse;
    }




}
