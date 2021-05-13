package warhammermod.utils.inithandler;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import warhammermod.utils.reference;

public class ModRegistrerevents {
    @Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class Events {
        @SubscribeEvent
        public static void onItemregister(RegistryEvent.Register<Item> event) {
            ItemsInit.ITEMS.add(Entityinit.Pegasus_SPAWN_EGG);
            ItemsInit.ITEMS.add(Entityinit.DWARF_SPAWN_EGG);
            event.getRegistry().registerAll(ItemsInit.ITEMS.toArray(new Item[0]));
        }

        @SubscribeEvent
        public static void onworld(RegistryEvent.Register<Structure<?>> event) {

            WarhammermodRegistry.BAse.setRegistryName(location("dwarf_village"));
            event.getRegistry().register(WarhammermodRegistry.BAse);

            }




        @SubscribeEvent
        public static void onEntityregister(RegistryEvent.Register<EntityType<?>> event) { WarhammermodRegistry.registerEntities(event); }
        @SubscribeEvent
        public static void sounds(RegistryEvent.Register<SoundEvent> event) {
            WarhammermodRegistry.registersounds();
        }
        @SubscribeEvent
        public static void attributes(EntityAttributeCreationEvent event){ WarhammermodRegistry.registerattributes(event); }
        @SubscribeEvent
        public static void Sensors(RegistryEvent.Register<SensorType<?>> event){ WarhammermodRegistry.registersensor(); }}

        public static void preinit(){WarhammermodRegistry.registerprofessions(); }


    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, name);
    }

}
