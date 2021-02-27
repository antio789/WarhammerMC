package warhammermod.Entities.living;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Entities.EntityStone;
import warhammermod.Entities.EntityWarpBullet;
import warhammermod.Entities.living.Emanager.EntityAIAttackRangedSkaven;
import warhammermod.Items.Ranged.RatlingGun;
import warhammermod.Items.Ranged.SkavenGuns;
import warhammermod.Items.Ranged.slingtemplate;
import warhammermod.util.Handler.Loottable;
import warhammermod.util.Handler.inithandler.Itemsinit;
import warhammermod.util.Handler.soundhandler.sounds;
import warhammermod.util.utils;
import warhammermod.worldgen.MapGenDwarfVillage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

import static warhammermod.util.reference.*;


public class EntitySkaven extends EntityMob implements IRangedAttackMob {

    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(EntitySkaven.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Float> Slingprogress = EntityDataManager.createKey(EntitySkaven.class, DataSerializers.FLOAT);
    private final EntityAIAttackRangedSkaven<EntitySkaven> aiRangedAttack = new EntityAIAttackRangedSkaven<>(this, 1.0D, 20, 15.0F);


    public static final DataParameter<String> SkavenType = EntityDataManager.<String>createKey(EntitySkaven.class,DataSerializers.STRING);
    private static ArrayList<String> Types = new ArrayList<String>(Arrays.asList(slave,clanrat,stormvermin,gutter_runner,globadier,ratling_gunner));
    private ArrayList<Float> SkavenSize = new ArrayList<Float>(Arrays.asList(1.1F,1.2F,1.3F,1.2F,1.2F,1.2F));
    private ArrayList<Integer> Spawnchance = new ArrayList<Integer>(Arrays.asList(38,0,0,0,0,0));//3
    //private ArrayList<Integer> Spawnchance = new ArrayList<Integer>(Arrays.asList(38,27,15,7,4,4));//3
    private ArrayList<Float> reinforcementchance = new ArrayList<Float>(Arrays.asList(0.08F,0.1F,0.14F,0F,0.08F,0.11F));
    private ArrayList<Integer> firerate = new ArrayList<Integer>(Arrays.asList(28,38,0,0,37,6));
    public static ItemStack[][][] SkavenEquipment =  {{{utils.getRandomspear(4)},{new ItemStack(Itemsinit.sling)}},
                                            {{utils.getRandomsword(4),new ItemStack(Itemsinit.SKAVEN_SHIELD)},{new ItemStack(Itemsinit.WarpLock_Jezzail)}},
                                            {{utils.getRandomHalberd(4)},{utils.getRandomsword(5),new ItemStack(Itemsinit.SKAVEN_SHIELD)}},
                                            {{new ItemStack(Itemsinit.iron_knife),new ItemStack(Itemsinit.iron_knife)}},
                                            {{}},
                                            {{new ItemStack(Itemsinit.Ratling_Gun)}},
    };
    private boolean fixgame;


    public EntitySkaven(World worldin){
        super(worldin);
        this.setSize(0.6F, getSkavenSize()+0.5F);
        fixgame=true;
    }

    protected ResourceLocation getLootTable() {return Loottable.Skaven;}

    //private ResourceLocation getloottablefromtype(){ }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        this.dataManager.set(SkavenType,setSkavenType());
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        setCombatTask();
        setSize(0.6F,getSkavenSize()+0.5F);

        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);

