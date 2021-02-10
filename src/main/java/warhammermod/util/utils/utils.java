package warhammermod.util.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.reference;

import java.util.Random;

public class utils {

    public static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, name);
    }



    public static boolean checkcorrectbiome(Biome biome){
        for(Biome x : reference.Biome_list){
            if(biome==x)return true;
        }
        return false;
    }
    public static Item getRandomShield(){
        int shield = new Random().nextInt(4);
        switch(shield){
            case 0:return ItemsInit.Dwarf_shield;
            case 1:return ItemsInit.Empire_shield;
            case 2:return ItemsInit.High_Elf_Shield;
            default:return ItemsInit.DArk_Elf_shield;
        }
    }
    public static Item getRandomarmor(int part){
        int random = new Random().nextInt(2);
        if(random==0){if(part<1)return ItemsInit.DIAMOND_CHAINMAIL_LEGGINGS;else return ItemsInit.DIAMOND_CHAINMAIL_BOOTS;}
        else{if(part<1)return ItemsInit.DIAMOND_CHAINMAIL_HELMET;else return ItemsInit.DIAMOND_CHAINMAIL_CHESTPLATE;}
    }
    public static Item[] skulls = {Items.SKELETON_SKULL, Items.CREEPER_HEAD, Items.ZOMBIE_HEAD, Items.PLAYER_HEAD, Items.DRAGON_HEAD};

    public static Item getrandomskull(){
        int random = new Random().nextInt(skulls.length);
        return skulls[random];
    }
    private static Item[] weapons = {ItemsInit.musket,ItemsInit.musket,ItemsInit.pistol,ItemsInit.iron_gunsword};
    public static ItemStack getRandomPirateWeapon(){
        int random = new Random().nextInt(4);
        return new ItemStack(weapons[random]);
    }
    private static Item[] spears = {ItemsInit.wooden_spear,ItemsInit.wooden_spear,ItemsInit.stone_spear,ItemsInit.iron_spear,ItemsInit.diamond_spear};
    public static ItemStack getRandomspear(int maxlevel5){
        int random = new Random().nextInt(maxlevel5);
        return new ItemStack(spears[random]);
    }
    private static Item[] sword = {Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.IRON_SWORD, Items.DIAMOND_SWORD};
    public static ItemStack getRandomsword(int maxlevel5){
        int random = new Random().nextInt(maxlevel5);
        return new ItemStack(sword[random]);
    }
    private static Item[] halberd =  {ItemsInit.stone_halberd,ItemsInit.iron_halberd,ItemsInit.iron_halberd,ItemsInit.diamond_halberd};
    public static ItemStack getRandomHalberd(int maxlevel4){
        int random = new Random().nextInt(maxlevel4);
        return new ItemStack(halberd[random]);
    }


/*
    @OnlyIn(Dist.CLIENT)
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

*/
    private static int count;
    public static void printer(Object a){
        if(count>20){
            count=0;
            System.out.println(a);
        }
        else count++;
    }

    private static String print_old;
    public static void nonrepeatingprinter(Object a){
        String b = a.toString();
        if(!a.equals(print_old)){
            System.out.println(b);
            print_old=b;
        }
    }

}
