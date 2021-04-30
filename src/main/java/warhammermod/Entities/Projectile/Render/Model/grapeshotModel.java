package warhammermod.Entities.Projectile.Render.Model;


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class grapeshotModel<T extends Entity> extends Model {
    public ModelRenderer bullet;
    public ModelRenderer bullet1;
    public ModelRenderer bullet2;
    public ModelRenderer bullet3;
    public ModelRenderer bullet4;
    public ModelRenderer bullet5;

    public grapeshotModel(int yoff) {
        super(RenderType::entitySolid);
        this.texWidth = 64;
        this.texHeight = 32;
        this.bullet = new ModelRenderer(this);
        this.bullet1 = new ModelRenderer(this);
        this.bullet2 = new ModelRenderer(this);
        this.bullet3 = new ModelRenderer(this);
        this.bullet4 = new ModelRenderer(this);
        this.bullet5 = new ModelRenderer(this);
        this.bullet.texOffs(0,yoff).setPos(0.0F, 0.0F, 0.0F);
        this.bullet1.texOffs(0,yoff).setPos(0.0F, 0.0F, 0.0F);
        this.bullet2.texOffs(0,yoff).setPos(0.0F, 0.0F, 0.0F);
        this.bullet3.texOffs(0,yoff).setPos(0.0F, 0.0F, 0.0F);
        this.bullet4.texOffs(0,yoff).setPos(0.0F, 0.0F, 0.0F);
        this.bullet5.texOffs(0,yoff).setPos(0.0F, 0.0F, 0.0F);
        this.bullet1.addBox(0F, 0F, 0F, 1, 1, 1, 0.1F);
        this.bullet2.addBox(-1.0F, -1.0F, -1.0F, 1, 1, 1, 0.1F);
        this.bullet3.addBox(0.5F, 1F, -2F, 1, 1, 1, 0.1F);
        this.bullet4.addBox(1.5F, -1F, 1F, 1, 1, 1, 0.1F);
        this.bullet5.addBox(-1.5F, 1F, -1.5F, 1, 1, 1, 0.1F);
        this.bullet.addBox(2F, 0.5F, 0F, 1, 1, 1, 0.1F);
    }


    public void setRotationAngles(T p_225597_1_, float partialticks, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

        bullet.xRot=p_225597_1_.tickCount*1.5F +2;
        bullet2.yRot=-p_225597_1_.tickCount*1.5F -1;
        bullet3.zRot=-p_225597_1_.tickCount*1.5F -2;
        bullet4.xRot=p_225597_1_.tickCount*1.5F +1.5F;
        bullet5.zRot=-p_225597_1_.tickCount*1.5F -1.5F;
    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.bullet,bullet1,bullet2,bullet3,bullet4,bullet5);
    }

    public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
        this.bullet.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.bullet1.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.bullet2.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.bullet3.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.bullet5.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.bullet4.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
    }


}
