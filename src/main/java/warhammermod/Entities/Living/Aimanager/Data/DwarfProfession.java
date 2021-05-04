package warhammermod.Entities.Living.Aimanager.Data;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import warhammermod.utils.reference;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DwarfProfession {
    public static DwarfProfession Warrior;
    public static DwarfProfession Miner;
    public static DwarfProfession Builder;
    public static DwarfProfession Engineer;
    public static DwarfProfession FARMER;
    public static DwarfProfession Slayer;
    public static DwarfProfession Lord;
    public static final List<DwarfProfession> Profession = new ArrayList<>();



    public DwarfProfession(String Name, int id, PointOfInterestType POI, ImmutableSet<Item> item, ImmutableSet<Block> block, @Nullable SoundEvent sound, Item equipement1, Item equipement2){
        System.out.println("\n \n"+ Name +" "+ id + "\n \n" );
        this.name = Name;
        this.pointOfInterest = POI;
        this.shareitem = item;
        this.farmblock = block;
        this.worksound = sound;
        this.ID=id;
        Nameloc=location(Name);
        Profession.add(this);
        Equipement[0]=equipement1;
        Equipement[1]=equipement2;

    }

    public DwarfProfession(String Name, int ID, PointOfInterestType POI, @Nullable SoundEvent sound, Item equipement){
        this(Name,ID,POI,ImmutableSet.of(),ImmutableSet.of(),sound, equipement, ItemStack.EMPTY.getItem());
    }

    public DwarfProfession(String Name, int ID, PointOfInterestType POI, @Nullable SoundEvent sound, Item equipement1, Item equipement2){
        this(Name,ID,POI,ImmutableSet.of(),ImmutableSet.of(),sound, equipement1,equipement2);
    }


    public String getName() {
        return name;
    }

    public final Item[] Equipement={ItemStack.EMPTY.getItem(), ItemStack.EMPTY.getItem()};



    private static ResourceLocation location(String name)
    {
        return new ResourceLocation(reference.modid, name);
    }

    private final String name;
    private final ResourceLocation Nameloc;

    private final int ID;

    public int getID(){return ID;}
    public ResourceLocation getRegistryName(){
        return Nameloc;
    }

    public PointOfInterestType getPointOfInterest() {
        return pointOfInterest;
    }

    public ImmutableSet<Item> getSpecificItems() {
        return shareitem;
    }

    public ImmutableSet<Block> getRelatedWorldBlocks() {
        return farmblock;
    }

    public Boolean has2items(){return Equipement.length==2;}

    public Item getItemtoslot(int slot){
        return Equipement[slot];
    }

    @Nullable
    public SoundEvent getWorksound() {
        return worksound;
    }

    private final PointOfInterestType pointOfInterest;
    private final ImmutableSet<Item> shareitem;
    private final ImmutableSet<Block> farmblock;
    @Nullable
    private final SoundEvent worksound;


}
