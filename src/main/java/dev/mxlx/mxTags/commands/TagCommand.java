package dev.mxlx.mxTags.commands;

import dev.mxlx.mxTags.MxTags;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCommand implements CommandExecutor {
    private MxTags mxTags = MxTags.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        String fullMessage = String.join(" ", args);
        fullMessage = mxTags.colorFormatter().formatHexColors(fullMessage);
        player.setDisplayName(player.getName() + " " + fullMessage);
        player.setPlayerListName(player.getName() + " " + fullMessage);

        return true;
    }

}
