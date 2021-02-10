package warhammermod.Entities.living;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import warhammermod.Entities.StoneEntity;
import warhammermod.Entities.WarpBulletEntity;
import warhammermod.Entities.living.Aimanager.RangedSkavenGoal;
import warhammermod.Items.Ranged.RatlingGun;
import warhammermod.Items.Ranged.Slingtemplate;
import warhammermod.Items.Ranged.WarpgunTemplate;
import warhammermod.util.Handler.Entityinit;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.Handler.WarhammermodRegistry;
import warhammermod.util.utils.utils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static warhammermod.util.reference.*;


public class SkavenEntity extends MonsterEntity implements IRangedAttackMob {


    private final RangedSkavenGoal aiRangedAttack = new RangedSkavenGoal<>(this, 1.0D, 20, 15.0F);
    private final RangedAttackGoal aiRangedPotion = new RangedAttackGoal(this,1,55,10F);
    private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false)
    {

        public void resetTask() {
            super.resetTask();
            setAggroed(false);
        }


        public void startExecuting() {
            super.startExecuting();
            setAggroed(true);
        }
    };

    public static final DataParameter<String> SkavenType = EntityDataManager.<String>createKey(SkavenEntity.class, DataSerializers.STRING);
    private static ArrayList<String> Types = new ArrayList<String>(Arrays.asList(slave,clanrat,stormvermin,gutter_runner,globadier,ratling_gunner));
    private ArrayList<Float> SkavenSize = new ArrayList<Float>(Arrays.asList(1F,(1.7F/1.6F),(1.8F/1.6F),(1.7F/1.6F),(1.7F/1.6F),(1.7F/1.6F)));
    private ArrayList<Integer> Spawnchance = new ArrayList<Integer>(Arrays.asList(38,27,15,7,4,4));//3
    private ArrayList<Float> reinforcementchance = new ArrayList<Float>(Arrays.asList(0.08F,0.1F,0.14F,0F,0.08F,0.11F));
    private ArrayList<Integer> firerate = new ArrayList<Integer>(Arrays.asList(28,38,0,0,55,6));
    public static ItemStack[][][] SkavenEquipment =  {{{utils.getRandomspear(4)},{new ItemStack(ItemsInit.Sling)}},
                                            {{utils.getRandomsword(4),new ItemStack(ItemsInit.Skaven_shield)},{new ItemStack(ItemsInit.Warplock_jezzail)}},
                                            {{utils.getRandomHalberd(4)},{utils.getRandomsword(5),new ItemStack(ItemsInit.Skaven_shield)}},
                                            {{new ItemStack(ItemsInit.iron_knife),new ItemStack(ItemsInit.iron_knife)}},
                                            {{}},
                                            {{new ItemStack(ItemsInit.RatlingGun)}},
    };
    private boolean fixgame;

    public SkavenEntity(EntityType<? extends SkavenEntity> p_i50194_1_, World p_i50194_2_) {
        super(p_i50194_1_, p_i50194_2_);
        fixgame=true;

    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SkavenType,Types.get(0));

    }

    private void setSkavenType(String type){
        this.dataManager.set(SkavenType,type);
    }

    private String getrandomtype(){
        int total=0;
        for(int x:Spawnchance){
            total+=x;
        }
        int perc = rand.nextInt(total);
        int chance=0;
        for(int x=0;x<Spawnchance.size();x++){
            chance+=Spawnchance.get(x);
            if(perc<chance){
                return Types.get(x);
            }
        }
        return Types.get(0);
    }

    public String getSkaventype(){
        return dataManager.get(SkavenType);
    }


    protected void registerGoals() {

        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, CatEntity.class, 6.0F, 1.0D, 1.2D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this,SkavenEntity.class).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));

    }


    //protected ResourceLocation getLootTable() {return Loottable.Skaven;}

    //private ResourceLocation getloottablefromtype(){ }


    protected void registerTypesattributes(){

        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.35F);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);


        if(getSkaventype().equals(slave)){
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D);
        }
        else if(getSkaventype().equals(clanrat)){
            this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.5D);
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
            this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        }
        else if(getSkaventype().equals(stormvermin)){
            this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
            this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5D);//5
        }else if(getSkaventype().equals(gutter_runner)){
            this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.5D);
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(17.0D);
            this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
        }
    };

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        setSkavenType(getrandomtype());
        this.setEquipmentBasedOnDifficulty(difficultyIn);
        this.setEnchantmentBasedOnDifficulty(difficultyIn);
        this.setCombatTask();
        this.registerTypesattributes();
        return spawnDataIn;
    }



    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        int typepos = Math.max(Types.indexOf(getSkaventype()),0);
        int weapontype;
        if(SkavenEquipment[typepos].length<2){
            weapontype = 0;
        }
        else {
            weapontype = this.rand.nextInt(2);
        }
        if(SkavenEquipment[typepos][weapontype].length>0){
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND,SkavenEquipment[typepos][weapontype][0]);
        if(SkavenEquipment[typepos][weapontype].length>1){
            this.setItemStackToSlot(EquipmentSlotType.OFFHAND,SkavenEquipment[typepos][weapontype][1]);
        }
        }

    }


    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("SkavenType", this.getSkaventype());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setSkavenType(compound.getString("SkavenType"));
        }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (SkavenType.equals(key)) {
            this.updateSkavenSize();
        }

        super.notifyDataManagerChange(key);
    }

    private void updateSkavenSize(){
        this.recalculateSize();
        if(getSkaventype().equals(slave)){
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D);
        }
        else if(getSkaventype().equals(clanrat)){
            this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.5D);
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
            this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        }
        else if(getSkaventype().equals(stormvermin)){
            this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
            this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5D);
        }else if(getSkaventype().equals(gutter_runner)){
            this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);
            this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
        }

    }




    public void livingTick(){
        if(fixgame){
            setCombatTask();
            fixgame=false;
        }
        super.livingTick();}

    protected SoundEvent getAmbientSound()
    {
        return WarhammermodRegistry.ratambient;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return WarhammermodRegistry.rathurt;
    }

    protected SoundEvent getDeathSound()
    {
        return WarhammermodRegistry.ratdeath;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected float getSoundPitch()
    {
        return 0.4F +this.rand.nextFloat()*0.5F;
    }//(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F


    public float getSkavenSize(){

       int typepos = Math.max(Types.indexOf(getSkaventype()),0);
       return SkavenSize.get(typepos);
    }

    public EntitySize getSize(Pose poseIn) {
        return super.getSize(poseIn).scale(getSkavenSize());
    }

    public int getSkavenTypePosition(){
        int typepos = Math.max(Types.indexOf(dataManager.get(SkavenType)),0);
        return typepos;
    }




    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor)
    {


        if(this.getHeldItemMainhand().getItem() instanceof WarpgunTemplate){
            attackEntitywithrifle(target,7,11);
        }
        else if(this.getHeldItemMainhand().getItem() instanceof RatlingGun){
            attackEntitywithrifle(target,5,26);
        }
        else if(this.getHeldItemMainhand().getItem() instanceof Slingtemplate){
            attackEntitywithstone(target,15);
        }
        else if(getSkaventype().equals(globadier)){
            attackpotions(target);
        }
    }
    private void attackpotions(LivingEntity target){

        Vec3d vec3d = target.getMotion();
        double d0 = target.getPosX() + vec3d.x - this.getPosX();
        double d1 = target.getPosYEye() - (double)1.1F - this.getPosY();
        double d2 = target.getPosZ() + vec3d.z - this.getPosZ();
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2);
        Potion potion = rand.nextBoolean() ? Potions.HARMING : Potions.POISON;

        PotionEntity potionentity = new PotionEntity(this.world, this);
        potionentity.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), potion));
        potionentity.rotationPitch -= -20.0F;
        potionentity.shoot(d0, d1 + (double)(f * 0.2F), d2, 0.75F, 8.0F);
        this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
        this.world.addEntity(potionentity);

    }
    private void attackEntitywithrifle(LivingEntity target,int damage, float inaccuracy){
        WarpBulletEntity bullet = new WarpBulletEntity(world,this,this.activeItemStack, damage);//7
        int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER,this);
        if (j > 0) {
            bullet.setpowerDamage(j);
        }
        int k = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, this) + 1;
        if (k > 0) {
            bullet.setknockbacklevel(k);
        }
        double d0 = target.getPosX()  - this.getPosX();
        double d1 = target.getPosYHeight(0.3333333333333333D) - bullet.getPosY();
        double d2 = target.getPosZ() - this.getPosZ();
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        bullet.shoot(d0,d1+d3*0.06,d2,3,(float)(inaccuracy - this.world.getDifficulty().getId() * 4));//inac14
        this.playSound( SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 2F / (rand.nextFloat() * 0.4F + 1.2F) + 0.5F);
        this.world.addEntity(bullet);

    }
    private void attackEntitywithstone(LivingEntity target, float inaccuracy){
        StoneEntity stone = new StoneEntity(world,this,this.activeItemStack,3);

        int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, this);
        if (j > 0) {
            stone.setDamage(stone.getDamage() + (double)j * 0.5D + 0.5D);
        }
        int k = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, this) + 1;
        if (k > 0) {
            stone.setKnockbackStrength(k);
        }

        stone.pickupStatus = ArrowEntity.PickupStatus.DISALLOWED;

        if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, this) > 0) {
            stone.setFire(100);
        }
        double d0 = target.getPosX()  - this.getPosX();
        double d1 = target.getPosYHeight(0.3333333333333333D) - stone.getPosY();
        double d2 = target.getPosZ() - this.getPosZ();
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        stone.shoot(d0,d1+d3*0.2,d2,1.3F,(float)(inaccuracy - this.world.getDifficulty().getId() * 4));
        this.playSound( SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 1.2F));
        this.world.addEntity(stone);

    }
