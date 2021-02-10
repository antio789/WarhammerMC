package warhammermod.Items.melee;


import net.minecraft.block.DispenserBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemModelsProperties;
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
    private static final AttributeModifier BLOCKING_SPEED_BOOST = (new AttributeModifier(BLOCKING_SPEED_BOOST_ID, "blocking speed boost", 1.1000001192092896D, AttributeModifier.Operation.MULTIPLY_TOTAL));
    private static final UUID SPRINT_SPEED_BOOST_ID = UUID.fromString("662A6B8D-DA3C-4F1D-8814-96EA6197278D");
    private static final AttributeModifier SPRINT_SPEED_BOOST = (new AttributeModifier(BLOCKING_SPEED_BOOST_ID, "sprint speed boost", 3.000001192092896D, AttributeModifier.Operation.MULTIPLY_TOTAL));



    public shieldtemplate(int type, Properties builder){

        super(builder.maxDamage(1008).group(reference.warhammer));
        ItemsInit.ITEMS.add(this);
        Type=type;
        ItemModelsProperties.func_239418_a_(this,new ResourceLocation("blocking"),(p_210314_0_, p_210314_1_, p_210314_2_) -> {
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
        if(player instanceof PlayerEntity && Type==0) {
            issprinting = player.world.isRemote && Minecraft.getInstance().gameSettings.keyBindSprint.isKeyDown();

            ModifiableAttributeInstance modifiableattributeinstance = player.getAttribute(Attributes.field_233821_d_);
            if (modifiableattributeinstance.getModifier(BLOCKING_SPEED_BOOST_ID) != null) {
                modifiableattributeinstance.removeModifier(BLOCKING_SPEED_BOOST);
            }
            if (modifiableattributeinstance.getModifier(SPRINT_SPEED_BOOST_ID) != null) {
                modifiableattributeinstance.removeModifier(SPRINT_SPEED_BOOST);
            }
            if (issprinting) {
                modifiableattributeinstance.func_233767_b_(SPRINT_SPEED_BOOST);
            }else modifiableattributeinstance.func_233767_b_(BLOCKING_SPEED_BOOST);
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(itemstack);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if(entityLiving instanceof PlayerEntity && Type==0) {
            ModifiableAttributeInstance modifiableattributeinstance = entityLiving.getAttribute(Attributes.field_233821_d_);
            if (modifiableattributeinstance.getModifier(BLOCKING_SPEED_BOOST_ID) != null) {
                modifiableattributeinstance.removeModifier(BLOCKING_SPEED_BOOST);
            }
            if (modifiableattributeinstance.getModifier(SPRINT_SPEED_BOOST_ID) != null) {
                modifiableattributeinstance.removeModifier(SPRINT_SPEED_BOOST);
            }

        }

    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(entityIn instanceof PlayerEntity){
            LivingEntity player = (LivingEntity) entityIn;
            if(!(player.getActiveItemStack() == stack)) {

                ModifiableAttributeInstance modifiableattributeinstance = player.getAttribute(Attributes.field_233821_d_);
                if (modifiableattributeinstance.getModifier(BLOCKING_SPEED_BOOST_ID) != null) {
                    modifiableattributeinstance.removeModifier(BLOCKING_SPEED_BOOST);
                }
                if (modifiableattributeinstance.getModifier(SPRINT_SPEED_BOOST_ID) != null) {
                    modifiableattributeinstance.removeModifier(SPRINT_SPEED_BOOST);
                }

            }

        }
    }

    public int gettype(){
        return Type;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return ItemTags.PLANKS.func_230235_a_(repair.getItem()) || super.getIsRepairable(toRepair, repair);
    }





}
