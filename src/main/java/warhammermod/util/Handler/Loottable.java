package warhammermod.util.Handler;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import warhammermod.util.reference;

public class Loottable {
    public static ResourceLocation Pirate_Skeleton;
    public static ResourceLocation Skaven;
    public static ResourceLocation Skaven_ratling;
    public static ResourceLocation Skaven_Village;
    public static void registerloot(){
        Pirate_Skeleton = registerloot("test");
        Skaven = registerloot("skaven");
        Skaven_Village = registerloot("Skaven_Village");
        Skaven_ratling = registerloot("skaven_ratling");
    }
    public static ResourceLocation registerloot(String name){
        return LootTableList.register(new ResourceLocation(reference.modid,"loot/"+name));
    }

}
