package dev.mxlx.mxTags.listeners;

import dev.mxlx.mxTags.MxTags;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickListener implements Listener {

    private MxTags mxTags = MxTags.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getCurrentItem() == null) return;

        String title = event.getView().getTitle();
        if (!title.startsWith("Tag Selector")) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        String itemName = item.getItemMeta().getDisplayName();

        String pageString = title.substring(title.indexOf("Page ") + 5);
        pageString = pageString.substring(0, pageString.indexOf("/"));
        int page = Integer.valueOf(pageString);

        if (itemName.equals(ChatColor.YELLOW + "Previous page")) {
            mxTags.tagSelectionGUI().openTagSelectionGUI(player, page - 1);
            return;
        } else if (itemName.equals(ChatColor.YELLOW + "Next page")) {
            mxTags.tagSelectionGUI().openTagSelectionGUI(player, page + 1);
            return;
        }

        if (player.getDisplayName().toLowerCase().contains(itemName.toLowerCase())) return;
        int tagID = (event.getSlot() + 1 ) * page;
        if (mxTags.tagManager().tagExists(itemName)) mxTags.tagManager().selectTag(player, tagID);
        mxTags.tagSelectionGUI().openTagSelectionGUI(player, page);
    }
}