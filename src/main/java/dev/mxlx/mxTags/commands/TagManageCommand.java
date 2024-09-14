package dev.mxlx.mxTags.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TagManageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) { showHelpMessage(sender); return true; }

        switch (args[0]) {
            case "create":
                break;
            case "remove":
                break;
            case "modify":
                break;
            case "list":
                break;
            case "help": default:
                showHelpMessage(sender);
                break;
        }
        return true;
    }

    private void showHelpMessage(CommandSender sender) {
        sender.sendMessage("==================== MxTags Help ====================");
        sender.sendMessage(" • Use -1 for priority if none specified");
        sender.sendMessage("/tag create <name> <priority> : Create a new tag");
        sender.sendMessage("/tag remove <name> <tagID> : Remove a tag");
        sender.sendMessage("/tag modify <tagID> <name> <priority> : Modify a tag");
        sender.sendMessage("/tag list : List all tags");
        sender.sendMessage("/tag help : Show this message");
    }
}