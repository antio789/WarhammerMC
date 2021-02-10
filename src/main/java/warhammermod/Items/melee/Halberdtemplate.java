package warhammermod.Items.melee;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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


    public Halberdtemplate(IItemTier tier){
        super(tier, 5,-2.8F,(new Item.Properties()).group(reference.warhammer));
        this.attackSpeed = -2.8F;
        this.attackDamage = 4.5F + tier.getAttackDamage();
        ItemsInit.ITEMS.add(this);
        this.addPropertyOverride(new ResourceLocation("powerhit"), (p_210309_0_, p_210309_1_, p_210309_2_) -> {
            return p_210309_2_ != null && p_210309_2_.isHandActive() && p_210309_2_.getActiveItemStack() == p_210309_0_ && p_210309_2_.getItemInUseCount()<980? 1.0F : 0.0F;
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
            entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3, 0.5F);
            if (playerIn.abilities.isCreativeMode) {
                entity.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
            }

            worldIn.addEntity(entity);
            worldIn.playSound(null, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, SoundCategory.PLAYERS, 1.0F, 1.0F);



            stack.damageItem(2,entityLiving, (p_220040_1_) -> {
                p_220040_1_.sendBreakAnimation(playerIn.getActiveHand());
            });
            playerIn.getCooldownTracker().setCooldown(this,60);

        }
    }

    public int getUseDuration(ItemStack stack) {
        return 1000;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
}
