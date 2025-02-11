package net.wishmc.mxTagsV2.manager;

import net.wishmc.mxTagsV2.MxTagsV2;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

import static net.wishmc.mxTagsV2.MxTagsV2.formatColors;

public class TagMenu {

    private MxTagsV2 mxTags = MxTagsV2.getInstance();

    public int guiPageAmount = (int) Math.ceil((double) mxTags.tagManager().getTagAmount() / mxTags.TAGS_PER_PAGE_GUI);

    public NamespacedKey nextPageKey = new NamespacedKey(mxTags, "nextpage");
    public NamespacedKey previousPageKey = new NamespacedKey(mxTags, "previouspage");

    private Map<Player, Inventory> activeGUIs = new HashMap<>();
    private Map<Player, Integer> guiPage = new HashMap<>();

    public void openMenu(Player player, int page) {
        player.closeInventory();
        Inventory inventory = Bukkit.createInventory(player, mxTags.TAGS_PER_PAGE_GUI + 9, "Tag Selector - Page " + page + "/" + guiPageAmount);

        activeGUIs.put(player, inventory);
        guiPage.put(player, page);

        setTagItems(player);
        player.openInventory(inventory);
    }

    public void setTagItems(Player player) {
        Inventory inventory = activeGUIs.get(player);
        inventory.clear();
        int page = getPage(player);
        String selectedTag = mxTags.tagManager().getPlayerTag(player);

        mxTags.tagManager().getGUITagList(page).forEach(tag -> addTagItem(inventory, tag, tag.equals(selectedTag)));

        if (page + 1 <= guiPageAmount) { // if there is a next page
            ItemStack nextPage = new ItemStack(Material.PAPER);
            nextPage.editMeta(meta -> {
                meta.setDisplayName(formatColors("&eNext Page"));
                meta.getPersistentDataContainer().set(nextPageKey, PersistentDataType.BOOLEAN, true);
            });
            inventory.setItem(mxTags.TAGS_PER_PAGE_GUI + 8, nextPage);

        }
        if (page > 1) { // if there is a previous page
            ItemStack previousPage = new ItemStack(Material.PAPER);
            previousPage.editMeta(meta -> {
                meta.setDisplayName(formatColors("&ePrevious Page"));
                meta.getPersistentDataContainer().set(previousPageKey, PersistentDataType.BOOLEAN, true);
            });
            inventory.setItem(mxTags.TAGS_PER_PAGE_GUI, previousPage);
        }
    }

    private void addTagItem(Inventory inventory, String tag, boolean tagSelected) {
        ItemStack item = new ItemStack(Material.NAME_TAG);
        item.editMeta(meta -> {
            meta.setDisplayName(tag);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            List<String> lore = Arrays.asList("", ChatColor.GREEN + "Click to select this tag");
            if (tagSelected) {
                meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
                lore.set(1, ChatColor.AQUA + "You have selected this tag");
            }
            meta.setLore(lore);
        });

        inventory.addItem(item);
    }

    public void changePage(Player player, int increment) {
        int page = guiPage.get(player) + increment;
        guiPage.put(player, page);
        openMenu(player, page);
    }

    public int getPage(Player player) {
        return guiPage.get(player);
    }

    public boolean isViewingTagGUI(Player player) {
        return activeGUIs.containsKey(player);
    }

    public boolean itemInGUI(Player player, ItemStack item) {
        return activeGUIs.get(player).contains(item);
    }

    public void closeGUI(Player player) {
        activeGUIs.remove(player);
        guiPage.remove(player);
    }
}