package warhammermod.Entities.Living.Renders;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import warhammermod.Entities.Living.Aimanager.Data.DwarfProfession;
import warhammermod.Entities.Living.DwarfEntity;
import warhammermod.Entities.Living.Renders.Layers.DwarfLevellayer;
import warhammermod.Entities.Living.Renders.Models.DwarfModel;
import warhammermod.utils.reference;


public class DwarfRenderer extends MobRenderer<DwarfEntity, DwarfModel<DwarfEntity>> {
    private static final ResourceLocation warrior = new ResourceLocation(reference.modid,"textures/entity/dwarf/warrior.png");
    private static final ResourceLocation miner = new ResourceLocation(reference.modid,"textures/entity/dwarf/miner.png");
    private static final ResourceLocation slayer = new ResourceLocation(reference.modid,"textures/entity/dwarf/slayer.png");
    private static final ResourceLocation builder = new ResourceLocation(reference.modid,"textures/entity/dwarf/builder.png");
    private static final ResourceLocation engineer = new ResourceLocation(reference.modid,"textures/entity/dwarf/engineer.png");
    private static final ResourceLocation farmer = new ResourceLocation(reference.modid,"textures/entity/dwarf/farmer.png");
    private static final ResourceLocation lord = new ResourceLocation(reference.modid,"textures/entity/dwarf/lord.png");
    private ResourceLocation[] Dwarf_Textures = {miner,builder,engineer,farmer,slayer,lord,warrior}; //

    public DwarfRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new DwarfModel<>(0.0F), 0.25F); //p3 0.5F default
        this.addLayer(new HeldItemLayer<DwarfEntity, DwarfModel<DwarfEntity>>(this) {
            public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, DwarfEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
                if (p_225628_4_.isAggressive()) {
                    super.render(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_, p_225628_8_, p_225628_9_, p_225628_10_);
                }

            }
        });
        this.addLayer(new DwarfLevellayer<>(this));
    }

    public ResourceLocation getTextureLocation(DwarfEntity entity)
    {
        return Dwarf_Textures[entity.getProfessionID()];
    }

/*
    public int getBlockLight(DwarfEntity entityIn, BlockPos partialTicks) {
        return entityIn.isOnFire() || entityIn.getProfession().equals(DwarfProfession.Miner) ? 15 : entityIn.level.getLightFor(LightType.BLOCK, partialTicks);
    }
*/
    public int getBlockLightLevel(DwarfEntity entity, BlockPos p_225624_2_) {
        return entity.isOnFire() || entity.getProfession().equals(DwarfProfession.Miner)? 15 : entity.level.getBrightness(LightType.BLOCK, p_225624_2_);
    }
    protected void scale(DwarfEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
        float f = 0.9375F;
        if (p_225620_1_.isBaby()) {
            f = (float)((double)f * 0.5D);
            this.shadowRadius = 0.25F;
        } else {
            this.shadowRadius = 0.5F;
        }

        p_225620_2_.scale(f, f, f);
    }


}
