package warhammermod.Entities.living;

import net.minecraft.block.Blocks;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.passive.horse.*;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.main;
import warhammermod.util.Handler.Entityinit;

import javax.annotation.Nullable;


//eytra up too fast
public class PegasusEntity extends HorseEntity {

    private static final DataParameter<Boolean> MixBlood = EntityDataManager.createKey(PegasusEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.createKey(HorseEntity.class, DataSerializers.VARINT);


    private static final String[] HORSE_TEXTURES = new String[]{"warhammermod:textures/entity/pegasus/pegasus.png", "warhammermod:textures/entity/PEGASUS/pegasus_creamy.png", "warhammermod:textures/entity/PEGASUS/pegasus_chestnut.png", "warhammermod:textures/entity/PEGASUS/pegasus_brown.png", "warhammermod:textures/entity/PEGASUS/pegasus_black.png", "warhammermod:textures/entity/PEGASUS/pegasus_gray.png", "warhammermod:textures/entity/PEGASUS/pegasus_darkbrown.png"};
    private static final String[] HORSE_TEXTURES_ABBR = new String[]{"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
    private static final String[] HORSE_MARKING_TEXTURES = new String[]{null, "warhammermod:textures/entity/pegasus/pegasus_markings_white.png", "warhammermod:textures/entity/PEGASUS/pegasus_markings_whitefield.png", "warhammermod:textures/entity/PEGASUS/pegasus_markings_whitedots.png", "warhammermod:textures/entity/PEGASUS/pegasus_markings_blackdots.png"};
    private static final String[] HORSE_MARKING_TEXTURES_ABBR = new String[]{"", "wo_", "wmo", "wdo", "bdo"};



    public PegasusEntity(EntityType<? extends HorseEntity> p_i50238_1_, World p_i50238_2_) {
        super(p_i50238_1_, p_i50238_2_);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(MixBlood, false);
    }

    public boolean isIselytrafly() {
        return iselytrafly;
    }

    public boolean isIsstationaryflying() {
        return isstationaryflying;
    }

    private boolean iselytrafly = false;
    private boolean isstationaryflying = false;

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


    public Boolean isgrounded(){
        BlockPos blockpos = this.getPositionUnderneath();
        return!(this.world.getBlockState(blockpos).getBlock()== Blocks.AIR);

    }

    public void travel(Vector3d p_213352_1_) {

        if (this.isAlive()) {
            if (this.isBeingRidden() && this.canBeSteered() && this.isHorseSaddled()) {


                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
                if (world.isRemote) {

                    ClientPlayerEntity player = (ClientPlayerEntity) livingentity;
                    boolean flag = player.movementInput.jump;
                    if (flag) {
                        if (jumpcounter == 0 && !lastpressjump) {
                            jumpcounter = 10;
                        } else if (!lastpressjump) {
                            changeflymode();
                            jumpcounter = 0;
                        }
                        lastpressjump = true;
                    } else lastpressjump = false;
                    if (jumpcounter > 0) {
                        jumpcounter--;
                    }
                }


                    if(!isFlying()){super.travel(p_213352_1_);
                        return; }


                    this.rotationYaw = livingentity.rotationYaw;
                    this.prevRotationYaw = this.rotationYaw;
                    this.rotationPitch = livingentity.rotationPitch * 0.5F;
                    this.setRotation(this.rotationYaw, this.rotationPitch);
                    this.renderYawOffset = this.rotationYaw;
                    this.rotationYawHead = this.renderYawOffset;
                    float f = livingentity.moveStrafing * 0.5F;
                    float f1 = livingentity.moveForward;
                    if (f1 <= 0.0F) {
                        f1 *= 0.25F;
                        this.gallopTime = 0;
                    }
                    if (isstationaryflying || (iselytrafly && livingentity.rotationPitch < 0)) {
                        if (Timer > 25) {
                            //world.playMovingSound(null,player,SoundEvents.ENTITY_ENDER_DRAGON_FLAP,SoundCategory.PLAYERS,0.65F,3);
                            world.playSound(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), SoundEvents.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.PLAYERS, 0.65F, 3F, true);
                            Timer = 0;
                        } else Timer++;
                    }



                    if (isgrounded()) {

                        setFlyFalse();
                    }
                    if (iselytrafly) {
                        Vector3d motionvec = this.getMotion();
                        if (motionvec.y > -0.5D) {
                            this.fallDistance = 1.0F;
                        }

                        Vector3d vec3d = this.getControllingPassenger().getLookVec();
                        float f6 = this.rotationPitch * ((float) Math.PI / 180F);
                        double dir = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
                        double speed = Math.sqrt(horizontalMag(motionvec)); //x^2+z^2 pos
                        double d12 = vec3d.length();
                        float f3 = MathHelper.cos(f6);
                        f3 = (float) ((double) f3 * (double) f3 * Math.min(1.0D, d12 / 0.4D)); //f3^2 * min 1 ,
                        ModifiableAttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
                        double d0 = gravity.getValue();
                        motionvec = this.getMotion().add(0.0D, d0 * (-1.0D + (double) f3 * 0.75D), 0.0D);
                        if (motionvec.y < 0.0D && dir > 0.0D) {// descend x y z +

                            double d3 = motionvec.y * -0.1D * (double) f3; // +
                            motionvec = motionvec.add(vec3d.x * d3 / dir, d3, vec3d.z * d3 / dir);
                        }
                        //  0.45*getHorseJumpStrength()*-MathHelper.sin(f);
                        if (f6 < 0.0F && dir > 0.0D) { // monte x z - y
                            double d13 = speed * (double) (-MathHelper.sin(f6)) * 0.04D; // +
                            if(speed<1){
                                motionvec=motionvec.add(vec3d.x*0.025,d13*3.2D,vec3d.z*0.025);
                            }
                            else
                            motionvec = motionvec.add(-vec3d.x * d13 / dir, d13 * 3.2D, -vec3d.z * d13 / dir);
                            if(motionvec.y<getHorseJumpStrength()*0.4){
                                motionvec=motionvec.add(0,getHorseJumpStrength(),0);
                            }

                        }

                        if (dir > 0.0D) {
                            motionvec = motionvec.add((vec3d.x / dir * speed - motionvec.x) * 0.1D, 0.0D, (vec3d.z / dir * speed - motionvec.z) * 0.1D);
                        }

                        this.setMotion(motionvec.mul((double) 0.99F, (double) 0.98F, (double) 0.99F));
                        this.move(MoverType.SELF, this.getMotion());
                        if (this.collidedHorizontally && !this.world.isRemote) {
                            double d14 = Math.sqrt(horizontalMag(this.getMotion()));
                            double d4 = speed - d14;
                            float f4 = (float) (d4 * 10.0D - 3.0D);
                            if (f4 > 0.0F) {
                                this.playSound(this.getFallSound((int) f4), 1.0F, 1.0F);
                                this.attackEntityFrom(DamageSource.FLY_INTO_WALL, f4);
                            }
                        }

                        if (this.onGround && !this.world.isRemote) {
                            this.setFlag(7, false);
                        }
                    }
                    else if (isstationaryflying && world.isRemote()) {
                        ClientPlayerEntity player = (ClientPlayerEntity)livingentity;
                        double flap = Math.max( Math.abs(Math.cos(Math.PI*this.ticksExisted/25)),0.5)*0.4;
                            if (player.movementInput.jump) {
                                setMotion(getMotion().getX(), flap * getHorseJumpStrength(), getMotion().getZ());
                            } else if (main.horsedown.isKeyDown()) {
                                setMotion(getMotion().getX(), -flap * getHorseJumpStrength(), getMotion().getZ());
                            } else setMotion(getMotion().getX(), (flap-0.32)*0.08, getMotion().getZ());

                            this.setAIMoveSpeed((float) this.getAttribute(Attributes.field_233821_d_).getValue());
                            movehorse(new Vector3d((double)f*0.5, p_213352_1_.y, (double)f1*0.5));


                            this.fallDistance = 0;

                    }



            }
            else {
                super.travel(p_213352_1_);
            }
            this.fallDistance=0;
        }
    }

    private void movehorse(Vector3d vec){
        ModifiableAttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
        double d0 = gravity.getValue();
        BlockPos blockpos = this.getPositionUnderneath();
        float f5 = Blocks.GRASS.getDefaultState().getSlipperiness(world, blockpos, this);
        float f7 =  f5 * 0.91F;
        this.moveRelative(this.func_213335_r(f5), vec);
        this.move(MoverType.SELF, this.getMotion());
        Vector3d vec3d5 = this.getMotion();
        if ((this.collidedHorizontally || this.isJumping) && this.isOnLadder()) {
            vec3d5 = new Vector3d(vec3d5.x, 0.2D, vec3d5.z);
        }

        double d10 = vec3d5.y;
        if (this.isPotionActive(Effects.LEVITATION)) {
            d10 += (0.05D * (double)(this.getActivePotionEffect(Effects.LEVITATION).getAmplifier() + 1) - vec3d5.y) * 0.2D;
            this.fallDistance = 0.0F;
        } else if (this.world.isRemote && !this.world.isBlockLoaded(blockpos)) {
            if (this.getPosY() > 0.0D) {
                d10 = -0.1D;
            } else {
                d10 = 0.0D;
            }
        } else if (!this.hasNoGravity()) {
            d10 -= d0;
        }

        this.setMotion(vec3d5.x * (double)f7, d10 * (double)0.98F, vec3d5.z * (double)f7);

    }


    private float func_213335_r(float p_213335_1_) {
        return  this.getAIMoveSpeed() * (0.21600002F / (p_213335_1_ * p_213335_1_ * p_213335_1_));
    }



    private void setHorseVariant(int p_234242_1_) {
        this.dataManager.set(HORSE_VARIANT, p_234242_1_);
    }

    private int getHorseVariant() {
        return this.dataManager.get(HORSE_VARIANT);
    }


    private void func_234242_w_(int p_234242_1_) {
        this.dataManager.set(HORSE_VARIANT, p_234242_1_);
    }
    private int func_234241_eS_() {
        return this.dataManager.get(HORSE_VARIANT);
    }

    private void setCoat(CoatColors p_234238_1_, CoatTypes p_234238_2_) {
        this.func_234242_w_(p_234238_1_.func_234253_a_() & 255 | p_234238_2_.func_234247_a_() << 8 & '\uff00');
    }

    public CoatColors getCoatcolor() {
        return CoatColors.func_234254_a_(this.func_234241_eS_() & 255);
    }

    public CoatTypes getCoatType() {
        return CoatTypes.func_234248_a_((this.func_234241_eS_() & '\uff00') >> 8);
    }


    public AgeableEntity createChild(AgeableEntity partner) {
        AbstractHorseEntity Pegasus_special=null;
        if (partner instanceof DonkeyEntity) {
            Pegasus_special = EntityType.MULE.create(this.world);
        } else if (partner instanceof PegasusEntity) {
            Pegasus_special = Entityinit.PEGASUS.create(this.world);

            ((PegasusEntity) Pegasus_special).setCoat(CoatColors.WHITE, CoatTypes.NONE);
        } else if (rand.nextFloat() < 0.1F) {

            HorseEntity mating_partner = (HorseEntity) partner;
            Pegasus_special = Entityinit.PEGASUS.create(this.world);
            ((PegasusEntity) Pegasus_special).setMixedblood(true);
            int j = this.rand.nextInt(9);
            CoatColors coatcolors;
            if (j < 8) {
                coatcolors = mating_partner.func_234239_eK_();
            } else {
                coatcolors = Util.func_240989_a_(CoatColors.values(), this.rand);
            }
            int k = this.rand.nextInt(5);
            CoatTypes coattypes;
            if (k < 4) {
                coattypes = mating_partner.func_234240_eM_();
            } else {
                coattypes = Util.func_240989_a_(CoatTypes.values(), this.rand);
                ((PegasusEntity) Pegasus_special).setCoat(coatcolors, coattypes);
            }
        } else
            {
                partner.createChild(partner);
            }
        if (Pegasus_special != null) {
            this.setOffspringAttributes(partner, Pegasus_special);
        }

            return Pegasus_special;

    }

    public void setMixedblood(Boolean mixblood) {
        this.dataManager.set(MixBlood, mixblood);
    }

    public Boolean ismixblood() {
        return this.dataManager.get(MixBlood);
    }


    @Nullable
    private String texturePrefix;
    private final String[] horseTexturesArray = new String[2];

    private void resetTexturePrefix() {
        this.texturePrefix = null;
    }

    @OnlyIn(Dist.CLIENT)
    private void setHorseTexturePaths() {
        int i = this.getHorseVariant();
        int j = (i & 255) % 7;
        int k = ((i & '\uff00') >> 8) % 5;
        this.horseTexturesArray[0] = HORSE_TEXTURES[j];
        this.horseTexturesArray[1] = HORSE_MARKING_TEXTURES[k];
        this.texturePrefix = "warhamermod:PEGASUS/" + HORSE_TEXTURES_ABBR[j] + HORSE_MARKING_TEXTURES_ABBR[k];
    }

    @OnlyIn(Dist.CLIENT)
    public String getHorseTexture() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }

        return this.texturePrefix;
    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }

        return this.horseTexturesArray;
    }




}
