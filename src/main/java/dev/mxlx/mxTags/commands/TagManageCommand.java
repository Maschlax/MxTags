package dev.mxlx.mxTags.commands;

import dev.mxlx.mxTags.MxTags;
import dev.mxlx.mxTags.utils.TagManager;
import dev.mxlx.mxTags.utils.math;
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
                listTags(sender, args);
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
        sender.sendMessage("/tm list (<page>) : List all tags");
        sender.sendMessage("/tm help : Show this message");
    }

    private void createTag(CommandSender sender, String[] args) {
        if (args.length != 3 && args.length != 2) {
            sender.sendMessage("Invalid arguments provided. Use /tm create <name> (<slot>)");
            return;
        }
        int slot = 0;

        if (args.length == 3) {
            if (!math.isInteger(args[2])) {
                sender.sendMessage("Invalid slot input. Use a valid integer");
                return;
            }
            slot = Integer.parseInt(args[2]);
        } else {
            slot = mxTags.tagManager().getEntryAmount(TagManager.listEntryType.TAG_AMOUNT) + 1;
        }

        mxTags.tagManager().createTag(args[1], slot);
        sender.sendMessage("Successfully created tag '" +  args[1] + ChatColor.WHITE + "' in slot " + ChatColor.GRAY + slot);
    }

    private void deleteTag(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Invalid arguments provided. Use /tm delete <tagID>");
            return;
        }
        if (!math.isInteger(args[1])) {
            sender.sendMessage("Invalid slot input. Use a valid integer");
            return;
        }
        mxTags.tagManager().deleteTag(Integer.parseInt(args[1]));
        sender.sendMessage("Successfully deleted tag with id: " + args[1]);
    }

    private void listTags(CommandSender sender, String[] args) {
        int page = 1;
        if (args.length > 1) {
            if (!math.isInteger(args[1])) {
                sender.sendMessage("Invalid page input. Use a valid integer");
                return;
            } else {
                page = Integer.parseInt(args[1]);
            }
        }
        ArrayList<String> tags = (ArrayList<String>) mxTags.tagManager().listTags(page);

        sender.sendMessage(ChatColor.GOLD + "==> MxTags List" + ChatColor.YELLOW +  "(" + ChatColor.GOLD + "Page " + page + ChatColor.YELLOW + "/" + ChatColor.GOLD + mxTags.tagManager().getEntryAmount(TagManager.listEntryType.CHAT_PAGE_AMOUNT) + ChatColor.YELLOW + ")");
        if (tags.isEmpty()) { sender.sendMessage(ChatColor.RED + "No tags found"); return; }

        sender.sendMessage(ChatColor.WHITE + "id" + ChatColor.GRAY + " :  " + ChatColor.WHITE + "tag" + ChatColor.GRAY + " : " + ChatColor.WHITE + "slot");
        for (String tagEntry : tags) {
            String[] entry = tagEntry.split("¢");
            String tagID = entry[0];
            String tag = entry[1];
            String slot = entry[2];
            sender.sendMessage(ChatColor.WHITE + tagID + ChatColor.GRAY + " : " + tag + ChatColor.GRAY + " : " + ChatColor.WHITE + slot);
        }
        sender.sendMessage("");
    }
}