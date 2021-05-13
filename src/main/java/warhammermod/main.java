package warhammermod;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import warhammermod.Items.melee.SmallShieldTemplate;
import warhammermod.utils.inithandler.IReloadItem;
import warhammermod.utils.inithandler.ItemsInit;
import warhammermod.utils.inithandler.ModRegistrerevents;
import warhammermod.utils.inithandler.WarhammermodRegistry;
import warhammermod.worldgen.dwarfvillage.DwarfVillagePools;
import warhammermod.worldgen.structures.StructuresHandler;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("warhammermod")
public class main
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    @OnlyIn(Dist.CLIENT)
    public static final KeyBinding horsedown = new KeyBinding("pegasus down", 66, "key.categories.movement");


    public main() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        //MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH,this::addtobiomes);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
    }

    public void biomeModification(final BiomeLoadingEvent event) {
        System.out.println("passed here \n\n\n\n");
        if(event.getName().toString().contains("mountain")) {
            System.out.println("passed here \n\n\n\n");
            event.getGeneration().getStructures().add(() -> WarhammermodRegistry.DWARF_VILLAGE2);
        }
    }
    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        ModRegistrerevents.preinit();
        event.enqueueWork(() -> {
            StructuresHandler.setupStructures();
            WarhammermodRegistry.registerConfiguredStructures();
        });





        //GlobalEntityTypeAttributes.getSupplier(Entityinit.PEGASUS).createInstance()
           //     ;.put(EntityType.HORSE, AbstractHorseEntity.createBaseHorseAttributes().build());

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
        WarhammermodRegistry.Registerrenderer();
        ClientRegistry.registerKeyBinding(horsedown);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

    @SubscribeEvent
    public void fovevent(FOVUpdateEvent event){

        if(event.getEntity().getUseItem().getItem() instanceof SmallShieldTemplate){
            event.setNewfov(1.0F);
        }
        else if(event.getEntity().getUseItem().getItem().equals(ItemsInit.Warplock_jezzail)){
            event.setNewfov(0.5F);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void guievent(RenderGameOverlayEvent event) {
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR)) {

            PlayerEntity player = Minecraft.getInstance().player;
            if(player != null && player.isUsingItem() && player.getUseItem().getItem() instanceof IReloadItem) {
                IReloadItem item = (IReloadItem) player.getUseItem().getItem();
                if (!item.isReadytoFire(player.getUseItem())) {

                    int reloadtime = item.getTimetoreload();
                    float diff = reloadtime - player.getTicksUsingItem();
                    float f = Math.max(diff / reloadtime, 0);
                    if( f>0 && f<1 ) {
                        int i = event.getWindow().getGuiScaledWidth() / 2;
                        int screenHeight = event.getWindow().getGuiScaledHeight();
                        int j2 = screenHeight - 20;
                        int k2 = i + 91 + 6;
                        MatrixStack p_238443_2_ = event.getMatrixStack();
                        Minecraft.getInstance().getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
                        int blitoffset = Minecraft.getInstance().gui.getBlitOffset();
                        int l1 = (int) (f * 19.0F);
                        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                        AbstractGui.blit(p_238443_2_, k2, j2, blitoffset, 0, 94, 18, 18, 256, 256);
                        AbstractGui.blit(p_238443_2_, k2, j2 + 18 - l1, blitoffset, 18, 112 - l1, 18, l1, 256, 256);
                    }
                }
            }
        }
    }






}
