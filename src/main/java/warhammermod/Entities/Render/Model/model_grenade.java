package warhammermod.Entities.Render.Model;


import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class model_grenade<T extends Entity> extends SegmentedModel<T> {
    public ModelRenderer grenadepart1;
    public ModelRenderer grenadetop;

    public model_grenade() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.grenadepart1 = new ModelRenderer(this, 3, 0);
        this.grenadepart1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.grenadepart1.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
        this.grenadetop = new ModelRenderer(this, 0, 0);
        this.grenadetop.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.grenadetop.addBox(-0.5F, 1.5F, -0.5F, 1, 1, 1, 0.0F);
    }

    public void setRotationAngles(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.grenadepart1,grenadetop);
    }

}
