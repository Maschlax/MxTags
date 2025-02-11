package net.wishmc.mxTagsV2.listener;

import net.wishmc.mxTagsV2.MxTagsV2;
import net.wishmc.mxTagsV2.manager.TagMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public class InventoryListener implements Listener {

    private MxTagsV2 mxTags = MxTagsV2.getInstance();
    private TagMenu tagMenu = mxTags.tagMenu();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (!tagMenu.isViewingTagGUI(player)) return;
        if (clickedItem == null) return;
        if (!tagMenu.itemInGUI(player, clickedItem)) return;

        event.setCancelled(true);

        PersistentDataContainer data = clickedItem.getItemMeta().getPersistentDataContainer();
        if (data.has(mxTags.tagMenu().nextPageKey)) {
            tagMenu.changePage(player, 1);
            return;
        }
        if (data.has(mxTags.tagMenu().previousPageKey)) {
            tagMenu.changePage(player, -1);
            return;
        }

        int index = event.getSlot() + mxTags.TAGS_PER_PAGE_GUI * tagMenu.getPage(player) - mxTags.TAGS_PER_PAGE_GUI;
        mxTags.tagManager().selectTag(player, index);
        mxTags.tagMenu().setTagItems(player);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (tagMenu.isViewingTagGUI(player)) tagMenu.closeGUI(player);
    }
}