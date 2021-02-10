package warhammermod.worldgen.dwarfvillage;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;

public class DwarfVillagePieces{
    public static void func_236425_a_() {
        DwarfVillagePools.init();
    }

    public static void addPieces(ChunkGenerator chunkGeneratorIn, TemplateManager templateManagerIn, BlockPos p_214838_2_, List<StructurePiece> structurePieces, SharedSeedRandom sharedSeedRandomIn, VillageConfig villageConfigIn) {
        func_236425_a_();
        JigsawManager.func_236823_a_(villageConfigIn.startPool, villageConfigIn.size, DwarfVillage::new, chunkGeneratorIn, templateManagerIn, p_214838_2_, structurePieces, sharedSeedRandomIn, true, true);
    }

    public static class DwarfVillage extends AbstractVillagePiece {
        public DwarfVillage(TemplateManager templateManagerIn, JigsawPiece jigsawPieceIn, BlockPos posIn, int groundLevelDelta, Rotation rotationIn, MutableBoundingBox boundsIn) {
            super(IStructurePieceType.NVI, templateManagerIn, jigsawPieceIn, posIn, groundLevelDelta, rotationIn, boundsIn);
        }

        public DwarfVillage(TemplateManager p_i50891_1_, CompoundNBT p_i50891_2_) {
            super(p_i50891_1_, p_i50891_2_, IStructurePieceType.NVI);
        }
    }
}