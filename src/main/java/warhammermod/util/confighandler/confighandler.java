package warhammermod.util.confighandler;

import net.minecraftforge.common.config.Config;
import warhammermod.util.reference;

public class confighandler {

    @Config(modid = reference.modid, type = Config.Type.INSTANCE, name = "main warhammer config")

    public static class Config_enable {
        @Config.RequiresMcRestart
        public static boolean generation_enabled = true;
        @Config.RequiresMcRestart
        public static boolean spears_included = true;
        @Config.RequiresMcRestart
        public static boolean hammers_included = true;
        @Config.RequiresMcRestart
        public static boolean halberds_included =true;
        @Config.RequiresMcRestart
        public static boolean knife_included =true;
        @Config.RequiresMcRestart
        public static boolean musket_included =true;
        @Config.RequiresMcRestart
        public static boolean pistol_included =true;
        @Config.RequiresMcRestart
        public static boolean repeater_handgun_inlcuded =true;

        public static boolean repeater_3D_model = true;
        @Config.RequiresMcRestart
        public static boolean grenadelauncher_included = true;
        @Config.RequiresMcRestart
        public static boolean shields_included = true;
        @Config.RequiresMcRestart
        public static boolean gunsword_included = true;
        @Config.RequiresMcRestart
        @Config.Comment("put on true to add compatibility with with headshot mods \n on right click attack of the halberd")
        public static boolean headshotmod_compatibility = false;

        @Config.RequiresMcRestart
        @Config.Comment("if the entity spawns naturally or not")
        public static boolean Pegasus_spawn= true;

        @Config.RequiresMcRestart
        @Config.Comment("if the entity spawns naturally or not")
        public static boolean Pirate_Skeleton_spawn = true;

