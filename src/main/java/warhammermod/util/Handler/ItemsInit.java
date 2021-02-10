package warhammermod.util.Handler;


import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import warhammermod.Items.Beertemplate;
import warhammermod.Items.Lorebook;
import warhammermod.Items.Ranged.Ammo.Cartridge;
import warhammermod.Items.Ranged.*;
import warhammermod.Items.Render.Model.*;
import warhammermod.Items.Render.RenderRatlingGun;
import warhammermod.Items.Render.RenderRepeater;
import warhammermod.Items.Render.RenderShield;
import warhammermod.Items.Render.RenderSling;
import warhammermod.Items.melee.*;
import warhammermod.Items.melee.special.Ghal_Maraz;
import warhammermod.Items.melee.special.Great_pick;
import warhammermod.util.reference;

import java.util.ArrayList;
import java.util.List;

public class ItemsInit {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static Item Pegasus_SPAWN_EGG;
    public static Item Skaven_SPAWN_EGG;
    public static Item DWARF_SPAWN_EGG;

    //public static Item testbook = new Lorebook((new Item.Properties()).maxStackSize(16)).setRegistryName(location("testbook"));


    public static Item Cartridge = new Cartridge().setRegistryName(location("cartridge"));
    public static Item Shell = new Cartridge().setRegistryName(location("shotgun_shell"));
    public static Item Grenade= new Cartridge().setRegistryName(location("grenade"));
    public static Item Warpstone= new Cartridge().setRegistryName(location("warpstone"));

    public static Item Beer = new Beertemplate().setRegistryName(location("beer"));

    public static final Item diamond_warhammer = new Hammertemplate(ItemTier.DIAMOND,new Item.Properties()).setRegistryName(location("diamond_warhammer"));
    public static final Item wooden_warhammer = new Hammertemplate(ItemTier.WOOD,new Item.Properties()).setRegistryName(location("wooden_warhammer"));
    public static final Item stone_warhammer = new Hammertemplate(ItemTier.STONE,new Item.Properties()).setRegistryName(location("stone_warhammer"));
    public static final Item iron_warhammer = new Hammertemplate(ItemTier.IRON,new Item.Properties()).setRegistryName(location("iron_warhammer"));
    public static final Item gold_warhammer = new Hammertemplate(ItemTier.GOLD,new Item.Properties()).setRegistryName(location("gold_warhammer"));

    public static final Item diamond_knife = new Knifetemplate(ItemTier.DIAMOND).setRegistryName(location("diamond_knife"));
    public static final Item wooden_knife = new Knifetemplate(ItemTier.WOOD).setRegistryName(location("wooden_knife"));
    public static final Item stone_knife = new Knifetemplate(ItemTier.STONE).setRegistryName(location("stone_knife"));
    public static final Item iron_knife = new Knifetemplate(ItemTier.IRON).setRegistryName(location("iron_knife"));
    public static final Item gold_knife = new Knifetemplate(ItemTier.GOLD).setRegistryName(location("gold_knife"));

    public static final Item diamond_spear = new SpearTemplate(ItemTier.DIAMOND).setRegistryName(location("diamond_spear"));
    public static final Item wooden_spear= new SpearTemplate(ItemTier.WOOD).setRegistryName(location("wooden_spear"));
    public static final Item stone_spear= new SpearTemplate(ItemTier.STONE).setRegistryName(location("stone_spear"));
    public static final Item iron_spear = new SpearTemplate(ItemTier.IRON).setRegistryName(location("iron_spear"));
    public static final Item gold_spear = new SpearTemplate(ItemTier.GOLD).setRegistryName(location("gold_spear"));

    public static final Item diamond_halberd = new Halberdtemplate(ItemTier.DIAMOND).setRegistryName(location("diamond_halberd"));
    public static final Item wooden_halberd= new Halberdtemplate(ItemTier.WOOD).setRegistryName(location("wooden_halberd"));
    public static final Item stone_halberd= new Halberdtemplate(ItemTier.STONE).setRegistryName(location("stone_halberd"));
    public static final Item iron_halberd = new Halberdtemplate(ItemTier.IRON).setRegistryName(location("iron_halberd"));
    public static final Item gold_halberd = new Halberdtemplate(ItemTier.GOLD).setRegistryName(location("gold_halberd"));

    public static final Item DIAMOND_CHAINMAIL_HELMET =  new ArmorTemplate(ModArmorMaterial.DIAMOND_CHAINMAIL, EquipmentSlotType.HEAD, (new Item.Properties())).setRegistryName(location("diamond_chainmail_helmet"));
    public static final Item DIAMOND_CHAINMAIL_CHESTPLATE =  new ArmorTemplate(ModArmorMaterial.DIAMOND_CHAINMAIL, EquipmentSlotType.CHEST, (new Item.Properties())).setRegistryName(location("diamond_chainmail_chestplate"));
    public static final Item DIAMOND_CHAINMAIL_LEGGINGS = new ArmorTemplate(ModArmorMaterial.DIAMOND_CHAINMAIL, EquipmentSlotType.LEGS, (new Item.Properties())).setRegistryName(location("diamond_chainmail_leggings"));
    public static final Item DIAMOND_CHAINMAIL_BOOTS =  new ArmorTemplate(ModArmorMaterial.DIAMOND_CHAINMAIL, EquipmentSlotType.FEET, (new Item.Properties())).setRegistryName(location("diamond_chainmail_boots"));

