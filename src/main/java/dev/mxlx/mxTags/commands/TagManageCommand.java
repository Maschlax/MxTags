package dev.mxlx.mxTags.commands;

import dev.mxlx.mxTags.MxTags;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class TagManageCommand implements CommandExecutor {

    private MxTags mxTags = MxTags.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) { showHelpMessage(sender); return true; }

        switch (args[0]) {
            case "create":
                createTag(sender, args);
                break;
            case "delete":
                deleteTag(sender, args);
                break;
            case "modify":
                break;
            case "list":
                listTags(sender);
                break;
            case "help": default:
                showHelpMessage(sender);
                break;
        }
        return true;
    }

    private void showHelpMessage(CommandSender sender) {
        sender.sendMessage("==> MxTags Help");
        sender.sendMessage(" • Use -1 for inverted slot order");
        sender.sendMessage("/tm create <name> <slot> : Create a new tag");
        sender.sendMessage("/tm delete <tagID> : Remove a tag");
        sender.sendMessage("/tm modify <tagID> <name> <slot> : Modify a tag");
        sender.sendMessage("/tm list : List all tags");
        sender.sendMessage("/tm help : Show this message");
    }

    private void createTag(CommandSender sender, String[] args) {
        if (args.length != 3) {
            sender.sendMessage("Invalid arguments provided. Use /tm create <name> <slot>");
            return;
        }
        try {
            Integer.parseInt(args[2]);
        } catch (NumberFormatException exception) {
            sender.sendMessage("Invalid slot input. Use a valid integer");
            return;
        }
        mxTags.tagManager().createTag(args[1], Integer.parseInt(args[2]));
        sender.sendMessage("Successfully created tag '" +  args[1] + ChatColor.WHITE + "' in slot " + ChatColor.GRAY + args[2]);
    }

    private void deleteTag(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Invalid arguments provided. Use /tm delete <tagID>");
            return;
        }
        try {
            Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
            sender.sendMessage("Invalid slot input. Use a valid integer");
            return;
        }
        mxTags.tagManager().deleteTag(Integer.parseInt(args[1]));
        sender.sendMessage("Successfully deleted tag with id: " + args[1]);
    }

    private void listTags(CommandSender sender) {
        sender.sendMessage("==> MxTags List");
        ArrayList<String> tags = (ArrayList<String>) mxTags.tagManager().listTags();

        if (tags.isEmpty()) { sender.sendMessage(ChatColor.GRAY + "No tags found"); return; }

        for (String tagEntry : tags) {
            String[] entry = tagEntry.split("¢");
            String tagID = entry[0];
            String tag = entry[1];
            sender.sendMessage(ChatColor.GRAY + tagID + ChatColor.DARK_GRAY + " : " + tag);
        }
    }
}