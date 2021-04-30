package warhammermod.utils.inithandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import warhammermod.Entities.Living.Renders.PegasusRenderer;
import warhammermod.Entities.Living.Renders.SkavenRenderer;
import warhammermod.Entities.Living.SkavenEntity;
import warhammermod.Entities.Projectile.FlameEntity;
import warhammermod.Entities.Projectile.Render.*;
import warhammermod.Entities.Projectile.StoneEntity;
import warhammermod.utils.reference;



public class WarhammermodRegistry {
    /**
     * Sounds
     */

    public static SoundEvent ratambient;
    public static SoundEvent ratdeath;
    public static SoundEvent rathurt;
    public static SoundEvent flame;

    public static void registersounds(){
        ratambient = registersound("entity.skaven.ambient");
        ratdeath = registersound("entity.skaven.death");
        rathurt = registersound("entity.skaven.hurt");
        flame = registersound("flamethrower");
    }

    private static SoundEvent registersound(String name){
        ResourceLocation location = new ResourceLocation(reference.modid, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(location);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

    /**
     * entities
     */

    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event){
        Entityinit.PEGASUS.setRegistryName(location("pegasus"));
        //Entityinit.DWARF.setRegistryName(location("dwarf"));
        Entityinit.SKAVEN.setRegistryName(location("skaven"));
        Entityinit.Bullet.setRegistryName(location("bullet"));
        Entityinit.Grenade.setRegistryName(location("grenadeentity"));
        Entityinit.ShotEntity.setRegistryName(location("shotgunentity"));
        Entityinit.Spear.setRegistryName(location("spearentity"));
        Entityinit.StoneEntity.setRegistryName(location("stoneentity"));
        Entityinit.WarpBullet.setRegistryName(location("warpbullet"));
        Entityinit.Flame.setRegistryName(location("flameentity"));
        Entityinit.halberdentity.setRegistryName(location("halberd_entity"));

        event.getRegistry().registerAll(Entityinit.Bullet,Entityinit.Grenade,Entityinit.ShotEntity,Entityinit.Spear,Entityinit.StoneEntity,Entityinit.WarpBullet,Entityinit.Flame,Entityinit.halberdentity,Entityinit.PEGASUS,Entityinit.SKAVEN);
    }
    //just why
    public static void registerattributes(EntityAttributeCreationEvent event){
        event.put(Entityinit.PEGASUS, AbstractHorseEntity.createBaseHorseAttributes().build());
        event.put(Entityinit.SKAVEN, SkavenEntity.createAttributes().build());
    }

    public static void Registerrenderer(){
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.PEGASUS, PegasusRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.SKAVEN, SkavenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.Bullet, BulletRender::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.halberdentity, Halberdrender::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.WarpBullet, WarpbulletRender::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.Grenade, GrenadeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.ShotEntity,grapeshotRender::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.Spear, RenderSpear::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.StoneEntity, new IRenderFactory<StoneEntity>() {
            @Override
            public EntityRenderer<? super StoneEntity> createRenderFor(EntityRendererManager manager) {
                return new SpriteRenderer(manager, Minecraft.getInstance().getItemRenderer());
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.Flame, new IRenderFactory<FlameEntity>() {
            @Override
            public EntityRenderer<? super FlameEntity> createRenderFor(EntityRendererManager manager) {
                return new SpriteRenderer(manager, Minecraft.getInstance().getItemRenderer());
            }
        });

    }

    /**
     *helper methods
     */

    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, name);
    }

}
