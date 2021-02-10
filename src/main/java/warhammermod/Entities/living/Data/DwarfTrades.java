package warhammermod.Entities.living.Data;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import warhammermod.util.Handler.ItemsInit;
import warhammermod.util.utils.utils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class DwarfTrades {
   public static final Map<DwarfProfession, Int2ObjectMap<ITrade[]>> TRADES = Util.make(Maps.newHashMap(), (p_221237_0_) -> {
       p_221237_0_.put(DwarfProfession.Farmer, func_221238_a(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Items.WHEAT, 20, 16, 2), new EmeraldForItemsTrade(Items.POTATO, 26, 16, 2), new EmeraldForItemsTrade(Items.CARROT, 22, 16, 2), new ItemsForEmeraldsTrade(ItemsInit.Beer, 8, 1, 2)}, 2, new ITrade[]{new EmeraldForItemsTrade(Blocks.PUMPKIN, 6, 12, 10), new ItemsForEmeraldsTrade(Items.PUMPKIN_PIE, 1, 4, 5), new ItemsForEmeraldsTrade(Items.APPLE, 1, 4, 16, 5)}, 3, new ITrade[]{new ItemsForEmeraldsTrade(Items.COOKIE, 3, 18, 10), new EmeraldForItemsTrade(Blocks.MELON, 4, 12, 20)}, 4, new ITrade[]{new ItemsForEmeraldsTrade(Blocks.CAKE, 1, 1, 12, 15), new SuspiciousStewForEmeraldTrade(Effects.NIGHT_VISION, 100, 15), new SuspiciousStewForEmeraldTrade(Effects.JUMP_BOOST, 160, 15), new SuspiciousStewForEmeraldTrade(Effects.WEAKNESS, 140, 15), new SuspiciousStewForEmeraldTrade(Effects.BLINDNESS, 120, 15), new SuspiciousStewForEmeraldTrade(Effects.POISON, 280, 15), new SuspiciousStewForEmeraldTrade(Effects.SATURATION, 7, 15)}, 5, new ITrade[]{new ItemsForEmeraldsTrade(Items.GOLDEN_CARROT, 3, 3, 30), new ItemsForEmeraldsTrade(Items.GLISTERING_MELON_SLICE, 4, 3, 30)})));
       p_221237_0_.put(DwarfProfession.Miner,func_221238_a(ImmutableMap.of(1,new ITrade[]{new EmeraldForItemsTrade(Items.COAL,15,16,2),new EmeraldForItemsTrade(Items.REDSTONE,32,14,1),new EmeraldForItemsTrade(Items.GOLD_INGOT,3,12,5),new EmeraldForItemsTrade(Items.OBSIDIAN,3,12,15),new EmeraldForItemsTrade(Items.IRON_INGOT,8,10,3)},2,new ITrade[]{new ItemsForEmeraldsTrade(Items.MINECART,3,1,15),new ItemsForEmeraldsTrade(Items.LANTERN,1,2,10),new EmeraldForItemsTrade(ItemsInit.Beer,3,30,8)},3,new ITrade[]{new EmeraldForItemsTrade(Items.TNT,8,10,30),new ItemsForEmeraldsTrade(Items.QUARTZ,35,4,5,30),new ItemsForEmeraldsTrade(Items.RAIL,1,2,5)},4,new ITrade[]{new ItemsForEmeraldsTrade(Items.POWERED_RAIL,3,18,20)},5, new ITrade[]{new ItemsForEmeraldsTrade(Items.QUARTZ, 20, 1, 30),new EnchantedItemForEmeraldsTrade(ItemsInit.Great_pick,25,1,20)})));
      p_221237_0_.put(DwarfProfession.Builder, func_221238_a(ImmutableMap.of(1, new ITrade[]{new ItemsForEmeraldsTrade(Items.CLAY_BALL, 10, 1, 2), new ItemsForEmeraldsTrade(Items.BRICK, 20, 1, 16, 10),new ItemsForEmeraldsTrade(Items.SANDSTONE,8,2,20,5),new ItemsForEmeraldsTrade(Items.RED_SANDSTONE,12,2,20,5)}, 2, new ITrade[]{new EmeraldForItemsTrade(Blocks.STONE, 20, 16, 10), new ItemsForEmeraldsTrade(Blocks.CHISELED_STONE_BRICKS, 1, 4, 16, 5)}, 3, new ITrade[]{new EmeraldForItemsTrade(Blocks.GRANITE, 16, 16, 20), new EmeraldForItemsTrade(Blocks.ANDESITE, 16, 16, 20), new EmeraldForItemsTrade(Blocks.DIORITE, 16, 16, 20), new ItemsForEmeraldsTrade(Blocks.POLISHED_ANDESITE, 1, 4, 16, 10), new ItemsForEmeraldsTrade(Blocks.POLISHED_DIORITE, 1, 4, 16, 10), new ItemsForEmeraldsTrade(Blocks.POLISHED_GRANITE, 1, 4, 16, 10)}, 4, new ITrade[]{new ItemsForEmeraldsTrade(Items.BLACK_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.BLUE_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.CYAN_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.BROWN_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.GRAY_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.GREEN_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.LIGHT_BLUE_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.LIGHT_GRAY_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.LIME_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.MAGENTA_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.ORANGE_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.PINK_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.RED_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.WHITE_CONCRETE,2,1,12,15),new ItemsForEmeraldsTrade(Items.YELLOW_CONCRETE,2,1,12,15)}, 5, new ITrade[]{new ItemsForEmeraldsTrade(Blocks.QUARTZ_PILLAR, 1, 2, 20, 30), new ItemsForEmeraldsTrade(Blocks.QUARTZ_BLOCK, 1, 2, 20, 30)})));
      p_221237_0_.put(DwarfProfession.Slayer,func_221238_a(ImmutableMap.of(1,new ITrade[]{new EmeraldForItemsTrade(Items.COOKED_BEEF,12,15,5),new EmeraldForItemsTrade(Items.COOKED_CHICKEN,12,15,5),new EmeraldForItemsTrade(Items.COOKED_COD,12,15,5),new EmeraldForItemsTrade(Items.COOKED_MUTTON,12,15,5),new EmeraldForItemsTrade(Items.COOKED_PORKCHOP,12,15,5),new EmeraldForItemsTrade(Items.COOKED_RABBIT,12,15,5),new EmeraldForItemsTrade(Items.COOKED_SALMON,12,15,5)},2,new ITrade[]{new EmeraldForItemsTrade(Items.RABBIT_STEW,12,15,10),new EmeraldForItemsTrade(ItemsInit.Beer,3,30,15)},3,new ITrade[]{new ItemsForEmeraldsTrade(Items.BONE,20,1,30,10)},4,new ITrade[]{new EmeraldForItemsTrade(Items.STRING,18,1,20),new EmeraldForItemsTrade(Items.PHANTOM_MEMBRANE,40,3,30)},5,new ITrade[]{new ItemsForEmeraldsTrade(utils.getrandomskull(),60,1,1,40)})));
      p_221237_0_.put(DwarfProfession.Slayer,func_221238_a(ImmutableMap.of(1,new ITrade[]{new EmeraldForItemsTrade(Items.COOKED_BEEF,12,15,5),new EmeraldForItemsTrade(Items.COOKED_CHICKEN,12,15,5),new EmeraldForItemsTrade(Items.COOKED_COD,12,15,5),new EmeraldForItemsTrade(Items.COOKED_MUTTON,12,15,5),new EmeraldForItemsTrade(Items.COOKED_PORKCHOP,12,15,5),new EmeraldForItemsTrade(Items.COOKED_RABBIT,12,15,5),new EmeraldForItemsTrade(Items.COOKED_SALMON,12,15,5)},2,new ITrade[]{new EmeraldForItemsTrade(Items.RABBIT_STEW,12,15,10),new EmeraldForItemsTrade(ItemsInit.Beer,3,30,15)},3,new ITrade[]{new ItemsForEmeraldsTrade(Items.BONE,20,1,30,10)},4,new ITrade[]{new EmeraldForItemsTrade(Items.STRING,18,1,20),new EmeraldForItemsTrade(Items.PHANTOM_MEMBRANE,40,3,30)},5,new ITrade[]{new ItemsForEmeraldsTrade(utils.getrandomskull(),60,1,1,40)})));
      //p_221237_0_.put(DwarfProfession.Lord,func_221238_a(ImmutableMap.of(1,new ITrade[]{new EmeraldForItemsTrade(ItemsInit.Beer,5,10,10)},2,new ITrade[]{new EmeraldForItemsTrade(Items.field_226638_pX_,8,10,15)},3,new ITrade[]{new EmeraldForItemsTrade(Items.BLAZE_ROD,5,5,40)},4,new ITrade[]{new EmeraldForItemsTrade(Items.WITHER_ROSE,5,3,60)},5,new ITrade[]{new EnchantedItemForEmeraldsTrade(ItemsInit.Ghal_Maraz,20,1,50)})));
      p_221237_0_.put(DwarfProfession.Engineer, func_221238_a(ImmutableMap.of(1,new ITrade[]{new ItemsForEmeraldsTrade(ItemsInit.Cartridge,5,1,5),new ItemsForEmeraldsTrade(ItemsInit.Grenade,9,1,5),new ItemsForEmeraldsTrade(ItemsInit.Shell,6,1,5)},2,new ITrade[]{new EmeraldForItemsTrade(ItemsInit.Beer,2,30,8),new ItemsForEmeraldsTrade(utils.getRandomShield(),20,1,20),new ItemsForEmeraldsTrade(Items.PISTON,8,1,30,10),new ItemsForEmeraldsTrade(Items.DISPENSER,16,1,38,5)},3,new ITrade[]{new EnchantedItemForEmeraldsTrade(ItemsInit.thunderer_handgun,15,1,30),new ItemsForEmeraldsTrade(utils.getRandomShield(),15,1,1,15),new EnchantedItemForEmeraldsTrade(utils.getRandomarmor(0),15,5,20),new EnchantedItemForEmeraldsTrade(utils.getRandomarmor(1),15,5,20)},4,new ITrade[]{new EnchantedItemForEmeraldsTrade(ItemsInit.GrudgeRaker,16,1,20)},5,new ITrade[]{new EnchantedItemForEmeraldsTrade(ItemsInit.DrakeGun,20,1,30)})));
   });

   private static Int2ObjectMap<ITrade[]> func_221238_a(ImmutableMap<Integer, ITrade[]> p_221238_0_) {
      return new Int2ObjectOpenHashMap<>(p_221238_0_);
   }

   static class DyedArmorForEmeraldsTrade implements ITrade {
      private final Item field_221233_a;
      private final int field_221234_b;
      private final int field_221235_c;
      private final int field_221236_d;

      public DyedArmorForEmeraldsTrade(Item p_i50540_1_, int p_i50540_2_) {
         this(p_i50540_1_, p_i50540_2_, 12, 1);
      }

      public DyedArmorForEmeraldsTrade(Item p_i50541_1_, int p_i50541_2_, int p_i50541_3_, int p_i50541_4_) {
         this.field_221233_a = p_i50541_1_;
         this.field_221234_b = p_i50541_2_;
         this.field_221235_c = p_i50541_3_;
         this.field_221236_d = p_i50541_4_;
      }

      public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
         ItemStack itemstack = new ItemStack(Items.EMERALD, this.field_221234_b);
         ItemStack itemstack1 = new ItemStack(this.field_221233_a);
         if (this.field_221233_a instanceof DyeableArmorItem) {
            List<DyeItem> list = Lists.newArrayList();
            list.add(func_221232_a(p_221182_2_));
            if (p_221182_2_.nextFloat() > 0.7F) {
               list.add(func_221232_a(p_221182_2_));
            }

            if (p_221182_2_.nextFloat() > 0.8F) {
               list.add(func_221232_a(p_221182_2_));
            }

            itemstack1 = IDyeableArmorItem.dyeItem(itemstack1, list);
         }

         return new MerchantOffer(itemstack, itemstack1, this.field_221235_c, this.field_221236_d, 0.2F);
      }

      private static DyeItem func_221232_a(Random p_221232_0_) {
         return DyeItem.getItem(DyeColor.byId(p_221232_0_.nextInt(16)));
      }
   }

   static class EmeraldForItemsTrade implements ITrade {
      private final Item field_221183_a;
      private final int field_221184_b;
      private final int field_221185_c;
      private final int field_221186_d;
      private final float field_221187_e;

      public EmeraldForItemsTrade(IItemProvider p_i50539_1_, int price, int maxuse, int xp) {
         this.field_221183_a = p_i50539_1_.asItem();
         this.field_221184_b = price;
         this.field_221185_c = maxuse;
         this.field_221186_d = xp;
         this.field_221187_e = 0.05F;
      }

      public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
         ItemStack itemstack = new ItemStack(this.field_221183_a, this.field_221184_b);
         return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.field_221185_c, this.field_221186_d, this.field_221187_e);
      }
   }

   static class EmeraldForMapTrade implements VillagerTrades.ITrade {
      private final int count;
      private final Structure<?> structureName;
      private final MapDecoration.Type mapDecorationType;
      private final int maxUses;
      private final int xpValue;

      public EmeraldForMapTrade(int p_i231575_1_, Structure<?> p_i231575_2_, MapDecoration.Type p_i231575_3_, int p_i231575_4_, int p_i231575_5_) {
         this.count = p_i231575_1_;
         this.structureName = p_i231575_2_;
         this.mapDecorationType = p_i231575_3_;
         this.maxUses = p_i231575_4_;
         this.xpValue = p_i231575_5_;
      }

      @Nullable
      public MerchantOffer getOffer(Entity trader, Random rand) {
         if (!(trader.world instanceof ServerWorld)) {
            return null;
         } else {
            ServerWorld serverworld = (ServerWorld)trader.world;
            BlockPos blockpos = serverworld.func_241117_a_(this.structureName, trader.func_233580_cy_(), 100, true);
            if (blockpos != null) {
               ItemStack itemstack = FilledMapItem.setupNewMap(serverworld, blockpos.getX(), blockpos.getZ(), (byte)2, true, true);
               FilledMapItem.func_226642_a_(serverworld, itemstack);
               MapData.addTargetDecoration(itemstack, blockpos, "+", this.mapDecorationType);
               itemstack.setDisplayName(new TranslationTextComponent("filled_map." + this.structureName.getStructureName().toLowerCase(Locale.ROOT)));
               return new MerchantOffer(new ItemStack(Items.EMERALD, this.count), new ItemStack(Items.COMPASS), itemstack, this.maxUses, this.xpValue, 0.2F);
            } else {
               return null;
            }
         }
      }
   }


   static class EnchantedBookForEmeraldsTrade implements VillagerTrades.ITrade {
      private final int xpValue;

      public EnchantedBookForEmeraldsTrade(int xpValueIn) {
         this.xpValue = xpValueIn;
      }

      public MerchantOffer getOffer(Entity trader, Random rand) {
         List<Enchantment> list = Registry.ENCHANTMENT.stream().filter(Enchantment::func_230309_h_).collect(Collectors.toList());
         Enchantment enchantment = list.get(rand.nextInt(list.size()));
         int i = MathHelper.nextInt(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
         ItemStack itemstack = EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(enchantment, i));
         int j = 2 + rand.nextInt(5 + i * 10) + 3 * i;
         if (enchantment.isTreasureEnchantment()) {
            j *= 2;
         }

         if (j > 64) {
            j = 64;
         }

         return new MerchantOffer(new ItemStack(Items.EMERALD, j), new ItemStack(Items.BOOK), itemstack, 12, this.xpValue, 0.2F);
      }
   }

   static class EnchantedItemForEmeraldsTrade implements ITrade {
      private final ItemStack field_221195_a;
      private final int field_221196_b;
      private final int field_221197_c;
      private final int field_221198_d;
      private final float field_221199_e;

      public EnchantedItemForEmeraldsTrade(Item item, int price, int maxuse, int xp) {
         this(item, price, maxuse, xp, 0.05F);
      }

      public EnchantedItemForEmeraldsTrade(Item item, int price, int maxuse, int xp, float pricemultiplier) {
         this.field_221195_a = new ItemStack(item);
         this.field_221196_b = price;
         this.field_221197_c = maxuse;
         this.field_221198_d = xp;
         this.field_221199_e = pricemultiplier;
      }

      public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
         int i = 5 + p_221182_2_.nextInt(15);
         ItemStack itemstack = EnchantmentHelper.addRandomEnchantment(p_221182_2_, new ItemStack(this.field_221195_a.getItem()), i, false);
         int j = Math.min(this.field_221196_b + i, 64);
         ItemStack itemstack1 = new ItemStack(Items.EMERALD, j);
         return new MerchantOffer(itemstack1, itemstack, this.field_221197_c, this.field_221198_d, this.field_221199_e);
      }
   }

   public interface ITrade {
      @Nullable
      MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_);
   }

   static class ItemWithPotionForEmeraldsAndItemsTrade implements ITrade {
      private final ItemStack field_221219_a;
      private final int field_221220_b;
      private final int field_221221_c;
      private final int field_221222_d;
      private final int field_221223_e;
      private final Item field_221224_f;
      private final int field_221225_g;
      private final float field_221226_h;

      public ItemWithPotionForEmeraldsAndItemsTrade(Item p_i50526_1_, int p_i50526_2_, Item p_i50526_3_, int p_i50526_4_, int p_i50526_5_, int p_i50526_6_, int p_i50526_7_) {
         this.field_221219_a = new ItemStack(p_i50526_3_);
         this.field_221221_c = p_i50526_5_;
         this.field_221222_d = p_i50526_6_;
         this.field_221223_e = p_i50526_7_;
         this.field_221224_f = p_i50526_1_;
         this.field_221225_g = p_i50526_2_;
         this.field_221220_b = p_i50526_4_;
         this.field_221226_h = 0.05F;
      }

      public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
         ItemStack itemstack = new ItemStack(Items.EMERALD, this.field_221221_c);
         List<Potion> list = Registry.POTION.stream().filter((p_221218_0_) -> {
            return !p_221218_0_.getEffects().isEmpty() && PotionBrewing.func_222124_a(p_221218_0_);
         }).collect(Collectors.toList());
         Potion potion = list.get(p_221182_2_.nextInt(list.size()));
         ItemStack itemstack1 = PotionUtils.addPotionToItemStack(new ItemStack(this.field_221219_a.getItem(), this.field_221220_b), potion);
         return new MerchantOffer(itemstack, new ItemStack(this.field_221224_f, this.field_221225_g), itemstack1, this.field_221222_d, this.field_221223_e, this.field_221226_h);
      }
   }

   static class ItemsForEmeraldsAndItemsTrade implements ITrade {
      private final ItemStack field_221200_a;
      private final int field_221201_b;
      private final int field_221202_c;
      private final ItemStack field_221203_d;
      private final int field_221204_e;
      private final int field_221205_f;
      private final int field_221206_g;
      private final float field_221207_h;

      public ItemsForEmeraldsAndItemsTrade(IItemProvider p_i50533_1_, int p_i50533_2_, Item p_i50533_3_, int p_i50533_4_, int p_i50533_5_, int p_i50533_6_) {
         this(p_i50533_1_, p_i50533_2_, 1, p_i50533_3_, p_i50533_4_, p_i50533_5_, p_i50533_6_);
      }

      public ItemsForEmeraldsAndItemsTrade(IItemProvider p_i50534_1_, int p_i50534_2_, int p_i50534_3_, Item p_i50534_4_, int p_i50534_5_, int p_i50534_6_, int p_i50534_7_) {
         this.field_221200_a = new ItemStack(p_i50534_1_);
         this.field_221201_b = p_i50534_2_;
         this.field_221202_c = p_i50534_3_;
         this.field_221203_d = new ItemStack(p_i50534_4_);
         this.field_221204_e = p_i50534_5_;
         this.field_221205_f = p_i50534_6_;
         this.field_221206_g = p_i50534_7_;
         this.field_221207_h = 0.05F;
      }

      @Nullable
      public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
         return new MerchantOffer(new ItemStack(Items.EMERALD, this.field_221202_c), new ItemStack(this.field_221200_a.getItem(), this.field_221201_b), new ItemStack(this.field_221203_d.getItem(), this.field_221204_e), this.field_221205_f, this.field_221206_g, this.field_221207_h);
      }
   }

   static class ItemsForEmeraldsTrade implements ITrade {
      private final ItemStack field_221208_a;
      private final int field_221209_b;
      private final int field_221210_c;
      private final int field_221211_d;
      private final int field_221212_e;
      private final float field_221213_f;

      public ItemsForEmeraldsTrade(Block sell, int price, int amount, int maxuse, int xp) {
         this(new ItemStack(sell), price, amount, maxuse, xp);
      }

      public ItemsForEmeraldsTrade(Item sell, int price, int amount, int xp) {
         this(new ItemStack(sell), price, amount, 12, xp);
      }

      public ItemsForEmeraldsTrade(Item sell, int price, int amount, int maxuse, int xp) {
         this(new ItemStack(sell), price, amount, maxuse, xp);
      }

      public ItemsForEmeraldsTrade(ItemStack sell, int price, int amount, int maxuse, int xp) {
         this(sell, price, amount, maxuse, xp, 0.05F);
      }

      public ItemsForEmeraldsTrade(ItemStack sell, int price, int amount, int maxuse, int xp, float pricemultiplier) {
         this.field_221208_a = sell;
         this.field_221209_b = price;
         this.field_221210_c = amount;
         this.field_221211_d = maxuse;
         this.field_221212_e = xp;
         this.field_221213_f = pricemultiplier;
      }

      public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
         return new MerchantOffer(new ItemStack(Items.EMERALD, this.field_221209_b), new ItemStack(this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
      }
   }

   static class SuspiciousStewForEmeraldTrade implements ITrade {
      final Effect field_221214_a;
      final int field_221215_b;
      final int field_221216_c;
      private final float field_221217_d;

      public SuspiciousStewForEmeraldTrade(Effect effect, int duration, int xp) {
         this.field_221214_a = effect;
         this.field_221215_b = duration;
         this.field_221216_c = xp;
         this.field_221217_d = 0.05F;
      }

      @Nullable
      public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
         ItemStack itemstack = new ItemStack(Items.SUSPICIOUS_STEW, 1);
         SuspiciousStewItem.addEffect(itemstack, this.field_221214_a, this.field_221215_b);
         return new MerchantOffer(new ItemStack(Items.EMERALD, 1), itemstack, 12, this.field_221216_c, this.field_221217_d);
      }
   }
}