package warhammermod.util.Handler;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.structure.VillageStructure;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import warhammermod.Entities.living.Aimanager.Sensor.LordlastSeenSensor;
import warhammermod.Entities.living.Aimanager.Sensor.WHSecondaryPositionSensor;
import warhammermod.Entities.living.Data.DwarfProfession;
import warhammermod.Entities.living.DwarfEntity;
import warhammermod.util.reference;
import warhammermod.worldgen.dwarfvillage.DwarfVillagePieces;
import warhammermod.worldgen.dwarfvillage.DwarfVillageStructure;

import java.util.Locale;
import java.util.Optional;

public class WarhammermodRegistry {

    public static SensorType<WHSecondaryPositionSensor> SECONDARY_POIS;
    public static SensorType<LordlastSeenSensor> Lord_Last_Seen;

    public static void registersensor(){
        SECONDARY_POIS = registersensor("dwarf_secondary_pois");
        Lord_Last_Seen = registersensor("lord_last_seen");
    }

    private static SensorType registersensor(String name){
        ResourceLocation location = new ResourceLocation(reference.modid, name);
        SensorType event = new SensorType(WHSecondaryPositionSensor::new);
        event.setRegistryName(location);
        ForgeRegistries.SENSOR_TYPES.register(event);
        return event;
    }


    public static MemoryModuleType<DwarfEntity> BREED_TARGET;
    public static MemoryModuleType<LivingEntity> ATTACK_TARGET;

    public static void registermodules(){
        BREED_TARGET = registermodules("dwarf_breed_target");
        ATTACK_TARGET = registermodules("dwarf_attack_target");
    }

    private static MemoryModuleType registermodules(String name){
        ResourceLocation location = new ResourceLocation(reference.modid, name);
        MemoryModuleType event = new MemoryModuleType<>(Optional.empty());
        event.setRegistryName(location);
        ForgeRegistries.MEMORY_MODULE_TYPES.register(event);
        return event;
    }

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
        event.setRegistryName(location);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

    public static void registerprofessions(){
            DwarfProfession.Warrior = new DwarfProfession("warrior", 6, PointOfInterestType.UNEMPLOYED, null, Items.IRON_AXE,ItemsInit.Dwarf_shield);
            DwarfProfession.Miner = new DwarfProfession("miner", 0, PointOfInterestType.ARMORER, null, Items.IRON_PICKAXE);
            DwarfProfession.Builder = new DwarfProfession("builder", 1, PointOfInterestType.MASON, SoundEvents.ENTITY_VILLAGER_WORK_MASON,ItemsInit.iron_warhammer);
            DwarfProfession.Engineer = new DwarfProfession("engineer", 2, PointOfInterestType.TOOLSMITH, SoundEvents.ENTITY_VILLAGER_WORK_ARMORER, Items.IRON_AXE,ItemsInit.Dwarf_shield);
            DwarfProfession.Farmer = new DwarfProfession("farmer", 3, PointOfInterestType.FARMER, ImmutableSet.of(Items.WHEAT, Items.WHEAT_SEEDS), ImmutableSet.of(Blocks.FARMLAND), SoundEvents.ENTITY_VILLAGER_WORK_FARMER, Items.IRON_AXE, ItemStack.EMPTY.getItem());
            DwarfProfession.Slayer = new DwarfProfession("slayer", 4, PointOfInterestType.BUTCHER, SoundEvents.ENTITY_VILLAGER_WORK_BUTCHER, Items.IRON_AXE, Items.IRON_AXE);
            DwarfProfession.Lord = new DwarfProfession("lord", 5, PointOfInterestType.NITWIT, SoundEvents.ENTITY_VILLAGER_WORK_CLERIC,ItemsInit.diamond_warhammer,ItemsInit.Dwarf_shield);
    }


    public static final IStructurePieceType DVI= DwarfVillagePieces.DwarfVillage::new;

    public static void registerpieces(){
        //need jigsaw pieces

        register("dvi",DVI);
    }

    private static void register(String key, IStructurePieceType type){
    Registry.register(Registry.STRUCTURE_PIECE,new ResourceLocation(reference.modid, key),type);
    }


    //public static final DwarfVillageStructure Dwarf_village = new DwarfVillageStructure(VillageConfig.field_236533_a_);
    //public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> DwarfVillage = Dwarf_village.func_236391_a_(new VillageConfig(new ResourceLocation(reference.modid,"dwarf_village/town_centers"), 6));

    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, reference.modid);

    public static final RegistryObject<Structure<VillageConfig>> Dwarfvillage = register("dwarfvillage", new DwarfVillageStructure(VillageConfig.field_236533_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);

    private static <T extends Structure<?>> RegistryObject<T> register(String name, T structure, GenerationStage.Decoration decoration) {
        Structure.field_236365_a_.put(reference.modid + ":" + name, structure);
        return STRUCTURES.register(name, () -> structure);
    }
/*
    private static DwarfVillageStructure registersdwarfvillage(){
        ResourceLocation location = new ResourceLocation(reference.modid, "dwarf_village");
        Dwarf_village.setRegistryName(location);
        ForgeRegistries.STRUCTURE_FEATURES.register(Dwarf_village);
        return Dwarf_village;
    }
*/
    //public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> Dwarfvillage = Dwarf_village.func_236391_a_(new VillageConfig(new ResourceLocation("village/plains/town_centers"), 6));

/*
    public static void registerstructures(){
        registersdwarfvillage();
    }


*/



    /*    public static final PointOfInterestType SLAYER = registerPOI("slayer");

    private static PointOfInterestType registerPOI(String name){
        ResourceLocation location = new ResourceLocation(reference.modid, name);
        PointOfInterestType event = new PointOfInterestType(name,getAllStates(Blocks.CAMPFIRE),1,1);
        event.setRegistryName(location);
        ForgeRegistries.POI_TYPES.register();
        return event;
    }

    private static Set<BlockState> getAllStates(Block p_221042_0_) {
        return ImmutableSet.copyOf(p_221042_0_.getStateContainer().getValidStates());
    }
*/



}

