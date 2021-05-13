package warhammermod.worldgen.dwarfvillage;


import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;


public class DwarfVillageStructure extends JigsawStructure {
    public DwarfVillageStructure(Codec<VillageConfig> p_i232001_1_) {
        super(p_i232001_1_, 0, true, true);
    }

    public Structure.IStartFactory<VillageConfig> getStartFactory() {
        return (p_242778_1_, p_242778_2_, p_242778_3_, p_242778_4_, p_242778_5_, p_242778_6_) -> {
            return new Start(this, p_242778_2_, p_242778_3_, p_242778_4_, p_242778_5_, p_242778_6_);
        };
    }

    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    public static class Start extends JigsawStructure.Start{

        public Start(JigsawStructure p_i241979_1_, int p_i241979_2_, int p_i241979_3_, MutableBoundingBox p_i241979_4_, int p_i241979_5_, long p_i241979_6_) {
            super(p_i241979_1_, p_i241979_2_, p_i241979_3_, p_i241979_4_, p_i241979_5_, p_i241979_6_);

        }

        public void generatePieces(DynamicRegistries p_230364_1_, ChunkGenerator p_230364_2_, TemplateManager p_230364_3_, int p_230364_4_, int p_230364_5_, Biome p_230364_6_, VillageConfig p_230364_7_) {
            DwarfVillagePools.init();
            super.generatePieces(p_230364_1_, p_230364_2_, p_230364_3_, p_230364_4_, p_230364_5_, p_230364_6_, p_230364_7_);
        }
    }


    

}