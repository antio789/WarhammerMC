package warhammermod.Items.melee;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import warhammermod.Entities.HalberdEntity;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.reference;

public class Halberdtemplate extends SwordItem {
    private float attackDamage;
    private float attackSpeed;
    private final Multimap<Attribute, AttributeModifier> field_234674_d_;


    public Halberdtemplate(IItemTier tier){
        super(tier, 5,-2.8F,(new Properties()).group(reference.warhammer));
        this.attackSpeed = -2.8F;
        this.attackDamage = 4.5F + tier.getAttackDamage();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.field_233823_f_, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.field_233825_h_, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION));
        this.field_234674_d_ = builder.build();
        ItemsInit.ITEMS.add(this);
        ItemModelsProperties.func_239418_a_(this,new ResourceLocation("powerhit"),(p_239429_0_, p_239429_1_, p_239429_2_) -> {
            if (p_239429_2_ == null) {
                return 0.0F;
            } else {
                return p_239429_2_.getActiveItemStack() != p_239429_0_ ? 0.0F : (float)( p_239429_2_.getItemInUseCount() <980? 1.0F : 0.0F );
            }
        });
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (itemstack.getDamage() >= itemstack.getMaxDamage() - 2) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if(getUseDuration(stack)-20>=timeLeft && entityLiving instanceof PlayerEntity){
            PlayerEntity playerIn = (PlayerEntity)entityLiving;

            HalberdEntity entity = new HalberdEntity(worldIn, playerIn, stack,getAttackDamage()*1.3F);
            entity.shoot(playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3, 0.5F);
            if (playerIn.abilities.isCreativeMode) {
                entity.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
            }

            worldIn.addEntity(entity);
            worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, SoundCategory.PLAYERS, 1.0F, 1.0F);



            stack.damageItem(2,entityLiving, (p_220040_1_) -> {
                p_220040_1_.sendBreakAnimation(playerIn.getActiveHand());
            });
            playerIn.getCooldownTracker().setCooldown(this,60);

        }
    }

    public int getUseDuration(ItemStack stack) {
        return 1000;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.field_234674_d_ : super.getAttributeModifiers(equipmentSlot);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
}
