package warhammermod;


import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Entities.living.Emanager.ProfessionHelper;
import warhammermod.Entities.living.EntityDwarf;
import warhammermod.Entities.living.EntityPegasus;
import warhammermod.Entities.living.EntityPirateSkeleton;
import warhammermod.Entities.living.EntitySkaven;
import warhammermod.Items.Melee.shieldtemplate;
import warhammermod.util.Handler.Loottable;
import warhammermod.util.Handler.inithandler.Itemsinit;
import warhammermod.util.Handler.soundhandler.sounds;
import warhammermod.util.Itimetoreload;
import warhammermod.util.confighandler.confighandler;
import warhammermod.util.proxy.commonproxy;
import warhammermod.util.reference;
import warhammermod.worldgen.*;

import java.util.List;


@Mod(modid = reference.modid,useMetadata = true)
public class Main {




    @Mod.Instance
    public static Main instance;

    @SidedProxy(clientSide = reference.CLIENT_PROXY_CLASS, serverSide = reference.COMMON_PROXY_CLASS)
    public static commonproxy proxy;


    @EventHandler
    public void PreInit(FMLPreInitializationEvent event){
        WMWorldGen Wgen = new WMWorldGen();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(Wgen);
        GameRegistry.registerWorldGenerator(Wgen, 0);
        proxy.renderentity();
        proxy.registerclient();

        for(Biome biome : Biome.REGISTRY){
            if(biome.toString().toLowerCase().contains("hill") && confighandler.Config_enable.Pegasus_spawn){
                List<Biome.SpawnListEntry> SpawnPegasus= biome.getSpawnableList(EnumCreatureType.CREATURE);
                SpawnPegasus.add(new Biome.SpawnListEntry(EntityPegasus.class, 8, 1, 2));
            }
            if(EntityPirateSkeleton.skeleton_SPAWN_BIOMES.contains(biome) && confighandler.Config_enable.Pirate_Skeleton_spawn){
                List<Biome.SpawnListEntry> Spawnskely= biome.getSpawnableList(EnumCreatureType.MONSTER);
                Spawnskely.add(new Biome.SpawnListEntry(EntityPirateSkeleton.class,90,2,4));
            }
            {
                List<Biome.SpawnListEntry> Skaven= biome.getSpawnableList(EnumCreatureType.MONSTER);
                Skaven.add(new Biome.SpawnListEntry(EntitySkaven.class,90,4,9));
            }
        }




    }
    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(reference.modid))
        {
            ConfigManager.sync(reference.modid, Config.Type.INSTANCE);

        }
    }

    @EventHandler
    public void Init(FMLInitializationEvent event){


        MapGenStructureIO.registerStructure(MapGenDwarfVillage.DwarfStart.class,"Dwarf Village");
        MapGenStructureIO.registerStructure(MapGenSkavenVillage.SkavenStart.class,"Skaven Village");

        StructureDwarfVillagePieces.registerVillagePieces();
        StructureSkavenVillagePieces.registerVillagePieces();
        ProfessionHelper.tradesinit();
        sounds.registersounds();
        Loottable.registerloot();

    }

    @EventHandler
    public static void PostInit(FMLPostInitializationEvent event){
    }



    @SubscribeEvent
    public void breedingtime(LivingEvent event) {
        if (event.getEntityLiving() instanceof EntityDwarf) {
            EntityDwarf villager = (EntityDwarf) event.getEntityLiving();
            if (villager.getGrowingAge() > confighandler.Change_Durations.getBreeding_cooldown()) {
                villager.setGrowingAge(confighandler.Change_Durations.getBreeding_cooldown());
            } else if (villager.getGrowingAge() < confighandler.Change_Durations.getBabyvillager_to_grow_up()) {
                villager.setGrowingAge(confighandler.Change_Durations.getBabyvillager_to_grow_up());
            }
        }
    }

    @SubscribeEvent
    public void populate(PopulateChunkEvent event){

    }

    @SubscribeEvent
    public void fovevent(FOVUpdateEvent event){

        if(event.getEntity().getActiveItemStack().getItem() instanceof shieldtemplate){
            event.setNewfov(1.0F);
        }
        else if(event.getEntity().getActiveItemStack().getItem().equals(Itemsinit.WarpLock_Jezzail)){
            event.setNewfov(0.5F);
        }

    }



    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void guievent(RenderGameOverlayEvent event){
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR)){
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if(player.isHandActive()&& !player.capabilities.isCreativeMode && player.getActiveItemStack().getItem() instanceof Itimetoreload && !(event instanceof  RenderGameOverlayEvent.Post)) {
                if (!(((Itimetoreload) player.getActiveItemStack().getItem()).isReadytoFire())) {
                    int i = player.inventory.getSlotFor(player.getActiveItemStack());
                    int x = event.getResolution().getScaledWidth() / 2 - 90;
                    int y = event.getResolution().getScaledHeight() - 80;
                    int reloadtime = ((Itimetoreload) player.getActiveItemStack().getItem()).getTimetoreload();
                    float diff = player.getItemInUseCount() - player.getActiveItemStack().getMaxItemUseDuration() + reloadtime;
                    double percentage = diff / reloadtime;
                    GlStateManager.disableTexture2D();
                    GlStateManager.disableDepth();
                    Tessellator tessellator1 = Tessellator.getInstance();
                    BufferBuilder bufferbuilder1 = tessellator1.getBuffer();
                    this.draw(bufferbuilder1, x, y + MathHelper.floor(16.0F * (1.0F)), (int) (180 * percentage), 10, 245, 245, 255, 127);
                    GlStateManager.enableTexture2D();
                    GlStateManager.enableDepth();

                }
            }
        }
    }
    @SideOnly(Side.CLIENT)
    private void draw(BufferBuilder renderer,int x, int y, int width, int height, int red, int green, int blue, int alpha)
    {
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos((double)(x), (double)(y), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y), 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
    }

    /*
    Biome biome;
    @SubscribeEvent
    public void onvillagerspawn(EntityJoinWorldEvent event)
    {
        if ((event.getEntity() instanceof EntityVillager) && utils.checkcorrectbiome(biome=event.getWorld().getBiome(((EntityVillager) event.getEntity()).getPos()))){

    }*/


}
