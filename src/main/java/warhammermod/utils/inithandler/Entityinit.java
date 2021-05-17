package warhammermod.utils.inithandler;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import warhammermod.Entities.Living.DwarfEntity;
import warhammermod.Entities.Living.PegasusEntity;
import warhammermod.Entities.Living.SkavenEntity;
import warhammermod.Entities.Projectile.*;
import warhammermod.utils.reference;

public class Entityinit {
    public static EntityType<BulletEntity> Bullet = EntityType.Builder.<BulletEntity>of(BulletEntity::new, EntityClassification.MISC)
            .setCustomClientFactory(BulletEntity::new).setTrackingRange(60).setUpdateInterval(5)
            .setShouldReceiveVelocityUpdates(true).sized(0.5F,0.5F)
            .build(location("bullet").toString());
    public static EntityType<SpearEntity> Spear = EntityType.Builder.<SpearEntity>of(SpearEntity::new, EntityClassification.MISC)
            .setCustomClientFactory(SpearEntity::new).setTrackingRange(60).setUpdateInterval(5)
            .setShouldReceiveVelocityUpdates(true).sized(0.5F,0.5F)
            .build(location("spearentity").toString());
    public static EntityType<HalberdEntity> halberdentity = EntityType.Builder.<HalberdEntity>of(HalberdEntity::new, EntityClassification.MISC)
            .setCustomClientFactory(HalberdEntity::new).setTrackingRange(60).setUpdateInterval(5)
            .setShouldReceiveVelocityUpdates(true).sized(0.5F,0.5F)
            .build(location("halberdentity").toString());
    public static EntityType<FlameEntity> Flame = EntityType.Builder.<FlameEntity>of(FlameEntity::new, EntityClassification.MISC)
            .setCustomClientFactory(FlameEntity::new).setTrackingRange(60).setUpdateInterval(5)
            .setShouldReceiveVelocityUpdates(true).sized(0.5F,0.5F)
            .build(location("flameentity").toString());
    public static EntityType<GrenadeEntity> Grenade = EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, EntityClassification.MISC)
            .setCustomClientFactory(GrenadeEntity::new).setTrackingRange(60).setUpdateInterval(5)
            .setShouldReceiveVelocityUpdates(true).sized(0.5F,0.5F)
            .build(location("grenadeentity").toString());
    public static EntityType<WarpBulletEntity> WarpBullet = EntityType.Builder.<WarpBulletEntity>of(WarpBulletEntity::new, EntityClassification.MISC)
            .setCustomClientFactory(WarpBulletEntity::new).setTrackingRange(60).setUpdateInterval(5)
            .setShouldReceiveVelocityUpdates(true).sized(0.5F,0.5F)
            .build(location("warpbullet").toString());
    public static EntityType<ShotEntity> ShotEntity = EntityType.Builder.<ShotEntity>of(ShotEntity::new, EntityClassification.MISC)
            .setCustomClientFactory(ShotEntity::new).setTrackingRange(60).setUpdateInterval(5)
            .setShouldReceiveVelocityUpdates(true).sized(0.5F,0.5F)
            .build(location("shotentity").toString());
    public static EntityType<StoneEntity> StoneEntity = EntityType.Builder.<StoneEntity>of(StoneEntity::new, EntityClassification.MISC)
            .setCustomClientFactory(StoneEntity::new).setTrackingRange(60).setUpdateInterval(5)
            .setShouldReceiveVelocityUpdates(true).sized(0.5F,0.5F)
            .build(location("stoneentity").toString());


    public static EntityType<PegasusEntity> PEGASUS = EntityType.Builder.<PegasusEntity>of(PegasusEntity::new, EntityClassification.CREATURE)
            .setTrackingRange(64).clientTrackingRange(10).setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true).sized(1.3964844F, 1.6F)
            .build(location("pegasus").toString());
    public static Item Pegasus_SPAWN_EGG= new SpawnEggItem(PEGASUS, 15528173,15395562, (new Item.Properties()).tab(reference.warhammer)).setRegistryName("pegasus_egg");

    public static EntityType<SkavenEntity> SKAVEN = EntityType.Builder.<SkavenEntity>of(SkavenEntity::new, EntityClassification.CREATURE)
            .setTrackingRange(64).clientTrackingRange(10).setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true).sized(1.3964844F, 1.6F)
            .build(location("skaven").toString());
    public static Item SKAVEN_SPAWN_EGG = new SpawnEggItem(SKAVEN,13698049,894731,(new Item.Properties()).tab(reference.warhammer)).setRegistryName("skaven_egg");


    public static EntityType<DwarfEntity> DWARF = EntityType.Builder.<DwarfEntity>of(DwarfEntity::new, EntityClassification.MISC)
            .sized(0.6F, 1.7F).clientTrackingRange(10).build(location("dwarf").toString());
    public static Item DWARF_SPAWN_EGG = new SpawnEggItem(DWARF,1599971,15721509,(new Item.Properties()).tab(reference.warhammer)).setRegistryName("dwarf_egg");







    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, name);
    }

}
