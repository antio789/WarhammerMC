package warhammermod.worldgen.dwarfvillage;


import com.mojang.serialization.Codec;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class DwarfVillageStructure extends Structure<VillageConfig> {
    public DwarfVillageStructure(Codec<VillageConfig> p_i232001_1_) {
        super(p_i232001_1_);
    }

    public GenerationStage.Decoration func_236396_f_() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    public IStartFactory<VillageConfig> getStartFactory() {
        return Start::new;
    }

    public static class Start extends MarginedStructureStart<VillageConfig> {
        public Start(Structure<VillageConfig> p_i225821_1_, int p_i225821_2_, int p_i225821_3_, MutableBoundingBox p_i225821_4_, int p_i225821_5_, long p_i225821_6_) {
            super(p_i225821_1_, p_i225821_2_, p_i225821_3_, p_i225821_4_, p_i225821_5_, p_i225821_6_);
        }

        public void func_230364_a_(ChunkGenerator p_230364_1_, TemplateManager p_230364_2_, int p_230364_3_, int p_230364_4_, Biome p_230364_5_, VillageConfig p_230364_6_) {
            BlockPos blockpos = new BlockPos(p_230364_3_ * 16, 0, p_230364_4_ * 16);
            DwarfVillagePieces.addPieces(p_230364_1_, p_230364_2_, blockpos, this.components, this.rand, p_230364_6_);
            this.recalculateStructureSize();
        }
    }


    public final ChunkPos spawnvillage(long p_236392_2_, SharedSeedRandom p_236392_4_, int p_236392_5_, int p_236392_6_) {
        int i = 19;
        int j = 4;
        int k = Math.floorDiv(p_236392_5_, i);
        int l = Math.floorDiv(p_236392_6_, i);
        p_236392_4_.setLargeFeatureSeedWithSalt(p_236392_2_, k, l, 34222645);
        int i1;
        int j1;
        if (this.func_230365_b_()) {
            i1 = p_236392_4_.nextInt(i - j);
            j1 = p_236392_4_.nextInt(i - j);
        } else {
            i1 = (p_236392_4_.nextInt(i - j) + p_236392_4_.nextInt(i - j)) / 2;
            j1 = (p_236392_4_.nextInt(i - j) + p_236392_4_.nextInt(i - j)) / 2;
        }

        return new ChunkPos(k * i + i1, l * i + j1);
    }


    @Override
    protected boolean func_230363_a_(ChunkGenerator p_230363_1_, BiomeProvider p_230363_2_, long p_230363_3_, SharedSeedRandom p_230363_5_, int p_230363_6_, int p_230363_7_, Biome p_230363_8_, ChunkPos p_230363_9_, VillageConfig p_230363_10_) {
        ;
        ChunkPos chunkpos = this.spawnvillage(p_230363_3_, p_230363_5_, p_230363_6_, p_230363_7_);
        return p_230363_6_ == chunkpos.x && p_230363_7_ == chunkpos.z;


    }
}