package warhammermod.Items.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import warhammermod.utils.inithandler.ItemsInit;
import warhammermod.utils.reference;

public class HammerTemplate extends SwordItem {
    protected float attackDamage;
    protected float attackSpeed;
    private final Multimap<Attribute, AttributeModifier> modifierMultimap;

    public HammerTemplate(IItemTier tier, Item.Properties properties) {
        super(tier,0,0, properties.tab(reference.warhammer));
        ItemsInit.ITEMS.add(this);
        this.attackDamage = 2.5F + tier.getAttackDamageBonus();
        attackSpeed=-2.9F;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        this.modifierMultimap = builder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType p_111205_1_) {
        return p_111205_1_ == EquipmentSlotType.MAINHAND ? this.modifierMultimap : super.getDefaultAttributeModifiers(p_111205_1_);
    }
}
