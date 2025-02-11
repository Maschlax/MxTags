package net.wishmc.mxTagsV2.command;

import net.wishmc.mxTagsV2.MxTagsV2;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCommand implements CommandExecutor {

    private MxTagsV2 mxTags = MxTagsV2.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(mxTags.formatColors("&cThis command can only be executed by players!"));
            return true;
        }

        Player player = (Player) sender;
        mxTags.tagMenu().openMenu(player, 1);

        return true;
    }
}