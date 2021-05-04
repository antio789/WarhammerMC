package warhammermod.Entities.Living.Renders.Layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import warhammermod.Entities.Living.Aimanager.Data.DwarfProfession;
import warhammermod.Entities.Living.DwarfEntity;
import warhammermod.utils.reference;

import java.util.function.Predicate;

public class DwarfLevellayer<T extends DwarfEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> implements ISelectiveResourceReloadListener {
    private static final Int2ObjectMap<ResourceLocation> LEVEL_LOCATIONS = Util.make(new Int2ObjectOpenHashMap<>(), (p_215348_0_) -> {
        p_215348_0_.put(1, location("stone"));
        p_215348_0_.put(2, location("iron"));
        p_215348_0_.put(3, location("gold"));
        p_215348_0_.put(4, location("emerald"));
        p_215348_0_.put(5, location("diamond"));
    });

    private static ResourceLocation location(String name){
        return new ResourceLocation(reference.modid,"textures/entity/dwarf/belt/"+name+".png");
    }

    public DwarfLevellayer(IEntityRenderer<T, M> p_i50955_1_) {
        super(p_i50955_1_);
    }

    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if (!p_225628_4_.isInvisible()) {
            DwarfProfession prof = p_225628_4_.getProfession();
            M m = this.getParentModel();
            if (prof != DwarfProfession.Warrior && !p_225628_4_.isBaby()) {
                if (prof != DwarfProfession.Lord) {
                    ResourceLocation resourcelocation2 =  LEVEL_LOCATIONS.get(MathHelper.clamp(p_225628_4_.getProfessionLevel(), 1, LEVEL_LOCATIONS.size()));
                    renderColoredCutoutModel(m, resourcelocation2, p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }

    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
    }
}
