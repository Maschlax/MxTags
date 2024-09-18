package dev.mxlx.mxTags.commands;

import dev.mxlx.mxTags.MxTags;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCommand implements CommandExecutor {

    private MxTags mxTags = MxTags.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) { sender.sendMessage(ChatColor.RED + "This command is player exclusive!"); return true; }
        Player player = (Player) sender;

        mxTags.tagSelectionGUI().openTagSelectionGUI(player, 1);

        /*
        try {
            int tagID = Integer.parseInt(args[0]);
            mxTags.tagManager().selectTag(player, tagID);

        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }*/

        return true;
    }
}