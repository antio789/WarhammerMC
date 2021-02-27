package warhammermod.Entities.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class model_grenade extends ModelBase {
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

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.grenadepart1.render(f5);
        this.grenadetop.render(f5);
    }


    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
