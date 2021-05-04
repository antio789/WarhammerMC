package warhammermod.Entities.Living.Aimanager.Data;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;

public class EntityAttributes {
    public static AttributeModifierMap.MutableAttribute registerDwarfTypesattributes(){
        double speed = 0.46;
        double armor=2;
        double AD=2;
        double followrange = 45;

        return DwarfAttributes(speed,followrange,armor,AD);
    };
    public static AttributeModifierMap.MutableAttribute DwarfAttributes(double speed, double followrange, double armor, double AD) {
        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, speed).add(Attributes.ATTACK_DAMAGE, AD).add(Attributes.ARMOR, armor).add(Attributes.FOLLOW_RANGE,followrange);
    }
}
