package warhammermod.Entities.Render.Model;


import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class WarpBulletModel <T extends Entity> extends SegmentedModel<T> {
    public ModelRenderer bullet;

    public WarpBulletModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.bullet = new ModelRenderer(this, 0, 8);
        this.bullet.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bullet.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
    }

    public void setRotationAngles(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.bullet);
    }

}