        @Config.Comment("the amount of smoke produced by the guns: 0 no smoke, 100 a lot")
        @Config.RangeInt(min=0,max=100)
        public static int smokeamount=60;

    }
    @Config(modid = reference.modid, type = Config.Type.INSTANCE, name = "warhammer items values")
    public static class Config_values {
        @Config.RequiresMcRestart
        @Config.RangeDouble(min = 0, max = 20)
        @Config.Comment("for reference the sword has a damage of 3")
        public static double spear_damage = 2;
        @Config.RequiresMcRestart
        @Config.RangeDouble(min = 0, max = 20)
        public static double warhammer_damage = 8;
        @Config.RequiresMcRestart
        @Config.RangeDouble(min = 0, max = 20)
        public static double knife_damage = 0.5;
        @Config.RequiresMcRestart
        @Config.RangeDouble(min = 0, max = 20)
        public static double halberd_damage = 5.4;
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 0, max = 40)
        public static int pistol_damage = 8;
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 0, max = 50)
        @Config.Comment("affects musket and nuln repeater handgun")
        public static int rifle_damage = 13;
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 0, max = 50)
        public static int gunsword_damage = 8;

        @Config.RequiresMcRestart
        @Config.RangeInt(min = 0, max = 50)
        public static int blunderbusses_damage = 21;

        @Config.RequiresMcRestart
        @Config.RangeDouble(min = -5, max = 20)
        @Config.Comment("for reference the sword has a speed of 2.4 (lower is faster)")
        public static double spear_speed = 2;
        @Config.RequiresMcRestart
        @Config.RangeDouble(min = -5, max = 20)
        public static double warhammer_speed = 2.9;
        @Config.RequiresMcRestart
        @Config.RangeDouble(min = -5, max = 20)
        public static double knife_speed = -5;
        @Config.RequiresMcRestart
        @Config.RangeDouble(min = -5, max = 20)
        public static double halberd_speed = 2.8;
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 0, max = 1000)
        public static int pistol_speed = 25;
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 0, max = 1000)
        @Config.Comment("affects musket and repeater hangun")
        public static int rifle_reload_speed = 40;
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 0, max = 1000)
        public static int gunsword_reload_speed = 25;
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 0, max = 1000)
        public static int grenadelauncher_reload_speed = 40;
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 0, max = 1000)
        public static int drakegun_reload_speed = 40;

        @Config.RequiresMcRestart
        @Config.RangeInt(min = 0, max = 100)
        public static int blunderbusses_speed = 40;

        @Config.RequiresMcRestart
        @Config.RangeInt(min = 1, max = 100000)
        @Config.Comment("base shield durability is 336")
        public static int shields_durability = 1008;

        @Config.RequiresMcRestart
        @Config.RangeInt(min = 0, max = 2000)
        public static int drakegun_ammo = 45;

        @Config.RequiresMcRestart
        @Config.RangeDouble(min = 0, max = 100)
        public static double Ghal_maraz_speed = 2.35;

        @Config.RequiresMcRestart
        @Config.RangeDouble(min = 0, max = 100)
        public static double Ghal_maraz_damage = 9;
    }
    @Config(modid = reference.modid, type = Config.Type.INSTANCE, name = "warhammer entities values")
    public static class Change_Durations{
        @Config.RangeInt(min=1,max=375)
        @Config.Comment("cooldown for the dwarf between having a baby and be able to mate again \n in seconds (default: 375) \n same feature as in my other mod: configurable breeding and grow up speed")
        public static int breeding_cooldown=375;
        @Config.RangeInt(min=1,max=1250)
        @Config.Comment("time for a baby to grow up \n in seconds (default: 1200)")
        public static int babydwarf_to_grow_up=1250;

        public static int getBreeding_cooldown() {
            return breeding_cooldown*20;
        }
        public static int getBabyvillager_to_grow_up() {
            return -babydwarf_to_grow_up*20;
        }

    }
    @Config(modid = reference.modid,type = Config.Type.INSTANCE,name = "warhammer mod wiki")
    public static class wiki{
        @Config.Comment("all recipes in this wiki:from left to right and top to bottom [,] is for next row and 0 means nothing in this space \n ex:stairs: planks 0 0, planks x2 0, planks x3")
        public static String crafting="";
        @Config.Comment("some information on weapons + recipes")
        public static String g1gunpowder_weapons="";
        @Config.Comment("used for musket,pistol,handgun,gunsword \n Iron nugget,gunpowder,paper \n can be sold by dwarf engineer")
        public static String g2cartridge="";
        @Config.Comment("for grenade launcher \n 0 iron ingot 0, iron ingot tnt iron ingot, 0 iron ingot 0 \n can be sold by dwarf engineer")
        public static String g3Grenade="";
        @Config.Comment("for blunderbuss and grudge_raker \n without order iron nugget x5 paper gunpowder \n can be sold by dwarf engineer")
        public static String g4shotgun_shell="";
        @Config.Comment("more powerful than a bow; crafting:\n flint&steel 0 0,iron ingot x3,Log planks planks")
        public static String g5musket="";
        @Config.Comment("like a musket times 6; crafting:\n musket x3,musket lead musket,0 musket 0")
        public static String g6repeater_handgun="";
        @Config.Comment("less damage but reloads faster than a musket; crafting:\n flint&steel 0 0, iron ingotx2 0,log plank 0")
        public static String g7pistol="";
        @Config.Comment("launch grenades, power makes it shoot further and punch the explosion bigger \n 0 iron ingot x2,musket 0 0,0 iron ingot x2")
        public static String g8grenade_launcher="";
        @Config.Comment("same as sword and pistol but together \n without order : sword(iron/diamond) slime ball pistol")
        public static String g9Gunsword="";
        @Config.Comment("a lot of damage close but loses damage fast at a distance(infinity can be applied but works only partially) \n flint&steel iron ingot x2 ,iron ingot x3,log planks planks")
        public static String g91blunderbuss="";

        public static String m1melee_weapons="";
        @Config.Comment("can be thrown faster but less damage than a sword \n 0 0 material(wood to diamond), 0 stick 0, stick 0 0")
        public static String m2spear="";
        @Config.Comment("slower but much more damage than a sword can one hit crit with diamond sharpness 5 \n  material(wood to diamond)x3, 0 stick material, 0 stick 0")
        public static String m3warhammer="";
        @Config.Comment("much faster but much less damage than a sword \n  material(wood to diamond)x3,material 0 0,stick 0 0")
        public static String m4knife="";
        @Config.Comment("more damage but slower than a sword has a right click charge attack with a long range \n   0 material(wood to diamond)x2, 0 stick material, stick 0 0")
        public static String m5halberd="";
        @Config.Comment("more durable than a normal shield, can be obtained through dwarf engineer \n dwarf : iron ingot x3, cyan shield gold ingot, 0 iron ingot 0\n" +
                "empire: Iron ingot x3,bonemeal shield red,0 iron ingot 0\n"+
                "dark elf: Gold ingot x3,purple shield purple, 0 gold ingot 0\n"+
                "high elf:Gold ingot x3,lightblue shield bonemeal, 0 goldingot 0")
        public static String m6shields="";

        @Config.Comment("all equipment obtainable through dwarf trading")
        public static String z1dwarf_equipment="";
        @Config.Comment("more damage,durability and reloads faster than a musket \n engineer")
        public static String z2thunderer_handgun="";
        @Config.Comment("2 shot blunderbuss with fast reload \n engineer")
        public static String z3grudgeraker="";
        @Config.Comment("like a pickaxe but can take sword enchantements and do sweeping attacks\nminer")
        public static String z4war_pick="";
        @Config.Comment("flamethrower, uses blaze rods\nengineer")
        public static String z5Drakegun="";
        @Config.Comment("sold by the engineer")
        public static String z6diamond_chainmail_armor="";
        @Config.Comment("sold by the farmer, but they also throw it at each other instead of bread")
        public static String z7beer="";
        @Config.Comment("a legendary warhammer gives effects in melee\nobtained through the lord after a few trades")
        public static String z8Ghal_Maraz="";
    }
}
