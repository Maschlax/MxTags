package dev.mxlx.mxTags.commands;

import dev.mxlx.mxTags.MxTags;
import dev.mxlx.mxTags.utils.TagManager;
import dev.mxlx.mxTags.utils.math;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class TagManageCommand implements TabExecutor {

    private MxTags mxTags = MxTags.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mxtags.admin")) { sender.sendMessage(ChatColor.RED + "You don't have permission to use this command"); return true; }
        if (args.length == 0) { showHelpMessage(sender); return true; }

        switch (args[0]) {
            case "create":
                createTag(sender, args);
                break;
            case "delete":
                deleteTag(sender, args);
                break;
            case "modify":
                modifyTag(sender, args);
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
        sender.sendMessage("/tm create <name> <priority> : Create a new tag");
        sender.sendMessage("/tm delete <tagID> : Remove a tag");
        sender.sendMessage("/tm modify <tagID> <type> <change> : Modify a tag");
        sender.sendMessage("/tm list (<page>) : List all tags");
        sender.sendMessage("/tm help : Show this message");
    }

    private void createTag(CommandSender sender, String[] args) {
        if (args.length != 3 && args.length != 2) {
            sender.sendMessage("Invalid arguments provided. Use /tm create <name> (<priority>)");
            return;
        }
        int priority = 0;

        if (args.length == 3) {
            if (!math.isInteger(args[2])) {
                sender.sendMessage("Invalid priority input. Use a valid integer");
                return;
            }
            priority = Integer.parseInt(args[2]);
            if (priority < 0) {
                sender.sendMessage(ChatColor.RED + "Invalid priority input. Value cannot be negative");
                return;
            }
        } else {
            priority = mxTags.tagManager().getEntryAmount(TagManager.listEntryType.TAG_AMOUNT) + 1;
        }

        mxTags.tagManager().createTag(args[1], priority);
        sender.sendMessage("Successfully created tag '" +  args[1] + ChatColor.WHITE + "' with priority " + ChatColor.GRAY + priority);
    }

    private void deleteTag(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Invalid arguments provided. Use /tm delete <tagID>");
            return;
        }
        if (!math.isInteger(args[1])) {
            sender.sendMessage(ChatColor.RED + "Invalid priority input. Use a valid integer");
            return;
        }
        mxTags.tagManager().deleteTag(Integer.parseInt(args[1]));
        sender.sendMessage("Successfully deleted tag with id: " + args[1]);
    }

    private void modifyTag(CommandSender sender, String[] args) {
        if (args.length != 4) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments provided. Use /tm modify <tagID> <type> <change>");
            return;
        }
        String id = args[1];
        String type = args[2];
        String change = args[3];
        int tagID = 0;
        if (!math.isInteger(id) || mxTags.tagManager().getTag(Integer.parseInt(id)) == null) {
            sender.sendMessage(ChatColor.RED + "Invalid tagID input. Use a valid integer and make sure the tag exists");
            return;
        }
        tagID = Integer.parseInt(id);

        switch (type) {
            case "tag":
                mxTags.tagManager().modifyTag(tagID, change);
                break;
            case "priority":
                if (!math.isInteger(change)) { sender.sendMessage(ChatColor.RED + "Invalid priority input. Use a valid integer"); return; }
                mxTags.tagManager().modifyTagPriority(tagID, Integer.parseInt(change));
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Invalid type input. Use either 'tag' or 'priority'");
                return;
        }

        sender.sendMessage(ChatColor.GREEN + "Changed " + type + " to '" + change + "' at tag with ID: " + tagID);
    }

    private void listTags(CommandSender sender, String[] args) {
        int page = 1;
        if (args.length > 1) {
            if (!math.isInteger(args[1])) {
                sender.sendMessage(ChatColor.RED + "Invalid page input. Use a valid integer");
                return;
            } else {
                page = Integer.parseInt(args[1]);
            }
        }
        if (page == 0 || page > mxTags.tagManager().getEntryAmount(TagManager.listEntryType.CHAT_PAGE_AMOUNT)) {
            sender.sendMessage(ChatColor.RED + "TagList Page " + page + " doesn't exist");
            return;
        }
        ArrayList<String> tags = (ArrayList<String>) mxTags.tagManager().listTags(page);

        sender.sendMessage(ChatColor.GOLD + "==> MxTags List " + ChatColor.YELLOW +  "(" + ChatColor.GOLD + "Page " + page + ChatColor.YELLOW + "/" + ChatColor.GOLD + mxTags.tagManager().getEntryAmount(TagManager.listEntryType.CHAT_PAGE_AMOUNT) + ChatColor.YELLOW + ")");
        if (tags.isEmpty()) { sender.sendMessage(ChatColor.RED + "No tags found"); return; }

        sender.sendMessage(ChatColor.WHITE + "id" + ChatColor.GRAY + " :  " + ChatColor.WHITE + "tag" + ChatColor.GRAY + " : " + ChatColor.WHITE + "priority");
        for (String tagEntry : tags) {
            String[] entry = tagEntry.split("¢");
            String tagID = entry[0];
            String tag = entry[1];
            String priority = entry[2];
            sender.sendMessage(ChatColor.WHITE + tagID + ChatColor.GRAY + " : " + tag + ChatColor.GRAY + " : " + ChatColor.WHITE + priority);
        }
        sender.sendMessage("");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            ArrayList<String> options = new ArrayList<>();
            options.add("create");
            options.add("delete");
            options.add("modify");
            options.add("list");
            options.add("help");
            return options;

        } else if (args.length == 2 && args[0].equals("list")) {
            ArrayList<String> options = new ArrayList<>();
            int chatPageAmount = mxTags.tagManager().getEntryAmount(TagManager.listEntryType.CHAT_PAGE_AMOUNT);
            for (int i = 1; i <= chatPageAmount; i++) {
                options.add(String.valueOf(i));
            }
            return options;

        } else if (args.length == 3 && args[0].equals("modify")) {
            ArrayList<String> options = new ArrayList<>();
            options.add("tag");
            options.add("priority");
            return options;
        }

        return null;
    }
}