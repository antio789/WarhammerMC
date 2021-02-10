package warhammermod.Entities.Render.Model;


import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class grapeshotModel<T extends Entity> extends SegmentedModel<T> {
    public ModelRenderer bullet;
    public ModelRenderer bullet1;
    public ModelRenderer bullet2;
    public ModelRenderer bullet3;
    public ModelRenderer bullet4;
    public ModelRenderer bullet5;

    public grapeshotModel(int yoff) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.bullet = new ModelRenderer(this);
        this.bullet1 = new ModelRenderer(this);
        this.bullet2 = new ModelRenderer(this);
        this.bullet3 = new ModelRenderer(this);
        this.bullet4 = new ModelRenderer(this);
        this.bullet5 = new ModelRenderer(this);
        this.bullet.setTextureOffset(0,yoff).setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bullet1.setTextureOffset(0,yoff).setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bullet2.setTextureOffset(0,yoff).setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bullet3.setTextureOffset(0,yoff).setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bullet4.setTextureOffset(0,yoff).setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bullet5.setTextureOffset(0,yoff).setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bullet1.addBox(0F, 0F, 0F, 1, 1, 1, 0.1F);
        this.bullet2.addBox(-1.0F, -1.0F, -1.0F, 1, 1, 1, 0.1F);
        this.bullet3.addBox(0.5F, 1F, -2F, 1, 1, 1, 0.1F);
        this.bullet4.addBox(1.5F, -1F, 1F, 1, 1, 1, 0.1F);
        this.bullet5.addBox(-1.5F, 1F, -1.5F, 1, 1, 1, 0.1F);
        this.bullet.addBox(2F, 0.5F, 0F, 1, 1, 1, 0.1F);
    }


    public void setRotationAngles(T p_225597_1_, float partialticks, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

        bullet.rotationPointX=p_225597_1_.ticksExisted*1.5F +2;
        bullet2.rotationPointY=-p_225597_1_.ticksExisted*1.5F -1;
        bullet3.rotationPointZ=-p_225597_1_.ticksExisted*1.5F -2;
        bullet4.rotationPointX=p_225597_1_.ticksExisted*1.5F +1.5F;
        bullet5.rotationPointZ=-p_225597_1_.ticksExisted*1.5F -1.5F;
    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.bullet,bullet1,bullet2,bullet3,bullet4,bullet5);
    }


}
