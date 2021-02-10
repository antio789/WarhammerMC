package warhammermod.Entities.living.Data;

import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import warhammermod.util.Handler.Entityinit;

import static warhammermod.Entities.living.DwarfEntity.DwarfAttributes;

public class EntititiesAttributes {
    public static void registerDwarfTypesattributes(){
        double speed = 0.46;
        double armor=2;
        double AD=2;
        double followrange = 45;

        GlobalEntityTypeAttributes.put(Entityinit.DWARF,DwarfAttributes(speed,followrange,armor,AD).func_233813_a_());
    };
    public static void registerskavenTypesattributes(){
            double speed = 0.46;
            double armor=2;
            double AD=2;
            double followrange = 45;

            GlobalEntityTypeAttributes.put(Entityinit.SKAVEN,skavenattributes(speed,followrange,armor,AD).func_233813_a_());
    };
    public static void registerPegasusattributes(){
        GlobalEntityTypeAttributes.put(Entityinit.PEGASUS, AbstractHorseEntity.func_234237_fg_().func_233813_a_());
    }
    public static void registerattributes(){
        registerDwarfTypesattributes();
        registerPegasusattributes();
        registerskavenTypesattributes();
    }

    public static AttributeModifierMap.MutableAttribute skavenattributes(double speed, double health, double armor, double AD) {
        return MonsterEntity.func_234295_eP_().func_233815_a_(Attributes.field_233821_d_, (double)speed).func_233815_a_(Attributes.field_233823_f_, AD).func_233815_a_(Attributes.field_233826_i_, armor).func_233815_a_(Attributes.field_233818_a_,health);
    }


}
