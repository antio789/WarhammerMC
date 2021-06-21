package warhammermod.util.Handler.Registryhandler;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Entities.*;
import warhammermod.Entities.living.EntityDwarf;
import warhammermod.Entities.living.EntityPegasus;
import warhammermod.Entities.living.EntityPirateSkeleton;
import warhammermod.Entities.living.EntitySkaven;
import warhammermod.Entities.living.Render.RenderDwarf;
import warhammermod.Entities.living.Render.RenderPegasus;
import warhammermod.Entities.living.Render.RenderSkaven;
import warhammermod.Entities.living.Render.Rendermusketeer_skeleton;
import warhammermod.Items.Melee.shieldtemplate;
import warhammermod.util.Handler.inithandler.Itemsinit;
import warhammermod.util.confighandler.confighandler;
import warhammermod.util.reference;

import static warhammermod.util.reference.modid;

@Mod.EventBusSubscriber
public class Registryhandler {

    @SubscribeEvent
    public static void onItemregister(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(Itemsinit.ITEMS.toArray(new Item[0]));
    }




    /*@SubscribeEvent
    public static void onBlockregister(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(Modblocks.BLOCKS.toArray(new Block[0]));
    }*/

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelregister(ModelRegistryEvent event){
        for(Item item : Itemsinit.ITEMS){
            registryitemrenderer(item,0,"inventory");
        }
        RenderingRegistry.registerEntityRenderingHandler(EntityDwarf.class, new IRenderFactory<EntityDwarf>() {
            @Override
            public RenderLiving<? super EntityDwarf> createRenderFor(RenderManager manager) {
                return new RenderDwarf(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityPegasus.class, new IRenderFactory<EntityPegasus>() {
            @Override
            public RenderLiving<? super EntityPegasus> createRenderFor(RenderManager manager) {
                return new RenderPegasus(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityPirateSkeleton.class, new IRenderFactory<EntityPirateSkeleton>() {
            @Override
            public RenderLiving<? super EntityPirateSkeleton> createRenderFor(RenderManager manager) {
                return new Rendermusketeer_skeleton(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySkaven.class, new IRenderFactory<EntitySkaven>() {
            @Override
            public RenderLiving<? super EntitySkaven> createRenderFor(RenderManager manager) {
                return new RenderSkaven(manager);
            }
        });
    }
    @SubscribeEvent
    public static void entityis(RegistryEvent.Register<EntityEntry> event){
        EntityRegistry.registerModEntity(new ResourceLocation(modid+":"+"bullet"), EntityBullet.class,modid+"."+"bullet",0, modid,64,30,true);
        EntityRegistry.registerModEntity(new ResourceLocation(modid+":"+"spear"), Entityspear.class,modid+"."+"spear_entity",1, modid,55,30,true);
        EntityRegistry.registerModEntity(new ResourceLocation(modid+":"+"grenade"), EntityGrenade.class,modid+"."+"grenade_entity",2, modid,64,30,true);
        EntityRegistry.registerModEntity(new ResourceLocation(modid+":"+"halberd"), EntityHalberd.class,modid+"."+"halberd_entity",3, modid,10,30,true);
        EntityRegistry.registerModEntity(new ResourceLocation(modid+":"+"dwarf"), EntityDwarf.class,modid+"."+"dwarf",4, modid,64,1,true,1599971,15721509);
        EntityRegistry.registerModEntity(new ResourceLocation(modid+":"+"shotgun pellet"), Entityshotgun.class,modid+"."+"pellet",5, modid,64,30,true);
        EntityRegistry.registerModEntity(new ResourceLocation(modid+":"+"pegasus"), EntityPegasus.class,modid+"."+"pegasus",6, modid,64,1,true,15528173,15395562);
        EntityRegistry.registerModEntity(new ResourceLocation(modid+":"+"PirateSkeleton"), EntityPirateSkeleton.class,modid+"."+"Pirate Skeleton",7, modid,64,1,true,12698049,12910592);

        EntityRegistry.registerModEntity(new ResourceLocation(modid+":"+"skaven"), EntitySkaven.class,modid+"."+"skaven",8, modid,64,1,true,13698049,11910592);
        EntityRegistry.registerModEntity(new ResourceLocation(modid+":"+"Warp bullet"), EntityWarpBullet.class,modid+"."+"warp bullet",9, modid,64,30,true);
        EntityRegistry.registerModEntity(new ResourceLocation(modid+":"+"stone"), EntityStone.class,modid+"."+"stone",10, modid,64,30,true);
        EntityRegistry.registerModEntity(new ResourceLocation(modid+":"+"flame"), Entityflame.class,modid+"."+"flame",11, modid,64,30,true);


    }

    public static void registryitemrenderer(Item item, int meta, String id) {
        if(item instanceof shieldtemplate){
            ModelLoader.setCustomModelResourceLocation(item, meta,new ModelResourceLocation("minecraft:shield",id));
        }
        else if(item.getRegistryName().toString().equals("warhammermod:nuln repeater handgun")){
            ModelResourceLocation repeater_3d = new ModelResourceLocation("warhammermod:nuln repeater handgun3d",id);
            ModelResourceLocation repeater_normal = new ModelResourceLocation("warhammermod:nuln repeater handgun",id);
            ModelLoader.registerItemVariants(item, repeater_3d,repeater_normal);
            ModelLoader.setCustomMeshDefinition(item,(stack -> {if(confighandler.Config_enable.repeater_3D_model){
                return repeater_3d;}
            else {return repeater_normal;}
            }));
        }
        else {
            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
        }
    }




}
