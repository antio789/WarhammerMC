package warhammermod.Items.melee;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import warhammermod.Entities.Projectile.HalberdEntity;
import warhammermod.utils.inithandler.ItemsInit;
import warhammermod.utils.reference;

public class Halberdtemplate extends SwordItem {
    private final float attackDamage;
    private final float attackSpeed;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;


    public Halberdtemplate(IItemTier tier){
        super(tier, 5,-2.8F,(new Properties()).tab(reference.warhammer));
        this.attackSpeed = -2.8F;
        this.attackDamage = 4.5F + tier.getAttackDamageBonus();
       ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
        ItemsInit.ITEMS.add(this);
        ItemModelsProperties.register(this,new ResourceLocation("powerhit"),(stack, clientWorld, entity) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : entity.getTicksUsingItem() <980? 1.0F : 0.0F;
            }
        });
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity playerIn, Hand hand) {
        ItemStack itemstack = playerIn.getItemInHand(hand);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 2) {
            return ActionResult.fail(itemstack);
        } else {
            playerIn.startUsingItem(hand);
            return ActionResult.consume(itemstack);
        }
    }

    public void releaseUsing(ItemStack stack, World world, LivingEntity entityLiving, int timeLeft) {
        if(getUseDuration(stack)-20>=timeLeft && entityLiving instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity)entityLiving;

            HalberdEntity entity = new HalberdEntity(player,world, getDamage()*1.3F);
            entity.shoot(player.xRot, player.yRot, 0.0F, 3F, 0.5F);

            world.addFreshEntity(entity);
            world.playSound(null,player.blockPosition(),SoundEvents.PLAYER_ATTACK_KNOCKBACK,SoundCategory.PLAYERS,1,1);

            stack.hurtAndBreak(1, player, (p_220009_1_) -> {
                p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
            });
            player.getCooldowns().addCooldown(this,60);
        }
    }

    public int getUseDuration(ItemStack stack) {
        return 1000;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType p_111205_1_) {
        return p_111205_1_ == EquipmentSlotType.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_111205_1_);
    }

    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.SPEAR;
    }

    public float getDamage() {
        return this.attackDamage;
    }
}
