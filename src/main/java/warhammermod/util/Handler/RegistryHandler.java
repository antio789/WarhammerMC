package warhammermod.util.Handler;


import com.google.common.collect.Lists;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import warhammermod.util.reference;
import warhammermod.worldgen.biomesgeneration;

import java.util.List;


public class RegistryHandler {
    @Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class Events {

        @SubscribeEvent
        public static void onmemeory(RegistryEvent.Register<MemoryModuleType<?>> event){
            WarhammermodRegistry.registermodules();
        }

        @SubscribeEvent
        public static void onstructure(RegistryEvent.Register<Feature<?>> event){

        }

        @SubscribeEvent
        public static void registerstructure(RegistryEvent.Register<Structure<?>> event){

        }

        @SubscribeEvent
        public static void onbiome(RegistryEvent.Register<Biome> event){

            //WarhammermodRegistry.registerstructures();

            //biomesgeneration.setStructuresGeneration();
        }



        @SubscribeEvent
        public static void onsensor(RegistryEvent.Register<SensorType<?>> event){
            WarhammermodRegistry.registersensor();
        }
        @SubscribeEvent
        public static void sounds(RegistryEvent.Register<SoundEvent> event) {
            WarhammermodRegistry.registersounds();
        }

        @SubscribeEvent
        public static void professions(RegistryEvent.Register<VillagerProfession> event){
            WarhammermodRegistry.registerprofessions();
        }

        private static List<EntityType> entities = Lists.newArrayList();
        @SubscribeEvent
        public static void onItemregister(RegistryEvent.Register<Item> event) {
            ItemsInit.Pegasus_SPAWN_EGG = new SpawnEggItem(Entityinit.PEGASUS, 15528173,15395562, (new Item.Properties()).group(reference.warhammer)).setRegistryName("pegasus_egg");
            ItemsInit.DWARF_SPAWN_EGG = new SpawnEggItem(Entityinit.DWARF,10528073,15528173,(new Item.Properties()).group(reference.warhammer)).setRegistryName("dwarf_egg");
            ItemsInit.Skaven_SPAWN_EGG = new SpawnEggItem(Entityinit.SKAVEN, 15528473,15335562, (new Item.Properties()).group(reference.warhammer)).setRegistryName("skaven_egg");

            ItemsInit.ITEMS.add(ItemsInit.DWARF_SPAWN_EGG);
            ItemsInit.ITEMS.add(ItemsInit.Pegasus_SPAWN_EGG);
            ItemsInit.ITEMS.add(ItemsInit.Skaven_SPAWN_EGG);
            event.getRegistry().registerAll(ItemsInit.ITEMS.toArray(new Item[0]));
        }

        @SubscribeEvent
        public static void onattributes(RegistryEvent.Register<Attribute> event){

        }

        @SubscribeEvent
        public static void onEntityregister(RegistryEvent.Register<EntityType<?>> event) {
            Entityinit.PEGASUS.setRegistryName(location("pegasus"));
            Entityinit.DWARF.setRegistryName(location("dwarf"));
            Entityinit.SKAVEN.setRegistryName(location("skaven"));
            Entityinit.Bullet.setRegistryName(location("bullet"));
            Entityinit.grenade.setRegistryName(location("grenadeentity"));
            Entityinit.shotgun.setRegistryName(location("shotgunentity"));
            Entityinit.spear.setRegistryName(location("spearentity"));
            Entityinit.stone.setRegistryName(location("stoneentity"));
            Entityinit.warpbullet.setRegistryName(location("warpbullet"));
            Entityinit.flame.setRegistryName(location("flameentity"));
            Entityinit.HalberdEntity.setRegistryName(location("halberd_entity"));
            event.getRegistry().registerAll(Entityinit.PEGASUS, Entityinit.DWARF, Entityinit.SKAVEN,Entityinit.Bullet, Entityinit.grenade, Entityinit.HalberdEntity, Entityinit.shotgun, Entityinit.spear, Entityinit.stone, Entityinit.warpbullet, Entityinit.flame);
        }




    }

    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, name);
    }







}
