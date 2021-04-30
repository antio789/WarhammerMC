package warhammermod.Items.melee;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import warhammermod.utils.inithandler.ItemsInit;
import warhammermod.utils.reference;

import java.util.UUID;

public class SmallShieldTemplate extends ShieldItem {
    private Boolean issprinting=false;

    private static final UUID BLOCKING_SPEED_BOOST_ID = UUID.fromString("662A6B8D-DA3B-4F1C-8813-96EA6097278D");
    private static final AttributeModifier BLOCKING_SPEED_BOOST = new AttributeModifier(BLOCKING_SPEED_BOOST_ID, "blocking speed boost", 2D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final UUID SPRINT_SPEED_BOOST_ID = UUID.fromString("662A6B8D-DA3C-4F1D-8814-96EA6197278D");
    private static final AttributeModifier SPRINT_SPEED_BOOST = (new AttributeModifier(SPRINT_SPEED_BOOST_ID, "sprint speed boost", 3.5D, AttributeModifier.Operation.MULTIPLY_TOTAL));


    public SmallShieldTemplate(Properties builder){
        super(builder.durability(1008).tab(reference.warhammer));
        ItemsInit.ITEMS.add(this);
        ItemModelsProperties.register(this, new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_, p_239421_2_) -> {
            return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F;
        });
    }

    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if(player instanceof PlayerEntity) {

            issprinting = player.level.isClientSide() && Minecraft.getInstance().options.keySprint.isDown();
            ModifiableAttributeInstance modifiableattributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (modifiableattributeinstance.getModifier(BLOCKING_SPEED_BOOST_ID) != null) {
                modifiableattributeinstance.removeModifier(BLOCKING_SPEED_BOOST);
            }
            if (modifiableattributeinstance.getModifier(SPRINT_SPEED_BOOST_ID) != null) {
                modifiableattributeinstance.removeModifier(SPRINT_SPEED_BOOST);
            }
            if (issprinting) {
                modifiableattributeinstance.addPermanentModifier(SPRINT_SPEED_BOOST);
            }

            else {modifiableattributeinstance.addPermanentModifier(BLOCKING_SPEED_BOOST);
            }

        }
    }

    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)  {
        if(entityLiving instanceof PlayerEntity) {
            ModifiableAttributeInstance modifiableattributeinstance = entityLiving.getAttribute(Attributes.MOVEMENT_SPEED);
            if (modifiableattributeinstance.getModifier(BLOCKING_SPEED_BOOST_ID) != null) {
                modifiableattributeinstance.removeModifier(BLOCKING_SPEED_BOOST);
            }
            if (modifiableattributeinstance.getModifier(SPRINT_SPEED_BOOST_ID) != null) {
                modifiableattributeinstance.removeModifier(SPRINT_SPEED_BOOST);
            }

        }

    }

    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(entityIn instanceof PlayerEntity){
            LivingEntity player = (LivingEntity) entityIn;
            if(!(player.getUseItem() == stack)) {
                ModifiableAttributeInstance modifiableattributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
                if (modifiableattributeinstance.getModifier(BLOCKING_SPEED_BOOST_ID) != null) {

                    modifiableattributeinstance.removeModifier(BLOCKING_SPEED_BOOST);
                }
                if (modifiableattributeinstance.getModifier(SPRINT_SPEED_BOOST_ID) != null) {

                    modifiableattributeinstance.removeModifier(SPRINT_SPEED_BOOST);
                }
            }

        }
    }
}
