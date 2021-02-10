package warhammermod.worldgen.dwarfvillage;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import warhammermod.util.Handler.WarhammermodRegistry;
import warhammermod.util.utils.utils;

import java.util.Random;
import java.util.function.Function;

import static warhammermod.util.Handler.WarhammermodRegistry.Dwarf_village;

public class DwarfVillageStructure extends Structure<NoFeatureConfig> {
    public DwarfVillageStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51419_1_) {
        super(p_i51419_1_);
    }

    protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
        int i = 21;
        int j = 5;
        int k = x + i * spacingOffsetsX;
        int l = z + i * spacingOffsetsZ;
        int i1 = k < 0 ? k - i + 1 : k;
        int j1 = l < 0 ? l - i + 1 : l;
        int k1 = i1 / i;
        int l1 = j1 / i;
        ((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 10387312);
        k1 = k1 * i;
        l1 = l1 * i;
        k1 = k1 + random.nextInt(i - j);
        l1 = l1 + random.nextInt(i - j);
        return new ChunkPos(k1, l1);
    }

    public boolean canBeGenerated(BiomeManager p_225558_1_, ChunkGenerator<?> p_225558_2_, Random p_225558_3_, int p_225558_4_, int p_225558_5_, Biome p_225558_6_) {

        ChunkPos chunkpos = this.getStartPositionForPosition(p_225558_2_, p_225558_3_, p_225558_4_, p_225558_5_, 0, 0);

        utils.nonrepeatingprinter(p_225558_6_.toString()+ " " + p_225558_2_.hasStructure(p_225558_6_, Dwarf_village)+ " canspawninbiome"+ p_225558_6_.hasStructure(Dwarf_village)+ "and in the biome?");
        return p_225558_4_ == chunkpos.x && p_225558_5_ == chunkpos.z && p_225558_6_.hasStructure(Dwarf_village);
    }

    public IStartFactory getStartFactory() {
        return DwarfVillageStructure.Start::new;
    }

    public String getStructureName() {
        return "dwarf_Village";
    }

    public int getSize() {
        return 8;
    }

    public static class Start extends MarginedStructureStart {
        public Start(Structure<?> p_i225821_1_, int p_i225821_2_, int p_i225821_3_, MutableBoundingBox p_i225821_4_, int p_i225821_5_, long p_i225821_6_) {
            super(p_i225821_1_, p_i225821_2_, p_i225821_3_, p_i225821_4_, p_i225821_5_, p_i225821_6_);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {

            BlockPos blockpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
            DwarfVillagePieces.func_214838_a(generator, templateManagerIn, blockpos, this.components, this.rand);
            this.recalculateStructureSize();
        }
    }
}