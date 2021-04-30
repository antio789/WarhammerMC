package warhammermod.utils.inithandler;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import warhammermod.Items.BeerTemplate;
import warhammermod.Items.Ranged.*;
import warhammermod.Items.Render.Model.*;
import warhammermod.Items.Render.RenderRatlingGun;
import warhammermod.Items.Render.RenderRepeater;
import warhammermod.Items.Render.RenderShield;
import warhammermod.Items.Render.RenderSling;
import warhammermod.Items.melee.*;
import warhammermod.Items.melee.specials.Ghal_Maraz;
import warhammermod.Items.melee.specials.Great_pick;
import warhammermod.utils.reference;

import java.util.ArrayList;
import java.util.List;

public class ItemsInit {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static Item Beer = new BeerTemplate().setRegistryName("beer");

    public static Item Cartridge = new Cartridge().setRegistryName(location("cartridge"));
    public static Item Shell = new Cartridge().setRegistryName(location("shot_shell"));
    public static Item Grenade= new Cartridge().setRegistryName(location("grenade"));
    public static Item Warpstone= new Cartridge().setRegistryName(location("warpstone"));

    public static Item musket = new GunTemplate(new Item.Properties().durability(420).tab(reference.warhammer), Cartridge,40,1,13).setRegistryName(location("musket"));
    public static Item pistol = new GunTemplate(new Item.Properties().durability(384).tab(reference.warhammer), Cartridge,25,1,8).setRegistryName(location("flintlock_pistol"));
    public static Item repeater_handgun = new GunTemplate(new Item.Properties().durability(500).tab(reference.warhammer).setISTER(()-> { return RenderRepeater::new;}), Cartridge,92,6,13).setRegistryName(location("nuln_repeater_handgun"));
    public static Item thunderer_handgun = new GunTemplate(new Item.Properties().durability(540).tab(reference.warhammer), Cartridge,35,1,16).setRegistryName(location("thunderer_handgun"));

    public static Item blunderbuss = new ShotgunTemplate(new Item.Properties().durability(390).tab(reference.warhammer), Shell,40,1,19).setRegistryName(location("blunderbuss"));
    public static Item GrudgeRaker = new ShotgunTemplate(new Item.Properties().durability(450).tab(reference.warhammer), Shell,40,2,19).setRegistryName(location("grudgeraker"));

    public static Item grenade_launcher = new GrenadeTemplate(new Item.Properties().durability(384).tab(reference.warhammer), Grenade,75,1).setRegistryName(location("grenade_launcher"));

    public static Item DrakeGun = new DrakeGunTemplate(new Item.Properties().durability(210).tab(reference.warhammer),64,40).setRegistryName(location("drakegun"));
    public static Item RatlingGun = new RatlingGun(new Item.Properties().durability(210).tab(reference.warhammer).setISTER(()-> { return RenderRatlingGun::new;}),64,80).setRegistryName(location("ratling_gun"));

    public static Item Warplock_jezzail = new WarpgunTemplate(new Item.Properties().durability(384).tab(reference.warhammer), Warpstone,50,1,14).setRegistryName(location("warplock_jezzail"));

    public static Item Sling = new SlingTemplate(new Item.Properties().durability(180).tab(reference.warhammer).setISTER(()-> { return RenderSling::new;})).setRegistryName("sling");

    public static final Item Great_pick = new Great_pick(new Item.Properties().setNoRepair()).setRegistryName("war_pick");
    public static final Item Ghal_Maraz = new Ghal_Maraz(new Item.Properties().setNoRepair().durability((int)(ItemTier.DIAMOND.getUses()*1.3))).setRegistryName("ghal_maraz");


    public static final Item diamond_warhammer = new HammerTemplate(ItemTier.DIAMOND,new Item.Properties()).setRegistryName(location("diamond_warhammer"));
    public static final Item wooden_warhammer = new HammerTemplate(ItemTier.WOOD,new Item.Properties()).setRegistryName(location("wooden_warhammer"));
    public static final Item stone_warhammer = new HammerTemplate(ItemTier.STONE,new Item.Properties()).setRegistryName(location("stone_warhammer"));
    public static final Item iron_warhammer = new HammerTemplate(ItemTier.IRON,new Item.Properties()).setRegistryName(location("iron_warhammer"));
    public static final Item gold_warhammer = new HammerTemplate(ItemTier.GOLD,new Item.Properties()).setRegistryName(location("gold_warhammer"));

    public static final Item diamond_dagger = new DaggerTemplate(ItemTier.DIAMOND).setRegistryName(location("diamond_dagger"));
    public static final Item wooden_dagger = new DaggerTemplate(ItemTier.WOOD).setRegistryName(location("wooden_dagger"));
    public static final Item stone_dagger = new DaggerTemplate(ItemTier.STONE).setRegistryName(location("stone_dagger"));
    public static final Item iron_dagger = new DaggerTemplate(ItemTier.IRON).setRegistryName(location("iron_dagger"));
    public static final Item gold_dagger = new DaggerTemplate(ItemTier.GOLD).setRegistryName(location("gold_dagger"));

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

    public static Item High_Elf_Shield = new ShieldTemplate((new Item.Properties().setISTER(()-> {
        return () -> {
            return new RenderShield("textures/items/shields/high_elf_shield.png",new highelfshieldmodel());
        };
    }))).setRegistryName(location("high_elf_shield"));
    public static Item DArk_Elf_shield = new ShieldTemplate((new Item.Properties().setISTER(()-> {
        return () -> {
            return new RenderShield("textures/items/shields/dark_elf_shield.png",new DarkElfshieldmodel());
        };
    }))).setRegistryName(location("dark_elf_shield"));

    public static Item Dwarf_shield = new SmallShieldTemplate((new Item.Properties().setISTER(()-> {
        return () -> {
            return new RenderShield("textures/items/shields/dwarf_shield.png",new dwarfshieldmodel());
        };
    }))).setRegistryName(location("dwarf_shield"));
    public static Item Empire_shield = new SmallShieldTemplate((new Item.Properties().setISTER(()-> {
        return () -> {
            return new RenderShield("textures/items/shields/empire_shield.png",new EmpireShieldmodel());
        };
    }))).setRegistryName(location("imperial_shield"));
    public static Item Skaven_shield = new SmallShieldTemplate((new Item.Properties().setISTER(()-> {
        return () -> {
            return new RenderShield("textures/items/shields/skaven_shield.png",new SkavenShieldModel());
        };
    }))).setRegistryName(location("skaven_shield"));



    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, name);
    }
}
