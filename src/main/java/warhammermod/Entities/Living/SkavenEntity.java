package warhammermod.Entities.Living;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;
import warhammermod.Entities.Living.Aimanager.RangedSkavenAttackGoal;
import warhammermod.Entities.Projectile.StoneEntity;
import warhammermod.Entities.Projectile.WarpBulletEntity;
import warhammermod.Items.Ranged.RatlingGun;
import warhammermod.Items.Ranged.SlingTemplate;
import warhammermod.Items.Ranged.WarpgunTemplate;
import warhammermod.utils.functions;
import warhammermod.utils.inithandler.Entityinit;
import warhammermod.utils.inithandler.IReloadItem;
import warhammermod.utils.inithandler.ItemsInit;
import warhammermod.utils.inithandler.WarhammermodRegistry;

import javax.annotation.Nullable;
import java.util.*;

import static warhammermod.utils.reference.*;


public class SkavenEntity extends MonsterEntity implements IRangedAttackMob {

    //useless but to remove annoying error message
    public SkavenEntity(World world){
        super(Entityinit.SKAVEN,world);
    }
    /**
     * Goals
     */
    private final RangedSkavenAttackGoal<SkavenEntity> bowGoal = new RangedSkavenAttackGoal<>(this, 1.0D, 20, 15.0F);
    private final RangedAttackGoal aiRangedPotion = new RangedAttackGoal(this,1,55,10F);
    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
        public void stop() {
            super.stop();
            setAggressive(false);
        }

