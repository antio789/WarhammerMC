package warhammermod.Entities.living;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.*;
import net.minecraft.entity.monster.AbstractIllagerEntity;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import warhammermod.Entities.living.Aimanager.DwarfTasks;
import warhammermod.Entities.living.Data.DwarfProfession;
import warhammermod.Entities.living.Data.DwarfTrades;
import warhammermod.util.Handler.Entityinit;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.Handler.WarhammermodRegistry;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static warhammermod.util.Handler.WarhammermodRegistry.BREED_TARGET;

public class DwarfEntity extends AbstractVillagerEntity implements IReputationTracking, IVillagerDataHolder {
    private static final DataParameter<Integer> Profession = EntityDataManager.createKey(DwarfEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> Level = EntityDataManager.createKey(DwarfEntity.class,DataSerializers.VARINT);
    public static final Map<Item, Integer> foodsource = ImmutableMap.of(ItemsInit.Beer, 6, Items.POTATO, 1, Items.CARROT, 1,Items.BEETROOT,1);
    private static final Set<Item> ALLOWED_INVENTORY_ITEMS = ImmutableSet.of(ItemsInit.Beer, Items.POTATO, Items.CARROT, Items.WHEAT, Items.WHEAT_SEEDS,Items.BEETROOT,Items.BEETROOT_SEEDS);
    private int timeUntilReset;
    private boolean customer;
    @Nullable
    private PlayerEntity field_213778_bG;

    private Boolean hasspawnedlord=false;

    private byte foodLevel;
    private final GossipManager gossip = new GossipManager();
    private long field_213783_bN;
    private long lastGossipDecay;
    private int xp;
    private long lastRestock;
    private int field_223725_bO;
    private long field_223726_bP;
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.MEETING_POINT, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, BREED_TARGET, MemoryModuleType.PATH, MemoryModuleType.INTERACTABLE_DOORS, MemoryModuleType.field_225462_q, MemoryModuleType.NEAREST_BED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleType.HIDING_PLACE, MemoryModuleType.HEARD_BELL_TIME, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LAST_SLEPT, MemoryModuleType.field_226332_A_, MemoryModuleType.LAST_WORKED_AT_POI, MemoryModuleType.GOLEM_LAST_SEEN_TIME,WarhammermodRegistry.ATTACK_TARGET);
    private static final ImmutableList<SensorType<? extends Sensor<? super DwarfEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.INTERACTABLE_DOORS, SensorType.NEAREST_BED, SensorType.HURT_BY, SensorType.VILLAGER_HOSTILES, SensorType.VILLAGER_BABIES, WarhammermodRegistry.SECONDARY_POIS, WarhammermodRegistry.Lord_Last_Seen);
    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<DwarfEntity, PointOfInterestType>> field_213774_bB = ImmutableMap.of(MemoryModuleType.HOME, (p_213769_0_, p_213769_1_) -> {
        return p_213769_1_ == PointOfInterestType.HOME;
    }, MemoryModuleType.JOB_SITE, (p_213771_0_, p_213771_1_) -> {
        return p_213771_0_.getProfession().getPointOfInterest() == p_213771_1_;
    }, MemoryModuleType.MEETING_POINT, (p_213772_0_, p_213772_1_) -> {
        return p_213772_1_ == PointOfInterestType.MEETING;
    });
