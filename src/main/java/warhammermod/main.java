package warhammermod;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import warhammermod.Entities.FlameEntity;
import warhammermod.Entities.Render.*;
import warhammermod.Entities.StoneEntity;
import warhammermod.Entities.living.Data.EntititiesAttributes;
import warhammermod.Entities.living.Render.DwarfRenderer;
import warhammermod.Entities.living.Render.PegasusRenderer;
import warhammermod.Entities.living.Render.SkavenRenderer;
import warhammermod.Items.melee.shieldtemplate;
import warhammermod.util.Handler.EntitySpawning;
import warhammermod.util.Handler.Entityinit;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.Handler.WarhammermodRegistry;
import warhammermod.util.Itimetoreload;
import warhammermod.worldgen.biomesgeneration;

@Mod("warhammermod")
public class main
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();


    public main() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        WarhammermodRegistry.STRUCTURES.register(eventBus);
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


    }


    private void setup(final FMLCommonSetupEvent event)
    {

        // some preinit code
        EntitySpawning.setSpawnsinWorld();
        EntitySpawning.setEntityplacement();
        WarhammermodRegistry.registerpieces();
        EntititiesAttributes.registerattributes();
        DeferredWorkQueue.runLater(() -> {
            biomesgeneration.setStructuresGeneration();
        });


    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.PEGASUS, PegasusRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.SKAVEN, SkavenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.DWARF,DwarfRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(Entityinit.Bullet, BulletRender::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.HalberdEntity, Halberdrender::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.warpbullet, WarpbulletRender::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.grenade, GrenadeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.shotgun,grapeshotRender::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.spear, RenderSpear::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.stone, new IRenderFactory<StoneEntity>() {
            @Override
            public EntityRenderer<? super StoneEntity> createRenderFor(EntityRendererManager manager) {
                return new SpriteRenderer(manager, Minecraft.getInstance().getItemRenderer());
            }
            });
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.stone, new IRenderFactory<StoneEntity>() {
            @Override
            public EntityRenderer<? super StoneEntity> createRenderFor(EntityRendererManager manager) {
                return new SpriteRenderer(manager, Minecraft.getInstance().getItemRenderer());
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.flame, new IRenderFactory<FlameEntity>() {
            @Override
            public EntityRenderer<? super FlameEntity> createRenderFor(EntityRendererManager manager) {
                return new SpriteRenderer(manager, Minecraft.getInstance().getItemRenderer());
            }
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod

    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods

    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts

    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here

        }

    }
    @SubscribeEvent
    public void fovevent(FOVUpdateEvent event){

        if(event.getEntity().getActiveItemStack().getItem() instanceof shieldtemplate){
            event.setNewfov(1.0F);
        }
        else if(event.getEntity().getActiveItemStack().getItem().equals(ItemsInit.Warplock_jezzail)){
            event.setNewfov(0.5F);
        }
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void guievent(RenderGameOverlayEvent event){
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR)){
            PlayerEntity player = Minecraft.getInstance().player;

            if(player.isHandActive()&& !player.isCreative() && player.getActiveItemStack().getItem() instanceof Itimetoreload) {
                if (!(((Itimetoreload) player.getActiveItemStack().getItem()).isReadytoFire(player.getActiveItemStack()))) {
                    Minecraft mc = Minecraft.getInstance();
                    int reloadtime = ((Itimetoreload) player.getActiveItemStack().getItem()).getTimetoreload();
                    float diff = player.getItemInUseCount() - player.getActiveItemStack().getUseDuration() + reloadtime;
                    float percentage = diff / reloadtime;


                    draw( mc.getMainWindow().getScaledWidth() / 2 - 91,percentage,event.getMatrixStack());
                }
            }
        }
    }
    @OnlyIn(Dist.CLIENT)
    private void draw(int x, float percentage, MatrixStack matrix) {
        Minecraft mc = Minecraft.getInstance();
        IngameGui gui = mc.ingameGUI;
        int w = mc.getMainWindow().getScaledWidth();
        int h = mc.getMainWindow().getScaledHeight();
        gui.func_238474_b_(matrix, (w - 15) / 2, (h - 15) / 2, 0, 0, 15, 15);
        float f = percentage;
        if (f < 1.0F) {



            int j = h / 2 - 7 + 16;
            int k = w / 2 - 8;
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            int l = (int)(f * 17.0F);
            gui.func_238474_b_(matrix, k, j, 36, 94, 16, 4);
            gui.func_238474_b_(matrix, k, j, 52, 94, l, 4);
        }
    }
    @OnlyIn(Dist.CLIENT)
    public static final KeyBinding horsedown = new KeyBinding("pegasus down", 66, "key.categories.movement");



}
