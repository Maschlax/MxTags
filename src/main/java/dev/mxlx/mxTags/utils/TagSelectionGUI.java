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

        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.DARK_AQUA + "Tag Selector - Page " + page);
        ArrayList<String> tags = (ArrayList<String>) mxTags.tagManager().listTagsAsGUIpage(page);

        int index = 0;
        for (String tag : tags) {
            ItemStack item = new ItemStack(Material.NAME_TAG);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(tag);
            List<String> lore = Arrays.asList("", ChatColor.GREEN + "Click to select this tag");
            if (tagManager.getTag(tagManager.getPlayerTagID(player)).equals(tag)) {
                lore.set(1, ChatColor.AQUA + "You have selected this tag");
            }
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