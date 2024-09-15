package dev.mxlx.mxTags.listeners;

import dev.mxlx.mxTags.MxTags;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinLeaveListener implements Listener {

    private MxTags mxTags = MxTags.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        int tagID = mxTags.tagManager().getPlayerTagID(player);
        if ((tagID < 1)) return;
        String tag = mxTags.tagManager().getTag(tagID);
        tag = mxTags.colorFormatter().formatHexColors(tag);

        player.setDisplayName(player.getName() + " " + tag);
        player.setPlayerListName(player.getName() + " " + tag);
    }
}