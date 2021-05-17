package warhammermod.worldgen.dwarfvillage;


import com.mojang.serialization.Codec;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.mutable.MutableObject;
import warhammermod.worldgen.ModJigsawManager;

import java.util.List;
import java.util.Random;


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
            BlockPos blockpos = new BlockPos(p_230364_4_ * 16, 0, p_230364_5_ * 16);
            DwarfVillagePools.init();
            addPieces(p_230364_1_, p_230364_7_, AbstractVillagePiece::new, p_230364_2_, p_230364_3_, blockpos, this.pieces, this.random, true, true);
            this.calculateBoundingBox();
        }
    }

    public static void addPieces(DynamicRegistries p_242837_0_, VillageConfig p_242837_1_, JigsawManager.IPieceFactory p_242837_2_, ChunkGenerator p_242837_3_, TemplateManager p_242837_4_, BlockPos p_242837_5_, List<? super AbstractVillagePiece> p_242837_6_, Random p_242837_7_, boolean p_242837_8_, boolean p_242837_9_) {
        ModJigsawManager.addPieces(p_242837_0_, p_242837_1_, p_242837_2_, p_242837_3_, p_242837_4_, p_242837_5_, p_242837_6_, p_242837_7_, p_242837_8_, p_242837_9_);
    }



    

}