package warhammermod.util.Handler.soundhandler;


import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import warhammermod.util.reference;

public class sounds {
    public static SoundEvent ratambient;
    public static SoundEvent ratdeath;
    public static SoundEvent rathurt;
    public static SoundEvent flame;

    public static void registersounds(){
        ratambient = registersound("entity.skaven.ambient");
        ratdeath = registersound("entity.skaven.death");
        rathurt = registersound("entity.skaven.hurt");
        flame = registersound("flamethrower");
    }

    private static SoundEvent registersound(String name){
        ResourceLocation location = new ResourceLocation(reference.modid, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
