package warhammermod.Items.Melee.special;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Items.Melee.heavytemplate;
import javax.annotation.Nullable;
import java.util.List;



public class GhalMaraz extends heavytemplate {
    ToolMaterial Material;
    public GhalMaraz(String name, ToolMaterial material, float damage, float attspeed, boolean enabled) {
        super(name, material, damage, attspeed);
        setMaxDamage((int) (material.getMaxUses() * 1.4));
        Material=material;
        this.addPropertyOverride(new ResourceLocation("powerhit"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && entityIn.getItemInUseCount()<(stack.getMaxItemUseDuration()-20)? 1.0F : 0.0F;
            }
        });
    }

    public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker) {
        return true;
    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(!itemstack.equals(playerIn.getHeldItemMainhand()))return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);;
        boolean flag = true;
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) {
            return ret;
        }
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        super.onUsingTick(stack, player, count);
    }



    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if( entityLiving instanceof EntityPlayer && stack.equals(entityLiving.getHeldItemMainhand()) && stack.getMaxItemUseDuration()-timeLeft>20){
            //Minecraft.getMinecraft().entityRenderer.displayItemActivation(stack);
            updated=8;
            EntityPlayer player = (EntityPlayer) entityLiving;
            applyeffects(stack,player,worldIn);
            boolean hitsomething = false;
            for (EntityLivingBase targets : worldIn.getEntitiesWithinAABB(EntityLivingBase.class, entityLiving.getEntityBoundingBox().grow(3D, 0.25D, 3D)))
            {
                if (targets != player && entityLiving.getDistanceSq(targets) < 9.0D)
                {
                    float f = (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                    Vec2f vec = calculatevector(player,targets);
                    targets.knockBack(player, 2.5F + EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack), vec.x, vec.y);
                    targets.attackEntityFrom(DamageSource.causePlayerDamage(player), f);
                    hitsomething=true;
                }
            }
            stack.damageItem(3, player);
            if(hitsomething) {
                worldIn.playSound( null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, player.getSoundCategory(), 0.5F, 1.0F);
            }
        }
    }


    public boolean applyeffects(ItemStack stack, EntityPlayer player,World world) {

        if (!world.isRemote && !player.getCooldownTracker().hasCooldown(stack.getItem()))
        {
            if (player.world.rand.nextFloat() < 0.8F)
            {
                if (player.world.rand.nextFloat() < 0.4F)
                {
                    if (player.world.rand.nextFloat() < 0.3F)
                    {
                        handleeffects(player,world,true);
                        applypotioneffect(world,player,2);
                        return true;
                    }
                    handleeffects(player,world,true);
                    applypotioneffect(world,player,1);
                    return true;
                }
                handleeffects(player,world,true);
                applypotioneffect(world,player,0);
                return true;
            }
            handleeffects(player,world,false);
            return false;
        }
        return false;
    }

    public void applypotioneffect(World wprmd,EntityPlayer player,int level){
            if(level ==0){
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,150,0));
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,300,0));
                player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,300,0));
                player.getCooldownTracker().setCooldown(this, 6);
            }
            else if(level ==1){
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,200,1));
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,400,1));
                player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,300,1));
                player.getCooldownTracker().setCooldown(this, 6);
            }
            else if(level ==2){
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,300,1));
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,500,1));
                player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,350,1));
                player.getCooldownTracker().setCooldown(this, 6);
            }
    }

    public  void handleeffects(EntityPlayer player,World world,Boolean succes){
        if(succes) {
            player.sendStatusMessage(new TextComponentTranslation("item.ghal_maraz.succes"), true);
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, player.getSoundCategory(), 0.5F, 1.6F);
            }
        else {
            player.sendStatusMessage(new TextComponentTranslation("item.ghal_maraz.fail"), true);
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.UI_TOAST_OUT, player.getSoundCategory(), 0.5F, 1.5F);
        }
    }
    PotionEffect[] Effect = {new PotionEffect(MobEffects.RESISTANCE,250,0),new PotionEffect(MobEffects.RESISTANCE,400,1),new PotionEffect(MobEffects.RESISTANCE,450,1)} ;

    int updated=-1;
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(worldIn.isRemote && entityIn instanceof  EntityPlayer){
            if(updated==0) {
                updated=-1;
                EntityPlayer player = (EntityPlayer) entityIn;
                if (haseffects(player)) {
                    Minecraft.getMinecraft().effectRenderer.emitParticleAtEntity(player, EnumParticleTypes.TOTEM, 30);
                } else
                    Minecraft.getMinecraft().effectRenderer.emitParticleAtEntity(player, EnumParticleTypes.SPELL_INSTANT, 25);
            }else if(updated>0) updated--;
        }
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    private Boolean haseffects(EntityPlayer player){
        return player.isPotionActive(MobEffects.REGENERATION) && player.isPotionActive(MobEffects.RESISTANCE) && player.isPotionActive(MobEffects.STRENGTH);
    }



    private Vec2f calculatevector(Entity e1, Entity e2){
        float x = (float)(e1.posX - e2.posX);
        float z = (float)(e1.posZ - e2.posZ);
        float r = (float)(Math.sqrt(Math.pow(x,2)+Math.pow(z,2)));
        System.out.println(x+"  x  "+ z +"  z");
        return new Vec2f(x/r,z/r);
    }

    public int getItemEnchantability()
    {
        return this.Material.getEnchantability()+15;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }

    public int getMaxItemUseDuration(ItemStack stack) { return 72000; }
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add("the legendary hammer, can give you effects in combat");
    }
}