package warhammermod.Entities.Living;

import net.minecraft.block.Blocks;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.passive.horse.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import warhammermod.main;
import warhammermod.utils.functions;
import warhammermod.utils.inithandler.Entityinit;


//eytra up too fast
public class PegasusEntity extends HorseEntity {

    //useless but to remove annoying error message
    public PegasusEntity(World world){
        super(EntityType.HORSE,world);
    }


    public PegasusEntity(EntityType<? extends HorseEntity> p_i50238_1_, World p_i50238_2_) {
        super(p_i50238_1_, p_i50238_2_);
    }


    /**
     * all of this crap just to have special colored pegasuses
     */
    private static final DataParameter<Boolean> MixBlood = EntityDataManager.defineId(PegasusEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Integer> Texture_parameter = EntityDataManager.defineId(PegasusEntity.class, DataSerializers.INT);


    private static final String[] HORSE_TEXTURES = new String[]{"warhammermod:textures/entity/pegasus/pegasus.png", "warhammermod:textures/entity/PEGASUS/pegasus_creamy.png", "warhammermod:textures/entity/PEGASUS/pegasus_chestnut.png", "warhammermod:textures/entity/PEGASUS/pegasus_brown.png", "warhammermod:textures/entity/PEGASUS/pegasus_black.png", "warhammermod:textures/entity/PEGASUS/pegasus_gray.png", "warhammermod:textures/entity/PEGASUS/pegasus_darkbrown.png"};
    private static final String[] HORSE_TEXTURES_ABBR = new String[]{"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
    private static final String[] HORSE_MARKING_TEXTURES = new String[]{null, "warhammermod:textures/entity/pegasus/pegasus_markings_white.png", "warhammermod:textures/entity/PEGASUS/pegasus_markings_whitefield.png", "warhammermod:textures/entity/PEGASUS/pegasus_markings_whitedots.png", "warhammermod:textures/entity/PEGASUS/pegasus_markings_blackdots.png"};
    private static final String[] HORSE_MARKING_TEXTURES_ABBR = new String[]{"", "wo_", "wmo", "wdo", "bdo"};


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MixBlood, false);
        this.entityData.define(Texture_parameter, 0);
    }

    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putInt("Variant", this.getTypeVariant());
        if (!this.inventory.getItem(1).isEmpty()) {
            p_213281_1_.put("ArmorItem", this.inventory.getItem(1).save(new CompoundNBT()));
        }

    }

    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        this.setTypeVariant(p_70037_1_.getInt("Variant"));
        if (p_70037_1_.contains("ArmorItem", 10)) {
            ItemStack itemstack = ItemStack.of(p_70037_1_.getCompound("ArmorItem"));
            if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
                this.inventory.setItem(1, itemstack);
            }
        }
        this.updateContainerEquipment();
    }


    private void setTypeVariant(int p_234242_1_) {
        this.entityData.set(Texture_parameter, p_234242_1_);
    }

    private int getTypeVariant() {
        return this.entityData.get(Texture_parameter);
    }

    private void setVariantAndMarkings(CoatColors p_234238_1_, CoatTypes p_234238_2_) {
        this.setTypeVariant(p_234238_1_.getId() & 255 | p_234238_2_.getId() << 8 & '\uff00');
    }

    public CoatColors getVariant() {
        return CoatColors.byId(this.getTypeVariant() & 255);
    }

    public CoatTypes getMarkings() {
        return CoatTypes.byId((this.getTypeVariant() & '\uff00') >> 8);
    }

    public void setMixedblood(Boolean mixblood) {
        this.entityData.set(MixBlood, mixblood);
    }
    public Boolean ismixblood() {
        return this.entityData.get(MixBlood);
    }

    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity partner) {
        AbstractHorseEntity pegasus=null;

        if (partner instanceof DonkeyEntity) {
            pegasus = EntityType.MULE.create(world);
        }

        else if (partner instanceof PegasusEntity) {
            pegasus = Entityinit.PEGASUS.create(world);
            ((PegasusEntity) pegasus).setVariantAndMarkings(CoatColors.WHITE, CoatTypes.NONE);
        }

        else if (random.nextFloat() < 0.1F) {
            HorseEntity mating_partner = (HorseEntity) partner;
            pegasus = Entityinit.PEGASUS.create(world);
            ((PegasusEntity) pegasus).setMixedblood(true);

            int j = this.random.nextInt(9);
            CoatColors coatcolors;
            if (j < 4) {
                ((PegasusEntity) pegasus).setMixedblood(false);
                coatcolors=CoatColors.WHITE;
            }
            else if (j < 8) {
                coatcolors = mating_partner.getVariant();
            } else {
                coatcolors = Util.getRandom(CoatColors.values(), this.random);
            }

            int k = this.random.nextInt(5);
            CoatTypes coattypes;
            if(k<2){
                coattypes=CoatTypes.NONE;
            }
            else if (k < 4) {
                coattypes = mating_partner.getMarkings();
            } else {
                coattypes = Util.getRandom(CoatTypes.values(), this.random);
            }

            ((PegasusEntity) pegasus).setVariantAndMarkings(coatcolors, coattypes);
        } else
        {
            partner.getBreedOffspring(world, partner);
        }
        if (pegasus != null) {
            this.setOffspringAttributes(partner, pegasus);
        }

        return pegasus;
    }

    /**
     * fun part of moving the horse
     */

    private int jumpcounter;
    private boolean lastpressjump;
    private int Timer;

    private boolean iselytrafly = false;
    private boolean isstationaryflying = false;
    public boolean flydownsafely = false;

    public boolean isIselytrafly() {
        return iselytrafly;
    }

    public boolean isIsstationaryflying() {
        return isstationaryflying;
    }

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

    public Boolean isgrounded(){
        BlockPos blockpos = this.getBlockPosBelowThatAffectsMyMovement();
        return!(this.level.getBlockState(blockpos).getBlock()== Blocks.AIR);
    }

    public boolean isFlying(){
        return iselytrafly ||isstationaryflying;
    }



    public void travel(Vector3d p_213352_1_) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.canBeControlledByRider() && this.isSaddled()) {

                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
                if (this.level.isClientSide()) {
                    ClientPlayerEntity player = (ClientPlayerEntity) livingentity;
                    boolean isjumping = player.input.jumping;
                    if (isjumping) {
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

                if(!isFlying()) {
                    {
                        super.travel(p_213352_1_);
                        return;
                    }
                }

                this.yRot = livingentity.yRot;
                this.yRotO = this.yRot;
                this.xRot = livingentity.xRot * 0.5F;
                this.setRot(this.yRot, this.xRot);
                this.yBodyRot = this.yRot;
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;

                if (isstationaryflying || (iselytrafly && livingentity.xRot < 0)) {
                    if (Timer > 25) {
                        this.playSound(SoundEvents.ENDER_DRAGON_FLAP, 0.65F, 3F);
                        Timer = 0;
                    } else Timer++;
                }
                if (isgrounded() || this.onGround) {
                    setFlyFalse();
                }
                if (iselytrafly && level.isClientSide()) {
                    elytramovement();
                }
                else if (isstationaryflying && level.isClientSide()) {
                    stationnarymovement(livingentity,f,p_213352_1_.y,f1);
                }
            }
            else {
                setFlyFalse();
                if(fallDistance>5){
                    flydownsafely();
                }else
                super.travel(p_213352_1_);
            }

        }
    }






    public void elytramovement() {
        Vector3d vector3d = this.getDeltaMovement();
        if (vector3d.y > -0.5D) {
            this.fallDistance = 1.0F;
        }
        this.fallDistance = 1.0F;
        Vector3d vec3d = this.getControllingPassenger().getLookAngle();
        float f6 = this.xRot/0.5F * ((float) Math.PI / 180F); //this is the fix to have the elytra behave normally
        double dir = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
        double speed = Math.sqrt(getHorizontalDistanceSqr(vector3d)); //x^2+z^2 pos
        double d12 = vec3d.length();
        float f3 = MathHelper.cos(f6);
        f3 = (float) ((double) f3 * (double) f3 * Math.min(1.0D, d12 / 0.4D)); //f3^2 * min 1 ,
        ModifiableAttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
        double d0 = gravity.getValue();
        vector3d = this.getDeltaMovement().add(0.0D, d0 * (-1.0D + (double) f3 * 0.75D), 0.0D);

        if (vector3d.y < 0.0D && dir > 0.0D) {// descend x y z +
            double d3 = vector3d.y * -0.1D * (double) f3; // +
            vector3d = vector3d.add(vec3d.x * d3 / dir, d3, vec3d.z * d3 / dir);
        }


        //  0.45*getCustomJump()()*-MathHelper.sin(f);
        if (f6 < 0.0F && dir > 0.0D) { // monte x z - y
            double d13 = speed * (double) (-MathHelper.sin(f6)) * 0.04D; // +
            if (speed < 0.6) {
                vector3d = vector3d.add(vec3d.x * 0.025, 0, vec3d.z * 0.025);
            }
            else
                vector3d = vector3d.add(-vec3d.x * d13 / dir, d13 * 3.2D, -vec3d.z * d13 / dir);  //normal behaviour
            if (vector3d.y < -0.05) {
                vector3d = vector3d.add(0, getCustomJump()*0.6, 0);
            }

        }

        if (dir > 0.0D) {
            vector3d = vector3d.add((vec3d.x / dir * speed - vector3d.x) * 0.1D, 0.0D, (vec3d.z / dir * speed - vector3d.z) * 0.1D);
        }

        this.setDeltaMovement(vector3d.multiply(0.99F, 0.98F, 0.99F));
        this.move(MoverType.SELF, this.getDeltaMovement());
        if (this.horizontalCollision && !this.level.isClientSide()) {
            double d14 = Math.sqrt(getHorizontalDistanceSqr(this.getDeltaMovement()));
            double d4 = speed - d14;
            float f4 = (float) (d4 * 10.0D - 3.0D);
            if (f4 > 0.0F) {
                this.playSound(this.getFallDamageSound((int) f4), 1.0F, 1.0F);
                this.hurt(DamageSource.FLY_INTO_WALL, f4);
            }
        }
    }

    public void stationnarymovement( LivingEntity livingentity,  float f,double y, float f1){
        ClientPlayerEntity player = (ClientPlayerEntity) livingentity;
        double flap = Math.max(Math.abs(Math.cos(Math.PI * this.tickCount / 25)), 0.5) * 0.4;
        if (player.input.jumping) {
            setDeltaMovement(getDeltaMovement().x, flap * getCustomJump()*1.5, getDeltaMovement().z);
        } else if (main.horsedown.isDown()) {
            setDeltaMovement(getDeltaMovement().x, -flap * getCustomJump(), getDeltaMovement().z);
        } else setDeltaMovement(getDeltaMovement().x, (flap - 0.32) * 0.08, getDeltaMovement().z);

        this.setSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
        this.fallDistance = 0;
        movehorse(new Vector3d((double) f * 0.5, y, (double) f1 * 0.5));
    }

    private void movehorse(Vector3d vec){
        float f3 = this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getSlipperiness(level, this.getBlockPosBelowThatAffectsMyMovement(), this);
        this.moveRelative(0.015F, vec);
        this.setDeltaMovement(this.getDeltaMovement());
        this.move(MoverType.SELF, this.getDeltaMovement());
        //Vector3d vector3d5 = this.handleRelativeFrictionAndCalculateMovement(vec, f3);
        //this.setDeltaMovement(vector3d5.x * 0.9, vector3d5.y, vector3d5.z * 0.9);
    }
