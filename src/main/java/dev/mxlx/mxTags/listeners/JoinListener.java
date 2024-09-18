package dev.mxlx.mxTags.listeners;

import dev.mxlx.mxTags.MxTags;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private MxTags mxTags = MxTags.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        int tagID = mxTags.tagManager().getPlayerTagID(player);
        if ((tagID < 1)) return;

        mxTags.tagManager().setTag(player, tagID, false);
    }
}