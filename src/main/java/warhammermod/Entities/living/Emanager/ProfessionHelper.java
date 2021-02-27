package warhammermod.Entities.living.Emanager;


import com.google.common.collect.Lists;
import warhammermod.Entities.living.EntityDwarf;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProfessionHelper {
    private static boolean hasInit = false;
    public static List<DwarfCareer> careers = Lists.newArrayList();
    public static void tradesinit()
    {
        if (hasInit)
        {
            return;
        }

        {

            careers.add((new DwarfCareer(0, "farmer")).init(DwarfTrades.trades[0][0]));

        }

        {

            careers.add((new DwarfCareer(1, "miner")).init(DwarfTrades.trades[1][0]));
        }
        {

            careers.add((new DwarfCareer(2, "engineer")).init(DwarfTrades.trades[2][0]));
        }
        {

            careers.add((new DwarfCareer(3, "armorer")).init(DwarfTrades.trades[3][0]));
        }
        {

            careers.add((new DwarfCareer(4, "lord")).init(DwarfTrades.trades[4][0]));
        }
        {
            careers.add((new DwarfCareer(5, "Slayer")).init(DwarfTrades.trades[5][0]));
        }
    }


    public static class DwarfCareer
    {
        private int profession;
        private String name;
        private List<List<EntityDwarf.ITradeList>> trades = Lists.newArrayList();

        public DwarfCareer(int parent, String name)
        {
            this.profession = parent;
            this.name = name;
        }

        public String getName()
        {
            return this.name;
        }


        public DwarfCareer addTrade(int level, EntityDwarf.ITradeList... trades)
        {
            if (level <= 0)
                throw new IllegalArgumentException("Levels start at 1");

            List<EntityDwarf.ITradeList> levelTrades = level <= this.trades.size() ? this.trades.get(level - 1) : null;
            if (levelTrades == null)
            {
                while (this.trades.size() < level)
                {
                    levelTrades = Lists.newArrayList();
                    this.trades.add(levelTrades);
                }
            }
            if (levelTrades == null) //Not sure how this could happen, but screw it
            {
                levelTrades = Lists.newArrayList();
                this.trades.set(level - 1, levelTrades);
            }
            levelTrades.addAll(Arrays.asList(trades));
            return this;
        }

        @Nullable
        public List<EntityDwarf.ITradeList> getTrades(int level)
        {
            return level >= 0 && level < trades.size() ? Collections.unmodifiableList(trades.get(level)) : null;
        }
        private DwarfCareer init(EntityDwarf.ITradeList[][] trades)
        {
            for (int x = 0; x < trades.length; x++)
                this.trades.add(Lists.newArrayList(trades[x]));

            return this;
        }

    }
    private static class DwarfTrades
    {
        private static final EntityDwarf.ITradeList[][][][] trades = EntityDwarf.GET_TRADES();
    }
}