//not working doesnt have lookangle
    private void flydownsafely(){


            Vector3d vec3d = new Vector3d(this.getLookAngle().x,-0.2,this.getLookAngle().z);
            Vector3d motionvec = this.getDeltaMovement();
            float f = 20 * 0.017453292F;
            double d1 = vec3d.length();
            float f4 = MathHelper.cos(f);
            f4 = (float)((double)f4 * (double)f4 * Math.min(1.0D, d1 / 0.4D));
            if(motionvec.y<-0.745) {
                ;
                motionvec = this.getDeltaMovement().add(0.0D, -0.08D + (double) f4 * 0.06D, 0.0D);
            }

            this.setDeltaMovement(motionvec.multiply((double) 1F, (double) 0.98F, (double) 1F));
            this.move(MoverType.SELF, this.getDeltaMovement());

    }

    /**
     * because MC falldamage is stupid
     * 5blocks - -0.745
     * 10 -0.964
     * 15 -1.143
     * 20 -1.26
     * 100 -1.85
     *
     * polynomial approach doesnt work because server doesnt have speed
     */

    public boolean causeFallDamage(float Falldistance, float modifier) {
        return super.causeFallDamage(Falldistance,modifier);
    }

    protected int calculateFallDamage(float Falldistance, float modifier) {
        System.out.println(fallDistance);
        return 0;
    }



}
