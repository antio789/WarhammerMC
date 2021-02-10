package warhammermod.Items.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.reference;

public class Hammertemplate extends SwordItem {
    protected float attackDamage;
    protected float attackSpeed;
    private final Multimap<Attribute, AttributeModifier> field_234810_b_;

    public Hammertemplate(IItemTier tier, Properties builder){
        super(tier,0 ,0,builder.group(reference.warhammer));
        attackDamage=2.5F+ tier.getAttackDamage()*2;
        attackSpeed=-2.9F;
        ItemsInit.ITEMS.add(this);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder2 = ImmutableMultimap.builder();
        builder2.put(Attributes.field_233823_f_, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder2.put(Attributes.field_233825_h_, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION));
        this.field_234810_b_ = builder2.build();
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.field_234810_b_ : ImmutableMultimap.of();
    }



    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker)
    {
        return true;
    }
}
