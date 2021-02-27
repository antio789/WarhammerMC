package warhammermod.Entities.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class bullet_model extends ModelBase {
    public ModelRenderer bullet;

    public bullet_model(int yoff) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.bullet = new ModelRenderer(this, 0, yoff);
        this.bullet.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bullet.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.bullet.render(f5);
    }


}
