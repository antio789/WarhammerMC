package warhammermod.utils.inithandler;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import warhammermod.Entities.Living.Aimanager.Data.DwarfProfession;
import warhammermod.Entities.Living.Aimanager.Data.EntityAttributes;
import warhammermod.Entities.Living.Aimanager.brain.sensor.LordLastSeenSensor;
import warhammermod.Entities.Living.Aimanager.brain.sensor.WHSecondaryPositionSensor;
import warhammermod.Entities.Living.Aimanager.brain.sensor.dwarfBabiesSensor;
import warhammermod.Entities.Living.Renders.DwarfRenderer;
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
        Entityinit.DWARF.setRegistryName(location("dwarf"));
        Entityinit.SKAVEN.setRegistryName(location("skaven"));
        Entityinit.Bullet.setRegistryName(location("bullet"));
        Entityinit.Grenade.setRegistryName(location("grenadeentity"));
        Entityinit.ShotEntity.setRegistryName(location("shotgunentity"));
        Entityinit.Spear.setRegistryName(location("spearentity"));
        Entityinit.StoneEntity.setRegistryName(location("stoneentity"));
        Entityinit.WarpBullet.setRegistryName(location("warpbullet"));
        Entityinit.Flame.setRegistryName(location("flameentity"));
        Entityinit.halberdentity.setRegistryName(location("halberd_entity"));

        event.getRegistry().registerAll(Entityinit.Bullet,Entityinit.Grenade,Entityinit.ShotEntity,Entityinit.Spear,Entityinit.StoneEntity,Entityinit.WarpBullet,Entityinit.Flame,Entityinit.halberdentity,Entityinit.PEGASUS,Entityinit.SKAVEN,Entityinit.DWARF);
    }
    //just why
    public static void registerattributes(EntityAttributeCreationEvent event){
        event.put(Entityinit.PEGASUS, AbstractHorseEntity.createBaseHorseAttributes().build());
        event.put(Entityinit.SKAVEN, SkavenEntity.createAttributes().build());
        event.put(Entityinit.DWARF, EntityAttributes.registerDwarfTypesattributes().build());
    }

    public static void Registerrenderer(){
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.PEGASUS, PegasusRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Entityinit.DWARF, DwarfRenderer::new);
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
     * AI parts
     */
    public static SensorType<WHSecondaryPositionSensor> SECONDARY_POIS;
    public static SensorType<LordLastSeenSensor> Lord_LastSeen;
    public static SensorType<dwarfBabiesSensor> VISIBLE_VILLAGER_BABIES;

    public static void registersensor(){
        SECONDARY_POIS = registersensor("dwarf_secondary_pois");
        Lord_LastSeen = registerlordseen("lord_lastseen");
        VISIBLE_VILLAGER_BABIES = registerbabysensor("baby_sensor_wh");
    }

    private static SensorType registersensor(String name){
        ResourceLocation location = new ResourceLocation(reference.modid, name);
        SensorType event = new SensorType(WHSecondaryPositionSensor::new);
        event.setRegistryName(location);
        ForgeRegistries.SENSOR_TYPES.register(event);
        return event;
    }
    private static SensorType registerlordseen(String name){
        ResourceLocation location = new ResourceLocation(reference.modid,name);
        SensorType event = new SensorType(LordLastSeenSensor::new);
        event.setRegistryName(location);
        ForgeRegistries.SENSOR_TYPES.register(event);
        return event;
    }
    private static SensorType registerbabysensor(String name){
        ResourceLocation location = new ResourceLocation(reference.modid, name);
        SensorType event = new SensorType(dwarfBabiesSensor::new);
        event.setRegistryName(location);
        ForgeRegistries.SENSOR_TYPES.register(event);
        return event;
    }


    public static void registerprofessions(){
        DwarfProfession.Warrior = new DwarfProfession("warrior", 6, PointOfInterestType.UNEMPLOYED, null, Items.IRON_AXE,ItemsInit.Dwarf_shield);
        DwarfProfession.Miner = new DwarfProfession("miner", 0, PointOfInterestType.ARMORER, null, Items.IRON_PICKAXE);
        DwarfProfession.Builder = new DwarfProfession("builder", 1, PointOfInterestType.MASON, SoundEvents.VILLAGER_WORK_MASON,ItemsInit.iron_warhammer);
        DwarfProfession.Engineer = new DwarfProfession("engineer", 2, PointOfInterestType.TOOLSMITH, SoundEvents.VILLAGER_WORK_ARMORER, Items.IRON_AXE,ItemsInit.Dwarf_shield);
        DwarfProfession.FARMER = new DwarfProfession("farmer", 3, PointOfInterestType.FARMER, ImmutableSet.of(Items.WHEAT, Items.WHEAT_SEEDS), ImmutableSet.of(Blocks.FARMLAND), SoundEvents.VILLAGER_WORK_FARMER, Items.IRON_AXE, ItemStack.EMPTY.getItem());
        DwarfProfession.Slayer = new DwarfProfession("slayer", 4, PointOfInterestType.BUTCHER, SoundEvents.VILLAGER_WORK_BUTCHER, Items.IRON_AXE, Items.IRON_AXE);
        DwarfProfession.Lord = new DwarfProfession("lord", 5, PointOfInterestType.NITWIT, SoundEvents.VILLAGER_WORK_CLERIC,ItemsInit.diamond_warhammer,ItemsInit.Dwarf_shield);
    }

    /**
     *helper methods
     */

    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, name);
    }

}
