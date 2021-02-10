package warhammermod.Items.melee;


import net.minecraft.block.DispenserBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import warhammermod.Items.GunBase;
import warhammermod.Items.Ranged.GunSwordTemplate;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.reference;

import java.util.UUID;

public class shieldtemplate extends ShieldItem {


    private Boolean issprinting=false;
    private int Type; // type 0 = can sprint , type 1 = can block
    private static final UUID BLOCKING_SPEED_BOOST_ID = UUID.fromString("662A6B8D-DA3B-4F1C-8813-96EA6097278D");
    private static final AttributeModifier BLOCKING_SPEED_BOOST = (new AttributeModifier(BLOCKING_SPEED_BOOST_ID, "blocking speed boost", 1.1000001192092896D, AttributeModifier.Operation.MULTIPLY_TOTAL)).setSaved(false);
    private static final UUID SPRINT_SPEED_BOOST_ID = UUID.fromString("662A6B8D-DA3C-4F1D-8814-96EA6197278D");
    private static final AttributeModifier SPRINT_SPEED_BOOST = (new AttributeModifier(BLOCKING_SPEED_BOOST_ID, "sprint speed boost", 3.000001192092896D, AttributeModifier.Operation.MULTIPLY_TOTAL)).setSaved(false);



    public shieldtemplate(int type, Item.Properties builder){

        super(builder.maxDamage(1008).group(reference.warhammer));
        ItemsInit.ITEMS.add(this);
        Type=type;
        this.addPropertyOverride(new ResourceLocation("blocking"), (p_210314_0_, p_210314_1_, p_210314_2_) -> {
            return (p_210314_2_ != null && p_210314_2_.isHandActive() && (p_210314_2_.getActiveItemStack() == p_210314_0_ || (p_210314_2_.getHeldItemOffhand() == p_210314_0_ & hasshield(p_210314_2_,p_210314_2_.getHeldItemMainhand()) )))? 1.0F : 0.0F;
        });
        DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);


    }

    private boolean hasshield(LivingEntity entityIn, ItemStack stack){
        if(entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entityIn;
            if (stack.getItem() instanceof GunBase && ((GunBase) stack.getItem()).hasshield(player) &&  ((GunBase) stack.getItem()).isReadytoFire(stack))
                return true;
            return stack.getItem() instanceof GunSwordTemplate && ((GunSwordTemplate) stack.getItem()).hasshield(player) && ((GunSwordTemplate) stack.getItem()).isReadytoFire(stack);
        }return false;
    }


    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if(player instanceof PlayerEntity) {
            issprinting = player.world.isRemote && Minecraft.getInstance().gameSettings.keyBindSprint.isKeyDown();
            if (Type == 0) {
                IAttributeInstance iattributeinstance = player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

                if (iattributeinstance.getModifier(BLOCKING_SPEED_BOOST_ID) != null) {
                    iattributeinstance.removeModifier(BLOCKING_SPEED_BOOST);
                }
                if (issprinting) {
                    if (iattributeinstance.getModifier(SPRINT_SPEED_BOOST_ID) != null) {
                        iattributeinstance.removeModifier(SPRINT_SPEED_BOOST);
                    }
                    iattributeinstance.applyModifier(SPRINT_SPEED_BOOST);
                } else iattributeinstance.applyModifier(BLOCKING_SPEED_BOOST);
            }
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(itemstack);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if(entityLiving instanceof PlayerEntity && Type==0) {
            IAttributeInstance iattributeinstance = entityLiving.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            iattributeinstance.removeModifier(BLOCKING_SPEED_BOOST);
            iattributeinstance.removeModifier(SPRINT_SPEED_BOOST);

        }

    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(entityIn instanceof PlayerEntity){
            LivingEntity player = (LivingEntity) entityIn;
            if(!(player.getActiveItemStack().getItem() instanceof shieldtemplate)) {


                IAttributeInstance iattributeinstance = player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
                if (iattributeinstance.getModifier(BLOCKING_SPEED_BOOST_ID) != null) {
                    iattributeinstance.removeModifier(BLOCKING_SPEED_BOOST);
                }
                if(iattributeinstance.getModifier(SPRINT_SPEED_BOOST_ID)!=null) {
                    iattributeinstance.removeModifier(SPRINT_SPEED_BOOST);
                }

            }

        }
    }

    public int gettype(){
        return Type;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return ItemTags.PLANKS.contains(repair.getItem()) || super.getIsRepairable(toRepair, repair);
    }





}
