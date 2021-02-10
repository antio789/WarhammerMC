package warhammermod.util.Handler;


import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.ResourceLocation;
import warhammermod.Entities.*;
import warhammermod.Entities.living.Data.DwarfProfession;
import warhammermod.Entities.living.DwarfEntity;
import warhammermod.Entities.living.PegasusEntity;
import warhammermod.Entities.living.SkavenEntity;
import warhammermod.util.reference;

import java.util.ArrayList;
import java.util.List;

public class Entityinit {
    public static final List<DwarfProfession> PROFESSION_LIST = new ArrayList<>();

    public static EntityType<warhammermod.Entities.HalberdEntity> HalberdEntity = EntityType.Builder.<HalberdEntity>create(HalberdEntity::new, EntityClassification.MISC).setCustomClientFactory(HalberdEntity::new).setTrackingRange(60).setUpdateInterval(5).setShouldReceiveVelocityUpdates(true).size(0.5F,0.5F).build(location("halberdentity").toString());
    public static EntityType<SpearEntity> spear = EntityType.Builder.<SpearEntity>create(SpearEntity::new, EntityClassification.MISC).setCustomClientFactory(SpearEntity::new).setTrackingRange(60).setUpdateInterval(5).setShouldReceiveVelocityUpdates(true).size(0.5F,0.5F).build(location("spearentity").toString());
    public static EntityType<WarpBulletEntity> warpbullet = EntityType.Builder.<WarpBulletEntity>create(WarpBulletEntity::new, EntityClassification.MISC).setCustomClientFactory(WarpBulletEntity::new).setTrackingRange(60).setUpdateInterval(5).setShouldReceiveVelocityUpdates(true).size(0.5F,0.5F).build(location("warpbullet").toString());
    public static EntityType<FlameEntity> flame = EntityType.Builder.<FlameEntity>create(FlameEntity::new, EntityClassification.MISC).setCustomClientFactory(FlameEntity::new).setTrackingRange(60).setUpdateInterval(5).setShouldReceiveVelocityUpdates(true).size(0.5F,0.5F).build(location("flameentity").toString());
    public static EntityType<GrenadeEntity> grenade = EntityType.Builder.<GrenadeEntity>create(GrenadeEntity::new, EntityClassification.MISC).setCustomClientFactory(GrenadeEntity::new).setTrackingRange(60).setUpdateInterval(5).setShouldReceiveVelocityUpdates(true).size(0.5F,0.5F).build(location("grenadeentity").toString());
    public static EntityType<ShotgunEntity> shotgun  = EntityType.Builder.<ShotgunEntity>create(ShotgunEntity::new, EntityClassification.MISC).setCustomClientFactory(ShotgunEntity::new).setTrackingRange(60).setUpdateInterval(5).setShouldReceiveVelocityUpdates(true).size(0.5F,0.5F).build(location("shotgunentity").toString());
    public static EntityType<StoneEntity> stone = EntityType.Builder.<StoneEntity>create(StoneEntity::new, EntityClassification.MISC).setCustomClientFactory(StoneEntity::new).setTrackingRange(60).setUpdateInterval(5).setShouldReceiveVelocityUpdates(true).size(0.5F,0.5F).build(location("stoneentity").toString());
    public static EntityType<BulletEntity> Bullet = EntityType.Builder.<BulletEntity>create(BulletEntity::new, EntityClassification.MISC).setCustomClientFactory(BulletEntity::new).setTrackingRange(60).setUpdateInterval(5).setShouldReceiveVelocityUpdates(true).size(0.5F,0.5F).build(location("bullet").toString());

    public static EntityType<PegasusEntity> PEGASUS = EntityType.Builder.create(warhammermod.Entities.living.PegasusEntity::new, EntityClassification.CREATURE).setTrackingRange(64).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).size(1.3964844F, 1.6F).build(location("pegasus").toString());
    public static EntityType<SkavenEntity> SKAVEN = EntityType.Builder.create(SkavenEntity::new, EntityClassification.MONSTER).setTrackingRange(64).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.6F).build(location("skaven").toString());
    public static EntityType<DwarfEntity> DWARF = EntityType.Builder.create(DwarfEntity::new, EntityClassification.MISC).setTrackingRange(64).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.7F).build(location("dwarf").toString());


    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, name);
    }

}
