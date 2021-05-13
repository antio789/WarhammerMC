package warhammermod.Entities.Living;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.GolemLastSeenSensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.*;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.village.GossipManager;
import net.minecraft.village.GossipType;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.Living.Aimanager.Data.DwarfProfession;
import warhammermod.Entities.Living.Aimanager.Data.DwarfTrades;
import warhammermod.Entities.Living.Aimanager.DwarfCombattasks;
import warhammermod.Entities.Living.Aimanager.DwarfVillagerTasks;
import warhammermod.utils.inithandler.Entityinit;
import warhammermod.utils.inithandler.WarhammermodRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class DwarfEntity extends AbstractVillagerEntity implements IReputationTracking, IVillagerDataHolder {
    //useless but to remove annoying error message
    public DwarfEntity(World world){
        super(Entityinit.DWARF,world);
    }
    //easier to work with than default mc way thats also private
    private static final DataParameter<Integer> Profession = EntityDataManager.defineId(DwarfEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> Level = EntityDataManager.defineId(DwarfEntity.class, DataSerializers.INT);
    private Boolean hasspawnedlord=false;


    public static final Map<Item, Integer> FOOD_POINTS = ImmutableMap.of(Items.BREAD, 4, Items.POTATO, 1, Items.CARROT, 1, Items.BEETROOT, 1);
    private static final Set<Item> WANTED_ITEMS = ImmutableSet.of(Items.BREAD, Items.POTATO, Items.CARROT, Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT, Items.BEETROOT_SEEDS);

    private int updateMerchantTimer;
    private boolean increaseProfessionLevelOnUpdate;
    @Nullable
    private PlayerEntity lastTradedPlayer;
    private byte foodLevel;
    private final GossipManager gossips = new GossipManager();
    private long lastGossipTime;
    private long lastGossipDecayTime;
    private int villagerXp;
    private long lastRestockGameTime;
    private int numberOfRestocksToday;
    private long lastRestockCheckDayTime;
    private boolean assignProfessionWhenSpawned;
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.ATTACK_COOLING_DOWN,MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleType.MEETING_POINT, MemoryModuleType.LIVING_ENTITIES, MemoryModuleType.VISIBLE_LIVING_ENTITIES, MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.BREED_TARGET, MemoryModuleType.PATH, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.NEAREST_BED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleType.HIDING_PLACE, MemoryModuleType.HEARD_BELL_TIME, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LAST_SLEPT, MemoryModuleType.LAST_WOKEN, MemoryModuleType.LAST_WORKED_AT_POI, MemoryModuleType.GOLEM_DETECTED_RECENTLY,MemoryModuleType.ATTACK_TARGET);
    private static final ImmutableList<SensorType<? extends Sensor<? super DwarfEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.NEAREST_BED, SensorType.HURT_BY, SensorType.VILLAGER_HOSTILES,WarhammermodRegistry.Hostiles, WarhammermodRegistry.VISIBLE_VILLAGER_BABIES, WarhammermodRegistry.SECONDARY_POIS, WarhammermodRegistry.Lord_LastSeen);
    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<DwarfEntity, PointOfInterestType>> POI_MEMORIES = ImmutableMap.of(MemoryModuleType.HOME, (dwarfEntity, pointOfInterestType) -> {
        return pointOfInterestType == PointOfInterestType.HOME;
    }, MemoryModuleType.JOB_SITE, (dwarfEntity, pointOfInterestType) -> {
        return dwarfEntity.getProfession().getPointOfInterest() == pointOfInterestType;
    }, MemoryModuleType.POTENTIAL_JOB_SITE, (dwarfEntity, pointOfInterestType) -> {
        return PointOfInterestType.ALL_JOBS.test(pointOfInterestType);
    }, MemoryModuleType.MEETING_POINT, (dwarfEntity, pointOfInterestType) -> {
        return pointOfInterestType == PointOfInterestType.MEETING;
    });
    /**
     * initialization
     */

    public DwarfEntity(EntityType<? extends DwarfEntity> entityType, World world) {
        super(entityType, world);
        ((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(true);
        this.getNavigation().setCanFloat(true);
        this.setCanPickUpLoot(true);
        this.setVillagerDataBase();
        this.setEquipment(this.getProfession());
    }



    /**
     * all custom profession
     */
    @Deprecated
    public void setVillagerData(VillagerData p_213753_1_) {
    }
    @Deprecated
    public VillagerData getVillagerData() {
        return null;
    }

    public int getProfessionID()
    {
        return Math.max(this.entityData.get(Profession), 0);//0farmer,1miner,2Engineer,3builder,4slayer,5lord,6 warrior
    }

    public void setVillagerData() {
        this.setVillagerData(getProfessionID(), getProfessionLevel());
    }

    public void setVillagerDataBase() {
        this.setVillagerData(DwarfProfession.Warrior.getID(),1);
    }

    public void setVillagerData(int prof,int level) {

        if (getProfession().getID() != prof) {
            this.offers = null;
        }
        if(entityData.get(Profession)!=DwarfProfession.Lord.getID()){
            this.entityData.set(Profession,prof);}
        this.entityData.set(Level,level);
    }

    public void registerVillagerData() {

        this.entityData.define(Profession, DwarfProfession.Warrior.getID());
        this.entityData.define(Level,1);
    }

    public void setVillagerlevel(int level) {
        setVillagerData(getProfessionID(),level);
    }

    public void setVillagerProfession(DwarfProfession profession){
        setVillagerData(profession.getID(), getProfessionLevel());
        this.setEquipment(profession);
        this.updateAttributes(profession);
    }


    public DwarfProfession getProfession(){
        for(DwarfProfession prof : DwarfProfession.Profession){
            if (prof.getID()==getProfessionID()){
                return prof;
            }
        }
        return DwarfProfession.Warrior;
    }


    public int getProfessionLevel(){
        return Math.max(this.entityData.get(Level),1);
    }

    public void setProfession(int professionId)
    {
        this.entityData.set(Profession, professionId);
    }


    private final int[] xpforlevel = new int[]{0, 10, 70, 150, 250};

    @OnlyIn(Dist.CLIENT)
    public int getlevelfromxpclient(int p_221133_0_) {
        return haslevel(p_221133_0_) ? xpforlevel[p_221133_0_ - 1] : 0;
    }

    public int getlevelfromxp(int p_221127_0_) {
        return haslevel(p_221127_0_) ? xpforlevel[p_221127_0_] : 0;
    }

    public boolean haslevel(int p_221128_0_) {
        return p_221128_0_ >= 1 && p_221128_0_ < 5;
    }

    /**
     * all related to combat capabilities
     */
    protected void setEquipment(DwarfProfession prof)
    {
        if(hasItemInSlot(EquipmentSlotType.MAINHAND)){
            this.setItemSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
        }
        if(hasItemInSlot(EquipmentSlotType.OFFHAND)){
            this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
        }
        this.setItemSlot(EquipmentSlotType.MAINHAND,new ItemStack(prof.getItemtoslot(0)));
        this.setItemSlot(EquipmentSlotType.OFFHAND,new ItemStack(prof.getItemtoslot(1)));
    }

    protected void updateAttributes(DwarfProfession prof){
        if(prof.equals(DwarfProfession.Slayer)){
            this.getAttribute(Attributes.ARMOR).setBaseValue(0);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public AbstractIllagerEntity.ArmPose getArmPose() {
        if (this.isAggressive()){
            return AbstractIllagerEntity.ArmPose.ATTACKING;
        } else {
            return AbstractIllagerEntity.ArmPose.CROSSED;
        }
    }
    @Override
    public boolean isBlocking() {
        if(this.getOffhandItem().getItem() instanceof ShieldItem && this.isAggressive() && this.random.nextFloat()<0.33){
            playSound(SoundEvents.SHIELD_BLOCK,1,1);
            return true;
        }
        else return false;
    }

    /**
     * where modifications are needed
     */
    protected float getVoicePitch()
    {
        return this.isBaby() ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.4F : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.6F;
    }

    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setVillagerData(compound.getInt("profession"),compound.getInt("level"));
        this.hasspawnedlord=compound.getBoolean("canspawnlord");

        if (compound.contains("Offers", 10)) {
            this.offers = new MerchantOffers(compound.getCompound("Offers"));
        }

        if (compound.contains("FoodLevel", 1)) {
            this.foodLevel = compound.getByte("FoodLevel");
        }

        ListNBT listnbt = compound.getList("Gossips", 10);
        this.gossips.update(new Dynamic<>(NBTDynamicOps.INSTANCE, listnbt));
        if (compound.contains("Xp", 3)) {
            this.villagerXp = compound.getInt("Xp");
        }

        this.lastRestockGameTime = compound.getLong("LastRestock");
        this.lastGossipDecayTime = compound.getLong("LastGossipDecay");
        this.setCanPickUpLoot(true);
        if (this.level instanceof ServerWorld) {
            this.refreshBrain((ServerWorld)this.level);
        }

        this.numberOfRestocksToday = compound.getInt("RestocksToday");
        if (compound.contains("AssignProfessionWhenSpawned")) {
            this.assignProfessionWhenSpawned = compound.getBoolean("AssignProfessionWhenSpawned");
        }

    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);

        compound.putInt("level", this.getProfessionLevel());
        compound.putInt("profession",this.getProfessionID());

        compound.putByte("FoodLevel", this.foodLevel);
        compound.put("Gossips", this.gossips.store(NBTDynamicOps.INSTANCE).getValue());
        compound.putInt("Xp", this.villagerXp);
        compound.putLong("LastRestock", this.lastRestockGameTime);
        compound.putLong("LastGossipDecay", this.lastGossipDecayTime);
        compound.putInt("RestocksToday", this.numberOfRestocksToday);
        if (this.assignProfessionWhenSpawned) {
            compound.putBoolean("AssignProfessionWhenSpawned", true);
        }

    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(Profession,
                DwarfProfession.Warrior.getID());
        this.entityData.define(Level,1);
    }

    public DwarfEntity getBreedOffspring(ServerWorld world, AgeableEntity entity) {
        double d0 = this.random.nextDouble();

        DwarfEntity dwarfEntity = new DwarfEntity(Entityinit.DWARF, world);
        dwarfEntity.finalizeSpawn(world, world.getCurrentDifficultyAt(dwarfEntity.blockPosition()), SpawnReason.BREEDING, (ILivingEntityData)null, (CompoundNBT)null);
        return dwarfEntity;
    }

    private void registerBrainGoals(Brain<DwarfEntity> p_213744_1_) {
        DwarfProfession villagerprofession = this.getProfession();
        if (this.isBaby()) {
            p_213744_1_.setSchedule(Schedule.VILLAGER_BABY);
            p_213744_1_.addActivity(Activity.PLAY, DwarfVillagerTasks.getPlayPackage(0.5F));
            p_213744_1_.addActivity(Activity.PANIC, DwarfVillagerTasks.getPanicPackage(villagerprofession, 0.5F));
        } else {
            p_213744_1_.setSchedule(Schedule.VILLAGER_DEFAULT);

            p_213744_1_.addActivity(Activity.PANIC, DwarfCombattasks.getAttackPackage(villagerprofession, 0.5F));
            p_213744_1_.addActivityWithConditions(Activity.WORK, DwarfVillagerTasks.getWorkPackage(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT)));
        }

        p_213744_1_.addActivity(Activity.CORE, DwarfVillagerTasks.getCorePackage(villagerprofession, 0.5F));
        p_213744_1_.addActivityWithConditions(Activity.MEET, DwarfVillagerTasks.getMeetPackage(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryModuleStatus.VALUE_PRESENT)));
        p_213744_1_.addActivity(Activity.REST, DwarfVillagerTasks.getRestPackage(villagerprofession, 0.5F));
        p_213744_1_.addActivity(Activity.IDLE, DwarfVillagerTasks.getIdlePackage(villagerprofession, 0.5F));
        p_213744_1_.addActivity(Activity.PRE_RAID, DwarfVillagerTasks.getPreRaidPackage(villagerprofession, 0.5F));
        p_213744_1_.addActivity(Activity.RAID, DwarfVillagerTasks.getRaidPackage(villagerprofession, 0.5F));
        p_213744_1_.addActivity(Activity.HIDE, DwarfVillagerTasks.getHidePackage(villagerprofession, 0.5F));
        p_213744_1_.setCoreActivities(ImmutableSet.of(Activity.CORE));
        p_213744_1_.setDefaultActivity(Activity.IDLE);
        p_213744_1_.setActiveActivityIfPossible(Activity.IDLE);
        p_213744_1_.updateActivityFromSchedule(this.level.getDayTime(), this.level.getGameTime());
    }

    public void spawnGolemIfNeeded(ServerWorld p_242367_1_, long p_242367_2_, int p_242367_4_) {
        if (this.wantsToSpawnGolem(p_242367_2_)) {
            AxisAlignedBB axisalignedbb = this.getBoundingBox().inflate(10.0D, 10.0D, 10.0D);
            List<DwarfEntity> list = p_242367_1_.getEntitiesOfClass(DwarfEntity.class, axisalignedbb);
            List<DwarfEntity> list1 = list.stream().filter((p_226554_2_) -> {
                return p_226554_2_.wantsToSpawnGolem(p_242367_2_);
            }).limit(5L).collect(Collectors.toList());
            if (list1.size() >= p_242367_4_) {
                DwarfEntity lord = this.trySpawnGolem(p_242367_1_);
                if (lord != null) {
                    lord.setVillagerProfession(DwarfProfession.Lord);
                    list.forEach(GolemLastSeenSensor::golemDetected);
                }
            }
        }
    }

    public boolean wantsToSpawnGolem(long p_223350_1_) {
        if (!this.golemSpawnConditionsMet(this.level.getGameTime())) {
            return false;
        } else {
            return !this.brain.hasMemoryValue(MemoryModuleType.GOLEM_DETECTED_RECENTLY);
        }
    }

    @Nullable
    private DwarfEntity trySpawnGolem(ServerWorld p_213759_1_) {
        BlockPos blockpos = this.blockPosition();

        for(int i = 0; i < 10; ++i) {
            double d0 = (double)(p_213759_1_.random.nextInt(16) - 8);
            double d1 = (double)(p_213759_1_.random.nextInt(16) - 8);
            BlockPos blockpos1 = this.findSpawnPositionForGolemInColumn(blockpos, d0, d1);
            if (blockpos1 != null) {
                DwarfEntity lord = Entityinit.DWARF.create(p_213759_1_, (CompoundNBT)null, (ITextComponent)null, (PlayerEntity)null, blockpos1, SpawnReason.MOB_SUMMONED, false, false);
                if (lord != null) {
                    if (lord.checkSpawnRules(p_213759_1_, SpawnReason.MOB_SUMMONED) && lord.checkSpawnObstruction(p_213759_1_)) {
                        p_213759_1_.addFreshEntityWithPassengers(lord);
                        return lord;
                    }

                    lord.remove();
                }
            }
        }

        return null;
    }

    private boolean golemSpawnConditionsMet(long gametime) {
        Optional<Long> optional = this.brain.getMemory(MemoryModuleType.LAST_SLEPT);
        if (optional.isPresent() && haslevelforlord()) {
            return gametime - optional.get() < 24000L;
        } else {
            return false;
        }
    }

    private boolean haslevelforlord() {
        if (this.getProfessionLevel()==5) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * copy from villager includes everything that doesnt needs to be modified
     */

    public Brain<DwarfEntity> getBrain() {
        return (Brain<DwarfEntity>)super.getBrain();
    }

    protected Brain.BrainCodec<DwarfEntity> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> p_213364_1_) {
        Brain<DwarfEntity> brain = this.brainProvider().makeBrain(p_213364_1_);
        this.registerBrainGoals(brain);
        return brain;
    }

    public void refreshBrain(ServerWorld p_213770_1_) {
        Brain<DwarfEntity> brain = this.getBrain();
        brain.stopAll(p_213770_1_, this);
        this.brain = brain.copyWithoutBehaviors();
        this.registerBrainGoals(this.getBrain());
    }



    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (this.level instanceof ServerWorld) {
            this.refreshBrain((ServerWorld)this.level);
        }

    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5D).add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    public boolean assignProfessionWhenSpawned() {
        return this.assignProfessionWhenSpawned;
    }

    protected void customServerAiStep() {
        DwarfCombattasks.updateActivity(this);
        this.level.getProfiler().push("villagerBrain");
        this.getBrain().tick((ServerWorld)this.level, this);
        this.level.getProfiler().pop();
        if (this.assignProfessionWhenSpawned) {
            this.assignProfessionWhenSpawned = false;
        }

        if (!this.isTrading() && this.updateMerchantTimer > 0) {
            --this.updateMerchantTimer;
            if (this.updateMerchantTimer <= 0) {
                if (this.increaseProfessionLevelOnUpdate) {
                    this.increaseMerchantCareer();
                    this.increaseProfessionLevelOnUpdate = false;
                }

                this.addEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
            }
        }

        if (this.lastTradedPlayer != null && this.level instanceof ServerWorld) {
            ((ServerWorld)this.level).onReputationEvent(IReputationType.TRADE, this.lastTradedPlayer, this);
            this.level.broadcastEntityEvent(this, (byte)14);
            this.lastTradedPlayer = null;
        }

        if (!this.isNoAi() && this.random.nextInt(100) == 0) {
            Raid raid = ((ServerWorld)this.level).getRaidAt(this.blockPosition());
            if (raid != null && raid.isActive() && !raid.isOver()) {
                this.level.broadcastEntityEvent(this, (byte)42);
            }
        }

        if (this.getProfession() == DwarfProfession.Warrior && this.isTrading()) {
            this.stopTrading();
        }

        super.customServerAiStep();
    }

    public void tick() {
        super.tick();
        if (this.getUnhappyCounter() > 0) {
            this.setUnhappyCounter(this.getUnhappyCounter() - 1);
        }

        this.maybeDecayGossip();
    }

    public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.isTrading() && !this.isSleeping() && !p_230254_1_.isSecondaryUseActive()) {
            if (this.isBaby()) {
                this.setUnhappy();
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            } else {
                boolean flag = this.getOffers().isEmpty() && this.isAggressive();
                if (p_230254_2_ == Hand.MAIN_HAND) {
                    if (flag && !this.level.isClientSide) {
                        this.setUnhappy();
                    }

                    p_230254_1_.awardStat(Stats.TALKED_TO_VILLAGER);
                }

                if (flag) {
                    return ActionResultType.sidedSuccess(this.level.isClientSide);
                } else {
                    if (!this.level.isClientSide && !this.offers.isEmpty()) {
                        this.startTrading(p_230254_1_);
                    }

                    return ActionResultType.sidedSuccess(this.level.isClientSide);
                }
            }
        } else {
            return super.mobInteract(p_230254_1_, p_230254_2_);
        }
    }

    private void setUnhappy() {
        this.setUnhappyCounter(40);
        if (!this.level.isClientSide()) {
            this.playSound(SoundEvents.VILLAGER_NO, this.getSoundVolume(), this.getVoicePitch());
        }

    }

    private void startTrading(PlayerEntity p_213740_1_) {
        this.updateSpecialPrices(p_213740_1_);
        this.setTradingPlayer(p_213740_1_);
        this.openTradingScreen(p_213740_1_, this.getDisplayName(), this.getProfessionLevel());
    }

    public void setTradingPlayer(@Nullable PlayerEntity p_70932_1_) {
        boolean flag = this.getTradingPlayer() != null && p_70932_1_ == null;
        super.setTradingPlayer(p_70932_1_);
        if (flag) {
            this.stopTrading();
        }

    }

    protected void stopTrading() {
        super.stopTrading();
        this.resetSpecialPrices();
    }

    private void resetSpecialPrices() {
        for(MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetSpecialPriceDiff();
        }

    }

    public boolean canRestock() {
        return true;
    }

    public void restock() {
        this.updateDemand();

        for(MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetUses();
        }

        this.lastRestockGameTime = this.level.getGameTime();
        ++this.numberOfRestocksToday;
    }

    private boolean needsToRestock() {
        for(MerchantOffer merchantoffer : this.getOffers()) {
            if (merchantoffer.needsRestock()) {
                return true;
            }
        }

        return false;
    }

    private boolean allowedToRestock() {
        return this.numberOfRestocksToday == 0 || this.numberOfRestocksToday < 2 && this.level.getGameTime() > this.lastRestockGameTime + 2400L;
    }

    public boolean shouldRestock() {
        long i = this.lastRestockGameTime + 12000L;
        long j = this.level.getGameTime();
        boolean flag = j > i;
        long k = this.level.getDayTime();
        if (this.lastRestockCheckDayTime > 0L) {
            long l = this.lastRestockCheckDayTime / 24000L;
            long i1 = k / 24000L;
            flag |= i1 > l;
        }

        this.lastRestockCheckDayTime = k;
        if (flag) {
            this.lastRestockGameTime = j;
            this.resetNumberOfRestocks();
        }

        return this.allowedToRestock() && this.needsToRestock();
    }

    private void catchUpDemand() {
        int i = 2 - this.numberOfRestocksToday;
        if (i > 0) {
            for(MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.resetUses();
            }
        }

        for(int j = 0; j < i; ++j) {
            this.updateDemand();
        }

    }

    private void updateDemand() {
        for(MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.updateDemand();
        }

    }

    private void updateSpecialPrices(PlayerEntity p_213762_1_) {
        int i = this.getPlayerReputation(p_213762_1_);
        if (i != 0) {
            for(MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.addToSpecialPriceDiff(-MathHelper.floor((float)i * merchantoffer.getPriceMultiplier()));
            }
        }

        if (p_213762_1_.hasEffect(Effects.HERO_OF_THE_VILLAGE)) {
            EffectInstance effectinstance = p_213762_1_.getEffect(Effects.HERO_OF_THE_VILLAGE);
            int k = effectinstance.getAmplifier();

            for(MerchantOffer merchantoffer1 : this.getOffers()) {
                double d0 = 0.3D + 0.0625D * (double)k;
                int j = (int)Math.floor(d0 * (double)merchantoffer1.getBaseCostA().getCount());
                merchantoffer1.addToSpecialPriceDiff(-Math.max(j, 1));
            }
        }

    }

    public boolean removeWhenFarAway(double p_213397_1_) {
        return false;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        if (this.isSleeping()) {
            return null;
        } else {
            return this.isTrading() ? SoundEvents.VILLAGER_TRADE : SoundEvents.VILLAGER_AMBIENT;
        }
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.VILLAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    public void playWorkSound() {
        SoundEvent soundevent = this.getProfession().getWorksound();
        if (soundevent != null) {
            this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
        }

    }

    protected void rewardTradeXp(MerchantOffer p_213713_1_) {
        int i = 3 + this.random.nextInt(4);
        this.villagerXp += p_213713_1_.getXp();
        this.lastTradedPlayer = this.getTradingPlayer();
        if (this.shouldIncreaseLevel()) {
            this.updateMerchantTimer = 40;
            this.increaseProfessionLevelOnUpdate = true;
            i += 5;
        }

        if (p_213713_1_.shouldRewardExp()) {
            this.level.addFreshEntity(new ExperienceOrbEntity(this.level, this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }

    }

    public void setLastHurtByMob(@Nullable LivingEntity p_70604_1_) {
        if (p_70604_1_ != null && this.level instanceof ServerWorld) {
            ((ServerWorld)this.level).onReputationEvent(IReputationType.VILLAGER_HURT, p_70604_1_, this);
            if (this.isAlive() && p_70604_1_ instanceof PlayerEntity) {
                this.level.broadcastEntityEvent(this, (byte)13);
            }
        }

        super.setLastHurtByMob(p_70604_1_);
    }

    public void die(DamageSource p_70645_1_) {
        LOGGER.info("Villager {} died, message: '{}'", this, p_70645_1_.getLocalizedDeathMessage(this).getString());
        Entity entity = p_70645_1_.getEntity();
        if (entity != null) {
            this.tellWitnessesThatIWasMurdered(entity);
        }

        this.releaseAllPois();
        super.die(p_70645_1_);
    }

    private void releaseAllPois() {
        this.releasePoi(MemoryModuleType.HOME);
        this.releasePoi(MemoryModuleType.JOB_SITE);
        this.releasePoi(MemoryModuleType.POTENTIAL_JOB_SITE);
        this.releasePoi(MemoryModuleType.MEETING_POINT);
    }

    private void tellWitnessesThatIWasMurdered(Entity p_223361_1_) {
        if (this.level instanceof ServerWorld) {
            Optional<List<LivingEntity>> optional = this.brain.getMemory(MemoryModuleType.VISIBLE_LIVING_ENTITIES);
            if (optional.isPresent()) {
                ServerWorld serverworld = (ServerWorld)this.level;
                optional.get().stream().filter((p_223349_0_) -> {
                    return p_223349_0_ instanceof IReputationTracking;
                }).forEach((p_223342_2_) -> {
                    serverworld.onReputationEvent(IReputationType.VILLAGER_KILLED, p_223361_1_, (IReputationTracking)p_223342_2_);
                });
            }
        }
    }

    public void releasePoi(MemoryModuleType<GlobalPos> p_213742_1_) {
        if (this.level instanceof ServerWorld) {
            MinecraftServer minecraftserver = ((ServerWorld)this.level).getServer();
            this.brain.getMemory(p_213742_1_).ifPresent((p_213752_3_) -> {
                ServerWorld serverworld = minecraftserver.getLevel(p_213752_3_.dimension());
                if (serverworld != null) {
                    PointOfInterestManager pointofinterestmanager = serverworld.getPoiManager();
                    Optional<PointOfInterestType> optional = pointofinterestmanager.getType(p_213752_3_.pos());
                    BiPredicate<DwarfEntity, PointOfInterestType> bipredicate = POI_MEMORIES.get(p_213742_1_);
                    if (optional.isPresent() && bipredicate.test(this, optional.get())) {
                        pointofinterestmanager.release(p_213752_3_.pos());
                        DebugPacketSender.sendPoiTicketCountPacket(serverworld, p_213752_3_.pos());
                    }

                }
            });
        }
    }

    public boolean canBreed() {
        return this.foodLevel + this.countFoodPointsInInventory() >= 12 && this.getAge() == 0;
    }

    private boolean hungry() {
        return this.foodLevel < 12;
    }

    private void eatUntilFull() {
        if (this.hungry() && this.countFoodPointsInInventory() != 0) {
            for(int i = 0; i < this.getInventory().getContainerSize(); ++i) {
                ItemStack itemstack = this.getInventory().getItem(i);
                if (!itemstack.isEmpty()) {
                    Integer integer = FOOD_POINTS.get(itemstack.getItem());
                    if (integer != null) {
                        int j = itemstack.getCount();

                        for(int k = j; k > 0; --k) {
                            this.foodLevel = (byte)(this.foodLevel + integer);
                            this.getInventory().removeItem(i, 1);
                            if (!this.hungry()) {
                                return;
                            }
                        }
                    }
                }
            }

        }
    }

    public int getPlayerReputation(PlayerEntity p_223107_1_) {
        return this.gossips.getReputation(p_223107_1_.getUUID(), (p_223103_0_) -> {
            return true;
        });
    }

    private void digestFood(int p_213758_1_) {
        this.foodLevel = (byte)(this.foodLevel - p_213758_1_);
    }

    public void eatAndDigestFood() {
        this.eatUntilFull();
        this.digestFood(12);
    }

    public void setOffers(MerchantOffers p_213768_1_) {
        this.offers = p_213768_1_;
    }

    private boolean shouldIncreaseLevel() {
        int i = this.getProfessionLevel();
        return VillagerData.canLevelUp(i) && this.villagerXp >= VillagerData.getMaxXpPerLevel(i);
    }

    private void increaseMerchantCareer() {
        this.setVillagerlevel(getProfessionLevel()+1);
        this.updateTrades();
    }

    protected ITextComponent getTypeName() {
        net.minecraft.util.ResourceLocation profName = this.getProfession().getRegistryName();
        return new TranslationTextComponent(this.getType().getDescriptionId() + '.' + (!"minecraft".equals(profName.getNamespace()) ? profName.getNamespace() + '.' : "") + profName.getPath());
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte p_70103_1_) {
        if (p_70103_1_ == 12) {
            this.addParticlesAroundSelf(ParticleTypes.HEART);
        } else if (p_70103_1_ == 13) {
            this.addParticlesAroundSelf(ParticleTypes.ANGRY_VILLAGER);
        } else if (p_70103_1_ == 14) {
            this.addParticlesAroundSelf(ParticleTypes.HAPPY_VILLAGER);
        } else if (p_70103_1_ == 42) {
            this.addParticlesAroundSelf(ParticleTypes.SPLASH);
        } else {
            super.handleEntityEvent(p_70103_1_);
        }

    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        if (p_213386_3_ == SpawnReason.BREEDING) {
            this.setVillagerProfession(DwarfProfession.Warrior);
        }

        if (p_213386_3_ == SpawnReason.COMMAND || p_213386_3_ == SpawnReason.SPAWN_EGG || p_213386_3_ == SpawnReason.SPAWNER || p_213386_3_ == SpawnReason.DISPENSER) {
            this.setVillagerDataBase();
        }

        if (p_213386_3_ == SpawnReason.STRUCTURE) {
            this.assignProfessionWhenSpawned = true;
        }

        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }




    protected void pickUpItem(ItemEntity p_175445_1_) {
        ItemStack itemstack = p_175445_1_.getItem();
        if (this.wantsToPickUp(itemstack)) {
            Inventory inventory = this.getInventory();
            boolean flag = inventory.canAddItem(itemstack);
            if (!flag) {
                return;
            }

            this.onItemPickup(p_175445_1_);
            this.take(p_175445_1_, itemstack.getCount());
            ItemStack itemstack1 = inventory.addItem(itemstack);
            if (itemstack1.isEmpty()) {
                p_175445_1_.remove();
            } else {
                itemstack.setCount(itemstack1.getCount());
            }
        }

    }

    public boolean wantsToPickUp(ItemStack p_230293_1_) {
        Item item = p_230293_1_.getItem();
        return (WANTED_ITEMS.contains(item) || this.getProfession().getSpecificItems().contains(item)) && this.getInventory().canAddItem(p_230293_1_);
    }

    public boolean hasExcessFood() {
        return this.countFoodPointsInInventory() >= 24;
    }

    public boolean wantsMoreFood() {
        return this.countFoodPointsInInventory() < 12;
    }

    private int countFoodPointsInInventory() {
        Inventory inventory = this.getInventory();
        return FOOD_POINTS.entrySet().stream().mapToInt((p_226553_1_) -> {
            return inventory.countItem(p_226553_1_.getKey()) * p_226553_1_.getValue();
        }).sum();
    }

    public boolean hasFarmSeeds() {
        return this.getInventory().hasAnyOf(ImmutableSet.of(Items.WHEAT_SEEDS, Items.POTATO, Items.CARROT, Items.BEETROOT_SEEDS));
    }

    protected void updateTrades() {
        DwarfProfession prof = getProfession();
        Int2ObjectMap<DwarfTrades.ITrade[]> int2objectmap = DwarfTrades.TRADES.get(prof);
        if (int2objectmap != null && !int2objectmap.isEmpty()) {
            DwarfTrades.ITrade[] avillagertrades$itrade = int2objectmap.get(this.getProfessionLevel());
            if (avillagertrades$itrade != null) {
                MerchantOffers merchantoffers = this.getOffers();
                this.addOffersFromItemListings(merchantoffers, avillagertrades$itrade, 2);
            }
        }
    }

    protected void addOffersFromItemListings(MerchantOffers p_213717_1_, DwarfTrades.ITrade[] p_213717_2_, int p_213717_3_) {
        Set<Integer> set = Sets.newHashSet();
        if (p_213717_2_.length > p_213717_3_) {
            while(set.size() < p_213717_3_) {
                set.add(this.random.nextInt(p_213717_2_.length));
            }
        } else {
            for(int i = 0; i < p_213717_2_.length; ++i) {
                set.add(i);
            }
        }

        for(Integer integer : set) {
            DwarfTrades.ITrade villagertrades$itrade = p_213717_2_[integer];
            MerchantOffer merchantoffer = villagertrades$itrade.getOffer(this, this.random);
            if (merchantoffer != null) {
                p_213717_1_.add(merchantoffer);
            }
        }

    }

    public void gossip(ServerWorld p_242368_1_, DwarfEntity p_242368_2_, long p_242368_3_) {
        if ((p_242368_3_ < this.lastGossipTime || p_242368_3_ >= this.lastGossipTime + 1200L) && (p_242368_3_ < p_242368_2_.lastGossipTime || p_242368_3_ >= p_242368_2_.lastGossipTime + 1200L)) {
            this.gossips.transferFrom(p_242368_2_.gossips, this.random, 10);
            this.lastGossipTime = p_242368_3_;
            p_242368_2_.lastGossipTime = p_242368_3_;
            this.spawnGolemIfNeeded(p_242368_1_, p_242368_3_, 5);
        }
    }

    private void maybeDecayGossip() {
        long i = this.level.getGameTime();
        if (this.lastGossipDecayTime == 0L) {
            this.lastGossipDecayTime = i;
        } else if (i >= this.lastGossipDecayTime + 24000L) {
            this.gossips.decay();
            this.lastGossipDecayTime = i;
        }
    }



    @Nullable
    private BlockPos findSpawnPositionForGolemInColumn(BlockPos p_241433_1_, double p_241433_2_, double p_241433_4_) {
        int i = 6;
        BlockPos blockpos = p_241433_1_.offset(p_241433_2_, 6.0D, p_241433_4_);
        BlockState blockstate = this.level.getBlockState(blockpos);

        for(int j = 6; j >= -6; --j) {
            BlockPos blockpos1 = blockpos;
            BlockState blockstate1 = blockstate;
            blockpos = blockpos.below();
            blockstate = this.level.getBlockState(blockpos);
            if ((blockstate1.isAir() || blockstate1.getMaterial().isLiquid()) && blockstate.getMaterial().isSolidBlocking()) {
                return blockpos1;
            }
        }

        return null;
    }

    public void onReputationEventFrom(IReputationType p_213739_1_, Entity p_213739_2_) {
        if (p_213739_1_ == IReputationType.ZOMBIE_VILLAGER_CURED) {
            this.gossips.add(p_213739_2_.getUUID(), GossipType.MAJOR_POSITIVE, 20);
            this.gossips.add(p_213739_2_.getUUID(), GossipType.MINOR_POSITIVE, 25);
        } else if (p_213739_1_ == IReputationType.TRADE) {
            this.gossips.add(p_213739_2_.getUUID(), GossipType.TRADING, 2);
        } else if (p_213739_1_ == IReputationType.VILLAGER_HURT) {
            this.gossips.add(p_213739_2_.getUUID(), GossipType.MINOR_NEGATIVE, 25);
        } else if (p_213739_1_ == IReputationType.VILLAGER_KILLED) {
            this.gossips.add(p_213739_2_.getUUID(), GossipType.MAJOR_NEGATIVE, 25);
        }

    }

    public int getVillagerXp() {
        return this.villagerXp;
    }

    public void setVillagerXp(int p_213761_1_) {
        this.villagerXp = p_213761_1_;
    }

    private void resetNumberOfRestocks() {
        this.catchUpDemand();
        this.numberOfRestocksToday = 0;
    }

    public GossipManager getGossips() {
        return this.gossips;
    }

    public void setGossips(INBT p_223716_1_) {
        this.gossips.update(new Dynamic<>(NBTDynamicOps.INSTANCE, p_223716_1_));
    }

    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPacketSender.sendEntityBrain(this);
    }

    public void startSleeping(BlockPos p_213342_1_) {
        super.startSleeping(p_213342_1_);
        this.brain.setMemory(MemoryModuleType.LAST_SLEPT, this.level.getGameTime());
        this.brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        this.brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    public void stopSleeping() {
        super.stopSleeping();
        this.brain.setMemory(MemoryModuleType.LAST_WOKEN, this.level.getGameTime());
    }





}
