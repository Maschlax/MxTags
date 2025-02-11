package net.wishmc.mxTagsV2.listener;

import net.wishmc.mxTagsV2.MxTagsV2;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private MxTagsV2 mxTags = MxTagsV2.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (mxTags.tagManager().tagNotExists(mxTags.tagManager().getPlayerTag(player))) mxTags.tagManager().removeTag(player);
        mxTags.tagManager().setTag(player);
    }
}