package dev.mxlx.mxTags.utils;

import dev.mxlx.mxTags.MxTags;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagSelectionGUI {

    MxTags mxTags = MxTags.getInstance();
    TagManager tagManager = mxTags.tagManager();

    public void openTagSelectionGUI(Player player, int page) {

        player.closeInventory();

        Inventory inventory = Bukkit.createInventory(null, 54, "Tag Selector - Page " + page + "/" + tagManager.getEntryAmount(TagManager.listEntryType.GUI_PAGE_AMOUNT));
        ArrayList<TagManager.Tag> tags = (ArrayList<TagManager.Tag>) mxTags.tagManager().listTagsAsGUIpage(page);

        int index = 0;
        for (TagManager.Tag tag : tags) {
            ItemStack item = new ItemStack(Material.NAME_TAG);
            ItemMeta meta = item.getItemMeta();
            String tagText = tag.getTag();
            meta.setDisplayName(tagText);
            List<String> lore = Arrays.asList("", ChatColor.GREEN + "Click to select this tag");
            try {
                if (ChatColor.stripColor(tagManager.getTag(tagManager.getPlayerTagID(player))).equalsIgnoreCase(ChatColor.stripColor(tagText))) {
                    lore.set(1, ChatColor.AQUA + "You have selected this tag");
                } } catch (NullPointerException exception) {}
            meta.setLore(lore);
            item.setItemMeta(meta);

            inventory.setItem(index, item);
            index++;
        }

        if (tagManager.nextGUIpageExists(page)) {
            ItemStack nextPage = new ItemStack(Material.PAPER);
            ItemMeta meta = nextPage.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + "Next page");
            nextPage.setItemMeta(meta);

            inventory.setItem(53, nextPage);
        }

        if (page > 1) {
            ItemStack previousPage = new ItemStack(Material.PAPER);
            ItemMeta meta = previousPage.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + "Previous page");
            previousPage.setItemMeta(meta);

            inventory.setItem(45, previousPage);
        }

        player.openInventory(inventory);
    }
}