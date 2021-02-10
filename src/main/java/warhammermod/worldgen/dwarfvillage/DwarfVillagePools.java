package warhammermod.worldgen.dwarfvillage;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.jigsaw.*;
import net.minecraft.world.gen.feature.template.*;
import warhammermod.util.reference;

public class DwarfVillagePools {
    public static void init() {
    }

    static {
        ImmutableList<StructureProcessor> immutablelist1 = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.getDefaultState()))));
        ImmutableList<StructureProcessor> immutablelist = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.STONE_BRICKS, 0.08F), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_STONE_BRICKS.getDefaultState()))));

        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("warhammermod:dwarf_village/town_centers"), new ResourceLocation("empty"), ImmutableList.of(new Pair<>(new SingleJigsawPiece(location("town_centers/dwarf_meeting_point_1").toString(), immutablelist), 1),new Pair<>(new SingleJigsawPiece(location("town_centers/tavern").toString(), immutablelist, JigsawPattern.PlacementBehaviour.RIGID), 3)), JigsawPattern.PlacementBehaviour.RIGID));
        ImmutableList<StructureProcessor> immutablelist2 = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of( new RuleEntry(new RandomBlockMatchRuleTest(Blocks.GRASS_PATH, 0.9F), AlwaysTrueRuleTest.INSTANCE, Blocks.COBBLESTONE.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.DIRT,0.9F), AlwaysTrueRuleTest.INSTANCE, Blocks.GRAVEL.getDefaultState()))));
        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("warhammermod:dwarf_village/streets"), new ResourceLocation("warhammermod:dwarf_village/terminators"), ImmutableList.of(new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/streets/corner_01", immutablelist2), 2), new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/streets/corner_02", immutablelist2), 2), new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/streets/corner_03", immutablelist2), 2), new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/streets/straight_01", immutablelist2), 3), new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/streets/straight_02", immutablelist2), 3), new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/streets/crossroad_01", immutablelist2), 3), new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/streets/crossroad_02", immutablelist2), 2),new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/streets/turn_01", immutablelist2), 3)), JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
        JigsawManager.REGISTRY.register(new JigsawPattern(location("houses"), new ResourceLocation("dwarf_village/terminators"), ImmutableList.of(new Pair<>(new SingleJigsawPiece(location("houses/dwarf_small_house_1").toString(), immutablelist1, JigsawPattern.PlacementBehaviour.RIGID), 4), new Pair<>(new SingleJigsawPiece(location("houses/dwarf_small_house_2").toString(), immutablelist1, JigsawPattern.PlacementBehaviour.RIGID), 4), new Pair<>(new SingleJigsawPiece(location("houses/dwarf_medium_house_1").toString(), immutablelist1, JigsawPattern.PlacementBehaviour.RIGID), 2), new Pair<>(new SingleJigsawPiece(location("houses/dwarf_builder_house_1").toString(), immutablelist1, JigsawPattern.PlacementBehaviour.RIGID), 2), new Pair<>(new SingleJigsawPiece(location("houses/dwarf_engineer_house_1").toString(), immutablelist1, JigsawPattern.PlacementBehaviour.RIGID), 2), new Pair<>(new SingleJigsawPiece(location("houses/dwarf_farmer_house_1").toString(), immutablelist1, JigsawPattern.PlacementBehaviour.RIGID), 2), new Pair<>(new SingleJigsawPiece(location("houses/dwarf_miner_house").toString(), immutablelist1, JigsawPattern.PlacementBehaviour.RIGID), 2), new Pair<>(new SingleJigsawPiece(location("houses/dwarf_slayer_house_1").toString(), immutablelist1, JigsawPattern.PlacementBehaviour.RIGID), 2), Pair.of(EmptyJigsawPiece.INSTANCE, 4)), JigsawPattern.PlacementBehaviour.RIGID));
        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("warhammermod:dwarf_village/terminators"), new ResourceLocation("empty"), ImmutableList.of(new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/terminators/terminator_01", immutablelist2), 1), new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/terminators/terminator_02", immutablelist2), 1), new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/terminators/terminator_03", immutablelist2), 1), new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/terminators/terminator_04", immutablelist2), 1)), JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
        JigsawManager.REGISTRY.register(new JigsawPattern(location("dwarf"), new ResourceLocation("empty"), ImmutableList.of( new Pair<>(new SingleJigsawPiece("warhammermod:dwarf_village/dwarf/dwarf"), 10)), JigsawPattern.PlacementBehaviour.RIGID));
    }

    public static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, "dwarf_village/"+name);
    }

}