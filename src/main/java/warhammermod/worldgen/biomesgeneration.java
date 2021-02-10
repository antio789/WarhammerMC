package warhammermod.worldgen;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import warhammermod.Entities.living.SkavenEntity;
import warhammermod.util.Handler.Entityinit;
import warhammermod.util.Handler.WarhammermodRegistry;
import warhammermod.util.reference;

public class biomesgeneration {

    public static void setStructuresGeneration(){
        for (Biome biome: BiomeList){

            if(biome.getRegistryName().toString().contains("mountains")){
                biome.addStructure(WarhammermodRegistry.Dwarf_village.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, WarhammermodRegistry.Dwarf_village.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
                }
        }
    }

























    public static Biome[] BiomeList = {
            Biomes.PLAINS ,
            Biomes.DESERT ,
            Biomes.MOUNTAINS ,
            Biomes.FOREST ,
            Biomes.TAIGA ,
            Biomes.SWAMP ,
            Biomes.RIVER ,
            Biomes.FROZEN_OCEAN ,
            Biomes.FROZEN_RIVER ,
            Biomes.SNOWY_TUNDRA ,
            Biomes.SNOWY_MOUNTAINS ,
            Biomes.MUSHROOM_FIELDS ,
            Biomes.MUSHROOM_FIELD_SHORE ,
            Biomes.BEACH ,
            Biomes.DESERT_HILLS ,
            Biomes.WOODED_HILLS ,
            Biomes.TAIGA_HILLS ,
            Biomes.MOUNTAIN_EDGE ,
            Biomes.JUNGLE ,
            Biomes.JUNGLE_HILLS ,
            Biomes.JUNGLE_EDGE ,
            Biomes.DEEP_OCEAN ,
            Biomes.STONE_SHORE ,
            Biomes.SNOWY_BEACH ,
            Biomes.BIRCH_FOREST ,
            Biomes.BIRCH_FOREST_HILLS ,
            Biomes.DARK_FOREST ,
            Biomes.SNOWY_TAIGA ,
            Biomes.SNOWY_TAIGA_HILLS ,
            Biomes.GIANT_TREE_TAIGA ,
            Biomes.GIANT_TREE_TAIGA_HILLS ,
            Biomes.WOODED_MOUNTAINS ,
            Biomes.SAVANNA ,
            Biomes.SAVANNA_PLATEAU ,
            Biomes.BADLANDS ,
            Biomes.WOODED_BADLANDS_PLATEAU ,
            Biomes.BADLANDS_PLATEAU ,
            Biomes.WARM_OCEAN ,
            Biomes.LUKEWARM_OCEAN ,
            Biomes.COLD_OCEAN ,
            Biomes.DEEP_WARM_OCEAN ,
            Biomes.DEEP_LUKEWARM_OCEAN ,
            Biomes.DEEP_COLD_OCEAN ,
            Biomes.DEEP_FROZEN_OCEAN ,
            Biomes.SUNFLOWER_PLAINS ,
            Biomes.DESERT_LAKES ,
            Biomes.GRAVELLY_MOUNTAINS ,
            Biomes.FLOWER_FOREST ,
            Biomes.TAIGA_MOUNTAINS ,
            Biomes.SWAMP_HILLS ,
            Biomes.ICE_SPIKES ,
            Biomes.MODIFIED_JUNGLE ,
            Biomes.MODIFIED_JUNGLE_EDGE ,
            Biomes.TALL_BIRCH_FOREST ,
            Biomes.TALL_BIRCH_HILLS ,
            Biomes.DARK_FOREST_HILLS ,
            Biomes.SNOWY_TAIGA_MOUNTAINS ,
            Biomes.GIANT_SPRUCE_TAIGA ,
            Biomes.GIANT_SPRUCE_TAIGA_HILLS ,
            Biomes.MODIFIED_GRAVELLY_MOUNTAINS ,
            Biomes.SHATTERED_SAVANNA ,
            Biomes.SHATTERED_SAVANNA_PLATEAU ,
            Biomes.ERODED_BADLANDS ,
            Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU ,
            Biomes.MODIFIED_BADLANDS_PLATEAU ,
            Biomes.BAMBOO_JUNGLE ,
            Biomes.BAMBOO_JUNGLE_HILLS ,
    };
}

