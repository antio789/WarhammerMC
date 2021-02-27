package warhammermod.util;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Entities.living.EntityDwarf;
import warhammermod.util.Handler.inithandler.Itemsinit;

import java.util.ArrayList;
import java.util.Random;

public class utils {


    public static void addbiome(Biome[] list){
        ArrayList<Biome> TEMP= new ArrayList<>();
        for (Biome x : MapGenVillage.VILLAGE_SPAWN_BIOMES){
            TEMP.add(x);
        }
        for(Biome x : list){
            TEMP.add(x);
        }
        MapGenVillage.VILLAGE_SPAWN_BIOMES=TEMP;
    }
    public static boolean checkcorrectbiome(Biome biome){
        for(Biome x : reference.Biome_list){
            if(biome==x)return true;
        }
        return false;
    }
    public static ItemShield getRandomShield(){
        int shield = new Random().nextInt(4);
        switch(shield){
            case 0:return Itemsinit.shield;
            case 1:return Itemsinit.shield2;
            case 2:return Itemsinit.shield3;
            default:return Itemsinit.shield4;
        }
    }
    public static ItemArmor getRandomarmor(int part){
        int random = new Random().nextInt(2);
        if(random==0){if(part<1)return Itemsinit.Diamond_Chainmail_legging;else return Itemsinit.Diamond_Chainmail_boots;}
        else{if(part<1)return Itemsinit.Diamond_Chainmail_helmet;else return Itemsinit.Diamond_Chainmail_plate;}
    }
    public static int getrandomskull(){
        int random = new  Random().nextInt(5);
        if(random==1){return getrandomskull();}
        else {return random;}
    }
    private static Item[] weapons = {Itemsinit.musket,Itemsinit.musket,Itemsinit.flintlock_pistol,Itemsinit.iron_gunsword};
    public static ItemStack getRandomPirateWeapon(){
        int random = new  Random().nextInt(4);
        return new ItemStack(weapons[random]);
    }
    private static Item[] spears = {Itemsinit.wooden_spear,Itemsinit.wooden_spear,Itemsinit.stone_spear,Itemsinit.iron_spear,Itemsinit.diamond_spear};
    public static ItemStack getRandomspear(int maxlevel5){
        int random = new  Random().nextInt(maxlevel5);
        return new ItemStack(spears[random]);
    }
    private static Item[] sword = {Items.WOODEN_SWORD,Items.STONE_SWORD,Items.IRON_SWORD,Items.IRON_SWORD,Items.DIAMOND_SWORD};
    public static ItemStack getRandomsword(int maxlevel5){
        int random = new  Random().nextInt(maxlevel5);
        return new ItemStack(sword[random]);
    }
    private static Item[] halberd =  {Itemsinit.stone_halberd,Itemsinit.iron_halberd,Itemsinit.iron_halberd,Itemsinit.diamond_halberd};
    public static ItemStack getRandomHalberd(int maxlevel4){
        int random = new  Random().nextInt(maxlevel4);
        return new ItemStack(halberd[random]);
    }



    @SideOnly(Side.CLIENT)
    public static class dwarftexturechooser {
        private static final ResourceLocation DWARF_VILLAGER_TEXTURES = new ResourceLocation(reference.modid, "textures/entity/dwarf_farmer.png");
        private static final ResourceLocation DWARF_VILLAGER_TEXTURES1 = new ResourceLocation(reference.modid, "textures/entity/dwarf.png");
        private static final ResourceLocation DWARF_VILLAGER_TEXTURES2 = new ResourceLocation(reference.modid, "textures/entity/dwarf_engineer.png");
        private static final ResourceLocation DWARF_VILLAGER_TEXTURES3 = new ResourceLocation(reference.modid, "textures/entity/dwarf_armourer.png");
        private static final ResourceLocation DWARF_VILLAGER_TEXTURES4 = new ResourceLocation(reference.modid, "textures/entity/dwarf_lord.png");
        private static final ResourceLocation DWARF_VILLAGER_TEXTURES5 = new ResourceLocation(reference.modid, "textures/entity/dwarf_slayer.png");

        public static ResourceLocation getdwarftexture(EntityDwarf entity) {
            int prof = entity.getProfession();
            switch (prof) {
                case 0:
                    return DWARF_VILLAGER_TEXTURES;
                case 1:
                    return DWARF_VILLAGER_TEXTURES1;
                case 2:
                    return DWARF_VILLAGER_TEXTURES2;
                case 3:
                    return DWARF_VILLAGER_TEXTURES3;
                case 4:
                    return DWARF_VILLAGER_TEXTURES4;
                default:
                    return DWARF_VILLAGER_TEXTURES5;

            }
        }
    }


    private static int count;
    public static void printer(Object a){
        if(count>20){
            count=0;
            System.out.println(a);
        }
        else count++;
    }

}