/***cannot be used use a memory instead
    public void registerGoals() {
        this.goalSelector.addGoal(0,new MeleeAttackGoal(this,1.3,true));


    }

 */


    public DwarfEntity(EntityType<? extends DwarfEntity> p_i50183_1_, World p_i50183_2_) {
        super(p_i50183_1_, p_i50183_2_);
        ((GroundPathNavigator)this.getNavigator()).setBreakDoors(true);
        this.getNavigator().setCanSwim(true);
        this.setCanPickUpLoot(true);
        this.setVillagerData();
        this.brain = this.createBrain(new Dynamic<>(NBTDynamicOps.INSTANCE, new CompoundNBT()));
    }

    public Brain<DwarfEntity> getBrain() {
        return (Brain<DwarfEntity>) super.getBrain();
    }

    protected Brain<?> createBrain(Dynamic<?> p_213364_1_) {
        Brain<DwarfEntity> brain = new Brain<>(MEMORY_TYPES, SENSOR_TYPES, p_213364_1_);
        this.initBrain(brain);
        return brain;
    }

    public float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isChild() ? 0.7F : 1.48F;
    }


    public void resetBrain(ServerWorld p_213770_1_) {

        Brain<DwarfEntity> brain = this.getBrain();
        brain.stopAllTasks(p_213770_1_, this);
        this.brain = brain.copy();
        this.initBrain(this.getBrain());

    }

    private void initBrain(Brain<DwarfEntity> p_213744_1_) {

        DwarfProfession profession = this.getProfession();
        float f = (float)this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
        if (this.isChild()) {
            p_213744_1_.setSchedule(Schedule.VILLAGER_BABY);
            p_213744_1_.registerActivity(Activity.PLAY, DwarfTasks.play(f));
        } else {
            p_213744_1_.setSchedule(Schedule.VILLAGER_DEFAULT);
            p_213744_1_.registerActivity(Activity.PANIC, DwarfTasks.fight(profession, f));
            p_213744_1_.registerActivity(Activity.WORK, DwarfTasks.work(profession, f), ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT)));
        }

        p_213744_1_.registerActivity(Activity.CORE, DwarfTasks.core(profession, f));
        p_213744_1_.registerActivity(Activity.MEET, DwarfTasks.meet(profession, f), ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryModuleStatus.VALUE_PRESENT)));
        p_213744_1_.registerActivity(Activity.REST, DwarfTasks.rest(profession, f));
        p_213744_1_.registerActivity(Activity.IDLE, DwarfTasks.idle(profession, f));

        p_213744_1_.registerActivity(Activity.PRE_RAID, DwarfTasks.preRaid(profession, f));
        p_213744_1_.registerActivity(Activity.RAID, DwarfTasks.raid(profession, f));
        p_213744_1_.registerActivity(Activity.HIDE, DwarfTasks.hide(profession, f));
        p_213744_1_.setDefaultActivities(ImmutableSet.of(Activity.CORE));
        p_213744_1_.setFallbackActivity(Activity.IDLE);
        p_213744_1_.switchTo(Activity.IDLE);
        p_213744_1_.updateActivity(this.world.getDayTime(), this.world.getGameTime());

    }

    /**
     * This is called when Entity's growing age timer reaches 0 (negative values are considered as a child, positive as
     * an adult)
     */
    protected void onGrowingAdult() {
        super.onGrowingAdult();
        if (this.world instanceof ServerWorld) {
            this.resetBrain((ServerWorld)this.world);
            this.setEquipment(this.getProfession());
        }

    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.46D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2);
    }
    protected void updateAttributes(DwarfProfession prof){
        if(prof.equals(DwarfProfession.Slayer)){
            this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0);
        }
    }

    protected void updateAITasks() {

        this.world.getProfiler().startSection("brain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().endSection();
        if (!this.hasCustomer() && this.timeUntilReset > 0) {
            --this.timeUntilReset;
            if (this.timeUntilReset <= 0) {
                if (this.customer) {
                    this.populateBuyingList();
                    this.customer = false;
                }

                this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
            }
        }

        if (this.field_213778_bG != null && this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).updateReputation(IReputationType.TRADE, this.field_213778_bG, this);
            this.world.setEntityState(this, (byte)14);
            this.field_213778_bG = null;
        }

        if (!this.isAIDisabled() && this.rand.nextInt(100) == 0) {
            Raid raid = ((ServerWorld)this.world).findRaid(new BlockPos(this));
            if (raid != null && raid.isActive() && !raid.isOver()) {
                this.world.setEntityState(this, (byte)42);
            }
        }

        if (this.getProfession() == DwarfProfession.Warrior && this.hasCustomer()) {
            this.resetcustomer();
        }

        super.updateAITasks();

    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
         super.tick();
        if (this.getShakeHeadTicks() > 0) {
            this.setShakeHeadTicks(this.getShakeHeadTicks() - 1);
        }

        this.func_223343_eC();


    }

    public boolean processInteract(PlayerEntity player, Hand hand) {

        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = itemstack.getItem() == Items.NAME_TAG;
        if (flag) {
            itemstack.interactWithEntity(player, this, hand);
            return true;
        } else if (itemstack.getItem() != ItemsInit.DWARF_SPAWN_EGG && this.isAlive() && !this.hasCustomer() && !this.isSleeping() && !player.isSecondaryUseActive()) {

            if (this.isChild()) {
                this.shakeHead();
                return super.processInteract(player, hand);
            } else {

                boolean empty = this.getOffers().isEmpty();
                if (hand == Hand.MAIN_HAND) {
                    if (empty && !this.world.isRemote) {
                        this.shakeHead();
                    }

                    player.addStat(Stats.TALKED_TO_VILLAGER);
                }

                if (empty) {

                    return super.processInteract(player, hand);
                } else {
                    if (!this.world.isRemote && !this.offers.isEmpty()) {
                        this.displayMerchantGui(player);
                    }

                    return true;
                }
            }
        } else {

            return super.processInteract(player, hand);

        }

    }

    private void shakeHead() {
        this.setShakeHeadTicks(40);
        if (!this.world.isRemote()) {
            this.playSound(SoundEvents.ENTITY_VILLAGER_NO, this.getSoundVolume(), this.getSoundPitch());
        }

    }


    protected float getSoundPitch()
    {
        return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.4F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.6F;
    }

    private void displayMerchantGui(PlayerEntity playerEntity) {

        this.recalculateSpecialPricesFor(playerEntity);
        this.setCustomer(playerEntity);
        this.openMerchantContainer(playerEntity, this.getDisplayName(),this.getLevel());

    }

    public void setCustomer(@Nullable PlayerEntity player) {
        boolean flag = this.getCustomer() != null && player == null;
        super.setCustomer(player);
        if (flag) {
            this.resetcustomer();
        }

    }

    protected void resetcustomer() {

        super.resetCustomer();
        this.resetAllSpecialPrices();

    }

    private void resetAllSpecialPrices() {

        for(MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetSpecialPrice();
        }


    }

    public boolean func_223340_ej() {
        return true;
    }

    public void func_213766_ei() {

        this.func_223715_ey();

        for(MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetUses();
        }

        if (this.getProfession() == DwarfProfession.Farmer) {
            this.dropCraftedBread();
        }

        this.lastRestock = this.world.getGameTime();
        ++this.field_223725_bO;

    }

    private boolean func_223723_ev() {
        for(MerchantOffer merchantoffer : this.getOffers()) {
            if (merchantoffer.hasBeenUsed()) {
                return true;
            }
        }

        return false;
    }

    private boolean func_223720_ew() {
        return this.field_223725_bO == 0 || this.field_223725_bO < 2 && this.world.getGameTime() > this.lastRestock + 2400L;
    }

    public boolean func_223721_ek() {

        long i = this.lastRestock + 12000L;
        long j = this.world.getGameTime();
        boolean flag = j > i;
        long k = this.world.getDayTime();
        if (this.field_223726_bP > 0L) {
            long l = this.field_223726_bP / 24000L;
            long i1 = k / 24000L;
            flag |= i1 > l;
        }

        this.field_223726_bP = k;
        if (flag) {
            this.lastRestock = j;
            this.func_223718_eH();
        }

        return this.func_223720_ew() && this.func_223723_ev();
    }

    private void func_223719_ex() {

        int i = 2 - this.field_223725_bO;
        if (i > 0) {
            for(MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.resetUses();
            }
        }

        for(int j = 0; j < i; ++j) {
            this.func_223715_ey();
        }


    }

    private void func_223715_ey() {

        for(MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.calculateDemand();
        }


    }

    private void recalculateSpecialPricesFor(PlayerEntity playerin) {

        int i = this.getPlayerReputation(playerin);
        if (i != 0) {
            for(MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.increaseSpecialPrice(-MathHelper.floor((float)i * merchantoffer.getPriceMultiplier()));
            }
        }

        if (playerin.isPotionActive(Effects.HERO_OF_THE_VILLAGE)) {
            EffectInstance effectinstance = playerin.getActivePotionEffect(Effects.HERO_OF_THE_VILLAGE);
            int k = effectinstance.getAmplifier();

            for(MerchantOffer merchantoffer1 : this.getOffers()) {
                double d0 = 0.3D + 0.0625D * (double)k;
                int j = (int)Math.floor(d0 * (double)merchantoffer1.getBuyingStackFirst().getCount());
                merchantoffer1.increaseSpecialPrice(-Math.max(j, 1));
            }
        }


    }

    protected void registerData() {

        super.registerData();
        this.registerVillagerData();
    }

    public void writeAdditional(CompoundNBT compound) {

        super.writeAdditional(compound);
        compound.putInt("level", this.getLevel());
        compound.putInt("profession",this.getProfessionID());
        compound.putByte("FoodLevel", this.foodLevel);
        compound.put("Gossips", this.gossip.serialize(NBTDynamicOps.INSTANCE).getValue());
        compound.putInt("Xp", this.xp);
        compound.putLong("LastRestock", this.lastRestock);
        compound.putLong("LastGossipDecay", this.lastGossipDecay);
        compound.putInt("RestocksToday", this.field_223725_bO);
        compound.putBoolean("canspawnlord",this.hasspawnedlord);

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {

        super.readAdditional(compound);

            this.setVillagerData(compound.getInt("profession"),compound.getInt("level"));

        if (compound.contains("Offers", 10)) {

            this.offers = new MerchantOffers(compound.getCompound("Offers"));

        }

        if (compound.contains("FoodLevel", 1)) {
            this.foodLevel = compound.getByte("FoodLevel");
        }

        ListNBT listnbt = compound.getList("Gossips", 10);
        this.gossip.deserialize(new Dynamic<>(NBTDynamicOps.INSTANCE, listnbt));
        if (compound.contains("Xp", 3)) {
            this.xp = compound.getInt("Xp");
        }

        this.lastRestock = compound.getLong("LastRestock");
        this.hasspawnedlord=compound.getBoolean("canspawnlord");
        this.lastGossipDecay = compound.getLong("LastGossipDecay");
        this.setCanPickUpLoot(true);
        if (this.world instanceof ServerWorld) {
            this.resetBrain((ServerWorld)this.world);
        }

        this.field_223725_bO = compound.getInt("RestocksToday");

    }

    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        if (this.isSleeping()) {
            return null;
        } else {
            return this.hasCustomer() ? SoundEvents.ENTITY_VILLAGER_TRADE : SoundEvents.ENTITY_VILLAGER_AMBIENT;
        }
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    public void playWorkstationSound() {
        SoundEvent soundevent = this.getProfession().getWorksound();
        if (soundevent != null) {
            this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
        }

    }


    @Deprecated
    public VillagerData getVillagerData() {
        return null;
    }

    protected void onVillagerTrade(MerchantOffer offer) {

        int i = 3 + this.rand.nextInt(4);
        this.xp += offer.getGivenExp();
        this.field_213778_bG = this.getCustomer();
        if (this.canincreaselevel()) {
            this.timeUntilReset = 40;
            this.customer = true;
            i += 5;
        }

        if (offer.getDoesRewardExp()) {
            this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), i));
        }


    }

    /**
     * Hint to AI tasks that we were attacked by the passed EntityLivingBase and should retaliate. Is not guaranteed to
     * change our actual active target (for example if we are currently busy attacking someone else)
     */
    public void setRevengeTarget(@Nullable LivingEntity livingBase) {
        if (livingBase != null && this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).updateReputation(IReputationType.VILLAGER_HURT, livingBase, this);
            if (this.isAlive() && livingBase instanceof PlayerEntity) {
                this.world.setEntityState(this, (byte)13);
            }
        }

        super.setRevengeTarget(livingBase);
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause) {
        LOGGER.info("Villager {} died, message: '{}'", this, cause.getDeathMessage(this).getString());
        Entity entity = cause.getTrueSource();
        if (entity != null) {
            this.onDeathUpdateReputation(entity);
        }

        this.onDeathUpdatePOI(MemoryModuleType.HOME);
        this.onDeathUpdatePOI(MemoryModuleType.JOB_SITE);
        this.onDeathUpdatePOI(MemoryModuleType.MEETING_POINT);
        super.onDeath(cause);
    }

    private void onDeathUpdateReputation(Entity entity) {

        if (this.world instanceof ServerWorld) {
            Optional<List<LivingEntity>> optional = this.brain.getMemory(MemoryModuleType.VISIBLE_MOBS);
            if (optional.isPresent()) {
                ServerWorld serverworld = (ServerWorld)this.world;
                optional.get().stream().filter((p_223349_0_) -> {
                    return p_223349_0_ instanceof IReputationTracking;
                }).forEach((p_223342_2_) -> {
                    serverworld.updateReputation(IReputationType.VILLAGER_KILLED, entity, (IReputationTracking)p_223342_2_);
                });
            }
        }

    }

    public void onDeathUpdatePOI(MemoryModuleType<GlobalPos> POI) {

        if (this.world instanceof ServerWorld) {
            MinecraftServer minecraftserver = ((ServerWorld)this.world).getServer();
            this.brain.getMemory(POI).ifPresent((p_213752_3_) -> {
                ServerWorld serverworld = minecraftserver.getWorld(p_213752_3_.getDimension());
                PointOfInterestManager pointofinterestmanager = serverworld.getPointOfInterestManager();
                Optional<PointOfInterestType> optional = pointofinterestmanager.getType(p_213752_3_.getPos());
                BiPredicate<DwarfEntity, PointOfInterestType> bipredicate = field_213774_bB.get(POI);
                if (optional.isPresent() && bipredicate.test(this, optional.get())) {
                    pointofinterestmanager.release(p_213752_3_.getPos());
                    DebugPacketSender.func_218801_c(serverworld, p_213752_3_.getPos());
                }

            });
        }

    }

    public boolean canBreed() {
        return this.foodLevel + this.getFoodValueFromInventory() >= 12 && this.getGrowingAge() == 0;
    }

    private boolean hasnotEnoughFood() {
        return this.foodLevel < 12;
    }

    private void ConsumeFood() {

        if (this.hasnotEnoughFood() && this.getFoodValueFromInventory() != 0) {
            for(int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i) {
                ItemStack itemstack = this.getVillagerInventory().getStackInSlot(i);
                if (!itemstack.isEmpty()) {
                    Integer integer = foodsource.get(itemstack.getItem());
                    if (integer != null) {
                        int j = itemstack.getCount();

                        for(int k = j; k > 0; --k) {
                            this.foodLevel = (byte)(this.foodLevel + integer);
                            this.getVillagerInventory().decrStackSize(i, 1);
                            if (!this.hasnotEnoughFood()) {
                                return;
                            }
                        }
                    }
                }
            }

        }

    }

    public int getPlayerReputation(PlayerEntity p_223107_1_) {
        return this.gossip.getReputation(p_223107_1_.getUniqueID(), (p_223103_0_) -> {
            return true;
        });
    }

    private void decrFoodLevel(int p_213758_1_) {
        this.foodLevel = (byte)(this.foodLevel - p_213758_1_);
    }

    public void updateFood() {
        this.ConsumeFood();
        this.decrFoodLevel(12);
    }

    public void setoffers(MerchantOffers p_213768_1_) {
        this.offers = p_213768_1_;
    }

    private boolean canincreaselevel() {
        int i = this.getLevel();
        return haslevel(i) && this.xp >= getlevelfromxp(i);
    }

    private void populateBuyingList() {
        this.setVillagerlevel(getLevel()+1);
        this.populateTradeData();
    }

    protected ITextComponent getProfessionName() {
        net.minecraft.util.ResourceLocation profName = this.getProfession().getNameloc();
        return new TranslationTextComponent(this.getType().getTranslationKey() + '.' + (!"minecraft".equals(profName.getNamespace()) ? profName.getNamespace() + '.' : "") + profName.getPath());
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 12) {
            this.spawnParticles(ParticleTypes.HEART);
        } else if (id == 13) {
            this.spawnParticles(ParticleTypes.ANGRY_VILLAGER);
        } else if (id == 14) {
            this.spawnParticles(ParticleTypes.HAPPY_VILLAGER);
        } else if (id == 42) {
            this.spawnParticles(ParticleTypes.SPLASH);
        } else {
            super.handleStatusUpdate(id);
        }

    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (reason == SpawnReason.BREEDING) {
            this.setVillagerDataBase();
        }

        else if (reason == SpawnReason.COMMAND || reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.SPAWNER || reason == SpawnReason.DISPENSER) {
            this.setVillagerDataBase();
            this.setEquipment(this.getProfession());
        }
        else if(reason==SpawnReason.MOB_SUMMONED){
            this.setVillagerDataBase();
            this.setVillagerProfession(DwarfProfession.Lord);
        }

        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);

    }

    public DwarfEntity createChild(AgeableEntity ageable) {
        double d0 = this.rand.nextDouble();

        DwarfEntity dwarfEntity = new DwarfEntity(Entityinit.DWARF, this.world);
        dwarfEntity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(dwarfEntity)), SpawnReason.BREEDING, (ILivingEntityData)null, (CompoundNBT)null);
        return dwarfEntity;
    }


    /**
     * Tests if this entity should pickup a weapon or an armor. Entity drops current weapon or armor if the new one is
     * better.
     */
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        Item item = itemstack.getItem();
        if (this.func_223717_b(item)) {
            Inventory inventory = this.getVillagerInventory();
            boolean flag = false;

            for(int i = 0; i < inventory.getSizeInventory(); ++i) {
                ItemStack itemstack1 = inventory.getStackInSlot(i);
                if (itemstack1.isEmpty() || itemstack1.getItem() == item && itemstack1.getCount() < itemstack1.getMaxStackSize()) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                return;
            }

            int j = inventory.count(item);
            if (j == 256) {
                return;
            }

            if (j > 256) {
                inventory.func_223374_a(item, j - 256);
                return;
            }

            this.onItemPickup(itemEntity, itemstack.getCount());
            ItemStack itemstack2 = inventory.addItem(itemstack);
            if (itemstack2.isEmpty()) {
                itemEntity.remove();
            } else {
                itemstack.setCount(itemstack2.getCount());
            }
        }

    }

    public boolean func_223717_b(Item p_223717_1_) {
        return ALLOWED_INVENTORY_ITEMS.contains(p_223717_1_) || this.getProfession().getShareitem().contains(p_223717_1_);
    }

    /**
     *  to check if the villager
     * can give some items from an inventory to another villager.
     */
    public boolean canAbondonItems() {
        return this.getFoodValueFromInventory() >= 24;
    }

    public boolean wantsMoreFood() {
        return this.getFoodValueFromInventory() < 12;
    }

    private int getFoodValueFromInventory() {
        Inventory inventory = this.getVillagerInventory();
        return foodsource.entrySet().stream().mapToInt((p_226553_1_) -> {
            return inventory.count(p_226553_1_.getKey()) * p_226553_1_.getValue();
        }).sum();
    }

    private void dropCraftedBread() {
        Inventory inventory = this.getVillagerInventory();
        int i = inventory.count(Items.WHEAT);
        int j = i / 3;
        if (j != 0) {
            int k = j * 3;
            inventory.func_223374_a(Items.WHEAT, k);
            ItemStack itemstack = inventory.addItem(new ItemStack(ItemsInit.Beer, j));
            if (!itemstack.isEmpty()) {
                this.entityDropItem(itemstack, 0.5F);
            }

        }
    }

    /**
     * Returns true if villager has seeds, potatoes or carrots in inventory
     */
    public boolean isFarmItemInInventory() {
        Inventory inventory = this.getVillagerInventory();
        return inventory.hasAny(ImmutableSet.of(Items.WHEAT_SEEDS, Items.POTATO, Items.CARROT, Items.BEETROOT_SEEDS));
    }

    protected void populateTradeData() {

        Int2ObjectMap<DwarfTrades.ITrade[]> int2objectmap = DwarfTrades.TRADES.get(this.getProfession());
        if (int2objectmap != null && !int2objectmap.isEmpty()) {
            DwarfTrades.ITrade[] trades = int2objectmap.get(this.getLevel());
            if (trades != null) {
                MerchantOffers merchantoffers = this.getOffers();
                this.addTrades(merchantoffers, trades, 2);
            }
        }

    }

    protected void addTrades(MerchantOffers givenMerchantOffers, DwarfTrades.ITrade[] newTrades, int maxNumbers) {
        Set<Integer> set = Sets.newHashSet();
        if (newTrades.length > maxNumbers) {
            while(set.size() < maxNumbers) {
                set.add(this.rand.nextInt(newTrades.length));
            }
        } else {
            for(int i = 0; i < newTrades.length; ++i) {
                set.add(i);
            }
        }

        for(Integer integer : set) {
            DwarfTrades.ITrade villagertrades$itrade = newTrades[integer];
            MerchantOffer merchantoffer = villagertrades$itrade.getOffer(this, this.rand);
            if (merchantoffer != null) {
                givenMerchantOffers.add(merchantoffer);
            }
        }

    }

    public void func_213746_a(DwarfEntity p_213746_1_, long gametime) {

        if ((gametime < this.field_213783_bN || gametime >= this.field_213783_bN + 1200L) && (gametime < p_213746_1_.field_213783_bN || gametime >= p_213746_1_.field_213783_bN + 1200L)) {
            this.gossip.transferFrom(p_213746_1_.gossip, this.rand, 10);
            this.field_213783_bN = gametime;
            p_213746_1_.field_213783_bN = gametime;
            this.spawnlord(gametime, 5);
        }

    }

    private void func_223343_eC() {

        long i = this.world.getGameTime();
        if (this.lastGossipDecay == 0L) {
            this.lastGossipDecay = i;
        } else if (i >= this.lastGossipDecay + 24000L) {
            this.gossip.tick();
            this.lastGossipDecay = i;
        }

    }

    public void spawnlord(long gametime, int requiredpeers) {
        if (this.canSpawnlord(gametime)) {
            AxisAlignedBB axisalignedbb = this.getBoundingBox().grow(10.0D, 10.0D, 10.0D);
            List<DwarfEntity> list = this.world.getEntitiesWithinAABB(DwarfEntity.class, axisalignedbb);
            List<DwarfEntity> list1 = list.stream().filter((p_226554_2_) -> {
                return p_226554_2_.canSpawnlord(gametime);
            }).limit(5L).collect(Collectors.toList());
            if (list1.size() >= requiredpeers) {
                DwarfEntity irongolementity = this.SpawnLord();
                if (irongolementity != null) {
                    list.forEach((p_226552_2_) -> {
                        p_226552_2_.updateGolemLastSeenMemory(gametime);
                    });
                }
            }
        }

    }

    private void updateGolemLastSeenMemory(long gameTime) {
        this.brain.setMemory(MemoryModuleType.GOLEM_LAST_SEEN_TIME, gameTime);
    }

    private boolean hasSeenGolemRecently(long gameTime) {
        Optional<Long> optional = this.brain.getMemory(MemoryModuleType.GOLEM_LAST_SEEN_TIME);
        if (!optional.isPresent()) {
            return false;
        } else {
            Long olong = optional.get();
            return gameTime - olong <= 600L;
        }
    }

    public boolean canSpawnlord(long p_223350_1_) {
             this.getProfession();
        if (this.getProfession() != DwarfProfession.Warrior && this.getProfession() != DwarfProfession.Lord) {
            if (!this.haslevel() || hasspawnedlord) {
                return false;
            } else {
                return !this.hasSeenGolemRecently(p_223350_1_);
            }
        } else {
            return false;
        }
    }

    @Nullable
    private DwarfEntity SpawnLord() {
        BlockPos blockpos = new BlockPos(this);

        for(int i = 0; i < 10; ++i) {
            double d0 = (double)(this.world.rand.nextInt(16) - 8);
            double d1 = (double)(this.world.rand.nextInt(16) - 8);
            double d2 = 6.0D;

            for(int j = 0; j >= -12; --j) {
                BlockPos blockpos1 = blockpos.add(d0, d2 + (double)j, d1);
                if ((this.world.getBlockState(blockpos1).isAir() || this.world.getBlockState(blockpos1).getMaterial().isLiquid()) && this.world.getBlockState(blockpos1.down()).getMaterial().isOpaque()) {
                    d2 += (double)j;
                    break;
                }
            }

            BlockPos blockpos2 = blockpos.add(d0, d2, d1);
            DwarfEntity lord = Entityinit.DWARF.create(this.world, (CompoundNBT)null, (ITextComponent)null, (PlayerEntity)null, blockpos2, SpawnReason.MOB_SUMMONED, false, false);
            if (lord != null) {
                if (lord.canSpawn(this.world, SpawnReason.MOB_SUMMONED) && lord.isNotColliding(this.world)) {
                    this.world.addEntity(lord);
                    lord.setVillagerProfession(DwarfProfession.Lord);
                    this.hasspawnedlord=true;
                    return lord;
                }

                lord.remove();
            }
        }

        return null;
    }

    public void updateReputation(IReputationType type, Entity target) {
        if (type == IReputationType.TRADE) {
            this.gossip.add(target.getUniqueID(), GossipType.TRADING, 2);
        } else if (type == IReputationType.VILLAGER_HURT) {
            this.gossip.add(target.getUniqueID(), GossipType.MINOR_NEGATIVE, 25);
        } else if (type == IReputationType.VILLAGER_KILLED) {
            this.gossip.add(target.getUniqueID(), GossipType.MAJOR_NEGATIVE, 25);
        }

    }

    public int getXp() {
        return this.xp;
    }

    public void setXp(int p_213761_1_) {
        this.xp = p_213761_1_;
    }

    private void func_223718_eH() {
        this.func_223719_ex();
        this.field_223725_bO = 0;
    }

    public GossipManager getGossip() {
        return this.gossip;
    }

    public void func_223716_a(INBT p_223716_1_) {
        this.gossip.deserialize(new Dynamic<>(NBTDynamicOps.INSTANCE, p_223716_1_));
    }

    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPacketSender.sendLivingEntity(this);
    }

    public void startSleeping(BlockPos pos) {
        super.startSleeping(pos);
        this.brain.setMemory(MemoryModuleType.LAST_SLEPT, LongSerializable.of(this.world.getGameTime()));
    }

    public void wakeUp() {
        super.wakeUp();
        this.brain.setMemory(MemoryModuleType.field_226332_A_, LongSerializable.of(this.world.getGameTime()));
    }

    private boolean haslevel() {
        return this.getLevel()==5;
    }

    /** personal code start
     *
     *
     */
    public int getProfessionID()
    {
        return Math.max(this.dataManager.get(Profession), 0);//0farmer,1miner,2Engineer,3builder,4slayer,5lord,6 warrior
    }

    public void setVillagerData() {
        this.setVillagerData(getProfessionID(),getLevel());
    }

    public void setVillagerDataBase() {
        this.setVillagerData(DwarfProfession.Warrior.getID(),1);
    }

    public void setVillagerData(int prof,int level) {

        if (getProfession().getID() != prof) {
            this.offers = null;
        }
        if(dataManager.get(Profession)!=DwarfProfession.Lord.getID()){
        this.dataManager.set(Profession,prof);}
        this.dataManager.set(Level,level);
    }

    public void registerVillagerData() {

        this.dataManager.register(Profession, DwarfProfession.Warrior.getID());
        this.dataManager.register(Level,1);
    }

    public void setVillagerlevel(int level) {
        setVillagerData(getProfessionID(),level);
    }

    public void setVillagerProfession(DwarfProfession profession){
        setVillagerData(profession.getID(),getLevel());
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

    public int getLevel(){
        return Math.max(this.dataManager.get(Level),1);
    }

    public void setProfession(int professionId)
    {
        this.dataManager.set(Profession, professionId);
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

    @OnlyIn(Dist.CLIENT)
    public AbstractIllagerEntity.ArmPose getArmPose() {
        if (this.isAggressive()){
            return AbstractIllagerEntity.ArmPose.ATTACKING;
        } else {
            return AbstractIllagerEntity.ArmPose.CROSSED;
        }
    }

    public Boolean isBattleready() {
        return battlestate>0;
    }

    private int battlestate;

    public void setBattlestate(int amount){
        battlestate=amount;
    }

    protected void setEquipment(DwarfProfession prof)
    {
        if(hasItemInSlot(EquipmentSlotType.MAINHAND)){
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND,ItemStack.EMPTY);
        }
        if(hasItemInSlot(EquipmentSlotType.OFFHAND)){
            this.setItemStackToSlot(EquipmentSlotType.OFFHAND,ItemStack.EMPTY);
        }
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND,new ItemStack(prof.getItemtoslot(0)));
        this.setItemStackToSlot(EquipmentSlotType.OFFHAND,new ItemStack(prof.getItemtoslot(1)));
    }

    public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) {
            if(battlestate>0)battlestate--;
        }
        if(isAggressive() && !world.isRemote) {
            makesound();

        }

    }

    private int randomsounddelay;
    private void makesound(){
        if (this.randomsounddelay > 0 && --this.randomsounddelay == 0) {
            this.playSound(SoundEvents.ENTITY_VILLAGER_NO, this.getSoundVolume() * 1.5F, getSoundPitch());
        }
            if(randomsounddelay<=0)
            randomsounddelay=this.rand.nextInt(40);
            else randomsounddelay--;
    }

}