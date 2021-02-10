package warhammermod.Items;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.world.World;

import java.util.List;
import java.util.ListIterator;

public class Lorebook extends Item {
    private final List<String> bookPages = Lists.newArrayList();
    private String bookTitle = "testing";
    private int currPage;

    public Lorebook(Properties builder) {
        super(builder);
        bookPages.add("hello_world");
        //ItemsInit.ITEMS.add(this);
    }

    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
            if(entityIn instanceof PlayerEntity){
                PlayerEntity player = (PlayerEntity)entityIn;
                player.replaceItemInInventory(itemSlot,generatebook());

            }
    }

    public ItemStack generatebook() {

        ListNBT listnbt = new ListNBT();
        WrittenBookItem lorebook= (WrittenBookItem) Items.WRITTEN_BOOK;
        ItemStack book = new ItemStack(lorebook);
        this.bookPages.stream().map(StringNBT::valueOf).forEach(listnbt::add);
        if (!this.bookPages.isEmpty()) {
            book.setTagInfo("pages", listnbt);
        }
        book.setTagInfo("author", StringNBT.valueOf("warhammer wiki"));
        book.setTagInfo("title", StringNBT.valueOf(this.bookTitle.trim()));
        this.trimEmptyPages();
        return book;
    }


    private void trimEmptyPages() {
        ListIterator<String> listiterator = this.bookPages.listIterator(this.bookPages.size());
        while(listiterator.hasPrevious() && listiterator.previous().isEmpty()) {
            listiterator.remove();
        }

    }

}
