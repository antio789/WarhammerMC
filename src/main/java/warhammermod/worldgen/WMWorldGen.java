package warhammermod.worldgen;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import warhammermod.util.confighandler.confighandler;
import java.util.Random;

public class WMWorldGen implements IWorldGenerator{
    private MapGenDwarfVillage villageGenerator = new MapGenDwarfVillage();
    private MapGenSkavenVillage SkavenvillageGenerator = new MapGenSkavenVillage();
    protected int range = 8;
    protected Random rand = new Random();
    protected World world;

    public void generate(Random random, int x, int z, World worldIn, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        this.world = worldIn;
        if (world.isRemote ||!confighandler.Config_enable.generation_enabled || !world.getWorldInfo().isMapFeaturesEnabled()) return;
        int i = this.range;

        for (int l = x - i; l <= x + i; ++l) {
            for (int i1 = z - i; i1 <= z + i; ++i1) {
                ChunkPrimer chunkprimer = new ChunkPrimer();

                this.villageGenerator.generate(world, x, z, chunkprimer);
                this.SkavenvillageGenerator.generate(world, x, z, chunkprimer);
            }
        }
    }


    @SubscribeEvent
    public void populate(PopulateChunkEvent event){
        this.world = event.getWorld();
        if (world.isRemote || !confighandler.Config_enable.generation_enabled || !world.getWorldInfo().isMapFeaturesEnabled()) return;
        this.rand=event.getRand();
        ChunkPos chunkpos = new ChunkPos(event.getChunkX(), event.getChunkZ());
        villageGenerator.generateStructure(world, rand, chunkpos);
        SkavenvillageGenerator.generateStructure(world, rand, chunkpos);
    }
    





}