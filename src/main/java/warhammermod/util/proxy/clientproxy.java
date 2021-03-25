package warhammermod.util.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import warhammermod.Entities.*;
import warhammermod.Entities.render.renderbullet;
import warhammermod.Entities.render.rendergrenade;
import warhammermod.Entities.render.renderspear;
import warhammermod.Items.Render.*;

@SideOnly(Side.CLIENT)
public class clientproxy extends commonproxy{
    public static final KeyBinding horsedown = new KeyBinding("pegasus down", KeyConflictContext.IN_GAME, Keyboard.KEY_W,"Warhammermod");
    public static final KeyBinding mapshow = new KeyBinding("show mod info", KeyConflictContext.IN_GAME, Keyboard.KEY_M,"Warhammermod");
    public static int repeater_value=0;

    public void registerclient(){
        ClientRegistry.registerKeyBinding(horsedown);
        ClientRegistry.registerKeyBinding(mapshow);
    }

    public void registryitemrenderer(Item item, int meta, String id){ }

    public void renderentity(){
        RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new IRenderFactory<EntityBullet>() {
            @Override
            public Render<? super EntityBullet> createRenderFor(RenderManager manager) {
                return new renderbullet<>(manager,0);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(Entityspear.class, new IRenderFactory<Entityspear>() {
            @Override
            public Render<? super Entityspear> createRenderFor(RenderManager manager) {
                return new renderspear<>(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new IRenderFactory<EntityGrenade>() {
            @Override
            public Render<? super EntityGrenade> createRenderFor(RenderManager manager) {
                return new rendergrenade<>(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityWarpBullet.class, new IRenderFactory<EntityWarpBullet>() {
            @Override
            public Render<? super EntityWarpBullet> createRenderFor(RenderManager manager) {
                return new renderbullet<>(manager,8);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityStone.class,new IRenderFactory<EntityStone>() {
            @Override
            public Render<? super EntityStone> createRenderFor(RenderManager manager) {
                return new RenderSnowball<>(manager,Item.getItemFromBlock(Blocks.COBBLESTONE), Minecraft.getMinecraft().getRenderItem());
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(Entityflame.class,new IRenderFactory<Entityflame>() {
            @Override
            public Render<? super Entityflame> createRenderFor(RenderManager manager) {
                return new RenderFireball(manager, 0.5F);
            }
        });

    }


    public void settheteisr(String name, Item item){
        switch (name) {
            case "high elf shield":item.setTileEntityItemStackRenderer(new RenderShieldHE());break;
            case "dark elf shield":item.setTileEntityItemStackRenderer(new RendershieldDE());break;
            case "dwarf shield":item.setTileEntityItemStackRenderer(new RenderShieldDW());break;
            case "skaven shield":item.setTileEntityItemStackRenderer(new RendershieldSK());break;
            case "ratling gun":item.setTileEntityItemStackRenderer(new RenderRatlingGun());break;
            case "sling":item.setTileEntityItemStackRenderer(new RenderSling());break;
            case "nuln repeater handgun":item.setTileEntityItemStackRenderer(new RenderRepeater());break;
            default:item.setTileEntityItemStackRenderer(new RenderShieldEM());break;
        }
    }
}

