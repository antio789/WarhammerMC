package warhammermod.Entities.living.Data;

import net.minecraft.item.ItemStack;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.utils.utils;

public class SkavenTypes {
    private static ItemStack[][] slaveequipement = {{utils.getRandomspear(4)},{new ItemStack(ItemsInit.Sling)}};
    public static final SkavenTypes slave = new SkavenTypes("slave", 1F,38,0.08F,28,slaveequipement);


    private SkavenTypes(String name, Float size, Integer spawnChance, Float reinforcementchance, Integer fireRate, ItemStack[][] equipement){
        this.Name = name;
        this.Size=size;
        this.Spawnchance=spawnChance;
        this.Reinforcement=reinforcementchance;
        this.FireRate = fireRate;
        this.Equipement = equipement;
    }

    public String getName() {
        return Name;
    }
    public Float getSize() { return Size; }
    public Integer getSpawnchance() { return Spawnchance; }
    public Float getReinforcement() { return Reinforcement; }
    public Integer getFireRate() { return FireRate; }
    public ItemStack[][] getEquipement() { return Equipement; }

    private final String Name;
    private final Float Size;
    private final Integer Spawnchance;
    private final Float Reinforcement;
    private final Integer FireRate;
    private final ItemStack[][] Equipement;



    public static ItemStack[][][] SkavenEquipment =  {{{utils.getRandomspear(4)},{new ItemStack(ItemsInit.Sling)}},
            {{utils.getRandomsword(4),new ItemStack(ItemsInit.Skaven_shield)},{new ItemStack(ItemsInit.Warplock_jezzail)}},
            {{utils.getRandomHalberd(4)},{utils.getRandomsword(5),new ItemStack(ItemsInit.Skaven_shield)}},
            {{new ItemStack(ItemsInit.iron_knife),new ItemStack(ItemsInit.iron_knife)}},
            {{}},
            {{new ItemStack(ItemsInit.RatlingGun)}},
    };
}
