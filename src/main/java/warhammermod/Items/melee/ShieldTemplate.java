package warhammermod.Items.melee;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.ResourceLocation;
import warhammermod.Items.GunBase;
import warhammermod.utils.inithandler.ItemsInit;
import warhammermod.utils.reference;

public class ShieldTemplate  extends ShieldItem {


    public ShieldTemplate( Properties builder){

        super(builder.durability(1008).tab(reference.warhammer));
        ItemsInit.ITEMS.add(this);
        ItemModelsProperties.register(this, new ResourceLocation("blocking"), (stack, world, entity) -> {
            return entity != null && entity.isUsingItem() && (entity.getUseItem() == stack || (entity.getOffhandItem() == stack && hasGun(entity, entity.getMainHandItem()))) ? 1.0F : 0.0F;
        });

    }

    private boolean hasGun(LivingEntity entityIn, ItemStack stack){
        if(entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entityIn;
            return stack.getItem() instanceof GunBase && ((GunBase) stack.getItem()).isReadytoFire(stack);
            //return stack.getItem() instanceof GunSwordTemplate && ((GunSwordTemplate) stack.getItem()).hasshield(player) && ((GunSwordTemplate) stack.getItem()).isReadytoFire(stack);
        }return false;
    }


}