    public static final Item Great_pick = new Great_pick(new Item.Properties().setNoRepair()).setRegistryName("war_pick");
    public static final Item Ghal_Maraz = new Ghal_Maraz(new Item.Properties().setNoRepair().maxDamage((int)(ItemTier.DIAMOND.getMaxUses()*1.3))).setRegistryName("ghal_maraz");

    public static Item musket = new GunTemplate(new Item.Properties().maxDamage(420).group(reference.warhammer), reference.Cartridge,40,1,13).setRegistryName(location("musket"));
    public static Item pistol = new GunTemplate(new Item.Properties().maxDamage(384).group(reference.warhammer), reference.Cartridge,25,1,8).setRegistryName(location("flintlock_pistol"));
    public static Item repeater_handgun = new GunTemplate(new Item.Properties().maxDamage(500).group(reference.warhammer).setISTER(()-> { return () -> { return new RenderRepeater(); };}), reference.Cartridge,92,6,13).setRegistryName(location("nuln_repeater_handgun"));
    public static Item grenade_launcher = new GrenadeTemplate(new Item.Properties().maxDamage(384).group(reference.warhammer), reference.Cartridge,75,1).setRegistryName(location("grenade_launcher"));
    public static Item Warplock_jezzail = new WarpgunTemplate(new Item.Properties().maxDamage(384).group(reference.warhammer), reference.Warpstone,50,1,14).setRegistryName(location("warplock_jezzail"));
    public static Item blunderbuss = new ShotgunTemplate(new Item.Properties().maxDamage(390).group(reference.warhammer), reference.Shotgun_Shells,40,1,19).setRegistryName(location("blunderbuss"));
    public static Item thunderer_handgun = new GunTemplate(new Item.Properties().maxDamage(540).group(reference.warhammer), reference.Cartridge,35,1,16).setRegistryName(location("thunderer_handgun"));
    public static Item DrakeGun = new DrakeGunTemplate(new Item.Properties().maxDamage(210).group(reference.warhammer),64,40).setRegistryName(location("drakegun"));
    public static Item RatlingGun = new RatlingGun(new Item.Properties().maxDamage(210).group(reference.warhammer).setISTER(()-> { return () -> { return new RenderRatlingGun(); };}),64,80).setRegistryName(location("ratling_gun"));
    public static Item GrudgeRaker = new ShotgunTemplate(new Item.Properties().maxDamage(450).group(reference.warhammer), reference.Shotgun_Shells,40,2,19).setRegistryName(location("grudgeraker"));
    public static Item Sling = new Slingtemplate(new Item.Properties().maxDamage(180).group(reference.warhammer).setISTER(()-> { return () -> { return new RenderSling(); };})).setRegistryName("sling");

    public static Item iron_gunsword = new GunSwordTemplate(ItemTier.IRON,25).setRegistryName(location("iron_gunsword"));
    public static Item diamond_gunsword = new GunSwordTemplate(ItemTier.DIAMOND,25).setRegistryName(location("diamond_gunsword"));


    public static Item High_Elf_Shield = new shieldtemplate(1,(new Item.Properties().setISTER(()-> {
        return () -> {
            return new RenderShield("textures/items/shields/high_elf_shield.png",new highelfshieldmodel());
        };
    }))).setRegistryName(location("high_elf_shield"));
    public static Item Dwarf_shield = new shieldtemplate(0,(new Item.Properties().setISTER(()-> {
        return () -> {
            return new RenderShield("textures/items/shields/dwarf_shield.png",new dwarfshieldmodel());
        };
    }))).setRegistryName(location("dwarf_shield"));
    public static Item Empire_shield = new shieldtemplate(0,(new Item.Properties().setISTER(()-> {
        return () -> {
            return new RenderShield("textures/items/shields/empire_shield.png",new EmpireShieldmodel());
        };
    }))).setRegistryName(location("imperial_shield"));
    public static Item Skaven_shield = new shieldtemplate(0,(new Item.Properties().setISTER(()-> {
        return () -> {
            return new RenderShield("textures/items/shields/skaven_shield.png",new SkavenShieldModel());
        };
    }))).setRegistryName(location("skaven_shield"));
    public static Item DArk_Elf_shield = new shieldtemplate(1,(new Item.Properties().setISTER(()-> {
        return () -> {
            return new RenderShield("textures/items/shields/dark_elf_shield.png",new DarkElfshieldmodel());
        };
    }))).setRegistryName(location("dark_elf_shield"));




    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, name);
    }







}