        if(getSkaventype().equals(slave)){
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D);
        }
        else if(getSkaventype().equals(clanrat)){
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.5D);
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
            this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        }
        else if(getSkaventype().equals(stormvermin)){
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
            this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5D);
        }else if(getSkaventype().equals(gutter_runner)){
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
        }

        return super.onInitialSpawn(difficulty,livingdata);
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        int typepos = Math.max(Types.indexOf(dataManager.get(SkavenType)),0);
        int weapontype;
        if(SkavenEquipment[typepos].length<2){
            weapontype = 0;
        }
        else {
            weapontype = this.rand.nextInt(2);
        }
        if(SkavenEquipment[typepos][weapontype].length>0){
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND,SkavenEquipment[typepos][weapontype][0]);
        if(SkavenEquipment[typepos][weapontype].length>1){
            this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND,SkavenEquipment[typepos][weapontype][1]);
        }
        }

    }


    public static void registerFixes(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntitySkaven.class);
    }

    public void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        //this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityDwarf.class, true));
    }

    public void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);
        compound.setString("SkavenType",getSkaventype());
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setSkavenType(compound.getString("SkavenType"));
    }

    public void applyEntityAttributes()
    {
        super.applyEntityAttributes();
    }

    public float getEyeHeight()
    {
        return this.height * 0.95F;
    }

    public void entityInit()
    {
        super.entityInit();
        this.dataManager.register(SWINGING_ARMS, Boolean.valueOf(false));
        this.dataManager.register(SkavenType,Types.get(0));
        this.dataManager.register(Slingprogress, 0F);
    }



    public void onLivingUpdate(){
        if(fixgame){
            setCombatTask();
            this.setSize(0.6F, getSkavenSize()*1.1F+0.5F);
            fixgame=false;
        }
        super.onLivingUpdate();}

    protected SoundEvent getAmbientSound()
    {
        return sounds.ratambient;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return sounds.rathurt;
    }

    protected SoundEvent getDeathSound()
    {
        return sounds.ratdeath;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected float getSoundPitch()
    {
        return 0.4F +this.rand.nextFloat()*0.5F;
    }//(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F
    private void setSkavenType(String type){
        this.dataManager.set(SkavenType,type);
    }

    private String setSkavenType(){
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

    public float getSkavenSize(){
       int typepos = Math.max(Types.indexOf(dataManager.get(SkavenType)),0);
       return SkavenSize.get(typepos);
    }
    public int getSkavenTypePosition(){
        int typepos = Math.max(Types.indexOf(dataManager.get(SkavenType)),0);
        return typepos;
    }


    public String getSkaventype(){
        return dataManager.get(SkavenType);
    }

    @SideOnly(Side.CLIENT)
    public boolean isSwingingArms()
    {
        return (this.dataManager.get(SWINGING_ARMS));
    }

    public void setSwingingArms(boolean swingingArms)
    {
        this.dataManager.set(SWINGING_ARMS, Boolean.valueOf(swingingArms));
    }

    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
        if(this.getHeldItemMainhand().getItem() instanceof SkavenGuns){
            attackEntitywithrifle(target,7,11);
        }
        else if(this.getHeldItemMainhand().getItem() instanceof RatlingGun){
            attackEntitywithrifle(target,5,26);
        }
        else if(this.getHeldItemMainhand().getItem() instanceof slingtemplate){
            attackEntitywithstone(target,15);
        }
        else if(getSkaventype().equals(globadier)){
            attackpotions(target);
        }
    }
    private void attackpotions(EntityLivingBase target){
        double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.posX + target.motionX - this.posX;
        double d2 = d0 - this.posY;
        double d3 = target.posZ + target.motionZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
        PotionType potiontype;
        potiontype = rand.nextBoolean() ? PotionTypes.HARMING : PotionTypes.POISON;
        EntityPotion entitypotion = new EntityPotion(this.world, this, PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), potiontype));
        entitypotion.rotationPitch -= -20.0F;
        entitypotion.shoot(d1, d2 + (double)(f * 0.2F), d3, 0.75F, 8.0F);
        this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
        this.world.spawnEntity(entitypotion);
    }
    private void attackEntitywithrifle(EntityLivingBase target,int damage, float inaccuracy){
        EntityWarpBullet bullet = new EntityWarpBullet(world,this,damage);//7
        int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER,this);
        if (j > 0) {
            bullet.setpowerDamage(j);
        }
        int k = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, this) + 1;
        if (k > 0) {
            bullet.setknockbacklevel(k);
        }
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - bullet.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        bullet.shoot(d0,d1+d3*0.06,d2,3,(float)(inaccuracy - this.world.getDifficulty().getDifficultyId() * 4));//inac14
        this.playSound( SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 2F / (rand.nextFloat() * 0.4F + 1.2F) + 0.5F);
        this.world.spawnEntity(bullet);

    }
    private void attackEntitywithstone(EntityLivingBase target, float inaccuracy){
        EntityStone stone = new EntityStone(world,this,3);

        int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, this);
        if (j > 0) {
            stone.setDamage(stone.getDamage() + (double)j * 0.5D + 0.5D);
        }
        int k = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, this) + 1;
        if (k > 0) {
            stone.setKnockbackStrength(k);
        }

        stone.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;

        if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, this) > 0) {
            stone.setFire(100);
        }
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - stone.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        stone.shoot(d0,d1+d3*0.2,d2,1.3F,(float)(inaccuracy - this.world.getDifficulty().getDifficultyId() * 4));
        this.playSound( SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 1.2F));
        this.world.spawnEntity(stone);

    }

    private final EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false)
    {
        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            super.resetTask();
            EntitySkaven.this.setSwingingArms(false);
        }
        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            super.startExecuting();
            EntitySkaven.this.setSwingingArms(true);

        }
    };


    public void setCombatTask()
    {
        if (this.world != null && !this.world.isRemote)
        {
            this.tasks.removeTask(this.aiAttackOnCollide);
            this.tasks.removeTask(this.aiRangedAttack);
            ItemStack item = this.getHeldItemMainhand();
            if (item.getItem() instanceof SkavenGuns || item.getItem() instanceof RatlingGun || item.getItem() instanceof slingtemplate)
            {


                int i=firerate.get(getSkavenTypePosition());
                this.aiRangedAttack.setAttackCooldown(i);
                this.tasks.addTask(4, this.aiRangedAttack);
            }else if(getSkaventype().equals(globadier)){
                this.tasks.addTask(4,new EntityAIAttackRanged(this, 1.0D, 60, 10.0F));
            }
            else
            {
                this.tasks.addTask(4, this.aiAttackOnCollide);
            }
        }
    }
    public boolean canBeHitWithPotion()
    {
        return !getSkaventype().equals(globadier);
    }


    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (super.attackEntityFrom(source, amount))
        {
            EntityLivingBase entitylivingbase = this.getAttackTarget();

            if (entitylivingbase == null && source.getTrueSource() instanceof EntityLivingBase)
            {
                entitylivingbase = (EntityLivingBase)source.getTrueSource();
            }

            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY);
            int k = MathHelper.floor(this.posZ);
            if(entitylivingbase instanceof EntityPlayer && ((EntityPlayer) entitylivingbase).isCreative()){
                return true;
            }
            if (entitylivingbase != null && this.world.getDifficulty() == EnumDifficulty.HARD && (double)this.rand.nextFloat() < reinforcementchance.get(getSkavenTypePosition())/2 && this.world.getGameRules().getBoolean("doMobSpawning"))
            {

                EntitySkaven skaven;

                skaven = new EntitySkaven(this.world);



                for (int l = 0; l < 50; ++l)
                {
                    int i1 = i + MathHelper.getInt(this.rand, 7, 20) * MathHelper.getInt(this.rand, -1, 1);
                    int j1 = j + MathHelper.getInt(this.rand, 7, 20) * MathHelper.getInt(this.rand, -1, 1);
                    int k1 = k + MathHelper.getInt(this.rand, 7, 20) * MathHelper.getInt(this.rand, -1, 1);

                    if (this.world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(this.world, new BlockPos(i1, j1 - 1, k1), net.minecraft.util.EnumFacing.UP) && this.world.getLightFromNeighbors(new BlockPos(i1, j1, k1)) < 10)
                    {
                        skaven.setPosition((double)i1, (double)j1, (double)k1);
                        skaven.onInitialSpawn(world.getDifficultyForLocation(new BlockPos((double)i1, (double)j1, (double)k1)), (IEntityLivingData)null);

                        if (!this.world.isAnyPlayerWithinRangeAt((double)i1, (double)j1, (double)k1, 7.0D) && this.world.checkNoEntityCollision(skaven.getEntityBoundingBox(), skaven) && this.world.getCollisionBoxes(skaven, skaven.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(skaven.getEntityBoundingBox()))
                        {
                            this.world.spawnEntity(skaven);
                            if (entitylivingbase != null) skaven.setAttackTarget(entitylivingbase);
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

    public boolean getCanSpawnHere()
    {
        BlockPos blockpos = new BlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));

                Biome biome = this.world.getBiome(blockpos);

                if (MapGenDwarfVillage.VILLAGE_SPAWN_BIOMES.contains(biome))
                {
                    return super.getCanSpawnHere();
                }

                else if (this.posY < 37.0D)
                {
                    return super.getCanSpawnHere();
                }


            return false;

    }

    @Override
    public boolean isActiveItemStackBlocking()
    {
        if(this.getHeldItemOffhand().getItem() instanceof ItemShield && getAttackTarget()!=null && this.rand.nextInt(10)<4){
            playSound(SoundEvents.ITEM_SHIELD_BLOCK,1,1);
            return true;
        }
        else return false;
    }

}
