package warhammermod.worldgen.dwarfvillage;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.jigsaw.*;
import net.minecraft.world.gen.feature.template.*;
import warhammermod.utils.reference;

public class DwarfVillagePools {
    public static final JigsawPattern START = JigsawPatternRegistry.register(new JigsawPattern(new ResourceLocation("warhammermod:dwarf_village/town_centers"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(JigsawPiece.legacy(location("town_centers/dwarf_meeting_point_1").toString(), ProcessorLists.EMPTY), 1),Pair.of(JigsawPiece.legacy(location("town_centers/tavern").toString(), ProcessorLists.EMPTY), 3)), JigsawPattern.PlacementBehaviour.RIGID));

    public static void init() {
    }

    static {
        ImmutableList<StructureProcessor> list1 = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState()))));
        ImmutableList<StructureProcessor> immutablelist = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.STONE_BRICKS, 0.08F), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_STONE_BRICKS.defaultBlockState()))));
        ImmutableList<StructureProcessor> list2 = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of( new RuleEntry(new RandomBlockMatchRuleTest(Blocks.GRASS_PATH, 0.9F), AlwaysTrueRuleTest.INSTANCE, Blocks.COBBLESTONE.defaultBlockState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.DIRT,0.9F), AlwaysTrueRuleTest.INSTANCE, Blocks.GRAVEL.defaultBlockState()))));

         JigsawPatternRegistry.register(new JigsawPattern(new ResourceLocation("warhammermod:dwarf_village/streets"), new ResourceLocation("warhammermod:dwarf_village/terminators"), ImmutableList.of(Pair.of(JigsawPiece.legacy("warhammermod:dwarf_village/streets/corner_01", ProcessorLists.EMPTY), 2),  Pair.of(JigsawPiece.legacy("warhammermod:dwarf_village/streets/corner_02", ProcessorLists.EMPTY), 2),  Pair.of(JigsawPiece.legacy("warhammermod:dwarf_village/streets/corner_03", ProcessorLists.EMPTY), 2),  Pair.of(JigsawPiece.legacy("warhammermod:dwarf_village/streets/straight_01", ProcessorLists.EMPTY), 4), Pair.of(JigsawPiece.legacy("warhammermod:dwarf_village/streets/straight_02", ProcessorLists.EMPTY), 4), Pair.of(JigsawPiece.legacy("warhammermod:dwarf_village/streets/crossroad_01", net.minecraft.world.gen.feature.template.ProcessorLists.EMPTY), 2), Pair.of(JigsawPiece.legacy("warhammermod:dwarf_village/streets/crossroad_02", net.minecraft.world.gen.feature.template.ProcessorLists.EMPTY), 1),Pair.of(JigsawPiece.legacy("warhammermod:dwarf_village/streets/turn_01", ProcessorLists.EMPTY), 3)), JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
         JigsawPatternRegistry.register(new JigsawPattern(location("houses"), new ResourceLocation("dwarf_village/terminators"),ImmutableList.of(Pair.of(JigsawPiece.legacy(location("houses/dwarf_small_house_1").toString(), ProcessorLists.EMPTY), 4),Pair.of(JigsawPiece.legacy(location("houses/dwarf_small_house_2").toString(), ProcessorLists.EMPTY), 4), Pair.of(JigsawPiece.legacy(location("houses/dwarf_medium_house_1").toString(), ProcessorLists.EMPTY), 2),Pair.of(JigsawPiece.legacy(location("houses/dwarf_builder_house_1").toString(), ProcessorLists.EMPTY), 2),Pair.of(JigsawPiece.legacy(location("houses/dwarf_engineer_house_1").toString(), ProcessorLists.EMPTY), 2), Pair.of(JigsawPiece.legacy(location("houses/dwarf_farmer_house_1").toString(), ProcessorLists.EMPTY), 2),Pair.of(JigsawPiece.legacy(location("houses/dwarf_miner_house_1").toString(), ProcessorLists.EMPTY), 2), Pair.of(JigsawPiece.legacy(location("dwarf_slayer_house_1").toString(), ProcessorLists.EMPTY), 2),Pair.of(JigsawPiece.empty(), 4)), JigsawPattern.PlacementBehaviour.RIGID));
         JigsawPatternRegistry.register(new JigsawPattern(new ResourceLocation("warhammermod:dwarf_village/terminators"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(JigsawPiece.legacy("warhammermod:dwarf_village/terminators/terminator_01", net.minecraft.world.gen.feature.template.ProcessorLists.EMPTY), 1), Pair.of(JigsawPiece.legacy("warhammermod:dwarf_village/terminators/terminator_02", net.minecraft.world.gen.feature.template.ProcessorLists.EMPTY), 1), Pair.of(JigsawPiece.legacy("warhammermod:dwarf_village/terminators/terminator_03", net.minecraft.world.gen.feature.template.ProcessorLists.EMPTY), 1), Pair.of(JigsawPiece.legacy("warhammermod:dwarf_village/terminators/terminator_04", net.minecraft.world.gen.feature.template.ProcessorLists.EMPTY), 1)), JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
         JigsawPatternRegistry.register(new JigsawPattern(location("dwarf"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(JigsawPiece.legacy(location("dwarf/dwarf").toString()), 1)), JigsawPattern.PlacementBehaviour.RIGID));
    }


    public static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, "dwarf_village/"+name);
    }

    public static JigsawPattern register(JigsawPattern p_244094_0_) {
        return WorldGenRegistries.register(WorldGenRegistries.TEMPLATE_POOL, p_244094_0_.getName(), p_244094_0_);
    }
}