/*


*/
    public void setCombatTask()
    {

        if (this.world != null && !this.world.isRemote) {
            this.goalSelector.removeGoal(this.aiAttackOnCollide);
            this.goalSelector.removeGoal(this.aiRangedAttack);
            this.goalSelector.removeGoal(this.aiRangedPotion);
            ItemStack item = this.getHeldItemMainhand();
            int i = firerate.get(getSkavenTypePosition());
            if (this.world.getDifficulty() != Difficulty.HARD) {
                i *= 1.5;
            }
            if (item.getItem() instanceof WarpgunTemplate || item.getItem() instanceof RatlingGun || item.getItem() instanceof Slingtemplate)
             {
                this.aiRangedAttack.setAttackCooldown(i);
                this.goalSelector.addGoal(4, this.aiRangedAttack);
            } else if(getSkaventype().equals(globadier)){
                this.goalSelector.addGoal(4,aiRangedPotion);
            } else {
                this.goalSelector.addGoal(4, this.aiAttackOnCollide);
            }

        }
    }
    public boolean canBeHitWithPotion()
    {
        return !getSkaventype().equals(globadier);
    }


    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        System.out.println(this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getValue());
        if (super.attackEntityFrom(source, amount))
        {
            LivingEntity entitylivingbase = this.getAttackTarget();

            if (entitylivingbase == null && source.getTrueSource() instanceof LivingEntity)
            {
                entitylivingbase = (LivingEntity) source.getTrueSource();
            }
            int i = MathHelper.floor(this.getPosX());
            int j = MathHelper.floor(this.getPosY());
            int k = MathHelper.floor(this.getPosZ());
            if(entitylivingbase instanceof PlayerEntity && ((PlayerEntity) entitylivingbase).isCreative()){
                return true;
            }
            if (entitylivingbase != null && this.world.getDifficulty() == Difficulty.HARD && (double)this.rand.nextFloat() < reinforcementchance.get(getSkavenTypePosition())/2 && this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING))
            {
                SkavenEntity skaven = Entityinit.SKAVEN.create(world);

                for (int l = 0; l < 50; ++l)
                {
                    int i1 = i + MathHelper.nextInt(this.rand, 7, 20) * MathHelper.nextInt(this.rand, -1, 1);
                    int j1 = j + MathHelper.nextInt(this.rand, 7, 20) * MathHelper.nextInt(this.rand, -1, 1);
                    int k1 = k + MathHelper.nextInt(this.rand, 7, 20) * MathHelper.nextInt(this.rand, -1, 1);

                    BlockPos blockpos = new BlockPos(i1, j1 - 1, k1);
                    if (this.world.getBlockState(blockpos).isTopSolid(this.world, blockpos, skaven) && this.world.getLight(new BlockPos(i1, j1, k1)) < 10) {
                        skaven.setPosition((double)i1, (double)j1, (double)k1);
                        if (!this.world.isPlayerWithin((double)i1, (double)j1, (double)k1, 7.0D) && this.world.checkNoEntityCollision(skaven) && this.world.hasNoCollisions(skaven) && !this.world.containsAnyLiquid(skaven.getBoundingBox())) {
                            this.world.addEntity(skaven);
                            if (entitylivingbase != null)
                                skaven.setAttackTarget(entitylivingbase);
                            break;
                        }
                    }
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean Spawnrules(EntityType<SkavenEntity> p_223366_0_, IWorld world, SpawnReason reason, BlockPos blockPos, Random randomIn) {
            if (world.getDifficulty() != Difficulty.PEACEFUL && world.getLight(blockPos) <= randomIn.nextInt(8)) {
                Biome biome = world.getBiome(blockPos);
                if (biome == Biomes.MOUNTAINS) {
                    return canSpawnOn(p_223366_0_, world, reason, blockPos, randomIn);
                }
                else if( blockPos.getY() < 35);

            }

            return false;

    }



    @Override
    public boolean isActiveItemStackBlocking()
    {
        if(this.getHeldItemOffhand().getItem() instanceof ShieldItem && getAttackTarget()!=null && this.rand.nextInt(10)<4){
            playSound(SoundEvents.ITEM_SHIELD_BLOCK,1,1);
            return true;
        }
        else return false;
    }

}