        public void start() {
            super.start();
            setAggressive(true);
        }
    };

    public void reassessWeaponGoal() {
        if (this.level != null && !this.level.isClientSide) {
            this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.removeGoal(this.bowGoal);
            this.goalSelector.removeGoal(this.aiRangedPotion);
            ItemStack item = this.getMainHandItem();
            int i = firerate.get(getSkavenTypePosition());
            if (item.getItem() instanceof WarpgunTemplate || item.getItem() instanceof RatlingGun || item.getItem() instanceof SlingTemplate) {
                if (this.level.getDifficulty() != Difficulty.HARD) {
                    i *= 1.5;
                }

                this.bowGoal.setMinAttackInterval(i);
                this.goalSelector.addGoal(4, this.bowGoal);
            } else if(getSkaventype().equals(globadier)){
                this.goalSelector.addGoal(4,aiRangedPotion);
            }

            else{
                this.goalSelector.addGoal(4, this.meleeGoal);
            }

        }
    }

    protected void registerGoals() {

        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, CatEntity.class, 6.0F, 1.0D, 1.2D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, SkavenEntity.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));

    }
    /**
     * handle all of skaventypes
     *
     */
    public static final DataParameter<String> SkavenType = EntityDataManager.<String>defineId(SkavenEntity.class, DataSerializers.STRING);
    private static ArrayList<String> Types = new ArrayList<String>(Arrays.asList(slave,clanrat,stormvermin,gutter_runner,globadier,ratling_gunner));
    private ArrayList<Float> SkavenSize = new ArrayList<Float>(Arrays.asList(1F,(1.7F/1.6F),(1.8F/1.6F),(1.7F/1.6F),(1.7F/1.6F),(1.7F/1.6F)));
    private ArrayList<Integer> Spawnchance = new ArrayList<Integer>(Arrays.asList(38,27,15,7,4,4));//3
    private ArrayList<Float> reinforcementchance = new ArrayList<Float>(Arrays.asList(0.08F,0.1F,0.14F,0F,0.08F,0.11F));
    private ArrayList<Integer> firerate = new ArrayList<Integer>(Arrays.asList(28,38,0,0,55,6));
    public static ItemStack[][][] SkavenEquipment =  {{{functions.getRandomspear(4)},{new ItemStack(ItemsInit.Sling)}},
            {{functions.getRandomsword(4),new ItemStack(ItemsInit.Skaven_shield)},{new ItemStack(ItemsInit.Warplock_jezzail)}},
            {{functions.getRandomHalberd(4)},{functions.getRandomsword(5),new ItemStack(ItemsInit.Skaven_shield)}},
            {{new ItemStack(ItemsInit.iron_dagger),new ItemStack(ItemsInit.iron_dagger)}},
            {{}},
            {{new ItemStack(ItemsInit.RatlingGun)}},
    };
    private static int Skaven_ranged_dropchance = 100;
    public static void setSkaven_ranged_dropchance(int skaven_ranged_dropchance) {
        Skaven_ranged_dropchance = skaven_ranged_dropchance;
    }
    public static int getSkaven_ranged_dropchance() {
        return Skaven_ranged_dropchance;
    }




    public String getSkaventype(){
        return entityData.get(SkavenType);
    }
    public int getSkavenTypePosition(){
        int typepos = Math.max(Types.indexOf(entityData.get(SkavenType)),0);
        return typepos;
    }
    public float getSkavenSize(){
        int typepos = Math.max(Types.indexOf(getSkaventype()),0);
        return SkavenSize.get(typepos);
    }
    public EntitySize getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(getSkavenSize());
    }

    private void updateSkavenSize(){
        this.refreshDimensions();
        handleAttributes();
    }

    private void setSkavenType(String type){
        this.entityData.set(SkavenType,type);
    }

    private String getrandomtype(){
        int total=0;
        for(int x:Spawnchance){
            total+=x;
        }
        int perc = random.nextInt(total);
        int chance=0;
        for(int x=0;x<Spawnchance.size();x++){
            chance+=Spawnchance.get(x);
            if(perc<chance){
                return Types.get(x);
            }
        }
        return Types.get(0);
    }

    /**
     * initializing
     */
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SkavenType, Types.get(0));
    }


    private boolean fixgame; //skaven task dont work without was for 1.15 to test if still necessary

    public SkavenEntity(EntityType<? extends SkavenEntity> p_i48555_1_, World p_i48555_2_) {
        super(p_i48555_1_, p_i48555_2_);
        this.reassessWeaponGoal();
        fixgame=true;
    }
    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.finalizeSpawn(world, difficultyIn, reason, spawnDataIn, dataTag);
        setSkavenType(getrandomtype());
        this.populateDefaultEquipmentSlots(difficultyIn);
        this.populateDefaultEquipmentEnchantments(difficultyIn);
        this.reassessWeaponGoal();
        this.handleAttributes();
        return spawnDataIn;
    }


    public void aiStep() {
        if(fixgame){
            reassessWeaponGoal();
            this.handleAttributes();
            fixgame=false;
        }
        super.aiStep();
    }

        /**
         * attributes
         */
    public void updateSkavenAttributes(double speed,double health,double armor,double AD){
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speed);
        this.getAttribute(Attributes.ARMOR).setBaseValue(armor);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(AD);
    }
    public void handleAttributes(){
        double speed = 0.55;
        double max_health = 18;
        double armor=0;
        double AD=1.5;

        if(getSkaventype().equals(slave)){
            max_health=14;
        }
        else if(getSkaventype().equals(clanrat)){
            AD=2;
            max_health=16;
            armor=2;

        }
        else if(getSkaventype().equals(stormvermin)){
            AD=3;
            max_health=20;
            armor=5;
        }else if(getSkaventype().equals(gutter_runner)){
            AD=5;
            max_health=17;
            speed=0.75;
        }
        updateSkavenAttributes(speed,max_health,armor,AD);
    };

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMonsterAttributes();
    }
    /**
     * behaviour
     */
    public boolean isAffectedByPotions() {
        return !getSkaventype().equals(globadier);
    }

    public void performRangedAttack(LivingEntity target, float p_82196_2_) {
        Item item = this.getMainHandItem().getItem();
        if(item instanceof WarpgunTemplate){
            attackEntitywithrifle(target,7,11);
        }
        else if(item instanceof RatlingGun){
            attackEntitywithrifle(target,5,26);
        }
        else if(item instanceof SlingTemplate){
            attackEntitywithstone(target,15);
        }
        else if(getSkaventype().equals(globadier)){
            attackpotions(target);
        }
    }
    private void attackpotions(LivingEntity target){


        Vector3d vec3d = target.getDeltaMovement();
        double d0 = target.getX() + vec3d.x - this.getX();
        double d1 = target.getEyeY() - (double)1.1F - this.getY();
        double d2 = target.getZ() + vec3d.z - this.getZ();
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2);
        Potion potion = random.nextBoolean() ? Potions.HARMING : Potions.POISON;

        PotionEntity potionentity = new PotionEntity(this.level, this);
        potionentity.setItem(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potion));
        potionentity.xRot -= -20.0F;
        potionentity.shoot(d0, d1 + (double)(f * 0.2F), d2, 0.75F, 8.0F);
        this.level.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_THROW, this.getSoundSource(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
        this.level.addFreshEntity(potionentity);

    }
    private void attackEntitywithrifle(LivingEntity target, int damage, float inaccuracy){
        WarpBulletEntity bullet = new WarpBulletEntity(this,level, damage);
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS,this);
        if (j > 0) {
            bullet.setpowerDamage(j);
        }
        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH_ARROWS, this) + 1;
        if (k > 0) {
            bullet.setknockbacklevel(k);
        }
        double d0 = target.getX()  - this.getX();
        double d1 = target.getY(0.3333333333333333D) - bullet.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        bullet.shoot(d0,d1+d3*0.06,d2,3,(float)(inaccuracy - this.level.getDifficulty().getId() * 4));//inac14
        this.playSound( SoundEvents.GENERIC_EXPLODE, 1.0F, 2F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
        this.level.addFreshEntity(bullet);

    }
    private void attackEntitywithstone(LivingEntity target, float inaccuracy){
        StoneEntity stone = new StoneEntity(this,level,3);

        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS, this);
        if (j > 0) {
            stone.setTotaldamage(stone.projectiledamage + (float)j * 0.5F + 0.5F);
        }
        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH_ARROWS, this) + 1;
        if (k > 0) {
            stone.setknockbacklevel(k);
        }


        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, this) > 0) {
            stone.setSecondsOnFire(100);
        }
        double d0 = target.getX()  - this.getX();
        double d1 = target.getY(0.3333333333333333D) - stone.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        stone.shoot(d0,d1+d3*0.2,d2,1.3F,(float)(inaccuracy - this.level.getDifficulty().getId() * 4));
        this.playSound( SoundEvents.ARROW_SHOOT, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));
        this.level.addFreshEntity(stone);

    }

    public boolean hurt(DamageSource source, float amount){
        if (!super.hurt(source, amount)) {
            return false;
        } else if (!(this.level instanceof ServerWorld)) {
            return false;
        } else {
            ServerWorld serverworld = (ServerWorld)this.level;
            LivingEntity livingentity = this.getTarget();
            if (livingentity == null && source.getEntity() instanceof LivingEntity) {
                livingentity = (LivingEntity)source.getEntity();
            }

            int i = MathHelper.floor(this.getX());
            int j = MathHelper.floor(this.getY());
            int k = MathHelper.floor(this.getZ());


            if (livingentity != null && this.level.getDifficulty() == Difficulty.HARD && (double)this.random.nextFloat() < reinforcementchance.get(getSkavenTypePosition())/2 && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                SkavenEntity skavenEntity = Entityinit.SKAVEN.create(this.level);

                for(int l = 0; l < 50; ++l) {
                    int i1 = i + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
                    int j1 = j + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
                    int k1 = k + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
                    BlockPos blockpos = new BlockPos(i1, j1, k1);
                    EntityType<?> entitytype = skavenEntity.getType();
                    EntitySpawnPlacementRegistry.PlacementType placementType = EntitySpawnPlacementRegistry.getPlacementType(entitytype);
                    if (WorldEntitySpawner.isSpawnPositionOk(placementType, this.level, blockpos, entitytype) && EntitySpawnPlacementRegistry.checkSpawnRules(entitytype, serverworld, SpawnReason.REINFORCEMENT, blockpos, this.level.random)) {
                        skavenEntity.setPos((double)i1, (double)j1, (double)k1);
                        if (!this.level.hasNearbyAlivePlayer((double)i1, (double)j1, (double)k1, 7.0D) && this.level.isUnobstructed(skavenEntity) && this.level.noCollision(skavenEntity) && !this.level.containsAnyLiquid(skavenEntity.getBoundingBox())) {

                            skavenEntity.setTarget(livingentity);
                            skavenEntity.finalizeSpawn(serverworld, this.level.getCurrentDifficultyAt(skavenEntity.blockPosition()), SpawnReason.REINFORCEMENT, (ILivingEntityData)null, (CompoundNBT)null);
                            serverworld.addFreshEntityWithPassengers(skavenEntity);
                            break;
                        }
                    }
                }
            }

            return true;
        }
    }
    /**
     * other
     */
    protected SoundEvent getAmbientSound() {
        return WarhammermodRegistry.ratambient;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return WarhammermodRegistry.rathurt;
    }

    protected SoundEvent getDeathSound() {
        return WarhammermodRegistry.ratdeath;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ZOMBIE_STEP;
    }
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }
    protected float getVoicePitch()
    {
        return 0.4F +this.random.nextFloat()*0.5F;
    }

    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty)
    {
        int typepos = Math.max(Types.indexOf(getSkaventype()),0);
        int weapontype;
        if(SkavenEquipment[typepos].length<2){
            weapontype = 0;
        }
        else {
            weapontype = this.random.nextInt(2);
        }
        if(SkavenEquipment[typepos][weapontype].length>0){
            this.setItemSlot(EquipmentSlotType.MAINHAND,SkavenEquipment[typepos][weapontype][0]);
            if(SkavenEquipment[typepos][weapontype].length>1){
                this.setItemSlot(EquipmentSlotType.OFFHAND,SkavenEquipment[typepos][weapontype][1]);
            }
        }
    }

    protected void populateDefaultEquipmentEnchantments(DifficultyInstance difficulty) {
        float f = difficulty.getSpecialMultiplier();
        this.enchantSpawnedWeapon(f);
    }

    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putString("SkavenType", this.getSkaventype());

    }

    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        setSkavenType(compound.getString("SkavenType"));
    }

    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (SkavenType.equals(key)) {
            this.updateSkavenSize();
        }

        super.onSyncedDataUpdated(key);
    }

    public static boolean checkSkavenSpawnRules(EntityType<SkavenEntity> entityType, IServerWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (world.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(world, pos, random) && checkMobSpawnRules(entityType, world, spawnReason, pos, random) && !spawnReason.equals(SpawnReason.REINFORCEMENT)) {
            return Objects.equals(world.getBiomeName(pos), Optional.of(Biomes.MOUNTAINS)) || pos.getY() < 35;
        }
        return false;
    }

    protected float getEquipmentDropChance(EquipmentSlotType p_205712_1_) {
        float f;
        switch(p_205712_1_.getType()) {
            case HAND:
                f = this.handDropChances[p_205712_1_.getIndex()];
                if(this.getMainHandItem().getItem() instanceof IReloadItem)f=2;
                break;
            case ARMOR:
                f = this.armorDropChances[p_205712_1_.getIndex()];
                break;
            default:
                f = 0.0F;
        }

        return f;
    }

    @Override
    public boolean isBlocking() {
        if(this.getOffhandItem().getItem() instanceof ShieldItem && getTarget()!=null && this.random.nextFloat()<0.25){
            playSound(SoundEvents.SHIELD_BLOCK,1,1);
            return true;
        }
        else return false;
    }
}
